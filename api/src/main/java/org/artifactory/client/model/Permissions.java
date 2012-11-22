package org.artifactory.client.model;

import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public interface Permissions {

    List<? extends Principal> getUsers();
    List<? extends Principal> getGroups();

    Principal user(String user);
    Principal group(String group);

}
