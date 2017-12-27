package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.PluginHandle
import org.jfrog.artifactory.client.Plugins
import org.jfrog.artifactory.client.impl.util.Util

/**
 *
 * @author jbaruch
 * @since 11/12/12
 */
class PluginHandleImpl implements PluginHandle {

    private ArtifactoryImpl artifactory
    private Plugins plugins
    private String name
    private params = [:]

    PluginHandleImpl(ArtifactoryImpl artifactory, Plugins plugins, String name) {
        this.artifactory = artifactory
        this.plugins = plugins
        this.name = name;
    }

    @Override
    PluginHandle withParameter(String name, String... values) {
        params[name] = values as List
        return this
    }

    @Override
    PluginHandle withParameters(Map<String, List<String>> parameters) {
        params << parameters
        this
    }

    @Override
    String sync() {
        doExecute(false)
    }

    @Override
    String aSync() {
        doExecute(true)
    }

    String doExecute(boolean async) {
        StringBuilder queryPath = new StringBuilder("?params=");
        if (params) {
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                queryPath.append(Util.encodeParams(key)).append("=");
                for (String value : values) {
                    queryPath.append(Util.encodeParams(value)).append(",")
                }
                queryPath.replace(queryPath.length()-1, queryPath.length(), ";");
            }
        }
        int asyncValue = async ? 1 : 0;
        queryPath.append("async&=").append(asyncValue);
        artifactory.post("${plugins.getPluginsApi()}/execute/$name" + queryPath.toString(), org.apache.http.entity.ContentType.TEXT_PLAIN, null, new HashMap<String, String>(), String, null )
    }

}
