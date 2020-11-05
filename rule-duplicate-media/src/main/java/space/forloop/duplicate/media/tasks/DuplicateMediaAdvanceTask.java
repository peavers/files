/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.services.ScanService;
import space.forloop.data.domain.DuplicateAdvance;
import space.forloop.data.domain.File;
import space.forloop.data.repositories.RuleDuplicateMediaAdvanceRepository;
import space.forloop.data.rules.RuleDuplicateMediaAdvance;
import space.forloop.duplicate.media.services.HashService;
import space.forloop.duplicate.media.services.ThumbnailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DuplicateMediaAdvanceTask {

  private final ExecutorService executor;

  private final ScanService scanService;

  private final ThumbnailService thumbnailService;

  private final HashService hashService;

  private final RuleDuplicateMediaAdvanceRepository repository;

  @Scheduled(fixedDelayString = "${files.timer.duplicate-media-advance}")
  public void deleteDuplicatesByContent() {

    log.info("Running: {}", this.getClass().getName());

    getFilesStream()
        .map(this::buildDuplicate)
        .collect(Collectors.groupingBy(DuplicateAdvance::getHash))
        .values()
        .parallelStream()
        .filter(duplicateAdvances -> duplicateAdvances.size() > 1)
        .forEach(deleteAllButLargestFile());
  }

  private Stream<File> getFilesStream() {

    return repository
        .findAll()
        .filter(RuleDuplicateMediaAdvance::isEnabled)
        .flatMap(this::getFiles)
        .collect(Collectors.toList())
        .parallelStream();
  }

  private Stream<File> getFiles(RuleDuplicateMediaAdvance ruleDuplicateMediaAdvance) {

    return scanService.findFiles(ruleDuplicateMediaAdvance.getSourceDirectory());
  }

  private DuplicateAdvance buildDuplicate(final File file) {

    final Path path = thumbnailService.create(file);
    final long hash = hashService.getPerceptualHash(path.toFile());

    return DuplicateAdvance.builder().path(path).hash(hash).file(file).build();
  }

  private Consumer<List<DuplicateAdvance>> deleteAllButLargestFile() {
    return duplicates ->
        executor.submit(
            () -> {
              duplicates.sort(Comparator.naturalOrder());

              for (final DuplicateAdvance duplicateAdvance : Iterables.skip(duplicates, 1)) {
                FileUtils.deleteQuietly(duplicateAdvance.getPath().toFile());
                log.info("Deleted duplicate: {}", duplicateAdvance.getPath());
              }
            });
  }
}
