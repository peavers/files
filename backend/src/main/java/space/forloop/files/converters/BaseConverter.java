/* Licensed under Apache-2.0 */
package space.forloop.files.converters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import org.apache.commons.io.FilenameUtils;

class BaseConverter {

  protected long getSize(final File file) {

    long size;

    try {
      final Path path = Paths.get(file.getAbsolutePath());
      final BasicFileAttributes fileAttributes =
          Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
      size = fileAttributes.size();
    } catch (final Exception e) {
      size = -1L;
    }

    return size;
  }

  protected String getName(final File file) {

    return FilenameUtils.removeExtension(file.getName());
  }

  protected String getPath(final File file) {

    try {
      return file.getCanonicalPath().trim();
    } catch (final IOException e) {
      return null;
    }
  }

  protected LocalDateTime getCreationTime(final File file) {

    LocalDateTime time;
    try {
      final Path path = Paths.get(file.getAbsolutePath());
      final BasicFileAttributes fileAttributes =
          Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
      time =
          LocalDateTime.ofInstant(
              fileAttributes.creationTime().toInstant(), ZoneId.systemDefault());
    } catch (final Exception e) {
      time = null;
    }
    return time;
  }

  protected LocalDateTime getModificationTime(final File file) {

    LocalDateTime time;
    try {
      final Path path = Paths.get(file.getAbsolutePath());
      final BasicFileAttributes fileAttributes =
          Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
      time =
          LocalDateTime.ofInstant(
              fileAttributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
    } catch (final Exception e) {
      time = null;
    }
    return time;
  }

  protected LocalDateTime getLastAccessTime(final File file) {

    LocalDateTime time;
    try {
      final Path path = Paths.get(file.getAbsolutePath());
      final BasicFileAttributes fileAttributes =
          Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
      time =
          LocalDateTime.ofInstant(
              fileAttributes.lastAccessTime().toInstant(), ZoneId.systemDefault());
    } catch (final Exception e) {
      time = null;
    }
    return time;
  }

  protected String getId(final File file) {

    try {
      return Base64.getEncoder().encodeToString(file.getCanonicalPath().getBytes());
    } catch (final IOException e) {
      return null;
    }
  }
}
