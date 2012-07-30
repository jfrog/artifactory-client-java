package org.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface LocalRepository extends Repository, NonVirtualRepository {
    ChecksumPolicyType getChecksumPolicyType();

    public enum ChecksumPolicyType {
        client_checksums("client-checksums"), server_generated_checksums("server-generated-checksums");

        private String name;

        private ChecksumPolicyType(String name) {
            this.name = name;
        }

        @Override
        @JsonValue
        public String toString() {
            return name;
        }
    }
}
