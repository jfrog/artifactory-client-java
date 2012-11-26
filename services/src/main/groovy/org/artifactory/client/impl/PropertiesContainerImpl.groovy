package org.artifactory.client.impl

import org.artifactory.client.PropertiesContainer

/**
 * 
 * @author jbaruch
 * @since 22/11/12
 */
class PropertiesContainerImpl implements PropertiesContainer {

    private final ArtifactoryImpl artifactory
    private final String repo
    private final String path

    private Map<String, ?> props

    public PropertiesContainerImpl(ArtifactoryImpl artifactory, String repo, String path){
        this.path = path
        this.repo = repo
        this.artifactory = artifactory
        props = [:]
    }

    @Override
    PropertiesContainer addProperties(Map<String, ?> properties) {
        props << properties
        this
    }

    @Override
    PropertiesContainer addProperty(String propertyName, String... values) {
        props.put(propertyName, values)
        this
    }

    @Override
    PropertiesContainer addProperty(String propertyName, String value) {
        props.put(propertyName, value)
        this
    }

    @Override
    void doSet(boolean recursive = false) {
        assert artifactory
        assert repo
        assert path
        if (!props){
            throw new IllegalStateException("Please add some properties first using add* methods")
        }
        def propList = props.inject([]) {result, entry ->
            def value = entry.value
            if (value.metaClass.respondsTo(value, 'join')) {
              value = value.join(',')
            }
            result << "$entry.key=$value"
        }.join('|')
        artifactory.put("/api/storage/$repo/$path", [properties: propList, recursive: recursive ? 1 : 0])
    }
}
