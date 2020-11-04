/* Licensed under Apache-2.0 */
package space.forloop.autotools.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.autotools.services.RuleService;
import space.forloop.data.dto.RuleDto;
import space.forloop.data.rules.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/root/rules")
public class RuleController {

  private final RuleService ruleService;

  @PostMapping
  public Mono<RuleDto> save(@RequestBody final RuleDto ruleDto) {

    switch (RuleEnum.get(ruleDto.getType())) {
      case COPY_MEDIA_FILES:
        return ruleService.saveRuleCopyMedia(new RuleCopyMediaFiles(ruleDto)).map(RuleDto::new);
      case DELETE_EMPTY_DIRECTORIES:
        return ruleService
            .saveRuleDeleteEmptyDirectories(new RuleDeleteEmptyDirectories(ruleDto))
            .map(RuleDto::new);
      case DELETE_FILES:
        return ruleService.saveRuleDeleteFiles(new RuleDeleteFiles(ruleDto)).map(RuleDto::new);
      case DUPLICATE_MEDIA_ADVANCE:
        return ruleService
            .saveRuleDuplicateMediaAdvance(new RuleDuplicateMediaAdvance(ruleDto))
            .map(RuleDto::new);
      case DUPLICATE_MEDIA_BASIC:
        return ruleService
            .saveRuleDuplicateMediaBasic(new RuleDuplicateMediaBasic(ruleDto))
            .map(RuleDto::new);
      default:
        return Mono.empty();
    }
  }

  @GetMapping
  public Flux<Object> findAll() {

    return Flux.fromStream(
        StreamEx.of(ruleService.findAllRuleCopyMedia().stream().map(RuleDto::new))
            .append(ruleService.findAllRuleDeleteEmptyDirectories().stream().map(RuleDto::new))
            .append(ruleService.findAllRuleDeleteFiles().stream().map(RuleDto::new))
            .append(ruleService.findAllRulesDuplicateMediaAdvance().stream().map(RuleDto::new))
            .append(ruleService.findAllRulesDuplicateMediaBasic().stream().map(RuleDto::new)));
  }

  @PostMapping("/delete")
  public Mono<Void> delete(@RequestBody final RuleDto ruleDto) {

    switch (RuleEnum.get(ruleDto.getType())) {
      case COPY_MEDIA_FILES:
        ruleService.deleteRuleCopyMedia(new RuleCopyMediaFiles(ruleDto));
      case DELETE_EMPTY_DIRECTORIES:
        ruleService.deleteRuleDeleteEmptyDirectories(new RuleDeleteEmptyDirectories(ruleDto));
      case DELETE_FILES:
        ruleService.deleteRuleDeleteFiles(new RuleDeleteFiles(ruleDto));
      case DUPLICATE_MEDIA_ADVANCE:
        ruleService.deleteRuleDuplicateMediaAdvance(new RuleDuplicateMediaAdvance(ruleDto));
      case DUPLICATE_MEDIA_BASIC:
        ruleService.deleteRuleDuplicateMediaBasic(new RuleDuplicateMediaBasic(ruleDto));
      default:
        return Mono.empty();
    }
  }
}
