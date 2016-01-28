package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.MissionControl;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.io.IOException;
import groovyx.net.http.HttpResponseException;


/**
 * Created by Eyal BM on 30/07/2015.
 */
public class MissionControlTests extends ArtifactoryTestsBase {

    public static final String TOKEN1 = "123";
    public static final String TOKEN2 = "1234";

    @Test
    public void testCreateAndUpdateConnection() throws IOException {
        /*
        String missionControlExternalUrl = "http://mc-java-client-test";
        try {
            artifactory.missionControl().createConnection(missionControlExternalUrl, TOKEN1);
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

        assertEquals(NEW_LOCAL, artifactory.missionControl(TOKEN1).repository(NEW_LOCAL).get().getKey());
        artifactory.missionControl(TOKEN1).updateConnection(missionControlExternalUrl, TOKEN2);
        assertEquals(NEW_LOCAL, artifactory.missionControl(TOKEN2).repository(NEW_LOCAL).get().getKey());
        artifactory.missionControl(TOKEN2).updateConnection(missionControlExternalUrl, TOKEN1);
        */
    }
}
