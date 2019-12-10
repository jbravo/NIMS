notServiceModuleList = ["cicd", "commons"]
env.cloud_config_git_uri = "http://git.5f.cloud:8180/spqlhtml/co-dien/codien-config-production"

def deployToProduction() {
    def serviceList = []
    def moduleVersionList = [:]
    def listModulesRebuild = []
    def listModulesDeploy = []
    def listServicesChangedInConfig = []
    def gitlabBranch = env.gitlabBranch
    def semantic_version = gitlabBranch.split("/")[2].split("\\.")
    env.cloud_config_git_branch = "v${semantic_version[0]}.${semantic_version[1]}"
    echo "Cloud config git branch: ${env.cloud_config_git_branch}"
    env.DEPLOY_RESULT_DESCRIPTION += "<h4>Test & Verify Phase Result</h4>"

    stage("Checkout Source Code") {
        jenkinsfile_utils.checkoutSourceCode("PUSH")
        def commitIdStdOut = sh(script: 'git rev-parse HEAD', returnStdout: true)
        env.DEPLOY_GIT_COMMIT_ID = commitIdStdOut.trim()
    }
    env.DEPLOY_RESULT_DESCRIPTION += "<h4>Deploy Phase Result</h4>"
    stage("Get & check Production ENV") {
        def pomXMLStr = readFile "pom.xml"
        serviceList = jenkinsfile_utils.getServiceList(pomXMLStr, notServiceModuleList)
        echo "Service List: ${serviceList}"
        def pom = readMavenPom(file: "pom.xml")
        env.project_version = pom.getVersion()
        loadGitConfig(env.cloud_config_git_uri, env.cloud_config_git_branch, "")
        env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Cloud Config Git Version: ${env.cloud_config_git_branch}</h5>"
    }

    stage("Get sub modules version") {
        getSubModuleVersion(serviceList, moduleVersionList)
    }

    stage("Check for services needed to be deploy") {
        //Get Token to get last successful deploy info
        withCredentials([usernamePassword(
            credentialsId: 'vsmart-production-openstack-authentication',
            usernameVariable: 'openstack_username',
            passwordVariable: 'openstack_password')]) {
            env.openStackAuthToken = getOpenStackAuthToken(
                env.openstack_auth_url,
                env.openstack_project_name,
                env.openstack_user_domain_name,
                env.openstack_project_domain_name,
                openstack_username,
                openstack_password
            )
        }
        def listServiceUpgrade = getListServiceUpgrade(serviceList)
        echo("listServiceUpgrade: ${listServiceUpgrade}")
        listModulesRebuild = listServiceUpgrade.getAt(0)
        listServicesChangedInConfig = listServiceUpgrade.getAt(1)
        listModulesDeploy = listServiceUpgrade.get(2)
        if (listModulesRebuild.size() == 0 && listServicesChangedInConfig.size() == 0) {
            error("Dont have any changes")
        }
        echo "List services is going to be rebuild: ${listModulesRebuild}"
        echo("List services just changed in configuration: ${listServicesChangedInConfig}")
        echo "listModulesDeploy = ${listModulesDeploy}"
    }
    def isReloadConfig = false
    def defaultDeployModule = ['gateway-service']
    def serviceListToUpgrade = listModulesDeploy + listServicesChangedInConfig
    serviceListToUpgrade -= defaultDeployModule
    serviceListToUpgrade += defaultDeployModule
    if (listModulesDeploy.size() == 0 && listServicesChangedInConfig.size() > 0) isReloadConfig = true
    withCredentials([file(
        credentialsId: 'vsmart-production-cloud-config-bootstrap.yml',
        variable: 'bootstrap_production_config')]) {
        def currentDir = pwd()
        sh "cp $bootstrap_production_config ${currentDir}/configuration-service/src/main/resources/bootstrap-production.yml"
    }
    if (!isReloadConfig) {
        jenkinsfile_CI.autoTest()

        stage("Package Production Service") {
            for (def i = 0; i < listModulesDeploy.size(); i++) {
                def service = listModulesDeploy.get(i)
                sh("./build.sh package ${service}")
            }
        }

        stage("Build & Upload Production Service Docker Images") {
            //Build docker images and push to docker registry
            env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Docker Image Deployed To Production List:</h5>"
            env.DEPLOY_RESULT_DESCRIPTION += "<ul style='list-style-type:disc'>"
            def tasks = [:]
            for (def i = 0; i < listModulesDeploy.size(); i++) {
                // Package service
                def service = listModulesDeploy.get(i)
                tasks[service] = {
                    if (service.endsWith("service")) {
                        def service_version
                        dir(service) {
                            def pom = readMavenPom(file: "pom.xml")
                            service_version = pom.getVersion()
                            // Build docker images for service
                            sh """
                            docker build --build-arg JDK_BASE_IMAGE=${env.production_image_base} \
                               --build-arg SERVICE_VERSION=${service_version} \
                               -t ${env.docker_push_image_registry_url}/vsmart/${env.docker_repo_name}/${service}:${
                                service_version
                            } .
                        """
                        }
                        // Push docker images to docker registry
                        withDockerRegistry([
                            credentialsId: 'nexus-repo-credentials',
                            url          : "http://${env.docker_push_image_registry_url}"]) {
                            def stagingImage = docker.image("${env.docker_push_image_registry_url}/vsmart/" +
                                "${env.docker_repo_name}/${service}:${service_version}")
                            stagingImage.push()
                            env.DEPLOY_RESULT_DESCRIPTION += "<li><code>${env.docker_pull_image_registry_url}/vsmart/" +
                                "${env.docker_repo_name}/${service}:${service_version}</code></li>"
                        }
                    }
                }
            }

            parallel tasks
            env.DEPLOY_RESULT_DESCRIPTION += "</ul>"
            docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
                dir("cicd/deploy-production/ansible") {
                    def currentDir = pwd()
                    moduleVersionList.remove("commons")
                    def moduleVersionListStr = jenkinsfile_utils.toJSONString(["map_module_version": moduleVersionList])
                    def moduleVersionListJSON = readJSON text: moduleVersionListStr
                    writeJSON file: 'moduleVersionList.json', json: moduleVersionListJSON
                    sh 'cat moduleVersionList.json'
                    sh """
                        ansible-playbook  -i jenkins_localhost.ini deploy_utilities.yml \
                            --tags "gen-docker-compose-vsmart" \
                            -e use_production_utils=True \
                            -e IMAGE_TYPE=${env.docker_repo_name} \
                            -e IMAGE_REGISTRY_URL=${env.docker_push_image_registry_url} \
                            -e JDK_BASE_IMAGE=${env.production_image_base} \
                            -e CURRENT_JENKINS_LOCAL_DIR=${currentDir} \
                            -e "@moduleVersionList.json"
                        """
                    echo "${currentDir}"
                    sh "cat ${currentDir}/../../../deploy/docker-compose-vsmart.yml"
                }
            }
            // Remove all images in jenkins server
            sh './build.sh service-stack-down --production'
        }
    }

    stage("Generate secret files") {
        generateSecretFile()
    }

    if (!isReloadConfig) {
        stage("Deploy application to pre-production environment and notify to maintainer") {
            // generate ansible inventory
            docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
                dir('cicd/deploy-production/ansible') {
                    def currentDir = pwd()
                    sh """
                            ansible-playbook -i jenkins_localhost.ini deploy_utilities.yml \
                                --tags "gen-pre-production-inventory-file" \
                                -e "use_production_utils=True" \
                                -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                                -e "PRE_PRODUCTION_IP=${env.pre_production_ip}"
                        """

                    sh 'cat pre_production_inventory.ini'
                    moduleVersionList.remove('commons')
                    def moduleVersionListStr = jenkinsfile_utils.toJSONString(["map_module_version": moduleVersionList])
                    def moduleVersionListJSON = readJSON text: moduleVersionListStr
                    writeJSON file: 'moduleVersionList.json', json: moduleVersionListJSON
                    sh 'cat moduleVersionList.json'
                    sh """
                            ansible-playbook\
                                -i pre_production_inventory.ini deploy_pre_production.yml\
                                -e '{ "SERVICE_LIST":${serviceList}}'\
                                -e "@moduleVersionList.json"\
                                -e IMAGE_TYPE=${env.docker_repo_name} \
                                -e IMAGE_REGISTRY_URL=${env.docker_push_image_registry_url} \
                                -e JDK_BASE_IMAGE=${env.production_image_base} \
                                -e CURRENT_JENKINS_LOCAL_DIR=${currentDir}\
                                -e "@production-config.yml"\
                                -e "label=${env.cloud_config_git_branch}"\
                                -e '{ "REFRESH_SERVICE_LIST_TIMEOUT":${env.REFRESH_SERVICE_LIST_TIMEOUT}}'
                        """
                }
            }
            def deployResultTitle = "Deploy vsmart cloud version ${env.project_version} " +
                "to pre production environment Result"
            def deployResultDescription = "Vsmart cloud version ${env.project_version} " +
                "is deployed to pre production environment at address: " +
                "<a href='${env.pre_production_ip}:${env.pre_production_hompage_port}'>" +
                "<h2><a href='http://${env.pre_production_ip}:${env.pre_production_hompage_port}'>" +
                "http://${env.pre_production_ip}:${env.pre_production_hompage_port}</a></h2>"
            deployResultDescription += "<h5>- CommitID: ${env.GIT_COMMIT_ID}</h5>"
            deployResultDescription += "<h5>- Cloud Config Git Version: ${env.cloud_config_git_branch}</h5>"
            deployResultDescription += "<h5>- Project Service List:</h5>"
            deployResultDescription += "<ul style='list-style-type:disc'>"
            for (serviceName in serviceList) {
                deployResultDescription += "<li>${serviceName}: version ${moduleVersionList[serviceName]} </li>"
            }
            deployResultDescription += "</ul>"
            deployResultDescription += "<h4>Please check if pre production is accepted and decide " +
                "deploy this version to production environment or not by select at:</h4>" +
                "<h2><i><a href='${env.BUILD_URL}display/redirect'>" +
                "Deploy Process Details...</a></i></h2>" +
                "<h4>Deploy to production process will be aborted after 24 hours from this message.</h4>"
            createIssueAndMentionMaintainer(deployResultTitle, deployResultDescription)
        }
    }

    def deployInput = "Deploy"

    stage("Wait for maintainer accept or reject to deploy to production") {
        try {
            timeout(time: 24, unit: 'HOURS') {
                deployInput = input(
                    submitter: "${env.project_maintainer_list}",
                    message: 'Pause for wait maintainer selection', ok: "Execute", parameters: [
                    choice(choices: ['Deploy', 'Abort'], description: 'Deploy to production or abort deploy process?', name: 'DEPLOY_CHOICES')
                ])
            }
        } catch (err) { // timeout reached or input false
            echo "Exception"
            def user = err.getCauses()[0].getUser()
            if ('SYSTEM' == user.toString()) { // SYSTEM means timeout.
                echo "Timeout is exceeded!"
            } else {
                echo "Aborted by: [${user}]"
            }
            deployInput = "Abort"

        }
        echo "Input value: $deployInput"
    }

    if (deployInput == "Deploy") {
        try {
            genImportantFileAndDeploy(moduleVersionList, serviceList, serviceListToUpgrade, env.cloud_config_git_branch)
        } catch (Exception e) {
            echo "Error: ${e.toString()}. Rollback!"
            env.DEPLOY_RESULT_DESCRIPTION += "<h4>Error Occur when Deploy Service. Rollback to latest success version</h4>"
            rollBackDeployServers(serviceListToUpgrade)
            error "Deploy Failed."
        } finally {
            if (!isReloadConfig) {
                stage("Delete Old Image") {
                    // Gen playbook for service
                    dir('cicd/deploy-production/ansible') {
                        def currentDir = pwd()
                        def listServiceUpgrade = listModulesDeploy + listServicesChangedInConfig
                        def vsmartServiceListJSONStr = jenkinsfile_utils.toJSONString(
                            ["SERVICE_LIST": listServiceUpgrade])
                        def vsmartServiceListJSON = readJSON text: vsmartServiceListJSONStr
                        writeJSON file: 'vsmart_services.json', json: vsmartServiceListJSON
                        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_gen-docker-compose-vsmartcache:/tmp/facts_cache:rw'") {
                            sh """
                                  ansible-playbook -i jenkins_localhost.ini deploy_utilities.yml \
                                      --tags "gen-playbook-delete-image" \
                                      -e "use_production_utils=True" \
                                      -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                                      -e "@vsmart_services.json"

                                  cat delete_old_images_production.yml
                                """
                        }
                        //Delete images for service
                        for (def service : listServiceUpgrade) {
                            docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_gen-docker-compose-vsmartcache:/tmp/facts_cache:rw'") {
                                sh """
                                     ansible-playbook -i production_inventory.ini delete_old_images_production.yml \
                                        -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                                         --tags "delete-${service}-image"
                                    """
                            }
                        }
                    }
                }
            }
            stage("Resume Stack AutoScaling") {
                resumeHeatStackAutoScaling()
            }
        }
        currentBuild.result = "SUCCESS"
    } else { // Deploy input is Abort
        stage("Cancel deploy process") {
            echo "Deploy process is canceled."
            currentBuild.result = "ABORTED"
        }
    }
}

