package org.jfrog.artifactory.client.model.repository.settings;

import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsGitProvider;
import org.jfrog.artifactory.client.model.repository.settings.vcs.VcsType;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface VcsRepositorySettings extends RepositorySettings {

    // ** remote ** //

    VcsGitProvider getVcsGitProvider();

    VcsType getVcsType();

    Integer getMaxUniqueSnapshots();

    String getVcsGitDownloadUrl();

    Boolean getListRemoteFolderItems();

}
