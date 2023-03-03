package org.hexagonal.reference.domain.factory;

import io.vavr.control.Either;
import org.hexagonal.reference.domain.model.User;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * The interface User factory.
 */
public interface UserFactory {


   /**
    * Create user either.
    *
    * @param nameValue  the name value
    * @param emailValue the email value
    * @param ageValue   the age value
    * @param id         the id
    * @return the either
    */
   Either<Error, User> createUser(String nameValue, String emailValue,
       Integer ageValue, Long id);
}
