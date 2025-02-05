pipeline{
    agent any
    tools{
        maven "maven-3.8.6"
    }

    stages{


        stage('SCM Checkout'){
            steps{
             script {
                def scmVars = checkout([
                $class: 'GitSCM'
      ])
    }
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'sreeram-git', url: 'https://github.com/ramettan/PetclinicApp-Webhook.git']]])
        }
    }

    stage ('Maven Clean Package'){
        steps{
            sh '''
            mvn clean package -DskipTests
            '''
        }
    }

    stage ('Docker build and push'){
        steps{
            withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', passwordVariable: 'docker_password', usernameVariable: 'docker_user')]){
            sh"""
            docker build -t ${docker_user}/hello-world:${BUILD_NUMBER} .
            docker login -u ${docker_user} -p ${docker_password}
            docker push ${docker_user}/hello-world:${BUILD_NUMBER} >&1 | tee docker.txt
            
            """
            }
        }
    }


}
}



