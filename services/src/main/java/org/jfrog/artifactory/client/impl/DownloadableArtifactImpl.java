package org.jfrog.artifactory.client.impl;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.DownloadableArtifact;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by eyalb on 21/06/2018.
 */
public class DownloadableArtifactImpl extends ArtifactBase<DownloadableArtifact> implements DownloadableArtifact {

    private HashMap<String, Object[]> mandatoryProps = new HashMap<>();
    private Artifactory artifactory;
    private String path;

    DownloadableArtifactImpl(String repo, String path, Artifactory artifactory) {
        super(repo);
        this.artifactory = artifactory;
        this.path = path;
    }

    public InputStream doDownload() throws IOException {
        String params = parseParams(props, "=") + parseParams(mandatoryProps, "+=");
        if (params.length() > 0) {
            params = ";" + params;
        }
        String uri = String.format("/%s/%s%s", repo, path, params);
        return artifactory.getInputStream(uri);
    }

    public DownloadableArtifact withProperty(String name, Object... values) {
        super.withProperty(name, values);
        return this;
    }

    public DownloadableArtifact withProperty(String name, Object value) {
        super.withProperty(name, value);
        return this;
    }

    public DownloadableArtifact withMandatoryProperty(String name, Object... values) {
        mandatoryProps.put(name, values);
        return this;
    }

    public DownloadableArtifact withMandatoryProperty(String name, Object value) {
        mandatoryProps.put(name, new Object[]{value});
        return this;
    }
}