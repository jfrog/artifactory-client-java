package org.jfrog.artifactory.client.model.repository.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jfrog.artifactory.client.model.PackageType;

/**
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "packageType", defaultImpl = GenericRepositorySettings.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BowerRepositorySettings.class, name = "bower"),
        @JsonSubTypes.Type(value = CocoaPodsRepositorySettings.class, name = "cocoapods"),
        @JsonSubTypes.Type(value = DebianRepositorySettings.class, name = "debian"),
        @JsonSubTypes.Type(value = DockerRepositorySettings.class, name = "docker"),
        @JsonSubTypes.Type(value = GemsRepositorySettings.class, name = "gems"),
        @JsonSubTypes.Type(value = GenericRepositorySettings.class, name = "generic"),
        @JsonSubTypes.Type(value = GitLfsRepositorySettings.class, name = "gitlfs"),
        @JsonSubTypes.Type(value = GradleRepositorySettings.class, name = "gradle"),
        @JsonSubTypes.Type(value = IvyRepositorySettings.class, name = "ivy"),
        @JsonSubTypes.Type(value = MavenRepositorySettings.class, name = "maven"),
        @JsonSubTypes.Type(value = NpmRepositorySettings.class, name = "npm"),
        @JsonSubTypes.Type(value = NugetRepositorySettings.class, name = "nuget"),
        @JsonSubTypes.Type(value = OpkgRepositorySettings.class, name = "opkg"),
        @JsonSubTypes.Type(value = P2RepositorySettings.class, name = "p2"),
        @JsonSubTypes.Type(value = PypiRepositorySettings.class, name = "pypi"),
        @JsonSubTypes.Type(value = SbtRepositorySettings.class, name = "sbt"),
        @JsonSubTypes.Type(value = VagrantRepositorySettings.class, name = "vagrant"),
        @JsonSubTypes.Type(value = VcsRepositorySettings.class, name = "vcs"),
        @JsonSubTypes.Type(value = RpmRepositorySettings.class, name = "rpm"),
        @JsonSubTypes.Type(value = ComposerRepositorySettings.class, name = "composer"),
        @JsonSubTypes.Type(value = ConanRepositorySettings.class, name = "conan"),
        @JsonSubTypes.Type(value = ChefRepositorySettings.class, name = "chef"),
        @JsonSubTypes.Type(value = PuppetRepositorySettings.class, name = "puppet")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface RepositorySettings {

    PackageType getPackageType();

    String getRepoLayout();

    void setRepoLayout(String repoLayout);
}
