package org.artifactory.client.model;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Repository {
    String getKey();

    RepositoryType getRclass();

    String getDescription();

    String getNotes();

    String getIncludesPattern();

    String getExcludesPattern();

    String getRepoLayoutRef();
}
