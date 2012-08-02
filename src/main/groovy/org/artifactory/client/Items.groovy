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
    private Class itemType

    private Items(Artifactory artifactory, String repo, String path, Class itemType){
        this.artifactory = artifactory
        this.repo = repo
        this.path = path
        this.itemType = itemType
    }

     public <T> T get(){
        assert artifactory
        assert repo
        assert path
        artifactory.getJson("/api/storage/$repo/$path", itemType)
    }
}
