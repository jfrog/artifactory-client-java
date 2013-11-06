package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * Created by Jeka on 05/11/13.
 */
public class CopyMoveResultReportImpl implements CopyMoveResultReport {
    private List<CopyMoveResultMessage> messages;

    @Override
    public List<CopyMoveResultMessage> getMessages() {
        return messages;
    }
}
