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
        // From version 5.5.0, running a snapshot produced also dev revision.
        if (revision.equals("${buildNumber.prop}") || revision.equals("dev")) {
            assertTrue(version.getVersion().contains("-SNAPSHOT"));
        } else {
            int rev = Integer.parseInt(revision);
            assertTrue(rev > 0);
        }
        assertTrue(version.getAddons().size() > 5);
        assertNotNull(version.getLicense()); // Since to even perform REST API calls, we need a license
    }

    @Test
    public void testSystemInfo() {
        SystemInfo info = artifactory.system().info();
        Assert.assertNotNull(info);
        Assert.assertTrue(info.getCommittedVirtualMemorySize() >= 0L);
        Assert.assertTrue(info.getTotalSwapSpaceSize() >= 0L);
        Assert.assertTrue(info.getFreeSwapSpaceSize() >= 0L);
        Assert.assertTrue(info.getProcessCpuTime() >= 0L);
        Assert.assertTrue(info.getTotalPhysicalMemorySize() >= 0L);
        Assert.assertTrue(info.getOpenFileDescriptorCount() >= 0L);
        Assert.assertTrue(info.getMaxFileDescriptorCount() >= 0L);
        Assert.assertTrue(info.getProcessCpuLoad() >= 0.0D);
        Assert.assertTrue(info.getSystemCpuLoad() >= 0.0D);
        Assert.assertTrue(info.getFreePhysicalMemorySize() >= 0L);
        Assert.assertTrue(info.getNumberOfCores() >= 0L);
        Assert.assertTrue(info.getHeapMemoryUsage() >= 0L);
        Assert.assertTrue(info.getNoneHeapMemoryUsage() >= 0L);
        Assert.assertTrue(info.getThreadCount() >= 0);
        Assert.assertTrue(info.getNoneHeapMemoryMax() >= 0L);
        Assert.assertTrue(info.getHeapMemoryMax() >= 0L);
        Assert.assertTrue(info.getJvmUpTime() >= 0L);
    }
}
