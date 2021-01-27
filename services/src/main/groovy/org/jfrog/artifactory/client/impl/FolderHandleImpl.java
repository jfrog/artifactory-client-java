package org.jfrog.artifactory.client.impl;

import org.jfrog.artifactory.client.FolderHandle;
import org.jfrog.artifactory.client.model.FileList;
import org.jfrog.artifactory.client.model.impl.FileListImpl;

import java.io.IOException;

public class FolderHandleImpl extends ItemHandleImpl implements FolderHandle {

    private final String baseApiPath;

    private final ArtifactoryImpl artifactory;
    private final String repo;
    private final String path;

    FolderHandleImpl(ArtifactoryImpl artifactory, String baseApiPath, String repo, String path, Class itemType) {
        super(artifactory, baseApiPath, repo, path, itemType);
        this.artifactory = artifactory;
        this.repo = repo;
        this.path = path;
        this.baseApiPath = baseApiPath;
    }

    public FileList list(boolean deep, boolean listFolders, boolean timestamps) {
        String deepInt = toInt(deep);
        String listFoldersInt = toInt(listFolders);
        String timestampsInt = toInt(timestamps);
        String url = String.format("%s/storage/%s/%s?list&deep=%s&listFolders=%s&mdTimestamps=%s",
                baseApiPath, repo, path, deepInt, listFoldersInt, timestampsInt);

        FileListImpl fileListImpl = new FileListImpl();
        try {
           return artifactory.get(url, fileListImpl.getClass(), null);
        } catch (IOException e) {
            return null;
        }
    }

    private String toInt(boolean b) {
        return b?"1":"0";
    }
}
