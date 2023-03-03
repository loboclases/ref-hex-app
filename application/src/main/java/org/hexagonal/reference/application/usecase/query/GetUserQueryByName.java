package org.hexagonal.reference.application.usecase.query;

import io.vavr.control.Validation;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.error.Error.ValidationErrors;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidName;
import org.hexagonal.reference.domain.specification.StringNotEmpty;

/**
 * The type Get user query.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserQueryByName {

  private final String name;

  /**
   * Validate and create validation.
   *
   * @param name the name
   * @return the validation
   */
  public static Validation<Error, GetUserQueryByName> validateAndCreate(
      final String name) {
    return new StringNotEmpty().isSatisfiedBy(name) ? Validation.valid(new GetUserQueryByName(name))
        : Validation.invalid(new ValidationErrors(Arrays.asList(new InvalidName(name))));
  }

}
