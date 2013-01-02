package org.artifactory.client.impl

import org.artifactory.client.PluginHandle
import org.artifactory.client.impl.util.QueryUtil

/**
 *
 * @author jbaruch
 * @since 11/12/12
 */
class PluginHandleImpl implements PluginHandle {

    private ArtifactoryImpl artifactory
    private String name
    private params = [:]

    PluginHandleImpl(ArtifactoryImpl artifactory, String name) {
        this.artifactory = artifactory
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
        def query = [:]
        if (params) {
            query.params = QueryUtil.getQueryList(params)
        }
        query.async = async ? 1 : 0
        artifactory.post("$PluginsImpl.PLUGINS_API/execute/$name", query, null)
    }

}
