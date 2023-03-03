package org.hexagonal.reference.domain.model.error;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The type Validation error.
 */
public sealed interface ValidationError  {

  /**
   * The type Invalid age.
   */
  record InvalidAge(Integer age) implements ValidationError {}

  /**
   * The type Invalid email.
   */
  record InvalidEmail(String email) implements ValidationError {}

  /**
   * The type Invalid name.
   */
  record InvalidName(String name) implements ValidationError {}

  /**
   * The type Invalid id.
   */
  record InvalidId(Long id)implements ValidationError{}
}
