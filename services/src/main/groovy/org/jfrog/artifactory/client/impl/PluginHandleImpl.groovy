package org.jfrog.artifactory.client.impl

import groovyx.net.http.ContentType
import org.jfrog.artifactory.client.PluginHandle
import org.jfrog.artifactory.client.Plugins
import org.jfrog.artifactory.client.impl.util.QueryUtil

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
        def query = [:]
        if (params) {
            query.params = QueryUtil.getQueryList(params)
        }
        query.async = async ? 1 : 0
        artifactory.post("${plugins.getPluginsApi()}/execute/$name", query, ContentType.TEXT, Class)
    }

}
