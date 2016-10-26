package org.jfrog.artifactory.client.model.repository.settings;

public interface XraySettings {

  Boolean getXrayIndex();

  Boolean getBlockXrayUnscannedArtifacts();

  String getXrayMinimumBlockedSeverity();
}
