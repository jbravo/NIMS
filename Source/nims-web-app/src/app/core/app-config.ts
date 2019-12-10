export const CONFIG: any = {
  API_PATH: {
    'test': '/uploadFile',
    /********************OAUTH SERVICE*****************/
    'oauthToken': '/',
    /********************File SYSTEM****************/
    'fileStorage': '/file',
    /********************Sys SYSTEM*****************/
    'sysCat': '/v1/sys-cats',
    'sysCatType': '/v1/sys-cat-types',
    'nation': '/v1/nations',
    'language': '/v1/languages',
    'sysProperty': '/v1/sys-propertys',
    'sysPropertyDetails': '/v1/sys-property-details',
    'workFollow': '/v1/work-follow',
    'wfMenuMapping': '/v1/wf-menu-mappings',
    'location': '/v1/locations',

    /********************EMPLOYEE SYSTEM*****************/
    'dataPicker': '/v1/data-pickers',
    'empType': '/v1/emp-types',
    'positionCareer': '/v1/position-careers',
    'labourContractType': '/v1/labour-contract-types',
    'allowanceType': '/v1/allowance-types',
    'empFile': '/v1/emp-files',

    'familyRelationship': '/v1/employee/family-relationships',
    'empBankAccount': '/v1/employee/emp-bank-account',

    'salaryProcess': '/v1/employee/salary-process',
    'employeeInfo': '/v1/employee/employee-infos',
    'workProcess': '/v1/employee/work-process',

    'otherInformation': '/v1/employee/employee-infos',
    'partyUnion': '/v1/party-unions',
    'militaryInformation': '/v1/employee/military-information',
    'languageDegree': '/v1/employee/language-degree',
    'empTradeUnion': '/v1/employee/emp-trade-unions',
    'documentReason': '/v1/document-reasons',

    'empRewardProcess': '/v1/employee/emp-reward-process',
    'empDiscipline': '/v1/employee/emp-disciplines',
    'empTypeProcess': '/v1/employee/emp-type-process',
    'empLanguaeDegree': '/v1/emp-language-degree',
    'empPoliticalDegree': '/v1/employee/emp-political-degree',
    /******************ORGANIZATION SYSTEM***************/
    'organization': '/v1/organizations',
    'org-plan': '/v1/org-plans',
    'orgDraff': '/v1/org-draffs',
    'org-duty': '/v1/org-duty',
    'orgSelector': '/v1/org-selectors',
    'lineOrg': '/v1/line-orgs',
    'majorCareer': '/v1/major-careers',
    'position': '/v1/positions',
    'positionDes': '/v1/position-descriptions',
    'organization-type': '/v1/organization-type',
    'documentType': '/v1/document-types',
    'document': '/v1/document',
    'document-attachment': '/v1/document-attachment',
    'infraStation': 'infraStations',
    'infraSleeve': 'infraSleeve',
    'weldingOdf': 'weldingOdf',
    'infraCables': 'infraCables',
    'infraPools': 'infraPools',
    'transmission': 'transmission',
    'weldSleeve': 'weldSleeve',
    'infraOdf': 'infraOdf',
    'infraPool': 'infraPool',
    'weldingODf': 'weldingODf',
    'map': 'map',
    'pillar': 'pillar',
    'infraCable': 'infraCable',
    'configMap': 'configMap',
    'infraPillar': 'infraPillar',

    /**************Cate type****************/
    'pillarType': 'pillarType',
    'sleeveType': 'sleeveType',
    'department': 'department',
    'catTenant': 'catTenants',
    'sysGridView': 'sysGridView',
  }
};


