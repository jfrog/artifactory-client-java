package org.artifactory.client

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Storage {

    private Artifactory artifactory

    Storage(Artifactory artifactory) {
        this.artifactory = artifactory
    }

    FolderInfo folderInfo() {
        new FolderInfo(artifactory)
    }

    FileInfo fileInfo() {
        new FileInfo(artifactory)
    }

    ItemLastModified itemLastModified() {
        new ItemLastModified(artifactory)
    }


}
