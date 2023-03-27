package org.hexagonal.reference.infrastructure.adapter.driving;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.error.Error.ValidationErrors;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidAge;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidEmail;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidId;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

/**
 * The type Error handler.
 */
public class ErrorHandler {

  /**
   * Handle error response entity.
   *
   * Note: The Switch Pattern Matching is in preview mode in Java 17, so it will need to be enabled, otherwise this will not work
   *
   * @param error the error
   * @return the response entity
   */
  public static List<ProblemDetail> handleError(Error error){
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("");
    return switch (error){
      case ValidationErrors validationErrors ->  List.of(mapError(validationErrors));
      case Error.TechnicalError technicalError -> List.of(mapError(technicalError));
      case Error.BusinessError.UserNotFound userNotFoundError -> List.of(mapError(userNotFoundError));
    };
  }
  private static ProblemDetail mapError(Error.BusinessError.UserNotFound error){
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    problemDetail.setTitle("User not found");
    problemDetail.setDetail("User with id %s not found".formatted(error.msg()));
    return problemDetail;
  }
  private static ProblemDetail mapError(Error.TechnicalError error){
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problemDetail.setTitle("Something went wrong");
    problemDetail.setInstance(URI.create(error.origin()));
    problemDetail.setDetail(error.msg());
    return problemDetail;
  }
  private static ProblemDetail mapError(Error.ValidationErrors error){
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Input Validation error");
    problemDetail.setDetail(error.errors().stream().map(validationError->getValidationError(validationError)).collect(Collectors.joining(", ")));
    return problemDetail;
  }

  private static String getValidationError(ValidationError error){
    return switch (error){
      case InvalidAge invalidAge->"Age %s is invalid".formatted(invalidAge.age());
      case InvalidEmail invalidEmail -> "Email %s is invalid".formatted(invalidEmail.email());
      case InvalidId invalidId ->  "Id %s is invalid".formatted(invalidId.id());
      case InvalidName invalidName -> "Name %s is invalid".formatted(invalidName.name());
    };
  }
}
