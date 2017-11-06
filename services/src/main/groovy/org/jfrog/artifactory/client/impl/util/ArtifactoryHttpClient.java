package org.jfrog.artifactory.client.impl.util;

import org.jfrog.client.http.HttpBuilder;
import org.jfrog.client.http.auth.PreemptiveAuthInterceptor;

/**
 * @author Alexei Vainshtein
 */
public class ArtifactoryHttpClient extends HttpBuilder {

    public ArtifactoryHttpClient() {
        builder.addInterceptorFirst(new PreemptiveAuthInterceptor());
    }
}
