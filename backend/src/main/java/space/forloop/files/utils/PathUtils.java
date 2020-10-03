/* Licensed under Apache-2.0 */
package space.forloop.files.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PathUtils {

  public boolean isReadable(final Path path) {
    return Files.isReadable(path);
  }

  public boolean isDirectory(final Path path) {
    return Files.isDirectory(path);
  }

  public boolean isNotDirectory(final Path path) {
    return !Files.isDirectory(path);
  }
}
