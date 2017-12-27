package org.jfrog.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.PropertiesHandler
import org.jfrog.artifactory.client.impl.util.Util

/**
 *
 * @author jbaruch
 * @since 22/11/12
 */
class PropertiesHandlerImpl implements PropertiesHandler {

    private String baseApiPath

    private final ArtifactoryImpl artifactory
    private final String repo
    private final String path

    private Map<String, Iterable<?>> props

    public PropertiesHandlerImpl(ArtifactoryImpl artifactory, String baseApiPath, String repo, String path) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
        this.path = path
        this.repo = repo
        this.props = [:]
    }

    @Override
    PropertiesHandler addProperties(Map<String, ?> properties) {
        properties.each { entry ->
            if (entry.value instanceof Iterable) {
                props.put(entry.key, (Iterable<?>) entry.value)
            } else {
                props.put(entry.key, [entry.value])
            }
        }
        this
    }

    @Override
    PropertiesHandler addProperty(String propertyName, String... values) {
        props.put(propertyName, values as List<String>)
        this
    }

    @Override
    PropertiesHandler addProperty(String propertyName, String value) {
        props.put(propertyName, [value])
        this
    }

    @Override
    void doSet(boolean recursive = false) {
        assert artifactory
        assert repo
        assert path
        if (!props) {
            throw new IllegalStateException("Please add some properties first using add* methods")
        }
        try {
            StringBuilder queryPath = getQueryPath("?properties=");
            queryPath.append("&recursive=");
            if (recursive) {
                queryPath.append("1");
            } else
                queryPath.append("0");

            artifactory.put(baseApiPath + "/storage/$repo/$path" + queryPath.toString(), null, null, new HashMap<String, String>(), null, -1, String, null)
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                StringBuilder queryPath = getQueryPath("")
                artifactory.put("/$repo/$path/;" + queryPath.toString(), null, null, new HashMap<String, String>(), null, -1, String, null)
            } else {
                throw e
            }
        }
    }

    private StringBuilder getQueryPath(String prefix) {
        StringBuilder queryPath = new StringBuilder(prefix);
        if (props) {
            for (String key : props.keySet()) {
                String[] values = props.get(key);
                queryPath.append(Util.encodeParams(key)).append("=");
                for (String value : values) {
                    queryPath.append(Util.encodeParams(value)).append(",")
                }
                queryPath.replace(queryPath.length() - 1, queryPath.length(), ";");
            }
        }
        queryPath
    }

}
