package org.hexagonal.reference.infrastructure.adapter.driving;

import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.application.usecase.CreateUserUseCase;
import org.hexagonal.reference.application.usecase.FindUserUseCase;
import org.hexagonal.reference.application.usecase.command.CreateUserCommand;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.application.usecase.query.GetUserByIdQuery;
import org.hexagonal.reference.application.usecase.query.GetUserQueryByName;
import org.hexagonal.reference.infrastructure.adapter.model.UserResource;
import org.hexagonal.reference.infrastructure.bus.command.SpringCommandBus;
import org.hexagonal.reference.infrastructure.bus.query.SpringQueryBus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type User rest adapter.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestAdapter {

  private final FindUserUseCase findUserUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final SpringQueryBus springQueryBus;
  private final SpringCommandBus springCommandBus;

  /**
   * Find user by id response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findUserById(@PathVariable("id") Long id) {
    return GetUserByIdQuery.validateAndCreate(
            id)
        .toEither()
        .flatMap(query ->findUserUseCase.findUserById(query))
        .mapLeft(error ->
            ResponseEntity.badRequest().body(error)
        )
        .fold(error -> ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(error),
            data -> ResponseEntity.ok(
                UserResource.builder().age(data.getAge())
                    .name(data.getName()).email(data.getEmail())
                    .id(data.getId())
                    .build()));
  }
  /**
   * Find user by id response entity using Query Bus.
   *
   * @param id the id
   * @return the response entity
   */
  @GetMapping("/bus/{id}")
  public ResponseEntity<?> findUserByIdViaQueryBus(@PathVariable("id") Long id) {
    return GetUserByIdQuery.validateAndCreate(
            id)
        .toEither()
        .<UserDTO>flatMap(query ->springQueryBus.execute(query))
        .mapLeft(error ->
            ResponseEntity.badRequest().body(error)
        )
        .fold(error -> ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(error),
            data -> ResponseEntity.ok(
                UserResource.builder().age(data.getAge())
                    .name(data.getName()).email(data.getEmail())
                    .id(data.getId())
                    .build()));
  }

  /**
   * Find user by name response entity.
   *
   * @param name the name
   * @return the response entity
   */
  @GetMapping("")
  public ResponseEntity<?> findUserByName(@RequestParam("name") String name) {
    return GetUserQueryByName.validateAndCreate(
        name)
    .toEither()
        .flatMap(query->findUserUseCase.findUserByName(query))
        .mapLeft(ErrorHandler::handleError)
        .fold(error ->ErrorHandler.handleError(error.getBody()),
            data -> ResponseEntity.ok(data.map(user->UserResource.builder().age(user.getAge())
                .name(user.getName()).email(user.getEmail())
                .id(user.getId())
                .build()))
                );

  }

  /**
   * Create user response entity.
   *
   * @param userResource the user resource
   * @return the response entity
   */
  @PostMapping("/")
  public ResponseEntity<?> createUser(@RequestBody UserResource userResource) {
    return CreateUserCommand.validateAndCreate(
        userResource.getName(),
        userResource.getAge(), userResource.getEmail()).toEither()
        .flatMap(command->this.createUserUseCase.createUser(command))
        .mapLeft(ErrorHandler::handleError)
        .fold(error -> ErrorHandler.handleError(error.getBody()),savedUser->ResponseEntity.ok(
            UserResource.builder().age(savedUser.getAge())
                .name(savedUser.getName()).email(savedUser.getEmail())
                .id(savedUser.getId())
                .build()));
  }
  /**
   * Create user response entity.
   *
   * @param userResource the user resource
   * @return the response entity
   */
  @PostMapping("/bus/")
  public ResponseEntity<?> createUserByQueryBus(@RequestBody UserResource userResource) {
    return CreateUserCommand.validateAndCreate(
            userResource.getName(),
            userResource.getAge(), userResource.getEmail()).toEither()
        .flatMap(command->this.springCommandBus.execute(command))
        .mapLeft(ErrorHandler::handleError)
        .fold(error -> ErrorHandler.handleError(error.getBody()),savedUser->ResponseEntity.status(201).build());
  }

}
