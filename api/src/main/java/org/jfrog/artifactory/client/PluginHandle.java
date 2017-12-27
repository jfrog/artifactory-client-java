package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

/**
 * @author jbaruch
 * @since 11/12/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface PluginHandle {

    PluginHandle withParameter(String name, String... values);

    PluginHandle withParameters(Map<String, List<String>> parameters);

    String sync();

    String aSync();

}
