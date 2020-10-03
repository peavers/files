/* Licensed under Apache-2.0 */
package space.forloop.files.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import space.forloop.files.converters.DirectoryConverter;
import space.forloop.files.converters.FileConverter;
import space.forloop.files.domain.Directory;
import space.forloop.files.domain.File;
import space.forloop.files.utils.PathUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {

  private final FileConverter fileConverter;

  private final DirectoryConverter directoryConverter;

  @Override
  public Flux<File> findAllFiles(final String rootDirectory) {

    try {
      return Flux.fromStream(
              Files.walk(Paths.get(rootDirectory))
                  .filter(PathUtils::isReadable)
                  .filter(PathUtils::isNotDirectory))
          .map(fileConverter::convert);
    } catch (final IOException e) {
      return Flux.error(e);
    }
  }

  @Override
  public Flux<Directory> findAllDirectories(final String rootDirectory) {

    try {
      return Flux.fromStream(
              Files.walk(Paths.get(rootDirectory))
                  .filter(PathUtils::isReadable)
                  .filter(PathUtils::isDirectory))
          .map(directoryConverter::convert);
    } catch (final IOException e) {
      return Flux.error(e);
    }
  }
}
