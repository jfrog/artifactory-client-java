package org.jfrog.artifactory.client.model.xray.settings.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.repository.settings.XraySettings;

import java.util.Objects;

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XraySettingsImpl implements XraySettings {

  private Boolean xrayIndex;
  private Boolean blockXrayUnscannedArtifacts;
  private String xrayMinimumBlockedSeverity;

  public Boolean getXrayIndex() {
    return xrayIndex;
  }

  public void setXrayIndex(Boolean xrayIndex) {
    this.xrayIndex = xrayIndex;
  }

  public Boolean getBlockXrayUnscannedArtifacts() {
    return blockXrayUnscannedArtifacts;
  }

  public void setBlockXrayUnscannedArtifacts(Boolean blockXrayUnscannedArtifacts) {
    this.blockXrayUnscannedArtifacts = blockXrayUnscannedArtifacts;
  }

  public String getXrayMinimumBlockedSeverity() {
    return xrayMinimumBlockedSeverity;
  }

  public void setXrayMinimumBlockedSeverity(String xrayMinimumBlockedSeverity) {
    this.xrayMinimumBlockedSeverity = xrayMinimumBlockedSeverity;
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XraySettingsImpl)) return false;

        XraySettingsImpl that = (XraySettingsImpl) o;

        if (xrayIndex != null ? !xrayIndex.equals(that.xrayIndex) : that.xrayIndex != null) return false;
        if (blockXrayUnscannedArtifacts != null ? !blockXrayUnscannedArtifacts.equals(that.blockXrayUnscannedArtifacts) : that.blockXrayUnscannedArtifacts != null)
            return false;
        return xrayMinimumBlockedSeverity != null ? xrayMinimumBlockedSeverity.equals(that.xrayMinimumBlockedSeverity) : that.xrayMinimumBlockedSeverity == null;
    }

    @Override
    public int hashCode() {
        int result = xrayIndex != null ? xrayIndex.hashCode() : 0;
        result = 31 * result + (blockXrayUnscannedArtifacts != null ? blockXrayUnscannedArtifacts.hashCode() : 0);
        result = 31 * result + (xrayMinimumBlockedSeverity != null ? xrayMinimumBlockedSeverity.hashCode() : 0);
        return result;
    }
}
