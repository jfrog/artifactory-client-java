package org.jfrog.artifactory.client.model;

import java.util.Date;
import java.util.List;

public interface FileList {

    String getUri();
    Date getCreated();
    List<ListItem> getFiles();
}
