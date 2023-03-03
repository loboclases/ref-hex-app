package org.hexagonal.reference.infrastructure.configuration;


import org.hexagonal.reference.application.ApplicationService;
import org.hexagonal.reference.domain.factory.UserFactory;
import org.hexagonal.reference.domain.factory.UserFactoryImpl;
import org.hexagonal.reference.domain.port.driven.UserRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Application configuration.
 *
 * @author joseluis.anton
 */
@Configuration
@EnableJpaRepositories("org.hexagonal.reference.infrastructure.persistence.repository")
@EntityScan(" org.hexagonal.reference.infrastructure.persistence.model")
@EnableAspectJAutoProxy
@ComponentScan("org.hexagonal.reference")
public class ApplicationConfiguration {


  /**
   * Application curso service application service.
   *
   * @param userRepository the user repository
   * @return the application service
   */
  @Bean
  public ApplicationService applicationCursoService(UserRepository userRepository) {

    return new ApplicationService(userFactory(), userRepository);
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