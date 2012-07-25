package org.artifactory.client

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

    void get(){
        assert artifactory
        assert repoKey
        assert folderPath
        artifactory.get("/api/storage/$repoKey/$folderPath")
    }
}
