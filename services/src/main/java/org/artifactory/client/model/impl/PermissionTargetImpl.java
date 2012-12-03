package org.artifactory.client.model.impl;

import org.artifactory.client.model.ItemPermission;
import org.artifactory.client.model.PermissionTarget;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public class PermissionTargetImpl implements PermissionTarget {

    private final String name;
    private final String includesPattern;
    private final String excludesPattern;
    private final List<String> repositories;
    private final List<ItemPermission> itemPermissions;

    public PermissionTargetImpl(String name, String includesPattern, String excludesPattern, List<String> repositories, List<ItemPermission> itemPermissions) {
        this.name = name;
        this.includesPattern = includesPattern;
        this.excludesPattern = excludesPattern;
        this.repositories = repositories;
        this.itemPermissions = itemPermissions;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getIncludesPattern() {
        return includesPattern;
    }

    public String getExcludesPattern() {
        return excludesPattern;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public List<ItemPermission> getItemPermissions() {
        return itemPermissions;
    }
}
