package org.artifactory.client

import org.artifactory.client.model.Item
import org.artifactory.client.model.ItemImpl

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
        artifactory.getJson("/api/storage/$repoKey/$filePath", ItemImpl)
    }
}
