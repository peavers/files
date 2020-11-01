/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.tasks;

// @Slf4j
// @Async
// @Component
// @RequiredArgsConstructor
// public class DuplicateMediaTaskBasic {
//
//  private final FileConverter fileConverter;
//
//  private final RootRepository rootRepository;
//
//  @SneakyThrows
//  private static void deleteAllButLastAccess(final Duplicate duplicate) {
//    duplicate.getDuplicates().sort(Comparator.comparing(File::getLastAccessTime).reversed());
//
//    for (final File file : Iterables.skip(duplicate.getDuplicates(), 1)) {
//      log.info("Deleting: {}", file.getPath());
//      Files.deleteIfExists(Path.of(file.getPath()));
//    }
//  }
//
//  @Scheduled(fixedDelayString = "${files.timer.duplicate.media}")
//  public void deleteDuplicatesByFileSize() {
//
//    rootRepository.findRoot().getRuleDuplicateMedia().stream()
//        .filter(RuleDuplicateMedia::isEnabled)
//        .forEach(
//            rule ->
//                getFileStream(rule)
//                    .collect(Collectors.groupingBy(File::getSize))
//                    .entrySet()
//                    .stream()
//                    .filter(entry -> entry.getValue().size() > 1)
//                    .map(
//                        entry ->
//                            Duplicate.builder()
//                                .id(entry.getKey())
//                                .duplicates(entry.getValue())
//                                .build())
//                    .collect(Collectors.toList())
//                    .forEach(DuplicateMediaTaskBasic::deleteAllButLastAccess));
//  }
//
//  private Stream<File> getFileStream(final RuleDuplicateMedia rule) {
//    try {
//      return Files.walk(Paths.get(rule.getSourceDirectory()))
//          .filter(PathUtils::isReadable)
//          .filter(PathUtils::isNotDirectory)
//          .map(fileConverter::convert);
//    } catch (final IOException e) {
//      return Stream.empty();
//    }
//  }
// }
