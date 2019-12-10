import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException
import java.text.DecimalFormat
import hudson.tasks.test.AbstractTestResultAction


def getSonarQubeAnalysisResult(sonarQubeURL, projectKey) {
    def metricKeys = "bugs,vulnerabilities"
    def measureResp = httpRequest([
        acceptType : 'APPLICATION_JSON',
        httpMode   : 'GET',
        contentType: 'APPLICATION_JSON',
        url        : "${sonarQubeURL}/api/measures/component?metricKeys=${metricKeys}&component=${projectKey}"
    ])
    def measureInfo = jenkinsfile_utils.jsonParse(measureResp.content)
    def metricResultList = measureInfo['component']['measures']
    echo "${metricResultList}"
    int bugsEntry = getMetricEntryByKey(metricResultList, "bugs")['value'] as Integer
    int vulnerabilitiesEntry = getMetricEntryByKey(metricResultList, "vulnerabilities")['value'] as Integer
    return ["bugs": bugsEntry, "vulnerabilities": vulnerabilitiesEntry]
}

def getMetricEntryByKey(metricResultList, metricKey) {
    for (metricEntry in metricResultList) {
        if (metricEntry["metric"] == metricKey) {
            echo "${metricEntry}"
            return metricEntry
        }
    }
    return null
}


@NonCPS
def getProjectCodeCoverageInfo(coverageInfoXmlStr) {
    def coverageInfoXml = jenkinsfile_utils.parseXml(coverageInfoXmlStr)
    def coverageInfoStr = ""
    coverageInfoXml.counter.each {
        def coverageType = it.@type as String
        int missed = (it.@missed as String) as Integer
        int covered = (it.@covered as String) as Integer
        int total = missed + covered

        def coveragePercent = 0.00
        if (total > 0) {
            coveragePercent = Double.parseDouble(
                new DecimalFormat("###.##").format(covered * 100.0 / total))
        }
        coverageInfoStr += "- <b>${coverageType}</b>: <i>${covered}</i>/<i>${total}</i> (<b>${coveragePercent}%</b>)<br/>"
    }
    return coverageInfoStr
}


@NonCPS
def getTestResultFromJenkins() {
    def testResult = [:]
    AbstractTestResultAction testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    testResult["total"] = testResultAction.totalCount
    testResult["failed"] = testResultAction.failCount
    testResult["skipped"] = testResultAction.skipCount
    testResult["passed"] = testResultAction.totalCount - testResultAction.failCount - testResultAction.skipCount
    return testResult
}

Long getTotalCheckStyleWarning(checkStyleXMLStr) {
    def checkStyleXML = jenkinsfile_utils.parseXml(checkStyleXMLStr)
    def totalWarning = 0L
    checkStyleXML.file.each {
        def fileWarning = 0L
        it.error.each {
            fileWarning += 1L
        }
        totalWarning += fileWarning
    }
    return totalWarning
}

@NonCPS
def genSonarQubeProjectKey() {
    def sonarqubeProjectKey = ""
    if ("${env.gitlabActionType}".toString() == "PUSH" || "${env.gitlabActionType}".toString() == "TAG_PUSH") {
        sonarqubeProjectKey = "${env.gitlabSourceRepoName}:${env.gitlabSourceBranch}"
    } else if ("${env.gitlabActionType}".toString() == "MERGE" || "${env.gitlabActionType}".toString() == "NOTE") {
        sonarqubeProjectKey = "MR-${env.gitlabSourceRepoName}:${env.gitlabSourceBranch}-to-" +
            "${env.gitlabTargetBranch}"
    }
    return sonarqubeProjectKey.replace('/', '-')
}


