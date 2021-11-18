def APP_NAME = "contactManager-"
def JAR_NAME = APP_NAME + new Date().format("yyyy-MM-dd") + ".jar"
pipeline {
    agent any

    stages {
        stage('Git Clone') {
            steps {
                git 'https://github.com/The-Saiyan/Contact-Manager.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    mkdir -p /TheSaiyan/Contact-Manager/
                    cp build/libs/contactmanager-0.0.1-SNAPSHOT.jar ${JAR_NAME}
                '''
            }
        }

        stage('Start the service') {
            steps {
                sh '''
                    sudo supervisorctl stop contact_manager
                    gzip /TheSaiyan/Contact-Manager/${APP_NAME}*.jar
                    mv ${JAR_NAME}.jar /TheSaiyan/Contact-Manager/
                    sudo supervisorctl restart contact_manager
                '''
            }
        }
    }
}