def rollBackTag() {
    def gitTag = ''
    def serviceList = []
    env.DEPLOY_RESULT_DESCRIPTION += "<h4>Test & Verify Phase Result</h4>"
    stage('Wait for user submit Tag to rollback') {
        try {
            timeout(time: 24, unit: 'HOURS') {
                gitTag = input(
                    submitter: "${env.ROLLBACK_MAINTAINER_LIST}",
                    message: 'Pause for wait maintainer selection', ok: "Rollback", parameters: [
                    string(defaultValue: '',
                        description: 'Tag to rollback',
                        name: 'Tag')
                ])
            }
        } catch (err) {
            echo "Exception"
            def user = err.getCauses()[0].getUser()
            if ('SYSTEM' == user.toString()) { // SYSTEM means timeout.
                echo "Timeout is exceeded!"
            } else {
                echo "Aborted by: [${user}]"
            }
            gitTag = 'Aborted'
        }
    }
    def statusCode = sh(script: "git show-ref --verify refs/tags/${gitTag}", returnStatus: true)
    env.GIT_TAG_ROLLBACK = gitTag
    if (gitTag == 'Aborted' || gitTag == '') {
        stage("Cancel deploy process") {
            echo "Deploy process is canceled."
            currentBuild.result = "ABORTED"
        }
    } else {
        if (statusCode == 0) {
            def moduleVersionList = [:]
            stage("Checkout Source Code") {
                sh "ls -la"
                sh "git checkout -b ${gitTag} ${gitTag}"
                sh "ls -la"
                def commitIdStdOut = sh(script: 'git rev-parse HEAD', returnStdout: true)
                env.DEPLOY_GIT_COMMIT_ID = commitIdStdOut.trim()
            }
            env.DEPLOY_RESULT_DESCRIPTION += "<h4>Deploy Phase Result</h4>"
            stage("Get & check Production ENV") {
                def semantic_version = gitTag.split("\\.")
                env.cloud_config_git_branch = "v${semantic_version[0]}.${semantic_version[1]}"
                echo("Cloud config git branch: ${env.cloud_config_git_branch}")
                def pomXMLStr = readFile "pom.xml"
                serviceList = jenkinsfile_utils.getServiceList(pomXMLStr, notServiceModuleList)
                echo "Service List: ${serviceList}"
                def pom = readMavenPom(file: "pom.xml")
                env.project_version = pom.getVersion()
                loadGitConfig(env.cloud_config_git_uri, env.cloud_config_git_branch, "")
                env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Cloud Config Git Version: ${env.cloud_config_git_branch}</h5>"
            }

            stage("Get sub modules version") {
                getSubModuleVersion(serviceList, moduleVersionList)
            }
            stage("Generate secret files") {
                generateSecretFile()
            }
            try {
                genImportantFileAndDeploy(moduleVersionList, serviceList, serviceList, env.cloud_config_git_branch)
            } catch (err) {
                echo "Error: ${err.toString()}. Rollback to latest version!"
                env.DEPLOY_RESULT_DESCRIPTION += "<h4>Error Occur when RollBack Service. Rollback to latest success version</h4>"
                rollBackDeployServers(serviceList)
                error "Rollback to tag ${gitTag} Failed."
            } finally {
                stage("Resume Stack AutoScaling") {
                    resumeHeatStackAutoScaling()
                }
            }
        } else error("Invalid git tag ${gitTag}")
    }
}

