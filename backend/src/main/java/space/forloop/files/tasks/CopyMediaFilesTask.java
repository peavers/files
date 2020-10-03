/* Licensed under Apache-2.0 */
package space.forloop.files.tasks;

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
import reactor.core.publisher.Mono;
import space.forloop.files.domain.LogItem;
import space.forloop.files.domain.Profile;
import space.forloop.files.dto.FileDto;
import space.forloop.files.repositories.RootRepository;
import space.forloop.files.services.ScanService;
import space.forloop.files.utils.FileUtils;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class CopyMediaFilesTask {

  private final ScanService scanService;

  private final RootRepository rootRepository;

  @Scheduled(fixedDelayString = "${files.timer.copy-files}")
  public void copyMediaFiles() {

    rootRepository.findRoot().getProfiles().stream()
        .filter(Profile::isCopyMediaFiles)
        .forEach(
            profile ->
                scanService
                    .findAllFiles(profile.getMonitorDirectory())
                    .filter(file -> fileAlreadyExist(file.getPath(), profile.getCopyToDirectory()))
                    .filter(file -> FileUtils.isMediaFile(Path.of(file.getPath())))
                    .doOnNext(file -> copyFile(file.getPath(), profile.getCopyToDirectory()))
                    .flatMap(file -> writeLog(profile, file))
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

  private Mono<Void> writeLog(final Profile profile, final space.forloop.files.domain.File file) {

    profile
        .getLogItems()
        .add(LogItem.builder().object(new FileDto(file)).message("copyMediaFiles").build());

    rootRepository.store(profile.getLogItems());

    return Mono.empty();
  }
}
