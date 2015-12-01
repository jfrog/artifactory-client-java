package org.jfrog.artifactory.client;

import org.testng.annotations.Test;
import org.jfrog.artifactory.client.model.Storageinfo;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Eyal BM on 30/11/2015.
 */
public class StorageTests extends ArtifactoryTestsBase {
    @Test
    void testStorageSummaryInfo() {
        Storageinfo info = artifactory.storage().getStorageinfo();
        assertTrue(info == info);
    }
}