# Release Notes

## 2.11.1 (February 14, 2022)
- Bug fix - Library dependencies are not exported.

## 2.11.0 (February 13, 2022)
- Support for managing Federated Repositories

## 2.10.0 (January 9, 2022)
- Allow uploading artifacts with progress.
- Add builds API for downloading basic build information from Artifactory.
- Support search by file specs.
- Allow REST requests without Content-Type header.
- Dependencies updates.

## 2.9.2 (May 2, 2021)
- Migrate project to Maven Central.
- Add missing properties to repository builders.
- Support providing SSLContext in the client builder.

## 2.9.1 (April 15, 2020)
- Remove Authorization header on cross host redirection.

## 2.9.0 (March 11, 2020)
- Support for Go repositories.

## 2.8.6 (October 10, 2019)
- Build thin JAR instead of Uber JAR.
- Added support for Helm repositories.

## 2.8.5 (September 12, 2019)
- Fix failure to upload large files.

## 2.8.4 (July 02, 2019)
- Added Artifactory requests interceptor.

## 2.8.3 (March 31, 2019)
- Added PyPIRegistryUrl configuration to pyPI repository settings.

## 2.8.2 (March 14, 2019)
- Allow full configuration of Apache HTTP Client SSLContext.

## 2.8.1 (December 10, 2018)
- Added atomic header to doUploadAndExplode.
- Added support for virtual debian repositories.

## 2.8.0 (September 27, 2018)
- Added support for the Conda package type.
- Enhanced the API to Support the OPTIONS HTTP method.

## 2.7.0 (August 13, 2018)
- Migrate the client build to use JDK 8.
- Added new yamlConfiguration API.
- Added support for maven force authentication
- Added support for Nuget v3.

## 2.6.2 (May 23, 2018)
- Upgraded project dependencies.

## 2.6.1 (May 09, 2018)
- Removed Xray APIs following changes in Artifactory 5.10
- Bug fixes

## 2.6.0 (February 21, 2018)
- JttpBuilder replaced with the native Apache Http Client.
- Many groovy classes were converted to java.
- RestCall API was modified, and now returns ArtifactoryResponse, instead of throwing exceptions on errors.
- Rename repoLayout to repoLayoutRef in repository settings.
- Set listRemoteFolderItems to true by default.
- Added "application/jose+json" content type option.
- Encoding fixes.

## 2.5.2 (August 03, 2017)
- Added custom package type and properties support

## 2.5.1 (July 10, 2017)
- Fixed EqualsAndHashCode implementation for repositories.

## 2.5.0 (June 22, 2017)
- Added sha256 for File info
- clientTlsCertificate added to RemoteRepository
- enableFileListsIndexing added to local RpmRepositorySettings

## 2.4.0 (May 30, 2017)
- Support concurrent requests
- Added RPM package type support
- Copy artifacts by sha1
- Fixed building Artifactory URL in ArtifactoryClientBuilder

## 2.3.5 (April 27, 2017)
- Authentication token support.
- "ignoreSSLIssues" option added to the Artifactory client.

## 2.3.4 (March 15, 2017)
- Fix support for JDK7
- Support searching latest version by GAVC and repository

## 2.3.3 (February 15, 2017)
- Added support for chef and puppet package types
- Added URLENC content-type to requests
- Support the Docker 'maxUniqueTags' repository property
- Permission principals support

## 2.3.2 (December 18, 2016)
- Smart remote repo support

## 2.3.1 (December 08, 2016)
- Conan package type support.

## 2.3.0 (October 30, 2016)
- Jfrog Xray properties support

## 2.2.0 (October 16, 2016)
- PHP composer support
- Added jvm up time support to system info query
- Support for external user groups

## 2.1.0 (September 19, 2016)
- GAVC Search
- YUM virtual repository support

## 2.0.0 (July 20, 2016)
- Changes and improvements to the Repositories API.

## 1.2.2 (January 28, 2016)
- https://github.com/JFrogDev/artifactory-client-java/pull/69

## 1.2.1 (January 28, 2016)
- Repositories API fixes.

## 1.2.0 (December 24, 2015)
- Added storage-info and system-info API.
- A few improvements and bug fixes.

## 1.1.0 (November 12, 2015)
- https://www.jfrog.com/jira/browse/RTFACT-6025

## 1.0.0 (October 27, 2015)
- Support proxy configuration for remote repositories
- Adding configuration to RemoteRepository creation with the client
- Support Artifactory 4 repository package type
- Support Mission Control authentication
- Add vagrant repository support
- Generic Rest call to Artifactory do enable all requests to Artifactory
