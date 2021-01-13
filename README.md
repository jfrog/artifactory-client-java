|Branch|Status|
|:---:|:---:|
|master|[![Build status](https://ci.appveyor.com/api/projects/status/sarjlbpi6dfgrd5w/branch/master?svg=true)](https://ci.appveyor.com/project/jfrog-ecosystem/artifactory-client-java/branch/master)
|dev|[![Build status](https://ci.appveyor.com/api/projects/status/sarjlbpi6dfgrd5w/branch/dev?svg=true)](https://ci.appveyor.com/project/jfrog-ecosystem/artifactory-client-java/branch/dev)

# Artifactory Java Client

Artifactory Java client provides simple yet powerful Artifactory connection and management within your Java code.

The client allows managing Artifactory repositories, users, groups, permissions and system configuration.
It also allows searches, upload and download artifacts to or from Artifactory and a lot more.

## Table of Contents  
[Getting Started](#Getting_Started)<br>
[Building and Testing the Sources](#Building_and_Testing_the_Sources)<br>
[Example Projects](#Example_Projects)<br>
[Contributing Code](#Contributing_Code)<br>
[License](#License)<br>
[Release Notes](#Release_Notes)<br>

<a name="Getting_Started"/>

## Getting Started

### Add *artifactory-java-client-services* as a dependency to your build script.
#### Maven
Add the following dependency to your `pom.xml` file:

```maven
<dependency>
    <groupId>org.jfrog.artifactory.client</groupId>
    <artifactId>artifactory-java-client-services</artifactId>
    <version>2.9.1</version>
</dependency>
```
#### Gradle
Add the following snippets to your `build.gradle` file:

```groovy
repositories {
    jcenter()
}
dependencies {
    compile 'org.jfrog.artifactory.client:artifactory-java-client-services:+'
}
```

### Examples:
This section includes a few usage examples of the Java client APIs from your application code.

#### Setting up Artifactory
```
Artifactory artifactory = ArtifactoryClientBuilder.create()
    .setUrl("ArtifactoryUrl")
    .setUsername("username")
    .setPassword("password")
    .build();
```

Trusting your own self-signed certificates without ignoring any SSL issue:
```
Artifactory artifactory = ArtifactoryClientBuilder.create()
    .setUrl("ArtifactoryUrl")
    .setUsername("username")
    .setPassword("password")
    .setSslContextBuilder(SSLContexts.custom()
        .loadTrustMaterial( <your trust strategy here> ))
    .build();
```

Adding a request interceptor for logging or modifying outgoing requests:
```
Artifactory artifactory = ArtifactoryClientBuilder.create()
    .setUrl("ArtifactoryUrl")
    .setUsername("username")
    .setPassword("password")
    .addInterceptorLast((request, httpContext) -> {
                System.out.println("Artifactory request: "+ request.getRequestLine());
            })
    .build();
```

#### Uploading and downloading artifacts

##### Uploading an Artifacts
```
java.io.File file = new java.io.File("fileToUpload.txt");
File result = artifactory.repository("RepoName").upload("path/to/newName.txt", file).doUpload();
```

##### Upload and explode an Archive
```
java.io.File file = new java.io.File("fileToUpload.txt");
File result = artifactory.repository("RepoName").upload("path/to/newName.txt",file).doUploadAndExplode(true)
```

##### Uploading an Artifact with Properties
```
java.io.File file = new java.io.File("fileToUpload.txt");
File deployed = artifactory.repository("RepoName")
    .upload("path/to/newName.txt", file)
    .withProperty("color", "blue")
    .withProperty("color", "red")
    .doUpload();
```

##### Copy an Artifact by SHA-1
```
java.io.File file = new java.io.File("fileToUpload.txt");
String sha1 = calcSha1(file)

File deployed = artifactory.repository("RepoName")
    .copyBySha1("path/to/newName.txt", sha1)
    .doUpload();
```

##### Downloading an Artifact
```
InputStream iStream = artifactory.repository("RepoName")
    .download("path/to/fileToDownload.txt")
    .doDownload();
```

##### Downloading an Artifact with [non-mandatory Properties](https://www.jfrog.com/confluence/display/RTF4X/Using+Properties+in+Deployment+and+Resolution#UsingPropertiesinDeploymentandResolution-Non-mandatoryProperties)
```
InputStream iStream = artifactory.repository("RepoName")
    .download("path/to/fileToDownload.txt")
    .withProperty("colors", "red")
    .doDownload();
```

##### Downloading Artifact with [mandatory properties](https://www.jfrog.com/confluence/display/RTF4X/Using+Properties+in+Deployment+and+Resolution#UsingPropertiesinDeploymentandResolution-MandatoryProperties)
```
InputStream iStream = artifactory.repository("RepoName")
    .download("path/to/fileToDownload.txt")
    .withMandatoryProperty("colors", "red")
    .doDownload();
```

#### File, Folder and Repository info
##### File Info

```
File file = artifactory.repository("RepoName").file("path/to/file.txt").info();
boolean isFile = file.isFolder();
long fileSize = file.getSize();
String fileUri = file.getDownloadUri();
String md5Checksum = file.getChecksums().getMd5();
String sha1Checksum = file.getChecksums().getSha1();
String sha2Checksum = file.getChecksums().getSha256();
```

##### Folder Info
```
Folder folder = artifactory.repository("RepoName").folder("path/to/folder").info();
boolean isFolder = folder.isFolder();
String repoName = folder.getRepo();
String folderPath = folder.getPath();
int childrenItemsSize = folder.getChildren().size();
```
##### Repository Info

```
Repository repo = artifactory.repository("RepoName").get();
String repoKey = repo.getKey();
String desc = repo.getDescription();
String layout = repo.getRepoLayoutRef();
RepositoryType repoClass = repo.getRclass();

RepositorySettings settings = repo.getRepositorySettings();
PackageType packageType  = settings.getPackageType();

if (PackageType.bower == packageType) {
    BowerRepositorySettings settingsForBower = (BowerRepositorySettings)settings;
    String bowerRegistryUrl = settingsForBower.getBowerRegistryUrl();
}
```

##### Storage Summary Info
```
BinariesSummary binariesSummary = artifactory.storage().getStorageInfo().getBinariesSummary();
FileStorageSummary fileStorageSummary = artifactory.storage().getStorageInfo().getFileStoreSummary();

for (RepositorySummary repoSummary : artifactory.storage().getStorageInfo().getRepositoriesSummaryList()) {
    repoSummary.getRepoKey();
}
```

#### Managing Items (files and folders)

##### Getting Items
```
ItemHandle fileItem = artifactory.repository("RepoName").file("path/to/file.txt");
ItemHandle folderItem = artifactory.repository("RepoName").folder("path/to/folder");
```

##### Copying Items
```
ItemHandle item = ...
ItemHandle newItem = item.copy("ToRepoName", "path/to/item");
```

##### Moving Items
```
ItemHandle item = ...
ItemHandle newItem = item.move("ToRepoName", "path/to/item");
```

##### Deleting Items
```
String result = artifactory.repository("RepoName").delete("path/to/item");
```

#### Managing Repositories
##### List all Repositories
```
import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.LOCAL;
import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.REMOTE;
import static org.jfrog.artifactory.client.model.impl.RepositoryTypeImpl.VIRTUAL;
...
List<LightweightRepository> localRepoList = artifactory.repositories().list(LOCAL);
List<LightweightRepository> remoteRepoList = artifactory.repositories().list(REMOTE);
List<LightweightRepository> virtualRepoList = artifactory.repositories().list(VIRTUAL);
```

##### Creating Repositories
```
DebianRepositorySettingsImpl settings = new DebianRepositorySettingsImpl();
settings.setDebianTrivialLayout(true);

Repository repository = artifactory.repositories()
    .builders()
    .localRepositoryBuilder()
    .key("NewRepoName")
    .description("new local repository")
    .repositorySettings(settings)
    .build();

String result = artifactory.repositories().create(2, repository);
```

###### Repository Settings

For choosing your repository characteristic, use the right settings, each one of them possess the relevant attributes available in Artifactory.

Example for using generic repository with maven layout:
```
RepositorySettings settings = new GenericRepositorySettingsImpl()
settings.setRepoLayout("maven-2-default")
```

##### Updating Repositories
```
Repository repository = artifactory.repository("RepoName").get();
RepositorySettings settings = repository.getRepositorySettings();

if (PackageType.debian == settings.getPackageType()) {
    DebianRepositorySettingsImpl settingsForDebian = (DebianRepositorySettingsImpl) settings;
    settingsForDebian.setDebianTrivialLayout(false);
}

Repository updatedRepository = artifactory.repositories()
    .builders()
    .builderFrom(repository)
    .description("new_description")
    .build();

String result = artifactory.repositories().update(updatedRepository);
```

##### Deleting Repositories
```
String result = artifactory.repository("RepoName").delete();
```

##### Deleting all Repository Replications
```
// Method supported for local and remote repositories
artifactory.repository("RepoName").replications.delete()
```

##### Creating or replacing a replication on a local repository
```
LocalReplication replication = new LocalReplicationBuilderImpl()
    .url("http://hostname1:port/artifactory/RepoName")
    .socketTimeoutMillis(30000)
    .username("john.doe")
    .password("secret")
    .enableEventReplication(false)
    .enabled(false)
    .cronExp("0 0 0/2 * * ?")
    .syncDeletes(true)
    .syncProperties(true)
    .syncStatistics(true)
    .pathPrefix("")
    .repoKey("RepoName")
    .build();

artifactory.repository("RepoName").getReplications().createOrReplace(replication);
```

##### Creating or replacing a replication on a remote repository
```
RemoteReplication replication = new RemoteReplicationBuilderImpl()
    .enabled(false)
    .cronExp("0 0 0/2 * * ?")
    .syncDeletes(true)
    .syncProperties(true)
    .repoKey("RepoName")
    .build();

artifactory.repository("RepoName").getReplications().createOrReplace(replication)
```

##### Managing Xray properties
```
    Repository repository = artifactory.repository("RepoName").get();
    
    XraySettings xraySettings = repository.getXraySettings();
    xraySettings.setXrayIndex(true)

    Repository updatedRepository = artifactory.repositories()
        .builders()
        .builderFrom(repository)
        .description("new_description")
        .build();

    String result = artifactory.repositories().update(updatedRepository);
```

##### Custom Package Type and Properties
```
    CustomPackageTypeImpl customPackageType = new CustomPackageTypeImpl("name");
    CustomRepositorySettingsImpl settings = new CustomRepositorySettingsImpl(customPackageType);

    Map<String, Object> customProperties = new HashMap<>();
    customProperties.put("key", "value");

    Repository repository = artifactory.repositories()
        .builders()
        .localRepositoryBuilder()
        .key("NewRepoName")
        .description("new local repository")
        .repositorySettings(settings)
        .customProperties(customProperties)
        .build();
    
    String result = artifactory.repositories().create(2, repository);
```

##### Smart Remote Repositories

A [smart remote repository](https://www.jfrog.com/confluence/display/RTF/Smart+Remote+Repositories) is a remote repository that proxies a repository from another instance of Artifactory.
Smart remote repositories are configured with four additional properties.

```
        RemoteRepository remoteRepository = (RemoteRepository) artifactory.repository("SmartRemoteRepoName").get();
        ContentSync contentSync = remoteRepository.getContentSync();
        contentSync.setEnabled(true);
        // Report Statistics
        contentSync.getStatistics().setEnabled(true);
        // Sync Properties
        contentSync.getProperties().setEnabled(true);
        // Source Absence Detection
        contentSync.getSource().setOriginAbsenceDetection(true);

        Repository updatedRepository = artifactory.repositories()
            .builders()
            .builderFrom(remoteRepository)
            .listRemoteFolderItems(true)    // List Remote Folder Items
            .build();

        String result = artifactory.repositories().update(updatedRepository);
```

#### Search

##### Available Searches
```
Searches repositories(String... repositories);
Searches artifactsByName(String name);
Searches artifactsCreatedSince(long sinceMillis);
Searches artifactsCreatedInDateRange(long fromMillis, long toMillis);
Searches artifactsByGavc();
Searches artifactsLatestVersion();
```

##### Searching Files in Repositories
```
List<RepoPath> searchItems = artifactory.searches()
    .repositories("RepoName", "RepoName2")
    .artifactsByName("prefixedWith*.txt")
    .doSearch();
                    
for (RepoPath searchItem : searchItems) {
    String repoKey = searchItem.getRepoKey();
    String itemPath = searchItem.getItemPath();
}
```

##### Searching Files by Properties
```
List<RepoPath> searchItems = artifactory.searches()
    .repositories("RepoName", "RepoName2")
    .itemsByProperty()
    .property("released")
    .property("colors", "r*?")
    .doSearch();
                    
for (RepoPath searchItem : searchItems) {
    String repoKey = searchItem.getRepoKey();
    String itemPath = searchItem.getItemPath();
}
```

##### Searching Files by GAVC
```
List<RepoPath> results = artifactory.searches().artifactsByGavc()
    .groupId("com.example")
    .artifactId("com.example.test")
    .version("1.0.0")
    .classifier("zip")
    .doSearch();

for (RepoPath searchItem : searchItems) {
    String repoKey = searchItem.getRepoKey();
    String itemPath = searchItem.getItemPath();
}
```

##### Searching Files by GAVC and Repository
```
List<RepoPath> results = artifactory.searches().artifactsByGavc()
    .groupId("com.example")
    .artifactId("com.example.test")
    .repositories("liba-release-local")
    .doSearch();

for (RepoPath searchItem : searchItems) {
    String repoKey = searchItem.getRepoKey();
    String itemPath = searchItem.getItemPath();
}
```

##### Searching Latest Version by GAVC and Repository
```
String latestVersion = artifactory.searches().artifactsLatestVersion()
    .groupId("com.example")
    .artifactId("com.example.test")
    .repositories("liba-release-local")
    .doRawSearch();
```

#### Managing Users

##### Geting User Information
```
User user = artifactory.security().user("userName");
String name = user.getName();
String email = user.getEmail();
Collection<String> groups = user.getGroups();
Date loggedIn = user.getLastLoggedIn();
boolean profileUpdatable = user.isProfileUpdatable();
boolean isAdmin = user.isAdmin();
boolean internalPass = user.isInternalPasswordDisabled();
String realm = user.getRealm();
```

##### List all User Names
```
Collection<String> userNames = artifactory.security().userNames();
for (String userName : userNames) {
    User user = artifactory.security().user(userName);
}
```

##### Creating or Updating Users
```
UserBuilder userBuilder = artifactory.security().builders().userBuilder();
User user = userBuilder.name("userName")
    .email("user@mail.com")
    .admin(false)
    .profileUpdatable(true)
    .password("password")
    .build();

artifactory.security().createOrUpdate(user);
```

##### Deleting Users
```
String result = artifactory.security().deleteUser("userName");
```

#### Managing User Groups

##### Get User Group Information
```
Group group = artifactory.security().group("groupName");
String description = group.getDescription();
boolean isAutoJoin = group.isAutoJoin();
boolean isAdminPrivileges = group.isAdminPrivileges();
```

##### List all User Groups
```
List<String> groupNames = artifactory.security().groupNames();
for (String groupName : groupNames) {
    Group group = artifactory.security().group(groupName);
}
```

##### Creating or Updating User Groups
```
Group group = artifactory.security().builders().groupBuilder()
    .name("groupName")
    .autoJoin(true)
    .adminPrivileges(true)
    .description("new group")
    .build();

artifactory.security().createOrUpdateGroup(group);
```

##### Creating or Updating User External Groups

When using [LDAP integration](https://www.jfrog.com/confluence/display/RTF/Managing+Security+with+LDAP) or [realm User plugin](https://www.jfrog.com/confluence/display/RTF/User+Plugins#UserPlugins-Realms), it could be interesting to preload groups (and permissions) before any user login :

```
String realmAttributes = "ldapGroupName=groupName;groupsStrategy=STATIC;groupDn=cn=GROUPNAME,ou=foo,o=bar";
Group group = artifactory.security().builders().groupBuilder()
    .name("groupName")
    .description("GROUPNAME")
    .realm("ldap")
    .realmAttributes(realmAttributes)
    .build();
artifactory.security().createOrUpdateGroup(group);
```

**NB**: The *realmAttributes* depends of realm implementation ; so firstly, use [LDAP Groups Synchronization](https://www.jfrog.com/confluence/display/RTF/LDAP+Groups#LDAPGroups-SynchronizingLDAPGroupswithArtifactory) to import some groups manually in Artifactory, and analyse a JSON GET on one of this/these group(s) to understand the content of *realmAttributes* parameter. 

##### Deleting User Groups
```
artifactory.security().deleteGroup("groupName");
```

#### Permissions

##### Getting Item Permissions
```
Set<ItemPermission> itemPermissions = artifactory.repository("RepoName")
    .file("path/to/file.txt")
    .effectivePermissions();
                                        
for (ItemPermission itemPermission : itemPermissions) {
    RepoPath repoPath = itemPermissions.getRepoPath();
    List<Privilege> privileges = getPrivileges();
    Subject subject = getSubject();
    boolean isAllowedTo = isAllowedTo(ADMIN, DELETE, DEPLOY, ANNOTATE, READ);
}
```

##### Getting Permission Target information
```
PermissionTarget permissionTarget = artifactory.security().permissionTarget("permissionName");
String name = permissionTarget.getName());
String exclude = permissionTarget.getExcludesPattern();
String include = permissionTarget.getIncludesPattern();
List<String> repos = permissionTarget.getRepositories();
List<ItemPermission> perm = permissionTarget.getItemPermissions();
```

##### Listing all Permission Targets
```
List<String> permissionTargetNames = artifactory.security().permissionTargets();
for (String permissionTargetName : permissionTargetNames) {
    PermissionTarget permissionTarget = artifactory.security().permissionTarget(permissionTargetName);
}
```

##### Creating a Permission Target
```
Principal userAdmin = artifactory.security().builders().principalBuilder()
    .name("admin")
    .privileges(Privilege.ADMIN)
    .build();

Principal groupTest = artifactory.security().builders().principalBuilder()
    .name("myTest")
    .privileges(Privilege.DEPLOY, Privilege.READ)
    .build();

Principals principals = artifactory.security().builders().principalsBuilder()
    .users(userAdmin)
    .groups(groupTest)
    .build();

PermissionTarget permissionTarget = artifactory.security().builders().permissionTargetBuilder()
    .name("myPermission")
    .principals(principals)
    .repositories("some-repository")
    .includesPattern("com/company/**,org/public/**")
    .build();

artifactory.security().createOrReplacePermissionTarget(permissionTarget);
```

#### System

##### Artifactory version
```
Version version = artifactory.system().version();
String version = version.getVersion();
String revision = version.getRevision();
String license = version.getLicense();
List<String> addons = version.getAddons();
```

##### Getting System Configuration XML
```
String xml = artifactory.system().configuration();
```

##### Setting System Configuration XML
```
String xml = ... 
artifactory.system().configuration(xml);
```

##### Getting System Configuration YAML
```
String yaml = artifactory.system().yamlConfiguration();
```

##### Setting System Configuration YAML
```
String yaml = ... 
artifactory.system().yamlConfiguration(yaml);
```

#### Rest API
Executing an Artifactory REST API
```
ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl().apiUrl("api/repositories")
    .method(ArtifactoryRequest.Method.GET)
    .responseType(ArtifactoryRequest.ContentType.JSON);
ArtifactoryResponse response = artifactory.restCall(repositoryRequest);
  
// Get the response headers
org.apache.http.Header[] headers = response.getAllHeaders();
  
// Get the response status information
org.apache.http.StatusLine statusLine = response.getStatusLine();
  
// A convenience method for verifying success
assert response.isSuccessResponse()
  
// Get the response raw body
String rawBody = response.rawBody();
    
// If the the response raw body has a JSON format, populate an object with the body content, 
// by providing a object's class. 
List<Map<String, String>> parsedBody = response.parseBody(List.class);
```
<a name="Building_and_Testing_the_Sources"/>

## Building and Testing the Sources
The code is built using Gradle and includes integration tests.

Since the tests may use features which have been recently added to Artifactory, such as new package types, it is best to run the tests against the latest release of Artifactory. Some tests may therefore fail otherwise. Those tests can be manually commented out in that case.

If you'd like to build the code without tests, run:
```
> gradle clean build -x test
```

Please follow these steps to build and test the code:
* Startup an Artifactory-Pro instance.
* Set the *CLIENTTESTS_ARTIFACTORY_URL*, *CLIENTTESTS_ARTIFACTORY_USERNAME* and *CLIENTTESTS_ARTIFACTORY_PASSWORD* environment variables with your Artifactory URL, username and password.
* Run:
```
> gradle clean build
```

<a name="Example_Projects"/>

## Example Projects
We created [sample projects](https://github.com/JFrogDev/project-examples/tree/master/artifactory-client-java-examples) demonstrating how to use the Artifactory Java Client.

<a name="Contributing_Code"/>

## Contributing Code
We welcome community contribution through pull requests.

### Guidelines
* If the existing tests do not already cover your changes, please add tests..
* Pull requests should be created on the *dev* branch.

<a name="License"/>

## License
This client is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

<a name="Release_Notes"/>

## Release Notes
Release notes are available on [Bintray](https://bintray.com/jfrog/artifactory-tools/artifactory-client-java#release).

(c) All rights reserved JFrog
