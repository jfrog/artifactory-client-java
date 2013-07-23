package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.ReplicationStatus;

import java.util.Date;

/**
 * @author Yoav Landman
 */
public class ReplicationStatusImpl implements ReplicationStatus {
    private String status;
    private Date lastCompleted;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Date getLastCompleted() {
        return lastCompleted;
    }
}
