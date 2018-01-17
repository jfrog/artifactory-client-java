package org.jfrog.artifactory.client.model.impl;

import java.util.List;
import java.util.Map;

import org.jfrog.artifactory.client.model.NonVirtualRepository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

/**
 * @author jbaruch
 * @since 29/07/12
 */
public abstract class NonVirtualRepositoryBase extends RepositoryBase implements NonVirtualRepository, XraySettings {

    private boolean blackedOut;
    private List<String> propertySets;
    protected boolean archiveBrowsingEnabled;

    protected NonVirtualRepositoryBase() {
    }

    protected NonVirtualRepositoryBase(String key, RepositorySettings settings, XraySettings xraySettings,
        String description, String excludesPattern, String includesPattern,
        String notes, boolean blackedOut,
        List<String> propertySets,
        String repoLayoutRef,
        boolean archiveBrowsingEnabled,
        Map customProperties) {

        super(key, settings, xraySettings, description, excludesPattern, includesPattern, notes,
            repoLayoutRef, customProperties);

        this.blackedOut = blackedOut;
        this.propertySets = propertySets;
        this.archiveBrowsingEnabled = archiveBrowsingEnabled;
    }

    public boolean isBlackedOut() {
        return blackedOut;
    }

    private void setBlackedOut(boolean blackedOut) {
        this.blackedOut = blackedOut;
    }

    public List<String> getPropertySets() {
        return propertySets;
    }

    private void setPropertySets(List<String> propertySets) {
        this.propertySets = propertySets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NonVirtualRepositoryBase)) return false;
        if (!super.equals(o)) return false;

        NonVirtualRepositoryBase that = (NonVirtualRepositoryBase) o;

        if (blackedOut != that.blackedOut) return false;
        if (propertySets != null ? !propertySets.equals(that.propertySets) : that.propertySets != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (blackedOut ? 1 : 0);
        result = 31 * result + (propertySets != null ? propertySets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NonVirtualRepositoryBase{" +
                "blackedOut=" + blackedOut +
                ", propertySets=" + propertySets +
                '}';
    }

    public boolean isArchiveBrowsingEnabled() {
        return archiveBrowsingEnabled;
    }

    private void setArchiveBrowsingEnabled(boolean archiveBrowsingEnabled) {
        this.archiveBrowsingEnabled = archiveBrowsingEnabled;
    }

    public Boolean getXrayIndex() {
        if (xraySettings != null) {
            return xraySettings.getXrayIndex();
        }
        return null;
    }

    public void setXrayIndex(Boolean xrayIndex) {
        if (xraySettings != null) {
            xraySettings.setXrayIndex(xrayIndex);
        }
    }

    public Boolean getBlockXrayUnscannedArtifacts() {
        if (xraySettings != null) {
            return xraySettings.getBlockXrayUnscannedArtifacts();
        }
        return null;
    }

    public void setBlockXrayUnscannedArtifacts(Boolean blockXrayUnscannedArtifacts) {
        if (xraySettings != null) {
            xraySettings.setBlockXrayUnscannedArtifacts(blockXrayUnscannedArtifacts);
        }
    }

    public String getXrayMinimumBlockedSeverity() {
        if (xraySettings != null) {
            return xraySettings.getXrayMinimumBlockedSeverity();
        }
        return null;
    }

    public void setXrayMinimumBlockedSeverity(String xrayMinimumBlockedSeverity) {
        if (xraySettings != null) {
            xraySettings.setXrayMinimumBlockedSeverity(xrayMinimumBlockedSeverity);
        }
    }
}
