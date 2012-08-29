package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.Item
import org.artifactory.client.model.builder.ItemBuilder
import org.artifactory.client.model.impl.ItemImpl
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class ItemBuilderImpl implements ItemBuilder {
    private String uri;
    private boolean folder;


    private ItemBuilderImpl() {
    }

    ItemBuilderImpl uri(String uri) {
        this.uri = uri;
        return this;
    }

    ItemBuilderImpl isFolder(boolean folder) {
        this.folder = folder;
        return this;
    }

    Item build() {
        return new ItemImpl(folder, uri);
    }

}
