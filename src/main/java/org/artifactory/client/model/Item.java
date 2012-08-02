package org.artifactory.client.model;

import org.artifactory.client.model.impl.ItemImpl;

import java.util.Date;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Item {
    boolean isFolder();

    String getUri();

    String getMetadataUri();

    Date getLastModified();

    String getModifiedBy();

    Date getLastUpdated();
}
