package org.artifactory.client.model.builder.impl

import org.artifactory.client.model.Plugin
import org.artifactory.client.model.builder.PluginBuilder
import org.artifactory.client.model.impl.PluginImpl

/**
 * @author jbaruch
 * @since 11/12/12
 */
class PluginBuilderImpl implements PluginBuilder {
    private name = ''
    private version = ''
    private description = ''
    private users = []
    private groups = []
    private params = [:]

    @Override
    public PluginBuilder name(String name) {
        this.name = name
        this
    }

    @Override
    public PluginBuilder version(String version) {
        this.version = version
        this
    }

    @Override
    public PluginBuilder description(String description) {
        this.description = description
        this
    }

    @Override
    public PluginBuilder users(List<String> users) {
        this.users = users
        this
    }

    @Override
    public PluginBuilder groups(List<String> groups) {
        this.groups = groups
        this
    }

    @Override
    public PluginBuilder params(Map<String, String> params) {
        this.params = params
        this
    }

    @SuppressWarnings("GroovyAccessibility")
    @Override
    public Plugin build() {
        new PluginImpl(name, version, description, users, groups, params)
    }
}
