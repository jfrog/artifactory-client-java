package org.jfrog.artifactory.client.model.repository.settings.impl;

import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl;
import org.jfrog.artifactory.client.model.repository.settings.AbstractRepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.HelmRepositorySettings;

/**
 * @author Glen Lockhart (glen@openet.com)
 */
public class HelmRepositorySettingsImpl extends AbstractRepositorySettings implements HelmRepositorySettings {
    private static String defaultLayout = "simple-default";
    private Integer virtualRetrievalCachePeriodSecs;

    public HelmRepositorySettingsImpl() {
        super(defaultLayout);
    }

    public PackageType getPackageType() {
        return PackageTypeImpl.helm;
    }

    public Integer getVirtualRetrievalCachePeriodSecs() {
        return virtualRetrievalCachePeriodSecs;
    }

    public void setVirtualRetrievalCachePeriodSecs(Integer virtualRetrievalCachePeriodSecs) {
        this.virtualRetrievalCachePeriodSecs = virtualRetrievalCachePeriodSecs;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((virtualRetrievalCachePeriodSecs == null) ? 0 : virtualRetrievalCachePeriodSecs.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HelmRepositorySettingsImpl other = (HelmRepositorySettingsImpl) obj;
        if (virtualRetrievalCachePeriodSecs == null) {
            if (other.virtualRetrievalCachePeriodSecs != null)
                return false;
        } else if (!virtualRetrievalCachePeriodSecs.equals(other.virtualRetrievalCachePeriodSecs))
            return false;
        return true;
    }
    
    
}
