/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.common.converters.FileConverter;
import space.forloop.common.utils.PathUtils;
import space.forloop.data.domain.Duplicate;
import space.forloop.data.domain.File;
import space.forloop.data.repositories.RootRepository;
import space.forloop.data.rules.RuleDuplicateMedia;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class DuplicateMediaTask {

  private final FileConverter fileConverter;

  private final RootRepository rootRepository;

  @SneakyThrows
  private static void deleteAllButLastAccess(final Duplicate duplicate) {
    duplicate.getDuplicates().sort(Comparator.comparing(File::getLastAccessTime).reversed());

    for (final File file : Iterables.skip(duplicate.getDuplicates(), 1)) {
      log.info("Deleting: {}", file.getPath());
      Files.deleteIfExists(Path.of(file.getPath()));
    }
  }

  @Scheduled(fixedDelayString = "${files.timer.delete-small-files}")
  public void deleteDuplicates() {

    rootRepository.findRoot().getRuleDuplicateMedia().stream()
        .filter(RuleDuplicateMedia::isEnabled)
        .forEach(
            rule ->
                getFileStream(rule)
                    .collect(Collectors.groupingBy(File::getSize))
                    .entrySet()
                    .stream()
                    .filter(f -> f.getValue().size() > 1)
                    .map(
                        entry ->
                            Duplicate.builder()
                                .id(entry.getKey())
                                .duplicates(entry.getValue())
                                .build())
                    .collect(Collectors.toList())
                    .forEach(DuplicateMediaTask::deleteAllButLastAccess));
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
