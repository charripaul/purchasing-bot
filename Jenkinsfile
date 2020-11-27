pipeline {
	environment {
		registry = "104.131.4.183:5000/deployapp/purchasingbot"
		dockerImage = ""
	}
	
	agent {
		docker {
            image 'openjdk:8-jdk-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
	}
	
	stages {
		stage('Install build') {
			steps{
				withMaven {
					sh 'mvn clean install'
				}
			}
		}
		stage('Build Image') {
			steps{
				script {
					dockerImage = docker.build registry + ":$BUILD_NUMBER"
				}
			}
		}
		stage('Deploy') {
			steps{
				script {
					docker.withRegistry(registry) {
						dockerImage.push()
					}
				}
			}
		}
		stage('Deploy to Kubernetes cluster'){
			steps{
				script {
				  kubernetesDeploy(configs: "values.yaml", kubeconfigId: "ventexclusterconfig")
				}
			}
		}
	}
}