/* Licensed under Apache-2.0 */
package space.forloop.delete.files.services;

import reactor.core.publisher.Flux;
import space.forloop.data.domain.File;

public interface ScanService {

  Flux<File> findAllFiles(String rootDirectory);
}
