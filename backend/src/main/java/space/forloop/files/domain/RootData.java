/* Licensed under Apache-2.0 */
package space.forloop.files.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import space.forloop.files.properties.TimerProperties;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RootData {

  @Builder.Default private TimerProperties timerProperties = new TimerProperties();

  @Builder.Default private Set<Profile> profiles = new HashSet<>();
}
