import { Component, OnInit, Type, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';
import { MessageService } from 'primeng/api';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { CAT_ITEM, STATUS_POOL } from '@app/shared/services/constants';
import { PermissionService } from '@app/shared/services/permission.service';
import { ActivatedRoute } from '@angular/router';
import { TabLayoutService, TabLayoutComponent } from '@app/layouts/tab-layout';
import { PoolService } from '../../service/pool.service';
import {
  AutocompleteSearchDepartmentModalComponent
} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {
  AutocompleteSearchLocationModalComponent
} from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import { EventBusService } from '@app/shared/services/event-bus.service';
import {
  AutocompleteSearchControlComponent
} from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import { Subscription } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'pool-save',
  templateUrl: './pool-save.component.html',
  styleUrls: ['./pool-save.component.css']
})
export class PoolSaveComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('autoCompleteDept') autoCompleteDept: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteLocation') autoCompleteLocation: AutocompleteSearchControlComponent;

  formAdd: FormGroup;
  statusCheck = false;
  ownerList: any[] = [];
  action: string;
  deptList: any[] = [];
  locationList: any[] = [];

  msgsPool_Number: any[] = [];
  statusListNew: any[] = [];
  poolTypeList: any[];
  _numberPool: any = '';
  back_numberPool: any = '';
  _deptCode_TTT: any = '';
  _key_P: any = 'P';
  _default_Char_Z: any = 'A';
  _endChar_Z: any = '';
  _path: any;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  disabledLocationModal = true;
  isDownProvince = false;
  isTabChanged: string;
  private contentHasChangedSub: Subscription;
  defaultDate: any;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLocation = this.app.translation.translate('common.dialog.header.location');
  idPoolLocal: any;
  private _genKey = '_';
  tabId: number;
  splitLocation = 5;
  splitDept = 3;
  splitDeptCode = 2;
  poolCodeStart = 4;
  poolCodeEnd = 8;
  errorLongitude = false;
  errorLatitude = false;
  isEditDept = false;
  isEditLocation = false;

  constructor(
    private app: AppComponent,
    private poolService: PoolService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private fb: FormBuilder,
    private tabLayoutComponent: TabLayoutComponent,
    private spinner: NgxSpinnerService,
  ) {
    this.buildForm();
  }

  ngOnInit() {
    this.statusListNew = this.dataCommon.getDropDownList(STATUS_POOL);
    this.action = this.poolService.action;
    this.idPoolLocal = this.poolService.id;
    this.tabId = this.poolService.id;
    this.poolService.id = null;
    this.poolService.action = null;

    if (this.action === 'edit' || this.action === 'view') {
      this.formAdd.value.poolId = this.idPoolLocal;

      this.poolService.findById(this.idPoolLocal).subscribe(res => {

        const object = res.content;
        if (object) {
          object.constructionDate = CommonUtils.stringToDate(object.constructionDate, 'yyyy/MM/dd');
          object.deliveryDate = CommonUtils.stringToDate(object.deliveryDate, 'yyyy/MM/dd');
          object.acceptanceDate = CommonUtils.stringToDate(object.acceptanceDate, 'yyyy/MM/dd');
          object.longitude = this.poolService.formatLongLat(object.longitude);
          object.latitude = this.poolService.formatLongLat(object.latitude);
          if (this.action === 'edit') {
            this.isEditDept = true;
            this.isEditLocation = true;
          }
          this.formAdd.reset(object);

          if (this.action === 'edit') {
            // debugger
            this.isDownProvince = true;
            this.formAdd.patchValue({
              numberPool: this.poolService.formartNumberPool(object.poolCode.substring(this.poolCodeStart, this.poolCodeEnd) + '', false)
            });
            this.permissionService.findDepartmentById(object.deptId).subscribe(data => {

              this.formAdd.patchValue({
                deptId: data
              });
              this.autoCompleteDept.displayField = { pathName: data.pathName };
              this.autoCompleteDept.displayFieldTooltip = data.pathName;
              this.eventBusService.emit({
                type: 'poolSave',
                deptDataObj: data
              });
              this.isEditDept = false;
            });

            this.permissionService.findCatLocationById(object.locationId).subscribe(location => {
              this.formAdd.patchValue({
                locationId: location
              });
              this.autoCompleteLocation.displayField = { pathLocalName: location.pathLocalName };
              this.autoCompleteLocation.displayFieldTooltip = location.pathLocalName;
              this.eventBusService.emit({
                type: 'poolSave',
                locationDataObj: location
              });
              this.isEditLocation = false;
            });


          }
        }

      });
    }
    if (this.action !== 'edit' && this.action !== 'view') {
      this.defaultDate = new Date();
      this.formAdd.controls['constructionDate'].setValue(this.defaultDate);
      this.formAdd.controls['deliveryDate'].setValue(this.defaultDate);
      this.formAdd.controls['acceptanceDate'].setValue(this.defaultDate);
    }
    if (this.action !== 'view') {
      // list chu so huu
      this.getOwnerList();
      // lay getPoolTypeList
      this.getPoolTypeList();
    }

  }

  ngAfterViewInit(): void {
    this.contentHasChangedSub = this.formAdd.valueChanges.subscribe(val => {
      if (!this.isEditDept && !this.isEditLocation) {
        this.isTabChanged = 'isTabChanged';
        const action = this.action ? this._genKey + this.action : '';
        const tabId = this.tabId ? this._genKey + this.tabId : '';
        const key = PoolSaveComponent.name + action + tabId;
        this.tabLayoutService.isTabContentHasChanged({ component: PoolSaveComponent.name, key });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
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

  buildForm() {
    this.formAdd = this.fb.group({
      poolId: [''],
      deptId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pool.deptName')])],
      numberPool: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pool.poolNumber')])],
      poolTypeId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('requiredSelect//pool.poolTypeCode')])],
      locationId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pool.locationName')])],
      address: ['', Validators.maxLength(500)],
      ownerId: [null],
      status: [0],
      longitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pool.longitude')])],
      latitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('pool.latitude')])],
      note: ['', Validators.maxLength(500)],
      poolCode: [''],
      deliveryDate: [''],
      acceptanceDate: [''],
      constructionDate: [''],
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  onClearDatePicker(type?: string) {
    if (type === 'constructionDate') {
      this.formAdd.controls['constructionDate'].setValue(null);
    } else if (type === 'deliveryDate') {
      this.formAdd.controls['deliveryDate'].setValue(null);
    } else if (type === 'acceptanceDate') {
      this.formAdd.controls['acceptanceDate'].setValue(null);
    }
  }

  onInput(event, type?: string) {
    if (event) {
      if (type === 'constructionDate') {
        if (event.currentTarget.value === '') {
          this.formAdd.controls['constructionDate'].setValue(null);
        }
      } else if (type === 'deliveryDate') {
        if (event.currentTarget.value === '') {
          this.formAdd.controls['deliveryDate'].setValue(null);
        }
      } else if (type === 'acceptanceDate') {
        if (event.currentTarget.value === '') {
          this.formAdd.controls['acceptanceDate'].setValue(null);
        }
      }
    }
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    let is_Success = false;
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'station', 'msgsPool')) {
      is_Success = true;
      // success();
    }
    if (this.formAdd.value.longitude !== '') {
      const resultCheck = this.poolService.checkLongLat(this.formAdd.value.longitude, 180);
      if (resultCheck === 1) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPool',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('pool.vaidateLongitude'),
        });
        this.errorLongitude = true;
      } else if (resultCheck === 2) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPool',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('pool.vaidateLongitude2'),
        });
        this.errorLongitude = true;
      } else {
        this.formAdd.value.longitude = resultCheck;
        this.errorLongitude = false;
      }
    }

    if (this.formAdd.value.latitude !== '') {
      const resultCheck = this.poolService.checkLongLat(this.formAdd.value.latitude, 90);
      if (resultCheck === 1) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPool',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('pool.vaidateLatitude'),
        });
        this.errorLatitude = true;
      } else if (resultCheck === 2) {
        is_Success = false;
        this.messageService.add({
          key: 'msgsPool',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('pool.vaidateLatitude2'),
        });
        this.errorLatitude = true;
      } else {
        this.formAdd.value.latitude = resultCheck;
        this.errorLatitude = false;
      }
    }

    if (!this.isDownProvince && this.formAdd.value.deptId) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPool',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pool.vaidateDept'),
      });
    }

    if (this.formAdd.value.locationId && this.formAdd.value.locationId
      && ((this.formAdd.value.locationId.pathLocalId).split('/')[this.splitLocation] === undefined
        || (this.formAdd.value.locationId.pathLocalId).split('/')[this.splitLocation] === '')) {
      is_Success = false;
      this.messageService.add({
        key: 'msgsPool',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('pool.vaidateLocation'),
      });
    }

    if (is_Success) {
      success();
    }
  }

  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    this.getFormValidationErrors(() => {
      const bk_DeptID = this.formAdd.value.deptId;
      const bk_LocationID = this.formAdd.value.locationId;

      this.formAdd.value.deptId = this.formAdd.value.deptId && this.formAdd.value.deptId.deptId ? this.formAdd.value.deptId.deptId : null;
      this.formAdd.value.locationId = this.formAdd.value.locationId
        && this.formAdd.value.locationId.locationId ? this.formAdd.value.locationId.locationId : null;
      this.spinner.show();
      this.poolService.savePool(this.formAdd.value).subscribe(res => {
        this.spinner.hide();
        if (res.status === '200') {
          // console.log('thong bao nhanh ');
          this.messageService.add({
            severity: 'success',
            key: 'rightDown',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.action === 'edit' ? this.translation.translate('pool.update.successful')
              : this.translation.translate('pool.create.successful')
          });
          // this.isTabChanged = null;
          this.onClosed();
        } else if (status === '404' || status === '500' || status === '400') {
          // this.app.messageProcess(res.status, res.content);
          // console.log('Đã có lỗi xảy ra, hệ thống không thể lưu bản ghi dung Toast message.add');
          // this.formAdd.value.deptId = bk_DeptID;
          // this.formAdd.value.locationId = bk_LocationID;
        }
      }, error => {
        this.spinner.hide();
        // console.log(error);
        // console.log('Đã có lỗi xảy ra, hệ thống không thể lưu bản ghi dung Toast message.add');
        this.messageService.add({
          severity: 'error',
          key: 'rightDown',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.action === 'edit' ? this.translation.translate('pool.update.error')
            : this.translation.translate('pool.create.error')
        });
        // this.formAdd.value.deptId = bk_DeptID;
        // this.formAdd.value.locationId = bk_LocationID;
      });
      this.formAdd.value.deptId = bk_DeptID;
      this.formAdd.value.locationId = bk_LocationID;
    });


  }

  onClosed() {
    this.eventBusService.emit({
      type: 'poolList',
    });

    this.tabLayoutService.close({
      component: PoolSaveComponent.name,
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
    this.poolService.openPoolListTab();
  }

  onClosedTab() {
    // debugger
    const action = this.action ? this._genKey + this.action : '';
    const tabId = this.tabId ? this._genKey + this.tabId : '';
    const key = PoolSaveComponent.name + action + tabId;

    if (this.isTabChanged === 'isTabChanged') {
      this.tabLayoutService.isTabContentHasChanged({ component: PoolSaveComponent.name, key });
      const parent = {
        component: 'PoolListComponent',
        header: 'pool.header',
        breadcrumb: [
          { label: this.translation.translate('pool.header') }
        ]
      };

      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(key), parent);
    } else {
      this.tabLayoutService.close({
        component: PoolSaveComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
      this.poolService.openPoolListTab();
    }

  }

  // lay list PoolType
  getPoolTypeList() {
    this.permissionService.getPoolTypeList().subscribe(data => {
      const res = data.content;
      this.poolTypeList = [];
      this.poolTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.poolTypeList.push({ label: res[i].poolTypeCode, value: +res[i].poolTypeId });
      }
    });
  }


  setautoCompleteValue(event, element) {
    // debugger
    // this.formAdd.controls[element].setValue(event.value);
    if (element === 'deptId') {
      if ((event.path).split('/')[this.splitDept] === undefined || (event.path).split('/')[this.splitDept] === '') {
        // console.log('thong bao chua chon den cap  tinh!!');
        this.messageService.clear('msgsPool_Number');
        this.messageService.add({
          key: 'msgsPool_Number',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('pool.vaidateDept'),
        });
        this.formAdd.patchValue({ poolCode: '' });
        this.formAdd.patchValue({ numberPool: '' });
        this.isDownProvince = false;
      } else {
        this._path = (event.path).split('/')[this.splitDept]; // Id TTT
        this._deptCode_TTT = (event.pathcode).split('/')[this.splitDeptCode]; // code TTT


        this.poolService.getNumberGenerate(this._path).subscribe(res => {
          if (res.content === 0) {
            // console.log('thong bao');
            // this.messageService.clear('msgsPool_Number');
            // this.messageService.add({
            //   key: 'msgsPool_Number',
            //   sticky: true,
            //   severity: 'error',
            //   summary: this.translation.translate('pool.poolNumberLimit'),
            // });
            this.messageService.add({
              severity: 'error',
              key: 'rightDown',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('pool.poolNumberLimit')
            });

            this._numberPool = '';
            this.back_numberPool = this._numberPool;
            this.formAdd.patchValue({ numberPool: this._numberPool });
            this.formAdd.patchValue({ poolCode: '' });
          } else {
            this._numberPool = this.poolService.formartNumberPool(res.content + '', false);
            this.back_numberPool = this._numberPool;
            this.formAdd.patchValue({ numberPool: this._numberPool });
            this.poolService.checkNumber(this._deptCode_TTT, Number(this._numberPool)).subscribe(data => {
              if (data.content == null) {
                this._endChar_Z = this._default_Char_Z;
                this.formAdd.patchValue({ poolCode: this._deptCode_TTT + this._key_P + this._numberPool + this._endChar_Z });

              } else {
                this._endChar_Z = data.content;
              }
            });
          }
          this.isDownProvince = true;
        });
      }
    }
  }


  myFunction(e) {
    if (this.action === 'edit' || e.target.value === '') {
      return;
    }
    const dataFortmat = this.poolService.formartNumberPool(e.target.value, true);
    if (this._numberPool === dataFortmat && e.target.value !== '') {
      this.formAdd.patchValue({ numberPool: dataFortmat });
      return;
    }
    this.back_numberPool = this._numberPool;
    this._numberPool = dataFortmat;
    this.formAdd.patchValue({ numberPool: this._numberPool });
    if (this._numberPool !== '' && this.isDownProvince) {
      this.poolService.checkNumber(this._deptCode_TTT, Number(this._numberPool)).subscribe(res => {
        if (res.content === null) {
          this._endChar_Z = this._default_Char_Z;
          this.formAdd.patchValue({ poolCode: this._deptCode_TTT + this._key_P + this._numberPool + this._endChar_Z });
        } else if (res.content === 'Z') {
          // console.log('Full Z');
          this._numberPool = this.back_numberPool;
          this.formAdd.patchValue({ numberPool: this._numberPool });
          this.messageService.clear('msgsPool_Number');
          this.messageService.add({
            key: 'msgsPool_Number',
            sticky: true,
            severity: 'error',
            summary: this.translation.translate('pool.poolEndCharLimit'),
          });

        } else {
          // console.log('Trùng : ' + res.content + '=>' + String.fromCharCode(res.content.charCodeAt(0) + 1));

          this.messageService.clear();
          this.messageService.add({
            key: 'ToastnumberPool',
            sticky: true,
            severity: 'warn',
            summary: this.translation.translate('pool.poolCodeDuplicate'),
            data: res.content,
          });
        }
      });
    } else {
      this.formAdd.patchValue({ poolCode: null });
    }

  }

  onReject() {
    this.messageService.clear('ToastnumberPool');
    this._numberPool = this.back_numberPool;
    this.formAdd.patchValue({ numberPool: this._numberPool });
    this.formAdd.patchValue({ poolCode: this._deptCode_TTT + this._key_P + this._numberPool + this._endChar_Z });
  }

  onConfirm(e) {
    this.messageService.clear('ToastnumberPool');
    this._endChar_Z = String.fromCharCode(e.charCodeAt(0) + 1);
    this.formAdd.patchValue({ poolCode: this._deptCode_TTT + this._key_P + this._numberPool + this._endChar_Z });
  }

  get formControls() {
    return this.formAdd.controls;
  }

  onChangeRowSelectDept(event: any) {
    // debugger
    this.setautoCompleteValue(event, 'deptId');
    this.formAdd.patchValue({
      locationId: null
    });
    this.autoCompleteLocation.displayField = { pathLocalName: '' };
    this.autoCompleteLocation.displayFieldTooltip = '';
    this.eventBusService.emit({
      type: 'poolSave',
      locationDataObj: null
    });
    this.eventBusService.emit({
      type: 'poolSave',
      deptDataObj: event
    });
  }

  onClearRowSelect() {
    this.formAdd.controls['deptId'].setValue(null);
    this.formAdd.controls['locationId'].setValue(null);
    this.disabledLocationModal = true;
  }

  onChangeRowSelectLocation(event: any) {
    this.formAdd.controls['locationId'].setValue(event);
  }

}
