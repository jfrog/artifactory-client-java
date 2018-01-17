package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Plugin;
import org.jfrog.artifactory.client.model.builder.PluginBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
public class PluginBuilderImpl implements PluginBuilder {
    private String name;
    private String version;
    private String description;
    private List users = new ArrayList();
    private List groups = new ArrayList();
    private Map<String, String> params = new HashMap();

    public PluginBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PluginBuilder version(String version) {
        this.version = version;
        return this;
    }

    public PluginBuilder description(String description) {
        this.description = description;
        return this;
    }

    public PluginBuilder users(List<String> users) {
        this.users = users;
        return this;
    }

    public PluginBuilder groups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    public PluginBuilder params(Map<String, String> params) {
        if ( !this.params.isEmpty() ){
            this.params.clear();
        }

        this.params.putAll(params);
        return this;
    }

    public Plugin build() {
        return new PluginImpl(name, version, description, users, groups, params);
    }
}
