package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 * @author jbaruch
 * @since 26/11/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Subject extends Serializable {

    String getName();

    String getRealm();

    String getRealmAttributes();

    boolean isGroup();

}
