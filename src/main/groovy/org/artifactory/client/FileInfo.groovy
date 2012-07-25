package org.artifactory.client

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class FileInfo {

    private Artifactory artifactory
    private String repoKey
    private String filePath

    FileInfo(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    FileInfo repoKey(String repoKey) {
        this.repoKey = repoKey
        this
    }

    FileInfo filePath(String filePath) {
        this.filePath = filePath
        this
    }

    void get() {
        assert artifactory
        assert repoKey
        assert filePath
        artifactory.get("/api/storage/$repoKey/$filePath")
    }
}
