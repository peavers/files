/* Licensed under Apache-2.0 */
package space.forloop.files.properties;

import lombok.Data;

@Data
public class TimerProperties {

  private int deleteSmallFiles;

  private int deleteEmptyDirectories;

  private int copyFiles;
}
