package org.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.artifactory.client.ItemHandle
import org.artifactory.client.PropertiesContainer
import org.artifactory.client.model.*
import org.artifactory.client.model.impl.PermissionsImpl
import org.artifactory.client.model.impl.PrincipalImpl

/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ItemHandleImpl implements ItemHandle {

    private ArtifactoryImpl artifactory
    private String repo
    private String path
    private Class itemType

    ItemHandleImpl(ArtifactoryImpl artifactory, String repo, String path, Class itemType) {
        this.artifactory = artifactory
        this.repo = repo
        this.path = path
        this.itemType = itemType
    }

    public <T> T info() {
        assert artifactory
        assert repo
        assert path
        artifactory.getJson("/api/storage/$repo/$path", itemType)
    }

    public Map<String, List<String>> getProperties(String... properties) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.getJson("/api/storage/$repo/$path", Map, [properties: properties.join(',')])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) return [:]
            throw e
        }
    }

    public List<String> getPropertyValues(String propertyName) {
        Map<String, ?> properties = getProperties([propertyName] as String[])
        properties[propertyName]
    }

    boolean isFolder() {
        return Folder.class.isAssignableFrom(itemType)
    }

    public PropertiesContainer properties() {
        return new PropertiesContainerImpl(artifactory, repo, path);
    }

    @Override
    public Map<String, ?> deleteProperties(String... properties) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.delete("/api/storage/$repo/$path", [properties: properties.join(',')])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) return [:]
            throw e
        }
    }


    @Override
    Permissions effectivePermissions() {
        Map json = artifactory.getJson("/api/storage/${repo}/${path}", Map, ['permissions': null]) as Map
        new PermissionsImpl(mapToPrincipals(json.principals.users), mapToPrincipals(json.principals.groups))
    }

    private static Map<String, Principal> mapToPrincipals(Map<String, List> map) {
        map.collectEntries { String key, List value ->
            List permissions = value.collect() {
                Permission.fromAbbreviation(it as char)
            }
            [key, new PrincipalImpl(key, permissions)]
        } as Map<String, Principal>
    }

    @Override
    public Map<String, ?> deleteProperty(String property) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.delete("/api/storage/$repo/$path", [properties: property])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) return [:]
            throw e
        }
    }
}
