/* Licensed under Apache-2.0 */
package space.forloop.files.services;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.files.domain.Profile;
import space.forloop.files.domain.RootData;
import space.forloop.files.exceptions.NotFound;
import space.forloop.files.repositories.RootRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final RootRepository rootRepository;

  /** True if the ID of the item matches */
  private static Predicate<Profile> byId(final String id) {
    return item -> item.getId().equals(id);
  }

  @Override
  public Flux<Profile> findAll() {
    return Flux.fromStream(rootRepository.findRoot().getProfiles().stream());
  }

  @Override
  public Mono<Profile> findById(final String id) throws NotFound {
    return Mono.just(findByIdOpt(id, rootRepository.findRoot()).orElseThrow(NotFound::new));
  }

  @Override
  public Mono<Profile> save(final Profile profile) {
    final RootData root = rootRepository.findRoot();

    final Profile existingProfile = findByIdOpt(profile.getId(), root).orElse(null);

    if (existingProfile == null) {
      profile.setId(UUID.randomUUID().toString());
    } else {
      root.getProfiles().removeIf(byId(profile.getId()));
    }

    root.getProfiles().add(profile);

    rootRepository.store(root.getProfiles());

    return Mono.just(profile);
  }

  /** Return the first item with a matching ID as the ID passed in. */
  private Optional<Profile> findByIdOpt(final String id, final RootData root) {
    return root.getProfiles().stream().filter(byId(id)).findFirst();
  }
}
