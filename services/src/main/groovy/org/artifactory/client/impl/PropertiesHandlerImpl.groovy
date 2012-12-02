package org.artifactory.client.impl

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

    private Map<String, ?> props

    public PropertiesHandlerImpl(ArtifactoryImpl artifactory, String repo, String path) {
        this.path = path
        this.repo = repo
        this.artifactory = artifactory
        props = [:]
    }

    @Override
    PropertiesHandler addProperties(Map<String, ?> properties) {
        props << properties
        this
    }

    @Override
    PropertiesHandler addProperty(String propertyName, String... values) {
        props.put(propertyName, values)
        this
    }

    @Override
    PropertiesHandler addProperty(String propertyName, String value) {
        props.put(propertyName, value)
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
        def propList = props.inject([]) { result, entry ->
            def value = entry.value
            if (value.metaClass.respondsTo(value, 'join')) {
                value = value.join(',')
            }
            result << "$entry.key=$value"
        }.join('|')
        artifactory.put("/api/storage/$repo/$path", [properties: propList, recursive: recursive ? 1 : 0])
    }
}
