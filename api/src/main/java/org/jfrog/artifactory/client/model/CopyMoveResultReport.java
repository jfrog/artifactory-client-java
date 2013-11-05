package org.jfrog.artifactory.client.model;

import java.util.List;

/**
 * Created by Jeka on 05/11/13.
 */
public interface CopyMoveResultReport {
    List<CopyMoveResultMessage> getMessages();
}
