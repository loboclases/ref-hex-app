package org.hexagonal.reference.infrastructure.adapter.driven.transactional;

import io.vavr.control.Either;
import io.vavr.control.Validation;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * The type Transaction interceptor.
 *
 * @author joseluis.anton
 */
@AllArgsConstructor
@Slf4j
public class TransactionInterceptor implements MethodInterceptor {

  private PlatformTransactionManager transactionManager;

  /**
   * {@inheritDoc}
   */
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    log.error("");
    Object result = null;
    if (UseCaseHelper.isUseCasePresentInType(invocation.getThis())
        || UseCaseHelper.isUseCasePresentInMethod(invocation.getMethod())) {
      result = executeInTransaction(invocation);
    } else {
      result = invocation.proceed();
    }
    return result;


  }

  private Object executeInTransaction(MethodInvocation invocation) {
    final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
    return transactionTemplate.execute(new TransactionCallback<Object>() {
      @SneakyThrows
      @Override
      public Object doInTransaction(TransactionStatus status) {
        Object result = invocation.proceed();
//        Note: The Switch Pattern Matching is in preview mode in Java 17, so it will need to be enabled, otherwise this will not work
        switch (result){
          case Either eitherResult->{if(eitherResult.isLeft()) status.setRollbackOnly();}
          case Validation validationResult->{if(validationResult.isInvalid()) status.setRollbackOnly();}
          case Try tryResult->{if(tryResult.isFailure()) status.setRollbackOnly();}
          default -> {}
        }
        return result;
      }
    });
  }
}
