package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 22/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepoPath {

    String getRepoKey();
    String getItemPath();
}
