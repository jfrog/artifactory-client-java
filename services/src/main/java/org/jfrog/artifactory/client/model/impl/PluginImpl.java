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

    //Required for JSON parsing of PluginImpl
    private PluginImpl() {
        users = new ArrayList<>();
        groups = new ArrayList<>();
        params = new HashMap<>();
    }

    //Required for JSON parsing of PluginImpl
    private PluginImpl(String name) {
        this.name = name;
    }

    //Required for JSON parsing of PluginImpl
    private PluginImpl(String name, String version, String description, List<String> users, List<String> groups, Map<String, String> params, String httpMethod) {
        this(name, version, description, users, groups, params);
        this.httpMethod = httpMethod;
    }

    protected PluginImpl(String name, String version, String description, List<String> users, List<String> groups, Map<String, String> params) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.users = users;
        this.groups = groups;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getUsers() {
        return unmodifiableList(users);
    }

    public void addUsers(List<String> users) {
        this.users.addAll(users);
    }

    public List<String> getGroups() {
        return unmodifiableList(groups);
    }

    public void addGroups(List<String> groups) {
        this.groups.addAll(groups);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void addParams(Map<String, String> params) {
        this.params.putAll(params);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
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
