import {Component, Input, OnInit} from '@angular/core';
import {StationService} from "@app/modules/stations-management/service/station.service";
import {AUDIT_STATUS, AUDIT_TYPE, BACKUP_STATUS, CAT_ITEM, POSITION, STATUS} from '@app/shared/services/constants';
import {DataCommonService} from "@app/shared/services/data-common.service";
import {CommonUtils} from "@app/shared/services";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CommonParam} from "@app/core/app-common-param";

@Component({
  selector: 'station-detail',
  templateUrl: './station-detail.component.html',
  styleUrls: ['./station-detail.component.css']
})
export class StationMapDetailComponent implements OnInit {

  @Input() data;
  object: any;
  formGroup: FormGroup;
  statusList: any[] = [];
  ownerList: any[] = [];
  positions: any[] = [];
  auditTypeList: any[] = [];
  auditStatus: any[] = [];
  backupStatusList: any[] = [];
  action: string;
  deptList: any[] = [];
  mapComponent: any;
  constructor(
    private fb: FormBuilder,
    private stationService: StationService,
    private dataCommon: DataCommonService,
    private commonParam: CommonParam) {
    this.buildForm();
  }

  ngOnInit() {
    this.mapComponent = this.data.component;
    // list trang thai
    this.statusList = this.dataCommon.getDropDownList(STATUS);
    // list vu hoi
    this.backupStatusList = this.dataCommon.getDropDownList(BACKUP_STATUS);
    // list vi tri
    this.positions = this.dataCommon.getDropDownList(POSITION);
    // list phan loai kiem dinh
    this.auditTypeList = this.dataCommon.getDropDownList(AUDIT_TYPE);
    // list trang thai kiem dinh
    this.auditStatus = this.dataCommon.getDropDownList(AUDIT_STATUS);
    let id =this.data.id || this.data.data.station_id;
    this.stationService.findStationById(id).subscribe(res => {
      this.object = res.content;
      if (this.object) {
        this.object.constructionDate = CommonUtils.stringToDate(this.object.constructionDate, 'yyyy/MM/dd');
          this.statusList.forEach(item => {
            if (item.value === this.object.status) {
              this.object.status = item.label;
            }
          });
          this.backupStatusList.forEach(item => {
            if (item.value === this.object.backupStatus) {
              this.object.backupStatus = item.label;
            }
          });
          this.positions.forEach(item => {
            if (item.value === this.object.position) {
              this.object.position = item.label;
            }
          });
          this.auditTypeList.forEach(item => {
            if (item.value === this.object.auditType) {
              this.object.auditType = item.label;
            }
          });
          this.formGroup.reset(this.object);
      }
      console.log(this.object);
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  buildForm() {
    this.formGroup = this.fb.group({
      stationId: [null],
      stationCode: [''],
      deptId: [null],
      deptName: [''],
      locationId: [null],
      locationName: [''],
      houseOwnerName: [''],
      houseOwnerPhone: [''],
      address: [''],
      ownerName: [''],
      constructionDate: [''],
      status: [null],
      houseStationType: [null],
      stationType: [null],
      stationFeature: [null],
      backupStatus: [''],
      position: [null],
      length: [null],
      width: [null],
      height: [''],
      heightestBuilding: [''],
      longitude: [''],
      latitude: [''],
      auditType: [null],
      auditStatus: [null],
      note: [''],
      createTime: [null],
      rowStatus: [null]
    });
  }
  clickItem() {
    debugger;
    let item = {
      action: 'edit',
      data: this.data.data
    }
    if(this.data.id){
      item.data.station_id = this.data.id;
    }
    this.commonParam.showProperties('stationsEdit', item);
    this.commonParam.onChange(false);
  }
}
