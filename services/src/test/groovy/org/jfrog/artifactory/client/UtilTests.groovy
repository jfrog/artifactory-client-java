package org.jfrog.artifactory.client

import org.hamcrest.CoreMatchers
import org.jfrog.artifactory.client.impl.util.Util
import org.jfrog.artifactory.client.model.impl.PackageTypeImpl
import org.jfrog.artifactory.client.model.repository.settings.impl.BowerRepositorySettingsImpl
import org.jfrog.artifactory.client.model.repository.settings.impl.MavenRepositorySettingsImpl
import org.junit.Assert
import org.testng.annotations.Test

class UtilTests {

    @Test(groups = "utilTests")
    void getRepositorySettingsForTypeTest() {
        Assert.assertThat(Util.getRepositorySettingsClassForPackageType(PackageTypeImpl.bower), CoreMatchers.equalTo(BowerRepositorySettingsImpl.class))
        Assert.assertThat(Util.getRepositorySettingsClassForPackageType(PackageTypeImpl.maven), CoreMatchers.equalTo(MavenRepositorySettingsImpl.class))
    }

}
