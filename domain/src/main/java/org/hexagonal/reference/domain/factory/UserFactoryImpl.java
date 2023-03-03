package org.hexagonal.reference.domain.factory;

import io.vavr.control.Either;
import io.vavr.control.Validation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hexagonal.reference.domain.model.User;
import org.hexagonal.reference.domain.model.error.Error;
import org.hexagonal.reference.domain.model.error.Error.ValidationErrors;
import org.hexagonal.reference.domain.model.error.ValidationError;
import org.hexagonal.reference.domain.model.vo.Age;
import org.hexagonal.reference.domain.model.vo.Email;
import org.hexagonal.reference.domain.model.vo.Id;

/**
 * The type User factory.
 */
public class UserFactoryImpl implements UserFactory{

  @Override
  public Either<Error, User> createUser(String nameValue, String emailValue,
      Integer ageValue, Long idValue) {
    Validation<ValidationError, Age> age = Age.validateAndCreate(ageValue);
    Validation<ValidationError, Email> email=Email.validateAndCreate(emailValue);
    Validation<ValidationError, Id> id= Id.validateAndCreate(idValue);
    Either<Error, User> result=null;
    List<ValidationError> errors=new ArrayList<>();
    if(!age.isValid()){
      errors.add(age.getError());
    }
    if(!email.isValid()){
      errors.add(email.getError());
    }
    if(!id.isValid()){
      errors.add(id.getError());
    }
    if(errors.size()>0){
      result=Either.left(new ValidationErrors(errors));
    }else{
      Validation<ValidationError,User> user=User.validateThenCreate(nameValue,age.get(),email.get(),id.get());
      result=user.toEither().mapLeft(error->new ValidationErrors(Arrays.asList(error)));
    }
    return result;
  }
}
