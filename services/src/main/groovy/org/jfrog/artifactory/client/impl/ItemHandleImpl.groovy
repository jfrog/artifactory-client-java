package org.jfrog.artifactory.client.impl

import groovyx.net.http.HttpResponseException
import org.jfrog.artifactory.client.ItemHandle
import org.jfrog.artifactory.client.PropertiesHandler
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.impl.*

import static groovyx.net.http.ContentType.JSON
import static java.util.Collections.unmodifiableSet
/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ItemHandleImpl implements ItemHandle {

    private ArtifactoryImpl artifactory
    private String repo
    private String path
    private Class<? extends Item>  itemType

    ItemHandleImpl(ArtifactoryImpl artifactory, String repo, String path, Class itemType) {
        this.artifactory = artifactory
        this.repo = repo
        this.path = path
        this.itemType = itemType
    }

    public <T extends Item> T info() {
        assert artifactory
        assert repo
        assert path
        artifactory.get("/api/storage/$repo/$path", JSON, itemType)
    }

    public Map<String, List<String>> getProperties(String... properties) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.get("/api/storage/$repo/$path", [properties: properties.join(',')], JSON, Map.class)?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                return [:]
            }
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

    public PropertiesHandler properties() {
        return new PropertiesHandlerImpl(artifactory, repo, path);
    }

    @Override
    public Map<String, ?> deleteProperties(String... properties) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.delete("/api/storage/$repo/$path", [properties: properties.join(',')])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                return [:]
            }
            throw e
        }
    }

    @Override
    Set<ItemPermission> effectivePermissions() {
        Map json = artifactory.get("/api/storage/${repo}/${path}", ['permissions': null], JSON, Map) as Map
        unmodifiableSet(mapToItemPermissions(json.principals.users, User) + mapToItemPermissions(json.principals.groups, Group) as Set<? extends ItemPermission>) as Set<ItemPermission>
    }


    private Set<? extends ItemPermission> mapToItemPermissions(Map<String, List> map, Class<? extends Subject> type) {
        map.collect { String key, List value ->
            List<Privilege> permissions = value.collect() {
                Privilege.fromAbbreviation(it as char)
            }
            new ItemPermissionImpl(new RepoPathImpl(repo, path), type == User ? new UserImpl(key) : new GroupImpl(key), permissions)
        }
    }

    @Override
    public Map<String, ?> deleteProperty(String property) {
        assert artifactory
        assert repo
        assert path
        try {
            artifactory.delete("/api/storage/$repo/$path", [properties: property])?.properties
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                return [:]
            }
            throw e
        }
    }

    @Override
    ItemHandle move(String toRepo, String toPath) {
        moveOrCopy(toRepo, toPath, 'move')
    }

    @Override
    ItemHandle copy(String toRepo, String toPath) {
        moveOrCopy(toRepo, toPath, 'copy')
    }

    @Override
    Folder create() {
        Folder folder = artifactory.put("/$repo/$path/", [:], JSON, FolderImpl)
        folder.createdBy = artifactory.username
        folder

    }

    private ItemHandle moveOrCopy(String toRepo, String toPath, String operation) {
        CopyMoveResultReport message = artifactory.post("/api/$operation/$repo/$path", [to: "$toRepo/$toPath"], JSON, CopyMoveResultReportImpl) as CopyMoveResultReport
        if (!message.getMessages().get(0).getLevel().equals('INFO')) {
            throw new CopyMoveException(message)
        }
        new ItemHandleImpl(artifactory, toRepo, toPath, folder ? FolderImpl : FileImpl)
    }
}
