package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonValue;

import org.jfrog.artifactory.client.model.PackageType;

public class CustomPackageTypeImpl implements PackageType {

    private String packageName;

    public CustomPackageTypeImpl(String packageName) {
        this.packageName = packageName;
    }

    @Override
    @JsonValue
    public String name() {
        return packageName;
    }

    @Override
    public boolean isCustom() {
        return true;
    }
}
