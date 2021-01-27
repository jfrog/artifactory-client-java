package org.jfrog.artifactory.client.model;

import java.util.Date;

public interface ListItem {
    String getUri();
    Long getSize();
    Date getLastModified();
    Boolean isFolder();
    String getSha1();
}
