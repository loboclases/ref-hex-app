package org.hexagonal.reference.infrastructure.bus.command;

import io.vavr.Lazy;
import org.hexagonal.reference.application.bus.command.CommandHandler;
import org.springframework.context.ApplicationContext;

/**
 * The type Command provider.
 *
 * @param <H> the type parameter
 * @author joseluis.anton
 */
public class CommandProvider<H extends CommandHandler<?>> {
  private final ApplicationContext applicationContext;
  private final Lazy<H> commandProvider;

  private Class<H> type;

  /**
   * Instantiates a new Command provider.
   *
   * @param applicationContext the application context
   * @param type               the type
   */
  CommandProvider(ApplicationContext applicationContext, Class<H> type) {
    this.applicationContext = applicationContext;
    commandProvider =Lazy.of(()->applicationContext.getBean(type));
  }

  /**
   * Get the H typed command handler.
   *
   * @return the h
   */
  public H get() {
    return commandProvider.get();
  }
}
