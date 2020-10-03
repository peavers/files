/* Licensed under Apache-2.0 */
package space.forloop.files.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

@UtilityClass
public class JsonUtils {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public Mono<String> toJson(final Object value) {
    try {
      return Mono.just(objectMapper.writeValueAsString(value));
    } catch (final JsonProcessingException e) {
      return Mono.error(e);
    }
  }
}
