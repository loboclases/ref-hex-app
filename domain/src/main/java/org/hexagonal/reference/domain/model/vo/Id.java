package org.hexagonal.reference.domain.model.vo;

import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidId;
import org.hexagonal.reference.domain.specification.PositiveNumber;

/**
 * The type Id.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Id {

  private final Long value;

  /**
   * Validate and create validation.
   *
   * @param id the id
   * @return the validation
   */
  public static Validation<ValidationError, Id> validateAndCreate(Long id) {
    //this is a business validation relative only to Email
    //it is guaranteed by application layer that the input argument has already a valid email format
    return new PositiveNumber().isSatisfiedBy(id) ? Validation.valid(new Id(id))
        : Validation.invalid(new InvalidId(id));
  }
}
