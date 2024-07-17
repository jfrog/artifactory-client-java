package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.OciRepositorySettings;

public class OciRepositorySettingsImpl extends DockerRepositorySettingsImpl implements OciRepositorySettings {
    public static String defaultLayout = "simple-default";
    
    public OciRepositorySettingsImpl() {
        super();
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.oci;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OciRepositorySettingsImpl)) return false;
        if (!super.equals(o)) return false;

        OciRepositorySettingsImpl that = (OciRepositorySettingsImpl) o;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}
