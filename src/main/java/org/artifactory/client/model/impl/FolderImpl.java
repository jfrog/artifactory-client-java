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
    public String toString() {
        return "Folder{" +
                "children=" + children +
                ", repo='" + repo + '\'' +
                ", path='" + path + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
