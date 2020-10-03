/* Licensed under Apache-2.0 */
package space.forloop.files.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.files.domain.Profile;
import space.forloop.files.exceptions.NotFound;
import space.forloop.files.services.ProfileService;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/root/profile")
public class ProfileController {

  private final ProfileService profileService;

  @PostMapping
  public Mono<Profile> save(@RequestBody final Profile profile) {
    return profileService.save(profile);
  }

  @GetMapping
  public Flux<Profile> findAll() {
    return profileService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<Profile> findById(@PathVariable final String id) throws NotFound {
    return profileService.findById(id);
  }
}
