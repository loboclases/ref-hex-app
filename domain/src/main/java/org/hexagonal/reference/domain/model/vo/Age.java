package org.hexagonal.reference.domain.model.vo;


import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidAge;

/**
 * The type Age.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Age {

  private final Integer value;

  /**
   * Validate and create validation.
   *
   * @param age the age
   * @return the validation
   */
  public static Validation<ValidationError, Age> validateAndCreate(Integer age) {
    //this is a business validation relative only to Age
    return age >= 18 && age < 150 ? Validation.valid(new Age(age))
        : Validation.invalid(new InvalidAge(age));
  }
}
