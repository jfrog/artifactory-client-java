package org.jfrog.artifactory.client.model;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface RepositoryType {

    public Class<? extends Repository> getTypeClass();

    @Override
    public String toString();
}