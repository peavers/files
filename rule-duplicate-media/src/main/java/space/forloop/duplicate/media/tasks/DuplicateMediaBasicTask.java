/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

import com.google.common.collect.Iterables;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.services.ScanService;
import space.forloop.data.domain.Duplicate;
import space.forloop.data.domain.File;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleDuplicateMediaBasic;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class DuplicateMediaBasicTask {

  private final RootRepository rootRepository;

  private final ScanService scanService;

  @SneakyThrows
  private static void deleteAllButLastAccess(final Duplicate duplicate) {
    duplicate.getDuplicates().sort(Comparator.comparing(File::getLastAccessTime).reversed());

    for (final File file : Iterables.skip(duplicate.getDuplicates(), 1)) {
      Files.deleteIfExists(Path.of(file.getPath()));
    }
  }

  @Scheduled(fixedDelayString = "${files.timer.duplicate-media-basic}")
  public void deleteDuplicatesByFileSize() {

    rootRepository.findRoot().getRuleDuplicateMediaBasic().stream()
        .filter(RuleDuplicateMediaBasic::isEnabled)
        .forEach(
            rule ->
                scanService
                    .findFiles(rule.getSourceDirectory())
                    .collect(Collectors.groupingBy(File::getSize))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().size() > 1)
                    .map(buildDuplicate())
                    .collect(Collectors.toList())
                    .forEach(DuplicateMediaBasicTask::deleteAllButLastAccess));
  }

  private Function<Map.Entry<Long, List<File>>, Duplicate> buildDuplicate() {
    return entry -> Duplicate.builder().id(entry.getKey()).duplicates(entry.getValue()).build();
  }
}
