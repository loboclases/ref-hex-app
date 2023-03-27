package org.hexagonal.reference.infrastructure.bus.query;

import java.util.HashMap;
import java.util.Map;
import org.hexagonal.reference.application.bus.query.Query;
import org.hexagonal.reference.application.bus.query.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

/**
 * The type Registry holds the mapping between the queries and their handler.
 *
 * @author joseluis.anton
 */
public class QueryHandlerRegistry {
  private Map<Class<? extends Query>, QueryProvider> providerMap = new HashMap<>();

  /**
   * Instantiates a new Registry.
   *
   * @param applicationContext the application context
   */
  public QueryHandlerRegistry(ApplicationContext applicationContext) {
    String[] names = applicationContext.getBeanNamesForType(QueryHandler.class);
    for (String name : names) {
      register(applicationContext, name);
    }
  }

  private void register( ApplicationContext applicationContext, String name ){
    Class<QueryHandler<?,?>> handlerClass = (Class<QueryHandler<?,?>>) applicationContext.getType(name);
    Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, QueryHandler.class);
    Class<? extends Query> queryType = (Class<? extends Query>) generics[1];
    providerMap.put(queryType, new QueryProvider(applicationContext, handlerClass));
  }

  /**
   * Get query handler.
   *
   * @param <R>        the type parameter
   * @param <C>        the type parameter
   * @param queryClass the query class
   * @return the query handler
   */
  @SuppressWarnings("unchecked")
  <R, C extends Query> QueryHandler<R,C> get(Class<C> queryClass) {
    return providerMap.get(queryClass).get();
  }

}
