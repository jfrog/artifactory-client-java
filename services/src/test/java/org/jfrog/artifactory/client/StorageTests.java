package org.jfrog.artifactory.client;

import org.jfrog.artifactory.client.model.StorageInfo;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Eyal BM on 30/11/2015.
 */
public class StorageTests extends ArtifactoryTestsBase {

    @Test
    void testStorageSummaryInfo() {
        StorageInfo info = artifactory.storage().getStorageInfo();
        assertNotNull(info);
        assertNotNull(info.getFileStoreSummary());
        assertNotNull(info.getBinariesSummary());
        assertNotNull(info.getRepositoriesSummaryList());
    }
}