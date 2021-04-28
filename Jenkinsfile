pipeline {
    agent any
    stages {
        stage ('Build backend') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage ('Sonar Analysis') {
        	environment {
        		scannerHome = tool 'SONAR_SCANNER'
        	}
            steps {
            	withSonarQubeEnv('SONAR_LOCAL') {
	                sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployTaskBackEnd -Dsonar.host.url=http://localhost:9000 -Dsonar.login=3bc154ead1a4851a007d41c803d59e36c5ec14c6 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/src/test/**,**/model/**,**Application.java"            	
            	}
            }
        }
        stage ('Quality Gate') {
        	steps {
        		sleep(15)
        		timeout(time: 1, unit: 'MINUTES') {
        			waitForQualityGate abortPipeline: true
        		}
        	}
        }
        stage ('Deploy Backend') {
        	steps {
				deploy adapters: [tomcat9(credentialsId: 'TomcatID', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'        	
        	}
        }
        stage ('API Test') {
        	steps {
	        	dir('api-test') {
					git 'https://github.com/wsbrito/task-api-test'
					sh 'mvn test'
				}
        	}
        }
        stage ('Deploy Frontend') {
        	steps {
	        	dir('frontend') {
					git 'https://github.com/wsbrito/tasks-frontend'
	                sh 'mvn clean package'
					deploy adapters: [tomcat9(credentialsId: 'TomcatID', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks', war: 'target/tasks.war'        	
				}
        	}
        }
        stage ('Functional Test') {
        	steps {
	        	dir('functional-test') {
					git 'https://github.com/wsbrito/tasks-functional-test'
					sh 'mvn test'
				}
        	}
        }
        stage ('Deploy prod') {
            steps {
                sh 'docker-compose build'
                sh 'docker-compose up -d'
            }
        }
        stage ('Health Check') {
            steps {
                sleep(30)
                dir('functional-test') {
                    sh 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
}

