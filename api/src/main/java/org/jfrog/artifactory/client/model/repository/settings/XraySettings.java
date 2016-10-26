package org.jfrog.artifactory.client.model.repository.settings;

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
public interface XraySettings {

  Boolean getXrayIndex();

  Boolean getBlockXrayUnscannedArtifacts();

  String getXrayMinimumBlockedSeverity();
}
