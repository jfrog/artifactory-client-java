package org.jfrog.artifactory.client;

import org.testng.annotations.Test;
import org.jfrog.artifactory.client.model.Storageinfo;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Eyal BM on 30/11/2015.
 */
public class StorageTests extends ArtifactoryTestsBase {

    @Test
    void testStorageSummaryInfo() {
        Storageinfo info = artifactory.storage().getStorageinfo();
        assertNotNull(info);
        assertNotNull(info.getFileStorageSummary());
        assertNotNull(info.getBinariesSummary());
        assertNotNull(info.getRepositoriesSummaryList());
    }
}