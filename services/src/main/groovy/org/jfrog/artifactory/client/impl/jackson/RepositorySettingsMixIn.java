package org.jfrog.artifactory.client.impl.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;
import org.jfrog.artifactory.client.model.repository.settings.impl.BowerRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.ChefRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.CocoaPodsRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.ComposerRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.ConanRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.DebianRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.DockerRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.GemsRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.GenericRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.GitLfsRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.GradleRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.IvyRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.NpmRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.NugetRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.OpkgRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.P2RepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.PuppetRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.PypiRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.RpmRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.SbtRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.VagrantRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.VcsRepositorySettingsImpl;
import org.jfrog.artifactory.client.model.repository.settings.impl.YumRepositorySettingsImpl;

/**
 * special serialization / deserialization handling for {@link RepositorySettings}
 * 
 * @author Ivan Vasylivskyi (ivanvas@jfrog.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "packageType", defaultImpl = GenericRepositorySettingsImpl.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BowerRepositorySettingsImpl.class, name = "bower"),
    @JsonSubTypes.Type(value = CocoaPodsRepositorySettingsImpl.class, name = "cocoapods"),
    @JsonSubTypes.Type(value = DebianRepositorySettingsImpl.class, name = "debian"),
    @JsonSubTypes.Type(value = DockerRepositorySettingsImpl.class, name = "docker"),
    @JsonSubTypes.Type(value = GemsRepositorySettingsImpl.class, name = "gems"),
    @JsonSubTypes.Type(value = GenericRepositorySettingsImpl.class, name = "generic"),
    @JsonSubTypes.Type(value = GitLfsRepositorySettingsImpl.class, name = "gitlfs"),
    @JsonSubTypes.Type(value = GradleRepositorySettingsImpl.class, name = "gradle"),
    @JsonSubTypes.Type(value = IvyRepositorySettingsImpl.class, name = "ivy"),
    @JsonSubTypes.Type(value = MavenRepositorySettingsImpl.class, name = "maven"),
    @JsonSubTypes.Type(value = NpmRepositorySettingsImpl.class, name = "npm"),
    @JsonSubTypes.Type(value = NugetRepositorySettingsImpl.class, name = "nuget"),
    @JsonSubTypes.Type(value = OpkgRepositorySettingsImpl.class, name = "opkg"),
    @JsonSubTypes.Type(value = P2RepositorySettingsImpl.class, name = "p2"),
    @JsonSubTypes.Type(value = PypiRepositorySettingsImpl.class, name = "pypi"),
    @JsonSubTypes.Type(value = SbtRepositorySettingsImpl.class, name = "sbt"),
    @JsonSubTypes.Type(value = VagrantRepositorySettingsImpl.class, name = "vagrant"),
    @JsonSubTypes.Type(value = VcsRepositorySettingsImpl.class, name = "vcs"),
    @JsonSubTypes.Type(value = YumRepositorySettingsImpl.class, name = "yum"),
    @JsonSubTypes.Type(value = RpmRepositorySettingsImpl.class, name = "rpm"),
    @JsonSubTypes.Type(value = ComposerRepositorySettingsImpl.class, name = "composer"),
    @JsonSubTypes.Type(value = ConanRepositorySettingsImpl.class, name = "conan"),
    @JsonSubTypes.Type(value = ChefRepositorySettingsImpl.class, name = "chef"),
    @JsonSubTypes.Type(value = PuppetRepositorySettingsImpl.class, name = "puppet")
})

public abstract class RepositorySettingsMixIn {
  @JsonIgnore
  abstract String getRepoLayout();
}
