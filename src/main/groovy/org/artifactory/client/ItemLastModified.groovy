package org.artifactory.client

/**
 *GET /api/storage/{repoKey}/{item-path}?lastModified
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ItemLastModified {

    private Artifactory artifactory
    private String repoKey
    private String itemPath

    ItemLastModified(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    ItemLastModified repoKey(String repoKey) {
        this.repoKey = repoKey
        this
    }

    ItemLastModified itemPath(String filePath) {
        this.itemPath = filePath
        this
    }

    void get() {
        assert artifactory
        assert repoKey
        assert itemPath
        artifactory.get("/api/storage/$repoKey/$itemPath", [lastModified:''])
    }
}
