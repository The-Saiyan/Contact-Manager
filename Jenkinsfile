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
                    JAR_NAME=contactManager.jar
                    cp build/libs/contactmanager-0.0.1-SNAPSHOT.jar ${JAR_NAME}
                '''
            }
        }

        stage('Start the service') {
            steps {

                sh '''
                    supervisorctl stop contact_manager
                    sleep 1s

                    JAR_NAME=contactManager.jar

                    ARCH_JAR_NAME=contactManager-`date +'%Y%m%d'`.jar

                    if [ -f /TheSaiyan/Contact-Manager/${JAR_NAME} ]; then
                        mv /TheSaiyan/Contact-Manager/${JAR_NAME} /TheSaiyan/Contact-Manager/${ARCH_JAR_NAME}
                        gzip -f /TheSaiyan/Contact-Manager/${ARCH_JAR_NAME}
                    fi
                    sleep 2s

                    if [ -f ${JAR_NAME} ]; then
                        mv ${JAR_NAME} /TheSaiyan/Contact-Manager/
                    fi

                    supervisorctl start contact_manager
                '''
            }
        }
    }
}
