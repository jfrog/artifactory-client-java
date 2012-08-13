package org.artifactory.client.model;

import java.util.Date;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Item {
    boolean isFolder();

    String getName();

    String getUri();

    String getPath();

    String getRepo();

    String getMetadataUri();

    Date getLastModified();

    String getModifiedBy();

    Date getLastUpdated();
}
