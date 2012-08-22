package org.artifactory.client.impl

import org.artifactory.client.Artifact

/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
abstract class ArtifactBase<T extends Artifact> implements Artifact<T> {
    protected repo;
    protected props = [:]

    protected ArtifactBase() {

    }

    protected ArtifactBase(repo) {
        this.repo = repo
    }

    T withProperty(String name, Object... values) {
        //for some strange reason def won't work here
        props[name] = values.join(',')
        this as T
    }

    T withProperty(String name, Object value) {
        props[name] = value
        this as T
    }

    protected String parseParams(Map props, String delimiter) {
        props.inject(['']) {result, entry ->
            result << "$entry.key$delimiter$entry.value"
        }.join(';')
    }
}
