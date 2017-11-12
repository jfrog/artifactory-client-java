package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Aviad Shikloshi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface BinariesSummary {

    String getBinariesCount();

    String getBinariesSize();

    String getArtifactsSize();

    String getArtifactsCount();

    String getOptimization();

    String getItemsCount();

}
