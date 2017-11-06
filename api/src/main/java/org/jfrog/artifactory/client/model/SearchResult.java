package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * @author Alexei Vainshtein
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface SearchResult {
    List<SearchResultReport> getResults();
}
