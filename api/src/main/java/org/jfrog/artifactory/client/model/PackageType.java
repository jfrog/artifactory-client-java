package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Serhii Pichkurov (serhiip@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PackageType {
    String name();

    boolean isCustom();
}
