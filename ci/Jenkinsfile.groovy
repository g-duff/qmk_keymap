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
                    args '-w /qmk_firmware -v ./qmk_firmware:/qmk_firmware'
                }
            }
            steps {
                sh 'cd qmk_firmware && make crkbd:via'
            }
        }

    }
}