export const DEFAULT_MODAL_OPTIONS: any = {
  size: 'lg',
  backdrop: 'static'
};
export const PERMISSION_CODE: any = {
  // action tac dong
  'action.create': 'CREATE'
  , 'action.view': 'VIEW'
  , 'action.insert': 'INSERT'
  , 'action.update': 'UPDATE'
  , 'action.delete': 'DELETE'
  , 'action.import': 'IMPORT'
  , 'action.export': 'EXPORT'
  , 'action.approval': 'APPROVAL'
  , 'action.unDecide': 'UN_DECIDE'
  , 'action.unApproveAll': 'UN_APPROVE_ALL'
  , 'action.approveAll': 'APPROVE_ALL'
  , 'action.removeEmp': 'REMOVE_EMP'
  , 'action.addEmp': 'ADD_EMP'
  , 'action.unApprove': 'UN_APPROVE'
  , 'action.unLock': 'UN_LOCK'
  , 'action.lock': 'LOCK'
  , 'action.decide': 'DECIDE'
  , 'action.calculate': 'CALCULATE'
  , 'action.viewHistory': 'VIEW_HISTORY'
  , 'action.enable': 'ENABLE'
  , 'action.disable': 'DISABLE'
  , 'action.issueAgain': 'ISSUE_AGAIN'
  , 'action.issueChange': 'ISSUE_CHANGE'
  , 'action.quickImport': 'QUICK_IMPORT'
  , 'action.transfer': 'TRANSFER'
  , 'action.manage': 'MANAGE'
  , 'action.viewAll': 'VIEW_ALL'
  // resource tai nguyen he thong
  , 'resource.empType': 'EMP_TYPE'
  , 'resource.sysProperty': 'SYS_PROPERTY'
  , 'resource.allowanceType': 'ALLOWANCE_TYPE'
  , 'resource.labourContractType': 'LABOUR_CONTRACT_TYPE'
  , 'resource.positionCareer': 'POSITION_CAREER'
  , 'resource.organization': 'ORGANIZATION'
  , 'resource.nation': 'NATION'
  , 'resource.employee': 'EMPLOYEE'
  // Sontq
  , 'resource.empFile': 'EMP_FILE'
  , 'resource.militaryInformation': 'MILITARY_INFORMATION'
  , 'resource.languageInformation': 'LANGUAGE_INFORMATION'
  // Tanptn
  , 'resource.empTypeTan': 'EMP_TYPE_TAN'
  , 'resource.familyRelationship': 'FAMILY_RELATIONSHIP'
  // HiepNC
  , 'resource.empSalaryProcess': 'EMP_SALARY_PROCESS'
  , 'resource.otherInformation': 'OTHER_INFORMATION'
  , 'resource.employeeInfo': 'EMPLOYEE_INFO'
  , 'resource.partyUnion': 'PARTY_UNION'
  , 'resource.workProcess': 'WORK_PROCESS'
  // thaopv
  , 'resource.empRewardDiscipline': 'REWARD_DISCIPLINE'
  , 'resource.positionCategory': 'POSITION_CATEGORY'
  // tulv phan quyen chuc nang
  , 'resource.station': 'STATION' // quản lý nhà trạm
  , 'resource.odf': 'ODF' // quản lí odf
  , 'resource.pillar': 'PILLAR' // quản lí cột
  , 'resource.pool': 'POOL' // quản lí bể
  , 'resource.sleeve': 'SLEEVE' // quản lý măng xông
  , 'resource.cable.in.station': 'CABLES_IN_STATION' // quản lí cáp trong nhà trạm
  // action cua hàn nối măng xông , odf.
  , 'resource.create.weld': 'CREATE_WELD_CONNECTION'
  , 'resource.update.weld': 'UPDATE_WELD_CONNECTION'
  , 'resource.delete.weld': 'DELETE_WELD_CONNECTION'
  , 'resource.view.weld': 'VIEW_WELD_CONNECTION'


};

export const APP_CONSTANTS = {
  ACTION_TYPE: {
    ADD: 0,
    EDIT: 1,
    VIEW: 2
  },
  PAGE_DEFAULT: {
    ROWS: 10,
    PAGE_CHOOSE: 0,
    TOTAL_RECORDS: 0,
    FIRST: 0,
    SORT_FIELD: '',
    SORT_ORDER: 1,
    PAGE: 1,
  },
  ROWS_PER_PAGE_ITEMS: [
    {label: '5', value: 5},
    {label: '10', value: 10},
    {label: '15', value: 15},
    {label: '20', value: 20},
    {label: '25', value: 25},
    {label: '30', value: 30}]
} as any;
