package org.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.artifactory.client.model.Folder;
import org.artifactory.client.model.Item;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch1
 * @since 29/07/12
 */
public class FolderImpl extends ItemImpl implements Folder {

    private String repo;
    private String path;
    private Date created;
    private String createdBy;
    private List<Item> children;

    private FolderImpl() {
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    @Override
    public List<Item> getChildren() {
        return children;
    }

    @JsonDeserialize(contentAs = ItemImpl.class)
    private void setChildren(List<Item> children) {
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
                '}';
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (repo != null ? repo.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }


}
