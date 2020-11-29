pipeline {
	environment {
		registry = "chrisharripaul/purchasing-bot"
		registryCredential = 'dockerhub'
		dockerImage = ""
	}
	
	agent {
		dockerfile {
	        filename 'Dockerfile'
	        additionalBuildArgs '-v /tmp:/tmp -v /var/run/docker.sock:/var/run/docker.sock'
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