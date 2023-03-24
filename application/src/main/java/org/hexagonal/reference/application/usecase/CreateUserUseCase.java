package org.hexagonal.reference.application.usecase;


import io.vavr.control.Either;
import org.hexagonal.reference.application.usecase.command.CreateUserCommand;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * The interface Create user use case.
 */
public interface CreateUserUseCase {

  /**
   * Create user either.
   *
   * @param createUserCommand the create user command
   * @return the either
   */
  Either<Error, UserDTO> createUser(CreateUserCommand createUserCommand);
}
