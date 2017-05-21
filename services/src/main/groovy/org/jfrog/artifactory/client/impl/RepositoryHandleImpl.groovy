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
import org.jfrog.artifactory.client.model.xray.settings.impl.XraySettingsImpl

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
    @Override
    ItemHandle folder(String folderName) {
        new ItemHandleImpl(artifactory, baseApiPath, repoKey, folderName, FolderImpl)
    }

    @Override
    ItemHandle file(String filePath) {
        new ItemHandleImpl(artifactory, baseApiPath, repoKey, filePath, FileImpl)
    }

    @Override
    ReplicationStatus replicationStatus() {
        artifactory.get("${repository.getReplicationApi()}${repoKey}", ContentType.JSON, ReplicationStatusImpl.class)
    }

    @Override
    String delete() {
        artifactory.delete("${repository.getRepositoriesApi()}${repoKey}", [:], ContentType.TEXT)
    }

    @Override
    String delete(String path) {
        artifactory.delete("/${repoKey}/${path}", [:], ContentType.TEXT)
    }

    @Override
    Repository get() {
        String json = artifactory.get("${repository.getRepositoriesApi()}${repoKey}", ContentType.JSON, String)
        parseJsonAsRepository(json)
    }

    private Repository parseJsonAsRepository(String json) {
        def repo = artifactory.parseText(json, Repository)
        def settings = artifactory.parseText(json, RepositorySettings)
        XraySettingsImpl xray = artifactory.parseText(json, XraySettingsImpl)

        repo.setRepositorySettings settings
        repo.setXraySettings xray

        repo
    }

    @Override
    UploadableArtifact upload(String targetPath, InputStream content) {
        new UploadableArtifactImpl(repoKey, targetPath, (InputStream) content, artifactory)
    }

    @Override
    UploadableArtifact upload(String targetPath, File content) {
        assert content.isFile()
        new UploadableArtifactImpl(repoKey, targetPath, content, artifactory)
    }

    @Override
    UploadableArtifact copyBySha1(String targetPath, String sha1) {
        new UploadableArtifactImpl(repoKey, targetPath, sha1, artifactory)
    }

    @Override
    DownloadableArtifact download(String path) {
        new DownloadableArtifactImpl(repoKey, path, artifactory)
    }

    @Override
    Set<ItemPermission> effectivePermissions() {
        this.folder('').effectivePermissions()
    }

    @Override
    boolean isFolder(String path) {
        String itemInfoJson = artifactory.get("/api/storage/${repoKey}/${path}", ContentType.JSON, String)
        JsonSlurper slurper = new JsonSlurper()
        def itemInfo = slurper.parseText(itemInfoJson)
        return itemInfo.children != null;
    }

}
