pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
        }

        stage('Dependency Vulnerability Scan') {
            steps {
                sh 'mvn -B org.owasp:dependency-check-maven:check'
            }
        }

        stage('Generate Security Summary + Chart') {
            steps {
                sh 'mvn -B -DskipTests exec:java -Dexec.mainClass=com.demo.SecurityReportGenerator'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -B package'
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
