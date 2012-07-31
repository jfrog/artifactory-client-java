package org.artifactory.client.model.builder;

/**
 * @author jbaruch
 * @since 31/07/12
 */
abstract class RepositoryBuilderBase<T extends RepositoryBuilderBase> {

    private String description
    private String excludesPattern
    private String includesPattern = '**/*'
    private String key
    private String notes
    private String repoLayoutRef


    T description(String description) {
        this.description = description
        this as T
    }

    T excludesPattern(String excludesPattern) {
        this.excludesPattern = excludesPattern
        this as T
    }

    T includesPattern(String includesPattern) {
        this.includesPattern = includesPattern
        this as T
    }

    T key(String key) {
        this.key = key
        this as T
    }

    T notes(String notes) {
        this.notes = notes
        this as T
    }

    T repoLayoutRef(String repoLayoutRef) {
        this.repoLayoutRef = repoLayoutRef
        this as T
    }


}
