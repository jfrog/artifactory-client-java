package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

/**
 * @author jbaruch
 * @since 22/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PropertiesHandler {

    PropertiesHandler addProperties(Map<String, ?> properties);

    PropertiesHandler addProperty(String propertyName, String... values);

    PropertiesHandler addProperty(String propertyName, String value);

    void doSet(boolean recursive);

    void doSet();
}
