package org.artifactory.client

import org.artifactory.client.model.impl.FolderImpl
import org.artifactory.client.model.Folder

/**
 * 
 * @author jbaruch
 * @since 25/07/12
 */
class Items {

    private Artifactory artifactory
    private String repo
    private String path

    Items(Artifactory artifactory, String repo){
        this.artifactory = artifactory
        this.repo = repo
    }

    Items(Artifactory artifactory, String repo, String path){
        this(artifactory, repo)
        this.path = path
    }

    Folder get(){
        assert artifactory
        assert repo
        assert path
        artifactory.getJson("/api/storage/$repo/$path", FolderImpl)
    }
}
