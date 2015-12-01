package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.MissionControl;

/**
 * @author jbaruch
 * @since 25/07/12
 */
public interface Artifactory extends ApiInterface {

    static final String API_BASE = "/api";

    String getUri();

    String getContextName();

    String getUsername();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();

    Security security();

    Storage storage();

    Plugins plugins();

    ArtifactorySystem system();

    <T> T restCall(ArtifactoryRequest request);

    MissionControl missionControl();

    MissionControl missionControl(String missionControlAuthToken);

    String getMissionControlAuthToken();

    void close();
}
