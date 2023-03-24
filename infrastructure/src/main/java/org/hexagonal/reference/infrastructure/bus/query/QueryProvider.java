package org.hexagonal.reference.infrastructure.bus.query;

import io.vavr.Lazy;
import org.hexagonal.reference.application.bus.query.QueryHandler;
import org.springframework.context.ApplicationContext;

/**
 * The type Query provider.
 *
 * @param <H> the type of the Query Handler
 * @author joseluis.anton
 */
public class QueryProvider<H extends QueryHandler<?, ?>> {
  private final ApplicationContext applicationContext;
  private final Lazy<H> queryProvider;

  private Class<H> type;
  /**
   * Instantiates a new Query provider.
   *
   * @param applicationContext the application context
   * @param type               the type
   */
  QueryProvider(ApplicationContext applicationContext, Class<H> type) {
    this.applicationContext = applicationContext;
    this.type=type;
    queryProvider=Lazy.of(()->applicationContext.getBean(type));
  }

  /**
   * Get the H typed query handler.
   *
   * @return the h
   */
  public H get() {
    return queryProvider.get();
  }
}
