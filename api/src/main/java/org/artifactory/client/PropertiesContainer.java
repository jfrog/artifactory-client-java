package org.artifactory.client;

import java.util.Map;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public interface PropertiesContainer {

    PropertiesContainer addProperties(Map<String, ?> properties);

    PropertiesContainer addProperty(String propertyName, String... values);

    PropertiesContainer addProperty(String propertyName, String value);

    void doSet(boolean recursive);

    void doSet();
}