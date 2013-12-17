package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.ItemPermission;
import org.jfrog.artifactory.client.model.PermissionTarget;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties("principals")
public class PermissionTargetImpl implements PermissionTarget {

    private String name;
    private String includesPattern;
    private String excludesPattern;
    private List<String> repositories;
    //    @JsonIgnore this 2 lines of code can be used instead of annotation above the class
//    private  List<String> principals;
    private List<ItemPermission> itemPermissions;

    public PermissionTargetImpl() {
    }

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