def loadGitConfig(String cloudConfigGitURI, String branch, String gitConfigCommitID) {
    sh 'rm -rf git-config'
    sh 'mkdir git-config'
    dir('git-config') {
        checkout changelog: true, poll: true, scm: [
            $class                           : 'GitSCM',
            branches                         : [[name: "${branch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions                       : [[$class: 'UserIdentity', email: 'cloud@viettel.com.vn', name: 'cicd_bot']],
            submoduleCfg                     : [],
            userRemoteConfigs                : [[credentialsId: 'cicd_bot_gitlab_credentials',
                                                 name         : 'origin',
                                                 url          : "${cloudConfigGitURI}"]]
        ]

        def commitIdStdOut = sh(script: 'git rev-parse HEAD', returnStdout: true)
        env.CLOUD_CONFIG_GIT_COMMIT_ID = commitIdStdOut.trim()
        echo "cloud config git repo commit id : ${env.CLOUD_CONFIG_GIT_COMMIT_ID}"

        if (gitConfigCommitID != "") {
            sh "git checkout -b lastGitConfig ${gitConfigCommitID}"
        }
        // load config
        sh "ls -al deploy-config"
        sh "cp deploy-config/production-config.yml ../cicd/deploy-production/ansible/production-config.yml"
        def serviceConfig = readYaml file: "deploy-config/production-config.yml"
        echo "service Config: ${serviceConfig}"
        env.openstack_auth_url = serviceConfig["openstack_auth_url"]
        env.openstack_nova_api_url = serviceConfig["openstack_nova_api_url"]
        env.openstack_user_domain_name = serviceConfig["openstack_user_domain_name"]
        env.openstack_project_domain_name = serviceConfig["openstack_project_domain_name"]
        env.openstack_project_name = serviceConfig["openstack_project_name"]
        env.docker_repo_name = serviceConfig["docker_repo_name"]
        env.docker_push_image_registry_url = serviceConfig["docker_push_image_registry_url"]
        env.docker_pull_image_registry_url = serviceConfig["docker_pull_image_registry_url"]
        env.production_image_base = serviceConfig["production_image_base"]
        env.private_management_network_name = serviceConfig["private_management_network_name"]
        env.vsmart_heat_stack_name = serviceConfig['stack_name']
        env.openstack_aodh_api_url = serviceConfig['openstack_aodh_api_url']
        env.openstack_project_id = serviceConfig['openstack_project_id']
        env.pre_production_ip = serviceConfig['pre_production_ip']
        env.pre_production_hompage_port = serviceConfig['pre_production_hompage_port']
        env.project_maintainer_list = serviceConfig['project_maintainer_list']
        env.stop_autoscaling_timeout = serviceConfig['stop_autoscaling_timeout']
        env.vsmart_group_id = serviceConfig['vsmart-group-id']
    }
    def commitIdStdOut = sh(script: 'git rev-parse HEAD', returnStdout: true)
    env.GIT_COMMIT_ID = commitIdStdOut.trim()
    echo "git source code commit id : ${env.GIT_COMMIT_ID}"
}

def getListServiceUpgrade(serviceList) {
    def listServicesChangedInConfig = []
    def listModulesRebuild = []
    def listModulesDeploy = []
    def lastSuccessfulDeploymentInfo = getLastSuccessDeploymentInfo()
    echo "lastSuccessfulDeploymentInfo: ${lastSuccessDeploymentInfo}"
    def lastDeployGitCommitId = null
    def lastCloudConfigGitCommitId = null
    if (lastSuccessDeploymentInfo != null) {
        lastDeployGitCommitId = lastSuccessfulDeploymentInfo.get("latest-deploy-git-commit-id")
        lastCloudConfigGitCommitId = lastSuccessfulDeploymentInfo.get("latest-cloud-config-git-commit-id")
    }
    def listModulesChangedInCode = []


    if (lastSuccessfulDeploymentInfo == null || lastDeployGitCommitId == null || lastCloudConfigGitCommitId == null) {
        listModulesChangedInCode = serviceList.clone()
        listModulesChangedInCode.add("commons")
    } else {
        // Get list file changed in vsmart-cloud-repos
        def fileChangedArray = sh(script: "git diff --name-only ${lastDeployGitCommitId} ${env.DEPLOY_GIT_COMMIT_ID}", returnStdout: true).split('\n')
        def listFileChanged = toList(fileChangedArray)
        echo "List file change: \n${listFileChanged}"
        def listFileChangedNotInPom = []
        def isCommonsChangedInCode = false
        // Get list modules changed in code
        for (def fileChanged : listFileChanged) {
            if (!fileChanged.endsWith("pom.xml")) {
                def module = fileChanged.split('/')[0]
                if ((module.endsWith("service")) && !listModulesChangedInCode.contains(module)) {
                    listModulesChangedInCode.add(module)
                }
                if (module == "commons") isCommonsChangedInCode = true
                listFileChangedNotInPom.add(fileChanged)
            }
        }
        //Deploy every service when commons changed in code
        if (isCommonsChangedInCode) {
            listModulesChangedInCode = serviceList.clone()
            listModulesChangedInCode.add("commons")
        }
        def listFileChangedInPOM = listFileChanged - listFileChangedNotInPom
        echo "List file changed in POM: ${listFileChangedInPOM}"
        echo "Module changed in code: ${listModulesChangedInCode}"

        //Verify that Maintainer bump version in the good way
        echo "Start verify maintainer has bumped versions in the good way"

        //Verify bump commons version right way
        if (listFileChangedInPOM.contains("commons/pom.xml")) {
            dir("commons") {
                def isBumpCommonsTheRightWay = false
                sh "git show ${lastDeployGitCommitId}:./pom.xml > pom2.xml"
                def pom = readMavenPom([file: "pom.xml"])
                def lastPom = readMavenPom([file: "pom2.xml"])
                def lastVersion = lastPom.getVersion()
                def version = pom.getVersion()
                def dependencies = pom.getDependencies().toString()
                def lastDependencies = lastPom.getDependencies().toString()
                def differences = dependencies - lastDependencies
                def reverseDifferences = lastDependencies - dependencies
                //Cases that bump commons version in the right way
                if (listModulesChangedInCode.contains("commons")) {
                    if (lastVersion != version) isBumpCommonsTheRightWay = true
                } else if (lastVersion != version && (differences.size() != 0 || reverseDifferences.size() != 0))
                    isBumpCommonsTheRightWay = true
                if (isBumpCommonsTheRightWay) {
                    listModulesChangedInCode = serviceList.clone()
                    listModulesChangedInCode.add("commons")
                }
                //Other
                else error("Error when bump version in service commons")
            }
        }
        echo "listFileChangedInPOM: ${listFileChangedInPOM}"
        echo "listModulesChangedInCode: ${listModulesChangedInCode}"

        //Code change but pom.xml not changed
        for (def module : listModulesChangedInCode) {
            String pomFile = "${module}/pom.xml"
            if (!listFileChangedInPOM.contains(pomFile))
                error("Error: maintainer must bump version in ${module}.")
        }

        //Verify bump other service the right way
        for (def str : listFileChangedInPOM) {
            if (str.endsWith("pom.xml") && str != "pom.xml" && str.indexOf("cicd") == -1 && str.indexOf("commons") == -1) {
                def isBumpServiceTheRightWay = false
                String service = str.split('/')[0]
                dir(service) {
                    sh "git show ${lastDeployGitCommitId}:./pom.xml > pom2.xml"
                    def pom = readMavenPom([file: "pom.xml"])
                    def lastPom = readMavenPom([file: "pom2.xml"])
                    def lastVersion = lastPom.getVersion()
                    def version = pom.getVersion()
                    def dependencies = pom.getDependencies().toString()
                    def lastDependencies = lastPom.getDependencies().toString()
                    def differences = dependencies - lastDependencies
                    def reverseDifferences = lastDependencies - dependencies
                    //Cases that bump service version in the right way
                    if (listModulesChangedInCode.contains(service)) {
                        if (lastVersion != version) isBumpServiceTheRightWay = true
                    } else if (lastVersion != version && (differences.size() != 0 || reverseDifferences.size() != 0))
                        isBumpServiceTheRightWay = true
                    if (!listModulesChangedInCode.contains(service)) listModulesChangedInCode.add(service)
                    //Other
                    if (!isBumpServiceTheRightWay) error("Error when bump version in ${service}")
                }
            }
        }
    }
    echo "List modules changed in code: ${listModulesChangedInCode}"

// Get list file change in vsmart-cloud-config-repos
    echo "Last Cloud config Git CM ID: ${lastCloudConfigGitCommitId}"
    if (lastCloudConfigGitCommitId != null) {
        dir('git-config') {
            echo "latest cloud config: ${lastCloudConfigGitCommitId}"
            echo "current cloud config: ${env.CLOUD_CONFIG_GIT_COMMIT_ID}"
            def listConfigFileChanged = sh(script: "git diff --name-only ${lastCloudConfigGitCommitId} ${env.CLOUD_CONFIG_GIT_COMMIT_ID}", returnStdout: true).split('\n')
            echo "${listConfigFileChanged}"
            for (def str : listConfigFileChanged) {
                def service = str.split('/')[0]
                if (service.endsWith("service") && !listServicesChangedInConfig.contains(service)) {
                    echo "${service}"
                    listServicesChangedInConfig.add(service)
                }
            }
            echo "List services changed in cloud config: ${listServicesChangedInConfig}"
        }
        sh 'rm -rf git-config'
    }

// Get list services to be deployed
    echo "listModulesChangedInCode: ${listModulesChangedInCode}"
    listModulesRebuild = listModulesChangedInCode
    echo "listModulesRebuild: ${listModulesRebuild}"
    def listRemoved = []
    for (def i = 0; i < listServicesChangedInConfig.size(); i++) {
        if (listModulesRebuild.contains(listServicesChangedInConfig.getAt(i)))
            listRemoved.add(listServicesChangedInConfig.getAt(i))
    }
    listServicesChangedInConfig -= listRemoved
    listModulesDeploy = listModulesRebuild - ['commons']
    return [listModulesRebuild, listServicesChangedInConfig, listModulesDeploy]
}

def generateHeatStackTemplate(serviceList) {
    docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
        dir('cicd/deploy-production/ansible') {
            def vsmartServiceList = jenkinsfile_utils.toJSONString(
                ["SERVICE_LIST": serviceList])
            echo "${vsmartServiceList}"
            def vsmartServiceListJSON = readJSON text: vsmartServiceList
            writeJSON file: 'vsmart_service_list.json', json: vsmartServiceListJSON
            sh 'cat production-config.yml'
            sh 'cat vsmart_service_list.json'
            def currentDir = pwd()
            sh """
                    ansible-playbook -i jenkins_localhost.ini deploy_utilities.yml \
                        --tags "gen-heat-stack-template" \
                        -e "use_production_utils=True" \
                        -e "@moduleVersionList.json" \
                        -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                        -e "@production-config.yml" \
                        -e "@vsmart_service_list.json"
                        
                    cat vsmart_heat_template.yaml
                    cp vsmart_heat_template.yaml  ${currentDir}/../heat-stack/
                """
        }
    }
}

def stopHeatStackAutoScaling() {
    def openstackAuthToken = ""
    withCredentials([usernamePassword(
        credentialsId: 'vsmart-production-openstack-authentication',
        usernameVariable: 'openstack_username',
        passwordVariable: 'openstack_password')]) {
        openstackAuthToken = getOpenStackAuthToken(
            env.openstack_auth_url,
            env.openstack_project_name,
            env.openstack_user_domain_name,
            env.openstack_project_domain_name,
            openstack_username,
            openstack_password
        )
    }
    def alarmList = getStackScalingAlarmList(openstackAuthToken)
    for (alarmID in alarmList) {
        disableAlarm(openstackAuthToken, alarmID)
    }
}

def getStackScalingAlarmList(openstackAuthToken) {
    def alarmList = []
    def dataBody = """
        {"filter": "{\\"=\\": {\\"project_id\\": \\"${env.openstack_project_id}\\"}}"}
    """
    echo "data body: ${dataBody}"
    def alarmListResp = httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'POST',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${openstackAuthToken}"],
                        [name: "User-Agent", value: "aodh keystoneauth1/3.11.0"]],
        url          : "${env.openstack_aodh_api_url}/query/alarms",
        requestBody  : dataBody
    ])
    for (alarm in jenkinsfile_utils.jsonParse(alarmListResp.content)) {
        if (alarm['name'].contains(env.vsmart_heat_stack_name)) {
            alarmList.add(alarm['alarm_id'])
            echo "alarm detected: ${alarm['name']}"
        }
    }
    return alarmList
}

