/* Licensed under Apache-2.0 */
package space.forloop.data.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import space.forloop.data.rules.RuleCopyMediaFiles;
import space.forloop.data.rules.RuleDeleteEmptyDirectories;
import space.forloop.data.rules.RuleDeleteFiles;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RootData {

  @Builder.Default private Set<RuleDeleteFiles> ruleDeleteFiles = new HashSet<>();

  @Builder.Default
  private Set<RuleDeleteEmptyDirectories> ruleDeleteEmptyDirectories = new HashSet<>();

  @Builder.Default private Set<RuleCopyMediaFiles> ruleCopyMediaFiles = new HashSet<>();
}
