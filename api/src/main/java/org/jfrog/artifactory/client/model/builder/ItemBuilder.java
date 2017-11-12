package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.Item;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ItemBuilder {


    ItemBuilder uri(String uri);

    ItemBuilder isFolder(boolean folder);

    Item build();


}
