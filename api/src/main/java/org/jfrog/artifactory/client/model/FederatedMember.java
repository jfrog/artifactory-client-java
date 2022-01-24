package org.jfrog.artifactory.client.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Siva Singaravelan
 * @since 01/05/2022
 */
public class FederatedMember {
    private String url;
    private boolean enabled;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonCreator
    public FederatedMember(@JsonProperty("url") String url, @JsonProperty("enabled") boolean enabled) {
        this.url = url;
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FederatedMember federatedMember = (FederatedMember) o;

        return url.equals(federatedMember.url) ;
    }

    @Override
    public int hashCode() {
        return 31 * url.hashCode();
    }

    @Override
    public String toString() {
        return "FederatedMember{" +
                "url='" + url + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }
}
