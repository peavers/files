/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.services;

import java.nio.file.Path;
import space.forloop.data.domain.File;

public interface ThumbnailService {

  Path create(final File file);
}
