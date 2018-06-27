package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ihor Banadiga (ihorb@jfrog.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface XraySettings {

  Boolean getXrayIndex();

  void setXrayIndex(Boolean xrayIndex);
}
