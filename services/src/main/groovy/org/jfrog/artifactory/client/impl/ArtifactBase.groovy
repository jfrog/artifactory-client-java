package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.Artifact

/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
abstract class ArtifactBase<T extends Artifact> implements Artifact<T> {
    protected String repo;
    protected props = [:]

    protected ArtifactBase() {

    }

    protected ArtifactBase(String repo) {
        this.repo = repo
    }

    T withProperty(String name, Object... values) {
        //for some strange reason def won't work here
        values.each {
            props[name]
        }
        props[name] = values
        this as T
    }

    T withProperty(String name, Object value) {
        props[name] = value
        this as T
    }

    protected String parseParams(Map props, String delimiter) {
        props.inject(['']) { result, key, value ->
            if (value.class.isArray()) {
                value.each {
                    result << "$key$delimiter$it"
                }
            } else {
                result << "$key$delimiter${value}"
            }
            result
        }.join(';')
    }
}
