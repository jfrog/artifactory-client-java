package org.jfrog.artifactory.client.impl

import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.ArtifactorySystem
import org.jfrog.artifactory.client.Plugins
import org.jfrog.artifactory.client.Repositories
import org.jfrog.artifactory.client.RepositoryHandle
import org.jfrog.artifactory.client.Searches
import org.jfrog.artifactory.client.Security;
import org.jfrog.artifactory.client.model.MissionControl;

/**
 * Created by Eyal BM on 30/07/2015.
 */
public class MissionControlImpl implements MissionControl {

    private ArtifactoryImpl artifactory
    private String authToken

    MissionControlImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    public void createConnection(String missionControlExtUrl, String token) {
        createOrUpdateConnection(missionControlExtUrl, null, token)
    }

    @Override
    public void updateConnection(String missionControlExtUrl, String oldToken, String newToken) {
        createOrUpdateConnection(missionControlExtUrl, oldToken, newToken)
    }

    private void createOrUpdateConnection(String missionControlExtUrl, String oldToken, String newToken) {
        Map headers = [:]
        if (oldToken) {
            headers = getHeaders(oldToken)
        }

        Map body = ["url": missionControlExtUrl, "token": newToken]

        artifactory.post("${MISSION_CONTROL_API}setupmc",
                [:], ContentType.JSON, null, ContentType.JSON, body, headers)

        authToken = newToken
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        return getHeaders(null)
    }

    private Map<String, String> getHeaders(String token) {
        String t = token ? token : authToken
        return ['Authorization-token': t]
    }

    @Override
    Repositories repositories() {
        new RepositoriesImpl(artifactory, MC_API_BASE)
    }

    @Override
    RepositoryHandle repository(String repo) {
        if (!repo){
            throw new IllegalArgumentException('Repository name is required')
        }
        new RepositoriesImpl(artifactory, MC_API_BASE).repository(repo)
    }

    @Override
    Searches searches() {
        return new SearchesImpl(artifactory, MC_API_BASE)
    }

    @Override
    Security security() {
        new SecurityImpl(artifactory, MC_API_BASE)
    }

    @Override
    Plugins plugins() {
        new PluginsImpl(artifactory, MC_API_BASE)
    }

    @Override
    ArtifactorySystem system() {
        new ArtifactorySystemImpl(artifactory, API_BASE)
    }
}
