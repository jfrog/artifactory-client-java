package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Item
import org.jfrog.artifactory.client.model.builder.ItemBuilder
import org.jfrog.artifactory.client.model.impl.ItemImpl
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class ItemBuilderImpl implements ItemBuilder {
    private String uri
    private boolean folder


    private ItemBuilderImpl() {
    }

    ItemBuilderImpl uri(String uri) {
        this.uri = uri
        this
    }

    ItemBuilderImpl isFolder(boolean folder) {
        this.folder = folder
        this
    }

    Item build() {
        return new ItemImpl(folder, uri);
    }

}
