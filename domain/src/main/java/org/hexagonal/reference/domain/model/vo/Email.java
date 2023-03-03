package org.hexagonal.reference.domain.model.vo;

import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidEmail;

/**
 * The type Email.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Email {

  private final String value;

  /**
   * Validate and create validation.
   *
   * @param email the email
   * @return the validation
   */
  public static Validation<ValidationError, Email> validateAndCreate(String email) {
    //this is a business validation relative only to Email
    //it is guaranteed by application layer that the input argument has already a valid email format
    return email.endsWith("@epo.org") ? Validation.valid(new Email(email))
        : Validation.invalid(new InvalidEmail(email));
  }
}
