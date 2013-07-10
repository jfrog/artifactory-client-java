package org.artifactory.client.impl

import groovy.json.JsonSlurper
import org.artifactory.client.DownloadableArtifact
import org.artifactory.client.ItemHandle
import org.artifactory.client.RepositoryHandle
import org.artifactory.client.UploadableArtifact
import org.artifactory.client.model.ItemPermission
import org.artifactory.client.model.ReplicationStatus
import org.artifactory.client.model.Repository
import org.artifactory.client.model.impl.FileImpl
import org.artifactory.client.model.impl.FolderImpl
import org.artifactory.client.model.impl.ReplicationStatusImpl

import static org.artifactory.client.Repositories.REPLICATION_API
import static org.artifactory.client.Repositories.REPOSITORIES_API
import static org.artifactory.client.model.impl.RepositoryTypeImpl.parseString

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
        String replicationStatusJson = artifactory.getText("$REPLICATION_API${repo}")
        artifactory.parseText(replicationStatusJson, ReplicationStatusImpl.class)
    }

    String delete() {
        artifactory.delete("$REPOSITORIES_API${repo}")
    }

    String delete(String path) {
        artifactory.delete("/${repo}/${path}")
    }

    Repository get() {
        String repoJson = artifactory.getText("$REPOSITORIES_API${repo}")
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
