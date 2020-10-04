/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RuleDeleteEmptyDirectories {

  final RuleEnum type = RuleEnum.DELETE_EMPTY_DIRECTORIES;

  String id;

  boolean enabled;

  String name;

  private String sourceDirectory;
}
