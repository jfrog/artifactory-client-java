package org.artifactory.client.model.impl;

import org.artifactory.client.model.ReplicationStatus;

import java.util.Date;

/**
 * @author Yoav Landman
 */
public class ReplicationStatusImpl implements ReplicationStatus {
    private String statusDate;
    private Date lastCompleted;

    @Override
    public String getStatusDate() {
        return statusDate;
    }

    @Override
    public Date getLastCompleted() {
        return lastCompleted;
    }
}
