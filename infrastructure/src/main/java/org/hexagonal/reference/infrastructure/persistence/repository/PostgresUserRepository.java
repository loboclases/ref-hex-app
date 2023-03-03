package org.hexagonal.reference.infrastructure.persistence.repository;

import org.hexagonal.reference.infrastructure.persistence.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * The interface Postgres user repository.
 */
public interface PostgresUserRepository extends CrudRepository<UserEntity, Long> {

  /**
   * Find by name user entity.
   *
   * @param name the name
   * @return the user entity
   */
  UserEntity findByName(String name);
}
