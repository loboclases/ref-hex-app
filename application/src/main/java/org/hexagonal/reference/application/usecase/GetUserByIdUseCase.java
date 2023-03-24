package org.hexagonal.reference.application.usecase;

import io.vavr.control.Either;
import org.hexagonal.reference.application.bus.query.QueryHandler;
import org.hexagonal.reference.application.usecase.dto.UserDTO;
import org.hexagonal.reference.application.usecase.query.GetUserByIdQuery;
import org.hexagonal.reference.domain.model.error.Error;

/**
 * The type Get user by id query handler.
 * @author joseluis.anton
 */
public interface GetUserByIdUseCase extends QueryHandler<Either<Error, UserDTO>, GetUserByIdQuery> {

}
