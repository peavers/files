/* Licensed under Apache-2.0 */
package space.forloop.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import space.forloop.data.rules.RuleCopyMediaFiles;
import space.forloop.data.rules.RuleDeleteEmptyDirectories;
import space.forloop.data.rules.RuleDeleteFiles;
import space.forloop.data.rules.RuleEnum;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RuleDto {

  private String type;

  private String id;

  private String name;

  private boolean enabled;

  private String sourceDirectory;

  private String targetDirectory;

  private long lessThanThreshold;

  private long greaterThanThreshold;

  public RuleDto(final RuleCopyMediaFiles copyMediaFiles) {
    this.type = RuleEnum.COPY_MEDIA_FILES.getType();
    this.id = copyMediaFiles.getId();
    this.name = copyMediaFiles.getName();
    this.enabled = copyMediaFiles.isEnabled();
    this.sourceDirectory = copyMediaFiles.getSourceDirectory();
    this.targetDirectory = copyMediaFiles.getTargetDirectory();
  }

  public RuleDto(final RuleDeleteEmptyDirectories deleteEmptyDirectories) {
    this.type = RuleEnum.DELETE_EMPTY_DIRECTORIES.getType();
    this.id = deleteEmptyDirectories.getId();
    this.name = deleteEmptyDirectories.getName();
    this.enabled = deleteEmptyDirectories.isEnabled();
    this.sourceDirectory = deleteEmptyDirectories.getSourceDirectory();
  }

  public RuleDto(final RuleDeleteFiles deleteFiles) {
    this.type = RuleEnum.DELETE_EMPTY_DIRECTORIES.getType();
    this.id = deleteFiles.getId();
    this.name = deleteFiles.getName();
    this.enabled = deleteFiles.isEnabled();
    this.sourceDirectory = deleteFiles.getSourceDirectory();
    this.lessThanThreshold = deleteFiles.getLessThanThreshold();
    this.greaterThanThreshold = deleteFiles.getGreaterThanThreshold();
  }
}
