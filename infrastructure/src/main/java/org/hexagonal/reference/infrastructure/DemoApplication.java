package org.hexagonal.reference.infrastructure;

import org.hexagonal.reference.infrastructure.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The type Curso application.
 *
 * @author joseluis.anton
 */
@SpringBootApplication
@Import(ApplicationConfiguration.class)
public class DemoApplication {

  /**
   * Main.
   *
   * @param arg the arg
   */
  public static void main(String... arg){
    SpringApplication.run(DemoApplication.class);
  }
}
