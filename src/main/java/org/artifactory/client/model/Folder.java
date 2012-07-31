package org.artifactory.client.model;

import org.artifactory.client.model.impl.FolderImpl;
import org.artifactory.client.model.impl.ItemImpl;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Folder extends Item {
    List<ItemImpl> getChildren();

    Date getCreated();

    String getCreatedBy();

    Date getLastModified();

    Date getLastUpdated();

    String getMetadataUri();

    String getModifiedBy();

    String getPath();

    String getRepo();

}
