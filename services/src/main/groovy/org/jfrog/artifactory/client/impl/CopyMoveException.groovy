package org.jfrog.artifactory.client.impl

import org.jfrog.artifactory.client.model.CopyMoveResultReport

/**
 * Created by Jeka on 05/11/13.
 */
class CopyMoveException extends RuntimeException {
    private CopyMoveResultReport copyMoveResultReport

    CopyMoveException(CopyMoveResultReport copyMoveResultReport) {
        this.copyMoveResultReport = copyMoveResultReport
    }

    CopyMoveResultReport getCopyMoveResultReport() {
        return copyMoveResultReport
    }
}
