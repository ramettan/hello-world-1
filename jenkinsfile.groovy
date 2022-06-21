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


}
}
