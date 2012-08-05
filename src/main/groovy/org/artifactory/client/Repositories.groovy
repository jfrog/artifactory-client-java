package org.artifactory.client

import com.fasterxml.jackson.core.type.TypeReference
import groovy.json.JsonSlurper
import org.artifactory.client.model.File
import org.artifactory.client.model.LightweightRepository
import org.artifactory.client.model.Repository
import org.artifactory.client.model.RepositoryType
import org.artifactory.client.model.builder.RepositoryBuilders
import org.artifactory.client.model.impl.FileImpl
import org.artifactory.client.model.impl.FolderImpl
import org.artifactory.client.model.impl.LightweightRepositoryImpl

import static groovyx.net.http.ContentType.BINARY
import static org.artifactory.client.model.RepositoryType.parseString

/**
 *
 * @author jbaruch
 * @since 29/07/12
 */

class Repositories {

    private Artifactory artifactory
    private String repo
    private RepositoryBuilders builders

    private REPOSITORIES_API = '/api/repositories/'

    Repositories(Artifactory artifactory) {
        this.artifactory = artifactory
        builders = new RepositoryBuilders()
    }

    Repositories(Artifactory artifactory, String repo) {
        this(artifactory)
        this.repo = repo
    }

    RepositoryBuilders builders() {
        builders
    }

    Items folder(String folderName) {
        new Items(artifactory, repo, folderName, FolderImpl)
    }

    Items file(String filePath) {
        new Items(artifactory, repo, filePath, FileImpl)
    }

    String create(int position, Repository configuration) {
        artifactory.put("$REPOSITORIES_API${repo}", [pos: position], configuration)
    }

    String update(Repository configuration) {
        artifactory.post("$REPOSITORIES_API${repo}", configuration)
    }

    String delete() {
        artifactory.delete("$REPOSITORIES_API${repo}")
    }

    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.getJson(REPOSITORIES_API, new TypeReference<List<LightweightRepositoryImpl>>() {}, [type: repositoryType.toString()])
    }

    Repository get() {
        String repoJson = artifactory.getText("$REPOSITORIES_API${repo}")
        JsonSlurper slurper = new JsonSlurper()
        def repo = slurper.parseText(repoJson)
        artifactory.parseText(repoJson, parseString(repo.rclass).typeClass)
    }

    UploadableArtifact upload(String targetPath, InputStream content) {
        return new UploadableArtifact(targetPath, content)
    }

    DownloadableArtifact download(String path) {
        return new DownloadableArtifact(path)
    }

    abstract class Artifact<T extends Artifact> {
        protected props = [:]

        protected Artifact() {
        }

        T withProperty(String name, Object... values) {
            //for some strange reason def won't work here
            props[name] = values.join(',')
            this as T
        }

        T withProperty(String name, Object value) {
            props[name] = value
            this as T
        }

        protected String parseParams(Map props, String delimiter) {
            props.inject(['']) {result, entry ->
                result << "$entry.key$delimiter$entry.value"
            }.join(';')
        }
    }

    class UploadableArtifact extends Artifact<UploadableArtifact> {

        private path
        private content

        UploadableArtifact(String path, InputStream content) {
            this.path = path
            this.content = content
        }

        File doUpload() {
            def params = parseParams(props, '=')
            artifactory.put("/$repo/$path${params}", [:], content, FileImpl, BINARY)
        }
    }

    class DownloadableArtifact extends Artifact<DownloadableArtifact> {
        private path

        private mandatoryProps = [:]

        DownloadableArtifact(path) {
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
}