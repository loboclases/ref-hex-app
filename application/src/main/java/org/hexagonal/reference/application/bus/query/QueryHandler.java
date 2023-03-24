package org.hexagonal.reference.application.bus.query;

/**
 * A handler for a {@link Query}
 *
 * @param <R> the type parameter
 * @param <Q> the type parameter
 * @author joseluis.anton
 */
public interface QueryHandler<R, Q extends Query>{

  /**
   * Handles the query.
   *
   * @param query the query to handler
   * @return returned value according to {@link Query}
   */
  R handle(Q query);

}
