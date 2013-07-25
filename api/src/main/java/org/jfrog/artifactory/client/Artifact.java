package org.jfrog.artifactory.client;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface Artifact<T extends Artifact> {
    T withProperty(String name, Object... values);

    T withProperty(String name, Object value);
}
