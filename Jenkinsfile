pipeline {
	environment {
		registry = "104.131.4.183:5000/deployapp/purchasingbot"
	}
	
	agent: any
	
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
					docker build registry + ":$BUILD_NUMBER"
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