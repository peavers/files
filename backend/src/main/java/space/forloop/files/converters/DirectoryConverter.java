/* Licensed under Apache-2.0 */
package space.forloop.files.converters;

import java.nio.file.Path;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import space.forloop.files.domain.Directory;

@Slf4j
@Component
public class DirectoryConverter extends BaseConverter {

  public Directory convert(final Path path) {

    final java.io.File file = path.toFile();

    return Directory.builder()
        .id(getId(file))
        .name(getName(file))
        .path(getPath(file))
        .size(getSize(file))
        .children(getChildren(file))
        .createdTime(getCreationTime(file))
        .lastAccessTime(getLastAccessTime(file))
        .lastModifiedTime(getModificationTime(file))
        .build();
  }

  private int getChildren(final java.io.File file) {

    return Objects.requireNonNull(file.listFiles()).length;
  }
}
