/* Licensed under Apache-2.0 */
package space.forloop.files.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.files.domain.Profile;
import space.forloop.files.exceptions.NotFound;

public interface ProfileService {

  Flux<Profile> findAll();

  Mono<Profile> findById(String id) throws NotFound;

  Mono<Profile> save(Profile profile);
}
