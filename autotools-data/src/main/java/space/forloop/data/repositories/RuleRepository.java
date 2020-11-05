/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RuleRepository<T> {

  Stream<T> findAll();

  Flux<T> findAllFlux();

  Mono<T> save(T rule);

  Mono<Void> delete(T rule);
}
