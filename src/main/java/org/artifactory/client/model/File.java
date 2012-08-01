package org.artifactory.client.model;

import java.util.Date;

/**
 * @author jbaruch
 * @since 01/08/12
 */
public interface File extends Item {
    Date getCreated();

    String getCreatedBy();

    String getDownloadUri();

    String getMimeType();

    String getPath();

    String getRepo();

    long getSize();

    Checksums getChecksums();

    Checksums getOriginalChecksums();
}
