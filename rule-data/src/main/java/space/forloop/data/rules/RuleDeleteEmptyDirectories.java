/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Data;
import space.forloop.data.dto.RuleDto;

@Data
public class RuleDeleteEmptyDirectories {

  final RuleEnum type = RuleEnum.DELETE_EMPTY_DIRECTORIES;

  String id;

  boolean enabled;

  String name;

  private String sourceDirectory;

  public RuleDeleteEmptyDirectories(final RuleDto ruleDto) {
    this.id = ruleDto.getId();
    this.enabled = ruleDto.isEnabled();
    this.name = ruleDto.getName();
    this.sourceDirectory = ruleDto.getSourceDirectory();
  }
}
