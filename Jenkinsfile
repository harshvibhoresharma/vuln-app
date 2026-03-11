
pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                dir('C:/Users/harsh/devops-projects/vuln-app') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                dir('C:/Users/harsh/devops-projects/vuln-app') {
                    bat 'mvn test'
                }
            }
        }

        stage('Dependency Vulnerability Scan') {
            steps {
                dir('C:/Users/harsh/devops-projects/vuln-app') {
                    bat '"C:\\Program Files\\trivy.exe" fs . --exit-code 1 --severity HIGH,CRITICAL'
                }
            }
        }

        stage('Package') {
            steps {
                dir('C:/Users/harsh/devops-projects/vuln-app') {
                    bat 'mvn package'
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deployment successful!"
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully"
        }
        failure {
            echo "Pipeline failed"
        }
    }
}

