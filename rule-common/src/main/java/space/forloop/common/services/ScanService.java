/* Licensed under Apache-2.0 */
package space.forloop.common.services;

import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import space.forloop.data.domain.Directory;
import space.forloop.data.domain.File;

public interface ScanService {

  Stream<File> findFiles(String path);

  Flux<File> findFilesFlux(String path);

  Flux<Directory> findDirectoriesFlux(String path);
}
