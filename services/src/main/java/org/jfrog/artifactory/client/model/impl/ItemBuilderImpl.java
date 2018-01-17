package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Item;
import org.jfrog.artifactory.client.model.builder.ItemBuilder;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class ItemBuilderImpl implements ItemBuilder {
    private String uri;
    private boolean folder;

    public ItemBuilderImpl uri(String uri) {
        this.uri = uri;
        return this;
    }

    public ItemBuilderImpl isFolder(boolean folder) {
        this.folder = folder;
        return this;
    }

    public Item build() {
        return new ItemImpl(folder, uri);
    }
}
