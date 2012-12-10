package org.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.apache.commons.lang.StringUtils
import org.artifactory.client.PropertiesHandler

/**
 *
 * @author jbaruch
 * @since 22/11/12
 */
class PropertiesHandlerImpl implements PropertiesHandler {

    private final ArtifactoryImpl artifactory
    private final String repo
    private final String path

    private Map<String, Iterable<?>> props

    public PropertiesHandlerImpl(ArtifactoryImpl artifactory, String repo, String path) {
        this.path = path
        this.repo = repo
        this.artifactory = artifactory
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
            artifactory.put("/api/storage/$repo/$path", [properties: getPropList(), recursive: recursive ? 1 : 0])
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                artifactory.put("/$repo/$path/;${getPropList().replaceAll(/\|/, ';')}")
            } else {
                throw e
            }
        }
    }

    private String getPropList() {
        def propList = new StringBuilder()
        props.eachWithIndex { entry, pi ->
            if (pi != 0) propList.append('|')
            propList.append(escape(entry.key)).append('=')
            entry.value.eachWithIndex { val, vi ->
                if (vi != 0) propList.append(',')
                propList.append(escape(val))
            }
        }
        propList.toString()
    }

    String escape(Object o) {
        StringUtils.replaceEach(o?.toString(), [',', '\\', '|', '='] as String[], ['\\,', '\\\\', '\\|', '\\='] as String[])
    }
}
