package org.jfrog.artifactory.client.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.jfrog.artifactory.client.Artifact;

/**
 * Created by eyalb on 21/06/2018.
 */
public abstract class ArtifactBase<T extends Artifact> implements Artifact<T> {
    protected String repo;
    protected HashMap<String, Object[]> props = new HashMap<>();

    protected ArtifactBase() {
    }

    protected ArtifactBase(String repo) {
        this.repo = repo;
    }

    public Artifact withProperty(String name, Object... values) {
        props.put(name, values);
        return this;
    }

    public Artifact withProperty(String name, Object value) {
        props.put(name, new Object[]{value});
        return this;
    }

    protected String parseParams(HashMap<String, Object[]> props, String delimiter) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object[]> entry : props.entrySet()) {
            String urlEncodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
            for (Object value : entry.getValue()) {
                String urlEncodedValue = URLEncoder.encode(String.valueOf(value), StandardCharsets.UTF_8.toString());
                sb.append(";").append(urlEncodedKey).append(delimiter).append(urlEncodedValue);
            }
        }
        return sb.toString();
    }
}
