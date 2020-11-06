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
import space.forloop.data.rules.RuleDeleteEmptyDirectories;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleDeleteEmptyDirectoriesRepository
    implements RuleRepository<RuleDeleteEmptyDirectories> {

  private final RootRepository rootRepository;

  @Override
  public Stream<RuleDeleteEmptyDirectories> findAll() {
    return rootRepository.findRoot().getRuleDeleteEmptyDirectories().parallelStream();
  }

  @Override
  public Flux<RuleDeleteEmptyDirectories> findAllFlux() {
    return Flux.fromIterable(rootRepository.findRoot().getRuleDeleteEmptyDirectories());
  }

  @Override
  public Mono<RuleDeleteEmptyDirectories> save(final RuleDeleteEmptyDirectories rule) {
    final Set<RuleDeleteEmptyDirectories> rules =
        rootRepository.findRoot().getRuleDeleteEmptyDirectories();
    final Predicate<RuleDeleteEmptyDirectories> predicate =
        ruleCopyMedia -> ruleCopyMedia.getId().equals(rule.getId());
    final Optional<RuleDeleteEmptyDirectories> optionalRule =
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
  public Mono<Void> delete(final RuleDeleteEmptyDirectories rule) {
    final List<RuleDeleteEmptyDirectories> rules =
        findAll().filter(r -> r.getId().equals(rule.getId())).collect(Collectors.toList());

    rootRepository.store(rules);

    return Mono.empty();
  }
}
