package org.artifactory.client.model.impl;

import org.artifactory.client.model.Checksums;

/**
 * @author jbaruch
 * @since 01/08/12
 */
public class ChecksumsImpl implements Checksums {
    private String md5;
    private String sha1;

    private ChecksumsImpl() {
    }

    @Override
    public String getMd5() {
        return md5;
    }

    private void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String getSha1() {
        return sha1;
    }

    private void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecksumsImpl checksums = (ChecksumsImpl) o;

        if (md5 != null ? !md5.equals(checksums.md5) : checksums.md5 != null) return false;
        if (sha1 != null ? !sha1.equals(checksums.sha1) : checksums.sha1 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = md5 != null ? md5.hashCode() : 0;
        result = 31 * result + (sha1 != null ? sha1.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChecksumsImpl{" +
                "md5='" + md5 + '\'' +
                ", sha1='" + sha1 + '\'' +
                '}';
    }
}
