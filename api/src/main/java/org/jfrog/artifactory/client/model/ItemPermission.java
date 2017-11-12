package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemPermission {

    RepoPath getRepoPath();

    List<Privilege> getPrivileges();

    Subject getSubject();

    boolean isAllowedTo(Privilege... privileges);
}
