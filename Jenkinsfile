pipeline {
	environment {
		registry = "localhost:5000"
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
					dockerimage = docker.build("purchasing-bot")
				}
			}
		}
		stage('Deploy') {
			steps{
				script {
					docker.withRegistry(registry) {
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