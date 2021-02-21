package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author yahavi
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public interface BuildNumber {
    String getUri();

    String getStarted();
}
