package org.hexagonal.reference.application.usecase;


import io.vavr.control.Either;
import java.util.Optional;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.application.usecase.query.GetUserQueryById;
import org.hexagonal.reference.application.usecase.query.GetUserQueryByName;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * The interface Find user use case.
 */
public interface FindUserUseCase {

  /**
   * Find user by name either.
   *
   * @param getUserQuery the get user query
   * @return the either
   */
  Either<Error, Optional<UserDTO>> findUserByName(GetUserQueryByName getUserQuery);

  /**
   * Find user by id.
   *
   * @param getUserQuery the get user query
   * @return the either
   */
  Either<Error, UserDTO> findUserById(GetUserQueryById getUserQuery);

}
