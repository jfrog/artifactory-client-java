package org.jfrog.artifactory.client;

import groovyx.net.http.HttpResponseException;
import org.jfrog.artifactory.client.impl.CopyMoveException;
import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.Folder;
import org.jfrog.artifactory.client.model.LocalRepository;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * @author jbaruch
 * @since 03/08/12
 */
@SuppressWarnings("FeatureEnvy")
public class ItemTests extends ArtifactoryTestsBase {

    protected static final String NEW_LOCAL_FROM = "new-local-from";
    protected static final String NEW_LOCAL_TO = "new-local-to";

    @Test
    public void testFolderInfo() {
        Folder folder = artifactory.repository(JCENTER_CACHE).folder("junit").info();
        assertNotNull(folder);
        assertTrue(folder.isFolder());
        assertEquals(folder.getChildren().size(), 1);
        assertEquals(folder.getRepo(), JCENTER_CACHE);
        assertEquals(folder.getPath(), "/junit");
    }

    @Test
    public void testFileInfo() {
        File file = artifactory.repository(JCENTER_CACHE).file("junit/junit/4.10/junit-4.10-sources.jar").info();
        assertNotNull(file);
        assertFalse(file.isFolder());
        assertEquals(file.getSize(), 141185);
        assertEquals(file.getDownloadUri(),
                url + "/" + JCENTER_CACHE + "/junit/junit/4.10/junit-4.10-sources.jar");
        assertEquals(file.getChecksums().getMd5(), "8f17d4271b86478a2731deebdab8c846");
        assertEquals(file.getChecksums().getSha1(), "6c98d6766e72d5575f96c9479d1c1d3b865c6e25");
    }

    @Test(groups = "items", dependsOnGroups = "repositoryBasics")
    public void testSetItemProperties() throws Exception {
        setupLocalRepo(NEW_LOCAL);
        //Upload a clean file
        InputStream content = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(content);
        artifactory.repository(NEW_LOCAL).upload(PATH, content).doUpload();
        ItemHandle file = artifactory.repository(NEW_LOCAL).file(PATH);
        Map<String, ?> resProps = file.getProperties();
        assertTrue(resProps.isEmpty());
        Map<String, String> props = new HashMap<>();
        props.put("p2", "v2");
        props.put("p3", "v3");
        file.properties().addProperty("p1", "v1").addProperties(props).doSet(false);
        resProps = file.getProperties();
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        assertEquals(((List) resProps.get("p2")).get(0), "v2");
        assertEquals(((List) resProps.get("p3")).get(0), "v3");
        resProps = file.getProperties("p1", "p3");
        assertEquals(resProps.size(), 2);
        assertEquals(((List) resProps.get("p1")).get(0), "v1");
        assertEquals(((List) resProps.get("p3")).get(0), "v3");
        file.deleteProperties("p1", "p3");
        List<String> p2 = file.getPropertyValues("p2");
        assertEquals(p2.size(), 1);
        assertEquals(p2.get(0), "v2");

        file.properties().addProperty("multi", "a", "b").doSet();
        List<String> multi = file.getPropertyValues("multi");
        assertEquals(multi.size(), 2);
        assertTrue(multi.contains("a") && multi.contains("b"));

        file.properties().addProperty("label", "<label for=\"male\">Male, | And Female = Love</label>").doSet();
        List<String> specialChars = file.getPropertyValues("label");
        assertEquals(specialChars.size(), 1);
        assertTrue(specialChars.contains("<label for=\"male\">Male, | And Female = Love</label>"));

        file.properties().addProperty("testName", "<b>NetApp FAS/V-Series Storage Replication Adapter</b><br/>Version 2.0.1 | Released 09/10/2012").doSet();
        List<String> pipeTestValues = file.getPropertyValues("testName");
        assertEquals(pipeTestValues.size(), 1);
        assertTrue(pipeTestValues.contains("<b>NetApp FAS/V-Series Storage Replication Adapter</b><br/>Version 2.0.1 | Released 09/10/2012"));

    }

    private void setupLocalRepo(String repoName) {
        try {
            // Make sure the local repo exists
            LocalRepository localRepository = artifactory.repositories().builders().localRepositoryBuilder().key(
                    repoName)
                    .description("new local repository").build();
            artifactory.repositories().create(2, localRepository);
        } catch (Exception e) {
            //noinspection ConstantConditions
            if (!(e instanceof HttpResponseException) || !(((org.apache.http.client.HttpResponseException) e).getStatusCode() == 404 || ((org.apache.http.client.HttpResponseException) e).getStatusCode() == 405)) {
                throw e;
            }
        }
    }

    @Test//(groups = "items", dependsOnGroups = "repositoryBasics")
    public void testSetItemPropertiesOnNonExistingDirectory() throws Exception {
        setupLocalRepo(NEW_LOCAL);
        ItemHandle folder = artifactory.repository(NEW_LOCAL).folder("x/y/z");
        try {
            folder.info();
            //should fail
        } catch (Exception e) {
            //noinspection ConstantConditions
            if (!(e instanceof HttpResponseException) || !(((org.apache.http.client.HttpResponseException) e).getStatusCode() == 404 || ((org.apache.http.client.HttpResponseException) e).getStatusCode() == 405)) {
                throw e;
            }
        }
        folder.properties().addProperty("v1", "b2").doSet();
        Folder info = folder.info();
        assertNotNull(info);
        assertTrue(info.isFolder());
        assertTrue(folder.getPropertyValues("v1").contains("b2"));
    }

