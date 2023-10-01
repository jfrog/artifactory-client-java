package org.jfrog.artifactory.client.impl;

import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.DownloadableArtifact;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
        String uri = generateUriWithParams();
        return artifactory.getInputStream(uri);
    }

    public InputStream doDownloadWithHeaders(Map<String, String> headers) throws IOException {
        String uri = generateUriWithParams();
        return artifactory.getInputStreamWithHeaders(uri, headers);
    }

    private String generateUriWithParams() {
        String params = parseParams(props, "=") + parseParams(mandatoryProps, "+=");
        if (params.length() > 0) {
            params = ";" + params;
        }
        return String.format("/%s/%s%s", repo, path, params);
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