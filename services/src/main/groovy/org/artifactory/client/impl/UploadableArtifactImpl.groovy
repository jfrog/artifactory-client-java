package org.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.artifactory.client.Artifactory
import org.artifactory.client.UploadListener
import org.artifactory.client.UploadableArtifact
import org.artifactory.client.model.impl.FileImpl

import java.security.MessageDigest

import static groovyx.net.http.ContentType.BINARY

/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
class UploadableArtifactImpl extends ArtifactBase<UploadableArtifact> implements UploadableArtifact {

    private UploadListener listener
    private File file
    private String path
    private InputStream content
    private ArtifactoryImpl artifactory
    private String sha1

    UploadableArtifactImpl(String repo, String path, InputStream content, Artifactory artifactory) {
        super(repo)
        this.artifactory = artifactory as ArtifactoryImpl
        this.path = path
        this.content = content
    }

    UploadableArtifactImpl(String repo, String path, File file, Artifactory artifactory) {
        this(repo, path, file.newInputStream(), artifactory)
        this.file = file
    }

    org.artifactory.client.model.File doUpload() {
        def params = parseParams(props, '=')
        if (sha1) {
            try {
                artifactory.put("/$repo/$path${params}", [:], null, ['X-Checksum-Deploy': true, 'X-Checksum-Sha1': sha1], FileImpl, BINARY, file ? file.length() : -1)
            } catch (HttpResponseException e) {
                if (e.statusCode == 404) {
                    uploadContent(params)
                } else {
                    throw e
                }
            }
        } else {
            uploadContent(params)
        }
    }

    private uploadContent(params) {
        if (listener) {
            content = new ProgressInputStream(content, file.size(), listener)
        }
        content.withStream {
            artifactory.put("/$repo/$path${params}", [:], content, [:], FileImpl, BINARY, file ? file.size() : -1)
        } as org.artifactory.client.model.File
    }

    @Override
    UploadableArtifact withListener(UploadListener listener) {
        if (!file) {
            throw new IllegalStateException('Can\'t attach listener to content of unknown size. Try uploading a file instead of an input stream.')
        };
        this.listener = listener
        this
    }

    @Override
    UploadableArtifact bySha1Checksum(String sha1) {
        this.sha1 = sha1
        this
    }

    @Override
    UploadableArtifact bySha1Checksum() {
        if (!file) {
            throw new IllegalStateException('Can\'t calculate checksum for streaming content. Try uploading a file instead of an input stream or provide a checksum.')
        };
        MessageDigest md = MessageDigest.getInstance('SHA1')
        byte[] dataBytes = new byte[1024]
        int nread
        while ((nread = content.read(dataBytes)) != -1) {
            md.update dataBytes, 0, nread
        }

        byte[] mdbytes = md.digest()

        //convert the byte to hex format
        StringBuilder sb = new StringBuilder('')
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append Integer.toString((mdbytes[i] & 0xff) + 0x100 as int, 16).substring(1)
        }

        sha1 = sb.toString()
        //the content has been read, need to reset it
        content = file.newInputStream()
        this
    }
}