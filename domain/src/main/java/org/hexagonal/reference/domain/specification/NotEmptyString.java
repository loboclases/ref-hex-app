package org.hexagonal.reference.domain.specification;

/**
 * The specification Not empty string.
 */
public class NotEmptyString implements AbstractSpecification<String>{

  /**
   *
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(String s) {
    return null!=s && !s.isEmpty();
  }
}
