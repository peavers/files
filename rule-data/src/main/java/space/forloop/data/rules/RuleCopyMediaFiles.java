/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RuleCopyMediaFiles {

  final RuleEnum type = RuleEnum.COPY_MEDIA_FILES;

  String id;

  boolean enabled;

  String name;

  String sourceDirectory;

  String targetDirectory;
}
