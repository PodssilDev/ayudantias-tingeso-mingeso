pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/PodssilDev/ayudantias-tingeso-mingeso']])
                dir("pep1"){
                    sh "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("pep1"){
                    sh "mvn test"
                }
            }
        }
        stage("SonarQube Analysis"){
            steps{
                dir("pep1"){
                    sh "mvn sonar:sonar -Dsonar.projectKey=pep1 -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_fc56b1ba154bd3b0bf2aaa644210b01404548520"
                }
            }
        }
        stage("Build Docker Image"){
            steps{
                dir("pep1"){
                    sh "docker build -t johnserrano159/proyecto_docker ."
                }
            }
        }
        stage("Push Docker Image"){
            steps{
                dir("pep1"){
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]){
                        sh "docker login -u johnserrano159 -p ${dckpass}"
                        
                    }
                    sh "docker push johnserrano159/proyecto_docker"
                    
                }
                
            }
        }
    }
    post{
        always{
            dir("pep1"){
                sh "docker logout"
            }
        }
    }
}
