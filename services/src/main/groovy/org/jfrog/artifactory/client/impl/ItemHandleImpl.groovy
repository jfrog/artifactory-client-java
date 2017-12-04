package org.jfrog.artifactory.client.impl

import com.fasterxml.jackson.core.type.TypeReference
import groovyx.net.http.HttpResponseException
import org.apache.http.entity.ContentType
import org.jfrog.artifactory.client.ItemHandle
import org.jfrog.artifactory.client.PropertiesHandler
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.*
import org.jfrog.artifactory.client.model.impl.*
import static java.util.Collections.unmodifiableSet
/**
 *
 * @author jbaruch
 * @since 25/07/12
 */
class ItemHandleImpl implements ItemHandle {

    private String baseApiPath

    private ArtifactoryImpl artifactory
    private String repo
    private String path
    private Class<? extends Item> itemType

    ItemHandleImpl(ArtifactoryImpl artifactory, String baseApiPath, String repo, String path, Class itemType) {
        this.artifactory = artifactory
        this.baseApiPath = baseApiPath
        this.repo = repo
        this.path = path
        this.itemType = itemType
    }

    public <T extends Item> T info() {
        assert artifactory
        assert repo
        assert path
        if (itemType.getName().contains("Folder")) {
            return artifactory.get(baseApiPath + "/storage/$repo/$path", FolderImpl, Folder);
        }
        return artifactory.get(baseApiPath + "/storage/$repo/$path", FileImpl, File)
    }

    public Map<String, List<String>> getProperties(String... properties) {
        assert artifactory
        assert repo
        assert path
        try {
            String queryPath = buildParams(properties);
            String result = artifactory.get(baseApiPath + "/storage/$repo/$path" + queryPath, String, null);
            Map<String, List<String>> map = Util.parseObjectWithTypeReference(result, new TypeReference<Map<String, Object>>() {
            });
            return map.get("properties");
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
        return new PropertiesHandlerImpl(artifactory, baseApiPath, repo, path);
    }

    @Override
    public Map<String, ?> deleteProperties(String... properties) {
        assert artifactory
        assert repo
        assert path
        try {
            String queryPath = buildParams(properties)
            artifactory.delete(baseApiPath + "/storage/$repo/$path" + queryPath)?.properties;
        } catch (HttpResponseException e) {
            if (e.statusCode == 404) {
                return [:]
            }
            throw e
        }
    }

    @Override
    Set<ItemPermission> effectivePermissions() {
        Map json = artifactory.get(baseApiPath + "/storage/${repo}/${path}?permissions", HashMap, Map);
        unmodifiableSet(mapToItemPermissions(json.principals.users, User) + mapToItemPermissions(json.principals.groups, Group) as Set<? extends ItemPermission>) as Set<ItemPermission>
    }

    private String buildParams(String... properties) {
        StringBuilder queryPath = new StringBuilder("?properties=");
        for (String property : properties) {
            queryPath.append(Util.encodeParams(property)).append(",");
        }
        queryPath.toString()
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
            String queryPath = "?properties=" + property;
            artifactory.delete(baseApiPath + "/storage/$repo/$path" + queryPath.toString())?.properties;
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
    Folder create() throws IOException {
        Folder folder = artifactory.put("/$repo/$path/", ContentType.APPLICATION_JSON, null, new HashMap<String, String>(), null, -1, FolderImpl, Folder )
        folder.createdBy = artifactory.username
        folder
    }

    private ItemHandle moveOrCopy(String toRepo, String toPath, String operation) {
        StringBuilder queryPath = new StringBuilder("?to=");
        queryPath.append(toRepo).append("/").append(toPath);
        CopyMoveResultReportImpl message = (CopyMoveResultReportImpl)artifactory.post(baseApiPath + "/$operation/$repo/$path" + queryPath.toString(),
                                                    ContentType.APPLICATION_JSON, null, null, CopyMoveResultReportImpl, CopyMoveResultReport)
        if (!message.getMessages().get(0).getLevel().contains('INFO')) {
            throw new CopyMoveException(message)
        }
        new ItemHandleImpl(artifactory, baseApiPath, toRepo, toPath, folder ? FolderImpl : FileImpl)
    }
}