    //this test move content of directory "m" to another repo into directory "abc", than both repo's will be removed after finish
    @Test
    public void testMoveDirectory() throws Exception {
        try {
            prepareRepositoriesForMovingAndCoping();
            ItemHandle itemHandle = artifactory.repository(NEW_LOCAL_FROM).folder("m");
            String path = "abc";
            checkTheEqualityOfFolders(itemHandle.move(NEW_LOCAL_TO, path), NEW_LOCAL_TO, path);
        } finally {
            deleteAllRelatedRepos();
        }
    }

    //this test copy content of directory "m" to another repo into directory "abc", than both repo's will be removed after finish
    @Test
    public void testCopyDirectory() throws Exception {
        try {
            prepareRepositoriesForMovingAndCoping();
            ItemHandle itemHandle = artifactory.repository(NEW_LOCAL_FROM).folder("m");
            String path = "abc";
            checkTheEqualityOfFolders(itemHandle.copy(NEW_LOCAL_TO, path), NEW_LOCAL_TO, path);
        } finally {
            deleteAllRelatedRepos();
        }
    }

    //this test move file "sample.txt" to the root of another repo, than both repo's will be removed after finish
    @Test
    public void testMoveFile() throws Exception {
        try {
            prepareRepositoriesForMovingAndCoping();
            ItemHandle itemHandle = artifactory.repository(NEW_LOCAL_FROM).file(PATH);
            ItemHandle newItemHandle = itemHandle.move(NEW_LOCAL_TO, PATH);
            checkTheEqualityOfFiles(newItemHandle, NEW_LOCAL_TO, PATH);
        } finally {
            deleteAllRelatedRepos();
        }
    }

    //this test copy file "z" to the root of another repo, than both repo's will be removed after finish
    @Test
    public void testCopyFile() throws Exception {
        try {
            prepareRepositoriesForMovingAndCoping();
            ItemHandle itemHandle = artifactory.repository(NEW_LOCAL_FROM).file(PATH);
            ItemHandle newItemHandle = itemHandle.copy(NEW_LOCAL_TO, PATH);
            checkTheEqualityOfFiles(newItemHandle, NEW_LOCAL_TO, PATH);
        } finally {
            deleteAllRelatedRepos();
        }
    }

    @Test
    public void testExceptionOnMovingFile() throws Exception {
        prepareRepositoriesForMovingAndCoping();
        ItemHandle itemHandle = artifactory.repository(NEW_LOCAL_FROM).file("a/a");
        try {
            itemHandle.move(NEW_LOCAL_TO, PATH);
        } catch (CopyMoveException e) {
            assertTrue(curl("api/move/" + NEW_LOCAL_FROM + "/a/a?to=" + NEW_LOCAL_TO + "/" + PATH, "POST").contains(e.getCopyMoveResultReport().getMessages().get(0).getMessage()));
        } finally {
            deleteAllRelatedRepos();
        }
    }

    @Test
    public void testExceptionOnCopingFile() throws Exception {
        prepareRepositoriesForMovingAndCoping();
        ItemHandle itemHandle = artifactory.repository(NEW_LOCAL_FROM).file("a/a");
        try {
            itemHandle.copy(NEW_LOCAL_TO, PATH);
        } catch (CopyMoveException e) {
            assertTrue(curl("api/copy/" + NEW_LOCAL_FROM + "/a/a?to=" +  NEW_LOCAL_TO + "/" + PATH, "POST").contains(e.getCopyMoveResultReport().getMessages().get(0).getMessage()));
        } finally {
            deleteAllRelatedRepos();
        }
    }

    private void checkTheEqualityOfFolders(ItemHandle newItemHandle, String expectedRepo, String expectedPath) {
        ItemHandle itemHandle = artifactory.repository(expectedRepo).folder(expectedPath);
        assertEquals(itemHandle.info(), (newItemHandle.info()));
    }

    private void checkTheEqualityOfFiles(ItemHandle newItemHandle, String expectedRepo, String expectedPath) {
        ItemHandle itemHandle = artifactory.repository(expectedRepo).file(expectedPath);
        assertEquals(itemHandle.info(), (newItemHandle.info()));
    }


    private void prepareRepositoriesForMovingAndCoping() throws IOException {
        deleteAllRelatedRepos();
        setupLocalRepo(NEW_LOCAL_FROM);
        setupLocalRepo(NEW_LOCAL_TO);
        InputStream content = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(content);
        artifactory.repository(NEW_LOCAL_FROM).upload(PATH, content).doUpload();
    }

    private void deleteAllRelatedRepos() throws IOException {
        deleteRepoIfExists(NEW_LOCAL_FROM);
        deleteRepoIfExists(NEW_LOCAL_TO);
    }
}
