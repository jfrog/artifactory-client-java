package org.jfrog.artifactory.client.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.LocalRepository;

/**
 * @author jbaruch
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface LocalRepositoryBuilder extends NonVirtualRepositoryBuilder<LocalRepositoryBuilder, LocalRepository> {

}
