package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.RemoteReplication;

public class RemoteReplicationImpl implements RemoteReplication {

    private boolean enabled;
    private String cronExp;
    private boolean syncDeletes;
    private boolean syncProperties;
    private String repoKey;

    public RemoteReplicationImpl() {
    }

    RemoteReplicationImpl(boolean enabled, String cronExp, boolean syncDeletes, boolean syncProperties, String repoKey) {
        this.enabled = enabled;
        this.cronExp = cronExp;
        this.syncDeletes = syncDeletes;
        this.syncProperties = syncProperties;
        this.repoKey = repoKey;
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
    public String getRepoKey() {
        return repoKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoteReplicationImpl that = (RemoteReplicationImpl) o;

        if (enabled != that.enabled) return false;
        if (syncDeletes != that.syncDeletes) return false;
        if (syncProperties != that.syncProperties) return false;
        if (cronExp != null ? !cronExp.equals(that.cronExp) : that.cronExp != null) return false;

        return repoKey != null ? repoKey.equals(that.repoKey) : that.repoKey == null;
    }

    @Override
    public int hashCode() {
        int result = (enabled ? 1 : 0);
        result = 31 * result + (cronExp != null ? cronExp.hashCode() : 0);
        result = 31 * result + (syncDeletes ? 1 : 0);
        result = 31 * result + (syncProperties ? 1 : 0);
        result = 31 * result + (repoKey != null ? repoKey.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "RemoteReplicationImpl{" +
                "enabled=" + enabled +
                ", cronExp='" + cronExp + '\'' +
                ", syncDeletes=" + syncDeletes +
                ", syncProperties=" + syncProperties +
                ", repoKey='" + repoKey + '\'' +
                '}';
    }
}
