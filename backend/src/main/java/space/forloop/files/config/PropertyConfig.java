/* Licensed under Apache-2.0 */
package space.forloop.files.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.forloop.files.properties.TimerProperties;

@Configuration
@ConfigurationProperties("files")
public class PropertyConfig {

  @Bean
  @ConfigurationProperties("files.timer")
  public TimerProperties timerProperties() {

    return new TimerProperties();
  }
}
