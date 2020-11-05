/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

import com.google.common.collect.Iterables;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.services.ScanService;
import space.forloop.data.domain.Duplicate;
import space.forloop.data.domain.File;
import space.forloop.data.repositories.RuleDuplicateMediaBasicRepository;
import space.forloop.data.rules.RuleDuplicateMediaBasic;

@Slf4j
@Component
@RequiredArgsConstructor
public class DuplicateMediaBasicTask {

  private final ExecutorService executor;

  private final RuleDuplicateMediaBasicRepository repository;

  private final ScanService scanService;

  @Scheduled(fixedDelayString = "${files.timer.duplicate-media-basic}")
  public void deleteDuplicatesByFileSize() {

    log.info("Running: {}", this.getClass().getName());

    getFileStream().collect(Collectors.groupingBy(File::getSize)).entrySet().stream()
        .filter(entry -> entry.getValue().size() > 1)
        .map(buildDuplicate())
        .forEach(deleteAllButLastAccessed());
  }

  private Stream<File> getFileStream() {

    return repository
        .findAll()
        .filter(RuleDuplicateMediaBasic::isEnabled)
        .flatMap(this::getFiles)
        .collect(Collectors.toList())
        .parallelStream();
  }

  private Stream<File> getFiles(RuleDuplicateMediaBasic ruleDuplicateMediaBasic) {

    return scanService.findFiles(ruleDuplicateMediaBasic.getSourceDirectory());
  }

  private Function<Map.Entry<Long, List<File>>, Duplicate> buildDuplicate() {

    return entry -> Duplicate.builder().id(entry.getKey()).duplicates(entry.getValue()).build();
  }

  private Consumer<Duplicate> deleteAllButLastAccessed() {

    return duplicate ->
        executor.submit(
            () -> {
              duplicate
                  .getDuplicates()
                  .sort(Comparator.comparing(File::getLastAccessTime).reversed());

              for (final File file : Iterables.skip(duplicate.getDuplicates(), 1)) {
                FileUtils.deleteQuietly(file.toNative());
                log.info("Deleted duplicate: {}", file.getPath());
              }
            });
  }
}
