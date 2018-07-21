package org.jfrog.artifactory.client.model.xray.settings.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XraySettingsImpl implements XraySettings {

    private Boolean xrayIndex;

    public Boolean getXrayIndex() {
    return xrayIndex;
  }

    public void setXrayIndex(Boolean xrayIndex) {
    this.xrayIndex = xrayIndex;
  }

    @Override
    public boolean equals(Object o) {
        if (this.is(o)) return true;
        if (!(o instanceof XraySettingsImpl)) return false;

        XraySettingsImpl that = (XraySettingsImpl) o;

        return xrayIndex != null ? xrayIndex.equals(that.xrayIndex) : that.xrayIndex == null;
    }

    @Override
    public int hashCode() {
        int result = xrayIndex != null ? xrayIndex.hashCode() : 0;
        return result;
    }
}
