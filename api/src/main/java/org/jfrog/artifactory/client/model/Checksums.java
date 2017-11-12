package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 01/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Checksums {
    String getMd5();

    String getSha1();

    String getSha256();
}
