package org.artifactory.client.model;

import org.artifactory.client.model.ItemPermission;

import java.util.List;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public interface PermissionTarget {

    String getName();
    String getIncludesPattern();
    String getExcludesPattern();
    List<String> getRepositories();
    List<ItemPermission> getItemPermissions();
}
