/* Licensed under Apache-2.0 */
package space.forloop.files.dto;

import lombok.Value;
import space.forloop.files.domain.Directory;

@Value
public class DirectoryDto {

  String name;

  String path;

  public DirectoryDto(final Directory directory) {
    this.name = directory.getName();
    this.path = directory.getPath();
  }
}
