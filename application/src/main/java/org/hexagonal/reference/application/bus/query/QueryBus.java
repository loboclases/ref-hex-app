package org.hexagonal.reference.application.bus.query;

/**
 * Query bus is able to execute queries by passing the query object to its handler.
 */
public interface QueryBus {

  /**
   * Executes the query passed as parameter.
   *
   * @param <R>   the type of returned value
   * @param <Q>   the type of query parameter
   * @param query the query object
   * @return the returned value provided by the query handler in charge to handle the query Q
   */
  <R,Q extends Query> R execute(Q query);
}
