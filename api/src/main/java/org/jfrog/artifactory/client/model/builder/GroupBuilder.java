package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Group;

/**
 * Created by Jeka on 06/12/13.
 */
public interface GroupBuilder {

    GroupBuilder name(String name);

    GroupBuilder autoJoin(boolean autoJoin);

    GroupBuilder description(String description);

    /**
     * Realm of the group : 'artifactory' if internal group (default value), 'ldap' if LDAP link, ...
     * 
     * @param realm Realm name
     * @return {@link GroupBuilder}
     */
    GroupBuilder realm(String realm);

    /**
     * Used only for 'external' group (see {@link GroupBuilder#realm(String)})<br>
     * The realm attributes, check a JSON GET group result to understand the content (depends of realm implementation)
     * 
     * @param realmAttributes The attributes
     * @return {@link GroupBuilder}
     */
    GroupBuilder realmAttributes(String realmAttributes);

    Group build();

}
