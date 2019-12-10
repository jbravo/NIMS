export const TERRAINS = [
  {value: 0, label: 'common.plain'},
  {value: 1, label: 'common.midlands'},
  {value: 2, label: 'common.mountain'},
  {value: 3, label: 'common.river'},
  {value: 4, label: 'common.islands'},
  {value: 5, label: 'common.seaside'}
];

export const AUDIT_STATUS = [
  {value: 0, label: 'common.auditstatus.zero'},
  {value: 1, label: 'common.auditstatus.one'},
  {value: 2, label: 'common.auditstatus.two'},
  {value: 3, label: 'common.auditstatus.three'},
  {value: 4, label: 'common.auditstatus.four'},
  {value: 5, label: 'common.auditstatus.five'},
  {value: 6, label: 'common.auditstatus.six'},
  {value: 7, label: 'common.auditstatus.seven'},
  {value: 71, label: 'common.auditstatus.eight'},
  {value: 72, label: 'common.auditstatus.night'},
  {value: 8, label: 'common.auditstatus.ten'},
  {value: 81, label: 'common.auditstatus.eleven'},
  {value: 82, label: 'common.auditstatus.twelve'},
  {value: 9, label: 'common.auditstatus.thirteen'},
  {value: 10, label: 'common.auditstatus.fourteen'}
];

export const STATUS = [
  {value: 1, label: 'common.status.one'},
  {value: 2, label: 'common.status.two'},
  {value: 3, label: 'common.status.three'},
  {value: 4, label: 'common.status.four'},
  {value: 5, label: 'common.status.five'},
  {value: 6, label: 'common.status.six'}

];

export const BACKUP_STATUS = [
  {value: 0, label: 'common.backup.status.zero'},
  {value: 1, label: 'common.backup.status.one'}
];

export const POSITION = [
  {value: 1, label: 'common.position.one'},
  {value: 2, label: 'common.position.two'},
  {value: 3, label: 'common.position.three'}
];

export const AUDIT_TYPE = [
  {value: 1, label: 'common.audit.type.one'},
  {value: 2, label: 'common.audit.type.two'},
  {value: 3, label: 'common.audit.type.three'}
];

export const CAT_ITEM = {
  HOUSE_STATION_TYPE: 'HOUSE_STATION_TYPE',
  STATION_TYPE: 'STATION_TYPE',
  STATION_FEATURE: 'STATION_FEATURE',
  CAT_OWNER: 'CAT_OWNER',
  VENDOR: 'VENDOR'
};

export const STATUS_POOL = [
  {label: 'pool.status.zero', value: 0},
  {label: 'pool.status.one', value: 1}
];

