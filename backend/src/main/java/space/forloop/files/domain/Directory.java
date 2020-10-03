/* Licensed under Apache-2.0 */
package space.forloop.files.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Directory {

  String id;

  String name;

  String path;

  long size;

  long children;

  LocalDateTime createdTime;

  LocalDateTime lastAccessTime;

  LocalDateTime lastModifiedTime;

  LocalDateTime lastWatched;
}
