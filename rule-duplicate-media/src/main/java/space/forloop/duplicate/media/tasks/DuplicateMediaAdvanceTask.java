/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

import com.google.common.collect.Iterables;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.services.ScanService;
import space.forloop.data.domain.DuplicateAdvance;
import space.forloop.data.domain.File;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleDuplicateMediaAdvance;
import space.forloop.duplicate.media.services.HashService;
import space.forloop.duplicate.media.services.ThumbnailService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DuplicateMediaAdvanceTask {

  private final ExecutorService executor;

  private final ScanService scanService;

  private final RootRepository rootRepository;

  private final ThumbnailService thumbnailService;

  private final HashService hashService;

  @Scheduled(fixedDelayString = "${files.timer.duplicate-media-advance}")
  public void deleteDuplicatesByContent() {

    log.info("Running: {}", this.getClass().getName());

    rootRepository.findRoot().getRuleDuplicateMediaAdvance().stream()
        .filter(RuleDuplicateMediaAdvance::isEnabled)
        .forEach(
            rule ->
                scanService
                    .findFiles(rule.getSourceDirectory())
                    .map(this::buildDuplicate)
                    .collect(Collectors.groupingBy(DuplicateAdvance::getHash))
                    .entrySet()
                    .stream()
                    .filter(longListEntry -> longListEntry.getValue().size() > 1)
                    .collect(Collectors.toList())
                    .stream()
                    .map(Map.Entry::getValue)
                    .forEach(process()));
  }

  private Consumer<List<DuplicateAdvance>> process() {
    return duplicateAdvances -> executor.submit(() -> deleteAllButLargest(duplicateAdvances));
  }

  private DuplicateAdvance buildDuplicate(final File file) {
    final Path path = thumbnailService.create(file);

    if (path != null) {
      final long hash = hashService.getPerceptualHash(path.toFile());

      return DuplicateAdvance.builder().path(path).hash(hash).file(file).build();
    } else {
      return DuplicateAdvance.builder().path(null).file(file).build();
    }
  }

  @SneakyThrows
  private void deleteAllButLargest(final List<DuplicateAdvance> duplicates) {

    duplicates.sort(Comparator.naturalOrder());

    for (final DuplicateAdvance duplicateAdvance : Iterables.skip(duplicates, 1)) {
      Files.deleteIfExists(duplicateAdvance.getPath());
      log.info("Deleted duplicate: {}", duplicateAdvance.getPath());
    }
  }
}
