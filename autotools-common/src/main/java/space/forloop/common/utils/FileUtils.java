/* Licensed under Apache-2.0 */
package space.forloop.common.utils;

import com.google.common.net.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@UtilityClass
public class FileUtils {

  public boolean isMediaFile(final Path path) {
    final String contentType = probeContentType(path);

    //noinspection UnstableApiUsage
    return StringUtils.isNotEmpty(contentType)
        && MediaType.parse(contentType).is(MediaType.ANY_VIDEO_TYPE);
  }

  private String probeContentType(final Path path) {
    try {
      return Files.probeContentType(path);
    } catch (final IOException e) {
      log.error("Unable to probe {} {}", path, e.getMessage());
      return null;
    }
  }
}
