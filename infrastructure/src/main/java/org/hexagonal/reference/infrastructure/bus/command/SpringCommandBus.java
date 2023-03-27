package org.hexagonal.reference.infrastructure.bus.command;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.application.bus.command.Command;
import org.hexagonal.reference.application.bus.command.CommandBus;
import org.hexagonal.reference.application.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.hexagonal.reference.domain.model.error.Error;
/**
 * The type Spring command bus.
 * @author joseluis.anton
 */
@Component
@RequiredArgsConstructor
public class SpringCommandBus implements CommandBus{

    private final CommandHandlerRegistry commandHandlerRegistry;

    /**
     * {@inheritDoc}
     */
    @Override
    public <C extends Command> Either<Error,Void> execute(C command) {
      return ((CommandHandler<C>) commandHandlerRegistry.get(command.getClass())).handle(command);
    }
}
