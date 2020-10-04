/* Licensed under Apache-2.0 */
package space.forloop.copy.media.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.copy.media.services.ScanService;
import space.forloop.copy.media.utils.FileUtils;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleCopyMediaFiles;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class CopyMediaFilesTask {

  private final ScanService scanService;

  private final RootRepository rootRepository;

  @Scheduled(fixedDelayString = "${files.timer.copy-files}")
  public void copyMediaFiles() {

    rootRepository.findRoot().getRuleCopyMediaFiles().stream()
        .filter(RuleCopyMediaFiles::isEnabled)
        .forEach(
            rule ->
                scanService
                    .findAllFiles(rule.getSourceDirectory())
                    .filter(file -> fileAlreadyExist(file.getPath(), rule.getTargetDirectory()))
                    .filter(file -> FileUtils.isMediaFile(Path.of(file.getPath())))
                    .doOnNext(file -> copyFile(file.getPath(), rule.getTargetDirectory()))
                    .subscribe());
  }

  private boolean fileAlreadyExist(final String source, final String copyFilesTo) {
    final File file = new File(source);
    final File target = new File(copyFilesTo);

    return Arrays.stream(Objects.requireNonNull(target.listFiles()))
        .noneMatch(f -> f.getName().equalsIgnoreCase(file.getName()));
  }

  private void copyFile(final String source, final String copyFilesTo) {
    final File file = new File(source);
    final File target = new File(copyFilesTo);

    try {
      org.apache.commons.io.FileUtils.copyFileToDirectory(file, target);

      log.info("Copied {} to {}", file.getName(), target.getAbsolutePath());
    } catch (final IOException e) {
      log.error("Unable to copy file {}", e.getMessage());
    }
  }
}
