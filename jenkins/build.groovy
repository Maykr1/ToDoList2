pipeline {
    agent any

    environment {
        NEXUS           = credentials('nexus-deploy')
        NEXUS_BASE      = 'https://nexus.ethansclark.com'
        RELEASE_REPO    = "${NEXUS_BASE}/repository/maven-releases/"
        SNAPSHOT_REPO   = "${NEXUS_BASE}/repository/maven-snapshots/" 
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout Repo') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                sh 'chmod +x mvnw || true'
                sh './mvnw -B test'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw || true'
                sh './mvnw -B clean package -DskipTests'
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonar-local') {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh """
                        ./mvnw -B -Pwith-coverage \
                            -Dsonar.projectKey=todolist2 \
                            -Dsonar.projectName="todolist2" \
                            -Dsonar.token=$SONAR_TOKEN \
                            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
                            test sonar:sonar
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
                ./mvnw -B -ntp -DskipTests deploy \
                    -DaltReleaseDeploymentRepository=nexus-releases::${RELEASE_REPO} \
                    -DaltSnapshotDeploymentRepository=nexus-snapshots::${SNAPSHOT_REPO}
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