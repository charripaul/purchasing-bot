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
					def dockerHome = tool 'docker'
        			env.PATH = "${dockerHome}/bin:${env.PATH}"
					docker run -u 0 --privileged --name jenkins -it -d -p 8080:8080 -p 50000:50000 \
					-v /var/run/docker.sock:/var/run/docker.sock \
					-v $(docker):/usr/bin/docker \
					-v /home/jenkins_home:/var/jenkins_home \
					jenkins/jenkins:latest
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