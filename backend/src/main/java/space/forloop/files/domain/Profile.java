/* Licensed under Apache-2.0 */
package space.forloop.files.domain;

import java.util.ArrayDeque;
import lombok.Data;

@Data
public class Profile {

  private String id;

  private String name;

  private String monitorDirectory;

  private String copyToDirectory;

  private long deleteThreshold;

  private boolean deleteSmallFiles;

  private boolean deleteEmptyDirectories;

  private boolean copyMediaFiles;

  private ArrayDeque<LogItem> logItems = new ArrayDeque<>();
}