def unitTestCodeCoverageAndSonarQubeScan(buildType) {
    stage("Checkout Source Code") {
        jenkinsfile_utils.checkoutSourceCode(buildType)
        sh './build.sh clean'
    }

    stage("Unit Test & Code Coverage Test") {
        try {
            echo "code coverage started"
            sh './build.sh coverage'
            echo "code coverage done"
            publishHTML([
                allowMissing         : false,
                alwaysLinkToLastBuild: false,
                keepAll              : true,
                reportDir            : 'cicd/target/jacoco-aggregate-report/',
                reportFiles          : 'index.html',
                reportName           : 'Code-Coverage-Report',
                reportTitles         : 'Code-Coverage-Report'])

            def coverageResultStrComment = "<b>Coverage Test Result:</b> <br/><br/>"
            def coverageInfoXmlStr = readFile "cicd/target/jacoco-aggregate-report/jacoco.xml"
            echo "Coverage Info: ${getProjectCodeCoverageInfo(coverageInfoXmlStr)} "
            coverageResultStrComment += getProjectCodeCoverageInfo(coverageInfoXmlStr)
            coverageResultStrComment += "<i><a href='${env.BUILD_URL}Code-Coverage-Report/'>" +
                "Details Code Coverage Test Report...</a></i><br/><br/>"
            env.CODE_COVERAGE_RESULT_STR = coverageResultStrComment
        } catch (err) {
            echo "Error when test Unit Test"
            throw err
        } finally {
            sh 'ls -al'
            junit '*/target/*-reports/TEST-*.xml'
            def unitTestResult = getTestResultFromJenkins()

            env.UNIT_TEST_PASSED = unitTestResult["passed"]
            env.UNIT_TEST_FAILED = unitTestResult["failed"]
            env.UNIT_TEST_SKIPPED = unitTestResult["skipped"]
            env.UNIT_TEST_TOTAL = unitTestResult["total"]

            def testResultContent = "- Passed: <b>${unitTestResult['passed']}</b> <br/>" +
                "- Failed: <b>${unitTestResult['failed']}</b> <br/>" +
                "- Skipped: <b>${unitTestResult['skipped']}</b> <br/>"

            def testResultString = "<b> Unit Test Result:</b> <br/><br/>${testResultContent} " +
                "<i><a href='${env.BUILD_URL}testReport/'>Details Unit Test Report...</a></i><br/><br/>"
            env.UNIT_TEST_RESULT_STR = testResultString

            if (unitTestResult['failed'] > 0) {
                error "Failed ${unitTestResult['failed']} unit tests"
            }
        }
    }

    stage('SonarQube analysis') {
        env.SONAR_QUBE_PROJECT_KEY = genSonarQubeProjectKey()
        docker.image(env.MAVEN_ORACLE_JDK_10_IMAGE).inside("-v '${HOME}/.m2:/root/.m2:rw'") {
            withSonarQubeEnv('sonarqube-floor5') {
                sh "mvn  --settings .settings.xml sonar:sonar " +
                    "-Dsonar.projectName=${env.SONAR_QUBE_PROJECT_KEY} " +
                    "-Dsonar.projectKey=${env.SONAR_QUBE_PROJECT_KEY} "
                sh 'ls -al'
                sh 'cat target/sonar/report-task.txt'
                def props = readProperties file: 'target/sonar/report-task.txt'
                env.SONAR_CE_TASK_ID = props['ceTaskId']
                env.SONAR_PROJECT_KEY = props['projectKey']
                env.SONAR_SERVER_URL = props['serverUrl']
                env.SONAR_DASHBOARD_URL = props['dashboardUrl']

                echo "SONAR_SERVER_URL: ${env.SONAR_SERVER_URL}"
                echo "SONAR_PROJECT_KEY: ${env.SONAR_PROJECT_KEY}"
                echo "SONAR_DASHBOARD_URL: ${env.SONAR_DASHBOARD_URL}"
            }
        }
    }

    stage("Quality Gate") {
        def qg = null
        try {
            def sonarQubeRetry = 0
            def sonarScanCompleted = false
            while (!sonarScanCompleted) {
                try {
                    sleep 10
                    timeout(time: 1, unit: 'MINUTES') {
                        script {
                            qg = waitForQualityGate()
                            sonarScanCompleted = true
                            if (qg.status != 'OK') {
                                error "Pipeline failed due to quality gate failure: ${qg.status}"
                            }
                        }
                    }
                } catch (FlowInterruptedException interruptEx) {
                    // check if exception is system timeout
                    if (interruptEx.getCauses()[0] instanceof org.jenkinsci.plugins.workflow.steps.TimeoutStepExecution.ExceededTimeout) {
                        if (sonarQubeRetry <= 10) {
                            sonarQubeRetry += 1
                        } else {
                            error "Cannot get result from Sonarqube server. Build Failed."
                        }
                    } else {
                        throw interruptEx
                    }
                }
                catch (err) {
                    throw err
                }
            }
        }
        catch (err) {
            throw err
        } finally {
            def codeAnalysisResult = getSonarQubeAnalysisResult(env.SONAR_SERVER_URL, env.SONAR_PROJECT_KEY)
            def sonarQubeAnalysisStr = "- Vulnerabilities: <b>${codeAnalysisResult["vulnerabilities"]}</b> <br/>" +
                "- Bugs: <b>${codeAnalysisResult["bugs"]}</b> <br/>"
            def sonarQubeAnalysisComment = "<b>SonarQube Code Analysis Result:</b> <br/><br/>${sonarQubeAnalysisStr} " +
                "<i><a href='${SONAR_DASHBOARD_URL}'>" +
                "Details SonarQube Code Analysis Report...</a></i><br/><br/>"
            env.SONAR_QUBE_SCAN_RESULT_STR = sonarQubeAnalysisComment
            if ("${env.gitlabActionType}".toString() == "MERGE" || "${env.gitlabActionType}".toString() == "NOTE") {
                echo "check vulnerabilities and bugs"
                int maximumAllowedVulnerabilities = env.MAXIMUM_ALLOWED_VUNERABILITIES as Integer
                int maximumAllowedBugs = env.MAXIMUM_ALLOWED_BUGS as Integer
                echo "maximum allow vulnerabilities:  ${maximumAllowedVulnerabilities} "
                echo "maximum allow bugs:  ${maximumAllowedBugs}"
                if (codeAnalysisResult["vulnerabilities"] > maximumAllowedVulnerabilities ||
                    codeAnalysisResult["bugs"] > maximumAllowedBugs) {
                    error "Vulnerability or bug number overs allowed limits!"
                }
            }
        }
    }
}

