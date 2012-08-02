package org.artifactory.client.model.impl;

import org.artifactory.client.model.Item;

import java.util.Date;

/**
 * @author jbaruch
 * @since 29/07/12
 */

public class ItemImpl implements Item {
    private String uri;
    private boolean folder;
    private String metadataUri;
    private Date lastModified;
    private String modifiedBy;
    private Date lastUpdated;


    ItemImpl(boolean folder, String uri, String metadataUri, Date lastModified, String modifiedBy, Date lastUpdated) {
        this.folder = folder;
        this.uri = uri;
        this.metadataUri = metadataUri;
        this.lastModified = lastModified;
        this.modifiedBy = modifiedBy;
        this.lastUpdated = lastUpdated;
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
    public String getMetadataUri() {
        return metadataUri;
    }

    private void setMetadataUri(String metadataUri) {
        this.metadataUri = metadataUri;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }

    private void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }

    private void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public Date getLastUpdated() {
        return lastUpdated;
    }

    private void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}