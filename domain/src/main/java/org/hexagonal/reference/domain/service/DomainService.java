
package org.hexagonal.reference.domain.service;

import lombok.AllArgsConstructor;


/**
 * The type User service.
 */
@AllArgsConstructor
public class DomainService {
  //A domain service should only have business logic, hence, it is not correct to have ouput ports dependencies
  //If we consider a rich domain model, a Domain Service will take care of the logic that cannot be placed inside any entity of the model,
  //for instance, logic involving two or more entities

}
