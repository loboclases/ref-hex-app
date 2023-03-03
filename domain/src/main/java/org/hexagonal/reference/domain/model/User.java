package org.hexagonal.reference.domain.model;

import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidName;
import org.hexagonal.reference.domain.model.vo.Age;
import org.hexagonal.reference.domain.model.vo.Email;
import org.hexagonal.reference.domain.model.vo.Id;
import org.hexagonal.reference.domain.specification.StringNotEmpty;

/**
 * The type User.
 *
 * @author joseluis.anton
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)

public class User {

  private final String userName;
  private final Age age;
  private final Email email;
  private final Id id;


  /**
   * Validate then create validation.
   *
   * @param username the username
   * @param age      the age
   * @param email    the email
   * @return the validation
   */
  public static Validation<ValidationError, User> validateThenCreate(
      final String username, final Age age, final Email email, final Id id) {
    return new StringNotEmpty().isSatisfiedBy(username) ? Validation.valid(new User(username, age, email,id))
        : Validation.invalid(
            new InvalidName(username));


  }

  /**
   * Is adult boolean. Business logic
   *
   * @return the boolean
   */
  public boolean isAdult() {
    return this.age.getValue() > 18;
  }


}