def createIssueAndMentionMaintainer(issueTitle, issueDescription) {
    echo "issueTitle: ${issueTitle}"
    echo "issueDescription: ${issueDescription}"
    withCredentials([string(credentialsId: 'gitlab-floor5-api-token', variable: 'gitlab_api_token')]) {
        def issueContentJson = """
                                    {
                                        "title": "${issueTitle}",
                                        "description": "${issueDescription}",
                                        "labels": "Deploy Result"
                                    }
                                """
        echo "issueContentJson: ${issueContentJson}"
        def createIssueResp = httpRequest([
            acceptType   : 'APPLICATION_JSON',
            httpMode     : 'POST',
            contentType  : 'APPLICATION_JSON',
            customHeaders: [[name: "PRIVATE-TOKEN", value: "${gitlab_api_token}"]],
            url          : "${env.GITLAB_PROJECT_API_URL}/issues",
            requestBody  : issueContentJson
        ])
        def notifyMemberLevel = 40
        def projectMemberList = jenkinsfile_utils.getProjectMember(notifyMemberLevel)
        def issueCommentStr = ""
        for (member in projectMemberList) {
            issueCommentStr += "@${member} "
        }
        def issueCreated = jenkinsfile_utils.jsonParse(createIssueResp.content)
        def issueCommentJson = """
                                    {
                                        "body": "${issueCommentStr}"
                                    }
                                """
        httpRequest([
            acceptType   : 'APPLICATION_JSON',
            httpMode     : 'POST',
            contentType  : 'APPLICATION_JSON',
            customHeaders: [[name: "PRIVATE-TOKEN", value: "${gitlab_api_token}"]],
            url          : "${env.GITLAB_PROJECT_API_URL}/issues/${issueCreated["iid"]}/notes",
            requestBody  : issueCommentJson
        ])
    }
}

