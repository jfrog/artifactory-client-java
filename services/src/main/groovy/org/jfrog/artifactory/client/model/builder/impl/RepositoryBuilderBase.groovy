package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RepositoryBuilder
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.repository.settings.XraySettings

/**
 * @author jbaruch
 * @since 31/07/12
 */
abstract class RepositoryBuilderBase<B extends RepositoryBuilder, R extends Repository> implements RepositoryBuilder<B, R> {
    protected String description = ''
    protected String excludesPattern = ''
    protected String includesPattern = '**/*'
    protected String key
    protected String notes = ''
    protected String repoLayoutRef
    protected RepositorySettings settings
    protected XraySettings xraySettings
    protected Map customProperties

    public final Set<PackageType> supportedTypes

    RepositoryBuilderBase(Set<PackageType> supportedTypes) {
        this.supportedTypes = supportedTypes
    }

    @Override
    B description(String description) {
        this.description = description
        this as B
    }

    @Override
    String getDescription() {
        description
    }

    @Override
    B excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this as B
    }

    @Override
    String getExcludesPattern() {
        excludesPattern
    }

    @Override
    B includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this as B
    }

    @Override
    String getIncludesPattern() {
        includesPattern
    }

    @Override
    B key(String key) {
        this.key = key
        this as B
    }

    @Override
    String getKey() {
        key
    }

    @Override
    B notes(String notes) {
        this.notes = notes
        this as B
    }

    @Override
    String getNotes() {
        notes
    }

    @Override
    B repoLayoutRef(String repoLayoutRef) {
        this.repoLayoutRef = repoLayoutRef
        this as B
    }

    @Override
    String getRepoLayoutRef() {
        repoLayoutRef
    }

    @Override
    B repositorySettings(RepositorySettings settings) {
        this.settings = settings
        this as B
    }

    @Override
    RepositorySettings getRepositorySettings() {
        settings
    }

    @Override
    B xraySettings(XraySettings xraySettings) {
        this.xraySettings = xraySettings
        this as B
    }

    @Override
    XraySettings getXraySettings() {
        xraySettings
    }

    @Override
    B customProperties(Map customProperties) {
        this.customProperties = customProperties
        this as B
    }

    abstract RepositoryType getRepositoryType()

    @Override
    void validate() {
        if (!key) {
            throw new IllegalArgumentException("The 'key' property is mandatory.")
        }
        if (key.length() > 64) {
            throw new IllegalArgumentException("The 'key' value is limitted to 64 characters.")
        }
        if (this.settings != null && !settings.packageType.isCustom()
            && !supportedTypes.contains(settings.packageType)) {
            throw new IllegalArgumentException("Package type '${settings.packageType}' is not supported in $repositoryType repositories");
        }
    }
}
