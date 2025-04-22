package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Alexei Vainshtein
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultReport {

    private String uri;
    private String downloadUri;
    private String created;

    public String getUri() {
        return uri;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public String getCreated() {
        return created;
    }
}
