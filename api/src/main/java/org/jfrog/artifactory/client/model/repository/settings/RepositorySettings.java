package org.jfrog.artifactory.client.model.repository.settings;

import org.jfrog.artifactory.client.model.PackageType;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public interface RepositorySettings {

    PackageType getPackageType();
}
