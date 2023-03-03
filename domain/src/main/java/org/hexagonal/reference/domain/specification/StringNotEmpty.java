package org.hexagonal.reference.domain.specification;

/**
 * The type String not empty.
 */
public class StringNotEmpty implements AbstractSpecification<String>{

  /**
   *
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(String s) {
    return null!=s && !s.isEmpty();
  }
}
