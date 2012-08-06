package org.artifactory.client.model.impl;

import org.artifactory.client.model.Checksums;
import org.artifactory.client.model.File;

import java.util.Date;

/**
 * @author jbaruch
 * @since 01/08/12
 */
public class FileImpl extends ItemImpl implements File {

    private String downloadUri;
    private String repo;
    private String path;
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
    public int hashCode() {
        int result = downloadUri != null ? downloadUri.hashCode() : 0;
        result = 31 * result + (repo != null ? repo.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 31 * result + (checksums != null ? checksums.hashCode() : 0);
        result = 31 * result + (originalChecksums != null ? originalChecksums.hashCode() : 0);
        result = 31 * result + (remoteUrl != null ? remoteUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileImpl{" +
                "checksums=" + checksums +
                ", downloadUri='" + downloadUri + '\'' +
                ", repo='" + repo + '\'' +
                ", path='" + path + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", size=" + size +
                ", mimeType='" + mimeType + '\'' +
                ", originalChecksums=" + originalChecksums +
                ", remoteUrl='" + remoteUrl + '\'' +
                '}';
    }
}
