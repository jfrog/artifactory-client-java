package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.DockerRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.docker.DockerApiVersion;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
public class DockerRepositorySettingsImpl extends AbstractRepositorySettings implements DockerRepositorySettings {
    public static String defaultLayout = "simple-default";
    private DockerApiVersion dockerApiVersion;
    private Boolean enableTokenAuthentication;
    private Boolean listRemoteFolderItems;
    private Integer maxUniqueTags;
    private Integer dockerTagRetention;

    public DockerRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.docker;
    }

    public DockerApiVersion getDockerApiVersion() {
        return dockerApiVersion;
    }

    public void setDockerApiVersion(DockerApiVersion dockerApiVersion) {
        this.dockerApiVersion = dockerApiVersion;
    }

    public Boolean getEnableTokenAuthentication() {
        return enableTokenAuthentication;
    }

    public void setEnableTokenAuthentication(Boolean enableTokenAuthentication) {
        this.enableTokenAuthentication = enableTokenAuthentication;
    }

    public Boolean getListRemoteFolderItems() {
        return listRemoteFolderItems;
    }

    public void setListRemoteFolderItems(Boolean listRemoteFolderItems) {
        this.listRemoteFolderItems = listRemoteFolderItems;
    }

    public Integer getMaxUniqueTags() {
        return maxUniqueTags;
    }

    public void setMaxUniqueTags(Integer maxUniqueTags) {
        this.maxUniqueTags = maxUniqueTags;
    }

    public Integer getDockerTagRetention() {
        return dockerTagRetention;
    }

    public void setDockerTagRetention(Integer dockerTagRetention) {
        this.dockerTagRetention = dockerTagRetention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DockerRepositorySettingsImpl)) return false;

        DockerRepositorySettingsImpl that = (DockerRepositorySettingsImpl) o;

        if (dockerApiVersion != that.dockerApiVersion) return false;
        if (enableTokenAuthentication != null ? !enableTokenAuthentication.equals(that.enableTokenAuthentication) : that.enableTokenAuthentication != null)
            return false;
        if (listRemoteFolderItems != null ? !listRemoteFolderItems.equals(that.listRemoteFolderItems) : that.listRemoteFolderItems != null)
            return false;
        return maxUniqueTags != null ? maxUniqueTags.equals(that.maxUniqueTags) : that.maxUniqueTags == null;
    }

    @Override
    public int hashCode() {
        int result = dockerApiVersion != null ? dockerApiVersion.hashCode() : 0;
        result = 31 * result + (enableTokenAuthentication != null ? enableTokenAuthentication.hashCode() : 0);
        result = 31 * result + (listRemoteFolderItems != null ? listRemoteFolderItems.hashCode() : 0);
        result = 31 * result + (maxUniqueTags != null ? maxUniqueTags.hashCode() : 0);
        return result;
    }
}
