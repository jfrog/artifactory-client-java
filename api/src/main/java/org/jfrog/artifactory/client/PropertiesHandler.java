package org.jfrog.artifactory.client;

import java.util.Map;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public interface PropertiesHandler {

    PropertiesHandler addProperties(Map<String, ?> properties);

    PropertiesHandler addProperty(String propertyName, String... values);

    PropertiesHandler addProperty(String propertyName, String value);

    void doSet(boolean recursive);

    void doSet();
}
