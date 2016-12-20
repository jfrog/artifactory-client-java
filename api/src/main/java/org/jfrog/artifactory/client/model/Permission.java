package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public interface Permission {

    String getName();

    String getIncludesPattern();

    String getExcludesPattern();

    List<String> getRepositories();

    Principals getPrincipals();

}