export const COLS_TABLE_AUTOCOMPLETE_CONTROL = {
  STATION: [
    {
      id: 1,
      field: 'stationCode',
      header: 'common.label.stationCode',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'locationName',
      header: 'common.label.locationName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'deptName',
      header: 'common.label.deptName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  DEPARTMENT: [
    {
      id: 1,
      field: 'deptCode',
      header: 'common.label.deptCode',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'deptName',
      header: 'common.label.deptName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'pathName',
      header: 'common.label.pathName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  LOCATION: [
    {
      id: 1,
      field: 'locationCode',
      header: 'common.label.locationCode',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'locationName',
      header: 'common.label.locationName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'pathLocalName',
      header: 'common.label.pathLocalName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  LANECODE: [
    {
      id: 1,
      field: 'laneCode',
      header: 'pillar.laneCode',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'locationName',
      header: 'common.label.locationName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'deptName',
      header: 'common.label.deptName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  LANESLEEVE: [
    {
      id: 1,
      field: 'laneCode',
      header: 'pillar.laneCode',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'pathLocalName',
      header: 'common.label.locationName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'pathName',
      header: 'common.label.deptName',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  PILLAR: [
    {
      id: 1,
      field: 'pillarCode',
      header: 'pillar.pillarCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'pillarTypeCode',
      header: 'pillar.type.code',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
//     pathLocalName: "Việt Nam"
// pathName: "Tập đoàn Viettel"
    {
      id: 3,
      field: 'pathName',
      header: 'common.label.unit',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 4,
      field: 'pathLocalName',
      header: 'pillar.locationName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  POOL: [
    {
      id: 1,
      field: 'poolCode',
      header: 'pool.poolCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'poolTypeCode',
      header: 'pool.poolTypeCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'locationName',
      header: 'pool.locationName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 4,
      field: 'deptName',
      header: 'pool.deptName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ]
};

export const COLS_TABLE = {
  STATION: [
    {
      id: 1,
      field: 'stationCode',
      header: 'station.stationCode',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input',
      disabled: true
    },
    {
      id: 2,
      field: 'pathName',
      header: 'station.dept',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'deptName'
    },
    {
      id: 3,
      field: 'pathLocalName',
      header: 'station.location',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'locationName'
    },
    {
      id: 4,
      field: 'terrainName',
      header: 'station.terrainName',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'terrainName'
    },
    {
      id: 5,
      field: 'houseOwnerName',
      header: 'station.houseOwnerName',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 6,
      field: 'houseOwnerPhone',
      header: 'station.houseOwnerPhone',
      classHeader: '',
      classField: 'text-right',
      typeFilter: 'input',
      style: {'width': '15rem'}
    },
    {
      id: 7,
      field: 'address',
      header: 'station.address',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 8,
      field: 'ownerName',
      header: 'station.ownerName',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'ownerId'
    },
    {
      id: 9,
      field: 'constructionDate',
      header: 'station.constructionDate',
      classHeader: '',
      classField: 'text-center',
      typeFilter: 'constructionDate'
    },
    {
      id: 10,
      field: 'statusName',
      header: 'station.status',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'status'
    },
    {
      id: 11,
      field: 'houseStationTypeName',
      header: 'station.houseStationType',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'houseStationTypeName'
    },
    {
      id: 12,
      field: 'stationTypeName',
      header: 'station.stationType',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'stationTypeName'
    },
    {
      id: 13,
      field: 'stationFeatureName',
      header: 'station.stationFeature',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'stationFeatureName'
    },
    {
      id: 14,
      field: 'backupStatusName',
      header: 'station.backupStatus',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'backupStatus'
    },
    {
      id: 15,
      field: 'positionName',
      header: 'station.position',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'position'
    },
    {
      id: 16,
      field: 'length',
      header: 'station.length',
      classHeader: '',
      classField: 'text-right',
      typeFilter: 'input'
    },
    {
      id: 17,
      field: 'width',
      header: 'station.width',
      classHeader: '',
      classField: 'text-right',
      typeFilter: 'input'
    },
    {
      id: 18,
      field: 'height',
      header: 'station.height',
      classHeader: '',
      classField: 'text-right',
      filterMatchMode: 'contains',
      typeFilter: 'input'
    },
    {
      id: 19,
      field: 'heightestBuilding',
      header: 'station.heightestBuilding',
      classHeader: '',
      classField: 'text-right',
      typeFilter: 'input'
    },
    {
      id: 20,
      field: 'longitude',
      header: 'station.longitude',
      classHeader: '',
      classField: 'text-right',
      typeFilter: 'input'
    },
    {
      id: 21,
      field: 'latitude',
      header: 'station.latitude',
      classHeader: '',
      classField: 'text-right',
      typeFilter: 'input'
    },
    {
      id: 22,
      field: 'auditTypeName',
      header: 'station.auditType',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'auditType'
    },
    {
      id: 23,
      field: 'auditStatusName',
      header: 'station.auditStatus',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'auditStatusName'
    },
    {
      id: 24,
      field: 'auditReason',
      header: 'station.auditReason',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 25,
      field: 'fileCheck',
      header: 'station.file.check',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 26,
      field: 'fileListed',
      header: 'station.file.listed',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 27,
      field: 'note',
      header: 'station.note',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'input'
    }
  ],
  PILLAR: [
    {
      id: 1,
      field: 'laneCode',
      header: 'pillar.laneCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'laneCode'
    },
    {
      id: 2,
      field: 'pillarCode',
      header: 'pillar.pillarCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'pillarTypeCode',
      header: 'pillar.type.code',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'pillarTypeCode'
    },
    {
      id: 4,
      field: 'pathName',
      header: 'common.label.unit',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'deptName'
    },
    {
      id: 5,
      field: 'pathLocalName',
      header: 'pillar.locationName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'locationName'
    },
    {
      id: 6,
      field: 'ownerName',
      header: 'pillar.ownerName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'ownerName'
    },
    {
      id: 7,
      field: 'address',
      header: 'common.label.address',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 8,
      field: 'constructionDate',
      header: 'pillar.constructionDate',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'inputDate'
    },
    {
      id: 9,
      field: 'statusName',
      header: 'common.label.status',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'status'
    },
    {
      id: 10,
      field: 'longitude',
      header: 'common.longitude',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 11,
      field: 'latitude',
      header: 'common.latitude',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {id: 12, field: 'note', header: 'common.label.note', classHeader: 'text-center size-12', classField: 'text-left', typeFilter: 'input'}
  ],
  SLEEVE: [
    {
      id: 1, field: 'sleeveCode', header: 'infra.sleeves.code', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sSleeveCode'
    },
    {
      id: 2, field: 'sleeveTypeCode', header: 'infra.sleeves.type.code', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sSleeveTypeId'
    },
    {
      id: 3, field: 'pillarCode', header: 'infra.sleeves.contain.pillar', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sPillarCode'
    },
    {
      id: 4, field: 'poolCode', header: 'infra.sleeves.contain.pool', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sPoolCode'
    },
    {
      id: 5, field: 'laneCode', header: 'infra.sleeves.contain.lane', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sLaneName'
    },
    {
      id: 6, field: 'deptPath', header: 'common.label.unit', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sDeptPath'
    },
    {
      id: 7, field: 'location', header: 'infra.sleeves.locationName', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'input'
    },
    {
      id: 8, field: 'latitude', header: 'common.latitude', classHeader: 'text-center size-12',
      classField: 'text-right', typeFilter: 'input'
    },
    {
      id: 9, field: 'longitude', header: 'common.longitude', classHeader: 'text-center size-12',
      classField: 'text-right', typeFilter: 'input'
    },
    {
      id: 10, field: 'purposeName', header: 'common.label.purpose', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sPurpose'
    },
    {
      id: 11, field: 'note', header: 'infra.sleeves.cause.error', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'input'
    },
    {
      id: 12, field: 'installation', header: 'infra.sleeves.installation.date', classHeader: 'text-center size-12',
      classField: 'text-center', typeFilter: 'installationDate'
    },
    {
      id: 13, field: 'modifyDate', header: 'infra.sleeves.modify.date', classHeader: 'text-center size-12',
      classField: 'text-center', typeFilter: 'updateTime'
    },
    {
      id: 14, field: 'serial', header: 'common.label.serial', classHeader: 'text-center size-12',
      classField: 'text-right', typeFilter: 'sSerial'
    },
    {
      id: 15, field: 'ownerName', header: 'infra.sleeves.owner', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'ownerName'
    },
    {
      id: 16, field: 'vendorName', header: 'infra.sleeves.vendor', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'vendorName'
    },
    {
      id: 17, field: 'statusName', header: 'common.label.status', classHeader: 'text-center size-12',
      classField: 'text-left', typeFilter: 'sStatus'
    }
  ],
  WELDING_ODF: [
    {id: 1, field: 'odfCode', header: 'weldingOdf.sourceCode', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 2, field: 'couplerNo', header: 'weldingOdf.sourceCoupler', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 3, field: 'cableCode', header: 'weldingOdf.cableCode', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 4, field: 'lineNo', header: 'weldingOdf.lineNo', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 5, field: 'destOdfCode', header: 'weldingOdf.destCode', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {
      id: 6,
      field: 'destCouplerNo',
      header: 'weldingOdf.destCoupler',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12'
    },
    {id: 7, field: 'createUser', header: 'weldingOdf.employee', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 8, field: 'attenuation', header: 'weldingOdf.attenuation', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 9, field: 'createDate', header: 'weldingOdf.date', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 10, field: 'odfConnectType', header: 'weldingOdf.type', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 11, field: 'note', header: 'weldingOdf.note', classHeader: 'text-center size-12', classField: 'text-center size-12'}
  ],

  TYPES: [
    {label: 'welding.odf.weld', value: 'welding.odf.weld'},
    {label: 'welding.odf.joint', value: 'welding.odf.joint'}
  ],
  // WELD: [
  //   {id: 1, field: 'weldCode', header: 'weld.weldCode', classHeader: 'text-center size-12', classField: 'text-center size-12'},
  //   {
  //     id: 1,
  //     field: 'sourcecableCode',
  //     header: 'sourcecable.sourcecableCode',
  //     classHeader: 'text-center size-12',
  //     classField: 'text-center size-12'
  //   },
  //   {id: 1, field: 'sourceLine', header: 'sourceline.sourceLine', classHeader: 'text-center size-12', classField: 'text-center size-12'},
  //   {
  //     id: 1,
  //     field: 'destCableCode',
  //     header: 'destcable.destCableCode',
  //     classHeader: 'text-center size-12',
  //     classField: 'text-center size-12'
  //   },
  //   {id: 1, field: 'destLine', header: 'destline.destLine', classHeader: 'text-center size-12', classField: 'text-center size-12'},
  //   {id: 1, field: 'createUser', header: 'createuser.createUser', classHeader: 'text-center size-12', classField: 'text-center size-12'},
  //   {id: 1, field: 'attenuation', header: 'attenuation.attenuation', classHeader: 'text-center size-12', classField: 'text-center size-12'},
  // ],
  POOL: [
    {
      id: 1,
      field: 'poolCode',
      header: 'pool.poolCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'poolTypeCode',
      header: 'pool.poolTypeCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'poolTypeCode'
    },
    {
      id: 3,
      field: 'pathName',
      header: 'pool.deptName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'deptName'
    },
    {
      id: 4,
      field: 'pathLocationName',
      header: 'pool.locationName',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'locationName'
    },
    {
      id: 5,
      field: 'ownerName',
      header: 'pool.ownerId',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'ownerName'
    },
    {
      id: 6,
      field: 'deliveryDate',
      header: 'pool.deliveryDate',
      classHeader: 'text-center size-12',
      classField: 'text-center',
      typeFilter: 'inputDate'
    },
    {
      id: 7,
      field: 'acceptanceDate',
      header: 'pool.acceptanceDate',
      classHeader: 'text-center size-12',
      classField: 'text-center',
      typeFilter: 'inputDate'
    },
    {
      id: 8,
      field: 'note',
      header: 'pool.note',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 9,
      field: 'constructionDate',
      header: 'pool.constructionDate',
      classHeader: 'text-center size-12',
      classField: 'text-center',
      typeFilter: 'inputDate'
    },
    {
      id: 10,
      field: 'address',
      header: 'pool.address',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 11,
      field: 'longitude',
      header: 'pool.longitude',
      classHeader: 'text-center size-12',
      classField: 'text-right',
      typeFilter: 'longitude'
    },
    {
      id: 12,
      field: 'latitude',
      header: 'pool.latitude',
      classHeader: 'text-center size-12',
      classField: 'text-right',
      typeFilter: 'latitude'
    },
    {
      id: 13,
      field: 'statusName',
      header: 'pool.status',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'status'
    }
  ],
  ODF: [
    {
      id: 1,
      field: 'odfCode',
      header: 'odf.Code',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'stationCode',
      header: 'station.stationCode',
      classHeader: 'text-center size-12',
      classField: 'text-left',
      typeFilter: 'stationAutoSelect'
    },
    {
      id: 3,
      field: 'odfTypeCode',
      header: 'odf.odfTypeCode',
      classHeader: 'text-center size-12',
      classField: 'text-left size-12',
      typeFilter: 'autoSelect'
    },
    {
      id: 4,
      field: 'deptPath',
      header: 'odf.deptName',
      classHeader: 'text-center size-12',
      classField: 'text-left size-12',
      typeFilter: 'deptAutoSelect'
    },
    {
      id: 5,
      field: 'ownerName',
      header: 'odf.ownerCode',
      classHeader: 'text-center size-12',
      classField: 'text-left size-12',
      typeFilter: 'ownerDropdownSearch'
    },
    {
      id: 6,
      field: 'vendorName',
      header: 'odf.vendor',
      classHeader: 'text-center size-12',
      classField: 'text-left size-12',
      typeFilter: 'vendorDropdownSearch'
    },
    {
      id: 7,
      field: 'installationDate',
      header: 'odf.installationDate',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'dateTime'
    },
    {
      id: 8,
      field: 'note',
      header: 'odf.note',
      classHeader: 'text-center size-12',
      classField: 'text-left size-12',
      typeFilter: 'input'
    }
  ],
  CABLE: [
    {id: 1, field: 'cableCode', header: 'cable.cableCode', classHeader: 'size-12', classField: 'text-left', typeFilter: 'cableCodeTable'},
    {
      id: 2,
      field: 'stationCode',
      header: 'station.stationCode',
      classHeader: 'size-12',
      classField: 'text-left',
      typeFilter: 'stationCodeTable'
    },
    {id: 3, field: 'sourceCode', header: 'cable.ODFfist', classHeader: 'size-12', classField: 'text-left', typeFilter: 'sourceIdTable'},
    {id: 4, field: 'destCode', header: 'cable.ODFend', classHeader: 'size-12', classField: 'text-left', typeFilter: 'destIdTable'},
    {id: 5, field: 'cableTypeCode', header: 'cable.cableTypeCode', classHeader: 'size-12', classField: 'text-left', typeFilter: 'cableTypeIdTable'},
    {id: 6, field: 'status', header: 'common.label.status', classHeader: 'size-12', classField: 'text-left', typeFilter: 'status'},
    {id: 7, field: 'length', header: 'cable.cableLength', classHeader: 'size-12', classField: 'text-right', typeFilter: 'input'},
    {id: 8, field: 'deptName', header: 'common.label.unit', classHeader: 'size-12', classField: 'text-left', typeFilter: 'deptNameTable'},
    {
      id: 9,
      field: 'installationDate',
      header: 'cable.installationDate',
      classHeader: 'size-12',
      classField: 'text-center',
      typeFilter: 'installationDate'
    },
    {id: 10, field: 'note', header: 'common.label.note', classHeader: 'size-12', classField: 'text-left', typeFilter: 'input'}

  ],
  WELD: [
    {
      id: 1,
      field: 'sleeveCode',
      header: 'infra.sleeves.code',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'sourceCableCode',
      header: 'weld.sourceCableCode',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'sourceCableCode'
    },
    {
      id: 3,
      field: 'sourceLineNo',
      header: 'weld.sourceLineNo',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input-number'
    },
    {
      id: 4,
      field: 'destCableCode',
      header: 'weld.destCableCode',
      classHeader: '',
      classField: 'text-left',
      typeFilter: 'sourceCableCode'
    },
    {
      id: 5,
      field: 'destLineNo',
      header: 'weld.destLineNo',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input-number'
    },
    {
      id: 6,
      field: 'createUser',
      header: 'weld.createUser',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 7,
      field: 'attenuation',
      header: 'weld.attenuation',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    }
  ],
  // DungPH
  ODF_LANE: [
    {
      id: 1,
      field: 'odfCode',
      header: 'odf.Code',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'cableCode',
      header: 'cable.cableCode',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'laneCode',
      header: 'weld.laneCode',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    }
  ],
  WELDSlEEVE: [
    {id: 1, field: 'cableCode', header: 'odf', classHeader: 'text-center size-12', classField: 'text-left size-12'},
    {id: 2, field: 'quad', header: 'quad', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 3, field: 'yarn', header: 'yarns', classHeader: 'text-center size-12', classField: 'text-center size-12'}
  ],
  PILLAR_TYPE: [
    {id: 1, field: 'pillarTypeCode', header: 'pillar.type.code', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 2, field: 'note', header: 'common.label.note', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 3, field: 'height', header: 'pillar.type.height', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 4, field: 'createTime', header: 'pillar.type.createTime', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 5, field: 'updateTime', header: 'pillar.type.updateTime', classHeader: 'text-center size-12', classField: 'text-center size-12'}
  ],
  SLEEVE_TYPE: [
    {id: 1, field: 'sleeveTypeCode', header: 'sleeve.type.code', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 2, field: 'type', header: 'sleeve.type.type', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 4, field: 'capacity', header: 'sleeve.type.capacity', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 3, field: 'note', header: 'sleeve.type.note', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 5, field: 'createTime', header: 'sleeve.type.createTime', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 6, field: 'updateTime', header: 'sleeve.type.updateTime', classHeader: 'text-center size-12', classField: 'text-center size-12'}
  ],
  CAT_DEPARTMENT: [
    {id: 1, field: 'deptCode', header: 'cat.department.code', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 2, field: 'deptName', header: 'cat.department.name', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {id: 3, field: 'fullName', header: 'cat.department.fullName', classHeader: 'text-center size-12', classField: 'text-center size-12'},
    {
      id: 4,
      field: 'createTime',
      header: 'cat.department.createTime',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12'
    },
    {
      id: 5,
      field: 'updateTime',
      header: 'cat.department.updateTime',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12'
    }
  ],
  VIEW_LANECODE: [
    {
      id: 1,
      field: 'pillarCode',
      header: 'pillar.pillarCode',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 2,
      field: 'cableCode',
      header: 'cable.cableCode',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    },
    {
      id: 3,
      field: 'laneCode',
      header: 'pillar.laneCode',
      classHeader: 'text-center size-12',
      classField: 'text-center size-12',
      typeFilter: 'input'
    }
  ],
  VIEW_LANECODE_POOL: [
    {
      id: 1, field: 'poolCode', header: 'pool.poolCode', classHeader: 'text-center size-12',
      classField: 'text-left size-12', typeFilter: 'input'
    },
    {
      id: 2, field: 'cableCode', header: 'cable.cableCode', classHeader: 'text-center size-12',
      classField: 'text-left size-12', typeFilter: 'input'
    },
    {
      id: 3, field: 'laneCode', header: 'pillar.laneCode', classHeader: 'text-center size-12',
      classField: 'text-left size-12', typeFilter: 'input'
    }
  ]
};

export const SLEEVE_PURPOSE = [
  {value: 0, label: 'infra.sleeves.normal.sleeve'},
  {value: 1, label: 'infra.sleeves.trouble.sleeve'}
];

export const PILLAR_STATUS_CABLE = [
  {value: 0, label: 'pillar.cable.status.no'},
  {value: 1, label: 'pillar.cable.status.yes'}
];

export const SLEEVE_STATUS = [
  {value: 0, label: 'sleeves.status.non.using'},
  {value: 1, label: 'sleeves.status.using'}
];

export const WELDING_SLEEVE = {
  ONENORMAL: [{yarnSouce: '16', yarnDest: '17'}],
  ONEDIRECT: [{yarnSource: '10', yarnDest: '11'}]
};

export const CABLE_STATUS = [
  {value: 0, label: 'cable.status.zero'},
  {value: 1, label: 'cable.status.one'},
  {value: 2, label: 'cable.status.two'}
];

export const FROZENCOLS = [
  {value: 0, label: 'cable.status.zero'},
  {value: 1, label: 'cable.status.one'},
  {value: 2, label: 'cable.status.two'},
  {value: 2, label: 'cable.status.two'},
  {value: 2, label: 'cable.status.two'}];

export const PILLAR_STATUS = [
  {value: 0, label: 'pillar.status.two'},
  {value: 1, label: 'pillar.status.one'}
];

export const SLEEVE_TYPE_TYPE = [
  {value: 0, label: 'sleeve.type.type.chon'},
  {value: 1, label: 'sleeve.type.type.treo'}
];

export const CONFIG_TYPE = [
  {value: 0, label: 'map.config.common'},
  {value: 1, label: 'map.config.object'}
];

export const ZOOM_TYPE = [
  {value: 0, label: 'map.zoom.zero'},
  {value: 1, label: 'map.zoom.one'},
  {value: 2, label: 'map.zoom.two'},
  {value: 3, label: 'map.zoom.three'},
  {value: 4, label: 'map.zoom.four'},
  {value: 5, label: 'map.zoom.five'},
  {value: 6, label: 'map.zoom.six'},
  {value: 7, label: 'map.zoom.seven'},
  {value: 8, label: 'map.zoom.eight'},
  {value: 9, label: 'map.zoom.night'},
  {value: 10, label: 'map.zoom.ten'},
  {value: 11, label: 'map.zoom.eleven'},
  {value: 12, label: 'map.zoom.twelve'},
  {value: 13, label: 'map.zoom.thirteen'},
  {value: 14, label: 'map.zoom.fourteen'},
  {value: 15, label: 'map.zoom.fifteen'},
  {value: 16, label: 'map.zoom.sixteen'},
  {value: 17, label: 'map.zoom.seventeen'}
];

export const MAP_TYPE = [
  {value: 1, label: 'map.type.map'},
  {value: 2, label: 'map.type.satellite'},
  {value: 3, label: 'map.type.hybrid'},
  {value: 4, label: 'map.type.terrain'}
];

export const OBJECT_CHECKBOX = [
  {name: 'STATION', label: 'map.config.station'},
  {name: 'pipe', label: 'map.config.pipe'},
  {name: 'way', label: 'map.config.way'},
  {name: 'POOL', label: 'map.config.pool'},
  {name: 'PILLAR', label: 'map.config.pillar'},
  {name: 'OPTICAL_CABLE', label: 'map.config.cable'},
  {name: 'copperCable', label: 'map.config.copperCable'},
  {name: 'topWire', label: 'map.room.topWire.'},
  {name: 'cableCabinets', label: 'map.config.cableCabinets'},
  {name: 'box', label: 'map.config.box'},
  {name: 'opticalNode', label: 'map.config.opticalNode'},
  {name: 'amplification', label: 'map.config.amplification'},
  {name: 'tap', label: 'map.config.tap'},
  {name: 'coupler', label: 'map.config.coupler'},
  {name: 'spliter', label: 'map.config.spliter'},
  {name: 'electricity', label: 'map.config.electricity'},
  {name: 'powerInserter', label: 'map.config.powerInserter'},
  {name: 'coaxialCable', label: 'map.config.coaxialCable'},
  {name: 'eoc', label: 'map.config.eoc'},
  {name: 'engineRoom', label: 'map.config.engineRoom'},
  {name: 'opticalCableTelevision', label: 'map.config.opticalCableTelevision'},
  {name: 'broadband', label: 'map.config.broadband'},
  {name: 'cable-broadband', label: 'map.config.cable.broadband'}
];

export const CLASS_CHECKBOX = [
  {name: 'axis', label: 'map.class.axis'},
  {name: 'interdepartmental', label: 'map.class.interdepartmental'},
  {name: 'provincial', label: 'map.class.provincial'},
  {name: 'access', label: 'map.class.access'}
];

export const OBJECT_OUTDOOR_CHECKBOX = [
  {name: 'outdoor', label: 'map.odf.outdoor'},
  {name: 'sleeveOutdoor', label: 'map.config.sleeveOutdoor'},
  {name: 'cableOutdoor', label: 'map.config.cableOutdoor'}
];

export const VIEW_LAYER_CHECKBOX = [
  {name: 'station', label: 'layer.station'},
  {name: 'pillar', label: 'layer.pillar'},
  {name: 'pillar-sleeves', label: 'layer.pillar.sleeves'},
  {name: 'pool', label: 'layer.pool'},
  {name: 'pool-sleeves', label: 'layer.pool.sleeves'},
  {name: 'cableBox', label: 'layer.cableBox'},
  {name: 'cableCabinets', label: 'layer.cableCabinets'},
  {name: 'topWire', label: 'layer.topWire'},
  {name: 'sleeves-copperCable', label: 'layer.sleeves.copperCable'},
  {name: 'point', label: 'layer.point'},
  {name: 'pipe', label: 'layer.pipe'},
  {name: 'cable', label: 'layer.cable'},
  {name: 'copperCable', label: 'layer.copperCable'},
  {name: 'axis', label: 'layer.class.axis'},
  {name: 'interdepartmental', label: 'layer.class.interdepartmental'},
  {name: 'provincial', label: 'layer.class.provincial'},
  {name: 'access', label: 'layer.class.access'},
  {name: 'way', label: 'layer.way'},
  {name: 'way-name', label: 'layer.way.name'},
  {name: 'viba', label: 'layer.viba'},
  {name: 'cable-outdoor', label: 'layer.cable.outdoor'},
  {name: 'odf-outdoor', label: 'layer.odf.outdoor'},
  {name: 'sleeves-outdoor', label: 'layer.sleeves.outdoor'}
];

export const VALIDATE_STYLE = {
  VALID: 'ng-untouched ng-pristine ng-valid',
  VALID_DROPDOWN: '',
  INVALID: 'ng-invalid ng-dirty',
  INVALID_DROPDOWN: 'ng-invalid ng-dirty'
};

export const MAP = {
  STATIONS: 'stations',
  CABLES: 'cables',
  POOL: 'pool',
  PILLAR: 'pillar'
};

export const ATTACH_FILE_TYPE = [
  {value: 0, label: 'common.label.attachFileType.accreditation'},
  {value: 1, label: 'common.label.attachFileType.listing'}
];
