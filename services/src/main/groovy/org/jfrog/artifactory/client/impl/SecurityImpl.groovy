package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.Security
import org.jfrog.artifactory.client.model.User
import org.jfrog.artifactory.client.model.builder.SecurityBuilders
import org.jfrog.artifactory.client.model.builder.impl.SecurityBuildersImpl
import org.jfrog.artifactory.client.model.impl.UserImpl

/**
 *
 * Date: 10/18/12
 * Time: 9:59 AM
 * @author freds
 */
class SecurityImpl implements Security {
    private ArtifactoryImpl artifactory

    static private SecurityBuilders builders = SecurityBuildersImpl.create()

    SecurityImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    SecurityBuilders builders() {
        return builders
    }

    @Override
    Collection<String> userNames() {
        def users = artifactory.get(SECURITY_USERS_API, ContentType.JSON)
        users.collect { it.name }
    }

    @Override
    User user(String name) {
        artifactory.get("${SECURITY_USERS_API}/$name", ContentType.JSON, new TypeReference<UserImpl>() {})
    }

    @Override
    void createOrUpdate(User user) {
        artifactory.put("${SECURITY_USERS_API}/${user.name}", [:], ContentType.ANY, null, ContentType.JSON, user)
    }

    @Override
    String deleteUser(String name) {
        artifactory.delete("${SECURITY_USERS_API}/$name")
    }
}
