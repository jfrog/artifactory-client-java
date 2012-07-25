package org.artifactory.client

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class Storage {

    static FolderInfo folderInfo(Artifactory artifactory) {
        new FolderInfo(artifactory)
    }

    static FileInfo fileInfo(Artifactory artifactory) {
        new FileInfo(artifactory)
    }

    static ItemLastModified itemLastModified(Artifactory artifactory) {
        new ItemLastModified(artifactory)
    }


}
