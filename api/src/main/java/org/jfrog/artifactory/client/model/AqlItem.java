package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AqlItem {
    private String repo;
    private String path;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date modified;
    private AqlItemType type;
    @JsonProperty("actual_md5")
    private String actualMd5;
    @JsonProperty("actual_sha1")
    private String actualSha1;
    private Long size;
    private List<Property> properties;

    public String getRepo() {
        return repo;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public AqlItemType getType() {
        return type;
    }

    public String getActualMd5() {
        return actualMd5;
    }

    public String getActualSha1() {
        return actualSha1;
    }

    public Long getSize() {
        return size;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public static class Property {
        @JsonProperty("key")
        private String key;
        @JsonProperty("value")
        private String value;

        public String getkey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
