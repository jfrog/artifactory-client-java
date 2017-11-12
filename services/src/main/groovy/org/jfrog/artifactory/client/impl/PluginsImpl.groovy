package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import org.jfrog.artifactory.client.PluginHandle
import org.jfrog.artifactory.client.Plugins
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.Plugin
import org.jfrog.artifactory.client.model.PluginType
import org.jfrog.artifactory.client.model.impl.PluginImpl

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
        String res = artifactory.get(getPluginsApi(), String, null);
        return  Util.parseObjectWithTypeReference(res, new TypeReference<HashMap<PluginType, List<PluginImpl>>>() {
        });
    }

    @Override
    List<Plugin> list(PluginType type) {
        String pluginsMap = artifactory.get("${getPluginsApi()}/$type", String, null)
        Map<PluginType, List<Plugin>> map = Util.parseObjectWithTypeReference(pluginsMap, new TypeReference<HashMap<PluginType, List<PluginImpl>>>() {
        });
        return map.get(type);
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
