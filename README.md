# Artifactory Java Client

Artifactory Java client provides simple yet powerful Artifactory connection and management within your Java code.

The client aallows managing Artifactory repositories, users, groups, permissions and system configuration.
It also allows searches, upload and download artifacts to or from Artifactory and a lot more.

## Getting Started

### Add *artifactory-java-client-services* as a dependency to your build script.
#### Maven
Add the following dependency to your `pom.xml` file:

```maven
<dependency>
    <groupId>org.jfrog.artifactory.client</groupId>
    <artifactId>artifactory-java-client-services</artifactId>
    <version>LATEST</version>
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
Artifactory artifactory = ArtifactoryClient.create("ArtifactoryUrl", "username", "password");
```
#### Uploading and downloading artifacts

##### Uploading an Artifacts
```
File file = new File("fileToUpload.txt");
File result = artifactory.repository("RepoName").upload("path/to/newName.txt", file).doUpload();
```

##### Uploading an Artifact with Properties
```
File file = new File("fileToUpload.txt");
File deployed = artifactory.repository("RepoName")
    .upload("path/to/newName.txt", file)
    .withProperty("color", "blue")
    .withProperty("color", "red")
    .doUpload();
```

##### Downloading an Artifact
```
InputStream iStream = artifactory.repository("RepoName")
    .download("path/to/fileToDownload.txt")
    .doDownload();
```

##### Downloading an Artifact with properties
```
InputStream iStream = artifactory.repository("RepoName")
    .download("path/to/fileToDownload.txt")
    .withProperty("colors", "red")
    .doDownload();
```

##### Downloading Artifact with mandatory properties
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
List<LightweightRepository> repoList = artifactory.repositories().list(LOCAL);
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

##### Managing Xray properties
```
    Repository repository = artifactory.repository("RepoName").get();
    
    XraySettings xraySettings = repository.getXraySettings();
    xraySettings.setXrayIndex(true)
    xraySettings.setBlockXrayUnscannedArtifacts(true)
    xraySettings.setXrayMinimumBlockedSeverity('Minor')

    Repository updatedRepository = artifactory.repositories()
        .builders()
        .builderFrom(repository)
        .description("new_description")
        .build();

    String result = artifactory.repositories().update(updatedRepository);
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
User user = userBuilder.name("userName)
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
Group group = groupBuilder.name("groupName").autoJoin(true).description("new group").build();
artifactory.security().createOrUpdateGroup(group);
```

##### Creating or Updating User External Groups

When using [LDAP integration](https://www.jfrog.com/confluence/display/RTF/Managing+Security+with+LDAP) or [realm User plugin](https://www.jfrog.com/confluence/display/RTF/User+Plugins#UserPlugins-Realms), it could be interesting to preload groups (and permissions) before any user login :

```
String realmAttributes = "ldapGroupName=groupName;groupsStrategy=STATIC;groupDn=cn=GROUPNAME,ou=foo,o=bar";
Group group = groupBuilder.name("groupName")
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

#### Rest API
Executing an Artifactory REST API
```
ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl().apiUrl("api/repositories")
    .method(ArtifactoryRequest.Method.GET)
    .responseType(ArtifactoryRequest.ContentType.JSON);
List<String> response = artifactory.restCall(repositoryRequest);
```

## Building and Testing the Sources
The code is built using Gradle and includes integration tests.
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

## Contributing Code
We welcome community contribution through pull requests.

# License
This client is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

(c) All rights reserved JFrog
