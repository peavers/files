/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.converters.FileConverter;
import space.forloop.common.utils.PathUtils;
import space.forloop.data.domain.DuplicateAdvance;
import space.forloop.data.domain.File;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleDuplicateMedia;
import space.forloop.duplicate.media.services.HashService;
import space.forloop.duplicate.media.services.ThumbnailService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class DuplicateMediaTaskAdvance {

  private final FileConverter fileConverter;

  private final RootRepository rootRepository;

  private final ThumbnailService thumbnailService;

  private final HashService hashService;

  @SneakyThrows
  private static void deleteAllButLargest(final List<DuplicateAdvance> duplicates) {

    duplicates.sort(Comparator.naturalOrder()); //sorts based on filesize
    duplicates.sort(Comparator.reverseOrder()); //reverse to get the largest on top of the list to skip

    for (final DuplicateAdvance duplicateAdvance : Iterables.skip(duplicates, 1)) {
      log.info("Deleting duplicate: {}", duplicateAdvance.getFile());
      Files.deleteIfExists(duplicateAdvance.getPath());
    }
  }

  @Scheduled(fixedDelayString = "${files.timer.duplicate-media}")
  public void deleteDuplicatesByContent() {

    rootRepository.findRoot().getRuleDuplicateMedia().stream()
        .filter(RuleDuplicateMedia::isEnabled)
        .forEach(
            rule ->
                getFileStream(rule)
                    .map(this::getDuplicateAdvance)
                    .collect(Collectors.groupingBy(DuplicateAdvance::getHash))
                    .entrySet()
                    .stream()
                    .filter(longListEntry -> longListEntry.getValue().size() > 1)
                    .collect(Collectors.toList())
                    .forEach(longListEntry -> deleteAllButLargest(longListEntry.getValue())));
  }

  private DuplicateAdvance getDuplicateAdvance(final File file) {
    final Path path = thumbnailService.create(file);

    if (path != null) {
      final long hash = hashService.getPerceptualHash(path.toFile());

      return DuplicateAdvance.builder().path(path).hash(hash).file(file).build();
    } else {
      return DuplicateAdvance.builder().path(null).file(file).build();
    }
  }

  private Stream<File> getFileStream(final RuleDuplicateMedia rule) {
    try {
      return Files.walk(Paths.get(rule.getSourceDirectory()))
          .filter(PathUtils::isReadable)
          .filter(PathUtils::isNotDirectory)
          .map(fileConverter::convert);
    } catch (final IOException e) {
      return Stream.empty();
    }
  }
}
