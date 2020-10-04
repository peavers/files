/* Licensed under Apache-2.0 */
package space.forloop.autotools.services;

import java.util.Set;
import reactor.core.publisher.Mono;
import space.forloop.data.rules.RuleCopyMediaFiles;
import space.forloop.data.rules.RuleDeleteEmptyDirectories;
import space.forloop.data.rules.RuleDeleteFiles;

public interface RuleService {

  Mono<RuleCopyMediaFiles> saveRuleCopyMedia(RuleCopyMediaFiles rule);

  Mono<RuleDeleteEmptyDirectories> saveRuleDeleteEmptyDirectories(RuleDeleteEmptyDirectories rule);

  Mono<RuleDeleteFiles> saveRuleDeleteFiles(RuleDeleteFiles rule);

  Set<RuleCopyMediaFiles> findAllRuleCopyMedia();

  Set<RuleDeleteEmptyDirectories> findAllRuleDeleteEmptyDirectories();

  Set<RuleDeleteFiles> findAllRuleDeleteFiles();
}
