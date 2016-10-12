package org.jfrog.artifactory.client;

import junit.framework.Assert;
import org.jfrog.artifactory.client.model.SystemInfo;
import org.jfrog.artifactory.client.model.Version;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author jryan
 * @since 7/9/13
 */
public class SystemTests extends ArtifactoryTestsBase {
    @Test
    public void testPingOnLiveServer() {
        assertTrue(artifactory.system().ping());
    }

    @Test
    public void testLoadVersion() {
        Version version = artifactory.system().version();
        assertNotNull(version.getVersion());
        assertTrue(version.getVersion().contains("."));
        String revision = version.getRevision();
        if (revision.equals("${buildNumber.prop}")) {
            assertTrue(version.getVersion().contains("-SNAPSHOT"));
        } else {
            int rev = Integer.parseInt(revision);
            assertTrue(rev > 0);
        }
        assertTrue(version.getAddons().size() > 5);
        assertNotNull(version.getLicense()); // Since to even perform REST API calls, we need a license
    }

    @Test
    public void testDownloadOfSystemConfiguration() {
        String xml = artifactory.system().configuration();
        assertTrue(xml.contains("backups"));
        assertTrue(xml.contains("localRepositories"));
        assertTrue(xml.contains("repoLayouts"));
    }

    @Test
    public void testUploadOfSystemConfiguration() {
        String oldXml = artifactory.system().configuration();
        String changedXml = oldXml.replace("<excludeBuilds>false</excludeBuilds>", "<excludeBuilds>true</excludeBuilds>");

        artifactory.system().configuration(changedXml);

        String updatedXml = artifactory.system().configuration();
        assertTrue(updatedXml.contains("backups"));
        assertTrue(updatedXml.contains("localRepositories"));
        assertTrue(updatedXml.contains("repoLayouts"));

        // Restore
        String restoredXml = updatedXml.replace("<excludeBuilds>true</excludeBuilds>", "<excludeBuilds>false</excludeBuilds>");
        artifactory.system().configuration(restoredXml);
    }

    @Test
    public void testSystemInfo() {
        SystemInfo info = artifactory.system().info();
        Assert.assertNotNull(info);
        Assert.assertTrue(info.getCommittedVirtualMemorySize() > 0);
        Assert.assertTrue(info.getTotalSwapSpaceSize() > 0);
        Assert.assertTrue(info.getFreeSwapSpaceSize() > 0);
        Assert.assertTrue(info.getProcessCpuTime() > 0);
        Assert.assertTrue(info.getTotalPhysicalMemorySize() > 0);
        Assert.assertTrue(info.getOpenFileDescriptorCount() > 0);
        Assert.assertTrue(info.getMaxFileDescriptorCount() > 0);
        Assert.assertTrue(info.getProcessCpuLoad() > 0.0d);
        Assert.assertTrue(info.getSystemCpuLoad() > 0.0d);
        Assert.assertTrue(info.getFreePhysicalMemorySize() > 0);
        Assert.assertTrue(info.getNumberOfCores() > 0);
        Assert.assertTrue(info.getHeapMemoryUsage() > 0);
        Assert.assertTrue(info.getNoneHeapMemoryUsage() > 0);
        Assert.assertTrue(info.getThreadCount() > 0);
        Assert.assertTrue(info.getNoneHeapMemoryMax() > 0);
        Assert.assertTrue(info.getHeapMemoryMax() > 0);
        Assert.assertTrue(info.getJvmUpTime() > 0);
    }
}
