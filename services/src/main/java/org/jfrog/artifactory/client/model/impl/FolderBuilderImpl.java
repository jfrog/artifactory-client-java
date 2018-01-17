package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Folder;
import org.jfrog.artifactory.client.model.Item;
import org.jfrog.artifactory.client.model.builder.FolderBuilder;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public class FolderBuilderImpl implements FolderBuilder {
    private String uri;
    private String repo;
    private String path;
    private Date created;
    private String createdBy;
    private String modifiedBy;
    private Date lastModified;
    private Date lastUpdated;
    private String metadataUri;
    private List<Item> children;

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

    public Folder build() {
        return new FolderImpl(uri, metadataUri, lastModified, modifiedBy, lastUpdated, created, createdBy, children);
    }
}
