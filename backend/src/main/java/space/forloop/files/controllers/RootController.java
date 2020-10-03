/* Licensed under Apache-2.0 */
package space.forloop.files.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import space.forloop.files.repositories.RootRepository;
import space.forloop.files.utils.JsonUtils;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/root")
public class RootController {

  private final RootRepository rootRepository;

  @GetMapping
  public Mono<String> setDeleteSmallFiles() {
    return Mono.just(rootRepository.findRoot()).flatMap(JsonUtils::toJson);
  }
}
