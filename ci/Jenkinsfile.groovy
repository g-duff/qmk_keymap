pipeline {
    agent {
        docker {
		image 'ghcr.io/qmk/qmk_cli'
        	args '--rm --user 1000:1000 -w /qmk_firmware -v ./qmk_firmware:/qmk_firmware -e ALT_GET_KEYBOARDS=true -e SKIP_GIT= -e MAKEFLAGS='
	}
    }
    stages {
        stage('Docker test') {
            steps {
	            sh '''make crkbd:via'''
            }
        }
    }
}
