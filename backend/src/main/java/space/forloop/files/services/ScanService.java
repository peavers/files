/* Licensed under Apache-2.0 */
package space.forloop.files.services;

import reactor.core.publisher.Flux;
import space.forloop.files.domain.Directory;
import space.forloop.files.domain.File;

public interface ScanService {

  Flux<File> findAllFiles(String rootDirectory);

  Flux<Directory> findAllDirectories(String rootDirectory);
}
