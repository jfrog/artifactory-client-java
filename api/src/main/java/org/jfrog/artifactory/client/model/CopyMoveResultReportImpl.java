package org.jfrog.artifactory.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeka on 05/11/13.
 */
public class CopyMoveResultReportImpl implements CopyMoveResultReport {
    private List<CopyMoveResultMessage> messages = new ArrayList<>();

    @Override
    public List<CopyMoveResultMessage> getMessages() {
        return messages;
    }
}
