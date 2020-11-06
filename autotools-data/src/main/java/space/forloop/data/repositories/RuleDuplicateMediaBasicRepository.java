/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.data.rules.RuleDuplicateMediaBasic;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleDuplicateMediaBasicRepository implements RuleRepository<RuleDuplicateMediaBasic> {

  private final RootRepository rootRepository;

  @Override
  public Stream<RuleDuplicateMediaBasic> findAll() {
    return null;
  }

  @Override
  public Flux<RuleDuplicateMediaBasic> findAllFlux() {
    return Flux.fromIterable(rootRepository.findRoot().getRuleDuplicateMediaBasic());
  }

  @Override
  public Mono<RuleDuplicateMediaBasic> save(final RuleDuplicateMediaBasic rule) {
    final Set<RuleDuplicateMediaBasic> rules =
        rootRepository.findRoot().getRuleDuplicateMediaBasic();
    final Predicate<RuleDuplicateMediaBasic> predicate =
        duplicateMedia -> duplicateMedia.getId().equals(rule.getId());
    final Optional<RuleDuplicateMediaBasic> optionalRule =
        rules.stream().filter(predicate).findFirst();

    if (optionalRule.isEmpty()) {
      rule.setId(UUID.randomUUID().toString());
    } else {
      rules.removeIf(predicate);
    }

    rules.add(rule);
    rootRepository.store(rules);

    return Mono.just(rule);
  }

  @Override
  public Mono<Void> delete(final RuleDuplicateMediaBasic rule) {
    final List<RuleDuplicateMediaBasic> rules =
            findAll().filter(r -> r.getId().equals(rule.getId())).collect(Collectors.toList());

    rootRepository.store(rules);

    return Mono.empty();
  }
}
