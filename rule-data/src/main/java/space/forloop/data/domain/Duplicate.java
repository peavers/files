/* Licensed under Apache-2.0 */
package space.forloop.data.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Duplicate {

  long id;

  @Builder.Default List<File> duplicates = new ArrayList<>();
}
