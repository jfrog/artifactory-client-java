package org.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import net.sf.json.JSON
import org.artifactory.client.PluginHandle
import org.artifactory.client.Plugins
import org.artifactory.client.model.Plugin
import org.artifactory.client.model.PluginType
import org.artifactory.client.model.impl.PluginImpl

import static groovyx.net.http.ContentType.*

/**
 * @author jbaruch
 * @since 11/12/12
 */
public class PluginsImpl implements Plugins {

    static String PLUGINS_API = "/api/plugins"
    private ArtifactoryImpl artifactory

    PluginsImpl(ArtifactoryImpl artifactory) {
        this.artifactory = artifactory
    }

    @Override
    Map<PluginType, List<Plugin>> list() {
        artifactory.get(PLUGINS_API, ContentType.JSON, new TypeReference<Map<PluginType, List<PluginImpl>>>() {})
    }

    @Override
    List<Plugin> list(PluginType type) {
        def pluginsMap = artifactory.get("$PLUGINS_API/$type", ContentType.JSON, new TypeReference<Map<PluginType, List<PluginImpl>>>() {})
        pluginsMap[type] as List<Plugin>
    }

    @Override
    PluginHandle execute(String name) {
        new PluginHandleImpl(artifactory, name);
    }
}
