#!/usr/bin/env bash
set -euo pipefail

# Publishes a snapshot build of artifactory-java-client to Artifactory and JFrog Distribution.
#
# Runnable from GitHub Actions (.github/workflows/snapshot.yml) or directly on a developer machine.
# Expected environment variables:
#   ARTIFACTORY_URL     - Artifactory base URL
#   ARTIFACTORY_USER    - Artifactory username
#   ARTIFACTORY_APIKEY  - Artifactory API key/password
#   RUN_NUMBER           - unique build number, used for build-info and as the distributed release
#                          bundle version (e.g. the GitHub Actions run number)
#   JFROG_CLI_BUILD_NAME, JFROG_CLI_BUILD_NUMBER, JFROG_CLI_BUILD_PROJECT
#                        - build-info coordinates consumed by `jf rt` commands

# Configure JFrog CLI
jf c rm --quiet
jf c add internal --url=$ARTIFACTORY_URL --access-token=$ARTIFACTORY_APIKEY
jf gradlec --use-wrapper --deploy-ivy-desc=false --deploy-maven-desc --uses-plugin --repo-resolve ecosys-maven-remote --repo-deploy ecosys-oss-snapshot-local

# Run audit
jf audit

# Delete former snapshots
# Ensures the release bundle will not contain stale artifacts from a previous snapshot.
jf rt del "ecosys-oss-snapshot-local/org/jfrog/artifactory/client/artifactory-java-client-api/*" --quiet
jf rt del "ecosys-oss-snapshot-local/org/jfrog/artifactory/client/artifactory-java-client-services/*" --quiet
jf rt del "ecosys-oss-snapshot-local/org/jfrog/artifactory/client/artifactory-java-client-httpClient/*" --quiet

# Run install and publish
jf gradle clean aP -x test
jf rt bag && jf rt bce
jf rt bp

# Distribute release bundle
jf ds rbc ecosystem-artifactory-java-client-snapshot $RUN_NUMBER --spec=./release/specs/dev-rbc-filespec.json --sign
jf ds rbd ecosystem-artifactory-java-client-snapshot $RUN_NUMBER --site="releases.jfrog.io" --sync
