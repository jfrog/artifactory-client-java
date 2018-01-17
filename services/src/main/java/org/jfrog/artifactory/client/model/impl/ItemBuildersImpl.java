package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Folder;
import org.jfrog.artifactory.client.model.Item;
import org.jfrog.artifactory.client.model.builder.FolderBuilder;
import org.jfrog.artifactory.client.model.builder.ItemBuilder;
import org.jfrog.artifactory.client.model.builder.ItemBuilders;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class ItemBuildersImpl implements ItemBuilders {
    private ItemBuildersImpl() {
    }

    public ItemBuilder itemBuilder() {
        return ((ItemBuilder) (new ItemBuildersImpl()));
    }

    public FolderBuilder folderBuilder() {
        return new FolderBuilderImpl();
    }

    public FolderBuilder builderFrom(Folder from) {
        return new FolderBuilderImpl().path(from.getPath()).uri(from.getUri()).children(from.getChildren())
                .created(from.getCreated()).createdBy(from.getCreatedBy()).lastModified(from.getLastModified())
                .lastUpdated(from.getLastUpdated()).repo(from.getRepo()).path(from.getPath()).metadataUri(from.getMetadataUri());
    }

    public ItemBuilder builderFrom(Item from) {
        return new ItemBuilderImpl().uri(from.getUri()).isFolder(from.isFolder());
    }
}