def checkStyleTest(buildType) {
    stage("Checkout Source Code") {
        jenkinsfile_utils.checkoutSourceCode(buildType)
        sh './build.sh clean'
    }
    stage("Check Style Test") {
        def totalCheckStyleWarning = ""
        docker.image(env.MAVEN_ORACLE_JDK_10_IMAGE).inside("-v '${HOME}/.m2:/root/.m2:rw'") {
            sh 'mvn  --settings .settings.xml jxr:aggregate checkstyle:checkstyle-aggregate'
            checkstyle pattern: 'target/checkstyle-result.xml',
                canRunOnFailed: true,
                defaultEncoding: '',
                healthy: '100',
                unHealthy: '90',
                useStableBuildAsReference: true
            def checkstyleXMLStr = readFile "target/checkstyle-result.xml"
            totalCheckStyleWarning = getTotalCheckStyleWarning(checkstyleXMLStr)

        }
        def testResultString = "<b>Total warning</b>: ${totalCheckStyleWarning.toString()}<br/>".toString()
        def testResultComment = "<b>Check Sytle Test Result:</b> <br/><br/>${testResultString} " +
            "<i><a href='${env.BUILD_URL}checkstyleResult/'>Details Check Style Test Report...</a></i><br/><br/>"
        env.CHECK_STYLE_TEST_RESULT_STR = testResultComment
    }
}

