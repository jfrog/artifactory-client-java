package org.artifactory.client;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public interface Artifactory {

    String getUri();

    String getContextName();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();
}
