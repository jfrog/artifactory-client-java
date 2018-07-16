package org.jfrog.artifactory.client.impl.util;

import org.jfrog.artifactory.client.httpClient.http.HttpBuilder;
import org.jfrog.artifactory.client.httpClient.http.auth.PreemptiveAuthInterceptor;

/**
 * @author Alexei Vainshtein
 */
public class ArtifactoryHttpClient extends HttpBuilder {

    public ArtifactoryHttpClient() {
        builder.addInterceptorFirst(new PreemptiveAuthInterceptor());
    }
}
