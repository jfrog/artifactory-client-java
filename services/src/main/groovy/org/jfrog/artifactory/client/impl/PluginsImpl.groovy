package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.ContentType
import net.sf.json.JSON
import org.jfrog.artifactory.client.PluginHandle
import org.jfrog.artifactory.client.Plugins
import org.jfrog.artifactory.client.model.Plugin
import org.jfrog.artifactory.client.model.PluginType
import org.jfrog.artifactory.client.model.impl.PluginImpl

import static groovyx.net.http.ContentType.*

/**
 * @author jbaruch
 * @since 11/12/12
 */
public class PluginsImpl implements Plugins {

    private String baseApiPath
    private ArtifactoryImpl artifactory

    PluginsImpl(ArtifactoryImpl artifactory, String baseApiPath) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
    }

    @Override
    Map<PluginType, List<Plugin>> list() {
        artifactory.get(getPluginsApi(), ContentType.JSON, new TypeReference<Map<PluginType, List<PluginImpl>>>() {})
    }

    @Override
    List<Plugin> list(PluginType type) {
        def pluginsMap = artifactory.get("${getPluginsApi()}/$type", ContentType.JSON, new TypeReference<Map<PluginType, List<PluginImpl>>>() {})
        pluginsMap[type] as List<Plugin>
    }

    @Override
    PluginHandle execute(String name) {
        new PluginHandleImpl(artifactory, this, name);
    }

    @Override
    String getPluginsApi() {
        return baseApiPath + "/plugins";
    }
}
