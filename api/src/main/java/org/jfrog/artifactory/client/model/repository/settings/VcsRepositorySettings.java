package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface VcsRepositorySettings extends RepositorySettings {

    // ** remote ** //

    VcsGitProvider getVcsGitProvider();

    VcsType getVcsType();

    Integer getMaxUniqueSnapshots();

    String getVcsGitDownloadUrl();

    Boolean getListRemoteFolderItems();

}
