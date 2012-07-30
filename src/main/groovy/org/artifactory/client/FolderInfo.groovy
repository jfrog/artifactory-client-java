package org.artifactory.client

import org.artifactory.client.model.FolderImpl
import org.artifactory.client.model.Folder

/**
 * 
 * @author jbaruch
 * @since 25/07/12
 */
class FolderInfo {

    private Artifactory artifactory
    private String repoKey
    private String folderPath

    FolderInfo(Artifactory artifactory){
        this.artifactory = artifactory
    }

    FolderInfo repoKey(String repoKey){
        this.repoKey = repoKey
        this
    }

    FolderInfo folderPath(String folderPath){
        this.folderPath = folderPath
        this
    }

    Folder get(){
        assert artifactory
        assert repoKey
        assert folderPath
        artifactory.getJson("/api/storage/$repoKey/$folderPath", FolderImpl)
    }
}
