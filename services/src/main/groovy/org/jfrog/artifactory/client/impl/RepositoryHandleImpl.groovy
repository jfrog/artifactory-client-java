package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.jfrog.artifactory.client.*
import org.jfrog.artifactory.client.model.ItemPermission
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.ReplicationStatus
import org.jfrog.artifactory.client.model.Repository
import org.jfrog.artifactory.client.model.impl.FileImpl
import org.jfrog.artifactory.client.model.impl.FolderImpl
import org.jfrog.artifactory.client.model.impl.ReplicationStatusImpl
import org.jfrog.artifactory.client.model.impl.RepositoryBase
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings
import org.jfrog.artifactory.client.model.xray.settings.impl.XraySettingsImpl

import java.beans.BeanInfo
import java.beans.Introspector
import java.beans.PropertyDescriptor

import static org.jfrog.artifactory.client.impl.util.Util.configureObjectMapper

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

    @Override
    FolderHandle folder(String folderName) {
        new FolderHandleImpl(artifactory, baseApiPath, repoKey, folderName, FolderImpl)
    }

    @Override
    ItemHandle file(String filePath) {
        new ItemHandleImpl(artifactory, baseApiPath, repoKey, filePath, FileImpl)
    }

    @Override
    ReplicationStatus replicationStatus() {
        artifactory.get("${repository.getReplicationApi()}${repoKey}", ReplicationStatusImpl.class, ReplicationStatus)
    }

    @Override
    String delete() {
        artifactory.delete("${repository.getRepositoriesApi()}${repoKey}")
    }

    @Override
    String delete(String path) {
        artifactory.delete("/${repoKey}/${path}")
    }

    @Override
    Repository get() {
        String json = artifactory.get("${repository.getRepositoriesApi()}${repoKey}", String, null);
        parseJsonAsRepository(json)
    }

    private Repository parseJsonAsRepository(String json) {
        RepositoryBase repo = Util.parseText(json, Repository)
        RepositorySettings settings = Util.parseText(json, RepositorySettings)
        XraySettingsImpl xray = Util.parseText(json, XraySettingsImpl)

        repo.setRepositorySettings(settings)
        repo.setXraySettings(xray)
        repo.customProperties = getCustomProperties(json, repo)

        repo
    }

    private Map<String, Object> getCustomProperties(String json, Repository repo) {
        Map<String, Object> customProperties = Util.parseText(json, Map)

        Set<String> knownKeys = new HashSet<>()
        knownKeys.addAll(extractProperties(repo))
        knownKeys.addAll(extractProperties(repo.xraySettings))
        knownKeys.addAll(extractProperties(repo.repositorySettings))

        customProperties.keySet().removeAll(knownKeys)
        return customProperties
    }

    private Set<String> extractProperties(Object obj) {
        Set<String> props = new HashSet<>()
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass())
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            props.add(descriptor.getName())
        }
        return props
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
        if (sha1 == null) {
            throw new IllegalArgumentException("Sha1 is mandatory - can't be null")
        }
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
        String itemInfoJson = artifactory.get("/api/storage/${repoKey}/${path}", String, null)

        ObjectMapper objectMapper = new ObjectMapper()
        configureObjectMapper(objectMapper)
        JsonNode jsonNode = objectMapper.readTree(itemInfoJson)

        return jsonNode.get("children") != null
    }

    @Override
    boolean exists() {
        artifactory.head("${repository.getRepositoriesApi()}${repoKey}")
    }

    @Override
    Replications getReplications() {
        return new ReplicationsImpl(artifactory, baseApiPath, repoKey)
    }
}
