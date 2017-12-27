package org.jfrog.artifactory.client.model.xray.settings.impl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import org.jfrog.artifactory.client.model.repository.settings.XraySettings

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class XraySettingsImpl implements XraySettings {
  Boolean xrayIndex;
  Boolean blockXrayUnscannedArtifacts;
  String xrayMinimumBlockedSeverity;
}
