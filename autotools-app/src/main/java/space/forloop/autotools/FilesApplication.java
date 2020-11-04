/* Licensed under Apache-2.0 */
package space.forloop.autotools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import space.forloop.autotools.configuration.PropertyConfig;

@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = "space.forloop.*")
@EnableConfigurationProperties(PropertyConfig.class)
public class FilesApplication {

  public static void main(final String[] args) {
    SpringApplication.run(FilesApplication.class, args);
  }
}
