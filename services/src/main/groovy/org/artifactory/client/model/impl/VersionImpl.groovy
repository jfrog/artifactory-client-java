package org.artifactory.client.model.impl;

import groovy.transform.Canonical;
import org.artifactory.client.model.Plugin;
import org.artifactory.client.model.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

/**
 * @author jryan
 * @since 7/9/13
 */
@Canonical
public class VersionImpl implements Version {

    String version
    String revision
    List<String> addons

    public VersionImpl(String version, String revision, List<String> addons) {
        this.version = version;
        this.revision = revision;
        this.addons = addons;
    }

}
