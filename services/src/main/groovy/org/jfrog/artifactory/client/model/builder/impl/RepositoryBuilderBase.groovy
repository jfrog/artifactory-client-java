package org.jfrog.artifactory.client.model.builder.impl

import org.jfrog.artifactory.client.model.Repository
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
    protected boolean enableNuGetSupport = false
    protected boolean enableGemsSupport = false
    protected boolean enableNpmSupport = false
    protected boolean enableDebianSupport = false
    protected boolean debianTrivialLayout = false

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
    B enableDebianSupport(boolean enableDebianSupport) {
        this.enableDebianSupport = enableDebianSupport
        this as B
    }

    @Override
    B debianTrivialLayout(boolean debianTrivialLayout) {
        this.debianTrivialLayout = debianTrivialLayout
        this as B
    }
}