def packageServicesAndUploadToRepo(dockerImageTag, serviceList) {
    stage("Package & Build Staging Docker Image") {
        sh """
            ./build.sh clean

            rm .env
            touch .env
            echo 'IMAGE_STAGING_REGISTRY_URL=${env.DOCKER_STAGING_PUSH_REGISTRY}' >> .env
            echo 'IMAGE_TYPE=${env.STAGING_IMAGE_TYPE}' >> .env
            echo 'STACK_VERSION=${dockerImageTag}' >> .env
            echo 'JDK_BASE_IMAGE=${env.STAGING_BASE_IMAGE}' >> .env

            ./build.sh service-stack-build --staging
        """
    }

    stage('Upload artifacts to Docker repository') {
        def uploadSuccessComment = "<b>Build & package Artifact Results - " +
            "Staging Image for services is created. " +
            "Download it by command:</b><br/><br/> "

        withDockerRegistry([
            credentialsId: 'nexus-repo-credentials',
            url          : "http://${env.DOCKER_STAGING_PUSH_REGISTRY}"]) {
            for (def i = 0; i < serviceList.size(); i++) {
                def serviceName = serviceList.get(i)
                def stagingImage = docker.image("${env.DOCKER_STAGING_PUSH_REGISTRY}/vsmart/" +
                    "${env.STAGING_IMAGE_TYPE}/${serviceName}:${dockerImageTag}")
                stagingImage.push()
                uploadSuccessComment += "- <i>docker pull ${env.DOCKER_STAGING_PULL_REGISTRY}/vsmart/" +
                    "${env.DOCKER_STAGING_PULL_REGISTRY}/${serviceName}:${dockerImageTag}</i>" +
                    "<br/>"
            }
        }
        sh './build.sh service-stack-down --staging'
        env.PACKAGE_UPLOAD_IMAGE_RESULT_STR = uploadSuccessComment
    }
}


def updateStagingEnv(updateAction) {
    def stagingPublicIP = ""
    def actionTags = ""

    if (updateAction == "create") {
        actionTags = "create-staging-stack"
    } else if (updateAction == "destroy") {
        actionTags = "destroy-staging-stack"
    } else {
        error "Input action is not valid"
    }

    withCredentials([usernamePassword(
        credentialsId: 'openstack_staging_auth_credential_floor5',
        usernameVariable: 'openstack_username',
        passwordVariable: 'openstack_password')]) {
        docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
            dir('cicd/continous-integration/ansible') {
                def stagingStackName = "${env.gitlabTargetRepoName}-" +
                    "${env.gitlabSourceBranch}-${env.gitlabTargetBranch}-staging".replace("/", "-")
                sh """
                    ansible-playbook -i jenkins_localhost.ini\
                        deploy_staging_heat_stack.yml --tags ${actionTags}\
                        -e stack_name=${stagingStackName}  \
                        -e server_name=${env.gitlabTargetRepoName}-${env.gitlabMergeRequestIid}\
                        -e openstack_username=${openstack_username}\
                        -e openstack_password=${openstack_password}\
                        -e gitlab_merge_source_branch=${env.gitlabSourceBranch}\
                        -e gitlab_merge_target_branch=${env.gitlabTargetBranch}\
                        -e gitlab_project_api_url=${env.GITLAB_PROJECT_API_URL}\
                        -e openstack_staging_auth_url=${env.OPENSTACK_STAGING_AUTH_URL}\
                        -e staging_ssh_key=${env.STAGING_SSH_KEY}\
                        -e staging_flavor=${env.STAGING_FLAVOR}\
                        -e staging_image=${env.STAGING_IMAGE}\
                        -e staging_public_net_id=${env.STAGING_PUBLIC_NET_ID}\
                        -e staging_public_subnet_id=${env.STAGING_PUBLIC_SUBNET_ID}\
                        -e staging_private_net_id=${env.STAGING_PRIVATE_NET_ID}\
                        -e staging_private_subnet_id=${env.STAGING_PRIVATE_SUBNET_ID}\
                        -e staging_user_domain_name=${env.OPENSTACK_STAGING_USER_DOMAIN_NAME}\
                        -e staging_project_domain_name=${env.OPENSTACK_STAGING_PROJECT_DOMAIN_NAME}\
                        -e staging_project_name=${env.OPENSTACK_STAGING_PROJECT_NAME}
                """
                if (updateAction == "create") {
                    def staging_info = jenkinsfile_utils.jsonParse(readFile(file: 'staging_server_ip.json'))
                    for (output in staging_info) {
                        if (output['output_key'] == "staging_server_public_ip") {
                            echo " staging public ip: ${output['output_value']}"
                            stagingPublicIP = output['output_value']
                        }
                    }
                }
            }
        }
    }
    return stagingPublicIP
}

