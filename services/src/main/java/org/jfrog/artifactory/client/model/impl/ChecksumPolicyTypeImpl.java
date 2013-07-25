package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jfrog.artifactory.client.model.ChecksumPolicyType;

/**
* @author jbaruch
* @since 13/08/12
*/
public enum ChecksumPolicyTypeImpl implements ChecksumPolicyType {
    client_checksums("client-checksums"), server_generated_checksums("server-generated-checksums");

    private String name;

    ChecksumPolicyTypeImpl(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
