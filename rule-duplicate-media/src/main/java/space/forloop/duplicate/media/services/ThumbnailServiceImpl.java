/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.FileChannelWrapper;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Service;
import space.forloop.data.domain.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThumbnailServiceImpl implements ThumbnailService {

  public Path create(final File file) {
    try {
      final java.io.File videoFile = new java.io.File(file.getPath());
      final FileChannelWrapper channelWrapper = NIOUtils.readableChannel(videoFile);
      final FrameGrab frameGrab = FrameGrab.createFrameGrab(channelWrapper);
      final Picture picture =
          frameGrab.seekToFramePrecise(7900).getNativeFrame(); // ~5 minutes into the video

      final java.io.File tempFile = java.io.File.createTempFile(file.getName() + "-", ".jpg");
      tempFile.deleteOnExit();

      final BufferedImage resizeThumbnail = resizeThumbnail(AWTUtil.toBufferedImage(picture));
      writeImage(resizeThumbnail, tempFile);

      return tempFile.toPath().toAbsolutePath();
    } catch (final Exception e) {
      log.error("Error {}", e.getMessage());
    }

    return null;
  }

  private void writeImage(final BufferedImage input, final java.io.File output) {

    try {
      ImageIO.write(input, "jpg", output);
    } catch (final IOException e) {
      log.debug("cannot create thumbnail {}", e.getMessage(), e);
    } finally {
      input.flush();
    }
  }

  private BufferedImage resizeThumbnail(final BufferedImage bufferedImage) {

    try {
      return Thumbnails.of(bufferedImage).size(256, 256).outputFormat("jpg").asBufferedImage();
    } catch (final Exception e) {
      log.info("issue resizing thumbnail");
    }

    return null;
  }
}
