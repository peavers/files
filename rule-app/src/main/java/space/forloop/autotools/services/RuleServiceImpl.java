/* Licensed under Apache-2.0 */
package space.forloop.autotools.services;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleCopyMediaFiles;
import space.forloop.data.rules.RuleDeleteEmptyDirectories;
import space.forloop.data.rules.RuleDeleteFiles;
import space.forloop.data.rules.RuleDuplicateMedia;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

  private final RootRepository rootRepository;

  @Override
  public Mono<Object> save(final Object object) {
    rootRepository.store(object);

    return Mono.just(object);
  }

  @Override
  public Mono<RuleCopyMediaFiles> saveRuleCopyMedia(final RuleCopyMediaFiles rule) {

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
  public Mono<RuleDeleteEmptyDirectories> saveRuleDeleteEmptyDirectories(
      final RuleDeleteEmptyDirectories rule) {

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
  public Mono<RuleDeleteFiles> saveRuleDeleteFiles(final RuleDeleteFiles rule) {

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
  public Mono<RuleDuplicateMedia> saveRuleDuplicateMedia(final RuleDuplicateMedia rule) {
    final Set<RuleDuplicateMedia> rules = rootRepository.findRoot().getRuleDuplicateMedia();

    final Predicate<RuleDuplicateMedia> predicate =
        duplicateMedia -> duplicateMedia.getId().equals(rule.getId());
    final Optional<RuleDuplicateMedia> optionalRule = rules.stream().filter(predicate).findFirst();

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
  public Mono<Void> deleteRuleCopyMedia(final RuleCopyMediaFiles rule) {
    findAllRuleCopyMedia().removeIf(r -> r.getId().equals(rule.getId()));
    save(findAllRuleCopyMedia());

    return Mono.empty();
  }

  @Override
  public Mono<Void> deleteRuleDeleteEmptyDirectories(final RuleDeleteEmptyDirectories rule) {
    findAllRuleDeleteEmptyDirectories().removeIf(r -> r.getId().equals(rule.getId()));
    save(findAllRuleDeleteEmptyDirectories());

    return Mono.empty();
  }

  @Override
  public Mono<Void> deleteRuleDeleteFiles(final RuleDeleteFiles rule) {
    findAllRuleDeleteFiles().removeIf(r -> r.getId().equals(rule.getId()));
    save(findAllRuleDeleteFiles());

    return Mono.empty();
  }

  @Override
  public Set<RuleCopyMediaFiles> findAllRuleCopyMedia() {
    return rootRepository.findRoot().getRuleCopyMediaFiles();
  }

  @Override
  public Set<RuleDeleteEmptyDirectories> findAllRuleDeleteEmptyDirectories() {
    return rootRepository.findRoot().getRuleDeleteEmptyDirectories();
  }

  @Override
  public Set<RuleDeleteFiles> findAllRuleDeleteFiles() {
    return rootRepository.findRoot().getRuleDeleteFiles();
  }

  @Override
  public Set<RuleDuplicateMedia> findAllRulesDuplicateMedia() {
    return rootRepository.findRoot().getRuleDuplicateMedia();
  }
}
