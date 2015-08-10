package org.jfrog.artifactory.client.model;

import org.jfrog.artifactory.client.*;

import java.util.Map;

/**
 * Created by Eyal BM on 30/07/2015.
 */
public interface MissionControl {

    public static final String MC_API_BASE = "/mc";

    public void createConnection(String missionControlExtUrl, String token);

    public void updateConnection(String missionControlExtUrl, String newToken);

    public Repositories repositories();

    public RepositoryHandle repository(String repo);

    public Searches searches();

    public Security security();

    public Plugins plugins();

    public ArtifactorySystem system();
}
