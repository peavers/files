/* Licensed under Apache-2.0 */
package space.forloop.directories.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import space.forloop.common.converters.DirectoryConverter;
import space.forloop.common.utils.PathUtils;
import space.forloop.data.domain.Directory;

@Slf4j
@Service(value = "space.forloop.files.services.ScanServiceImpl")
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {

  private final DirectoryConverter directoryConverter;

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
