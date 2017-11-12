package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * @author jryan
 * @since 7/9/13
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Version {

    String getVersion();

    String getRevision();

    String getLicense();

    List<String> getAddons();
}
