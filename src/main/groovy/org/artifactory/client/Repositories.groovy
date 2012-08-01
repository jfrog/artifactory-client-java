package org.artifactory.client

import com.fasterxml.jackson.core.type.TypeReference
import groovy.json.JsonSlurper
import org.artifactory.client.model.LightweightRepository
import org.artifactory.client.model.Repository
import org.artifactory.client.model.RepositoryType
import org.artifactory.client.model.builder.RepositoryBuilders
import org.artifactory.client.model.impl.LightweightRepositoryImpl
import org.artifactory.client.model.impl.RepositoryBase

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
}
