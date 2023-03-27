package org.hexagonal.reference.domain.model.error;

import java.util.List;

/**
 * The interface  error.
 */
public sealed interface Error permits Error.BusinessError,
    Error.ValidationErrors, Error.TechnicalError {

  /**
   * The interface Business error.
   */
  sealed interface BusinessError extends Error {

    /**
     * The type User not found.
     */
    record UserNotFound(String msg) implements BusinessError{}
  }

  /**
   * The type Validation errors.
   */
  record ValidationErrors(List<ValidationError> errors) implements Error {
  }

  /**
   * The type Technical error.
   */
  record TechnicalError(String msg, String origin, Throwable cause) implements Error {
  }
}

