package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Lior Hasson
 */
public abstract class AbstractRepositorySettings implements RepositorySettings {
    protected String repoLayoutRef;

    public String getRepoLayout() {
        return this.repoLayoutRef;
    }

    public void setRepoLayout(String repoLayout) {
        this.repoLayoutRef = repoLayout;
    }
}
