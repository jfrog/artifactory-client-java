package org.jfrog.artifactory.client.aql;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashMap;
import java.util.Map;

public class AqlRootElement {
    private Map<String, Object> elements = new LinkedHashMap<>();

    public void putAll(Map<String, Object> item) {
        this.elements.putAll(item);
    }

    public void put(String key, Object value) {
        elements.put(key, value);
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !elements.isEmpty();
    }

    @JsonAnyGetter
    public Map<String, Object> getElements() {
        return elements;
    }
}
