package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.jfrog.artifactory.client.model.repository.RemoteRepoChecksumPolicyType;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public enum RemoteRepoChecksumPolicyTypeImpl implements RemoteRepoChecksumPolicyType {
    generate_if_absent("generate-if-absent"), fail("fail"), ignore_and_generate("ignore-and-generate"), pass_thru("pass-thru");

    private String name;

    RemoteRepoChecksumPolicyTypeImpl(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }

    @JsonCreator
    public static RemoteRepoChecksumPolicyTypeImpl parseValue(String value) {
        if (value == null || value.isEmpty()) {
            return generate_if_absent;
        }
        // The enum values include dashes (-), where the enum names include underscores (_).
        // We therefore replace all dashes with underscores.
        value = value.replaceAll("-", "_");
        return valueOf(value);
    }
}
