/* Licensed under Apache-2.0 */
package space.forloop.directories.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.services.ScanService;
import space.forloop.common.utils.DeleteUtils;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleDeleteEmptyDirectories;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteEmptyDirectoriesTask {

  private static final int ZERO = 0;

  private final ScanService scanService;

  private final RootRepository rootRepository;

  @Scheduled(fixedDelayString = "${files.timer.delete-empty-directories}")
  public void deleteEmptyDirectories() {

    log.info("Running: {}", this.getClass().getName());

    rootRepository.findRoot().getRuleDeleteEmptyDirectories().stream()
        .filter(RuleDeleteEmptyDirectories::isEnabled)
        .forEach(
            rule ->
                scanService
                    .findDirectoriesFlux(rule.getSourceDirectory())
                    .filter(directory -> directory.getChildren() <= ZERO)
                    .flatMap(DeleteUtils::deleteDirectory)
                    .subscribe());
  }
}
