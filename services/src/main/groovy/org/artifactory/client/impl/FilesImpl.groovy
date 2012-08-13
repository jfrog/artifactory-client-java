package org.artifactory.client.impl

import org.artifactory.client.Artifactory
import org.artifactory.client.Files
import org.artifactory.client.model.Item
import org.artifactory.client.model.impl.ItemImpl

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class FilesImpl implements Files {

    private ArtifactoryImpl artifactory
    private String repoKey
    private String filePath

    FilesImpl(Artifactory artifactory) {
        this.artifactory = artifactory as ArtifactoryImpl
    }

    Files repoKey(String repoKey) {
        this.repoKey = repoKey
        this
    }

    Files filePath(String filePath) {
        this.filePath = filePath
        this
    }

    Item get() {
        assert artifactory
        assert repoKey
        assert filePath
        artifactory.getJson("/api/storage/$repoKey/$filePath", ItemImpl)
    }
}
