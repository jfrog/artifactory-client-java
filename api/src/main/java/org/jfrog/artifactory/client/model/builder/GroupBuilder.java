package org.jfrog.artifactory.client.model.builder;

import org.jfrog.artifactory.client.model.Group;

/**
 * Created by Jeka on 06/12/13.
 */
public interface GroupBuilder {

    GroupBuilder name(String name);

    GroupBuilder autoJoin(boolean autoJoin);

    GroupBuilder description(String description);

    Group build();

}
