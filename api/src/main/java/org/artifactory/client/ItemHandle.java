package org.artifactory.client;

import org.artifactory.client.model.Item;
import org.artifactory.client.model.Permissions;

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

    PropertiesContainer properties();

    Map<String, ?> deleteProperty(String property);
    Map<String, ?> deleteProperties(String... properties);

    Permissions effectivePermissions();
}