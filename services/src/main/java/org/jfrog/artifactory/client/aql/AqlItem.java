package org.jfrog.artifactory.client.aql;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class AqlItem {
    private static final String ASC = "$asc";
    private static final String DESC = "$desc";
    private static final String OR = "$or";
    private static final String AND = "$and";
    private static final String MATCH = "$match";
    private static final String NOT_MATCH = "$nmatch";

    private Map<String, Object> item;

    private AqlItem(String key, Object value) {
        item = new HashMap<>();
        item.put(key, value);
    }

    public static AqlItem aqlItem(String key, Object value) {
        return new AqlItem(key, value);
    }

    public static AqlItem and(Object... items) {
        return new AqlItem(AND, items);
    }

    public static AqlItem or(Object... items) {
        return new AqlItem(OR, items);
    }

    public static AqlItem asc(String... items) {
        return new AqlItem(ASC, items);
    }

    public static AqlItem desc(String... items) {
        return new AqlItem(DESC, items);
    }

    public static AqlItem match(String key, String pattern) {
        return new AqlItem(key, new AqlItem(MATCH, pattern));
    }

    public static AqlItem notMatch(String key, String pattern) {
        return new AqlItem(key, new AqlItem(NOT_MATCH, pattern));
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return item != null && !item.isEmpty();
    }

    @JsonAnyGetter
    public Map<String, Object> value() {
        return this.item;
    }
}
