package org.hexagonal.reference.domain.specification;

import org.hexagonal.reference.domain.specification.AbstractSpecification;

public class PositiveNumber implements AbstractSpecification<Number> {

  @Override
  public boolean isSatisfiedBy(Number number) {
    boolean result = false;
    //this could be improved by using switch pattern matching but it is a preview feature in java 17
    if (number instanceof Integer value) {
      result = value >= 0;
    } else if (number instanceof Long value) {
      result = value >= 0;
    } else if (number instanceof Float value) {
      result = value >= 0.0;
    } else if (number instanceof Double value) {
      result = value >= 0.0;
    }

    return result;
  }
}
