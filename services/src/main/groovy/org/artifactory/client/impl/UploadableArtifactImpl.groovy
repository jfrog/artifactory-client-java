package org.artifactory.client.impl

import org.artifactory.client.Artifactory
import org.artifactory.client.UploadableArtifact
import org.artifactory.client.model.File
import org.artifactory.client.model.impl.FileImpl

import static groovyx.net.http.ContentType.BINARY

/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
class UploadableArtifactImpl extends ArtifactBase<UploadableArtifact> implements UploadableArtifact {

    private path
    private content
    private ArtifactoryImpl artifactory

    UploadableArtifactImpl(String repo, String path, InputStream content, Artifactory artifactory) {
        super(repo)
        this.artifactory = artifactory as ArtifactoryImpl
        this.path = path
        this.content = content
    }

    File doUpload() {
        content.withStream {
            def params = parseParams(props, '=')
            artifactory.put("/$repo/$path${params}", [:], content, FileImpl, BINARY)
        }
    }
}
