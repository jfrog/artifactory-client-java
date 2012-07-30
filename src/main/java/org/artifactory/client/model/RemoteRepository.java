package org.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface RemoteRepository extends Repository, NonVirtualRepository {
    String getUrl();

    String getUsername();

    String getPassword();

    String getProxy();

    RemoteRepoChecksumPolicyType getRemoteRepoChecksumPolicyType();

    boolean isHardFail();

    boolean isOffline();

    boolean isStoreArtifactsLocally();

    int getSocketTimeoutMillis();

    String getLocalAddress();

    int getRetrievalCachePeriodSecs();

    int getMissedRetrievalCachePeriodSecs();

    int getFailedRetrievalCachePeriodSecs();

    boolean isUnusedArtifactsCleanupEnabled();

    int getUnusedArtifactsCleanupPeriodHours();

    boolean isFetchJarsEagerly();

    boolean isFetchSourcesEagerly();

    boolean isShareConfiguration();

    boolean isSynchronizeProperties();

    String getRepoLayoutRef();

    public enum RemoteRepoChecksumPolicyType {
        generate_if_absent("generate-if-absent"), fail("fail"), ignore_and_generate("ignore-and-generate"), pass_thru("pass-thru");

        private String name;

        private RemoteRepoChecksumPolicyType(String name) {
            this.name = name;
        }

        @Override
        @JsonValue
        public String toString() {
            return name;
        }

        @JsonCreator
        public static RemoteRepoChecksumPolicyType parseValue(String value) {
            if (value == null || value.isEmpty()) {
                return generate_if_absent;
            }
            return valueOf(value.replaceAll("_", "-"));
        }
    }
}
