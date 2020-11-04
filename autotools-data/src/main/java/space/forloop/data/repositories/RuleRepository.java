/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RuleRepository<T> {

  Flux<T> findAll();

  Mono<T> save(T rule);

  Mono<Void> delete(T rule);
}
