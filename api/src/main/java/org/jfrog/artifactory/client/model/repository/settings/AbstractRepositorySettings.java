package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Lior Hasson
 */
public abstract class AbstractRepositorySettings implements RepositorySettings {
    protected String repoLayoutRef;

    public String getRepoLayoutRef() {
        return this.repoLayoutRef;
    }

    public void setRepoLayoutRef(String repoLayout) {
        this.repoLayoutRef = repoLayout;
    }
}
