package org.jfrog.artifactory.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexei Vainshtein
 */
public class SearchResultImpl implements SearchResult {
    private List<SearchResultReport> results = new ArrayList<>();

    @Override
    public List<SearchResultReport> getResults() {
        return results;
    }
}
