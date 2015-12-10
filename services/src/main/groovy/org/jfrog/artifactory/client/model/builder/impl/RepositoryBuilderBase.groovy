package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.RepositoryType
import org.jfrog.artifactory.client.model.builder.RepositoryBuilder;

/**
 * @author jbaruch
 * @since 31/07/12
 */
abstract class RepositoryBuilderBase<B extends RepositoryBuilder, R extends Repository> implements RepositoryBuilder<B, R> {
    protected String description
    protected String excludesPattern
    protected String includesPattern = '**/*'
    protected String key
    protected String notes
    protected String repoLayoutRef
    protected PackageType packageType
    protected boolean enableNuGetSupport = false
    protected boolean enableGemsSupport = false
    protected boolean enableNpmSupport = false
    protected boolean enableVagrantSupport = false
    protected boolean enableBowerSupport = false
    protected boolean enableGitLfsSupport = false
    protected boolean enableDebianSupport = false
    protected boolean enableDockerSupport = false
    protected boolean enablePypiSupport = false
    protected boolean debianTrivialLayout = false

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
    B excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this as B
    }

    @Override
    B includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this as B
    }

    @Override
    B key(String key) {
        this.key = key
        this as B
    }

    @Override
    B notes(String notes) {
        this.notes = notes
        this as B
    }

    @Override
    B repoLayoutRef(String repoLayoutRef) {
        this.repoLayoutRef = repoLayoutRef
        this as B
    }

    @Override
    B packageType(PackageType packageType) {
        this.packageType = packageType
        this as B
    }

    @Override
    B enableNuGetSupport(boolean enableNuGetSupport) {
        this.enableNuGetSupport = enableNuGetSupport
        this as B
    }

    @Override
    B enableGemsSupport(boolean enableGemsSupport) {
        this.enableGemsSupport = enableGemsSupport
        this as B
    }

    @Override
    B enableNpmSupport(boolean enableNpmSupport) {
        this.enableNpmSupport = enableNpmSupport
        this as B
    }

    @Override
    B enableVagrantSupport(boolean enableVagrantSupport) {
        this.enableVagrantSupport = enableVagrantSupport
        this as B
    }

    @Override
    B enableBowerSupport(boolean enableBowerSupport) {
        this.enableBowerSupport = enableBowerSupport
        this as B
    }

    @Override
    B enableGitLfsSupport(boolean enableGitLfsSupport) {
        this.enableGitLfsSupport = enableGitLfsSupport
        this as B
    }

    @Override
    B enableDebianSupport(boolean enableDebianSupport) {
        this.enableDebianSupport = enableDebianSupport
        this as B
    }

    @Override
    B enablePypiSupport(boolean enablePypiSupport) {
        this.enablePypiSupport = enablePypiSupport
        this as B
    }

    @Override
    B enableDockerSupport(boolean enableDockerSupport) {
        this.enableDockerSupport = enableDockerSupport
        this as B
    }

    @Override
    B debianTrivialLayout(boolean debianTrivialLayout) {
        this.debianTrivialLayout = debianTrivialLayout
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
        if (this.packageType != null && !supportedTypes.contains(this.packageType)) {
            throw new IllegalArgumentException("Package type '${packageType}' is not supported in $repositoryType repositories");
        }
    }
}
