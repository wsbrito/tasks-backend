pipeline {
    agent any
    stages {
        stage ('Build backend') {
            steps {
                sh 'mvn clear package -DskipTests=true'
            }
        }
    }
}
