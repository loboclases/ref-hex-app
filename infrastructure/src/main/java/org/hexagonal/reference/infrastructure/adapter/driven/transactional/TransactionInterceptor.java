package org.hexagonal.reference.infrastructure.adapter.driven.transactional;

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
        return invocation.proceed();
      }
    });
  }
}
