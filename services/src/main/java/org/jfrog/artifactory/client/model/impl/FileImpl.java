package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Checksums;
import org.jfrog.artifactory.client.model.File;

import java.util.Date;

/**
 * @author jbaruch
 * @since 01/08/12
 */
public class FileImpl extends ItemImpl implements File {

    private String downloadUri;
    private Date created;
    private String createdBy;
    private long size;
    private String mimeType;
    private ChecksumsImpl checksums;
    private ChecksumsImpl originalChecksums;
    private String remoteUrl;

    protected FileImpl() {
        super();
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    private void setCreated(Date created) {
        this.created = new Date(created.getTime());
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    private void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getDownloadUri() {
        return downloadUri;
    }

    private void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    private void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public long getSize() {
        return size;
    }

    private void setSize(long size) {
        this.size = size;
    }

    @Override
    public Checksums getChecksums() {
        return checksums;
    }

    private void setChecksums(ChecksumsImpl checksums) {
        this.checksums = checksums;
    }

    @Override
    public Checksums getOriginalChecksums() {
        return originalChecksums;
    }

    private void setOriginalChecksums(ChecksumsImpl originalChecksums) {
        this.originalChecksums = originalChecksums;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    @Override
    public String toString() {
        return "FileImpl{" +
                "checksums=" + checksums +
                ", downloadUri='" + downloadUri + '\'' +
                ", repo='" + getRepo() + '\'' +
                ", path='" + getPath() + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", size=" + size +
                ", mimeType='" + mimeType + '\'' +
                ", originalChecksums=" + originalChecksums +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }
}
