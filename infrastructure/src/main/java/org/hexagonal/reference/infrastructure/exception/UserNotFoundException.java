package org.hexagonal.reference.infrastructure.exception;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Instantiates a new User not found exception.
   *
   * @param message the message
   */
  public UserNotFoundException(String message) {
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);
  }

  public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public UserNotFoundException() {
  }
}
