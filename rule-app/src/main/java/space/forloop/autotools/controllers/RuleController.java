/* Licensed under Apache-2.0 */
package space.forloop.autotools.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.autotools.dto.RuleDto;
import space.forloop.autotools.services.RuleService;
import space.forloop.data.rules.RuleCopyMediaFiles;
import space.forloop.data.rules.RuleDeleteEmptyDirectories;
import space.forloop.data.rules.RuleDeleteFiles;
import space.forloop.data.rules.RuleEnum;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/root/rules")
public class RuleController {

    private final RuleService ruleService;

    @PostMapping
    public Mono<?> save(@RequestBody final RuleDto ruleDto) {

        switch (RuleEnum.get(ruleDto.getType())) {
            case COPY_MEDIA_FILES:
                return ruleService
                        .saveRuleCopyMedia(
                                RuleCopyMediaFiles.builder()
                                        .enabled(ruleDto.isEnabled())
                                        .name(ruleDto.getName())
                                        .sourceDirectory(ruleDto.getSourceDirectory())
                                        .targetDirectory(ruleDto.getTargetDirectory())
                                        .build())
                        .map(RuleDto::new);
            case DELETE_EMPTY_DIRECTORIES:
                return ruleService
                        .saveRuleDeleteEmptyDirectories(
                                RuleDeleteEmptyDirectories.builder()
                                        .enabled(ruleDto.isEnabled())
                                        .name(ruleDto.getName())
                                        .sourceDirectory(ruleDto.getSourceDirectory())
                                        .build())
                        .map(RuleDto::new);
            case DELETE_FILES:
                return ruleService
                        .saveRuleDeleteFiles(
                                RuleDeleteFiles.builder()
                                        .enabled(ruleDto.isEnabled())
                                        .name(ruleDto.getName())
                                        .sourceDirectory(ruleDto.getSourceDirectory())
                                        .lessThanThreshold(ruleDto.getLessThanThreshold())
                                        .greaterThanThreshold(ruleDto.getGreaterThanThreshold())
                                        .build())
                        .map(RuleDto::new);
            default:
                return Mono.empty();
        }
    }

    @GetMapping
    public Flux<Object> findAll() {

        return Flux.fromStream(
                Stream.of(
                        ruleService.findAllRuleCopyMedia().stream()
                                .map(RuleDto::new)
                                .collect(Collectors.toSet()),
                        ruleService.findAllRuleDeleteEmptyDirectories().stream()
                                .map(RuleDto::new)
                                .collect(Collectors.toSet()),
                        ruleService.findAllRuleDeleteFiles().stream()
                                .map(RuleDto::new)
                                .collect(Collectors.toSet()))
                        .flatMap(Collection::stream));
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable final String id) {
        return Mono.empty();
    }
}
