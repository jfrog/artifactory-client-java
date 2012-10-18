package org.artifactory.client.impl

import groovy.json.JsonSlurper
import org.artifactory.client.Security
import org.artifactory.client.model.User
import org.artifactory.client.model.builder.SecurityBuilders

/**
 *
 * Date: 10/18/12
 * Time: 9:59 AM
 * @author freds
 */
class SecurityImpl implements Security {
    private ArtifactoryImpl artifactory

    SecurityImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    SecurityBuilders builders() {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    Collection<String> userNames() {
        String allUsers = artifactory.getText("${SECURITY_API}users")
        JsonSlurper slurper = new JsonSlurper()
        def users = slurper.parseText(allUsers)
        users.collect { it.name }
    }

    @Override
    User user(String name) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    String createOrUpdate(User user) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    String deleteUser(String name) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
