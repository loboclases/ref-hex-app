package org.hexagonal.reference.infrastructure.adapter.model;

import lombok.Builder;
import lombok.Data;

/**
 * The type User resource.
 */
@Data
@Builder
public class UserResource {

  private String name;
  private Integer age;
  private String email;
  private Long id;

}
