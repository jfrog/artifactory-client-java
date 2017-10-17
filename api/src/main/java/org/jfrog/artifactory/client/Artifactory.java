package org.jfrog.artifactory.client;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public interface Artifactory extends ApiInterface {

    static final String API_BASE = "/api";

    String getUri();

    String getContextName();

    String getUsername();

    String getUserAgent();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();

    Security security();

    Storage storage();

    Plugins plugins();

    ArtifactorySystem system();

    <T> T restCall(ArtifactoryRequest request, Class responseClass);

    <T> T restCall(ArtifactoryRequest request);

    void close();
}
