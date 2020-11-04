/* Licensed under Apache-2.0 */
package space.forloop.autotools.services;

import java.util.Set;
import reactor.core.publisher.Mono;
import space.forloop.data.rules.*;

public interface RuleService {

  Mono<Object> save(Object object);

  Mono<RuleCopyMediaFiles> saveRuleCopyMedia(RuleCopyMediaFiles rule);

  Mono<RuleDeleteEmptyDirectories> saveRuleDeleteEmptyDirectories(RuleDeleteEmptyDirectories rule);

  Mono<RuleDeleteFiles> saveRuleDeleteFiles(RuleDeleteFiles rule);

  Mono<RuleDuplicateMediaAdvance> saveRuleDuplicateMediaAdvance(RuleDuplicateMediaAdvance rule);

  Mono<RuleDuplicateMediaBasic> saveRuleDuplicateMediaBasic(RuleDuplicateMediaBasic rule);

  Mono<Void> deleteRuleCopyMedia(RuleCopyMediaFiles rule);

  Mono<Void> deleteRuleDeleteEmptyDirectories(RuleDeleteEmptyDirectories rule);

  Mono<Void> deleteRuleDeleteFiles(RuleDeleteFiles rule);

  Mono<Void> deleteRuleDuplicateMediaAdvance(RuleDuplicateMediaAdvance rule);

  Mono<Void> deleteRuleDuplicateMediaBasic(RuleDuplicateMediaBasic rule);

  Set<RuleCopyMediaFiles> findAllRuleCopyMedia();

  Set<RuleDeleteEmptyDirectories> findAllRuleDeleteEmptyDirectories();

  Set<RuleDeleteFiles> findAllRuleDeleteFiles();

  Set<RuleDuplicateMediaAdvance> findAllRulesDuplicateMediaAdvance();

  Set<RuleDuplicateMediaBasic> findAllRulesDuplicateMediaBasic();
}
