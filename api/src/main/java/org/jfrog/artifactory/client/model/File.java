package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 * @author jbaruch
 * @since 01/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface File extends Item {
    Date getCreated();

    String getCreatedBy();

    String getDownloadUri();

    String getMimeType();

    long getSize();

    Checksums getChecksums();

    Checksums getOriginalChecksums();
}
