package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Folder
import org.jfrog.artifactory.client.model.Item
import org.jfrog.artifactory.client.model.builder.FolderBuilder
import org.jfrog.artifactory.client.model.builder.ItemBuilder
import org.jfrog.artifactory.client.model.builder.ItemBuilders
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class ItemBuildersImpl implements ItemBuilders {

    private ItemBuildersImpl() {}

    ItemBuilder itemBuilder() {
        new ItemBuildersImpl()
    }

    FolderBuilder folderBuilder() {
        new FolderBuilderImpl()
    }

    public FolderBuilder builderFrom(Folder from) {
        return new FolderBuilderImpl().path(from.path).uri(from.uri).children(from.children).created(from.created).createdBy(from.createdBy).lastModified(from.lastModified)
                .lastUpdated(from.lastUpdated).repo(from.repo).path(from.path).metadataUri(from.metadataUri);
    }

    ItemBuilder builderFrom(Item from) {
        return new ItemBuilderImpl().uri(from.uri).isFolder(from.folder);
    }

}
