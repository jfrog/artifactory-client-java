package org.artifactory.client.model.impl

import org.artifactory.client.model.RepoPath

/**
 *
 * @author jbaruch
 * @since 22/11/12
 */
class RepoPathImpl implements RepoPath {
    private String repoKey
    private String itemPath

    RepoPathImpl(String repoKey, String itemPath) {

        this.itemPath = itemPath
        this.repoKey = repoKey
    }

    @Override
    String getRepoKey() {
        return repoKey;
    }

    @Override
    String getItemPath() {
        return itemPath;
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        RepoPathImpl repoPath = (RepoPathImpl) o

        if (itemPath != repoPath.itemPath) return false
        if (repoKey != repoPath.repoKey) return false

        return true
    }

    int hashCode() {
        int result
        result = (repoKey != null ? repoKey.hashCode() : 0)
        result = 31 * result + (itemPath != null ? itemPath.hashCode() : 0)
        return result
    }

    @Override
    public String toString() {
        return "RepoPathImpl{" +
                "repoKey='" + repoKey + '\'' +
                ", itemPath='" + itemPath + '\'' +
                '}';
    }
}
