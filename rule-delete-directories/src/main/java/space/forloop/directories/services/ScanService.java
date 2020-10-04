/* Licensed under Apache-2.0 */
package space.forloop.directories.services;

import reactor.core.publisher.Flux;
import space.forloop.data.domain.Directory;

public interface ScanService {

  Flux<Directory> findAllDirectories(String rootDirectory);
}
