package org.hexagonal.reference.infrastructure.bus.query;

import lombok.RequiredArgsConstructor;
import org.hexagonal.reference.application.bus.query.Query;
import org.hexagonal.reference.application.bus.query.QueryBus;
import org.hexagonal.reference.application.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

/**
 * The type Spring query bus.
 * @author joseluis.anton
 */
@Component
@RequiredArgsConstructor
public class SpringQueryBus implements QueryBus {

  private final QueryHandlerRegistry querydHandlerRegistry;

  /**
   * {@inheritDoc}
   */
  @Override
  public <R, Q extends Query> R execute(Q query) {
    return ((QueryHandler<R, Q>) querydHandlerRegistry.get(query.getClass())).handle(query);
  }
}
