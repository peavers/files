/* Licensed under Apache-2.0 */
package space.forloop.data.repositories;

import space.forloop.data.domain.RootData;

public interface RootRepository {

  void store(Object object);

  RootData findRoot();
}
