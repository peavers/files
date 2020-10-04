/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Data;
import space.forloop.data.dto.RuleDto;

@Data
public class RuleCopyMediaFiles {

  final RuleEnum type = RuleEnum.COPY_MEDIA_FILES;

  String id;

  boolean enabled;

  String name;

  String sourceDirectory;

  String targetDirectory;

  public RuleCopyMediaFiles(final RuleDto ruleDto) {
    this.id = ruleDto.getId();
    this.enabled = ruleDto.isEnabled();
    this.name = ruleDto.getName();
    this.sourceDirectory = ruleDto.getSourceDirectory();
    this.targetDirectory = ruleDto.getTargetDirectory();
  }
}
