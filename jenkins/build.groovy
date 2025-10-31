pipeline {
    // --- SETUP ---
    agent any
    options { timestamps() }
    tools { maven 'maven-3.9.11' }

    environment {
        // --- APP ---
        APP_NAME = "todolist2"

        // --- NEXUS ---
        NEXUS           = credentials('nexus-deploy')
        NEXUS_BASE      = 'https://nexus.ethansclark.com'
        RELEASE_REPO    = "${NEXUS_BASE}/repository/maven-releases/"
        SNAPSHOT_REPO   = "${NEXUS_BASE}/repository/maven-snapshots/"
    }

    stages {
        stage('Checkout Repo') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests package'
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonar-local') {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh """
                        mvn -B -ntp sonar:sonar \
                            -Dsonar.projectKey=$APP_NAME \
                            -Dsonar.projectName="$APP_NAME" \
                            -Dsonar.token=$SONAR_TOKEN \
                        """
                    }
                }

                timeout(time:10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Containerize') {
            steps {
                sh '''
                mvn -B -ntp -DskipTests deploy \
                    -DaltReleaseDeploymentRepository=nexus-releases::default::${RELEASE_REPO} \
                    -DaltSnapshotDeploymentRepository=nexus-snapshots::default::${SNAPSHOT_REPO}
                '''
            }
        }
    }

    post {
        success {
            echo 'Build complete ✅'
        }
        failure {
            echo 'Build failed ❌'
        }
    }
}