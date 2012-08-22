package org.artifactory.client.model.builder;

import org.artifactory.client.model.Folder;
import org.artifactory.client.model.Item;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch
 * @since 13/08/12
 */
public interface FolderBuilder {

    FolderBuilder uri(String uri);

    FolderBuilder repo(String repo);

    FolderBuilder path(String path);

    FolderBuilder created(Date created);

    FolderBuilder createdBy(String createdBy);

    FolderBuilder modifiedBy(String modifiedBy);

    FolderBuilder lastModified(Date lastModified);

    FolderBuilder lastUpdated(Date lastUpdated);

    FolderBuilder metadataUri(String metadataUri);

    FolderBuilder children(List<Item> children);

    Folder build();

}
