/* Licensed under Apache-2.0 */
package space.forloop.delete.files.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.utils.DeleteUtils;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleDeleteFiles;
import space.forloop.delete.files.services.ScanService;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class DeleteFilesTask {

  private final ScanService scanService;

  private final RootRepository rootRepository;

  @Scheduled(fixedDelayString = "${files.timer.delete-small-files}")
  public void deleteSmallFiles() {

    rootRepository.findRoot().getRuleDeleteFiles().stream()
        .filter(RuleDeleteFiles::isEnabled)
        .forEach(
            rule ->
                scanService
                    .findAllFiles(rule.getSourceDirectory())
                    .filter(file -> file.getSize() <= rule.getLessThanThreshold())
                    .flatMap(DeleteUtils::deleteFile)
                    .subscribe());
  }
}
