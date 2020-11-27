pipeline {
	environment {
		registry = "104.131.4.183:5000/deployapp/purchasingbot"
	}
	
	agent {
		docker {
            image 'maven:3-alpine'
            args '-v ~root/.m2' 
        }
	}
	
	stages {
		stage('Install build') {
			steps{
				script {
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
					def dockerHome = tool 'docker'
        			env.PATH = "${dockerHome}/bin:${env.PATH}"
				}
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