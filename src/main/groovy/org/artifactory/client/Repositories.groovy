package org.artifactory.client

import com.fasterxml.jackson.core.type.TypeReference
import groovy.json.JsonSlurper
import org.artifactory.client.model.LightweightRepository
import org.artifactory.client.model.Repository
import org.artifactory.client.model.RepositoryType
import org.artifactory.client.model.builder.RepositoryBuilders
import org.artifactory.client.model.impl.FileImpl
import org.artifactory.client.model.impl.LightweightRepositoryImpl

import static groovyx.net.http.ContentType.BINARY
import org.artifactory.client.model.File

/**
 *
 * @author jbaruch
 * @since 29/07/12
 */

class Repositories {

    private Artifactory artifactory
    private String repo
    private RepositoryBuilders builders

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
        new Items(artifactory, repo, folderName)
    }

    String create(int position, Repository configuration) {
        artifactory.put("/api/repositories/${repo}", [pos: position], configuration)
    }

    String update(Repository configuration) {
        artifactory.post("/api/repositories/${repo}", configuration)
    }

    String delete() {
        artifactory.delete("/api/repositories/${repo}")
    }

    List<LightweightRepository> list(RepositoryType repositoryType) {
        artifactory.getJson('/api/repositories/', new TypeReference<List<LightweightRepositoryImpl>>() {}, [type: repositoryType.toString()])
    }

    Repository get() {
        String repoJson = artifactory.getText("/api/repositories/${repo}")
        JsonSlurper slurper = new JsonSlurper()
        def repo = slurper.parseText(repoJson)
        artifactory.parseText(repoJson, RepositoryType.parseString(repo.rclass).typeClass)
    }

    Artifact prepareArtifact() {
        return new Artifact(this)
    }

    static interface Uploadable{
        File to(String path)
    }

    static class Artifact {
        private props = [:]
        private Repositories repositories

        private Artifact(Repositories repositories) {
            this.repositories = repositories
        }

        Artifact withProperty(String name, Object... values) {//for some strange reason def won't work here
            props[name] = values.join(',')
            this
        }

        Artifact withProperty(String name, Object value) {
            props[name] = value
            this
        }

        Uploadable upload(InputStream content) {
            def uploadable = [:]
            uploadable.to = {String path ->
                repositories.artifactory.put("/$repositories.repo/$path${parseParams()}", [:], content, FileImpl, BINARY)
            }
            return uploadable as Uploadable
        }

        private String parseParams() {
            String propsString = props.empty ? '' : ';' + props.inject([]) {result, entry ->
                result << "$entry.key=$entry.value"
            }.join(';')
            propsString
        }

        InputStream downloadFrom(String path){
            repositories.artifactory.getInputStream("/$repositories.repo/$path${parseParams()}")
        }
    }

}