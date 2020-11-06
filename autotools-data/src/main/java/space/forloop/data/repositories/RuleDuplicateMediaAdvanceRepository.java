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
import space.forloop.data.rules.RuleDuplicateMediaAdvance;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleDuplicateMediaAdvanceRepository
    implements RuleRepository<RuleDuplicateMediaAdvance> {

  private final RootRepository rootRepository;

  @Override
  public Stream<RuleDuplicateMediaAdvance> findAll() {
    return rootRepository.findRoot().getRuleDuplicateMediaAdvance().parallelStream();
  }

  @Override
  public Flux<RuleDuplicateMediaAdvance> findAllFlux() {
    return Flux.fromIterable(rootRepository.findRoot().getRuleDuplicateMediaAdvance());
  }

  @Override
  public Mono<RuleDuplicateMediaAdvance> save(final RuleDuplicateMediaAdvance rule) {
    final Set<RuleDuplicateMediaAdvance> rules =
        rootRepository.findRoot().getRuleDuplicateMediaAdvance();
    final Predicate<RuleDuplicateMediaAdvance> predicate =
        duplicateMedia -> duplicateMedia.getId().equals(rule.getId());
    final Optional<RuleDuplicateMediaAdvance> optionalRule =
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
  public Mono<Void> delete(final RuleDuplicateMediaAdvance rule) {

    final List<RuleDuplicateMediaAdvance> rules =
        findAll().filter(r -> r.getId().equals(rule.getId())).collect(Collectors.toList());

    rootRepository.store(rules);

    return Mono.empty();
  }
}
