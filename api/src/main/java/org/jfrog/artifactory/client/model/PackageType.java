package org.jfrog.artifactory.client.model;

/**
 * @author Serhii Pichkurov (serhiip@jfrog.com)
 */
public interface PackageType {
    String name();

    boolean isCustom();

    String getLayout();
}
