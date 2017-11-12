package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 * @author Yoav Landman
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ReplicationStatus {
    String getStatus();

    Date getLastCompleted();
}
