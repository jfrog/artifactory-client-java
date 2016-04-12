artifactory-client-java
=======================

Artifactory REST Client Java API bindings

[ ![Download](https://api.bintray.com/packages/jfrog/artifactory-tools/artifactory-client-java/images/download.png) ](https://bintray.com/jfrog/artifactory-tools/artifactory-client-java/_latestVersion)

# Getting Started
### Add the artifactory-java-client-services as a dependency to your build script.
#### Maven
Add the following dependency to your `pom.xml` file:

```maven
<dependency>
  <groupId>org.jfrog.artifactory.client</groupId>
  <artifactId>artifactory-java-client-api</artifactId>
  <version>LATEST</version>
  <type>pom</type>
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
This section includes a few examples for using the Artifactory Java client APIs for your application code.

#### Setting up Artifactory
```
Artifactory artifactory = ArtifactoryClient.create("ArtifactoryUrl", "username", "password");
```
#### Downloading, uploading artifacts

##### Uploading artifacts
```
File file = new File("fileToUpload.txt");
File result = artifactory.repository("RepoName").upload("path/to/newName.txt", file).doUpload();
```

##### Upload artifact with property
```
File file = new File("fileToUpload.txt");
File deployed = artifactory.repository("RepoName")
        .upload("path/to/newName.txt", file)
        .withProperty("color", "blue")
        .withProperty("color", "red")
        .doUpload();
```

##### Download artifact
```
InputStream iStream = artifactory.repository("RepoName")
        .download("path/to/fileToDownload.txt")
        .doDownload();
```
##### Downloading artifact with property
```
InputStream iStream = artifactory.repository("RepoName")
        .download("path/to/fileToDownload.txt")
        .withProperty("colors", "red")
        .doDownload();
```

##### Downloading artifact with mandatory property
```
InputStream iStream = artifactory.repository("RepoName")
        .download("path/to/fileToDownload.txt")
        .withMandatoryProperty("colors", "red")
        .doDownload();
```

#### File, folder, repo information

##### File info

```
File file = artifactory.repository("RepoName").file("path/to/file.txt").info();
boolean isFile      = file.isFolder();
long fileSize       = file.getSize();
String fileUri      = file.getDownloadUri();
String md5Checksum  = file.getChecksums().getMd5();
String sha1Checksum = file.getChecksums().getSha1();
```

##### Folder info
```
Folder folder = artifactory.repository("RepoName").folder("path/to/folder").info();
boolean isFolder      = folder.isFolder();
String repoName       = folder.getRepo();
String folderPath     = folder.getPath();
int childrenItemsSize = folder.getChildren().size();
```
##### Repo info

```
Repository repo = artifactory.repository("RepoName").get();
String repoKey           = repo.getKey();           //RepoName
String desc              = repo.getDescription();
String layout            = repo.getRepoLayoutRef(); //maven-2-default
PackageType packageType  = repo.getPackageType();   //maven
RepositoryType repoClass = repo.getRclass();        //local
```

#### Managing items

##### Getting item
```
ItemHandle fileItem   = artifactory.repository("RepoName").file("path/to/file.txt");
ItemHandle folderItem = artifactory.repository("RepoName").folder("path/to/folder");
```

##### Copy item
```
ItemHandle item    = ...
ItemHandle newItem = item.copy("ToRepoName", "path/to/item");
```

##### Move item
```
ItemHandle item    = ...
ItemHandle newItem = item.move("ToRepoName", "path/to/item");
```

##### Delete item
```
String result = artifactory.repository("RepoName").delete("path/to/item");
```

#### Managing repositories
##### List all repositories
```
List<LightweightRepository> repoList = artifactory.repositories().list(LOCAL);
```

##### Create repository
```
Repository repository = artifactory.repositories()
        .builders()
        .localRepositoryBuilder()
        .key("NewRepoName")
        .description("new local repository")
        .packageType(PackageType.maven)
        .build();

String result = artifactory.repositories().create(2, repository);
```

##### Update repository
```
Repository repository = artifactory.repository("RepoName").get();
Repository updatedRepository = artifactory.repositories()
        .builders()
        .builderFrom(repository)            
        .description("new_description")
        .packageType(PackageType.maven)
        .build();
                
String result = artifactory.repositories().update(updatedRepository);
```

##### Delete repository
```
String result = artifactory.repository("RepoName").delete();
```

#### Search

##### Available searches
```
Searches repositories(String... repositories);
Searches artifactsByName(String name);
Searches artifactsCreatedSince(long sinceMillis);
Searches artifactsCreatedInDateRange(long fromMillis, long toMillis);
```

##### Search file in repository
```
List<RepoPath> searchItems = artifactory.searches()
        .repositories("RepoName", "RepoName2")
        .artifactsByName("prefixedWith*.txt")
        .doSearch();
                    
for (RepoPath searchItem : searchItems) {
    String repoKey  = searchItem.getRepoKey();
    String itemPath = searchItem.getItemPath();
}
```

##### Search by property
```
List<RepoPath> searchItems = artifactory.searches()
        .repositories("RepoName", "RepoName2")
        .itemsByProperty()
        .property("released")
        .property("colors", "r*?")
        .doSearch();
                    
for (RepoPath searchItem : searchItems) {
    String repoKey  = searchItem.getRepoKey();
    String itemPath = searchItem.getItemPath();
}
```

#### Managing users

##### Get user
```
User user = artifactory.security().user("userName");
String name               = user.getName();
String email              = user.getEmail();
Collection<String> groups = user.getGroups();
Date loggedIn             = user.getLastLoggedIn();
boolean profileUpdatable  = user.isProfileUpdatable();
boolean isAdmin           = user.isAdmin();
boolean internalPass      = user.isInternalPasswordDisabled();
String realm              = user.getRealm();
```


##### List all user names
```
Collection<String> userNames = artifactory.security().userNames();
for (String userName : userNames) {
    User user = artifactory.security().user(userName);
}
```

##### Create or update user
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

##### Delete user name 
```
String result = artifactory.security().deleteUser("userName");
```

#### Managing groups

##### Get group
```
Group group = artifactory.security().group("groupName");
String description = group.getDescription();
boolean isAutoJoin = group.isAutoJoin();
```

##### List all groups
```
List<String> groupNames = artifactory.security().groupNames();
for (String groupName : groupNames) {
    Group group = artifactory.security().group(groupName);
}
```

##### Create or update group
```
Group group = groupBuilder.name("groupName).autoJoin(true).description("new group").build();

artifactory.security().createOrUpdateGroup(group);
```

##### Delete group
```
artifactory.security().deleteGroup("groupName");
```

#### Permissions
##### Get item permissions
```
Set<ItemPermission> itemPermissions = artifactory.repository("RepoName")
        .file("path/to/file.txt")
        .effectivePermissions();
                                        
for (ItemPermission itemPermission : itemPermissions) {
    RepoPath repoPath          = itemPermissions.getRepoPath();
    List<Privilege> privileges = getPrivileges();
    Subject subject            = getSubject();
    boolean isAllowedTo        = isAllowedTo(ADMIN, DELETE, DEPLOY, ANNOTATE, READ);
}
```

##### Get permission target
```
PermissionTarget permissionTarget = artifactory.security().permissionTarget("permissionName");
String name               = permissionTarget.getName());
String exclude            = permissionTarget.getExcludesPattern();
String include            = permissionTarget.getIncludesPattern();
List<String> repos        = permissionTarget.getRepositories();
List<ItemPermission> perm = permissionTarget.getItemPermissions();
```

##### List all permission targets
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
String version      = version.getVersion();
String revision     = version.getRevision();
String license      = version.getLicense();
List<String> addons = version.getAddons();
```

##### Get Artifactory configuration XLM
```
String xml = artifactory.system().configuration();
```

##### Set Artifactory configuration XML
```
String xml = ... 
artifactory.system().configuration(xml);
```

#### Rest API
```
ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl().apiUrl("api/repositories")
        .method(ArtifactoryRequest.Method.GET)
        .responseType(ArtifactoryRequest.ContentType.JSON);
List<String> response = artifactory.restCall(repositoryRequest);
```

# License
This client is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

(c) All rights reserved JFrog
