/* Licensed under Apache-2.0 */
package space.forloop.files.repositories;

import space.forloop.files.domain.RootData;

public interface RootRepository {

  void store(Object object);

  void storeAll(Object... objects);

  RootData findRoot();
}
