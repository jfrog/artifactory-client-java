package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.ItemPermission;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface ItemHandle {

    <T> T info();

    boolean isFolder();

    Map<String, List<String>> getProperties(String... properties);

    List<String> getPropertyValues(String propertyName);

    PropertiesHandler properties();

    Map<String, ?> deleteProperty(String property);

    Map<String, ?> deleteProperties(String... properties);

    Set<ItemPermission> effectivePermissions();

    String move(String to);
}