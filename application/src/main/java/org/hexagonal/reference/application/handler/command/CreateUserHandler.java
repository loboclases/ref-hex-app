package org.hexagonal.reference.application.handler.command;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.application.bus.command.CommandHandler;
import org.hexagonal.reference.application.handler.ApplicationService;
import org.hexagonal.reference.application.usecase.command.CreateUserCommand;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.domain.factory.UserFactory;
import org.hexagonal.reference.domain.model.User;
import org.hexagonal.reference.domain.port.driven.UserRepository;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * The type Create user handler.
 *
 * @author joseluis.anton
 */
@RequiredArgsConstructor
@ApplicationService
public class CreateUserHandler implements CommandHandler<CreateUserCommand> {

  private final UserFactory userFactory;
  private final UserRepository userRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  //@UseCase-->Ojo, esto da problemas en el query bus pq usa proxys y no es capaz de localizar la bean de GetUserByIdHandler por tipo
  //work around es en el registry, registrar la bean en base a su interfaz, que en ese caso si es capaz de trabajar bien
  //Opción buena, reescribir el transaction manager según lo que tiene quique
  public Either<Error, Void> handle(CreateUserCommand command) {
    return userFactory.createUser(
            command.getUsername(), command.getEmail(), command.getAge(),
            0l).
        flatMap(entity -> userRepository.saveUser(entity))
        .<Void>flatMap(user -> Either.right(null));
  }

  private UserDTO mapToDto(User user) {
    return UserDTO.builder().name(user.getUserName()).id(user.getId().getValue())
        .age(user.getAge().getValue()).email(user.getEmail().getValue()).build();
  }
}
