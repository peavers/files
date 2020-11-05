/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.data.rules.RuleDeleteFiles;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleDeleteFilesRepository implements RuleRepository<RuleDeleteFiles> {

  private final RootRepository rootRepository;

  @Override
  public Stream<RuleDeleteFiles> findAll() {
    return null;
  }

  @Override
  public Flux<RuleDeleteFiles> findAllFlux() {
    return Flux.fromIterable(rootRepository.findRoot().getRuleDeleteFiles());
  }

  @Override
  public Mono<RuleDeleteFiles> save(final RuleDeleteFiles rule) {
    final Set<RuleDeleteFiles> rules = rootRepository.findRoot().getRuleDeleteFiles();
    final Predicate<RuleDeleteFiles> predicate =
        ruleCopyMedia -> ruleCopyMedia.getId().equals(rule.getId());
    final Optional<RuleDeleteFiles> optionalRule = rules.stream().filter(predicate).findFirst();

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
  public Mono<Void> delete(final RuleDeleteFiles rule) {
    final List<RuleDeleteFiles> rules =
        findAllFlux().filter(r -> r.getId().equals(rule.getId())).collectList().block();

    rootRepository.store(rules);

    return Mono.empty();
  }
}
