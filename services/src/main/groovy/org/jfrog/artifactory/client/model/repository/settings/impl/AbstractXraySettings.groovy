package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.repository.settings.XraySettings

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
public abstract class AbstractXraySettings implements XraySettings {
  Boolean xrayIndex;
  Boolean blockXrayUnscannedArtifacts;
  String xrayMinimumBlockedSeverity;
}
