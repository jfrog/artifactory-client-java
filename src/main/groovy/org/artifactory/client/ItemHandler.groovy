package org.artifactory.client

import groovyx.net.http.HttpResponseException
import org.artifactory.client.model.Folder

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ItemHandler {

    private Artifactory artifactory
    private String repo
    private String path
    private Class itemType

    private ItemHandler(Artifactory artifactory, String repo, String path, Class itemType) {
        this.artifactory = artifactory
        this.repo = repo
        this.path = path
        this.itemType = itemType
    }

    public <T> T get() {
        assert artifactory
        assert repo
        assert path
        artifactory.getJson("/api/storage/$repo/$path", itemType)
    }

    public Map<String, ?> getProps(Set props = []) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.getJson("/api/storage/$repo/$path", Map, [properties: props.join(',')])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) return [:]
            throw e
        }
    }

    boolean isFolder() {
        return Folder.class.isAssignableFrom(itemType)
    }

    public setProps(Map<String, ?> props, boolean recursive = false) {
        assert artifactory
        assert repo
        assert path
        def propList = props.inject([]) {result, entry ->
            result << "$entry.key=$entry.value"
        }.join('|')
        artifactory.put("/api/storage/$repo/$path", [properties: propList, recursive: recursive ? 1 : 0])
    }

    public Map<String, ?> deleteProps(Set props = []) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.delete("/api/storage/$repo/$path", [properties: props.join(',')])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) return [:]
            throw e
        }
    }
}
