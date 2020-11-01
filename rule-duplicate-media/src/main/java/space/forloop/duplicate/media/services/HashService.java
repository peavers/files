/* Licensed under Apache-2.0 */
package space.forloop.duplicate.media.services;

import java.io.File;

public interface HashService {

  long getPerceptualHash(final File imageFile);
}
