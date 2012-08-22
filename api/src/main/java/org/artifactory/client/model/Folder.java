package org.artifactory.client.model;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Folder extends Item {
    List<Item> getChildren();

    Date getCreated();

    String getCreatedBy();
}