def resumeHeatStackAutoScaling() {
    def openstackAuthToken = ""
    withCredentials([usernamePassword(
        credentialsId: 'vsmart-production-openstack-authentication',
        usernameVariable: 'openstack_username',
        passwordVariable: 'openstack_password')]) {
        openstackAuthToken = getOpenStackAuthToken(
            env.openstack_auth_url,
            env.openstack_project_name,
            env.openstack_user_domain_name,
            env.openstack_project_domain_name,
            openstack_username,
            openstack_password
        )
    }
    def alarmList = getStackScalingAlarmList(openstackAuthToken)
    for (alarmID in alarmList) {
        enableAlarm(openstackAuthToken, alarmID)
    }
}

def enableAlarm(openstackAuthToken, alarmID) {
    def alarmInfoResp = httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'GET',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${openstackAuthToken}"],
                        [name: "User-Agent", value: "aodh keystoneauth1/3.11.0"]],
        url          : "${env.openstack_aodh_api_url}/alarms/${alarmID}"
    ])
    def alarmInfo = jenkinsfile_utils.jsonParse(alarmInfoResp.content)
    echo "Alarm ${alarmID} Data: ${alarmInfo}"

    alarmInfo['enabled'] = true
    httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'PUT',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${openstackAuthToken}"],
                        [name: "User-Agent", value: "aodh keystoneauth1/3.11.0"]],
        url          : "${env.openstack_aodh_api_url}/alarms/${alarmID}",
        requestBody  : jenkinsfile_utils.toJSONString(alarmInfo)
    ])
}

def generateDeployInventoryAndPlaybook(listServiceUpgrade) {
    def vsmartServerList = []
    withCredentials([usernamePassword(
        credentialsId: 'vsmart-production-openstack-authentication',
        usernameVariable: 'openstack_username',
        passwordVariable: 'openstack_password')]) {
        env.openStackAuthToken = getOpenStackAuthToken(
            env.openstack_auth_url,
            env.openstack_project_name,
            env.openstack_user_domain_name,
            env.openstack_project_domain_name,
            openstack_username,
            openstack_password
        )
        vsmartServerList = getVsmartServerList(
            env.openstack_nova_api_url, listServiceUpgrade,
            env.openStackAuthToken, env.private_management_network_name)
    }
    echo "Server List: ${vsmartServerList}"

    def connectedServerList = vsmartServerList['connectedServerList']
    def disconnectedServerList = vsmartServerList['disconnectedServerList']
    if (disconnectedServerList.size() > 0) {
        env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Disconnected Project Server List:</h5>"
        env.DEPLOY_RESULT_DESCRIPTION += "<ul style='list-style-type:disc'>"
        for (server in disconnectedServerList) {
            env.DEPLOY_RESULT_DESCRIPTION +=
                "<li>${server['service']}: ${server['id']}</li>"
        }
        env.DEPLOY_RESULT_DESCRIPTION += "</ul>"
        error "Cannot connect to some project VMs. Fix them and try deploy again later."
    }

    env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Project Server List:</h5>"
    env.DEPLOY_RESULT_DESCRIPTION += "<ul style='list-style-type:disc'>"
    for (serviceName in listServiceUpgrade) {
        if (connectedServerList[serviceName].size() == 0) {
            error "service ${serviceName} has no VMs to deploy"
        }
        env.DEPLOY_RESULT_DESCRIPTION +=
            "<li>${serviceName}: ${connectedServerList[serviceName]}</li>"
    }
    env.DEPLOY_RESULT_DESCRIPTION += "</ul>"
    withCredentials([usernamePassword(credentialsId: 'vsmart-production-vm-authentication',
        usernameVariable: 'USER',
        passwordVariable: 'PASSWORD')]) {
        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
            dir('cicd/deploy-production/ansible') {
                def vsmartServerListJSONStr = jenkinsfile_utils.toJSONString(
                    ["SERVICE_LIST"      : listServiceUpgrade,
                     "VSMART_SERVER_LIST": connectedServerList])
                echo "${vsmartServerListJSONStr}"
                def vsmartServerListJSON = readJSON text: vsmartServerListJSONStr
                writeJSON file: 'vsmart_server_info.json', json: vsmartServerListJSON
                sh 'cat production-config.yml'
                sh 'cat vsmart_server_info.json'
                def currentDir = pwd()
                sh """
                   ansible-playbook -i jenkins_localhost.ini deploy_utilities.yml \
                       --tags "gen-production-inventory-file,gen-deploy-production-playbook" \
                       -e "use_production_utils=True" \
                       -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                       -e "@production-config.yml" \
                       -e "@vsmart_server_info.json"
                """
                sh "cat production_inventory.ini"
                sh "cat deploy_vsmart.yml"
            }
        }
    }
}

