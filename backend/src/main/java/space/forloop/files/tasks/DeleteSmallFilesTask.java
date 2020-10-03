/* Licensed under Apache-2.0 */
package space.forloop.files.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import space.forloop.files.domain.File;
import space.forloop.files.domain.LogItem;
import space.forloop.files.domain.Profile;
import space.forloop.files.dto.FileDto;
import space.forloop.files.repositories.RootRepository;
import space.forloop.files.services.ScanService;
import space.forloop.files.utils.DeleteUtils;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class DeleteSmallFilesTask {

  private final ScanService scanService;

  private final RootRepository rootRepository;

  @Scheduled(fixedDelayString = "${files.timer.delete-small-files}")
  public void deleteSmallFiles() {

    rootRepository.findRoot().getProfiles().stream()
        .filter(Profile::isDeleteSmallFiles)
        .forEach(
            profile ->
                scanService
                    .findAllFiles(profile.getMonitorDirectory())
                    .filter(file -> file.getSize() <= profile.getDeleteThreshold())
                    .flatMap(DeleteUtils::deleteFile)
                    .flatMap(file -> writeLog(profile, file))
                    .subscribe());
  }

  private Mono<Void> writeLog(final Profile profile, final File file) {

    profile
        .getLogItems()
        .add(LogItem.builder().object(new FileDto(file)).message("deleteSmallFiles").build());

    rootRepository.store(profile.getLogItems());

    return Mono.empty();
  }
}
