package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.ListItem;

import java.util.Date;

public class ListItemImpl implements ListItem {
    String uri;
    Long size;
    Date lastModified;
    Boolean folder;
    String sha1;

    ListItemImpl(String uri, Long size, Date lastModified, Boolean folder) {
        this.uri = uri;
        this.size = size;
        this.lastModified = lastModified;
        this.folder = folder;
    }

    ListItemImpl() {
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public Boolean isFolder() {
        return folder;
    }

    @Override
    public String getSha1() {
        return sha1;
    }

}
