/* Licensed under Apache-2.0 */
package space.forloop.autotools.configuration;

import java.util.concurrent.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

  @Bean
  public ExecutorService executor() {
    return new ThreadPoolExecutor(
        0,
        10,
        60L,
        TimeUnit.SECONDS,
        new SynchronousQueue<>(),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.CallerRunsPolicy());
  }
}
