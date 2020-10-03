/* Licensed under Apache-2.0 */
package space.forloop.files.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogItem {

  private String message;

  private Object object;
}
