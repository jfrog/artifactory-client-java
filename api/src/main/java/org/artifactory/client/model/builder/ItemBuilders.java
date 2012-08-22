package org.artifactory.client.model.builder;

import org.artifactory.client.model.Folder;
import org.artifactory.client.model.Item;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface ItemBuilders {

    FolderBuilder folderBuilder();

    FolderBuilder builderFrom(Folder from);

    ItemBuilder builderFrom(Item from);

    ItemBuilder itemBuilder();

}