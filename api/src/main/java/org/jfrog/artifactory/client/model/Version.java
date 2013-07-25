package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * @author jryan
 * @since 7/9/13
 */
public interface Version {

    String getVersion();

    String getRevision();

    String getLicense();

    List<String> getAddons();
}
