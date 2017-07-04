package org.jfrog.artifactory.client.model.repository.settings.impl

import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.PackageType
import org.jfrog.artifactory.client.model.repository.settings.DebianRepositorySettings

/**
 * GroovyBean implementation of the {@link DebianRepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@EqualsAndHashCode
class DebianRepositorySettingsImpl implements DebianRepositorySettings {
    Boolean debianTrivialLayout
    Boolean listRemoteFolderItems

    @Override
    public PackageType getPackageType() {
        return PackageType.debian
    }
}
