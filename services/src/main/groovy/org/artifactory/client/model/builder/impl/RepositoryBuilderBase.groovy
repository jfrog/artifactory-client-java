package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.Repository
import org.artifactory.client.model.builder.RepositoryBuilder;

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


    B description(String description) {
        this.description = description
        this as B
    }

    B excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this as B
    }

    B includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this as B
    }

    B key(String key) {
        this.key = key
        this as B
    }

    B notes(String notes) {
        this.notes = notes
        this as B
    }

    B repoLayoutRef(String repoLayoutRef) {
        this.repoLayoutRef = repoLayoutRef
        this as B
    }

    B enableNuGetSupport(boolean enableNuGetSupport) {
        this.enableNuGetSupport = enableNuGetSupport
        this as B
    }
}
