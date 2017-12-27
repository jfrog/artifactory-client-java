package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Plugin;
import org.jfrog.artifactory.client.model.PluginType;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Plugins {

    Map<PluginType, List<Plugin>> list();

    List<Plugin> list(PluginType type);

    PluginHandle execute(String name);

    String getPluginsApi();
}
