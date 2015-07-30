package org.jfrog.artifactory.client.model;

/**
 * Created by user on 30/07/2015.
 */
public interface MissionControl {

    public static final String MISSION_CONTROL_API = "/mc/";

    public void createConnection(String token);

    public void updateConnection(String oldToken, String newToken);
}
