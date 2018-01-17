package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Lior Hasson
 */
public abstract class AbstractRepositorySettings implements RepositorySettings {
    private String repoLayout;

    public AbstractRepositorySettings(String repoLayout) {
        this.repoLayout = repoLayout;
    }

    public String getRepoLayout() {
        return this.repoLayout;
    }

    public void setRepoLayout(String repoLayout) {
        this.repoLayout = repoLayout;
    }

    @Override
    public int hashCode() {
        return repoLayout != null ? repoLayout.hashCode() : 0;
    }
}
