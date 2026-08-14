package org.jfrog.artifactory.client.aql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

import static java.util.stream.Collectors.toMap;

import static org.jfrog.artifactory.client.aql.AqlInclude.buildWithElements;

public class AqlQueryBuilder {
    private AqlRootElement root = new AqlRootElement();
    private AqlItem sort;
    private AqlInclude include;
    private Integer limit;
    private Integer offset;

    public AqlQueryBuilder item(AqlItem item) {
        root.putAll(item.value());
        return this;
    }

    public AqlQueryBuilder elements(AqlItem... items) {
        if (isNotEmpty(items)) {
            root.putAll(Arrays.stream(items)
                    .map(item -> item.value().entrySet())
                    .flatMap(Collection::stream)
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }
        return this;
    }

    public AqlQueryBuilder array(String key, AqlItem... items) {
        if (isNotEmpty(items)) {
            root.put(key, items);
        }
        return this;
    }

    public AqlQueryBuilder and(AqlItem... items) {
        if (isNotEmpty(items)) {
            root.putAll(AqlItem.and((Object[]) items).value());
        }
        return this;
    }

    public AqlQueryBuilder and(Collection<AqlItem> items) {
        return and(setToArray(items));
    }

    public AqlQueryBuilder or(AqlItem... items) {
        if (isNotEmpty(items)) {
            root.putAll(AqlItem.or((Object[]) items).value());
        }
        return this;
    }

    public AqlQueryBuilder match(String key, String pattern) {
        if (key != null) {
            root.putAll(AqlItem.match(key, pattern).value());
        }
        return this;
    }

    public AqlQueryBuilder notMatch(String key, String pattern) {
        if (key != null) {
            root.putAll(AqlItem.notMatch(key, pattern).value());
        }
        return this;
    }

    public AqlQueryBuilder or(Collection<AqlItem> items) {
        return or(setToArray(items));
    }

    public AqlQueryBuilder include(String... elements) {
        if (isNotEmpty(elements)) {
            include = buildWithElements(elements);
        }
        return this;
    }

    public AqlQueryBuilder asc(String... by) {
        if (isNotEmpty(by)) {
            this.sort = AqlItem.asc(by);
        }
        return this;
    }

    public AqlQueryBuilder desc(String... by) {
        if (isNotEmpty(by)) {
            this.sort = AqlItem.desc(by);
        }
        return this;
    }

    public AqlQueryBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public AqlQueryBuilder offset(int offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Plain mapper for AQL structures — no mix-ins or abstract-type resolvers are needed here
     * because AQL only deals with plain Map/Integer/String values. Must not share
     * {@code Util.CONFIGURED_MAPPER} which carries Repository mix-ins inappropriate for this use.
     */
    private static final ObjectMapper PLAIN_MAPPER = new ObjectMapper();

    public String build() {
        try {
            return "items.find(" + getRootAsString() + ")" + getIncludeAsString()
                    + getSortAsString() + getOffsetAsString() + getLimitAsString();
        } catch (JsonProcessingException e) {
            throw new AqlBuilderException("Error serializing object to json: ", e);
        }
    }

    private String getSortAsString() throws JsonProcessingException {
        return hasSort() ? ".sort(" + PLAIN_MAPPER.writeValueAsString(sort) + ")" : "";
    }

    private String getIncludeAsString() {
        return hasInclude() ? include.toString() : "";
    }

    private String getOffsetAsString() throws JsonProcessingException {
        return hasOffset() ? ".offset(" + PLAIN_MAPPER.writeValueAsString(offset) + ")" : "";
    }

    private String getLimitAsString() throws JsonProcessingException {
        return hasLimit() ? ".limit(" + PLAIN_MAPPER.writeValueAsString(limit) + ")" : "";
    }

    private String getRootAsString() throws JsonProcessingException {
        return hasRoot() ? PLAIN_MAPPER.writeValueAsString(root) : "";
    }

    private boolean hasInclude() {
        return include != null && include.isNotEmpty();
    }

    private boolean hasSort() {
        return sort != null && sort.isNotEmpty();
    }

    private boolean hasLimit() {
        return limit != null;
    }

    private boolean hasOffset() {
        return offset != null;
    }

    private boolean hasRoot() {
        return root != null && root.isNotEmpty();
    }

    private AqlItem[] setToArray(Collection<AqlItem> items) {
        return items.toArray(new AqlItem[0]);
    }
}
