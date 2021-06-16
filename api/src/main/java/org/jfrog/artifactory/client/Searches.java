package org.jfrog.artifactory.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jfrog.artifactory.client.model.AqlItem;
import org.jfrog.artifactory.client.model.RepoPath;
import org.jfrog.filespecs.entities.FileSpec;

import java.util.List;

/**
 * @author jbaruch
 * @author rnaegele
 * @since 13/08/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Searches {

    Searches repositories(String... repositories);

    Searches artifactsByName(String name);

    Searches artifactsCreatedSince(long sinceMillis);

    Searches artifactsCreatedInDateRange(long fromMillis, long toMillis);

    /**
     * Search by maven coordinates.
     * GroupId, artifactId, version and classifier, as well as repositories.
     * @return this object
     */
    Searches artifactsByGavc();

    /**
     * Search for the latest artifact version by groupId and artifactId, based on the layout defined in the repository
     * Search can be limited to specific repositories (local, remote-cache or virtual) by settings the repos parameter. When searching in a virtual repository, each child-repository layout will be consulted accordingly.
     * <p><b>Latest release vs. latest integration:</b> Unless the version parameter is specified, the search returns the latest artifact release version. When version is specified, e.g. 1.0-SNAPSHOT, the result is the latest integration version. Integration versions are determined by the repository layout of the repositories searched. For integration search to work the repository layout requires an "Artifact Path Pattern" that contains the baseRev token and then the fileItegRev token with only literals between them.
     * <p><b>Remote searches:</b> By default only local and cache repositories will be used. When specifying remote=1, Artifactory searches for versions on remote repositories. NOTE! that this can dramatically slow down the search.
     * <p>
     * For Maven repositories the remote maven-metadata.xml will be consulted. For non-Maven layouts, remote file listing runs for all remote repositories that have the 'List Remote Folder Items' checkbox enabled.
     * <p><b>Filtering results (Artifactory 3.0.2+):</b> The version parameter can accept the * and/or ? wildcards which will then filter the final result to match only those who match the given version pattern.
     * <p><b>Artifact path pattern:</b> The [org] and [module] fields must be specified in the artifact path pattern of the repository layout for this call to work.
     * GroupId, artifactId, version and classifier, as well as repositories.
     * <p><b>Notes:</b> Requires Artifactory Pro
     * <p><b>Security:</b> Requires a privileged user (can be anonymous)
     * @since Artifactory 2.6.0
     * @return this object
     */
    Searches artifactsLatestVersion();

    Searches groupId(String groupId);

    Searches artifactId(String artifactId);

    Searches version(String version);

    Searches classifier(String classifierId);

    List<RepoPath> doSearch();

    String doRawSearch();

    PropertyFilters itemsByProperty();

    List<AqlItem> artifactsByFileSpec(FileSpec fileSpec);
}
