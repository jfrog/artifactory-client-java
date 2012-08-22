package org.artifactory.client.impl

import org.artifactory.client.Artifactory
import org.artifactory.client.DownloadableArtifact

/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
class DownloadableArtifactImpl extends ArtifactBase<DownloadableArtifact> implements DownloadableArtifact {
    private path

    private mandatoryProps = [:]
    private ArtifactoryImpl artifactory

    DownloadableArtifactImpl(String repo, String path, Artifactory artifactory) {
        super(repo)
        this.artifactory = artifactory as ArtifactoryImpl
        this.path = path
    }

    InputStream doDownload() {
        def params = parseParams(props, '=') + (mandatoryProps ? ";${parseParams(mandatoryProps, '+=')}" : '')
        //TODO (jb there must be better solution for that!)
        params = params ? ";$params" : ''
        artifactory.getInputStream("/$repo/$path${params}")
    }

    DownloadableArtifact withMandatoryProperty(String name, Object... values) {
        //for some strange reason def won't work here
        mandatoryProps[name] = values.join(',')
        this
    }

    DownloadableArtifact withMandatoryProperty(String name, Object value) {
        mandatoryProps[name] = value
        this
    }
}
