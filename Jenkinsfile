pipeline {
	environment {
		registry = "chrisharripaul/purchasing-bot"
		registryCredential = 'docker-creds'
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
					dockerimage = docker.build registry + ":latest"
				}
			}
		}
		stage('Deploy') {
			steps{
				script {
					docker.withRegistry(registry, registryCredential) {
						dockerImage.push("latest")
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