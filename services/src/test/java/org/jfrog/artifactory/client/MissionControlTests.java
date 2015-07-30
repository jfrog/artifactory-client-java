package org.jfrog.artifactory.client;

import org.testng.annotations.Test;

import java.io.IOException;
import groovyx.net.http.HttpResponseException;


/**
 * Created by user on 30/07/2015.
 */
public class MissionControlTests extends ArtifactoryTestsBase {

    public static final String TOKEN1 = "123";
    public static final String TOKEN2 = "1234";

    @Test
    /**
     * The test should be uncommented when the tests Artifactory server is upgraded to version 4.0.
     */
    public void testCreateAndUpdateConnection() throws IOException {
        /*
        try {
            artifactory.missionControl().createConnection(TOKEN1);
        } catch (Exception e) {
            // In case the connection has already been initialized, we should get
            // HTTP 403 Forbidden here.
            if (e instanceof HttpResponseException) {
                HttpResponseException h = (HttpResponseException)e;
                if (h.getResponse().getStatus() != 403) {
                    throw h;
                }
            } else {
                throw e;
            }
        }

        artifactory.missionControl().updateConnection(TOKEN1, TOKEN2);
        artifactory.missionControl().updateConnection(TOKEN2, TOKEN1);
        */
    }
}
