package org.artifactory.client.model;

import java.io.Serializable;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public interface Subject extends Serializable {

    String getName();

    String getRealm();

    boolean isGroup();
}
