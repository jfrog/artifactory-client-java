package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.FileList;
import org.jfrog.artifactory.client.model.ListItem;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileListImpl implements FileList {

    String uri;
    Date created;
    ListItemImpl[] files;

    FileListImpl(String uri, Date created, ListItemImpl[] files) {
        this.uri = uri;
        this.created = created;
        this.files = files;
    }

    public FileListImpl() {
    }

    @Override
    public  String getUri() {
        return uri;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public List<ListItem> getFiles() {
        return Arrays.asList(files);
    }
}
