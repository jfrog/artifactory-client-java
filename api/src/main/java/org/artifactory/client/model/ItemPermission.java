package org.artifactory.client.model;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public interface ItemPermission {

    RepoPath getRepoPath();

    List<Privilege> getPrivileges();

    Subject getSubject();

    boolean isAllowedTo(Privilege... privileges);
}
