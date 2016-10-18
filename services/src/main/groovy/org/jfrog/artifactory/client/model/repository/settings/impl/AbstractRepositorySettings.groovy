package org.jfrog.artifactory.client.model.repository.settings.impl

import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
public abstract class AbstractRepositorySettings implements RepositorySettings {
  Boolean xrayIndex;
  Boolean blockXrayUnscannedArtifacts;
  String xrayMinimumBlockedSeverity;
}
