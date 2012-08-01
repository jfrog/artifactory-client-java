package org.artifactory.client

import com.fasterxml.jackson.core.type.TypeReference
import groovy.json.JsonSlurper
import org.artifactory.client.model.LightweightRepository
import org.artifactory.client.model.Repository
import org.artifactory.client.model.RepositoryType
import org.artifactory.client.model.builder.RepositoryBuilders
import org.artifactory.client.model.impl.LightweightRepositoryImpl
import org.artifactory.client.model.impl.RepositoryBase
import org.artifactory.client.model.File
import org.artifactory.client.model.impl.FileImpl
import groovyx.net.http.ContentType

import static groovyx.net.http.ContentType.BINARY

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

    Artifact prepareArtifactFrom(InputStream content){
        return new Artifact(content, this)
    }

    static class Artifact{
        private InputStream content
        private params = [:]
        private Repositories repositories

        private Artifact(InputStream content, Repositories repositories) {
            this.content = content
            this.repositories = repositories
        }

        Artifact addParameter(String name, String value, String... additionalValues){
            params.name = values
            this
        }

        File deployTo(String path){
            repositories.artifactory.put("/$repositories.repo/$path", [:], content, FileImpl, BINARY)
        }

    }
}
