package org.hexagonal.reference.application.bus.command;

import io.vavr.control.Either;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * Command bus is able to execute commands by passing the command object to its handler.
 */
public interface CommandBus {

  /**
   * Executes the command passed as argument.
   *
   * @param <C>     the type parameter
   * @param command the command
   * @return the either containing the error or void
   */
  <C extends Command> Either<Error,Void> execute(C command);
}
