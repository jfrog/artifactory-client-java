package org.jfrog.artifactory.client.impl;

import org.apache.http.entity.ContentType;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.Builds;
import org.jfrog.artifactory.client.impl.util.Util;
import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.BuildPromotionRequest;
import org.jfrog.artifactory.client.model.BuildPromotionResponse;
import org.jfrog.artifactory.client.model.BuildRuns;
import org.jfrog.artifactory.client.model.impl.AllBuildsImpl;
import org.jfrog.artifactory.client.model.impl.BuildPromotionResponseImpl;
import org.jfrog.artifactory.client.model.impl.BuildRunsImpl;
import org.jfrog.build.api.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yahavi
 **/
public class BuildsImpl implements Builds {
    private final Artifactory artifactory;
    private final String baseApiPath;

    public BuildsImpl(Artifactory artifactory, String baseApiPath) {
        this.artifactory = artifactory;
        this.baseApiPath = baseApiPath;
    }

    @Override
    public AllBuilds getAllBuilds() throws IOException {
        return artifactory.get(getBuilderApi(), AllBuildsImpl.class, AllBuilds.class);
    }

    @Override
    public BuildRuns getBuildRuns(String buildName) throws IOException {
        return artifactory.get(getBuilderApi() + buildName, BuildRunsImpl.class, BuildRuns.class);
    }

    @Override
    public void uploadBuild(Build build) throws IOException {
        uploadBuild(build, null);
    }

    @Override
    public void uploadBuild(Build build, String project) throws IOException {
        String apiPath = getBuilderApi();
        if (project != null && !project.isEmpty()) {
            apiPath += "?project=" + project;
        }
        
        Map<String, String> headers = new HashMap<>();
        artifactory.put(apiPath, ContentType.APPLICATION_JSON,
                Util.getStringFromObject(build), headers, null, -1,
                String.class, null);
    }

    @Override
    public BuildPromotionResponse promoteBuild(String buildName, String buildNumber, BuildPromotionRequest promotionRequest) throws IOException {
        return promoteBuild(buildName, buildNumber, promotionRequest, null);
    }

    @Override
    public BuildPromotionResponse promoteBuild(String buildName, String buildNumber, BuildPromotionRequest promotionRequest, String project) throws IOException {
        String apiPath = getBuilderApi() + "promote/" + buildName + "/" + buildNumber;
        if (project != null && !project.isEmpty()) {
            apiPath += "?project=" + project;
        }
        
        Map<String, String> headers = new HashMap<>();
        return artifactory.post(apiPath, ContentType.APPLICATION_JSON,
                Util.getStringFromObject(promotionRequest), headers,
                BuildPromotionResponseImpl.class, BuildPromotionResponse.class);
    }

    public String getBuilderApi() {
        return baseApiPath + "/build/";
    }
}
