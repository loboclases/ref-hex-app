package org.hexagonal.reference.application;

import io.vavr.control.Either;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hexagonal.reference.application.usecase.CreateUserUseCase;
import org.hexagonal.reference.application.usecase.FindUserUseCase;
import org.hexagonal.reference.application.usecase.command.CreateUserCommand;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.application.usecase.query.GetUserByIdQuery;
import org.hexagonal.reference.application.usecase.query.GetUserQueryByName;
import org.hexagonal.reference.domain.factory.UserFactory;
import org.hexagonal.reference.domain.model.User;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.port.driven.UseCase;
import org.hexagonal.reference.domain.port.driven.UserRepository;

@RequiredArgsConstructor
@Slf4j
public class BaseApplicationService implements CreateUserUseCase, FindUserUseCase {

  private final UserFactory userFactory;
  private final UserRepository userRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  @UseCase
  public Either<Error, UserDTO> createUser(CreateUserCommand createUserCommand) {
    return userFactory.createUser(
            createUserCommand.getUsername(), createUserCommand.getEmail(), createUserCommand.getAge(),
            0l).
        flatMap(user -> userRepository.saveUser(user))
        .map(user -> mapToDto(user));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @UseCase
  public Either<Error, Optional<UserDTO>> findUserByName(
      GetUserQueryByName getUserQuery) {
    return userRepository.findByUsername(getUserQuery.getName())
        .map(optUser -> optUser.map(user -> mapToDto(user))
        );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @UseCase
  public Either<Error, UserDTO> findUserById(GetUserByIdQuery getUserQuery) {
    return userRepository.findById(getUserQuery.getId()).map(user -> mapToDto(user));
  }

  private UserDTO mapToDto(User user) {
    return UserDTO.builder().name(user.getUserName()).id(user.getId().getValue())
        .age(user.getAge().getValue()).email(user.getEmail().getValue()).build();
  }
}
