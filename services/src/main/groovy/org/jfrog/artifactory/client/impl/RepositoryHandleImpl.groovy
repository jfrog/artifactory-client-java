package org.jfrog.artifactory.client.impl

import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.*
import org.jfrog.artifactory.client.model.ItemPermission
import org.jfrog.artifactory.client.model.ReplicationStatus
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.impl.FileImpl
import org.jfrog.artifactory.client.model.impl.FolderImpl
import org.jfrog.artifactory.client.model.impl.ReplicationStatusImpl
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings

import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.parseString

/**
 *
 * @author jbaruch
 * @since 12/08/12
 */
class RepositoryHandleImpl implements RepositoryHandle {
    private ArtifactoryImpl artifactory
    private String baseApiPath
    private Repositories repository
    private String repoKey

    RepositoryHandleImpl(ArtifactoryImpl artifactory, String baseApiPath, Repositories repository, String repoKey) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
        this.repository = repository
        this.repoKey = repoKey
    }

    //TODO: [by yl] Use a FileHandler and a FolderHandler instead or returning Items
    ItemHandle folder(String folderName) {
        new ItemHandleImpl(artifactory, baseApiPath, repoKey, folderName, FolderImpl)
    }

    ItemHandle file(String filePath) {
        new ItemHandleImpl(artifactory, baseApiPath, repoKey, filePath, FileImpl)
    }

    ReplicationStatus replicationStatus() {
        artifactory.get("${repository.getReplicationApi()}${repoKey}", ContentType.JSON, ReplicationStatusImpl.class)
    }

    String delete() {
        artifactory.delete("${repository.getRepositoriesApi()}${repoKey}", [:], ContentType.TEXT)
    }

    String delete(String path) {
        artifactory.delete("/${repoKey}/${path}", [:], ContentType.TEXT)
    }

    Repository get() {
        String json = artifactory.get("${repository.getRepositoriesApi()}${repoKey}", ContentType.JSON, String)
        parseJsonAsRepository(json)
    }

    private Repository parseJsonAsRepository(String json) {
        def settings = artifactory.parseText(json, RepositorySettings)
        def repo = artifactory.parseText(json, Repository)

        repo.setRepositorySettings settings

        repo
    }

    UploadableArtifact upload(String targetPath, InputStream content) {
        new UploadableArtifactImpl(repoKey, targetPath, (InputStream) content, artifactory)
    }

    @Override
    UploadableArtifact upload(String targetPath, File content) {
        assert content.isFile()
        new UploadableArtifactImpl(repoKey, targetPath, content, artifactory)
    }

    DownloadableArtifact download(String path) {
        new DownloadableArtifactImpl(repoKey, path, artifactory)
    }

    @Override
    Set<ItemPermission> effectivePermissions() {
        this.folder('').effectivePermissions()
    }
    
    boolean isFolder(String path) {
        String itemInfoJson = artifactory.get("/api/storage/${repoKey}/${path}", ContentType.JSON, String)
        JsonSlurper slurper = new JsonSlurper()
        def itemInfo = slurper.parseText(itemInfoJson)
        return itemInfo.children != null;
    }

}
