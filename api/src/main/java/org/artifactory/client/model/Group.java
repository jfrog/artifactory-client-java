package org.artifactory.client.model;

/**
 * @author jbaruch
 * @since 26/11/12
 */
public interface Group extends Subject {

    String getDescription();

    boolean isAutoJoin();
}
