package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Plugin;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PluginBuilder {
    PluginBuilder name(String name);

    PluginBuilder version(String version);

    PluginBuilder description(String description);

    PluginBuilder users(List<String> users);

    PluginBuilder groups(List<String> groups);

    PluginBuilder params(Map<String, String> params);

    Plugin build();
}
