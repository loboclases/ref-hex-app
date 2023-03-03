package org.hexagonal.reference.infrastructure.adapter.driven.transactional;

//import javax.annotation.Resource;
import jakarta.annotation.Resource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * The type Transactional bean post processor.
 * 
 * @author joseluis.anton
 */
@Component
public class TransactionalBeanPostProcessor implements BeanPostProcessor {
  @Resource
  private PlatformTransactionManager transactionManager;
  /**
   * {@inheritDoc}
   */
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

    Object result = bean;
    
     if(UseCaseHelper.isUseCase(bean)){
      ProxyFactory proxyFactory = new ProxyFactory(bean);
      proxyFactory.addAdvice(new TransactionInterceptor(transactionManager));
      result = proxyFactory.getProxy();
    }
    return result;
  }

  
}
