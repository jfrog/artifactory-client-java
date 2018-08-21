package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 12/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Artifact<T extends Artifact> {
    Artifact withProperty(String name, Object... values);

    Artifact withProperty(String name, Object value);
}
