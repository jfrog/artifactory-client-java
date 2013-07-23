package org.jfrog.artifactory.client.impl

import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.DownloadableArtifact
import org.jfrog.artifactory.client.ItemHandle
import org.jfrog.artifactory.client.RepositoryHandle
import org.jfrog.artifactory.client.UploadableArtifact
import org.jfrog.artifactory.client.model.ItemPermission
import org.jfrog.artifactory.client.model.ReplicationStatus
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.impl.FileImpl
import org.jfrog.artifactory.client.model.impl.FolderImpl
import org.jfrog.artifactory.client.model.impl.ReplicationStatusImpl

import static org.jfrog.artifactory.client.Repositories.REPLICATION_API
import static org.jfrog.artifactory.client.Repositories.REPOSITORIES_API
import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.parseString
/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
class RepositoryHandleImpl implements RepositoryHandle {
    private String repo
    private ArtifactoryImpl artifactory

    RepositoryHandleImpl(ArtifactoryImpl artifactory, String repo) {
        this.artifactory = artifactory
        this.repo = repo
    }

    //TODO: [by yl] Use a FileHandler and a FolderHandler instead or returning Items
    ItemHandle folder(String folderName) {
        new ItemHandleImpl(artifactory, repo, folderName, FolderImpl)
    }

    ItemHandle file(String filePath) {
        new ItemHandleImpl(artifactory, repo, filePath, FileImpl)
    }

    ReplicationStatus replicationStatus() {
        artifactory.get("$REPLICATION_API${repo}", ContentType.JSON, ReplicationStatusImpl.class)
    }

    String delete() {
        artifactory.delete("$REPOSITORIES_API${repo}", [:], ContentType.TEXT)
    }

    String delete(String path) {
        artifactory.delete("/${repo}/${path}", [:], ContentType.TEXT)
    }

    Repository get() {
        // Use response to deserialize against proper type.
        String repoJson = artifactory.get("$REPOSITORIES_API${repo}", ContentType.JSON, String)
        JsonSlurper slurper = new JsonSlurper()
        def repo = slurper.parseText(repoJson)
        artifactory.parseText(repoJson, parseString(repo.rclass).typeClass)
    }

    UploadableArtifact upload(String targetPath, InputStream content) {
        new UploadableArtifactImpl(repo, targetPath, (InputStream) content, artifactory)
    }

    @Override
    UploadableArtifact upload(String targetPath, File content) {
        assert content.isFile()
        new UploadableArtifactImpl(repo, targetPath, content, artifactory)
    }

    DownloadableArtifact download(String path) {
        new DownloadableArtifactImpl(repo, path, artifactory)
    }

    @Override
    Set<ItemPermission> effectivePermissions() {
        this.folder('').effectivePermissions()
    }
}
