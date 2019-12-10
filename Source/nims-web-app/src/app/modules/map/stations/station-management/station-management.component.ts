import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CommonUtils, ValidationService} from "@app/shared/services";
import {MessageService} from "primeng/api";
import {PermissionService} from "@app/shared/services/permission.service";
import {DataCommonService} from "@app/shared/services/data-common.service";
import {TranslationService} from "angular-l10n";
import {StationService} from "@app/modules/stations-management/service/station.service";
import {AppComponent} from "@app/app.component";
import {ActivatedRoute} from "@angular/router";
import {AUDIT_TYPE, BACKUP_STATUS, CAT_ITEM, POSITION, STATUS} from "@app/shared/services/constants";
import {Dropdown} from "primeng/primeng";
import {CommonParamObserver} from "@app/core/app-common-param";

@Component({
  selector: 'station-management',
  templateUrl: './station-management.component.html',
  styleUrls: ['./station-management.component.css']
})
export class StationManagementComponent implements OnInit {

  @Input() data;
  formAdd: FormGroup;
  statusCheck = false;
  statusList: any[] = [];
  houseStationTypeList: any[] = [];
  stationTypeList: any[] = [];
  ownerList: any[] = [];
  stationFeatureList: any[] = [];
  positions: any[] = [];
  auditTypeList: any [] = [];
  backupStatusList: any [] = [];
  action: string;
  deptList: any[] = [];
  locationList: any[] = [];

  constructor(private app: AppComponent,
              private stationService: StationService,
              private translation: TranslationService,
              private dataCommon: DataCommonService,
              private permissionService: PermissionService,
              private messageService: MessageService,
              private activatedRoute: ActivatedRoute) {
    this.buildForm({});
  }

  ngOnInit() {
    // console.log('data',this.data.data.lat);
    console.log(this.data);
    if (this.data.action == 'edit' || this.data.action == 'view') {
      this.formAdd.value.stationId = this.data.data.station_id;
      this.stationService.findStationById(this.data.data.station_id).subscribe(res => {
        let object = res.content;
        if (object) {
          object.constructionDate = CommonUtils.stringToDate(object.constructionDate, 'yyyy/MM/dd');
          // lay danh sach don vi
          this.getDept("");
          // lay danh sach dia ban
          this.getLocation("");
          this.buildForm(object);
          this.formAdd.patchValue({ houseStationTypeId: +res.content.houseStationTypeId })
        }
      });
    }else{
      this.formAdd.controls['latitude'].setValue(this.data.data.lat);
      this.formAdd.controls['longitude'].setValue(this.data.data.lng);
    }
    this.action = this.data.action;
    // list lao nha tram
    this.getHouseStationTypeList();
    // list tinh chat nha tram
    this.getStationFeature();
    // list chu so huu
    this.getOwnerList();
    // list loai tram
    this.getStationTypeList();
    // list trang thai
    this.statusList = this.dataCommon.getDropDownList(STATUS);
    // list vi tri
    this.positions = this.dataCommon.getDropDownList(POSITION);
    // list phan loai kiem dinh
    this.auditTypeList = this.dataCommon.getDropDownList(AUDIT_TYPE);
    // list vu hoi
    this.backupStatusList = this.dataCommon.getDropDownList(BACKUP_STATUS);

  }
  onChange(data: any) {
    this.data = data;
    if (this.data.action == 'edit' || this.data.action == 'view') {
      this.formAdd.value.stationId = this.data.data.station_id;
      this.stationService.findStationById(this.data.data.station_id).subscribe(res => {
        let object = res.content;
        if (object) {
          object.constructionDate = CommonUtils.stringToDate(object.constructionDate, 'yyyy/MM/dd');
          // lay danh sach don vi
          this.getDept("");
          // lay danh sach dia ban
          this.getLocation("");
          this.buildForm(object);
          this.formAdd.patchValue({ houseStationTypeId: +res.content.houseStationTypeId })
        }
      });
    }else{
      this.buildForm({});
      this.formAdd.controls['latitude'].setValue(this.data.data.lat);
      this.formAdd.controls['longitude'].setValue(this.data.data.lng);
    }
  }

  test(event) {
    console.log(this.formAdd.value.houseStationTypeId)
  }

  // lay danh sach don vi
  getDept(event) {
    this.permissionService.filterDept(event.query).subscribe(s => {
      this.deptList = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.deptList.push({ label: s.content.listData[i].deptName, value: s.content.listData[i].deptId });
      }
    });

