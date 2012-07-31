package org.artifactory.client

import org.artifactory.client.model.impl.ItemImpl

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Files {

    private Artifactory artifactory
    private String repoKey
    private String filePath

    Files(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    Files repoKey(String repoKey) {
        this.repoKey = repoKey
        this
    }

    Files filePath(String filePath) {
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
