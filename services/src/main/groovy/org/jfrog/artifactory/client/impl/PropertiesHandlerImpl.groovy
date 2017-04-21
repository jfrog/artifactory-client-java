package org.jfrog.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.PropertiesHandler
import org.jfrog.artifactory.client.impl.util.QueryUtil

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
        def propList = QueryUtil.getQueryList(props)
        try {
            artifactory.put(baseApiPath + "/storage/$repo/$path", [properties: propList, recursive: recursive ? 1 : 0])
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                artifactory.put("/$repo/$path/;${propList.replaceAll(/\|/, ';')}")
            } else {
                throw e
            }
        }
    }

}
