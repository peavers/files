/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.microstream.storage.types.EmbeddedStorageManager;
import org.springframework.stereotype.Component;
import space.forloop.data.domain.RootData;

@Slf4j
@Component
@RequiredArgsConstructor
public class RootRepositoryImpl implements RootRepository {

  private final EmbeddedStorageManager storageManager;

  @Override
  public void store(final Object object) {
    this.storageManager.store(object);
    this.storageManager.storeRoot();
  }

  @Override
  public RootData findRoot() {
    return (RootData) this.storageManager.root();
  }
}
