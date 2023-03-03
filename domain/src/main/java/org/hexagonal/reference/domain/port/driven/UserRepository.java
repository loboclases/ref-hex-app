package org.hexagonal.reference.domain.port.driven;

import io.vavr.control.Either;
import java.util.Optional;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.User;

/**
 * The interface User repository.
 */
public interface UserRepository {

  /**
   * Find by username either.
   *
   * @param username the username
   * @return the either
   */
  Either<Error, Optional<User>> findByUsername(String username);

  /**
   * Save user either.
   *
   * @param user the user
   * @return the either
   */
  @UseCase
  Either<Error,User> saveUser(User user);

  /**
   * Delete user either.
   *
   * @param username the username
   * @return the either
   */
  Either<Error,Void> deleteUser(String username);

  /**
   * Find by id either.
   *
   * @param id the id
   * @return the either
   */
  Either<Error,User> findById(Long id);


}
