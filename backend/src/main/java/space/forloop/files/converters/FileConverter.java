/* Licensed under Apache-2.0 */
package space.forloop.files.converters;

import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import space.forloop.files.domain.File;

@Slf4j
@Component
public class FileConverter extends BaseConverter {

  public File convert(final Path path) {

    final java.io.File file = path.toFile();

    return File.builder()
        .id(getId(file))
        .name(getName(file))
        .extension(getExtension(file))
        .path(getPath(file))
        .size(getSize(file))
        .createdTime(getCreationTime(file))
        .lastAccessTime(getLastAccessTime(file))
        .lastModifiedTime(getModificationTime(file))
        .build();
  }

  private String getExtension(final java.io.File file) {

    return FilenameUtils.getExtension(file.getName());
  }
}
