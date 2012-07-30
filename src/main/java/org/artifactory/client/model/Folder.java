package org.artifactory.client.model;

import java.util.Date;
import java.util.List;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public interface Folder extends Item {
    List<ItemImpl> getChildren();

    Date getCreated();

    String getCreatedBy();

    Date getLastModified();

    Date getLastUpdated();

    String getMetadataUri();

    String getModifiedBy();

    String getPath();

    String getRepo();

    class Builder {
        String uri;
        String repo;
        String path;
        Date created;
        String createdBy;
        String modifiedBy;
        Date lastModified;
        Date lastUpdated;
        String metadataUri;
        List<ItemImpl> children;


        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder repo(String repo) {
            this.repo = repo;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder created(Date created) {
            this.created = new Date(created.getTime());
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public Builder lastModified(Date lastModified) {
            this.lastModified = new Date(lastModified.getTime());
            return this;
        }

        public Builder lastUpdated(Date lastUpdated) {
            this.lastUpdated = new Date(lastUpdated.getTime());
            return this;
        }

        public Builder metadataUri(String metadataUri) {
            this.metadataUri = metadataUri;
            return this;
        }

        public Builder children(List<ItemImpl> children) {
            this.children = children;
            return this;
        }

        public static Builder create() {
            return new Builder();
        }

        public Folder build() {
            return new FolderImpl(uri, children, created, createdBy, lastModified, lastUpdated, metadataUri, modifiedBy, path, repo);
        }

        public static Builder copyFrom(FolderImpl from) {
            return new Builder().path(from.path).uri(from.uri).children(from.children).created(from.created).createdBy(from.createdBy).lastModified(from.lastModified)
                    .lastUpdated(from.lastUpdated).repo(from.repo).path(from.path).metadataUri(from.metadataUri);
        }
    }
}
