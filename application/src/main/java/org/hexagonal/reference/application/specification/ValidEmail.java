package org.hexagonal.reference.application.specification;

import org.apache.commons.validator.routines.EmailValidator;
import org.hexagonal.reference.domain.specification.AbstractSpecification;

/**
 * The type Valid email.
 */
public class ValidEmail implements AbstractSpecification<String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(String emailAddress) {
    return EmailValidator.getInstance()
        .isValid(emailAddress);
  }
}
