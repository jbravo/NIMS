import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild, HostListener} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {CommonUtils} from '@app/shared/services/common-utils.service';
import {AppComponent} from '@app/app.component';
import {MessageService} from 'primeng/api';
import {PillarService} from '@app/modules/pillars-mgmt/service/pillar.service';
import {TranslationService} from 'angular-l10n';
import {DataCommonService} from '@app/shared/services/data-common.service';
import {CAT_ITEM, PILLAR_STATUS} from '@app/shared/services/constants';
import {PermissionService} from '@app/shared/services/permission.service';
import {ActivatedRoute} from '@angular/router';
import {TabLayoutService, TabLayoutComponent} from '@app/layouts/tab-layout';
import {Type} from 'angular-l10n/src/models/types';
import {Subscription} from 'rxjs';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {AutocompleteSearchLocationModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import {AutocompleteSearchDepartmentModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {AutocompleteSearchControlComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import {AutocompleteSearchLaneModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-lane-modal/autocomplete-search-lane-modal.component';
import {PoolService} from '@app/modules/pools-management/service/pool.service';

@Component({
  selector: 'pillar-save',
  templateUrl: './pillar-save.component.html',
  styleUrls: ['./pillar-save.component.css']
})
export class PillarSaveComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('autoCompleteLaneCode') autoCompleteLaneCode: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteDept') autoCompleteDept: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteLocation') autoCompleteLocation: AutocompleteSearchControlComponent;

  formAdd: FormGroup;
  statusCheck = false;
  statusList: any[] = [];
  houseStationTypeList: any[] = [];
  stationTypeList: any[] = [];
  ownerList: any[] = [];
  stationFeatureList: any[] = [];
  positions: any[] = [];
  auditTypeList: any[] = [];
  backupStatusList: any[] = [];
  deptList: any[] = [];
  locationList: any[] = [];
  laneList: any[] = [];
  pillarTypeCode: any[] = [];
  isSelectDept = true;
  isSelectLocation = true;
  private contentHasChangedSub: Subscription;
  private mapValueObjSub: Subscription;
  disabledLocationModal = true;
  deptId: number;
  deptLabel: string;
  locationId: number;
  geometryRegex: RegExp = /^[0-9-]+\.[0-9]{5}$/;
  errorLongitude = false;
  errorLatitude = false;
  dataRequest = {
    pillarId: [null],
    laneCode: undefined,
    pillarIndex: undefined,
    pillarCode: undefined,
    pillarTypeId: undefined,
    deptId: undefined,
    locationId: undefined,
    ownerId: undefined,
    address: undefined,
    constructionDate: undefined,
    status: undefined,
    longitude: undefined,
    latitude: undefined,
    note: undefined,
    rowStatus: undefined
  };
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  laneCode: Type<any> = AutocompleteSearchLaneModalComponent;
  isTabChanged: string;
  headerLaneCode = this.app.translation.translate('common.dialog.header.laneCode');
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLocation = this.app.translation.translate('common.dialog.header.location');
  private _genKey: string = '_';
  action: string;
  tabId: number;
  warningMessPillarSave: String[] = [];
  displayWarningMess: boolean = false;
  splitLocation = 5;
  isEditDept = false;
  isEditLocation = false;

  constructor(
    private fb: FormBuilder,
    private app: AppComponent,
    private pillarService: PillarService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private tabLayoutComponent: TabLayoutComponent,
    private poolService: PoolService,
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

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
    if (this.mapValueObjSub) {
      this.mapValueObjSub.unsubscribe();
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter' || event.code === 'NumpadEnter') {
      this.eventBusService.dataChange.subscribe(val => {
        console.log(val);
        if (val) {
          if (val.data.component === PillarSaveComponent.name && (!this.tabId || (val.data.tabId && val.data.tabId === this.tabId))) {
            this.onSubmit();
          }
        }
      }).unsubscribe();
    }
  }

  get f() {
    return this.formAdd.controls;
  }

  get formControls() {
    return this.formAdd.controls;
  }

  ngOnInit() {
    this.displayWarningMess = false;
    this.formAdd.patchValue({constructionDate: new Date()});
    this.tabId = this.pillarService.id;
    this.action = this.pillarService.action;
    if (this.action === 'edit' || this.action === 'view') {
      this.action = this.action;
      this.formAdd.value.pillarId = this.pillarService.id;
      this.pillarService.findPillarById(this.pillarService.id).subscribe(res => {
        const object = res.content;
        if (object) {
          this.eventBusService.emit({
            type: 'pillarSave',
            laneDataObj: object
          });
          object.constructionDate = CommonUtils.stringToDate(object.constructionDate, 'yyyy/MM/dd');
          object.longitude = this.poolService.formatLongLat(object.longitude);
          object.latitude = this.poolService.formatLongLat(object.latitude);
          if (this.action === 'edit') {
            this.isEditDept = true;
            this.isEditLocation = true;
          }
          this.getPillarTypeCode();
          this.getOwnerList();
          if (this.action === 'view') {
            this.statusList.forEach(item => {
              if (item.value === object.status) {
                object.status = item.label;
              }
            });
            if (object.pillarStatusCable == 1) {
              object.pillarStatusCable = this.translation.translate('pillar.cable.status.yes');
            }
            if (object.pillarStatusCable == 0) {
              object.pillarStatusCable = this.translation.translate('pillar.cable.status.no');
            }
          }
          this.formAdd.reset(object);
          this.formAdd.patchValue({laneCode: object});
          this.autoCompleteLaneCode.displayField = {laneCode: object.laneCode};
          this.autoCompleteLaneCode.displayFieldTooltip = object.laneCode;

          this.permissionService.findDepartmentById(object.deptId).subscribe(dept => {
            this.formAdd.patchValue({deptId: dept});
            this.autoCompleteDept.displayField = {pathName: dept.pathName};
            this.autoCompleteDept.displayFieldTooltip = dept.pathName;
            this.eventBusService.emit({
              type: 'pillarSave',
              deptDataObj: dept
            });
            this.isEditDept = false;
          });
          this.permissionService.findCatLocationById(object.locationId).subscribe(location => {
            this.formAdd.patchValue({locationId: location});
            this.autoCompleteLocation.displayField = {pathLocalName: location.pathLocalName};
            this.autoCompleteLocation.displayFieldTooltip = location.pathLocalName;
            this.eventBusService.emit({
              type: 'pillarSave',
              locationDataObj: location
            });
            this.isEditLocation = false;
          });
        }
      });
    }
    else{
      this.pillarService.selectedDeptData = undefined;
      this.permissionService.findCatDeptByPost({deptName: undefined,deptId: undefined}).subscribe(s => {
        if(s.content.listData.length > 0){
          this.formAdd.patchValue({deptId: s.content.listData[0].deptId});
          this.autoCompleteDept.displayField = {pathName: s.content.listData[0].pathName};
          this.pillarService.selectedDeptData = s.content.listData[0];
          this.formAdd.value.deptId = s.content.listData[0].deptId ;       
        }   
      });  
    }
    // Lay danh sach loai cot
    this.getPillarTypeCode();
    // Lay danh sach chu so huu
    this.getOwnerList();
    this.statusList = this.getDropDownList(PILLAR_STATUS);

  }

  public getDropDownList(constant: any[]) {
    const resultList = [];
    for (let i = 0; i < constant.length; i++) {
      resultList.push({label: this.translation.translate(constant[i].label), value: constant[i].value});
    }
    return resultList;
  }

  ngAfterViewInit(): void {
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      if (!this.isEditDept && !this.isEditLocation) {
        this.isTabChanged = 'isTabChanged';
        const action = this.action ? this._genKey + this.action : '';
        const tabId = this.tabId ? this._genKey + this.tabId : '';
        const key = PillarSaveComponent.name + action + tabId;
        this.tabLayoutService.isTabContentHasChanged({component: PillarSaveComponent.name, key});
      }
    });

  }

  buildForm() {
    this.formAdd = this.fb.group({
      pillarId: [null],
      laneCode: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pillar.laneCode')])],
      pillarIndex: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pillar.index'), Validators.maxLength(4)])],
      pillarCode: [null],
      pillarTypeId: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pillar.type.code')])],
      deptId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('common.label.unit')])],
      locationId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pillar.locationName')])],
      ownerId: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pillar.ownerName')])],
      address: [null, Validators.compose([Validators.required, Validators.maxLength(200)])],
      constructionDate: [''],
      status: [null],
      longitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('common.longitude')])],
      latitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('common.latitude')])],
      note: [null, Validators.compose([Validators.required, Validators.maxLength(500)])],
      rowStatus: [null],
      createTime: [null],
      pillarStatusCable: [null]
    });
  }

  onClearDatePicker() {
    this.formAdd.controls['constructionDate'].setValue(null);
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formAdd.controls['constructionDate'].setValue(null);
      }
    }
  }

  getPillarTypeCode() {
    this.pillarService.getPillarTypeCodeList().subscribe(res => {
      this.pillarTypeCode = [];
      this.pillarTypeCode.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      res.content.listData.forEach(element => {
        this.pillarTypeCode.push(({label: element.pillarTypeCode, value: element.pillarTypeId}));
      });
      this.pillarTypeCode.forEach(item => {
        if (item.value == this.formAdd.value.pillarTypeId) {
          if (this.action == 'view') {
            this.formAdd.value.pillarTypeId = item.label;
            this.formAdd.patchValue({pillarTypeId: item.label});
          }
          if (this.action == 'edit') {
            this.formAdd.value.pillarTypeId = item;
            // this.formAdd.patchValue({ pillarTypeId: item });
          }
        }
      });
    });
  }


  // lay danh sach don vi
  getDept(event) {
    const params = {
      deptName: event.query ? event.query : undefined,
      deptId: event.deptId ? event.deptId : undefined
    };
    this.permissionService.findCatDeptByPost(params).subscribe(s => {
      this.deptList = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.deptList.push({label: s.content.listData[i].deptName, value: s.content.listData[i].deptId});
      }
      this.deptList.forEach(item => {
        if (item.value === this.formAdd.value.deptId) {
          if (this.action === 'view') {
            this.formAdd.value.deptId = item.label;
            this.formAdd.patchValue({deptId: item.label});
          }
          if (this.action === 'edit') {
            this.formAdd.value.deptId = item;
            this.formAdd.patchValue({deptId: item});
          }
        }
      });
    });
  }

  // lay danh sach dia ban
  getLocation(event) {
    const params = {
      locationName: event.query ? event.query : undefined,
      locationId: event.locationId ? event.locationId : undefined
    };
    this.permissionService.findCatLocation(params).subscribe(s => {
      this.locationList = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.locationList.push({label: s.content.listData[i].locationName, value: s.content.listData[i].locationId});
      }
      this.locationList.forEach(item => {
        if (item.value === this.formAdd.value.locationId) {
          if (this.action === 'view') {
            this.formAdd.value.locationId = item.label;
            this.formAdd.patchValue({locationId: item.label});
          }
        }
        if (this.action === 'edit') {
          this.formAdd.value.locationId = item;
          this.formAdd.patchValue({locationId: item});
        }
      });
    });
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      // truong hop them moi
      this.ownerList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({label: res[i].itemName, value: +res[i].itemId});
      }
      if (this.action == 'view') {
        this.ownerList.forEach(item => {
          if (item.value == this.formAdd.value.ownerId) {
            this.formAdd.value.ownerId = item.label;
            this.formAdd.patchValue({ownerId: item.label});
          }
        });
      }
    });
  }

  onChangeRowSelectDept(event: any) {
    this.eventBusService.emit({
      type: 'pillarSave',
      deptDataObj: event
    });
  }

  onChangeRowSelectLocation(event: any) {
    this.locationId = event.locationId;
  }

  onClearRowSelect() {
    this.formAdd.controls['deptId'].setValue(null);
    // this.formAdd.controls['locationId'].setValue(null);
    this.disabledLocationModal = true;
    this.eventBusService.emit({
      type: 'pillarSave',
      deptDataObj: null
    });
  }

  onClearLaneCode(){
    this.formAdd.controls['laneCode'].setValue(null);
  }

  onSelectedData(data) {
    if (data === 'deptId') {
      this.isSelectDept = false;
    }
    if (data === 'locationId') {
      this.isSelectLocation = false;
    }
  }

  onNonSelectData(data) {
    if (data === 'deptId' && this.isSelectDept) {
      this.formAdd.controls['deptId'].setValue('');
    }
    if (data === 'locationId' && this.isSelectLocation) {
      this.formAdd.controls['locationId'].setValue('');
    }
  }

  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    this.getFormValidationErrors(() => {
      const bk_LocationID = this.formAdd.value.locationId;
      const bk_DeptID = this.formAdd.value.deptId;
      this.formAdd.value.laneCode = this.formAdd.controls['laneCode'].value.laneCode ? this.formAdd.controls['laneCode'].value.laneCode : null;
      if (this.formAdd.value.status === null) {
        this.formAdd.value.status = 0;
      }
      this.formAdd.value.deptId = this.formAdd.value.deptId && this.formAdd.value.deptId.deptId ? this.formAdd.value.deptId.deptId : this.formAdd.value.deptId;
      // this.formAdd.value.locationId = this.formAdd.controls['locationId'].value.locationId ? this.formAdd.controls['locationId'].value.locationId : null;
      this.formAdd.value.locationId = this.formAdd.value.locationId
      && this.formAdd.value.locationId.locationId ? this.formAdd.value.locationId.locationId : null;
      this.isExitCode(() => {
        this.pillarService.savePillar(this.formAdd.value).subscribe(res => {
          if (res.status == '200') {
            this.isTabChanged = null;
            this.onClosed();
            this.app.messageProcess(res.status, res.content);
            if (this.action == 'edit') {
              this.messageService.add({
                severity: 'success',
                key: 'kkkk',
                summary: this.translation.translate('common.label.NOTIFICATIONS'),
                detail: this.translation.translate('pillar.update.success')
              });
            } else {
              this.messageService.add({
                severity: 'success',
                key: 'kkkk',
                summary: this.translation.translate('common.label.NOTIFICATIONS'),
                detail: this.translation.translate('message.success.created.success')
              });
            }
          }
          if (res.status == '404' || res.status == '500' || res.status == '400') {
            this.app.messageProcess(res.status, res.content);
            this.messageService.add({
              severity: 'error',
              key: 'rightDownErr',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('common.message.error.system.save')
            });
          }
        }, error => {
          this.messageService.add({
            severity: 'error',
            key: 'rightDownErr',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.action === 'edit' ? this.translation.translate('pool.update.error')
              : this.translation.translate('pool.create.error')
          });
        });
        this.formAdd.value.locationId = bk_LocationID;
        this.formAdd.value.deptId = bk_DeptID;
      });

    });
  }

  isExitCode(success: () => void) {
    if (typeof this.action == 'undefined') {
      this.pillarService
        .isExitCode({pillarCode: this.formAdd.value.pillarCode})
        .subscribe(res => {
          const isExit = res.content;
          if (!isExit) {
            success();
          }
        });
    } else {
      success();
    }
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    let is_Success = true;
    // if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'pillar', 'msgsPillar')) {
    //   is_Success = true;
    // }
    if (!this.formAdd.value.laneCode) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidateLaneCode')
      });
    }
    if (!this.formAdd.value.pillarIndex) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidatePillarIndex')
      });
    }
    if (!this.formAdd.value.pillarTypeId) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidatePillarTypeCode')
      });
    }
    if (!this.formAdd.value.deptId) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidateDept')
      });
    }
    if (!this.formAdd.value.locationId) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidateLocal')
      });
    }
    if (!this.formAdd.value.ownerId) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidateOwner')
      });
    }
    if (!this.formAdd.value.longitude) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidateLongitude')
      });
    }
    if (!this.formAdd.value.latitude) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pillar.vaidateLatitude')
      });
    }
    if (this.action != 'edit') {
      this.pillarService
        .isExitCode({pillarCode: this.formAdd.value.pillarCode})
        .subscribe(res => {
          const isExit = res.content;
          if (isExit) {
            is_Success = false;
            this.messageService.add({
              key: 'msgsPillar',
              sticky: true,
              severity: 'error',
              summary: this.translation.translate('pillar.isExist', {pillarCode: this.formAdd.value.pillarCode}),
            });
          }
        });
    }
    // if (this.formAdd.value.longitude !== '') {
    //   const resultCheck = this.checkLongLat(this.formAdd.value.longitude, 180);
    //   if (resultCheck === 1) {
    //     is_Success = false;
    //     this.messageService.add({
    //       key: 'msgsPillar',
    //       sticky: true,
    //       severity: 'error',
    //       summary: this.translation.translate('pool.vaidateLongitude')
    //     });
    //   } else if (resultCheck === 2) {
    //     is_Success = false;
    //     this.messageService.add({
    //       key: 'msgsPillar',
    //       sticky: true,
    //       severity: 'error',
    //       summary: this.translation.translate('pool.vaidateLongitude2')
    //     });
    //   } else {
    //     this.formAdd.value.longitude = resultCheck;
    //   }
    // }

    // if (this.formAdd.value.latitude !== '') {
    //   const resultCheck = this.checkLongLat(this.formAdd.value.latitude, 90);
    //   if (resultCheck === 1) {
    //     is_Success = false;
    //     this.messageService.add({
    //       key: 'msgsPillar',
    //       sticky: true,
    //       severity: 'error',
    //       summary: this.translation.translate('pool.vaidateLatitude')
    //     });
    //   } else if (resultCheck === 2) {
    //     is_Success = false;
    //     this.messageService.add({
    //       key: 'msgsPillar',
    //       sticky: true,
    //       severity: 'error',
    //       summary: this.translation.translate('pool.vaidateLatitude2')
    //     });
    //   } else {
    //     this.formAdd.value.latitude = resultCheck;
    //   }
    // }

    if (this.formAdd.value.longitude != null && this.formAdd.value.longitude !== '') {
      const rs = this.isLongitude(this.formAdd.value.longitude);
      if (rs === 4) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPillar',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.messages.geometry.regex.longitude',{field: this.translation.translate('station.longitude')})
        });
        this.errorLongitude = true;
      } else if (rs === 2) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPillar',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.messages.geometry.regex.longitude.over',{field: this.translation.translate('station.longitude')})
        });
        this.errorLongitude = true;
      } else if (rs === 3) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPillar',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.messages.geometry.regex.longitude.require5',{field: this.translation.translate('station.longitude')})
        });
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
        is_Success = false;
        this.messageService.add({
          key: 'msgsPillar',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.messages.geometry.regex.latitude',{field: this.translation.translate('station.latitude')})
        });
        this.errorLatitude = true;
      } else if (rs === 2) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPillar',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.messages.geometry.regex.latitude.over',{field: this.translation.translate('station.latitude')})
        });
        this.errorLatitude = true;
      } else if (rs === 3) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPillar',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.messages.geometry.regex.latitude.require5',{field: this.translation.translate('station.latitude')})
        });
        this.errorLatitude = true;
      } else {
        this.errorLatitude = false;
      }
    } else {
      this.errorLatitude = false;
    }


    if (this.formAdd.value.locationId && this.formAdd.value.locationId
      && ((this.formAdd.value.locationId.pathLocalId).split('/')[this.splitLocation] === undefined
        || (this.formAdd.value.locationId.pathLocalId).split('/')[this.splitLocation] === '')) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPillar',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pool.vaidateLocation'),
      });
    }

    if (is_Success) {
      success();
    }
  }

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
  debugger
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


  checkLongLat(str, limit) {
    if (str > limit) {
      return 2;
    }
    str = str + '';
    const regex = /[^-.0-9]/g;
    if (str.match(regex)) {
      return 1;
    }
    if ((this.countInstances(str, '.') !== 1 || this.countInstances(str, '-') > 1)
      || (this.countInstances(str, '-') === 1 && str[0] !== '-')
      || (this.countInstances(str, '-') === 1 && str[0] === '-' && str[1] === '.')) {
      return 1;
    }
    const listNb = str.split('.');
    if (listNb[0] === '') {
      return 1;
    }
    if (Math.abs(Number(listNb[0])) > limit) {
      return 2;
    }
    if (listNb[1].length < 5) {
      return 2;
    } else {
      return listNb[0] + '.' + listNb[1].substring(0, 5);
    }
  }

  countInstances(string, word) {
    return string.split(word).length - 1;
  }

  onClosed() {
    this.eventBusService.emit({
      type: 'pillarList',
    });

    this.tabLayoutService.close({
      component: 'PillarSaveComponent',
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
    this.openPillarListTab();
  }

  onClosedTab() {
    const action = this.action ? this._genKey + this.action : '';
    const tabId = this.tabId ? this._genKey + this.tabId : '';
    const key = PillarSaveComponent.name + action + tabId;
    if (this.isTabChanged === 'isTabChanged') {
      this.tabLayoutService.isTabContentHasChanged({component: PillarSaveComponent.name, key});
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(key));
    } else {
      this.tabLayoutService.close({
        component: PillarSaveComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
      this.openPillarListTab();
    }
  }

  openPillarListTab() {
    this.tabLayoutService.open({
      component: 'PillarListComponent',
      header: 'pillar.manage.label',
      breadcrumb: [
        {label: this.translation.translate('pillar.manage.label')}
      ]
    });
  }

  onchangeLaneCode(event) {
    this.pillarService.getPillarIndex(event.laneCode).subscribe(res => {
      this.formAdd.controls['pillarIndex'].setValue(res.content);
      this.formAdd.controls['pillarCode'].setValue(event.laneCode + '_' + res.content);
    });
  }

  fillPillarCode(event) {
    if (event.target.value.length < 1) {
      this.formAdd.controls['pillarIndex'].setValue(null);
    } else if (event.target.value.length < 2) {
      this.formAdd.controls['pillarIndex'].setValue(event.target.value + '000');
    } else if (event.target.value.length < 3) {
      this.formAdd.controls['pillarIndex'].setValue(event.target.value + '00');
    } else if (event.target.value.length < 4) {
      this.formAdd.controls['pillarIndex'].setValue(event.target.value + '0');
    }
    if (typeof this.formAdd.value.laneCode != 'undefined') {
      if (this.formAdd.value.laneCode.laneCode) {
        this.formAdd.value.pillarCode = this.formAdd.value.laneCode.laneCode + '_' + event.target.value;
      } else {
        this.formAdd.value.pillarCode = this.formAdd.value.laneCode + '_' + event.target.value;
      }
    }
  }

  getValue(event, field) {
    if (field === 'note') {
      this.formAdd.value.note = event.target.value;
    }
    if (field === "address") {
      this.formAdd.value.address = event.target.value;
    }
  }

}
