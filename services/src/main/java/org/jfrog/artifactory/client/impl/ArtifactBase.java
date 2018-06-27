package org.jfrog.artifactory.client.impl;

import org.jfrog.artifactory.client.Artifact;
import java.util.HashMap;
import java.util.Map;

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

    protected String parseParams(HashMap<String, Object[]> props, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object[]> entry : props.entrySet()) {
            for (Object value : entry.getValue()) {
                sb.append(";").append(entry.getKey()).append(delimiter).append(value);
            }
        }
        return sb.toString();
    }
}
