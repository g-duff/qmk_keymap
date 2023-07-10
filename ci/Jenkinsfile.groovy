String QMK_VERSION = '0.21.6'

pipeline {

    agent none
    
    stages {
        stage('Clone QMK') {
            agent {
                label 'main'
            }
            steps {
                sh "git clone --depth 1 --branch ${QMK_VERSION} https://github.com/qmk/qmk_firmware.git"
            }
        }
        stage('Build Firmware') {
            agent {
                docker {
                    image 'ghcr.io/qmk/qmk_cli'
                    args '--volume ./qmk_firmware:/qmk_firmware'
                }
            }
            steps {
                dir('qmk_firmware') {
                    sh 'make crkbd:via'
                }
            }
        }
    }
    post {
        always {
            node('main') {
                archiveArtifacts artifacts: 'qmk_firmware/*.hex'
            }
        }
        cleanup {
            node('main') {
                deleteDir()
            }
        }
    }
}
