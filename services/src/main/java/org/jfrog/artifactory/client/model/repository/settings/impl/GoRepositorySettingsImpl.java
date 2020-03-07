package org.jfrog.artifactory.client.model.repository.settings.impl;

import java.util.Collection;
import java.util.Objects;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.GoRepositorySettings;

/**
 * @author David Csakvari
 */
public class GoRepositorySettingsImpl extends VcsRepositorySettingsImpl implements GoRepositorySettings {
    private static String defaultLayout = "go-default";

    private Boolean externalDependenciesEnabled;

    private Collection<String> externalDependenciesPatterns;

    public GoRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.go;
    }

    @Override
    public Boolean getExternalDependenciesEnabled() {
        return externalDependenciesEnabled;
    }

    void setExternalDependenciesEnabled(Boolean externalDependenciesEnabled) {
        this.externalDependenciesEnabled = externalDependenciesEnabled;
    }

    @Override
    public Collection<String> getExternalDependenciesPatterns() {
        return externalDependenciesPatterns;
    }

    void setExternalDependenciesPatterns(Collection<String> externalDependenciesPatterns) {
        this.externalDependenciesPatterns = externalDependenciesPatterns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoRepositorySettingsImpl)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        GoRepositorySettingsImpl that = (GoRepositorySettingsImpl) o;
        return Objects.equals(externalDependenciesEnabled, that.externalDependenciesEnabled) &&
                Objects.equals(externalDependenciesPatterns, that.externalDependenciesPatterns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), externalDependenciesEnabled, externalDependenciesPatterns);
    }

}
