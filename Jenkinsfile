pipeline {
    agent any

    environment {
        NVD_API_KEY = credentials('nvd-api-key')
        // OR replace with direct value if not using Jenkins credentials
    }

    stages {

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -B test'
            }
        }

        stage('Dependency Vulnerability Scan (OWASP)') {
            steps {
                bat 'mvn -B org.owasp:dependency-check-maven:check'
            }
        }

        stage('Generate Security Summary + Chart') {
            steps {
                bat 'mvn -B -DskipTests exec:java -Dexec.mainClass=com.demo.SecurityReportGenerator'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B package'
            }
        }

        stage('Filesystem Scan (Trivy)') {
            steps {
                bat '"C:\\Program Files\\trivy.exe" fs . --severity HIGH,CRITICAL'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/dependency-check-report.*', fingerprint: true, allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/security-summary.md', fingerprint: true, allowEmptyArchive: true
        }
        success {
            echo 'Pipeline completed successfully'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}