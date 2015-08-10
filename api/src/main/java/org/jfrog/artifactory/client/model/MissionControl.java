package org.jfrog.artifactory.client.model;

import org.jfrog.artifactory.client.*;

import java.util.Map;

/**
 * Created by Eyal BM on 30/07/2015.
 */
public interface MissionControl {

    public static final String MC_API_BASE = "/mc/";

    public void createConnection(String missionControlExtUrl, String token);

    public void updateConnection(String missionControlExtUrl, String oldToken, String newToken);

    public Map<String, String> getRequestHeaders();

    Repositories repositories();

    RepositoryHandle repository(String repo);

    Searches searches();

    Security security();

    Plugins plugins();

    ArtifactorySystem system();
}
