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
        def repos
        stage('Checkout') {
            echo "Inicializando Clone do Repositorio"
            repos = git credentialsId: 'Github', url: 'https://github.com/jefersonaraujo/missaodevops.git'
            echo repos.toString()          
        }
        stage('Package') {
            container('docker-container') {                
                echo "Inicializando empacotamento com Docker"
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USER')]) {
                      sh "docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD}"
                      sh "docker build -t jefersonaraujo/questcode-frontend:0.14 ./frontend --build-arg NPM_ENV='staging'  "
                      sh "docker push jefersonaraujo/questcode-frontend:0.14"       
                }
              
               
            }
        }
        stage('Deploy') {
            echo "Inicializando Deploy"
            sh 'ls -ltra'
        }
    }
} 


