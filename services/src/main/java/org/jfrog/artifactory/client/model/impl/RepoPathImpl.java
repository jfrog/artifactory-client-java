package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.RepoPath;

/**
 *
 * @author jbaruch
 * @since 22/11/12
 */
public class RepoPathImpl implements RepoPath {
    private String repoKey;
    private String itemPath;

    RepoPathImpl(String repoKey, String itemPath) {
        this.itemPath = itemPath;
        this.repoKey = repoKey;
    }

    @Override
    public String getRepoKey() {
        return repoKey;
    }

    @Override
    public String getItemPath() {
        return itemPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepoPathImpl repoPath = (RepoPathImpl) o;

        return itemPath.equals(repoPath.itemPath) && repoKey.equals(repoPath.repoKey);
    }

    @Override
    public int hashCode() {
        int result = repoKey.hashCode();
        result = 31 * result + itemPath.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RepoPathImpl{" +
                "repoKey='" + repoKey + '\'' +
                ", itemPath='" + itemPath + '\'' +
                '}';
    }
}