def getFunctionalTestStatus(unitTestResult) {
    def functionalTestResult = [:]
    def testResult = getTestResultFromJenkins()
    if (testResult != null && unitTestResult != null) {
        functionalTestResult["total"] = testResult["total"] - unitTestResult["total"].toInteger()
        functionalTestResult["failed"] = testResult["failed"] - unitTestResult["failed"].toInteger()
        functionalTestResult["skipped"] = testResult["skipped"] - unitTestResult["skipped"].toInteger()
        functionalTestResult["passed"] = testResult["passed"] - unitTestResult["passed"].toInteger()
    }
    return functionalTestResult
}

def buildPushCommit() {
    def tasks = [:]
    tasks['Unit Tests, Code Coverage And SonarQube Tests'] = {
        node("centos7-docker") {
            unitTestCodeCoverageAndSonarQubeScan("PUSH")
        }
    }
    tasks['Check Style Tests'] = {
        node("centos7-docker") {
            checkStyleTest("PUSH")
        }
    }
    parallel tasks
    currentBuild.result = "SUCCESS"
}

def buildMergeRequest() {

    def notServiceModuleList = ["cicd", "commons"]
    def pomXMLStr = readFile "pom.xml"
    def serviceList = jenkinsfile_utils.getServiceList(pomXMLStr, notServiceModuleList)
    def pom = readMavenPom file: "pom.xml"
    def projectVersion = pom.getVersion()
    def dockerImageTag = "${projectVersion}.${env.BUILD_ID}"
    def stagingPublicIP = null

    def tasks = [:]

    tasks['Unit Tests, Code Coverage And SonarQube Tests'] = {
        node("centos7-docker") {
            unitTestCodeCoverageAndSonarQubeScan("MERGE")
        }
    }
    tasks['Check Style Tests'] = {
        node("centos7-docker") {
            checkStyleTest("MERGE")
        }
    }
    tasks['Package & Build Staging Docker Image'] = {
        packageServicesAndUploadToRepo(dockerImageTag, serviceList)
    }

    if (env.gitlabTargetBranch == env.STAGING_BRANCH) {
        tasks['Create Or Update Staging Env'] = {
            stage("Ensure Staging Environment is created") {
                stagingPublicIP = updateStagingEnv("create")
            }
        }
    }

    parallel tasks

    if (env.gitlabTargetBranch == env.STAGING_BRANCH) {
        stage("Deploy to Staging") {
            echo "${serviceList}"
            withCredentials([sshUserPrivateKey(
                credentialsId: 'staging-private-key',
                keyFileVariable: 'stagingSSHPrivateKeyPath',
                usernameVariable: 'stagingSSHUserName'
            )]) {
                docker.image(env.ANSIBLE_CENTOS7_IMAGE).inside("-v '/tmp/facts_cache:/tmp/facts_cache:rw'") {
                    dir('cicd/continous-integration/ansible') {
                        def currentDir = pwd()
                        sh """
                            ansible-playbook -i jenkins_localhost.ini \
                                utilities.yml --tags prepare-staging-key-inventory\
                                -e SSH_USER_NAME=${stagingSSHUserName} \
                                -e SSH_PRIVATE_KEY_PATH=${stagingSSHPrivateKeyPath} \
                                -e STAGING_PUBLIC_IP=${stagingPublicIP} \
                                -e CURRENT_JENKINS_LOCAL_DIR=${currentDir}

                            ansible-playbook\
                                -i staging_hosts.ini deploy_staging.yml\
                                -e '{ "SERVICE_LIST":${serviceList}}'\
                                -e WORKING_DIR=${env.STAGING_WORKING_DIR}\
                                -e CURRENT_JENKINS_LOCAL_DIR=${currentDir}\
                                -e IMAGE_REGISTRY_URL=${env.DOCKER_MAIN_PULL_REGISTRY}\
                                -e IMAGE_STAGING_REGISTRY_URL=${env.DOCKER_STAGING_PULL_REGISTRY}\
                                -e IMAGE_TYPE=${env.STAGING_IMAGE_TYPE}\
                                -e STACK_VERSION=${dockerImageTag}\
                                -e '{ "REFRESH_SERVICE_LIST_TIMEOUT":${
                            env.REFRESH_SERVICE_LIST_TIMEOUT
                        }}'
                        """
                    }
                }
            }
            env.STAGING_IP = "${stagingPublicIP}"
            env.UPDATE_STAGING_ENV_RESULT_STR = "<br/>:information_source: " +
                "Staging Environment is created at IP: " +
                "<b><a href='http://${stagingPublicIP}'>${stagingPublicIP}</a></b><br/><br/>"
        }

        stage("Perform Functional Test") {
            try {
                sh """
                        ./build.sh functional_test http://${stagingPublicIP}:8060
                    """
            } catch (err) {
                echo "${err}"
            } finally {
                def exists = fileExists 'newman'
                if (exists) {
                    junit 'newman/test-result-*.xml'
                }

                def unitTestResult = [
                    "passed" : env.UNIT_TEST_PASSED,
                    "failed" : env.UNIT_TEST_FAILED,
                    "skipped": env.UNIT_TEST_SKIPPED,
                    "total"  : env.UNIT_TEST_TOTAL
                ]

                def functionalTestResult = getFunctionalTestStatus(unitTestResult)
                def functionalTestResultString = "- Passed: <b>${functionalTestResult['passed']}</b> <br/>" +
                    "- Failed: <b>${functionalTestResult['failed']}</b> <br/>" +
                    "- Skipped: <b>${functionalTestResult['skipped']}</b> <br/>"
                def testResultComment = "<b>Functional Test Result:</b> <br/><br/>" +
                    "${functionalTestResultString}" +
                    "<i><a href='${env.BUILD_URL}testReport/'>Details Funcational Test Report...</a></i><br/><br/>"
                env.FUNCTIONAL_TEST_RESULT_STR = testResultComment
                if (functionalTestResult.failed > 0) {
                    error "Failed ${functionalTestResult.failed} functional tests"
                }
            }
        }
    }
}

def buildAcceptAndCloseMR() {
    stage("Clean staging server") {
        updateStagingEnv("destroy")
    }
}

def autoTest(){
    def tasks = [:]
    tasks['Unit Tests, Code Coverage And SonarQube Tests'] = {
        node("centos7-docker") {
            unitTestCodeCoverageAndSonarQubeScan("PUSH")
        }
    }
    tasks['Check Style Tests'] = {
        node("centos7-docker") {
            checkStyleTest("PUSH")
        }
    }
    parallel tasks
}

return [
    buildPushCommit      : this.&buildPushCommit,
    buildMergeRequest    : this.&buildMergeRequest,
    buildAcceptAndCloseMR: this.&buildAcceptAndCloseMR,
    autoTest             : this.&autoTest
]
