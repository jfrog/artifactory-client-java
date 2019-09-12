package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.HelmRepositorySettings;

/**
 * @author Glen Lockhart (glen@openet.com)
 */
public class HelmRepositorySettingsImpl extends AbstractRepositorySettings implements HelmRepositorySettings {
    private static String defaultLayout = "simple-default";
    

    public HelmRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.helm;
    }
}
