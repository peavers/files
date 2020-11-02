/* Licensed under Apache-2.0 */
package space.forloop.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import space.forloop.data.rules.*;

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
    this.type = RuleEnum.DELETE_FILES.getType();
    this.id = deleteFiles.getId();
    this.name = deleteFiles.getName();
    this.enabled = deleteFiles.isEnabled();
    this.sourceDirectory = deleteFiles.getSourceDirectory();
    this.lessThanThreshold = deleteFiles.getLessThanThreshold();
    this.greaterThanThreshold = deleteFiles.getGreaterThanThreshold();
  }

  public RuleDto(final RuleDuplicateMediaAdvance duplicateMedia) {
    this.type = RuleEnum.DUPLICATE_MEDIA_ADVANCE.getType();
    this.id = duplicateMedia.getId();
    this.name = duplicateMedia.getName();
    this.enabled = duplicateMedia.isEnabled();
    this.sourceDirectory = duplicateMedia.getSourceDirectory();
  }

  public RuleDto(final RuleDuplicateMediaBasic duplicateMedia) {
    this.type = RuleEnum.DUPLICATE_MEDIA_BASIC.getType();
    this.id = duplicateMedia.getId();
    this.name = duplicateMedia.getName();
    this.enabled = duplicateMedia.isEnabled();
    this.sourceDirectory = duplicateMedia.getSourceDirectory();
  }
}
