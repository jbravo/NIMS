import {AfterViewInit, Component, ElementRef, HostListener, Input, OnDestroy, OnInit, Type, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CommonUtils} from '@app/shared/services/common-utils.service';
import {ValidationService} from '@app/shared/services';
import {AppComponent} from '@app/app.component';
import {MessageService} from 'primeng/api';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {TranslationService} from 'angular-l10n';
import {DataCommonService} from '@app/shared/services/data-common.service';
import {AUDIT_STATUS, AUDIT_TYPE, BACKUP_STATUS, CAT_ITEM, POSITION, STATUS} from '@app/shared/services/constants';
import {PermissionService} from '@app/shared/services/permission.service';
import {ActivatedRoute} from '@angular/router';
import {TabLayoutComponent, TabLayoutService} from '@app/layouts/tab-layout';
import {Subscription} from 'rxjs';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {NgxSpinnerService} from 'ngx-spinner';
import {AutocompleteSearchDepartmentModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {AutocompleteSearchLocationModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import {AutocompleteSearchControlComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import {CommonParam} from '@app/core/app-common-param';
import {MapInfraStationService} from '@app/core/services/map/map-infraStation.service';

@Component({
  selector: 'station-save',
  templateUrl: './station-save.component.html',
  styleUrls: ['./station-save.component.css']
})
export class StationSaveComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('autoCompleteDept') autoCompleteDept: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteLocation') autoCompleteLocation: AutocompleteSearchControlComponent;
  @Input() data;

  formAdd: FormGroup;
  statusCheck = false;
  statusList: any[] = [];
  houseStationTypeList: any[] = [];
  stationTypeList: any[] = [];
  ownerList: any[] = [];
  stationFeatureList: any[] = [];
  positions: any[] = [];
  auditTypeList: any[] = [];
  auditStatusList: any[] = [];
  backupStatusList: any[] = [];
  private contentHasChangedSub: Subscription;
  private mapValueObjSub: Subscription;
  dataRequest = {
    stationId: undefined,
    stationCode: undefined,
    deptId: undefined,
    locationId: undefined,
    houseOwnerName: undefined,
    houseOwnerPhone: undefined,
    address: undefined,
    ownerId: undefined,
    constructionDate: undefined,
    status: undefined,
    houseStationTypeId: undefined,
    stationTypeId: undefined,
    stationFeatureId: undefined,
    backupStatus: undefined,
    position: undefined,
    length: undefined,
    width: undefined,
    height: undefined,
    heightestBuilding: undefined,
    longitude: undefined,
    latitude: undefined,
    auditType: undefined,
    auditStatus: undefined,
    note: undefined,
    createTime: undefined,
    rowStatus: undefined
  };
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  isTabChanged: string;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLocation = this.app.translation.translate('common.dialog.header.location');
  private _genKey = '_';
  action: string;
  tabId: number;
  dataGeo;
  validateFloat: RegExp = /^[0-9]+(\.[0-9]+)?$/;
  geometryRegex: RegExp = /^[0-9-]+\.[0-9]{5}$/;
  isEditStation = false;
  errorLongitude = false;
  errorLatitude = false;

  constructor(
    private fb: FormBuilder,
    private app: AppComponent,
    private stationService: StationService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private tabLayoutComponent: TabLayoutComponent,
    private commonParam: CommonParam,
    private mapInfraStationService: MapInfraStationService,
    private elementRef: ElementRef
  ) {
    this.buildForm();
    this.mapValueObjSub = this.eventBusService.coordinatesChange.subscribe(val => {
      if (val && val['value']) {
        this.formAdd.patchValue({
          latitude: val['value'].lat,
          longitude: val['value'].lng
        });
      }
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  get formControls() {
    return this.formAdd.controls;
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.keyCode === 13) {
      this.eventBusService.dataChange.subscribe(val => {
        console.log(val);
        if (val) {
          if (val.data.component === StationSaveComponent.name && (!this.tabId || (val.data.tabId && val.data.tabId === this.tabId))) {
            // if (val.data.key === StationSaveComponent.name) {
            this.onSubmit();
          }
        }
      }).unsubscribe();
    }
  }

  ngOnInit() {
    this.dataGeo = {
      stationId: null,
      stationCode: null,
      address: null,
      action: null,
      lngLat: null,
      deptId: null,
      locationId: null,
    };
    if (this.data) {
      this.stationService.action = this.data.action;
      this.stationService.id = this.data.data.station_id;
    }
    this.formAdd.patchValue({constructionDate: new Date()});
    this.dataGeo.action = this.stationService.action;
    this.tabId = this.stationService.id;
    this.action = this.stationService.action;
    if (this.action === 'edit') {
      this.isEditStation = true;
      this.formAdd.value.stationId = this.stationService.id;
      this.stationService.findStationById(this.stationService.id).subscribe(res => {
        if (res.content) {
          res.content.constructionDate = CommonUtils.stringToDate(res.content.constructionDate, 'yyyy/MM/dd');
          this.getListCombobox();
          res.content.longitude = CommonUtils.cutLongLat(res.content.longitude);
          res.content.latitude = CommonUtils.cutLongLat(res.content.latitude);
          res.content.length = res.content.length ? CommonUtils.cutLongLat(res.content.length) : res.content.length;
          res.content.width = res.content.width ? CommonUtils.cutLongLat(res.content.width) : res.content.width;
          res.content.height = res.content.height ? CommonUtils.cutLongLat(res.content.height) : res.content.height;
          res.content.heightestBuilding = res.content.heightestBuilding ? CommonUtils.cutLongLat(res.content.heightestBuilding) : res.content.heightestBuilding;
          this.formAdd.reset(res.content);
          this.formAdd.patchValue({houseStationTypeId: +res.content.houseStationTypeId});

          this.permissionService.findDepartmentById(res.content.deptId).subscribe(dept => {
            this.formAdd.patchValue({deptId: dept});
            this.autoCompleteDept.displayField = {pathName: dept.pathName};
            this.autoCompleteDept.displayFieldTooltip = dept.pathName;
            this.eventBusService.emit({
              type: 'stationSave',
              deptDataObj: dept
            });
          }, error => {
          }, () => {
            this.permissionService.findCatLocationById(res.content.locationId).subscribe(location => {
              this.formAdd.patchValue({locationId: location});
              this.autoCompleteLocation.displayField = {pathLocalName: location.pathLocalName};
              this.autoCompleteLocation.displayFieldTooltip = location.pathLocalName;
              this.eventBusService.emit({
                type: 'stationSave',
                locationDataObj: location
              });
              this.isEditStation = false;
            });
          });

        }
      });
    } else {
      if (this.data) {
        this.formAdd.controls['latitude'].setValue(this.data.data.lat);
        this.formAdd.controls['longitude'].setValue(this.data.data.lng);
      }
      this.getListCombobox();
    }
  }

  getListCombobox() {
    // list lao nha tram
    this.getHouseStationTypeList();
    // list chu so huu
    this.getOwnerList();
    // list tinh chat nha tram
    this.getStationFeature();
    // list loai tram
    this.getStationTypeList();
    // list trang thai
    this.statusList = this.dataCommon.getDropDownList(STATUS);
    // list vu hoi
    this.backupStatusList = this.dataCommon.getDropDownList(BACKUP_STATUS);
    // list vi tri
    this.positions = this.dataCommon.getDropDownList(POSITION);
    // list phan loai kiem dinh
    this.auditTypeList = this.dataCommon.getDropDownList(AUDIT_TYPE);
    // list trang thai kiem dinh
    this.auditStatusList = this.dataCommon.getDropDownList(AUDIT_STATUS);
  }

  ngAfterViewInit(): void {
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      if (!this.isEditStation) {
        this.isTabChanged = 'isTabChanged';
        const action = this.action ? this._genKey + this.action : '';
        const tabId = this.tabId ? this._genKey + this.tabId : '';
        const key = StationSaveComponent.name + action + tabId;
        this.tabLayoutService.isTabContentHasChanged({component: StationSaveComponent.name, key});
      }
    });
  }

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
    if (this.mapValueObjSub) {
      this.mapValueObjSub.unsubscribe();
    }
  }

  buildForm() {
    this.formAdd = this.fb.group({
      stationId: [null],
      stationCode: [null,
        Validators.compose([Validators.required, Validators.maxLength(30),
          CommonUtils.customValidateReturnLabel('station.stationCode')])
      ],
      deptId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.dept')])],
      locationId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.location')])],
      terrainName: [null],
      houseOwnerName: [null, Validators.compose([Validators.maxLength(200)])],
      houseOwnerPhone: [null,
        Validators.compose([Validators.required, Validators.maxLength(20),
          ValidationService.phone, CommonUtils.customValidateReturnLabel('station.houseOwnerPhone')])
      ],
      address: [null, Validators.maxLength(500)],
      ownerId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('requiredSelect//station.owner')])],
      ownerName: [null],
      constructionDate: [null],
      status: [null],
      statusName: [null],
      houseStationTypeId: [null],
      houseStationTypeName: [null],
      stationTypeId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('requiredSelect//station.stationType')])],
      stationTypeName: [null],
      stationFeatureId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('requiredSelect//station.stationFeature')])],
      stationFeatureName: [null],
      backupStatus: [null],
      backupStatusName: [null],
      position: [null],
      positionName: [null],
      length: [null, ValidationService.positiveNumber],
      width: [null, ValidationService.positiveNumber],
      height: [null, ValidationService.positiveNumber],
      heightestBuilding: [null, ValidationService.positiveNumber],
      longitude: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.longitude')])],
      latitude: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.latitude')])],
      auditType: [null],
      auditTypeName: [null],
      auditStatus: [null],
      auditStatusName: [null],
      note: [null, Validators.maxLength(500)],
      createTime: [null],
      rowStatus: [null]
    });
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formAdd.controls['constructionDate'].setValue(null);
      }
    }
  }

  onClearDatePicker() {
    this.formAdd.controls['constructionDate'].setValue(null);
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    let _success = false;
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'station', 'stationSave')) {
      _success = true;
    }
    if (this.formAdd.value.stationCode != null) {
      const re = new RegExp('^[a-zA-Z0-9-]+$');
      if (re.test(this.formAdd.value.stationCode) === false) {
        _success = false;
        this.app.formMessage(
          'error',
          'messages.error.pattern.custom',
          undefined,
          {field: this.translation.translate('station.stationCode')}, 'stationSave'
        );
      }
    }
    if (this.formAdd.value.longitude != null && this.formAdd.value.longitude !== '') {
      const rs = this.isLongitude(this.formAdd.value.longitude);
      if (rs === 4) {
        _success = false;
        this.app.formMessage(
          'error',
          'station.messages.geometry.regex.longitude',
          undefined,
          {field: this.translation.translate('station.longitude')}, 'stationSave'
        );
        this.errorLongitude = true;
      } else if (rs === 2) {
        _success = false;
        this.app.formMessage(
          'error',
          'station.messages.geometry.regex.longitude.over',
          undefined,
          {field: this.translation.translate('station.longitude')}, 'stationSave'
        );
        this.errorLongitude = true;
      } else if (rs === 3) {
        _success = false;
        this.app.formMessage(
          'error',
          'station.messages.geometry.regex.longitude.require5',
          undefined,
          {field: this.translation.translate('station.longitude')}, 'stationSave'
        );
        this.errorLongitude = true;
      } else {
        this.errorLongitude = false;
      }
    } else {
      this.errorLongitude = false;
    }
    if (this.formAdd.value.latitude != null && this.formAdd.value.latitude !== '') {
      const rs = this.isLatitude(this.formAdd.value.latitude);
      if (rs === 4) {
        _success = false;
        this.app.formMessage(
          'error',
          'station.messages.geometry.regex.latitude',
          undefined,
          {field: this.translation.translate('station.latitude')}, 'stationSave'
        );
        this.errorLatitude = true;
      } else if (rs === 2) {
        _success = false;
        this.app.formMessage(
          'error',
          'station.messages.geometry.regex.latitude.over',
          undefined,
          {field: this.translation.translate('station.latitude')}, 'stationSave'
        );
        this.errorLatitude = true;
      } else if (rs === 3) {
        _success = false;
        this.app.formMessage(
          'error',
          'station.messages.geometry.regex.latitude.require5',
          undefined,
          {field: this.translation.translate('station.latitude')}, 'stationSave'
        );
        this.errorLatitude = true;
      } else {
        this.errorLatitude = false;
      }
    } else {
      this.errorLatitude = false;
    }
    // if (this.formAdd.value.houseOwnerPhone != null) {
    //   const re = new RegExp('((09|03|07|08|05)+([0-9]{8})\\b)');
    //   if (re.test(this.formAdd.value.houseOwnerPhone) === false) {
    //     _success = false;
    //     this.app.formMessage(
    //       'error',
    //       'station.messages.house.owner.phone.error',
    //       undefined,
    //       undefined
    //     );
    //   }
    // }
    if (_success) {
      success();
    }
  }

  //tulv sua tach rieng message
  isLatitude(lat): number {
    if (isFinite(lat) && Math.abs(lat) > 90 && !this.geometryRegex.test(lat)) {
      return 4;
    } else if (isFinite(lat) && Math.abs(lat) > 90) {
      return 2;
    } else if (!this.geometryRegex.test(lat)) {
      return 3;
    } else {
      return 1;
    }
  }


  isLongitude(lng) {
    if (isFinite(lng) && Math.abs(lng) > 180 && !this.geometryRegex.test(lng)) {
      return 4;
    } else if (isFinite(lng) && Math.abs(lng) > 180) {
      return 2;
    } else if (!this.geometryRegex.test(lng)) {
      return 3;
    } else {
      return 1;
    }
  }

  onChangeRowSelectDept(event: any) {
    this.eventBusService.emit({
      type: 'stationSave',
      deptDataObj: event
    });
  }

  onClearRowSelect() {
    this.formAdd.controls['deptId'].setValue(null);
    this.formAdd.controls['locationId'].setValue(null);
    this.eventBusService.emit({
      type: 'stationSave',
      deptDataObj: null
    });
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      this.ownerList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  // lay danh sach loai nha tram
  getHouseStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.HOUSE_STATION_TYPE).subscribe(res => {
      this.houseStationTypeList = [];
      this.houseStationTypeList.push({label: this.translation.translate('common.label.cboSelect'), valuonClosedTabe: null});
      for (let i = 0; i < res.length; i++) {
        this.houseStationTypeList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  // lay list loai tram
  getStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_TYPE).subscribe(res => {
      this.stationTypeList = [];
      // truong hop them moi
      this.stationTypeList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.stationTypeList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  // lay list tinh chat nha tram
  getStationFeature() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_FEATURE).subscribe(res => {
      this.stationFeatureList = [];
      // truong hop them moi
      this.stationFeatureList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.stationFeatureList.push({label: res[i].itemName, value: +res[i].itemId});
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

  convertDataRequest() {
    this.dataRequest.stationId = this.formAdd.value.stationId ? this.formAdd.value.stationId : undefined;
    this.dataRequest.stationCode = this.formAdd.value.stationCode ? this.formAdd.value.stationCode : undefined;
    this.dataRequest.deptId = this.formAdd.value.deptId && this.formAdd.value.deptId.deptId ? this.formAdd.value.deptId.deptId : undefined;
    this.dataRequest.locationId = this.formAdd.value.locationId && this.formAdd.value.locationId.locationId ? this.formAdd.value.locationId.locationId : null;
    this.dataRequest.houseOwnerName = this.formAdd.value.houseOwnerName ? this.formAdd.value.houseOwnerName : undefined;
    this.dataRequest.houseOwnerPhone = this.formAdd.value.houseOwnerPhone ? this.formAdd.value.houseOwnerPhone : undefined;
    this.dataRequest.address = this.formAdd.value.address ? this.formAdd.value.address : undefined;
    this.dataRequest.ownerId = this.formAdd.value.ownerId ? this.formAdd.value.ownerId : undefined;
    this.dataRequest.constructionDate = this.formAdd.value.constructionDate ? this.formAdd.value.constructionDate : undefined;
    this.dataRequest.status = this.formAdd.value.status ? this.formAdd.value.status : undefined;
    this.dataRequest.houseStationTypeId = this.formAdd.value.houseStationTypeId ? this.formAdd.value.houseStationTypeId : undefined;
    this.dataRequest.stationTypeId = this.formAdd.value.stationTypeId ? this.formAdd.value.stationTypeId : undefined;
    this.dataRequest.stationFeatureId = this.formAdd.value.stationFeatureId ? this.formAdd.value.stationFeatureId : undefined;
    this.dataRequest.backupStatus = this.formAdd.value.backupStatus === 0 || this.formAdd.value.backupStatus ? this.formAdd.value.backupStatus : undefined;
    this.dataRequest.position = this.formAdd.value.position ? this.formAdd.value.position : undefined;
    this.dataRequest.length = this.formAdd.value.length ? this.formAdd.value.length : undefined;
    this.dataRequest.width = this.formAdd.value.width ? this.formAdd.value.width : undefined;
    this.dataRequest.height = this.formAdd.value.height ? this.formAdd.value.height : undefined;
    this.dataRequest.heightestBuilding = this.formAdd.value.heightestBuilding ? this.formAdd.value.heightestBuilding : undefined;
    // cat longitude
    this.formAdd.value.longitude = CommonUtils.cutLongLat(this.formAdd.value.longitude);
    this.dataRequest.longitude = this.formAdd.value.longitude ? this.formAdd.value.longitude : undefined;
    // cat latitude
    this.formAdd.value.latitude = CommonUtils.cutLongLat(this.formAdd.value.latitude);
    this.dataRequest.latitude = this.formAdd.value.latitude ? this.formAdd.value.latitude : undefined;
    this.dataRequest.auditType = this.formAdd.value.auditType ? this.formAdd.value.auditType : undefined;
    this.dataRequest.auditStatus = this.formAdd.value.auditStatus === 0 || this.formAdd.value.auditStatus ? this.formAdd.value.auditStatus : undefined;
    this.dataRequest.note = this.formAdd.value.note ? this.formAdd.value.note : undefined;
    this.dataRequest.createTime = this.formAdd.value.createTime ? this.formAdd.value.createTime : undefined;
    this.dataRequest.rowStatus = this.formAdd.value.rowStatus ? this.formAdd.value.rowStatus : undefined;
  }

  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    // this.spinner.show();
    this.getFormValidationErrors(() => {
      this.convertDataRequest();
      this.stationService.saveStation(this.dataRequest).subscribe(res => {
        // this.spinner.hide();
        if (res.status === '200') {
          this.messageService.add({
            key: 'notification',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.action === 'edit' ? this.translation.translate('station.update.success') : this.translation.translate('station.add.success')
          });
          setTimeout(() => {
            this.onClosed();
          }, 2000);
        }
        if (res.status === '226') {
          this.messageService.add({
            key: 'notification',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.duplicate.code')
          });
        }
        if (res.status === '404' || res.status === '500' || res.status === '400') {
          this.app.messageProcess(res.status, res.content);
        }
      }, error => {
        console.log(error);
      });
    });
  }

  checkWarning(e: FormControl) {
    if (this.statusCheck) {
      if (!e.valid) {
        return true;
      }
    }
    return false;
  }

  changeAuditStatus(item) {
    if (item === 1 || item === 2) {
      this.formAdd.controls['auditStatus'].setValue(0);
      this.formAdd.patchValue({auditStatusName: this.translation.translate('common.auditstatus.zero')});
    } else if (item === 3) {
      this.formAdd.controls['auditStatus'].setValue(10);
      this.formAdd.patchValue({auditStatusName: this.translation.translate('common.auditstatus.ten')});
    } else {
      this.formAdd.value.auditStatus = null;
      this.formAdd.patchValue({auditStatusName: ''});
    }
  }

  onClosed() {

    this.tabLayoutService.close({
      component: 'StationSaveComponent',
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
    this.eventBusService.emit({
      type: 'stationList',
    });
    this.tabLayoutService.open({
      component: 'StationListComponent',
      header: 'station.manage.label',
      breadcrumb: [{label: this.translation.translate('station.manage.label')}]
    });
  }

  onClosedTab() {

    const action = this.action ? this._genKey + this.action : '';
    const tabId = this.tabId ? this._genKey + this.tabId : '';
    const key = StationSaveComponent.name + action + tabId;
    if (this.isTabChanged === 'isTabChanged') {
      this.tabLayoutService.isTabContentHasChanged({component: StationSaveComponent.name, key});
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(key));
    } else {
      this.tabLayoutService.close({
        component: StationSaveComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
      this.tabLayoutService.open({
        component: 'StationListComponent',
        header: 'station.manage.label',
        breadcrumb: [{label: this.translation.translate('station.manage.label')}]
      });
    }
  }

  validateNumber(type: string) {
    if (type === 'length') {
      if (!this.validateFloat.test(this.formAdd.value.length)) {
        this.formAdd.patchValue({length: null});
      }
    } else if (type === 'height') {
      if (!this.validateFloat.test(this.formAdd.value.height)) {
        this.formAdd.patchValue({height: null});
      }
    } else if (type === 'width') {
      if (!this.validateFloat.test(this.formAdd.value.width)) {
        this.formAdd.patchValue({width: null});
      }
    } else if (type === 'heightestBuilding') {
      if (!this.validateFloat.test(this.formAdd.value.heightestBuilding)) {
        this.formAdd.patchValue({heightestBuilding: null});
      }
    }

  }

  // @HostListener('document:keydown', ['$event'])
  // handleKeyboardEvent(event: KeyboardEvent): void {
  //   if (event.code=== 'Enter') {
  //     this.onSubmit();
  //   }
  // }


}
