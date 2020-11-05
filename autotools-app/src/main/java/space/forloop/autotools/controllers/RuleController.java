/* Licensed under Apache-2.0 */
package space.forloop.autotools.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.data.dto.RuleDto;
import space.forloop.data.repositories.RuleRepository;
import space.forloop.data.rules.*;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/root/rules")
public class RuleController {

  private final RuleRepository<RuleCopyMediaFiles> copyMediaFilesRuleRepository;
  private final RuleRepository<RuleDeleteEmptyDirectories> deleteEmptyDirectoriesRuleRepository;
  private final RuleRepository<RuleDeleteFiles> deleteFilesRuleRepository;
  private final RuleRepository<RuleDuplicateMediaAdvance> duplicateMediaAdvanceRuleRepository;
  private final RuleRepository<RuleDuplicateMediaBasic> duplicateMediaBasicRuleRepository;

  @GetMapping
  public Flux<Object> findAll() {

    return Flux.concat(
        copyMediaFilesRuleRepository.findAllFlux().map(RuleDto::new),
        deleteEmptyDirectoriesRuleRepository.findAllFlux().map(RuleDto::new),
        deleteFilesRuleRepository.findAllFlux().map(RuleDto::new),
        duplicateMediaAdvanceRuleRepository.findAllFlux().map(RuleDto::new),
        duplicateMediaBasicRuleRepository.findAllFlux().map(RuleDto::new));
  }

  @PostMapping
  public Mono<RuleDto> save(@RequestBody final RuleDto ruleDto) {

    switch (RuleEnum.get(ruleDto.getType())) {
      case COPY_MEDIA_FILES:
        return copyMediaFilesRuleRepository.save(new RuleCopyMediaFiles(ruleDto)).map(RuleDto::new);
      case DELETE_EMPTY_DIRECTORIES:
        return deleteEmptyDirectoriesRuleRepository
            .save(new RuleDeleteEmptyDirectories(ruleDto))
            .map(RuleDto::new);
      case DELETE_FILES:
        return deleteFilesRuleRepository.save(new RuleDeleteFiles(ruleDto)).map(RuleDto::new);
      case DUPLICATE_MEDIA_ADVANCE:
        return duplicateMediaAdvanceRuleRepository
            .save(new RuleDuplicateMediaAdvance(ruleDto))
            .map(RuleDto::new);
      case DUPLICATE_MEDIA_BASIC:
        return duplicateMediaBasicRuleRepository
            .save(new RuleDuplicateMediaBasic(ruleDto))
            .map(RuleDto::new);
      default:
        return Mono.empty();
    }
  }

  @PostMapping("/delete")
  public Mono<Void> delete(@RequestBody final RuleDto ruleDto) {

    switch (RuleEnum.get(ruleDto.getType())) {
      case COPY_MEDIA_FILES:
        return copyMediaFilesRuleRepository.delete(new RuleCopyMediaFiles(ruleDto));
      case DELETE_EMPTY_DIRECTORIES:
        deleteEmptyDirectoriesRuleRepository.delete(new RuleDeleteEmptyDirectories(ruleDto));
      case DELETE_FILES:
        deleteFilesRuleRepository.delete(new RuleDeleteFiles(ruleDto));
      case DUPLICATE_MEDIA_ADVANCE:
        duplicateMediaAdvanceRuleRepository.delete(new RuleDuplicateMediaAdvance(ruleDto));
      case DUPLICATE_MEDIA_BASIC:
        duplicateMediaBasicRuleRepository.delete(new RuleDuplicateMediaBasic(ruleDto));
      default:
        return Mono.empty();
    }
  }
}
