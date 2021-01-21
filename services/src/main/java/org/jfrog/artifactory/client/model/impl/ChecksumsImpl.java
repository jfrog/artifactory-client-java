package org.jfrog.artifactory.client.model.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jfrog.artifactory.client.model.Checksums;

/**
 * @author jbaruch
 * @since 01/08/12
 */
public class ChecksumsImpl implements Checksums {
    private String md5;
    private String sha1;
    private String sha256;

    private ChecksumsImpl() {
    }

    @Override
    public String getMd5() {
        return md5;
    }

    @Override
    public String getSha1() {
        return sha1;
    }

    public String getSha256() {
        return sha256;
    }

    private void setMd5(String md5) {
        this.md5 = md5;
    }

    private void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChecksumsImpl checksums = (ChecksumsImpl) o;

        return new EqualsBuilder()
                .append(md5, checksums.md5)
                .append(sha1, checksums.sha1)
                .append(sha256, checksums.sha256)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(md5)
                .append(sha1)
                .append(sha256)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ChecksumsImpl{" +
                "md5='" + md5 + '\'' +
                ", sha1='" + sha1 + '\'' +
                ", sha256='" + sha256 + '\'' +
                '}';
    }
}
