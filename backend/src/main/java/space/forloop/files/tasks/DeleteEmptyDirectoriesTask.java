/* Licensed under Apache-2.0 */
package space.forloop.files.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import space.forloop.files.domain.Directory;
import space.forloop.files.domain.LogItem;
import space.forloop.files.domain.Profile;
import space.forloop.files.dto.DirectoryDto;
import space.forloop.files.repositories.RootRepository;
import space.forloop.files.services.ScanService;
import space.forloop.files.utils.DeleteUtils;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class DeleteEmptyDirectoriesTask {

  private static final int ZERO = 0;

  private final ScanService scanService;

  private final RootRepository rootRepository;

  @Scheduled(fixedDelayString = "${files.timer.delete-empty-directories}")
  public void deleteEmptyDirectories() {

    rootRepository.findRoot().getProfiles().stream()
        .filter(Profile::isDeleteEmptyDirectories)
        .forEach(
            profile ->
                scanService
                    .findAllDirectories(profile.getMonitorDirectory())
                    .filter(directory -> directory.getChildren() <= ZERO)
                    .flatMap(DeleteUtils::deleteDirectory)
                    .flatMap(directory -> writeLog(profile, directory))
                    .subscribe());
  }

  private Mono<Void> writeLog(final Profile profile, final Directory directory) {

    profile
        .getLogItems()
        .add(
            LogItem.builder()
                .object(new DirectoryDto(directory))
                .message("deleteEmptyDirectories")
                .build());

    rootRepository.store(profile.getLogItems());

    return Mono.empty();
  }
}
