package org.hexagonal.reference.application.usecase.dto;

import lombok.Builder;
import lombok.Data;

/**
 * The type User dto.
 */
@Data
@Builder
public class UserDTO {
  private String name;
  private Integer age;
  private String email;
  private Long id;

}
