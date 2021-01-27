package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.FileList;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface FolderHandle extends ItemHandle {
    FileList list(boolean deep, boolean listFolders, boolean timestamps);
}