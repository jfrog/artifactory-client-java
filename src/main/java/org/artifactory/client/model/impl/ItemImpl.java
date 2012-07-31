package org.artifactory.client.model.impl;

import org.artifactory.client.model.Item;

/**
 * @author jbaruch
 * @since 29/07/12
 */

public class ItemImpl implements Item {
    String uri;
    boolean folder;

    ItemImpl(boolean folder, String uri) {
        this.folder = folder;
        this.uri = uri;
    }

    protected ItemImpl() {
    }

    @Override
    public boolean isFolder() {
        return folder;
    }

    private void setFolder(boolean folder) {
        this.folder = folder;
    }

    @Override
    public String getUri() {
        return uri;
    }

    private void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemImpl item = (ItemImpl) o;

        return folder == item.folder && !(uri != null ? !uri.equals(item.uri) : item.uri != null);

    }

    @Override
    public String toString() {
        return "Item{" +
                "folder=" + folder +
                ", uri='" + uri + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (folder ? 1 : 0);
        return result;
    }

}