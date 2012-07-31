package org.artifactory.client;

import org.testng.annotations.BeforeMethod;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.artifactory.client.Artifactory.create;

/**
 * @author jbaruch
 * @since 30/07/12
 */
public class ArtifactoryTests {
    protected Artifactory artifactory;

    @BeforeMethod
    public void init() throws IOException {

        Properties props = new Properties();
        try (FileReader reader = new FileReader("gradle.properties")) { //the jvm should be started from project root
            props.load(reader);
        }
        artifactory = create("http://clienttests.artifactoryonline.com", "clienttests", props.getProperty("username"), props.getProperty("password"));
    }
}
