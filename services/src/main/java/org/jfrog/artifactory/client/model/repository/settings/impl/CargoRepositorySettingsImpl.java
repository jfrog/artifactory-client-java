package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.CargoRepositorySettings;

import java.util.Objects;

public class CargoRepositorySettingsImpl extends AbstractRepositorySettings implements CargoRepositorySettings {
    public static String defaultLayout = "simple-default";
    private String gitRegistryUrl;
    private Boolean cargoInternalIndex;
    private Boolean cargoAnonymousAccess;

    public CargoRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.cargo;
    }

    @Override
    public String getGitRegistryUrl() {
        return gitRegistryUrl;
    }

    public void setGitRegistryUrl(String tRegistryUrl) {
        this.gitRegistryUrl = tRegistryUrl;
    }

    @Override
    public Boolean isCargoInternalIndex() {
        return cargoInternalIndex;
    }

    public void setCargoInternalIndex(Boolean cargoInternalIndex) {
        this.cargoInternalIndex = cargoInternalIndex;
    }
    @Override
    public Boolean isCargoAnonymousAccess() {
        return cargoAnonymousAccess;
    }
    public void setCargoAnonymousAccess(Boolean cargoAnonymousAccess) {
        this.cargoAnonymousAccess = cargoAnonymousAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoRepositorySettingsImpl that = (CargoRepositorySettingsImpl) o;
        return Objects.equals(gitRegistryUrl, that.gitRegistryUrl) && Objects.equals(cargoInternalIndex, that.cargoInternalIndex) && Objects.equals(cargoAnonymousAccess, that.cargoAnonymousAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gitRegistryUrl, cargoInternalIndex, cargoAnonymousAccess);
    }
}
