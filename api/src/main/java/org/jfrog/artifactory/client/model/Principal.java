package org.jfrog.artifactory.client.model;

import java.util.Set;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public interface Principal {

    String getName();
    Set<Privilege> getPrivileges();

    boolean isAllowedTo(Privilege... privileges);
}
