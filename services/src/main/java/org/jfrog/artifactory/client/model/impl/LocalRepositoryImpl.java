package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.LocalRepository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public class LocalRepositoryImpl extends NonVirtualRepositoryBase implements LocalRepository {

    private LocalRepositoryImpl() {
        repoLayoutRef = MAVEN_2_REPO_LAYOUT;
    }

    public LocalRepositoryImpl(String key, RepositorySettings settings, XraySettings xraySettings,
        String description, String excludesPattern, String includesPattern, String notes,
        boolean blackedOut,
        List<String> propertySets,
        String repoLayoutRef,
        boolean archiveBrowsingEnabled,
        Map<String, Object> customProperties) {

        super(key, settings, xraySettings, description, excludesPattern, includesPattern, notes, blackedOut,
            propertySets, repoLayoutRef, archiveBrowsingEnabled, customProperties);
    }

    @Override
    public RepositoryTypeImpl getRclass() {
        return RepositoryTypeImpl.LOCAL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "LocalRepositoryImpl{" +
                '}';
    }
}
