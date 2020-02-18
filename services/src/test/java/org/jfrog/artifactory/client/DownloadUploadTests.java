package org.jfrog.artifactory.client;

import org.apache.http.client.HttpResponseException;
import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.Item;
import org.jfrog.artifactory.client.model.impl.FolderImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author jbaruch
 * @since 03/08/12
 */
public class DownloadUploadTests extends ArtifactoryTestsBase {

    private static final int SAMPLE_FILE_SIZE = 3044;
    private static final int SAMPLE_FILE_SIZE_WIN_ENDINGS = 3017;

    @Test
    public void testUploadWithSingleProperty() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, inputStream).withProperty("color", "blue")
                .withProperty("color", "red").doUpload();
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), localRepository.getKey());
        assertEquals(deployed.getPath(), "/" + PATH);
        assertEquals(deployed.getCreatedBy(), username);
        assertEquals(deployed.getDownloadUri(), url + localRepository.getKey() + "/" + PATH);
        assertTrue(deployed.getSize() == 3044 || deployed.getSize() == 3017);
        assertTrue(curlAndStrip("api/storage/" + localRepository.getKey() + "/" + PATH + "?properties").contains("\"color\":[\"red\"]"));
    }

    /**
     * This test requires a 'set_property' user plugin
     *
     * @throws IOException
     */
    @Test
    public void testUploadWithSinglePropertyOnArbitraryLocationNoKeep() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, inputStream).withProperty("color", "red")
                .withProperty("target", "m/a").doUpload();
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), localRepository.getKey());
        assertEquals(deployed.getPath(), "/" + PATH);
        assertEquals(deployed.getCreatedBy(), username);
        assertEquals(deployed.getDownloadUri(), url + localRepository.getKey() + "/" + PATH);
        assertTrue(deployed.getSize() == 3044 || deployed.getSize() == 3017);
        try {
            curlAndStrip("api/storage/" + localRepository.getKey() + "/" + PATH + "?properties");
        } catch (IOException e) {
            assertEquals(e.getClass(), FileNotFoundException.class);
        }
    }

    /**
     * This test requires a 'set_property' user plugin
     *
     * @throws IOException
     */
    @Test
    public void testUploadWithSinglePropertyOnArbitraryLocationAndKeep() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, inputStream).withProperty("color", "red")
                .withProperty("target", "m", "keep").doUpload();
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), localRepository.getKey());
        assertEquals(deployed.getPath(), "/" + PATH);
        assertEquals(deployed.getCreatedBy(), username);
        assertEquals(deployed.getDownloadUri(), url + localRepository.getKey() + "/" + PATH);
        assertTrue(deployed.getSize() == 3044 || deployed.getSize() == 3017);
        assertTrue(curlAndStrip("api/storage/" + localRepository.getKey() + "/" + PATH + "?properties").contains("\"color\":[\"red\"]"));
        assertFalse(curlAndStrip("api/storage/" + localRepository.getKey() + "/" + PATH + "?properties").contains("\"target\":[\"m/a\"]"));
    }

    @Test
    public void testUploadWithListener() throws URISyntaxException, IOException {
        java.io.File file = new java.io.File(this.getClass().getResource("/sample.txt").toURI());
        final long[] uploaded = {0};
        final NumberFormat format = DecimalFormat.getPercentInstance();
        format.setMaximumFractionDigits(4);
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, file).withListener(new UploadListener() {
            @Override
            public void uploadProgress(long bytesRead, long totalBytes) {
                System.out.println("Uploaded " + format.format((double) bytesRead / totalBytes));
                uploaded[0] = bytesRead;
            }
        }).doUpload();
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), localRepository.getKey());
        assertEquals(deployed.getPath(), "/" + PATH);
        assertEquals(deployed.getCreatedBy(), username);
        assertEquals(deployed.getDownloadUri(), url + localRepository.getKey() + "/" + PATH);
        assertTrue(deployed.getSize() == SAMPLE_FILE_SIZE || deployed.getSize() == SAMPLE_FILE_SIZE_WIN_ENDINGS);
        assertTrue(uploaded[0] == SAMPLE_FILE_SIZE || uploaded[0] == SAMPLE_FILE_SIZE_WIN_ENDINGS);
    }

    @Test
    public void testUploadByChecksumWithListener() throws URISyntaxException, IOException {
        //plan: generate new content for the file
        //upload, listener should work
        //upload again, listener shouldn't work (no upload should happen)

        java.io.File file = new java.io.File(this.getClass().getResource("/sample.txt").toURI());
        Path tempFile = Files.createTempFile(null, null);
        java.nio.file.Files.copy(new FileInputStream(file), tempFile, StandardCopyOption.REPLACE_EXISTING);
        Files.write(tempFile, Arrays.asList(Double.toHexString(Math.random())), Charset.defaultCharset(), StandardOpenOption.APPEND);
        java.io.File temp = tempFile.toFile();
        temp.deleteOnExit();
        final long[] uploaded = {0};
        //first upload, should upload content, watch the listener
        artifactory.repository(localRepository.getKey()).upload(PATH, temp).withListener(new UploadListener() {
            @Override
            public void uploadProgress(long bytesRead, long totalBytes) {
                System.out.println("Uploaded " + ((int) ((double) bytesRead / totalBytes * 100)) + "%.");
                uploaded[0] = bytesRead;
            }
        }).bySha1Checksum().doUpload();
        assertEquals(uploaded[0], temp.length());

        //second upload, shouldn't upload a thing!
        File deployed = artifactory.repository(localRepository.getKey()).upload(PATH, temp).withListener(new UploadListener() {
            @Override
            public void uploadProgress(long bytesRead, long totalBytes) {
                Assert.fail("Checksum deploy shouldn't notify listener, since nothing should be uploaded");
            }
        }).bySha1Checksum().doUpload();
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), localRepository.getKey());
        assertEquals(deployed.getPath(), "/" + PATH);
        assertEquals(deployed.getCreatedBy(), username);
        assertEquals(deployed.getDownloadUri(), url + localRepository.getKey() + "/" + PATH);
        assertEquals(deployed.getSize(), temp.length());
    }


    @Test
    public void testUploadWithMavenGAVC() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.zip");
        assertNotNull(inputStream);
        final String targetPath = "com/example/com.example.test/1.0.0/com.example.test-1.0.0-zip.jar";
        File deployed = artifactory.repository(localRepository.getKey()).upload(targetPath, inputStream).doUpload();
        assertNotNull(deployed);
        assertEquals(deployed.getRepo(), localRepository.getKey());
        assertEquals(deployed.getPath(), "/" + targetPath);
        assertEquals(deployed.getCreatedBy(), username);
        // GroupId: com.example; ArtifactId: com.example.test; Version 1.0.0; Classifier: zip
        assertEquals(deployed.getDownloadUri(), url + localRepository.getKey() + "/" + targetPath);
        assertEquals(deployed.getSize(), 442);
    }

    @Test(dependsOnMethods = "testUploadWithSingleProperty")
    public void testUploadExplodeArchive() throws IOException {
        artifactory.repository(localRepository.getKey()).upload("sample/sample.zip", this.getClass().getResourceAsStream("/sample.zip"))
                .doUploadAndExplode();
        List<Item> items = ((FolderImpl) artifactory.repository(localRepository.getKey()).folder("sample").info()).getChildren();
        assertEquals(items.get(0).getUri(), "/a.txt");
        assertEquals(items.get(1).getUri(), "/b.txt");
        assertEquals(items.get(2).getUri(), "/c.txt");
    }

    @Test(dependsOnMethods = "testUploadWithSingleProperty")//to spare all the checks
    public void testUploadWithMultipleProperties() throws IOException {
        artifactory.repository(localRepository.getKey()).upload(PATH, this.getClass().getResourceAsStream("/sample.txt"))
                .withProperty("colors", "red")
                .withProperty("build", 28)
                .withProperty("released", false).doUpload();
        assertTrue(curlAndStrip("api/storage/" + localRepository.getKey() + "/" + PATH + "?properties").contains("{\"build\":[\"28\"],\"colors\":[\"red\"],\"released\":[\"false\"]}"));
    }

    //TODO (jb) enable once RTFACT-5126 is fixed
    @Test(enabled = false, dependsOnMethods = "testUploadWithSingleProperty")
    public void testUploadWithMultiplePropertyValues() throws IOException {
        artifactory.repository(localRepository.getKey()).upload(PATH, this.getClass().getResourceAsStream("/sample.txt"))
                .withProperty("colors", "red", "green", "blue")
                .withProperty("build", 28)
                .withProperty("released", false).doUpload();
        assertTrue(curl("api/storage/" + localRepository.getKey() + "/" + PATH + "?properties")
                .contains("{\"build\":[\"28\"],\"colors\":[\"red\",\"green\",\"blue\"],\"released\":[\"false\"]}"));
    }

    @Test(dependsOnMethods = "testUploadWithSingleProperty")
    public void testUploadCopyBySha1WithSingleProperty() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/sample.txt");
        assertNotNull(inputStream);

        String refPath = "r/a/b/c.txt";
        String sha1 = calcSha1(inputStream);
        File deployed = artifactory.repository(localRepository.getKey())
                .copyBySha1(refPath, sha1)
                .withProperty("color", "red")
                .doUpload();

        assertNotNull(deployed);
        inputStream = artifactory.repository(localRepository.getKey()).download(refPath).doDownload();
        String actual = textFrom(inputStream);
        assertEquals(actual, textFrom(this.getClass().getResourceAsStream("/sample.txt")));
        assertTrue(curlAndStrip("api/storage/" + localRepository.getKey() + "/" + refPath + "?properties").contains("\"color\":[\"red\"]"));
    }

    @Test(dependsOnMethods = "testUploadWithSingleProperty")
    public void testDownloadWithoutProperties() throws IOException {
        InputStream inputStream = artifactory.repository(localRepository.getKey()).download(PATH).doDownload();
        String actual = textFrom(inputStream);
        assertEquals(actual, textFrom(this.getClass().getResourceAsStream("/sample.txt")));
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties")
    public void testDownloadWithMatchingNonMandatoryProperties() throws IOException {
        //property matches
        InputStream inputStream =
                artifactory.repository(localRepository.getKey()).download(PATH).withProperty("colors", "red")
                        .doDownload();
        assertEquals(textFrom(inputStream), textFrom(this.getClass().getResourceAsStream("/sample.txt")));
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties")
    public void testDownloadWithNonExistingNonMandatoryProperties() throws IOException {
        //property doesn't exist
        InputStream inputStream =
                artifactory.repository(localRepository.getKey()).download(PATH).withProperty("foo", "bar").doDownload();
        assertEquals(textFrom(inputStream), textFrom(this.getClass().getResourceAsStream("/sample.txt")));
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties", expectedExceptions = HttpResponseException.class)
    public void testDownloadWithWrongNonMandatoryProperties() throws IOException {
        //property doesn't match, will fail
        artifactory.repository(localRepository.getKey()).download(PATH).withProperty("colors", "black").doDownload();
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties")
    public void testDownloadWithMatchingMandatoryProperties() throws IOException {
        // Property matches
        InputStream inputStream =
                artifactory.repository(localRepository.getKey()).download(PATH).withMandatoryProperty("colors", "red").doDownload();
        assertEquals(textFrom(inputStream), textFrom(this.getClass().getResourceAsStream("/sample.txt")));
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties", expectedExceptions = IOException.class)
    public void testDownloadWithNonExistingMandatoryProperties() throws IOException {
        // Property doesn't exist, will fail
        artifactory.repository(localRepository.getKey()).download(PATH).withMandatoryProperty("foo", "bar").doDownload();
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties", expectedExceptions = HttpResponseException.class)
    public void testDownloadWithWrongMandatoryProperties() throws IOException {
        // Property doesn't match, will fail
        artifactory.repository(localRepository.getKey()).download(PATH).withMandatoryProperty("colors", "black").doDownload();
    }

    @Test(dependsOnMethods = "testUploadWithMultipleProperties")
    public void testDownloadWithMandatoryAndNonMandatoryProperties() throws IOException {
        InputStream inputStream =
                artifactory.repository(localRepository.getKey()).download(PATH).withProperty("released", false)
                        .withProperty("foo", "bar").withMandatoryProperty("colors", "red").doDownload();
        assertEquals(textFrom(inputStream), textFrom(this.getClass().getResourceAsStream("/sample.txt")));
    }
}
