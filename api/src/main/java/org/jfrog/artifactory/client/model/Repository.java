package org.jfrog.artifactory.client.model;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Repository {
    String MAVEN_2_REPO_LAYOUT = "maven-2-default";

    String getKey();

    RepositoryType getRclass();

    String getDescription();

    String getNotes();

    String getIncludesPattern();

    String getExcludesPattern();

    String getRepoLayoutRef();

    boolean isEnableNuGetSupport();

    boolean isEnableGemsSupport();

    boolean isEnableNpmSupport();

    boolean isEnableVagrantSupport();

    boolean isEnableBowerSupport();

    boolean isEnableGitLfsSupport();

    boolean isEnablePypiSupport();

    boolean isEnableDockerSupport();

    boolean isEnableDebianSupport();

    boolean isDebianTrivialLayout();
}
