#!/bin/bash
set -euo pipefail

#if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ -n "${GITHUB_TOKEN}" ] && [ -n "${SONARQUBE_TOKEN}" ]; then
    echo 'Internal pull request: trigger QA and analysis'

    mvn org.jacoco:jacoco-maven-plugin:prepare-agent deploy sonar:sonar \
        $MAVEN_OPTIONS \
        -Dsource.skip=true \
        -Pdeploy-sonarsource \
        -Dsonar.projectKey=$SONAR_PROJECT_KEY \
        -Dsonar.analysis.mode=issues \
        -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
        -Dsonar.github.repository=$TRAVIS_REPO_SLUG \
        -Dsonar.github.oauth=$GITHUB_TOKEN \
        -Dsonar.host.url=$SONAR_HOST_URL \
        -Dsonar.login=$SONAR_TOKEN
        
#fi