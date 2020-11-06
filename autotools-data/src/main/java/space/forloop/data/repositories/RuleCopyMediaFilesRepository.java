/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.data.rules.RuleCopyMediaFiles;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleCopyMediaFilesRepository implements RuleRepository<RuleCopyMediaFiles> {

  private final RootRepository rootRepository;

  @Override
  public Stream<RuleCopyMediaFiles> findAll() {
    return rootRepository.findRoot().getRuleCopyMediaFiles().parallelStream();
  }

  @Override
  public Flux<RuleCopyMediaFiles> findAllFlux() {
    return Flux.fromIterable(rootRepository.findRoot().getRuleCopyMediaFiles());
  }

  @Override
  public Mono<RuleCopyMediaFiles> save(final RuleCopyMediaFiles rule) {
    final Set<RuleCopyMediaFiles> rules = rootRepository.findRoot().getRuleCopyMediaFiles();
    final Predicate<RuleCopyMediaFiles> predicate =
        ruleCopyMedia -> ruleCopyMedia.getId().equals(rule.getId());
    final Optional<RuleCopyMediaFiles> optionalRule = rules.stream().filter(predicate).findFirst();

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
  public Mono<Void> delete(final RuleCopyMediaFiles rule) {
    final List<RuleCopyMediaFiles> rules =
        findAll().filter(r -> r.getId().equals(rule.getId())).collect(Collectors.toList());

    rootRepository.store(rules);

    return Mono.empty();
  }
}
