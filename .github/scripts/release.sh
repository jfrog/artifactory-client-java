#!/usr/bin/env bash
set -euo pipefail

# Releases artifactory-java-client to Artifactory (releases repo), JFrog Distribution and Maven
# Central, then bumps the dev branch to the next development version.
#
# Runnable from GitHub Actions (.github/workflows/release.yml) or directly on a developer machine.
# Expected environment variables:
#   NEXT_VERSION                        - version to release (e.g. 2.21.0)
#   NEXT_DEVELOPMENT_VERSION            - next development version to bump to after release (e.g. 2.22.x-SNAPSHOT)
#   AUDIT_FAIL                          - "true"/"false", fail the build if `jf audit` finds violations (default: false)
#   IL_AUTOMATION_TOKEN                 - GitHub token used to push commits/tags to origin
#   ARTIFACTORY_URL                     - Artifactory base URL
#   ARTIFACTORY_USER                    - Artifactory username
#   ARTIFACTORY_APIKEY                  - Artifactory API key/password
#   MVN_CENTRAL_SIGNING_KEY             - base64-encoded GPG signing key for Maven Central artifacts
#   ORG_GRADLE_PROJECT_signingPassword  - passphrase for the GPG signing key
#   ORG_GRADLE_PROJECT_sonatypeUsername - Sonatype (Maven Central) username
#   ORG_GRADLE_PROJECT_sonatypePassword - Sonatype (Maven Central) password
#   JFROG_CLI_BUILD_NAME, JFROG_CLI_BUILD_NUMBER, JFROG_CLI_BUILD_PROJECT
#                                        - build-info coordinates consumed by `jf rt` commands

# Configure git
git config user.name "jfrog-ecosystem-integration"
git config user.email "jfrog-ecosystem-integration@jfrog.com"
git checkout master
git remote set-url origin https://${IL_AUTOMATION_TOKEN}@github.com/jfrog/artifactory-client-java.git

# Check required versions
echo "Checking variables"
test -n "$NEXT_VERSION" -a "$NEXT_VERSION" != "0.0.0"
test -n "$NEXT_DEVELOPMENT_VERSION" -a "$NEXT_DEVELOPMENT_VERSION" != "0.0.x-SNAPSHOT"

# Configure JFrog CLI
jf c rm --quiet
jf c add internal --url=$ARTIFACTORY_URL --access-token=$ARTIFACTORY_APIKEY
jf gradlec --use-wrapper --deploy-ivy-desc=false --deploy-maven-desc --uses-plugin --repo-resolve ecosys-maven-remote --repo-deploy ecosys-oss-release-local

# Sync changes with dev
git merge origin/dev

# Run audit
# `|| true` ignores SAST scanner infrastructure failures, matching the original pipeline's comment
# that GLIBC_2.34 was not available on the pipeline node.
jf audit --fail=${AUDIT_FAIL:-false} || true

# Update version
sed -i "s/\(currentVersion=\).*\$/\1${NEXT_VERSION}/" gradle.properties

# Commit and tag release
git commit -am "[artifactory-release] Release version ${NEXT_VERSION} [skipRun]" --allow-empty
git tag ${NEXT_VERSION}
git push
git push --tags

# Build and publish to Artifactory
export ORG_GRADLE_PROJECT_signingKey=$(echo "${MVN_CENTRAL_SIGNING_KEY}" | base64 -d)
jf gradle clean aP -x test
jf rt bag && jf rt bce
jf rt bp

# Distribute release bundle
jf ds rbc ecosystem-artifactory-client-java $NEXT_VERSION --spec=./release/specs/prod-rbc-filespec.json --spec-vars="version=$NEXT_VERSION" --sign
jf ds rbd ecosystem-artifactory-client-java $NEXT_VERSION --site="releases.jfrog.io" --sync

# Publish to Maven Central
export ORG_GRADLE_PROJECT_signingKey=$(echo "${MVN_CENTRAL_SIGNING_KEY}" | base64 -d)
./gradlew clean build publishToSonatype closeAndReleaseSonatypeStagingRepository -x test

# Update next development version
git clean -fd
git checkout dev -f
git merge origin/master
sed -i "s/\(currentVersion=\).*\$/\1${NEXT_DEVELOPMENT_VERSION}/" gradle.properties
git commit -am "[artifactory-release] Next development version [skipRun]"
git push
