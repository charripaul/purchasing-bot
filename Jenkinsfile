pipeline {
	environment {
		registry = "chrisharripaul/purchasing-bot"
		registryCredential = 'dockerhub'
		dockerImage = ""
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
					dockerimage = docker.build registry + ":latest"
				}
			}
		}
		stage('Push image to registry') {
			steps{
				script {
					docker.withRegistry(registry, registryCredential) {
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