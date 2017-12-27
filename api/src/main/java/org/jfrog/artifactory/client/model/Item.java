package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 * @author jbaruch
 * @since 30/07/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Item {
    boolean isFolder();

    String getName();

    String getUri();

    String getPath();

    String getRepo();

    String getMetadataUri();

    Date getLastModified();

    String getModifiedBy();

    Date getLastUpdated();
}
