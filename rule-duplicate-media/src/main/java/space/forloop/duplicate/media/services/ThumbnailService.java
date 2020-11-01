/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.services;

import space.forloop.data.domain.File;

import java.nio.file.Path;

public interface ThumbnailService {

  Path create(final File file);
}
