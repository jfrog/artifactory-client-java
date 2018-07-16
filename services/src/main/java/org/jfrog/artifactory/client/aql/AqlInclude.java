package org.jfrog.artifactory.client.aql;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.StringJoiner;

public class AqlInclude {
    private String[] elements;

    private AqlInclude(String[] elements) {
      this.elements = elements;
    }

    public static AqlInclude buildWithElements(String[] elements) {
      return new AqlInclude(elements);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        Arrays.stream(elements).forEach(s -> joiner.add(("\"" + s + "\"")));
        return ".include(" + joiner.toString() + ")";
    }

    public boolean isNotEmpty() {
      return ArrayUtils.isNotEmpty(elements);
    }
}
