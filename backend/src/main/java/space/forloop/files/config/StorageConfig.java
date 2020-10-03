/* Licensed under Apache-2.0 */
package space.forloop.files.config;

import lombok.extern.slf4j.Slf4j;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.forloop.files.domain.RootData;

@Slf4j
@Configuration
public class StorageConfig {

  @Bean
  public EmbeddedStorageManager embeddedStorageManager() {

    final EmbeddedStorageManager storageManager = EmbeddedStorage.start();

    if (storageManager.root() == null) {
      log.info("Loaded new configuration {}", storageManager.setRoot(RootData.builder().build()));
    } else {
      log.info("Loaded saved configuration {}", storageManager.root());
    }

    return storageManager;
  }
}
