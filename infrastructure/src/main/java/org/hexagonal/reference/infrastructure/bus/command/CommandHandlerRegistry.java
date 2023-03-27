package org.hexagonal.reference.infrastructure.bus.command;

import java.util.HashMap;
import java.util.Map;
import org.hexagonal.reference.application.bus.command.Command;
import org.hexagonal.reference.application.bus.command.CommandHandler;
import org.hexagonal.reference.application.bus.query.Query;
import org.hexagonal.reference.application.bus.query.QueryHandler;
import org.hexagonal.reference.infrastructure.bus.query.QueryProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

/**
 * The type Registry holds the mapping between the commands and their handler.
 * @author joseluis.anton
 */
public class CommandHandlerRegistry {
  private Map<Class<? extends Command>, CommandProvider> providerMap = new HashMap<>();

  /**
   * Instantiates a new Registry.
   *
   * @param applicationContext the application context
   */
  public CommandHandlerRegistry(ApplicationContext applicationContext) {
    String[] names = applicationContext.getBeanNamesForType(CommandHandler.class);
    for (String name : names) {
      register(applicationContext, name);
    }
  }


  /**
   * Get command handler.
   *
   * @param <C>          the type parameter
   * @param commandClass the command class
   * @return the command handler
   */
  @SuppressWarnings("unchecked")
  <C extends Command> CommandHandler<C> get(Class<C> commandClass) {
    return providerMap.get(commandClass).get();
  }
  private void register( ApplicationContext applicationContext, String name ){
    Class<CommandHandler<?>> handlerClass = (Class<CommandHandler<?>>) applicationContext.getType(name);
    Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
    Class<? extends Command> commandType = (Class<? extends Command>) generics[0];
    providerMap.put(commandType, new CommandProvider(applicationContext, handlerClass));
  }
}
