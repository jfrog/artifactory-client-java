package org.jfrog.artifactory.client.model.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RemoteRepoChecksumPolicyType {
    @Override
    String toString();
}