def upgradeServices(moduleVersionList, label, listServiceUpgrade) {

    stage("Deploy configuration service and discovery service") {
        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
            dir('cicd/deploy-production/ansible') {
                echo "listServiceUpgrade: ${listServiceUpgrade}"
                if (listServiceUpgrade.contains("configuration-service")) {
                    sh """
                        ansible-playbook -i production_inventory.ini deploy_vsmart.yml \
                            --tags "upgrade-configuration-service" \
                            -e "@production-config.yml" \
                            -e "service_version=${moduleVersionList['configuration-service']}" \
                            -e "label=${label}"
                    """
                    listServiceUpgrade -= ['configuration-service']
                }
                if (listServiceUpgrade.contains("discovery-service")) {
                    sh """
                        ansible-playbook -i production_inventory.ini deploy_vsmart.yml \
                            --tags "upgrade-discovery-service" \
                            -e "@production-config.yml" \
                            -e "service_version=${moduleVersionList['discovery-service']}" \
                            -e "label=${label}"
                    """
                    listServiceUpgrade -= ['discovery-service']
                }
                listServiceUpgrade -= ['gateway-service']
            }
        }
    }

    def branches = [:]
    for (int i = 0; i < listServiceUpgrade.size(); i++) {
        int index = i
        def vsmart_service = listServiceUpgrade.get(i)
        echo "process for service: ${vsmart_service}"
        node("centos7-docker") {
            stage("branch_${vsmart_service}") {
                branches["branch_${vsmart_service}"] = {
                    deployCommonsService(vsmart_service, moduleVersionList.get(vsmart_service), label)
                }
            }
        }
    }
    parallel branches

    stage("Deploy gateway-service") {
        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
            dir('cicd/deploy-production/ansible') {
                sh """
                        ansible-playbook -i production_inventory.ini deploy_vsmart.yml \
                            --tags "upgrade-gateway-service" \
                            -e "service_version=${moduleVersionList.get("gateway-service")}" \
                            -e "@production-config.yml" \
                            -e "label=${label}"
                """
            }
        }
    }
}

def updateVMLatestVersionMetadata(cloud_config_git_commit_id, deploy_git_commit_id) {
    def configurationServers = getProjectServerList(
        env.openstack_nova_api_url, env.openStackAuthToken, "configuration-service")
    def dataBody = """
        {
            "metadata": {
                "latest-cloud-config-git-commit-id": "${cloud_config_git_commit_id}",
                "latest-deploy-git-commit-id": "${deploy_git_commit_id}"
            }
        }
    """
    echo "data: ${dataBody}"
    for (server in configurationServers) {
        def configurationServerInfo = httpRequest([
            acceptType   : 'APPLICATION_JSON',
            httpMode     : 'POST',
            contentType  : 'APPLICATION_JSON',
            customHeaders: [[name: "X-Auth-Token", value: "${env.openStackAuthToken}"],
                            [name: "OpenStack-API-Version", value: "compute 2.60"],
                            [name: "User-Agent", value: "python-novaclient"],
                            [name: "X-OpenStack-Nova-API-Version", value: "2.60"]],
            url          : "${env.openstack_nova_api_url}/servers/${server["id"]}/metadata",
            requestBody  : dataBody
        ])
        echo "response: ${configurationServerInfo.content}"
    }
}

def rollBackDeployServers(listServicesUpgrade) {
    try {
        def lastSuccessDeploymentInfo = getLastSuccessDeploymentInfo()
        if (lastSuccessDeploymentInfo["latest-cloud-config-git-commit-id"] == null && lastSuccessDeploymentInfo["latest-deploy-git-commit-id"] == null) {
            env.DEPLOY_RESULT_DESCRIPTION += "<h4>Latest success version doesn't exist. Rollback failed.</h4>"
        } else {
            env.DEPLOY_RESULT_DESCRIPTION += "<h4>Roll back info: </h4>"
            env.DEPLOY_RESULT_DESCRIPTION += "<ul style='list-style-type:disc'>"
            env.DEPLOY_RESULT_DESCRIPTION += "<li>Roll backed git version: ${lastSuccessDeploymentInfo['latest-deploy-git-commit-id']}</li>"
            env.DEPLOY_RESULT_DESCRIPTION += "<li>Roll backed git config version: ${lastSuccessDeploymentInfo['latest-cloud-config-git-commit-id']}</li>"
            env.DEPLOY_RESULT_DESCRIPTION += "</ul>"

            stage("Load last git config") {
                loadGitConfig(env.cloud_config_git_uri, env.cloud_config_git_branch, lastSuccessDeploymentInfo["latest-cloud-config-git-commit-id"])
            }
            def pomXMLStr = readFile "pom.xml"
            def serviceList = jenkinsfile_utils.getServiceList(pomXMLStr, notServiceModuleList)
            def lastmoduleVersionList = [:]
            // TODO: Handle when service list changed
            stage("Re-Generate Heat Template") {
                sh 'ls -la'
                sh 'git reset --hard'
                sh "git checkout -b lastDeploy ${lastSuccessDeploymentInfo['latest-deploy-git-commit-id']}"
                echo "ServiceList: ${serviceList}"
                for (def service : serviceList) {
                    dir(service) {
                        def service_pom = readMavenPom(file: "pom.xml")
                        def service_version = service_pom.getVersion()
                        lastmoduleVersionList[service] = service_version
                    }
                }
                def moduleVersionListStr = jenkinsfile_utils.toJSONString(["map_module_version": lastmoduleVersionList])

                def lastmoduleVersionListJSON = readJSON text: moduleVersionListStr
                writeJSON file: 'moduleVersionList.json', json: lastmoduleVersionListJSON
                sh 'ls -la'
                sh "cat moduleVersionList.json"
                sh "cp moduleVersionList.json ./cicd/deploy-production/ansible/"
                generateHeatStackTemplate(serviceList)
            }

            stage("Revert Heat Stack To Previous Version") {
                docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
                    dir('cicd/deploy-production/ansible') {
                        def currentDir = pwd()
                        sh """
                            ansible-playbook -i jenkins_localhost.ini deploy_vsmart_heat_stack.yml \
                                --tags "create-or-update-stack" \
                                -e "@moduleVersionList.json" \
                                -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                                -e "@production-config.yml" \
                                -e "cloud_config_git_commit_id=${
                            lastSuccessDeploymentInfo["latest-cloud-config-git-commit-id"]
                        }"
                        """
                    }
                }
            }
            echo "rollback service list: ${listServicesUpgrade}"
            stage("Get vsmart server list from Cloud, gen inventory file and, gen production play book. ") {
                def defaultDeployModule = ['gateway-service']
                listServicesUpgrade -= defaultDeployModule
                listServicesUpgrade += defaultDeployModule
                generateDeployInventoryAndPlaybook(listServicesUpgrade)
            }

            upgradeServices(lastmoduleVersionList,
                lastSuccessDeploymentInfo["latest-cloud-config-git-commit-id"], listServicesUpgrade)
            env.DEPLOY_RESULT_DESCRIPTION += "<h4>:white_check_mark: Roll back result: Roll back success.</h4>"
        }
    } catch (Exception e) {
        echo "${e.toString()}"
        env.DEPLOY_RESULT_DESCRIPTION += "<h4>:x: Roll back result: Roll back failed.</h4>"
        error "Roll back failed."
    }
}

