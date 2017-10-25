package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.LocalReplication;

public class LocalReplicationImpl implements LocalReplication {

    private String url;
    private long socketTimeoutMillis;
    private String username;
    private String password;
    private boolean enableEventReplication;
    private boolean syncStatistics;
    private boolean enabled;
    private String cronExp;
    private boolean syncDeletes;
    private boolean syncProperties;
    private String pathPrefix;
    private String repoKey;

    public LocalReplicationImpl() {
    }

    LocalReplicationImpl(String url, long socketTimeoutMillis, String username, String password,
        boolean enableEventReplication, boolean syncStatistics, boolean enabled, String cronExp, boolean syncDeletes,
        boolean syncProperties, String pathPrefix, String repoKey) {

        this.url = url;
        this.socketTimeoutMillis = socketTimeoutMillis;
        this.username = username;
        this.password = password;
        this.enableEventReplication = enableEventReplication;
        this.syncStatistics = syncStatistics;
        this.enabled = enabled;
        this.cronExp = cronExp;
        this.syncDeletes = syncDeletes;
        this.syncProperties = syncProperties;
        this.pathPrefix = pathPrefix;
        this.repoKey = repoKey;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public long getSocketTimeoutMillis() {
        return socketTimeoutMillis;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnableEventReplication() {
        return enableEventReplication;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getCronExp() {
        return cronExp;
    }

    @Override
    public boolean isSyncDeletes() {
        return syncDeletes;
    }

    @Override
    public boolean isSyncProperties() {
        return syncProperties;
    }

    @Override
    public boolean isSyncStatistics() {
        return syncStatistics;
    }

    @Override
    public String getPathPrefix() {
        return pathPrefix;
    }

    @Override
    public String getRepoKey() {
        return repoKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalReplicationImpl that = (LocalReplicationImpl) o;

        if (socketTimeoutMillis != that.socketTimeoutMillis) return false;
        if (enableEventReplication != that.enableEventReplication) return false;
        if (syncStatistics != that.syncStatistics) return false;
        if (enabled != that.enabled) return false;
        if (syncDeletes != that.syncDeletes) return false;
        if (syncProperties != that.syncProperties) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (cronExp != null ? !cronExp.equals(that.cronExp) : that.cronExp != null) return false;
        if (pathPrefix != null ? !pathPrefix.equals(that.pathPrefix) : that.pathPrefix != null) return false;

        return repoKey != null ? repoKey.equals(that.repoKey) : that.repoKey == null;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (int) (socketTimeoutMillis ^ (socketTimeoutMillis >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (enableEventReplication ? 1 : 0);
        result = 31 * result + (syncStatistics ? 1 : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (cronExp != null ? cronExp.hashCode() : 0);
        result = 31 * result + (syncDeletes ? 1 : 0);
        result = 31 * result + (syncProperties ? 1 : 0);
        result = 31 * result + (pathPrefix != null ? pathPrefix.hashCode() : 0);
        result = 31 * result + (repoKey != null ? repoKey.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "LocalReplicationImpl{" +
                "url='" + url + '\'' +
                ", socketTimeoutMillis=" + socketTimeoutMillis +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enableEventReplication=" + enableEventReplication +
                ", syncStatistics=" + syncStatistics +
                ", enabled=" + enabled +
                ", cronExp='" + cronExp + '\'' +
                ", syncDeletes=" + syncDeletes +
                ", syncProperties=" + syncProperties +
                ", pathPrefix='" + pathPrefix + '\'' +
                ", repoKey='" + repoKey + '\'' +
                '}';
    }
}
