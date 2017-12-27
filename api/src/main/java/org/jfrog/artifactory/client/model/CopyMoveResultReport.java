package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Created by Jeka on 05/11/13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface CopyMoveResultReport {
    List<CopyMoveResultMessage> getMessages();
}
