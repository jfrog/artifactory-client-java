package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Actions;
import org.jfrog.artifactory.client.model.PrivilegeV2;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface ActionsBuilder {

    ActionsBuilder addUser(String user, PrivilegeV2... privileges);
    ActionsBuilder addGroup(String group, PrivilegeV2... privileges);
    Actions build();
}
