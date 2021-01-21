package org.jfrog.artifactory.client.model.impl;

import org.apache.commons.lang3.StringUtils;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.RepositoryType;
import org.jfrog.artifactory.client.model.builder.RepositoryBuilder;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

import java.util.Map;
import java.util.Set;

/**
 * @author jbaruch
 * @since 31/07/12
 */
public abstract class RepositoryBuilderBase<B extends RepositoryBuilder, R extends Repository> implements RepositoryBuilder<B, R> {
    protected String description = "";
    protected String excludesPattern = "";
    protected String includesPattern = "**/*";
    protected String key;
    protected String notes = "";
    protected String repoLayoutRef;
    protected RepositorySettings settings;
    protected XraySettings xraySettings;
    protected Map<String, Object> customProperties;

    public final Set<PackageType> supportedTypes;

    protected RepositoryBuilderBase(Set<PackageType> supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    public B description(String description) {
        this.description = description;
        return (B) this;
    }

    public String getDescription() {
        return description;
    }

    public B excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern;
        return (B) this;
    }

    public String getExcludesPattern() {
        return excludesPattern;
    }

    public B includesPattern(String includesPattern) {
        this.includesPattern = includesPattern;
        return (B) this;
    }

    public String getIncludesPattern() {
        return includesPattern;
    }

    public B key(String key) {
        this.key = key;
        return (B) this;
    }

    public String getKey() {
        return key;
    }

    public B notes(String notes) {
        this.notes = notes;
        return (B) this;
    }

    public String getNotes() {
        return notes;
    }

    public B repositorySettings(RepositorySettings settings) {
        this.settings = settings;
        return (B) this;
    }

    public RepositorySettings getRepositorySettings() {
        return settings;
    }

    public B xraySettings(XraySettings xraySettings) {
        this.xraySettings = xraySettings;
        return (B) this;
    }

    public XraySettings getXraySettings() {
        return xraySettings;
    }

    public B customProperties(Map<String, Object> customProperties) {
        this.customProperties = customProperties;
        return (B) this;
    }

    public abstract RepositoryType getRepositoryType();

    public void validate() {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("The 'key' property is mandatory.");
        }

        if (key.length() > 64) {
            throw new IllegalArgumentException("The 'key' value is limited to 64 characters.");
        }

        if (this.settings != null && !settings.getPackageType().isCustom() && !supportedTypes.contains(settings.getPackageType())) {
            throw new IllegalArgumentException("Package type \'" + String.valueOf(settings.getPackageType()) + "\' is not supported in " + String.valueOf(getRepositoryType()) + " repositories");
        }
    }

    public B repoLayoutRef(String repoLayoutRef){
        this.repoLayoutRef = repoLayoutRef;
        return (B) this;
    }

    public void setRepoLayoutFromSettings() {
        if (this.repoLayoutRef == null && settings != null) {
            this.repoLayoutRef = settings.getRepoLayout();
        }

    }
}
