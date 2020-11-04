/* Licensed under Apache-2.0 */
package space.forloop.autotools.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

  @Bean
  public ExecutorService executor() {
    return Executors.newCachedThreadPool();
  }
}
