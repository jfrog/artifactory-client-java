package org.jfrog.artifactory.client

import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.impl.BowerRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl
import org.testng.annotations.Test

import static org.testng.Assert.assertEquals

class UtilTests {

    @Test(groups = "utilTests")
    void getRepositorySettingsForTypeTest() {
        assertEquals(Util.getRepositorySettingsClassForPackageType(PackageTypeImpl.bower), BowerRepositorySettingsImpl.class)
        assertEquals(Util.getRepositorySettingsClassForPackageType(PackageTypeImpl.maven), MavenRepositorySettingsImpl.class)
    }

}
