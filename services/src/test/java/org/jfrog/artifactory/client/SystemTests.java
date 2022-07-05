package org.jfrog.artifactory.client;

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
        assertNotNull(info);
        assertTrue(info.getCommittedVirtualMemorySize() >= 0L);
        assertTrue(info.getTotalSwapSpaceSize() >= 0L);
        assertTrue(info.getFreeSwapSpaceSize() >= 0L);
        assertTrue(info.getProcessCpuTime() >= 0L);
        assertTrue(info.getTotalPhysicalMemorySize() >= 0L);
        assertTrue(info.getOpenFileDescriptorCount() >= 0L);
        assertTrue(info.getMaxFileDescriptorCount() >= 0L);
        assertTrue(info.getProcessCpuLoad() >= 0.0D);
        assertTrue(info.getSystemCpuLoad() >= 0.0D);
        assertTrue(info.getFreePhysicalMemorySize() >= 0L);
        assertTrue(info.getNumberOfCores() >= 0L);
        assertTrue(info.getHeapMemoryUsage() >= 0L);
        assertTrue(info.getNoneHeapMemoryUsage() >= 0L);
        assertTrue(info.getThreadCount() >= 0);
        assertTrue(info.getNoneHeapMemoryMax() >= 0L);
        assertTrue(info.getHeapMemoryMax() >= 0L);
        assertTrue(info.getJvmUpTime() >= 0L);
    }
}
