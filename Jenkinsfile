pipeline {
	environment {
		registry = "chrisharripaul/purchasing-bot"
		registryCredential = 'dockerhub'
		dockerImage = ''
	}
	
	agent any
	
	stages {
		stage('Install build') {
			steps{
				script {
					sh 'mvn clean install'
				}
			}
		}
		stage('Build image') {
			steps{
				script {
					dockerImage = docker.build registry + ":latest"
				}
			}
		}
		stage('Push image to registry') {
			steps{
				script {
					docker.withRegistry('', registryCredential) {
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