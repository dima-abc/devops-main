pipeline {
    agent { label 'agent1' }

    tools {
        git 'Default'
    }

    stages {
         stage('Prepare Environment') {
                            steps {
                                script {
                                    sh 'chmod +x ./gradlew'
                                }
                            }
                        }
        stage('Build') {
            parallel {
                stage('1. Checkstyle') {
                    steps {
                        script {
                            echo 'Checkstyle Main'
                            sh './gradlew checkstyleMain'
                            echo 'Checkstyle Test'
                            sh './gradlew checkstyleTest'
                        }
                    }
                }
                stage('2. Compile') {
                    steps {
                        script {
                            echo 'Build'
                            sh './gradlew compileJava'
                        }
                    }
                }
                stage('3. Test & Coverage') {
                    steps {
                        script {
                            echo 'Test'
                            sh './gradlew test'
                            echo 'JaCoCo Report'
                            sh './gradlew jacocoTestReport'
                            echo 'JaCoCo Verification'
                            sh './gradlew jacocoTestCoverageVerification'
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                def buildInfo = "Build number: ${currentBuild.number}\n" +
                                "Build status: ${currentBuild.currentResult}\n" +
                                "Started at: ${new Date(currentBuild.startTimeInMillis)}\n" +
                                "Duration so far: ${currentBuild.durationString}"
                telegramSend(message: buildInfo)
            }
        }
    }
}