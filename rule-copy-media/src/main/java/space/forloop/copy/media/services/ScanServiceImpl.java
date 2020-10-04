/* Licensed under Apache-2.0 */
package space.forloop.copy.media.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import space.forloop.common.converters.FileConverter;
import space.forloop.common.utils.PathUtils;
import space.forloop.data.domain.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {

  private final FileConverter fileConverter;

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
}
