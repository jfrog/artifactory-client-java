package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.MissionControl;

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

    <T> T restCall(ArtifactoryRequest request);

    MissionControl missionControl();

    void close();
}
