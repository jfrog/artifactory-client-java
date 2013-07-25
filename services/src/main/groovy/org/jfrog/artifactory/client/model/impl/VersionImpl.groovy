package org.jfrog.artifactory.client.model.impl;

import groovy.transform.Canonical;
import org.jfrog.artifactory.client.model.Version
/**
 * @author jryan
 * @since 7/9/13
 */
@Canonical
public class VersionImpl implements Version {

    String version
    String revision
    List<String> addons
    String license

    VersionImpl() {
        addons = []
    }

    public VersionImpl(String version, String revision, String license, List<String> addons) {
        this.version = version;
        this.revision = revision;
        this.license = license;
        this.addons = addons;
    }

}
