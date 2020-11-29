pipeline {
	environment {
		registry = "chrisharripaul/purchasing-bot"
		registryCredential = 'dockerhub'
		dockerImage = ""
	}
	
	agent {
		dockerfile {
	        filename 'Dockerfile'
	        args '-v /tmp:/tmp'
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
					sh 'sudo dockerd'
					dockerimage = docker.build registry + ":latest"
				}
			}
		}
		stage('Deploy') {
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