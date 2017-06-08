package org.jfrog.artifactory.client

import org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.testng.Assert.assertTrue

class OtherPropertiesRepositoryTests extends BaseRepositoryTests {

    @BeforeMethod
    protected void setUp() {
        otherProperties = [
            "enableComposerSupport" : true
        ]
        super.setUp()
    }

    @Test(groups = "otherProperties")
    void testDefaultLocalRepo() {
        localRepo.otherProperties = null
        artifactory.repositories().create(0, localRepo)
        assertTrue(curl(LIST_PATH).contains(localRepo.getKey()))

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.with {
            assertThat(rclass, is(RepositoryTypeImpl.LOCAL))
            assertThat(otherProperties, notNullValue())
            assertThat(otherProperties.isEmpty(), is(false))
            assertThat(otherProperties.get("enableComposerSupport"), is(false))
        }
    }

    @Test(groups = "otherProperties")
    void testLocalRepo() {
        artifactory.repositories().create(0, localRepo)
        assertTrue(curl(LIST_PATH).contains(localRepo.getKey()))

        def resp = artifactory.repository(localRepo.getKey()).get()
        resp.with {
            assertThat(rclass, is(RepositoryTypeImpl.LOCAL))
            assertThat(otherProperties, notNullValue())
            assertThat(otherProperties.isEmpty(), is(false))
            assertThat(otherProperties.get("enableComposerSupport"), is(true))
        }
    }

    @Test(groups = "otherProperties")
     void testRemoteRepo() {
        artifactory.repositories().create(0, remoteRepo)
        assertTrue(curl(LIST_PATH).contains(remoteRepo.getKey()))

        def resp = artifactory.repository(remoteRepo.getKey()).get()
        resp.with {
            assertThat(rclass, is(RepositoryTypeImpl.REMOTE))
            assertThat(otherProperties, notNullValue())
            assertThat(otherProperties.isEmpty(), is(false))
            assertThat(otherProperties.get("enableComposerSupport"), is(true))
        }
    }

    @Test(groups = "otherProperties")
    void testVirtualRepo() {
        artifactory.repositories().create(0, virtualRepo)
        assertTrue(curl(LIST_PATH).contains(virtualRepo.getKey()))

        def resp = artifactory.repository(virtualRepo.getKey()).get()
        resp.with {
            assertThat(rclass, is(RepositoryTypeImpl.VIRTUAL))
            assertThat(otherProperties, notNullValue())
            assertThat(otherProperties.isEmpty(), is(false))
            assertThat(otherProperties.get("enableComposerSupport"), is(true))
        }
    }
}
