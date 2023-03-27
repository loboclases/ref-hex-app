package org.hexagonal.reference.application.bus.command;

import io.vavr.control.Either;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * A handler for a {@link Command}.
 *
 * @param <C> the type parameter
 */
public interface CommandHandler <C extends Command> {

  /**
   * Handles the command.
   *
   * @param command the command
   * @return the either
   */
  Either<Error,Void> handle(C command);
}
