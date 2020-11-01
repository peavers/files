/* Licensed under Apache-2.0 */
package space.forloop.data.domain;

import java.nio.file.Path;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DuplicateAdvance implements Comparable<DuplicateAdvance> {

  long hash;

  Path path;

  File file;

  @Override
  public int compareTo(final DuplicateAdvance o) {
    return Long.compare(this.getFile().getSize(), o.getFile().getSize());
  }
}
