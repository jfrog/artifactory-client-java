package org.artifactory.client.model.impl;

import org.artifactory.client.model.ItemPermission;
import org.artifactory.client.model.Privilege;
import org.artifactory.client.model.RepoPath;
import org.artifactory.client.model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class ItemPermissionImpl implements ItemPermission {

    private final RepoPath repoPath;
    private final List<Privilege> privileges;
    private final Subject subject;

    public ItemPermissionImpl(RepoPath repoPath, Subject subject, List<Privilege> privileges) {
        this.repoPath = repoPath;
        this.subject = subject;
        this.privileges = new ArrayList<>(privileges);
    }

    @Override
    public RepoPath getRepoPath() {
        return repoPath;
    }

    @Override
    public List<Privilege> getPrivileges() {
        return privileges;
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public boolean isAllowedTo(Privilege... privileges) {
        for (Privilege privilege : privileges) {
            if (!this.privileges.contains(privilege)) {
                return false;
            }
        }
        return true;
    }
}
