podTemplate(
    name: 'questcode',
    namespace: 'devops', 
    label: 'questcode', 
    containers: [
            containerTemplate(alwaysPullImage: false, args: 'cat', command: '/bin/sh -c', envVars: [], image: 'docker', livenessProbe: containerLivenessProbe(execArgs: '', failureThreshold: 0, initialDelaySeconds: 0, periodSeconds: 0, successThreshold: 0, timeoutSeconds: 0), name: 'docker-container', ports: [], privileged: false, resourceLimitCpu: '', resourceLimitMemory: '', resourceRequestCpu: '', resourceRequestMemory: '', shell: null, ttyEnabled: true, workingDir: '/home/jenkins/agent')
    ],
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
) {
    node('questcode') {
        stage('Build') {
            echo "Inicializando Build"
            sh 'ls -ltra'
        }
        stage('Package') {
            container('docker-container') {
                sh 'docker images'
                echo "Inicializando Package"
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'Github', url: 'https://github.com/jefersonaraujo/missaodevops.git']]])
                sh 'ls -ltra'
            }
        }
        stage('Deploy') {
            echo "Inicializando Deploy"
            sh 'ls -ltra'
        }
    }
} 


 