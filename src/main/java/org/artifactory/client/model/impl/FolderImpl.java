package org.artifactory.client.model.impl;


import org.artifactory.client.model.Folder;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class FolderImpl extends ItemImpl implements Folder {

    String repo;
    String path;
    Date created;
    String createdBy;
    String modifiedBy;
    Date lastModified;
    Date lastUpdated;
    String metadataUri;
    List<ItemImpl> children;

    private FolderImpl() {
        super(true, null);
    }

    FolderImpl(String uri, List<ItemImpl> children, Date created, String createdBy, Date lastModified, Date lastUpdated, String metadataUri, String modifiedBy, String path, String repo) {
        super(true, uri);
        this.children = children;
        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastUpdated = lastUpdated;
        this.metadataUri = metadataUri;
        this.modifiedBy = modifiedBy;
        this.path = path;
        this.repo = repo;
    }

    @Override
    public List<ItemImpl> getChildren() {
        return children;
    }

    private void setChildren(List<ItemImpl> children) {
        this.children = children;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    private void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    private void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }

    private void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public Date getLastUpdated() {
        return lastUpdated;
    }

    private void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String getMetadataUri() {
        return metadataUri;
    }

    private void setMetadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
    }

    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }

    private void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String getPath() {
        return path;
    }

    private void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getRepo() {
        return repo;
    }

    private void setRepo(String repo) {
        this.repo = repo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FolderImpl folder = (FolderImpl) o;

        if (children != null ? !children.equals(folder.children) : folder.children != null) return false;
        if (created != null ? !created.equals(folder.created) : folder.created != null) return false;
        if (createdBy != null ? !createdBy.equals(folder.createdBy) : folder.createdBy != null) return false;
        if (lastModified != null ? !lastModified.equals(folder.lastModified) : folder.lastModified != null)
            return false;
        if (lastUpdated != null ? !lastUpdated.equals(folder.lastUpdated) : folder.lastUpdated != null) return false;
        if (metadataUri != null ? !metadataUri.equals(folder.metadataUri) : folder.metadataUri != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(folder.modifiedBy) : folder.modifiedBy != null) return false;
        if (path != null ? !path.equals(folder.path) : folder.path != null) return false;
        if (repo != null ? !repo.equals(folder.repo) : folder.repo != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "children=" + children +
                ", repo='" + repo + '\'' +
                ", path='" + path + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", lastModified=" + lastModified +
                ", lastUpdated=" + lastUpdated +
                ", metadataUri='" + metadataUri + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (repo != null ? repo.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
        result = 31 * result + (metadataUri != null ? metadataUri.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }


}
