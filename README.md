artifactory-client-java
=======================

Artifactory REST Client Java API bindings

[ ![Download](https://api.bintray.com/packages/jfrog/artifactory-tools/artifactory-client-java/images/download.png) ](https://bintray.com/jfrog/artifactory-tools/artifactory-client-java/_latestVersion)

# Getting Started

#### Add Artifactory Java latest client to your gradle build script:

Add the following snippets to your `build.gradle` file:

```groovy
repositories {
    jcenter()
}
dependencies {
    compile 'org.jfrog.artifactory.client:artifactory-java-client-api:+'
}
```
### Examples:

#### Setting up Artifactory
```
Artifactory artifactory = ArtifactoryClient.create("ArtifactoryUrl", "username", "password");
```
#### Downloading, uploading artifacts

##### Downloading artifacts
```
InputStream iStream = artifactory.repository("RepoName").
                download("path/to/fileToDownload.txt").doDownload();
```


##### Uploading artifacts
```
File file = new File("fileToUpload.txt");
File result = artifactory.repository("RepoName").upload("path/to/newName.txt", file).doUpload();
```

#### File, path, repo information

##### File info

```
File file = artifactory.repository("RepoName").file("path/to/file.txt").info();
bool isFile         = file.isFolder();
long fileSize       = file.getSize();
String fileUri      = file.getDownloadUri();
String md5Checksum  = file.getChecksums().getMd5();
String sha1Checksum = file.getChecksums().getSha1();
```

##### Folder info
```
Folder folder = artifactory.repository("RepoName").folder("path/to/folder").info();
bool isFolder         = folder.isFolder();
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
ItemHandle folderItem = artifactory.repository("RepoName").folder("path/to/folder);
```

##### Copy item

##### Move item

```
ItemHandle itemHandle = ...
ItemHandle newItemHandle = itemHandle.move("RepoName", "path/to/item);
```


##### Copy artifact

##### Delete artifact

#### Managing repositories

##### Create repo

##### Delete repo

#### Search

#### Security

#### System

#### Rest API



# Example Project
As an example, you can also refer to these [sample projects](https://github.com/bintray/bintray-examples/tree/master/gradle-bintray-plugin-examples).


# License
This client is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

(c) All rights reserved JFrog
