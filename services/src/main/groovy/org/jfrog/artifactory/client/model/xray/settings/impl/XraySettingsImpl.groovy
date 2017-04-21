package org.jfrog.artifactory.client.model.xray.settings.impl

import org.jfrog.artifactory.client.model.repository.settings.XraySettings

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
public class XraySettingsImpl implements XraySettings {
  Boolean xrayIndex;
  Boolean blockXrayUnscannedArtifacts;
  String xrayMinimumBlockedSeverity;
}
