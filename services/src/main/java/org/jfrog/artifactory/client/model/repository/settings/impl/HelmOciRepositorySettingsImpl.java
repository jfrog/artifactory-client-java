package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.OciRepositorySettings;

public class HelmOciRepositorySettingsImpl extends DockerRepositorySettingsImpl implements OciRepositorySettings {
    public static String defaultLayout = "simple-default";

    public PackageType getPackageType() {
        return PackageTypeImpl.helmoci;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HelmOciRepositorySettingsImpl)) {
            return false;
        }
        return super.equals(o);
    }
}
