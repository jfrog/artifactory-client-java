package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.model.CopyMoveResultReport

import java.util.stream.Collectors

/**
 * Created by Jeka on 05/11/13.
 */
public class CopyMoveException extends RuntimeException {
    private CopyMoveResultReport copyMoveResultReport

    CopyMoveException(CopyMoveResultReport copyMoveResultReport) {
        super(concatMessages(copyMoveResultReport))
        this.copyMoveResultReport = copyMoveResultReport
    }

    CopyMoveResultReport getCopyMoveResultReport() {
        return copyMoveResultReport
    }

    private static String concatMessages(CopyMoveResultReport copyMoveResultReport) {
        copyMoveResultReport.getMessages().stream()
                .map { result -> result.getMessage() }
                .collect(Collectors.joining(", "))
    }
}
