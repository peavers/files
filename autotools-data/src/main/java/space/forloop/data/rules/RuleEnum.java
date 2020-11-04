/* Licensed under Apache-2.0 */
package space.forloop.data.rules;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum RuleEnum {
  COPY_MEDIA_FILES("RuleCopyMediaFiles"),
  DELETE_FILES("RuleDeleteFiles"),
  DUPLICATE_MEDIA_ADVANCE("RuleDuplicateMediaAdvance"),
  DUPLICATE_MEDIA_BASIC("RuleDuplicateMediaBasic"),
  DELETE_EMPTY_DIRECTORIES("RuleDeleteEmptyDirectories");

  private final String type;

  private static final Map<String, RuleEnum> ENUM_MAP;

  RuleEnum(final String type) {
    this.type = type;
  }

  static {
    final Map<String, RuleEnum> map =
        Arrays.stream(RuleEnum.values())
            .collect(
                Collectors.toMap(
                    RuleEnum::getType, instance -> instance, (a, b) -> b, ConcurrentHashMap::new));

    ENUM_MAP = Collections.unmodifiableMap(map);
  }

  public static RuleEnum get(final String type) {
    return ENUM_MAP.get(type);
  }
}
