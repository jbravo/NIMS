import groovy.json.JsonOutput
import groovy.util.XmlSlurper


@SuppressWarnings("GrMethodMayBeStatic")
@NonCPS
def parseXml(xmlString) {
    def xmlParser = new XmlSlurper()
    xmlParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
    xmlParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    return xmlParser.parseText(xmlString)
}

@SuppressWarnings("GrMethodMayBeStatic")
@NonCPS
def jsonParse(def jsonString) {
    new groovy.json.JsonSlurperClassic().parseText(jsonString)
}

def toJSONString(data) {
    return JsonOutput.toJson(data)
}

def getServiceList(pomXMLStr, notServiceModuleList) {
    def serviceList = []
    def pomXml = parseXml(pomXMLStr)
    pomXml.modules[0].module.each {
        if (checkModuleIsService(it.text(), notServiceModuleList))
            serviceList.add(it.text())
    }
    return serviceList
}

def checkModuleIsService(String moduleName,notServiceModuleList) {
    isService = true
    for (notServiceModule in notServiceModuleList) {
        echo "${notServiceModule}"
        if (moduleName == notServiceModule) {
            isService = false
        }
    }
    echo "is service: ${isService}"
    return isService
}

//TODO: Remove harded-code by convert config to env file and put it in config file
/**
 * Checkout source code from gitlab repo to current working directory
 * @param checkoutType checkout type, PUSH for push commit build, and MERGE for merge request build
 * @return updated source code to repository
 */
def checkoutSourceCode(checkoutType) {
    if (checkoutType == "PUSH") {
        checkout changelog: true, poll: true, scm: [
            $class                           : 'GitSCM',
            branches                         : [[name: "${env.gitlabAfter}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions                       : [[$class: 'UserIdentity',
                                                 email : "${env.GitSCM_UserIdentity_Email}", name: "${env.GitSCM_UserIdentity_Name}"],
                                                [$class: 'CleanBeforeCheckout']],
            submoduleCfg                     : [],
            userRemoteConfigs                : [[credentialsId: "${env.GitSCM_CredentialsId}",
                                                 name         : 'origin',
                                                 url          : "${env.gitlabSourceRepoHomepage}"]]
        ]
    } else if (checkoutType == "MERGE") {
        checkout changelog: true, poll: true, scm: [
            $class                           : 'GitSCM',
            branches                         : [[name: "origin/${env.gitlabSourceBranch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions                       : [[$class : 'PreBuildMerge',
                                                 options: [
                                                     fastForwardMode: 'FF',
                                                     mergeRemote    : 'origin',
                                                     mergeStrategy  : 'RESOLVE',
                                                     mergeTarget    : "${env.gitlabTargetBranch}"
                                                 ]],
                                                [$class: 'UserIdentity',
                                                    email : "${env.GitSCM_UserIdentity_Email}", name: "${env.GitSCM_UserIdentity_Name}"],
                                                [$class: 'CleanBeforeCheckout']],
            submoduleCfg                     : [],
            userRemoteConfigs                : [[credentialsId: "${env.GitSCM_CredentialsId}",
                                                 name         : 'origin',
                                                 url          : "${env.gitlabSourceRepoHomepage}"]]
        ]
    }
}

def getProjectMember(notifyMemberLevel) {
    def project_members = []
    def project_member_notify = []
    withCredentials([string(credentialsId: "${env.GITLAB_TOKEN_CREDENTIALS}", variable: 'gitlab_api_token')]) {
        def currentPage = 1
        haveNextPage = true
        while (haveNextPage) {
            def response = httpRequest([
                acceptType   : 'APPLICATION_JSON',
                httpMode     : 'GET',
                contentType  : 'APPLICATION_JSON',
                customHeaders: [[name: 'Private-Token', value: gitlab_api_token]],
                url          : "${env.GITLAB_PROJECT_API_URL}/members?per_page=100&page=${currentPage}"
            ])

            def project_members_resp = jenkinsfile_utils.jsonParse(response.content)
            project_members.addAll(project_members_resp)
            if (project_members_resp.size() == 0) {
                haveNextPage = false
            } else {
                currentPage += 1
            }
        }

    }
    for (member in project_members) {
        if (member['access_level'].toInteger() >= notifyMemberLevel) {
            if (!project_member_notify.contains(member['username'])) {
                project_member_notify.add(member['username'])
            }
        }
    }
    return project_member_notify
}

return [
    parseXml                           : this.&parseXml,
    jsonParse                          : this.&jsonParse,
    toJSONString                       : this.&toJSONString,
    getServiceList                     : this.&getServiceList,
    checkoutSourceCode                 : this.&checkoutSourceCode,
    getProjectMember                   : this.&getProjectMember
]
