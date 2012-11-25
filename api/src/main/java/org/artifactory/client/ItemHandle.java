package org.artifactory.client;

import org.artifactory.client.model.ItemPermission;

import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface ItemHandle {

    <T> T info();

    boolean isFolder();

    Map<String, List<String>> getProperties(String... properties);

    List<String> getPropertyValues(String propertyName);

    PropertiesContainer properties();

    Map<String, ?> deleteProperty(String property);
    Map<String, ?> deleteProperties(String... properties);

    List<ItemPermission> effectivePermissions();
}