    // this.permissionService.findCatDeptByPost({ deptName: event.query }).subscribe(res => {
    //   this.deptList = [];
    //   this.deptList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
    //   for (let i = 0; i < res.content.listData.length; i++) {
    //     this.deptList.push({ label: res.content.listData[i].deptName, value: res.content.listData[i].deptId });
    //   }
    // });
  }

  // lay danh sach dia ban
  getLocation(event) {
    this.permissionService.filterLocation(event.query).subscribe(s => {
      this.locationList = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.locationList.push({ label: s.content.listData[i].locationName, value: s.content.listData[i].locationId });
      }
    });

    // this.permissionService.findCatLocation({ locationName: event.query }).subscribe(res => {
    //   this.locationList = [];
    //   this.locationList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
    //   for (let i = 0; i < res.content.listData.length; i++) {
    //     this.locationList.push({ label: res.content.listData[i].locationName, value: res.content.listData[i].locationId });
    //   }
    // });
  }

  // lay danh sach loai nha tram
  getHouseStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.HOUSE_STATION_TYPE).subscribe(res => {
      this.houseStationTypeList = [];
      // truong hop them moi
      this.houseStationTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.houseStationTypeList.push({ label: res[i].itemName, value: +res[i].itemId });
      }

      console.log(this.houseStationTypeList);
    });

    console.log(this.houseStationTypeList);
  }

  // lay list tinh chat nha tram
  getStationFeature() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_FEATURE).subscribe(res => {
      this.stationFeatureList = [];
      // truong hop them moi
      this.stationFeatureList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.stationFeatureList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
    });
  }

  // lay list loai tram
  getStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_TYPE).subscribe(res => {
      this.stationTypeList = [];
      // truong hop them moi
      this.stationTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.stationTypeList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
    });
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      // truong hop them moi
      this.ownerList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
    });
  }

  // lay gia tri cua dropdown roi set vao form
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formAdd.controls[element].setValue(event.value.value);
    } else {
      this.formAdd.controls[element].setValue('');
    }
  }

  setAuditStatus(event, element: string) {
    this.setSelectedValue(event, element);
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      stationId: [null],
      stationCode: ['', Validators.compose([Validators.required, Validators.maxLength(30), Validators.pattern("^[a-zA-Z0-9_]+$"), CommonUtils.customValidateReturnLabel('station.stationCode')])],
      deptId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.dept')])],
      locationId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.location')])],
      houseOwnerName: ['', Validators.compose([Validators.maxLength(200)])],
      houseOwnerPhone: ['', Validators.compose([Validators.required, Validators.maxLength(20), ValidationService.phone, CommonUtils.customValidateReturnLabel('station.houseOwnerPhone')])],
      address: ['', Validators.maxLength(500)],
      ownerId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.owner')])],
      constructionDate: [''],
      status: [null],
      houseStationTypeId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.houseStationType')])],
      // stationTypeId: ['', Validators.required],
      stationTypeId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.stationType')])],
      stationFeatureId: [null, Validators.required],
      backupStatus: [''],
      position: [null],
      length: [null, ValidationService.positiveNumber],
      width: [null, ValidationService.positiveNumber],
      height: ['', ValidationService.positiveNumber],
      heightestBuilding: ['', ValidationService.positiveNumber],
      longitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.longitude')])],
      latitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.latitude')])],
      auditType: [null],
      auditStatus: [null],
      // Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.auditStatus')])
      note: ['', Validators.maxLength(500)],
      createTime: [null],
      rowStatus: [null],
    });
  }

  get f() {
    return this.formAdd.controls;
  }
  // validate form va show message
  getFormValidationErrors(success: () => void) {
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'station')) {
      success();
    }
  }

  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    this.getFormValidationErrors(() => {
      this.formAdd.value.deptId = this.formAdd.value.deptId && this.formAdd.value.deptId.value ? this.formAdd.value.deptId.value : null;
      this.formAdd.value.locationId = this.formAdd.value.locationId && this.formAdd.value.locationId.value ? this.formAdd.value.locationId.value : null;
      this.stationService.saveStation(this.formAdd.value).subscribe(res => {
        if (res.status == '200') {
          this.app.messageProcess(res.status, res.content);
        }
        if (status == '404' || status == '500' || status == '400') {
          this.app.messageProcess(res.status, res.content);
        }
      }, error => {
        console.log(error);
      });
    })
  }

  checkWarning(e: FormControl) {
    if (this.statusCheck) {
      if (!e.valid) {
        return true;
      }
    }
    return false;
  }
}
