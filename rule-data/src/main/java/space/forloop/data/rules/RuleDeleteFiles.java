/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Data;
import space.forloop.data.dto.RuleDto;

@Data
public class RuleDeleteFiles {

  final RuleEnum type = RuleEnum.DELETE_FILES;

  String id;

  boolean enabled;

  String name;

  String sourceDirectory;

  long lessThanThreshold;

  long greaterThanThreshold;

  public RuleDeleteFiles(final RuleDto ruleDto) {
    this.id = ruleDto.getId();
    this.enabled = ruleDto.isEnabled();
    this.name = ruleDto.getName();
    this.sourceDirectory = ruleDto.getSourceDirectory();
    this.lessThanThreshold = ruleDto.getLessThanThreshold();
    this.greaterThanThreshold = ruleDto.getGreaterThanThreshold();
  }
}
