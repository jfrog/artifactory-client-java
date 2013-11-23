package org.jfrog.artifactory.client;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public interface Artifactory {

    String getUri();

    String getContextName();

    String getUsername();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();

    Security security();

    Plugins plugins();

    ArtifactorySystem system();

    void close();
}
