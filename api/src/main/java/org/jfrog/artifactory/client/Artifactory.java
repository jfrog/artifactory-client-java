package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * @author jbaruch
 * @since 25/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Artifactory extends ApiInterface, Closeable {

    String API_BASE = "/api";

    String getUri() throws MalformedURLException;

    String getContextName() throws MalformedURLException;

    String getUsername();

    String getUserAgent();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();

    Security security();

    Storage storage();

    Plugins plugins();

    ArtifactorySystem system();

    ArtifactoryResponse restCall(ArtifactoryRequest artifactoryRequest) throws IOException;

    InputStream getInputStream(String path) throws IOException;

    void close();
}
