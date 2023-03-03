package org.hexagonal.reference.infrastructure.adapter.driven;

import static io.vavr.API.$;
import static io.vavr.API.Match;

import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hexagonal.reference.domain.factory.UserFactory;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.User;
import org.hexagonal.reference.domain.model.error.Error.BusinessError.UserNotFound;
import org.hexagonal.reference.domain.model.error.Error.TechnicalError;
import org.hexagonal.reference.domain.port.driven.UserRepository;
import org.hexagonal.reference.infrastructure.persistence.model.UserEntity;
import org.hexagonal.reference.infrastructure.persistence.repository.PostgresUserRepository;
import org.springframework.stereotype.Repository;


/**
 * The type User repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

  private final UserFactory userFactory;
  private final PostgresUserRepository userRepository;

  @Override
  public Either<Error, Optional<User>> findByUsername(String username) {
    return Try.of(() -> userRepository.findByName(username))
        .onFailure(e -> log.error(
            "Could not find user with id %s due to some error %s".formatted(username,
                e.getMessage())))
        .toEither()
        .<Error>mapLeft(exception -> new TechnicalError(
            "Error searching user with id %s due to error &s".formatted(username,
                exception.getMessage()),
            exception)
        ).map(entity -> Optional.ofNullable(entity).map(user ->
            userFactory.createUser(user.getName(), user.getEmail(), user.getAge(), user.getId())
                .get()));

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Either<Error, User> saveUser(User user) {
    UserEntity entity = new UserEntity();
    entity.setAge(user.getAge().getValue());
    entity.setName(user.getUserName());
    entity.setEmail(user.getEmail().getValue());
    return Try.of(() ->userRepository.save(entity)
        )
        .toEither()
        .<Error>mapLeft(exception -> new TechnicalError(
            "Error saving user %s due to error %s".formatted(user, exception.getMessage()),
            exception)
        ).map(savedUser -> userFactory.createUser(savedUser.getName(), savedUser.getEmail(),
            savedUser.getAge(), savedUser.getId()).get());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Either<Error, Void> deleteUser(String username) {
    Either<List<Error>, Void> result = null;
    Either<List<Error>, UserEntity> findOperation = null;
    return Try.of(
            () -> userRepository.findByName(username))
        .onFailure(e -> log.error(
            "Could not find user with name %s due to some error %s".formatted(username,
                e.getMessage())))
        .toEither().<Error>mapLeft(
            exception ->
                new TechnicalError(
                    "Error, user with name %s could not be found due to error %s".formatted(
                        username, exception.getMessage()),
                    exception)).filter(entityOpt -> null != entityOpt).<Void>map(user -> {
          userRepository.delete(user.get());
          return null;
        })
        .toEither(new UserNotFound(username));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Either<Error, User> findById(Long id) {

    return Try.of(() -> userRepository.findById(id)).onFailure(e -> log.error(
            "Could not find user with id %s due to some error %s".formatted(id, e.getMessage())))
        .toEither()
        .<Error>mapLeft(exception -> new TechnicalError(
            "Error searching user with id %s due to error &s".formatted(id, exception.getMessage()),
            exception)
        ).filter(entityOpt -> entityOpt.isPresent()).map(user -> user.get().map(
            foundUser -> userFactory.createUser(foundUser.getName(), foundUser.getEmail(),
                    foundUser.getAge(), foundUser.getId())
                .get()).get())
        .toEither(new UserNotFound(id.toString()));

  }
}
