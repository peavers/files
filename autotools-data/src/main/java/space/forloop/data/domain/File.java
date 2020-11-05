/* Licensed under Apache-2.0 */
package space.forloop.data.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class File {

  String id;

  String name;

  String path;

  String extension;

  long size;

  LocalDateTime createdTime;

  LocalDateTime lastAccessTime;

  LocalDateTime lastModifiedTime;

  LocalDateTime lastWatched;

  public java.io.File toNative() {
    return new java.io.File(this.path);
  }
}
