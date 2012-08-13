package org.artifactory.client;

import org.artifactory.client.model.Item;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface Files {

    Files repoKey(String repoKey);

    Files filePath(String filePath);

    Item get();
}
