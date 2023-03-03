package org.hexagonal.reference.infrastructure.adapter.driving;

import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.error.Error.ValidationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorHandler {

  public static ResponseEntity<Error> handleError(Error error){
    return switch (error){
      case ValidationErrors validationErrors ->  ResponseEntity.badRequest().body(validationErrors);
      case Error.TechnicalError technicalError -> ResponseEntity.internalServerError().body(technicalError);
      case Error.BusinessError.UserNotFound userNotFoundError -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotFoundError);
    };
  }
}
