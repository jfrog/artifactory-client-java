package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;

/**
 * @author jbaruch
 * @since 11/12/12
 */
public class PluginImpl implements Plugin {
    private String name;
    private String version;
    private String description;
    private List<String> users;
    private List<String> groups;
    private Map<String, String> params;
    private String httpMethod;

    public PluginImpl() {
        users = new ArrayList<>();
        groups = new ArrayList<>();
        params = new HashMap<>();
    }

    private PluginImpl(String name) {
        this.name = name;
    }

    private PluginImpl(String name, String version, String description, List<String> users, List<String> groups, Map<String, String> params, String httpMethod) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.users = users;
        this.groups = groups;
        this.params = params;
        this.httpMethod = httpMethod;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<String> getUsers() {
        return unmodifiableList(users);
    }

    public void addUsers(List<String> users) {
        this.users.addAll(users);
    }

    @Override
    public List<String> getGroups() {
        return unmodifiableList(groups);
    }

    public void addGroups(List<String> groups) {
        this.groups.addAll(groups);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    public void addParams(Map<String, String> params) {
        this.params.putAll(params);
    }

    @Override
    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginImpl plugin = (PluginImpl) o;

        return name.equals(plugin.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "PluginImpl{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                ", groups=" + groups +
                ", params=" + params +
                ", httpMethod=" + httpMethod +
                '}';
    }
}
