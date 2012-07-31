package org.artifactory.client.model.builder

import org.artifactory.client.model.Item
import org.artifactory.client.model.impl.ItemImpl

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class ItemBuilder {
    private String uri;
    private boolean folder;


    private ItemBuilder() {
    }

    public static ItemBuilder create() {
        return new ItemBuilder();
    }

    ItemBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    ItemBuilder isFolder(boolean folder) {
        this.folder = folder;
        return this;
    }

    Item build() {
        return new ItemImpl(folder, uri);
    }

    public static ItemBuilder copyFrom(ItemImpl from) {
        return new ItemBuilder().uri(from.uri).isFolder(from.folder);
    }
}
