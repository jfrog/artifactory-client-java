package org.artifactory.client.model.builder

import org.artifactory.client.model.impl.FolderImpl
import org.artifactory.client.model.impl.ItemImpl
import org.artifactory.client.model.Folder

/**
 *
 * @author jbaruch
 * @since 31/07/12
 */
class FolderBuilder {
    String uri;
    String repo;
    String path;
    Date created;
    String createdBy;
    String modifiedBy;
    Date lastModified;
    Date lastUpdated;
    String metadataUri;
    List<ItemImpl> children;


    public FolderBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    public FolderBuilder repo(String repo) {
        this.repo = repo;
        return this;
    }

    public FolderBuilder path(String path) {
        this.path = path;
        return this;
    }

    public FolderBuilder created(Date created) {
        this.created = new Date(created.getTime());
        return this;
    }

    public FolderBuilder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public FolderBuilder modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public FolderBuilder lastModified(Date lastModified) {
        this.lastModified = new Date(lastModified.getTime());
        return this;
    }

    public FolderBuilder lastUpdated(Date lastUpdated) {
        this.lastUpdated = new Date(lastUpdated.getTime());
        return this;
    }

    public FolderBuilder metadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
        return this;
    }

    public FolderBuilder children(List<ItemImpl> children) {
        this.children = children;
        return this;
    }

    public static FolderBuilder create() {
        return new FolderBuilder();
    }

    public Folder build() {
        return new FolderImpl(uri, children, created, createdBy, lastModified, lastUpdated, metadataUri, modifiedBy, path, repo);
    }

    public static FolderBuilder copyFrom(FolderImpl from) {
        return new FolderBuilder().path(from.path).uri(from.uri).children(from.children).created(from.created).createdBy(from.createdBy).lastModified(from.lastModified)
                .lastUpdated(from.lastUpdated).repo(from.repo).path(from.path).metadataUri(from.metadataUri);
    }
}
