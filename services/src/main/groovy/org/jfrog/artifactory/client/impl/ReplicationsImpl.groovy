package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.Replications
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.Replication
import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl

class ReplicationsImpl implements Replications {

    private String baseApiPath

    private ArtifactoryImpl artifactory
    private String repoKey

    ReplicationsImpl(ArtifactoryImpl artifactory, String baseApiPath, String repoKey) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
        this.repoKey = repoKey
    }

    @Override
    void createOrReplace(Replication replication) {
        artifactory.put("${getReplicationsApi()}${repoKey}", null, Util.getStringFromObject(replication), new HashMap<String, String>(),
                      null, -1, String, null      );
    }

    @Override
    void delete() {
        // Determine the type of the repository (not all repository types support replications)
        def repository = artifactory.repository(repoKey).get()

        if (!repository) {
            throw new RuntimeException("The repository '${repoKey}' doesn't exist")
        }
        if (repository.rclass == RepositoryTypeImpl.VIRTUAL) {
            throw new UnsupportedOperationException("The method isn't supported for a ${repository.rclass} repository")
        }

        def path = "${getReplicationsApi()}${repoKey}"

        artifactory.delete(path)
    }

    @Override
    String getReplicationsApi() {
        return baseApiPath + "/replications/";
    }
}