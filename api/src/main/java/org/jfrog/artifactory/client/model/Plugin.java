package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Plugin {
    String getName();

    String getVersion();

    String getDescription();

    List<String> getUsers();

    List<String> getGroups();

    Map<String, String> getParams();

    String getHttpMethod();
}
