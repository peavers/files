/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Data;
import space.forloop.data.dto.RuleDto;

@Data
public class RuleDuplicateMediaBasic {

  final RuleEnum type = RuleEnum.DUPLICATE_MEDIA_BASIC;

  String id;

  boolean enabled;

  String name;

  String sourceDirectory;

  public RuleDuplicateMediaBasic(final RuleDto ruleDto) {
    this.id = ruleDto.getId();
    this.enabled = ruleDto.isEnabled();
    this.name = ruleDto.getName();
    this.sourceDirectory = ruleDto.getSourceDirectory();
  }
}
