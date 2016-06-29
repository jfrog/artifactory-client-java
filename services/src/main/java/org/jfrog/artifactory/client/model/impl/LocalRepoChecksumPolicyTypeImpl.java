package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jfrog.artifactory.client.model.repository.LocalRepoChecksumPolicyType;

/**
* @author jbaruch
* @since 13/08/12
*/
public enum LocalRepoChecksumPolicyTypeImpl implements LocalRepoChecksumPolicyType {
    client_checksums("client-checksums"), server_generated_checksums("server-generated-checksums");

    private String name;

    LocalRepoChecksumPolicyTypeImpl(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
