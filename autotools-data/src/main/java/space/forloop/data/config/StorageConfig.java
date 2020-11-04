/* Licensed under Apache-2.0 */
package space.forloop.data.config;

import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.forloop.data.domain.RootData;

@Slf4j
@Configuration
public class StorageConfig {

  @Bean
  public EmbeddedStorageManager embeddedStorageManager() {

    final EmbeddedStorageManager storageManager = EmbeddedStorage.start(Paths.get("/tmp/storage"));

    if (storageManager.root() == null) {
      log.info("Loaded new configuration {}", storageManager.setRoot(RootData.builder().build()));
    } else {
      log.info("Loaded saved configuration {}", storageManager.root());
    }

    return storageManager;
  }
}
