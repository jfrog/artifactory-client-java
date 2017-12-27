package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Group extends Subject {

    String getDescription();

    boolean isAutoJoin();

    boolean isAdminPrivileges();
}
