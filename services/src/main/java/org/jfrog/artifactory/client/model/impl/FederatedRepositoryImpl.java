package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.jfrog.artifactory.client.model.FederatedMember;
import org.jfrog.artifactory.client.model.FederatedRepository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

import java.util.List;
import java.util.Map;

/**
 * @author Siva Singaravelan
 * @since 01/07/2022
 */
public class FederatedRepositoryImpl extends NonVirtualRepositoryBase implements FederatedRepository {

    private FederatedRepositoryImpl() {
        repoLayoutRef = MAVEN_2_REPO_LAYOUT;
    }

    private List<FederatedMember> members;

    protected FederatedRepositoryImpl(String key, List<FederatedMember>  members, RepositorySettings settings, XraySettings xraySettings,
                                      String description, String excludesPattern, String includesPattern, String notes,
                                      boolean blackedOut,
                                      List<String> propertySets,
                                      String repoLayoutRef,
                                      boolean archiveBrowsingEnabled,
                                      Map<String, Object> customProperties) {

        super(key, settings, xraySettings, description, excludesPattern, includesPattern, notes, blackedOut,
            propertySets, repoLayoutRef, archiveBrowsingEnabled, customProperties);

        this.members = members;
    }

    public RepositoryTypeImpl getRclass() {
        return RepositoryTypeImpl.FEDERATED;
    }

    @Override
    public String toString() {
        return "FederatedRepositoryImpl{" +
                '}';
    }

    public List<FederatedMember> getMembers() {
        return members;
    }
    @JsonCreator
    private void setMembers(List<FederatedMember> members) {
        this.members = members;
    }
}
