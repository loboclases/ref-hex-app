package org.hexagonal.reference.application.handler.query;

import io.vavr.control.Either;
import org.hexagonal.reference.application.handler.ApplicationService;
import org.hexagonal.reference.application.usecase.GetUserByIdUseCase;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.application.usecase.query.GetUserByIdQuery;
import org.hexagonal.reference.domain.model.User;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.port.driven.UserRepository;

/**
 * The type Get user by id handler.
 *
 * This is an alternative way to build application services using query handlers instead of application services
 * This way the query input is decoupled from the logic that performs the query itself allowing multiple implementations
 *
 * @author joseluis.anton
 */
//@RequiredArgsConstructor
  @ApplicationService
public class GetUserByIdHandler implements GetUserByIdUseCase {
  private  UserRepository userRepository;

  public GetUserByIdHandler(UserRepository userRepository){
    this.userRepository=userRepository;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  //@UseCase-->Ojo, esto da problemas en el query bus pq usa proxys y no es capaz de localizar la bean de GetUserByIdHandler por tipo
  //work around es en el registry, registrar la bean en base a su interfaz, que en ese caso si es capaz de trabajar bien
  //Opción buena, reescribir el transaction manager según lo que tiene quique
  public Either<Error, UserDTO> handle(GetUserByIdQuery query) {
    return userRepository.findById(query.getId()).map(user -> mapToDto(user));
  }
  private UserDTO mapToDto(User user) {
    return UserDTO.builder().name(user.getUserName()).id(user.getId().getValue())
        .age(user.getAge().getValue()).email(user.getEmail().getValue()).build();
  }
}
