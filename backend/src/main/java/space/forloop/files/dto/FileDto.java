/* Licensed under Apache-2.0 */
package space.forloop.files.dto;

import lombok.Value;
import space.forloop.files.domain.File;

@Value
public class FileDto {

  String name;

  String path;

  String extension;

  long size;

  public FileDto(final File file) {
    this.name = file.getName();
    this.path = file.getPath();
    this.extension = file.getExtension();
    this.size = file.getSize();
  }
}
