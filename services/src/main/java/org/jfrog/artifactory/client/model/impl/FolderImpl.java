package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jfrog.artifactory.client.model.Folder;
import org.jfrog.artifactory.client.model.Item;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch1
 * @since 29/07/12
 */
public class FolderImpl extends ItemImpl implements Folder {

    private Date created;
    private String createdBy;
    private List<Item> children;

    public FolderImpl(String uri, String metadataUri, Date lastModified, String modifiedBy, Date lastUpdated, Date created, String createdBy, List<Item> children) {
        super(true, uri, metadataUri, lastModified, modifiedBy, lastUpdated);
        this.created = created;
        this.createdBy = createdBy;
        this.children = children;
    }

    public FolderImpl() {
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
        //TODO: [by yl] Use a childItem class since children ItemImpl lacks a lot of data
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
    public String toString() {
        return "Folder{" +
                "children=" + children +
                ", repo='" + getRepo() + '\'' +
                ", path='" + getPath() + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
