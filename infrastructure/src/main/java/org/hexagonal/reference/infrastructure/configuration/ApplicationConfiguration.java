package org.hexagonal.reference.infrastructure.configuration;


import org.hexagonal.reference.application.BaseApplicationService;
import org.hexagonal.reference.application.handler.ApplicationService;
import org.hexagonal.reference.domain.factory.UserFactory;
import org.hexagonal.reference.domain.factory.UserFactoryImpl;
import org.hexagonal.reference.domain.port.driven.UserRepository;
import org.hexagonal.reference.infrastructure.bus.query.Registry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Application configuration.
 *
 * @author joseluis.anton
 */
@Configuration
@EnableJpaRepositories("org.hexagonal.reference.infrastructure.persistence.repository")
@EntityScan(" org.hexagonal.reference.infrastructure.persistence.model")
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(value = "org.hexagonal.reference",includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = ApplicationService.class))
public class ApplicationConfiguration {




  /**
   * Application curso service application service.
   *
   * @param userRepository the user repository
   * @return the application service
   */
  @Bean
  public BaseApplicationService applicationCursoService(UserRepository userRepository) {

    return new BaseApplicationService(userFactory(), userRepository);
  }

  @Bean
  public Registry registry(ApplicationContext applicationContext){
    return new Registry(applicationContext);
  }

  /**
   * User factory user factory.
   *
   * @return the user factory
   */
  @Bean
  public UserFactory userFactory() {
    return new UserFactoryImpl();
  }

}