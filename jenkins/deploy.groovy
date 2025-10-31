pipeline {
    agent any
    options { timestamps() }

    environment {
        // --- APP ---
        GROUP_ID        = 'com.project'
        ARTIFACT_ID     = 'traveling-sales-person'
        BASE_VERSION    = '0.0.1-SNAPSHOT'

        // --- NEXUS ---
        NEXUS           = credentials('nexus-deploy')
        NEXUS_BASE      = 'https://nexus.ethansclark.com'
        REPOSITORY      = 'maven-snapshots'
        SNAPSHOT_API    = "${NEXUS_BASE}/service/rest/v1/search/assets"

        // --- REMOTE ---
        REMOTE_HOST     = 'eclarkserver'
        REMOTE_ROOT     = '/home/eclark/projects/eclarkCICDInfrastructure'
        REMOTE_APP_DIR  = '/home/eclark/projects/TravelingSalesPerson'
        SSH_CRED_ID     = 'home-server-ssh'
        
        // --- Cloudflare ---
        CF_ACCESS_CLIENT_ID     = credentials('cf-access-client-id')
        CF_ACCESS_CLIENT_SECRET = credentials('cf-access-client-secret')
    }

    stages {
        stage('Fetch newest SNAPSHOT JAR from Nexus') {
            steps {
                sh '''
                set -euo pipefail
                rm -rf deploy && mkdir -p deploy
                echo "Resolving latest JAR for ${GROUP_ID}:${ARTIFACT_ID}:${BASE_VERSION}"

                curl -sS -u "${NEXUS_USR}:${NEXUS_PSW}" \
                "${SNAPSHOT_API}?repository=${REPOSITORY}&sort=version&direction=desc&maven.groupId=${GROUP_ID}&maven.artifactId=${ARTIFACT_ID}&maven.baseVersion=${BASE_VERSION}" \
                > deploy/nexus.json

                JAR_URL=$(jq -r '.items[] | select(.path | endswith(".jar")) | .downloadUrl' deploy/nexus.json | head -n 1)
                test -n "$JAR_URL" && test "$JAR_URL" != "null" || { echo "Could not resolve JAR"; exit 1; }
                echo "Latest JAR: $JAR_URL"

                curl -fSL -u "${NEXUS_USR}:${NEXUS_PSW}" "$JAR_URL" -o deploy/app.jar
                ls -lh deploy/app.jar
                '''
            }
        }

        stage('Copy JAR & Compose Up (build)') {
            steps {
                sshagent(credentials: [env.SSH_CRED_ID]) {
                sh '''
                set -euo pipefail
                ssh -o StrictHostKeyChecking=no "${REMOTE_HOST}" "mkdir -p '${REMOTE_APP_DIR}'"
                scp -o StrictHostKeyChecking=no deploy/app.jar "${REMOTE_HOST}":"${REMOTE_APP_DIR}/app.jar"

                ssh -o StrictHostKeyChecking=no "${REMOTE_HOST}" \
                "cd '${REMOTE_ROOT}' && docker compose up -d --build traveling-sales-person"
                '''
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment complete ✅'
        }
        failure {
            echo 'Deployment failed ❌'
        }
    }
}
