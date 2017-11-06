package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;

/**
 * @author jbaruch
 * @since 22/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Principal {

    String getName();
    Set<Privilege> getPrivileges();

    boolean isAllowedTo(Privilege... privileges);
}
