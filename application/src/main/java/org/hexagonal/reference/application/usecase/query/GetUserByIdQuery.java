package org.hexagonal.reference.application.usecase.query;

import io.vavr.control.Validation;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.application.bus.query.Query;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.error.Error.ValidationErrors;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidId;
import org.hexagonal.reference.domain.specification.PositiveNumber;

/**
 * The type Get user by id query.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserByIdQuery implements Query {

  private final Long id;

  /**
   * Validate and create validation.
   *
   * @param id the id
   * @return the validation
   */
  public static Validation<Error, GetUserByIdQuery> validateAndCreate(
       final Long id) {
    return new PositiveNumber().isSatisfiedBy(id) ? Validation.valid(new GetUserByIdQuery(id))
        : Validation.invalid(new ValidationErrors(Arrays.asList(new InvalidId(id))));
  }

}
