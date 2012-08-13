package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.Folder
import org.artifactory.client.model.Item
import org.artifactory.client.model.builder.FolderBuilder
import org.artifactory.client.model.impl.FolderImpl
/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class FolderBuilderImpl implements FolderBuilder {
    String uri;
    String repo;
    String path;
    Date created;
    String createdBy;
    String modifiedBy;
    Date lastModified;
    Date lastUpdated;
    String metadataUri;
    List<Item> children;

    private FolderBuilderImpl() {

    }

    public FolderBuilderImpl uri(String uri) {
        this.uri = uri;
        return this;
    }

    public FolderBuilderImpl repo(String repo) {
        this.repo = repo;
        return this;
    }

    public FolderBuilderImpl path(String path) {
        this.path = path;
        return this;
    }

    public FolderBuilderImpl created(Date created) {
        this.created = new Date(created.getTime());
        return this;
    }

    public FolderBuilderImpl createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public FolderBuilderImpl modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public FolderBuilderImpl lastModified(Date lastModified) {
        this.lastModified = new Date(lastModified.getTime());
        return this;
    }

    public FolderBuilderImpl lastUpdated(Date lastUpdated) {
        this.lastUpdated = new Date(lastUpdated.getTime());
        return this;
    }

    public FolderBuilderImpl metadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
        return this;
    }

    public FolderBuilderImpl children(List<Item> children) {
        this.children = children;
        return this;
    }

    public static FolderBuilderImpl create() {
        return new FolderBuilderImpl();
    }

    public Folder build() {
        return new FolderImpl(uri, metadataUri, lastModified, modifiedBy, lastUpdated, created, createdBy, children);
    }
}
