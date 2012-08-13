package org.artifactory.client.model;

import java.util.Date;

/**
 * @author Yoav Landman
 */
public interface ReplicationStatus {
    String getStatus();

    Date getLastCompleted();
}
