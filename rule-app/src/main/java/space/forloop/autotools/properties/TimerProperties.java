/* Licensed under Apache-2.0 */
package space.forloop.autotools.properties;

import lombok.Data;

@Data
public class TimerProperties {

  private int deleteSmallFiles;

  private int deleteEmptyDirectories;

  private int copyFiles;

  private int duplicateMedia;
}
