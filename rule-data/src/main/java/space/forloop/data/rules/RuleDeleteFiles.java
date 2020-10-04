/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RuleDeleteFiles {

  final RuleEnum type = RuleEnum.DELETE_FILES;

  String id;

  boolean enabled;

  String name;

  String sourceDirectory;

  long lessThanThreshold = -1L;

  long greaterThanThreshold = -1L;
}
