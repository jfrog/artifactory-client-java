package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.StorageInfo;

/**
 * Created by Eyal BM on 30/11/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Storage {
    StorageInfo getStorageInfo();
}
