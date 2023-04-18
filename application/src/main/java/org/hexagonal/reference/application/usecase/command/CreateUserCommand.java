package org.hexagonal.reference.application.usecase.command;

import io.vavr.control.Validation;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.application.bus.command.Command;
import org.hexagonal.reference.application.specification.ValidEmail;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.error.Error.ValidationErrors;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidAge;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidEmail;
import org.hexagonal.reference.domain.model.error.ValidationError.InvalidName;
import org.hexagonal.reference.domain.specification.PositiveNumber;
import org.hexagonal.reference.domain.specification.NotEmptyString;

/**
 * The type Create user command.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserCommand implements Command {

  private final String username;
  private final Integer age;
  private final String email;

  /**
   * Validate and create validation.
   *
   * @param username the username
   * @param age      the age
   * @param email    the email
   * @return the validation
   */
  public static Validation<Error, CreateUserCommand> validateAndCreate(
      final String username, final Integer age, final String email) {
    return Validation.combine(isUsernameValid(username), isAgeValid(age), isEmailValid(email))
        .ap((user, userAge, userMail) -> new CreateUserCommand(user, userAge, userMail))
        .bimap(seq -> new ValidationErrors(seq.toJavaList()),
            Function.identity());

  }

  private static Validation<ValidationError, String> isUsernameValid(final String username) {
    return new NotEmptyString().isSatisfiedBy(username) ? Validation.valid(username)
        : Validation.invalid(new InvalidName(username));
  }

  private static Validation<ValidationError, String> isEmailValid(final String email) {
    return new ValidEmail().isSatisfiedBy(email) ? Validation.valid(email)
        : Validation.invalid(new InvalidEmail(email));
  }

  private static Validation<ValidationError, Integer> isAgeValid(final Integer age) {
    return new PositiveNumber().isSatisfiedBy(age) ? Validation.valid(age)
        : Validation.invalid(new InvalidAge(age));
  }

}
