package org.hexagonal.reference.domain.specification;

/**
 * The type Abstract specification.
 *
 * @param <T> the type parameter
 */
public interface AbstractSpecification<T> {

  /**
   * Verifies whether input T verifies the condition
   *
   * @param t the t
   * @return the boolean
   */
  boolean isSatisfiedBy(T t);

}