def getLastSuccessDeploymentInfo() {
    def latestCloudConfigGitCommitId = null
    def latestDeployGitCommitId = null
    def configurationServers = getProjectServerList(
        env.openstack_nova_api_url, env.openStackAuthToken, "configuration-service")
    def i = 0
    echo "configuration server list: ${configurationServers}"
    while (i < configurationServers.size() && latestCloudConfigGitCommitId == null) {
        def configurationServerInfo = httpRequest([
            acceptType   : 'APPLICATION_JSON',
            httpMode     : 'GET',
            contentType  : 'APPLICATION_JSON',
            customHeaders: [[name: "X-Auth-Token", value: "${env.openStackAuthToken}"],
                            [name: "OpenStack-API-Version", value: "compute 2.60"],
                            [name: "User-Agent", value: "python-novaclient"],
                            [name: "X-OpenStack-Nova-API-Version", value: "2.60"]],
            url          : "${env.openstack_nova_api_url}/servers/${configurationServers[i]["id"]}",
        ])
        echo "info: ${configurationServerInfo.content}"
        latestCloudConfigGitCommitId = jenkinsfile_utils.jsonParse(
            configurationServerInfo.content)["server"]["metadata"]["latest-cloud-config-git-commit-id"]
        latestDeployGitCommitId = jenkinsfile_utils.jsonParse(configurationServerInfo.content)["server"]["metadata"]["latest-deploy-git-commit-id"]
        i += 1
    }
    echo "latest CloudConfig Git CommitId: ${latestCloudConfigGitCommitId}"
    echo "latest Deploy Git CommitId: ${latestDeployGitCommitId}"

    return ["latest-cloud-config-git-commit-id": latestCloudConfigGitCommitId, "latest-deploy-git-commit-id": latestDeployGitCommitId]
}

def getProjectServerList(nova_api_url, auth_token) {
    def serverListResp = httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'GET',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${auth_token}"],
                        [name: "OpenStack-API-Version", value: "compute 2.60"],
                        [name: "User-Agent", value: "python-novaclient"],
                        [name: "X-OpenStack-Nova-API-Version", value: "2.60"]],
        url          : "${nova_api_url}/servers?tags=vsmart"
    ])
    return jenkinsfile_utils.jsonParse(serverListResp.content)["servers"]
}

def getProjectServerList(nova_api_url, auth_token, serviceName) {
    def serverListResp = httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'GET',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${auth_token}"],
                        [name: "OpenStack-API-Version", value: "compute 2.60"],
                        [name: "User-Agent", value: "python-novaclient"],
                        [name: "X-OpenStack-Nova-API-Version", value: "2.60"]],
        url          : "${nova_api_url}/servers?tags=${serviceName}"
    ])
    return jenkinsfile_utils.jsonParse(serverListResp.content)["servers"]
}

def disableAlarm(openstackAuthToken, alarmID) {
    def alarmInfoResp = httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'GET',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${openstackAuthToken}"],
                        [name: "User-Agent", value: "aodh keystoneauth1/3.11.0"]],
        url          : "${env.openstack_aodh_api_url}/alarms/${alarmID}"
    ])
    def alarmInfo = jenkinsfile_utils.jsonParse(alarmInfoResp.content)
    echo "Alarm ${alarmID} Data: ${alarmInfo}"

    alarmInfo['enabled'] = false
    httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'PUT',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${openstackAuthToken}"],
                        [name: "User-Agent", value: "aodh keystoneauth1/3.11.0"]],
        url          : "${env.openstack_aodh_api_url}/alarms/${alarmID}",
        requestBody  : jenkinsfile_utils.toJSONString(alarmInfo)
    ])
}

def getVsmartServerList(nova_api_url, service_list, auth_token, private_network_name) {
    echo "env: ${nova_api_url}, ${service_list}, ${auth_token}"
    def connectedServerList = [:]

    for (service in service_list) {
        connectedServerList[service] = []
    }
    def disConnectedServerList = []
    def projectServerList = getProjectServerList(nova_api_url, auth_token)
    echo " project server list: ${projectServerList}"

    def branches = [:]
    for (int i = 0; i < projectServerList.size(); i++) {
        int index = i
        node() {
            stage("branch_${projectServerList.get(index)["id"]}") {
                branches["check-${projectServerList.get(index)["id"]}"] = {

                    def checkServer = projectServerList.get(index)
                    checkServer["vsmartServer"] = false
                    def serverInfo = getServerInfo(nova_api_url, checkServer["id"], auth_token)
                    if (serverInfo["tags"].contains("vsmart")) {
                        for (tag in serverInfo["tags"]) {
                            for (service in service_list) {
                                if (service == tag) {
                                    checkServer["vsmartServer"] = true
                                    checkServer["service"] = tag
                                    break
                                }
                            }
                        }
                        if (checkServer["vsmartServer"] == true) {
                            def serverIP = null
                            if (serverInfo["status"] == "ACTIVE") {
                                //TODO (@conghm1) Re-enable check host status if necessary
//                                if (serverInfo["status"] == "ACTIVE" && serverInfo["host_status"] == "UP") {
                                serverIP = getServerIP(nova_api_url, auth_token, checkServer["id"], private_network_name)
                                def retry = 0
                                def isConnected = false
                                docker.image("10.240.201.50:7890/alpine:telnet").inside() {
                                    while (retry < 3 && !isConnected) {
                                        def exitCode = sh script: "telnet ${serverIP} 22", returnStatus: true
                                        if (exitCode == 0) {
                                            isConnected = true
                                        } else {
                                            sleep 120
                                            retry += 1
                                        }
                                    }
                                }
                                if (isConnected) {
                                    checkServer["status"] = "up"
                                } else {
                                    checkServer["status"] = "down"
                                }
                            } else {
                                checkServer["status"] = "down"
                            }
                            checkServer["IP"] = serverIP
                        }
                    }
                }

            }
        }
    }
    parallel branches

    for (checkServer in projectServerList) {
        if (checkServer['vsmartServer'] == true) {
            if (checkServer['status'] == "up") {
                echo "${checkServer['service']}"
                if (checkServer['service'] != null && service_list.contains(checkServer['service'])) {
                    connectedServerList[checkServer['service']].add(checkServer['IP'])
                }
            } else {
                disConnectedServerList.add(checkServer)
            }
        }
    }
    return ['connectedServerList': connectedServerList, 'disconnectedServerList': disConnectedServerList]
}

def getOpenStackAuthToken(auth_url, project_name, domain_name, project_domain_name, username, password) {
    def authBody = """
        { "auth": {
            "identity": {
              "methods": ["password"],
              "password": {
                "user": {
                  "name": "${username}",
                  "domain": { "id": "${domain_name}" },
                  "password": "${password}"
                }
              }
            },
            "scope": {
              "project": {
                "name": "${project_name}",
                "domain": { "id": "${project_domain_name}" }
              }
            }
          }
        }
    """
    def tokenResp = httpRequest([
        acceptType : 'APPLICATION_JSON',
        httpMode   : 'POST',
        contentType: 'APPLICATION_JSON',
        url        : "${auth_url}/auth/tokens",
        requestBody: authBody
    ])
    return tokenResp.headers['X-Subject-Token'][0]
}

