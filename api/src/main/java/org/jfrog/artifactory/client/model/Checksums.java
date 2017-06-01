package org.jfrog.artifactory.client.model;

/**
 * @author jbaruch
 * @since 01/08/12
 */
public interface Checksums {
    String getMd5();

    String getSha1();

    String getSha256();
}
