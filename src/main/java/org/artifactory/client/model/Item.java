package org.artifactory.client.model;

import org.artifactory.client.model.impl.ItemImpl;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Item {
    boolean isFolder();

    String getUri();

}