def getServerInfo(nova_api_url, server_id, auth_token) {
    def retry = 0
    def serverTags = ''
    while (retry < 3) {
        try {
            serverTags = httpRequest([
                acceptType   : 'APPLICATION_JSON',
                httpMode     : 'GET',
                contentType  : 'APPLICATION_JSON',
                customHeaders: [[name: "X-Auth-Token", value: "${auth_token}"],
                                [name: "OpenStack-API-Version", value: "compute 2.60"],
                                [name: "User-Agent", value: "python-novaclient"],
                                [name: "X-OpenStack-Nova-API-Version", value: "2.60"]],
                url          : "${nova_api_url}/servers/${server_id}",
            ])
            if (serverTags.status == 200) break
        } catch (err) {
            echo "${err}"
            echo "Retry to send request to nova api.."
            sleep 10
            retry++
        }
    }
    if (retry == 3) error "Failed when getting Server info"
    return jenkinsfile_utils.jsonParse(serverTags.content)["server"]
}

def getServerIP(nova_api_url, auth_token, server_id, private_network_name) {
    def serverInfo = httpRequest([
        acceptType   : 'APPLICATION_JSON',
        httpMode     : 'GET',
        contentType  : 'APPLICATION_JSON',
        customHeaders: [[name: "X-Auth-Token", value: "${auth_token}"],
                        [name: "OpenStack-API-Version", value: "compute 2.60"],
                        [name: "User-Agent", value: "python-novaclient"],
                        [name: "X-OpenStack-Nova-API-Version", value: "2.60"]],
        url          : "${nova_api_url}/servers/${server_id}",
    ])
    def ipAddr = null
    echo "network info list: ${jenkinsfile_utils.jsonParse(serverInfo.content)["server"]["addresses"]}"
    jenkinsfile_utils.jsonParse(serverInfo.content)["server"]["addresses"].each { networkName, networkInfoList ->
        if (networkName == private_network_name) {
            echo "network info list: ${networkInfoList}"
            echo "network info: ${networkInfoList[0]}"
            def networkInfo = networkInfoList[0]
            ipAddr = networkInfo["addr"]
        }
    }
    echo "address: ${ipAddr}"
    return ipAddr
}

def toList(value) {
    return [value].flatten().findAll { it != null }
}

def deployCommonsService(String service_name, String service_version, label) {
    docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
        dir('cicd/deploy-production/ansible') {
            sh """
            ansible-playbook -i production_inventory.ini deploy_vsmart.yml \
                --tags "upgrade-${service_name}" \
                -e "service_version=${service_version}" \
                -e "@production-config.yml" \
                -e "label=${label}"
            """
        }
    }
}

def genImportantFileAndDeploy(moduleVersionList, serviceList, serviceListToUpgrade, label) {
    dir('cicd/deploy-production/ansible') {
        moduleVersionList.remove('commons')
        def moduleVersionListStr = jenkinsfile_utils.toJSONString(["map_module_version": moduleVersionList])
        def moduleVersionListJSON = readJSON text: moduleVersionListStr
        writeJSON file: 'moduleVersionList.json', json: moduleVersionListJSON
        sh 'cat moduleVersionList.json'
    }
    stage("Generate Heat Template") {
        generateHeatStackTemplate(serviceList)
    }
    stage("Create Heat Stack if not exist & Update Heat Stack") {
        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
            dir('cicd/deploy-production/ansible') {
                def currentDir = pwd()
                sh """
                                ansible-playbook -i jenkins_localhost.ini deploy_vsmart_heat_stack.yml \
                                    --tags "create-or-update-stack" \
                                    -e "@moduleVersionList.json" \
                                    -e "CURRENT_JENKINS_LOCAL_DIR=${currentDir}" \
                                    -e "@production-config.yml" \
                                    -e "cloud_config_git_commit_id=${env.CLOUD_CONFIG_GIT_COMMIT_ID}"
                            """
            }
        }
    }

    stage("Stop Heat Stack Auto Scaling") {
        stopHeatStackAutoScaling()
        sleep(time: env.stop_autoscaling_timeout, unit: "SECONDS")
    }

    stage("Get vsmart server list inventory file and, gen production play book. ") {
        generateDeployInventoryAndPlaybook(serviceListToUpgrade)
    }
    upgradeServices(moduleVersionList, label, serviceListToUpgrade)
    updateVMLatestVersionMetadata(env.CLOUD_CONFIG_GIT_COMMIT_ID, env.DEPLOY_GIT_COMMIT_ID)
}

def getSubModuleVersion(serviceList, moduleVersionList) {
    sh 'ls -la'
    for (def service : serviceList) {
        echo "${service}"
        dir(service) {
            def service_pom = readMavenPom(file: "pom.xml")
            def service_version = service_pom.getVersion()
            moduleVersionList[service] = service_version
        }
    }
    dir("commons") {
        def commons_pom = readMavenPom(file: "pom.xml")
        def commons_version = commons_pom.getVersion()
        moduleVersionList['commons'] = commons_version
    }
    echo "Module and it's version: ${moduleVersionList}"
    env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Project Version: ${env.project_version} - commitID: ${env.GIT_COMMIT_ID}</h5>"
    env.DEPLOY_RESULT_DESCRIPTION += "<h5>- Project Service List:</h5>"
    env.DEPLOY_RESULT_DESCRIPTION += "<ul style='list-style-type:disc'>"
    for (serviceName in serviceList) {
        env.DEPLOY_RESULT_DESCRIPTION += "<li>${serviceName}: version ${moduleVersionList[serviceName]}</li>"
    }
    env.DEPLOY_RESULT_DESCRIPTION += "</ul>"
}

def generateSecretFile() {
    withCredentials([
        usernamePassword(
            credentialsId: 'vsmart-production-openstack-authentication',
            usernameVariable: 'production_openstack_username',
            passwordVariable: 'production_openstack_password'),
        usernamePassword(
            credentialsId: 'vsmart-production-vm-authentication',
            usernameVariable: 'production_vm_username',
            passwordVariable: 'production_vm_password')
    ]) {
        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
            dir('cicd/deploy-production/ansible') {
                def currentDir = pwd()
                sh """
                            ansible-playbook -i jenkins_localhost.ini deploy_utilities.yml \
                                --tags "gen-secret-env-file" \
                                -e use_production_utils=True \
                                -e CURRENT_JENKINS_LOCAL_DIR=${currentDir} \
                                -e openstack_username=${production_openstack_username}  \
                                -e openstack_password=${production_openstack_password}  \
                                -e production_vm_username=${production_vm_username}  \
                                -e production_vm_password=${production_vm_password}
                            """
                sh 'cat group_vars/all/secret.yml'
            }
        }
    }
}

return [
    deployToProduction: this.&deployToProduction,
    rollBackTag       : this.&rollBackTag
]
