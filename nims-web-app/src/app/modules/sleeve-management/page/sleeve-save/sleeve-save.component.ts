import { AfterViewInit, Component, ElementRef, HostListener, OnDestroy, OnInit, Type, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services';
import { CAT_ITEM, SLEEVE_PURPOSE, SLEEVE_STATUS, VALIDATE_STYLE } from '@app/shared/services/constants';
import { SleeveService } from '../../service/sleeve.service';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { AppComponent } from '@app/app.component';
import { PermissionService } from '@app/shared/services/permission.service';
import { MessageService, TreeNode } from 'primeng/api';
import { Table } from 'primeng/table';
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import { AutocompleteSearchLocationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import { AutocompleteSearchLaneSleeveModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-lane-sleeve-modal/autocomplete-search-lane-sleeve-modal.component';
import { AutocompleteSearchPillarModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-pillar-modal/autocomplete-search-pillar-modal.component';
import { AutocompleteSearchPoolModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-pool-modal/autocomplete-search-pool-modal.component';
import { AutocompleteSearchControlComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import { Subscription } from 'rxjs';
import { PoolService } from '@app/modules/pools-management/service/pool.service';
import { PillarService } from '@app/modules/pillars-mgmt/service/pillar.service';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'sleeve-save',
  templateUrl: './sleeve-save.component.html',
  styleUrls: ['./sleeve-save.component.css']
})
export class SleeveSaveComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('dataTable') dataTable: Table;
  @ViewChild('autoCompleteDept') autoCompleteDept: AutocompleteSearchControlComponent;
  @ViewChild('autoCompletePool') autoCompletePool: AutocompleteSearchControlComponent;
  @ViewChild('autoCompletePillar') autoCompletePillar: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteLane') autoCompleteLane: AutocompleteSearchControlComponent;
  @ViewChild('focus') searchElement: ElementRef;

  formAdd: FormGroup;
  first: number;
  rows: number;
  last: number;
  action: string;

  sleeveCode: any;
  sleeveTypeList: any[];
  purposeName: any[];
  serial: any;
  sleeveIndex: any;
  note: any;
  filteredDeptName: any[];
  supplier: any[];
  manufacturer: any[];
  ownerNameList: any[];
  vendorNameList: any[];
  statusName: any[];
  filteredPillarName: any[];
  filteredPoolName: any[];
  filteredLaneName: any[];
  deptName: any[];
  holderIdList: any[];
  resultList: any = [];
  selectedResult: any = [];
  laneName: any;
  isTabChanged: string;

  isRemoveUnit = true;
  isRemovePillar = false;
  isRemovePool = false;
  isRemoveLane = true;

  isCheckPool = true;
  isCheckPillar = true;

  statusCheck = false;
  is_Success = false;
  isConfirmClose = true;

  item: any;
  treeNodes: TreeNode[] = [];
  selectedFile: TreeNode;
  indexMax: any[];
  private contentHasChangedSub: Subscription;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  laneSleeve: Type<any> = AutocompleteSearchLaneSleeveModalComponent;
  pillar: Type<any> = AutocompleteSearchPillarModalComponent;
  pool: Type<any> = AutocompleteSearchPoolModalComponent;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLaneSleeve = this.app.translation.translate('common.dialog.header.laneCode');
  headerPillar = this.app.translation.translate('common.dialog.header.pillar');
  headerPool = this.app.translation.translate('common.dialog.header.pool');

  is_Pool = false;
  is_Pillar = false;
  isSubmit = true;
  listField: String[];

  pillarDataObjSub;
  // ----------------------
  is_False = false;
  isPurpose: false;
  private _genKey: string;
  tabId: number;
  displayConfirmClose = false;

  pillarPoolDetail: any;
  isLoadata = false;
  isFocus: boolean;

  constructor(
    private sleeveService: SleeveService,
    private fb: FormBuilder,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private messageService: MessageService,
    private permissionService: PermissionService,
    private app: AppComponent,
    private tabLayoutService: TabLayoutService,
    private poolService: PoolService,
    private pillarService: PillarService,
    private spinner: NgxSpinnerService,
    private eventBusService: EventBusService) {
    this.buildForm();
  }

  ngOnInit() {
    this.isFocus = false;
    this.action = this.sleeveService.action;
    this.getSleeveType();
    this.getOwnerName();
    this.getVandorName();
    if ('save' === this.action) {
      this.formAdd.controls['installationDate'].setValue(new Date());
      this.formAdd.controls['updateTime'].setValue(new Date());
      this.formAdd.patchValue({ status: 0 });
      this.setPurpose(this.dataCommon.getDropDownList(SLEEVE_PURPOSE));
      this.setStatus(this.dataCommon.getDropDownList(SLEEVE_STATUS));
      this.getFieldOnStart();
    }
    if ('edit' === this.action) {
      this._genKey = '_';
      this.tabId = this.sleeveService.id;
      this.item = this.sleeveService.item;
      this.formAdd.value.sleeveId = this.sleeveService.id;
      this.sleeveService.findViewSleeveById(this.sleeveService.id).subscribe(res => {
        const object = res.content;
        if (object) {
          object.installationDate = CommonUtils.stringToDate(object.installationDate, 'yyyy/MM/dd');
          object.updateTime = CommonUtils.stringToDate(object.updateTime, 'yyyy/MM/dd');
          this.sleeveIndex = object.sleeveIndex;
          if (this.sleeveIndex < 99) {
            if (this.sleeveIndex < 9) {
              this.sleeveIndex = '00'.concat(String(this.sleeveIndex));
            } else {
              this.sleeveIndex = '0'.concat(String(this.sleeveIndex));
            }
          }
          object.sleeveIndex = this.sleeveIndex;
          this.isLoadata = true;
          this.formAdd.reset(object);
          this.formAdd.controls['holderId'].setValue(res.content.holderId);
          this.formAdd.controls['laneName'].setValue(res.content.laneCode);

          if (res.content.pillarCode != null && res.content.pillarCode !== '') {
            this.formAdd.controls['pillarId'].setValue(res.content.holderId);
            this.autoCompletePillar.displayField = { pillarCode: res.content.pillarCode };
            this.autoCompletePillar.displayFieldTooltip = res.content.pillarCode;
            this.isCheckPool = false;
            this.formAdd.patchValue({ pillarCode: res.content.pillarCode });
          }
          if (res.content.poolCode != null && res.content.poolCode !== '') {
            this.formAdd.controls['poolId'].setValue(res.content.holderId);
            this.autoCompletePool.displayField = { poolCode: res.content.poolCode };
            this.autoCompletePool.displayFieldTooltip = res.content.poolCode;
            this.isCheckPillar = false;
            this.formAdd.patchValue({ poolCode: res.content.poolCode });
          }

          this.autoCompleteLane.displayField = { laneCode: res.content.laneCode };
          this.autoCompleteLane.displayFieldTooltip = res.content.laneCode;
          this.formAdd.patchValue({ laneCode: res.content.laneCode });

          this.formAdd.controls['deptPath'].setValue(res.content.deptId);
          this.permissionService.findDepartmentById(res.content.deptId).subscribe(dept => {
            this.autoCompleteDept.displayField = { pathName: dept.pathName };
            this.autoCompleteDept.displayFieldTooltip = dept.pathName;
            this.eventBusService.emit({
              type: 'sleeveSave',
              deptDataObj: dept
            });
          });

          this.formAdd.patchValue({ status: res.content.status });
          this.isLoadata = false;
        }
        this.getFieldOnStart();
      });
      this.setPurpose(this.dataCommon.getDropDownList(SLEEVE_PURPOSE));
      this.setStatus(this.dataCommon.getDropDownList(SLEEVE_STATUS));
      this.treeNodes = [{
        'label': this.translation.translate('infra.sleeves.infomation'),
        'data': 'Documents Folder',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
        'children': [{
          'label': this.translation.translate('infra.weld.in.sleeves'),
          'data': 'Work Folder',
          'expandedIcon': 'fa fa-folder-o',
          'collapsedIcon': 'fa fa-folder-o',
        }]
      }];
      this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
        if (!this.isLoadata) {
          this.isTabChanged = 'isTabChanged';
          const action = this.action ? this._genKey + this.action : '';
          const tabId = this.tabId ? this._genKey + this.tabId : '';
          const key = SleeveSaveComponent.name + action + tabId;
          this.tabLayoutService.isTabContentHasChanged({ component: SleeveSaveComponent.name, key });
        }
      });
    }

    // lay gia tri ban dau cua cac field
  }

  ngAfterViewInit(): void {
    if ('save' === this.action) {
      if (this.contentHasChangedSub) {
        this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
          this.isTabChanged = 'isTabChanged';
          this.tabLayoutService.isTabContentHasChanged({ component: SleeveSaveComponent.name });
        });
      }
      // ThieuNV
      if (this.poolService.id && this.poolService.poolCode) {
        this.eventBusService.emit({
          type: 'poolRowData',
          poolDataObj: this.poolService.id,
        });
        this.formAdd.controls['holderId'].setValue(this.poolService.id);
        this.formAdd.controls['poolId'].setValue(this.poolService.id);
        this.formAdd.controls['pillarId'].setValue(null);
        this.autoCompletePool.displayField = { poolCode: this.poolService.poolCode };
        this.autoCompletePool.displayFieldTooltip = this.poolService.poolCode;
        this.isCheckPillar = false;
        this.formAdd.patchValue({ poolCode: this.poolService.poolCode });
        this.is_Pool = true;
        this.formAdd.controls['deptId'].setValue(this.poolService.poolObj.deptId);
        this.autoCompleteDept.displayField = { pathName: this.poolService.poolObj.pathName };
        this.autoCompleteDept.displayFieldTooltip = this.poolService.poolObj.pathName;
        this.formAdd.patchValue({ deptPath: this.poolService.poolObj.pathName });
        this.pillarPoolDetail = 'pool';
        this.poolService.id = null;
        this.poolService.poolCode = null;
        this.poolService.poolObj = null;
      } else if (this.pillarService.id && this.pillarService.pillarCode) {
        this.eventBusService.emit({
          type: 'pillarRowData',
          pillarDataObj: { pillarId: this.pillarService.id, pillarCode: this.pillarService.pillarCode },
        });
        this.formAdd.controls['holderId'].setValue(this.pillarService.id);
        this.formAdd.controls['pillarId'].setValue(this.pillarService.id);
        this.formAdd.controls['poolId'].setValue(null);
        this.autoCompletePillar.displayField = { pillarCode: this.pillarService.pillarCode };
        this.autoCompletePillar.displayFieldTooltip = this.pillarService.pillarCode;
        this.isCheckPool = false;
        this.formAdd.patchValue({ pillarCode: this.pillarService.pillarCode });
        this.is_Pillar = true;

        this.pillarPoolDetail = 'pillar';

        this.pillarService.id = null;
        this.pillarService.pillarCode = null;
      }
      this.getFieldOnStart();
      // THieuNV
    }

    if ('edit' === this.action) {
      if (this.poolService.id && this.poolService.poolCode) {
        this.pillarPoolDetail = 'pool';
        this.poolService.id = null;
        this.poolService.poolCode = null;
        this.poolService.poolObj = null;
      }
      if (this.pillarService.id && this.pillarService.pillarCode) {
        this.pillarPoolDetail = 'pillar';
        this.pillarService.id = null;
        this.pillarService.pillarCode = null;
      }
    }

  }
  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
    if (this.pillarDataObjSub) {
      this.pillarDataObjSub.unsubscribe();
    }
  }

  private buildForm() {
    this.formAdd = this.fb.group({
      sleeveId: [null],
      sleeveCode: ['', Validators.compose([Validators.required, Validators.maxLength(100)])],
      sleeveTypeId: ['', Validators.compose([Validators.required])],
      installationDate: ['', Validators.compose([Validators.required])],
      ownerName: [''],
      ownerId: [''],
      updateTime: ['', Validators.compose([Validators.required])],
      vendorName: [''],
      vendorId: [''],
      laneCode: [''],
      laneName: ['', Validators.compose([Validators.required])],
      serial: [''],
      purpose: ['', Validators.compose([Validators.required])],
      deptPath: ['', Validators.compose([Validators.required])],
      sleeveIndex: ['', Validators.compose([Validators.required, Validators.maxLength(3)])],
      status: ['', Validators.compose([Validators.required])],
      note: [''],
      holderId: ['', Validators.compose([Validators.required])],
      first: [''],
      rows: [''],
      deptId: [''],
      pillarId: [undefined],
      poolId: [undefined],
    });
  }

  onClearDatePicker(data) {
    if ('updateTime' === data) {
      this.formAdd.controls['updateTime'].setValue(null);
    }
    if ('installationDate' === data) {
      this.formAdd.controls['installationDate'].setValue(null);
    }
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formAdd.controls['installationDate'].setValue(null);
      }
    }
  }

  // setter start
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formAdd.controls[element].setValue(event.value.value);
    } else {
      this.formAdd.controls[element].setValue('');
    }
  }

  setPurpose(purpose) {
    this.purposeName = purpose;
  }

  setStatus(statusName) {
    this.statusName = statusName;
  }

  // setter end

  getSleeveType() {
    this.sleeveService.getSleeveTypeCodeList().subscribe(res => {
      this.sleeveTypeList = [];
      this.sleeveTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      const element: any = res.content.listData;
      for (let i = 0; i < element.length; i++) {
        this.sleeveTypeList.push({ label: element[i].sleeveTypeCode, value: +element[i].sleeveTypeId });
      }
    });
  }

  // chu so huu
  getOwnerName() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerNameList = [];
      // truong hop them moi
      this.ownerNameList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.ownerNameList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
    });
  }

  // nha san xuat
  getVandorName() {
    this.sleeveService.getVendorNameList().subscribe(res => {
      this.vendorNameList = [];
      this.vendorNameList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      const element: any = res.content.listData;
      for (let i = 0; i < element.length; i++) {
        this.vendorNameList.push({ label: element[i].itemName, value: +element[i].itemId });
      }
    });
  }

  onSelectedData(event, data) {
    if (data === 'deptPath') {
      this.formAdd.controls['deptId'].setValue(event.value);
      this.isRemoveUnit = false;
    }
    if (data === 'pillarId') {
      this.eventBusService.emit({
        type: 'pillar',
        pillarDataObj: event
      });
      this.formAdd.controls['holderId'].setValue(event.pillarId);
      this.formAdd.controls['poolId'].setValue(null);
      this.isCheckPillar = true;
      this.isCheckPool = false;
      this.formAdd.controls['deptId'].setValue(event.deptId);
      this.autoCompleteDept.displayField = { pathName: event.pathName };
      this.autoCompleteDept.displayFieldTooltip = event.pathName;
      this.formAdd.patchValue({ deptPath: event.pathName });
    }
    // be
    if (data === 'poolId') {
      this.eventBusService.emit({
        type: 'pool',
        poolDataObj: event
      });
      this.formAdd.controls['holderId'].setValue(event.poolId);
      this.formAdd.controls['pillarId'].setValue(null);
      this.isCheckPool = true;
      this.isCheckPillar = false;
      this.formAdd.controls['deptId'].setValue(event.deptId);
      this.autoCompleteDept.displayField = { pathName: event.pathName };
      this.autoCompleteDept.displayFieldTooltip = event.pathName;
      this.formAdd.patchValue({ deptPath: event.pathName });
    }
    if (data === 'laneCode') {
      this.sleeveCode = '';
      this.formAdd.controls['laneCode'].setValue(event.laneCode);
      this.isRemoveLane = false;
      this.laneName = event.laneCode;
      this.getIndexMax();
    }
  }

  onSubmit() {
    setTimeout(() => {
      this.searchElement.nativeElement.focus();
    }, 0);
    // check va set lai value cho cac truong autocomplete
    if (this.formAdd.value.pillarId instanceof Object) {
      this.formAdd.controls['holderId'].setValue(this.formAdd.value.pillarId.pillarId);
    } else {
      if (this.isCheckPillar) {
        this.formAdd.controls['holderId'].setValue(this.formAdd.value.pillarId);
      }
    }
    if ('' === this.formAdd.value.pillarId) {
      this.formAdd.controls['holderId'].setValue(null);
    }

    if (this.formAdd.value.poolId instanceof Object) {
      this.formAdd.controls['holderId'].setValue(this.formAdd.value.poolId.poolId);
    } else {
      if (this.isCheckPool) {
        this.formAdd.controls['holderId'].setValue(this.formAdd.value.poolId);
      }
    }
    if ('' === this.formAdd.value.poolId) {
      this.formAdd.controls['holderId'].setValue(null);
    }

    if (this.formAdd.value.deptPath !== undefined && this.formAdd.value.deptPath instanceof Object &&
      this.formAdd.value.deptPath.deptId != null && this.formAdd.value.deptPath.deptId !== '') {
      this.formAdd.controls['deptId'].setValue(this.formAdd.value.deptPath.deptId);
    }

    this.formAdd.controls['laneName']
      .setValue(this.formAdd.value.laneName ? this.formAdd.value.laneName : '');
    //

    this.statusCheck = true;
    this.messageService.clear();
    if (!this.isFocus) {
      this.getFormValidationErrors(() => {
      });
    }
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    this.is_Success = false;
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'sleeve', 'addSleeve')) {
      this.is_Success = true;
      if ((this.formAdd.value.pillarId == null || '' === this.formAdd.value.pillarId || this.formAdd.value.pillarId === undefined) &&
        (this.formAdd.value.poolId == null || '' === this.formAdd.value.poolId || this.formAdd.value.poolId === undefined)) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.required.pillar.pool'),
        });
      }
      // ----
      if (this.formAdd.value.updateTime != null && '' !== this.formAdd.value.updateTime &&
        this.formAdd.value.installationDate != null && '' !== this.formAdd.value.installationDate) {
        if (this.formAdd.value.updateTime < this.formAdd.value.installationDate) {
          this.is_Success = false;
          this.messageService.add({
            key: 'addSleeve',
            severity: 'error',
            summary: this.translation.translate('validate.sleeve.date'),
          });
        }
      }
      if (this.formAdd.value.laneName == null || '' === this.formAdd.value.laneName) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.laneName'),
        });
      }
      if (this.formAdd.value.deptPath == null || '' === this.formAdd.value.deptPath) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.dept'),
        });
      }
      if (this.formAdd.value.sleeveTypeId == null || '' === this.formAdd.value.sleeveTypeId) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.type.code'),
        });
      }
      if (this.formAdd.value.installationDate == null || '' === this.formAdd.value.installationDate) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.installation.date'),
        });
      }
      if (this.formAdd.value.updateTime == null || '' === this.formAdd.value.updateTime) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.modify.date'),
        });
      }
      if (this.formAdd.value.purpose == null || '' === this.formAdd.value.purpose) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.purpose'),
        });
      }
      if (this.formAdd.value.status == null || '' === this.formAdd.value.status) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.status'),
        });
      }
      if (this.formAdd.value.sleeveCode == null || '' === this.formAdd.value.sleeveCode) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.sleeveCode'),
        });
      }
      if (this.formAdd.value.sleeveIndex == null || '' === this.formAdd.value.sleeveIndex) {
        this.is_Success = false;
        this.messageService.add({
          key: 'addSleeve',
          severity: 'error',
          summary: this.translation.translate('validate.sleeve.sleeveIndex'),
        });
      }
      // ----
      if (this.is_Success) {
        this.spinner.show();
        this.sleeveService.saveSleeve(this.formAdd.value).subscribe(res => {
          if (res.status === '200') {
            success();
            this.spinner.hide();
            if ('save' === this.action) {
              this.messageService.add({
                key: 'success',
                severity: 'success',
                summary: this.translation.translate('common.label.NOTIFICATIONS'),
                detail: this.translation.translate('create.sleeve.success')
              });
            }
            if ('edit' === this.action) {
              this.messageService.add({
                key: 'success',
                severity: 'success',
                summary: this.translation.translate('common.label.NOTIFICATIONS'),
                detail: this.translation.translate('update.sleeve.success')
              });
            }
            this.isTabChanged = null;
            this.isConfirmClose = false;
            setTimeout(() => {
              this.onClosed();
            }, 1000);
          }
          if (res.status === '226') {
            this.messageService.add({
              key: 'addSleeve',
              severity: 'warn',
              detail: this.translation.translate('validate.error.sleeve.code1') + this.formAdd.value.sleeveCode +
                this.translation.translate('validate.error.sleeve.code2')
            });
          }
          if (res.status === '404' || res.status === '500' || res.status === '400') {
            this.messageService.add({
              key: 'addSleeve',
              severity: 'error',
              summary: this.translation.translate('common.message.error.system.save'),
            });
            this.app.messageProcess(res.status, res.content);
          }
        }, error => {
          this.messageService.add({
            key: 'addSleeve',
            severity: 'error',
            summary: this.translation.translate('common.message.error.system.save'),
          });
        });
      }
    }
  }

  checkWarning(e: FormControl) {
    if (this.statusCheck) {
      if (!e.valid) {
        return true;
      }
    }
    return false;
  }
  // lay data cua field khi moi vao
  getFieldOnStart() {
    this.listField = [this.formAdd.value.sleeveCode, this.formAdd.value.sleeveTypeList,
    this.formAdd.value.pillarId, this.formAdd.value.installationDate,
    this.formAdd.value.ownerId, this.formAdd.value.poolId, this.formAdd.value.updateTime,
    this.formAdd.value.vendorId, this.formAdd.value.laneName, this.formAdd.value.serial,
    this.formAdd.value.purpose, this.formAdd.value.deptPath, this.formAdd.value.sleeveIndex,
    this.formAdd.value.status, this.formAdd.value.note];
  }

  // check thay doi du lieu cua field
  checkFieldOnClose() {
    const listField: String[] = [this.formAdd.value.sleeveCode, this.formAdd.value.sleeveTypeList,
    this.formAdd.value.pillarId, this.formAdd.value.installationDate,
    this.formAdd.value.ownerId, this.formAdd.value.poolId, this.formAdd.value.updateTime,
    this.formAdd.value.vendorId, this.formAdd.value.laneName, this.formAdd.value.serial,
    this.formAdd.value.purpose, this.formAdd.value.deptPath, this.formAdd.value.sleeveIndex,
    this.formAdd.value.status, this.formAdd.value.note];
    for (let i = 0; i < listField.length; i++) {
      if (listField[i] !== this.listField[i]) {
        return true;
      }
    }
    return false;
  }

  onClosed() {
    if ('pool' === this.pillarPoolDetail) {
      this.eventBusService.emit({
        type: {
          type: 'poolSleeveList',
          tabId: this.formAdd.value.holderId
        },
      });
    } else if ('pillar' === this.pillarPoolDetail) {
      this.eventBusService.emit({
        type: {
          type: 'pillarSleeveList',
          tabId: this.formAdd.value.holderId
        },
      });
    } else {
      this.eventBusService.emit({
        type: {
          type: 'sleeveList',
        },
      });
    }
    if (this.checkFieldOnClose() && this.isConfirmClose) {
      this.isConfirmClose = true;
      this.messageService.clear('addSleeve');
      this.messageService.clear('success');
      this.displayConfirmClose = true;
    } else {
      this.onConfirm();
    }
  }

  onConfirm() {
    this.messageService.clear('addSleeve');
    this.messageService.clear('success');
    this.displayConfirmClose = false;
    if ('pool' === this.pillarPoolDetail) {
      'save' === this.action ? this.tabLayoutService.close({
        component: 'SleeveSaveComponent',
        header: '',
      }) : this.tabLayoutService.close({
        component: 'SleeveSaveComponent',
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
      this.tabLayoutService.open({
        component: 'PoolDetailComponent',
        header: 'pool.manage.detail.label',
        action: 'view',
        tabId: this.formAdd.value.holderId,
      });
    } else if ('pillar' === this.pillarPoolDetail) {
      'save' === this.action ? this.tabLayoutService.close({
        component: 'SleeveSaveComponent',
        header: '',
      }) : this.tabLayoutService.close({
        component: 'SleeveSaveComponent',
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
      this.tabLayoutService.open({
        component: 'PillarDetailComponent',
        header: 'pillar.manage.detail.label',
        action: 'view',
        tabId: this.formAdd.value.holderId,
      });
    } else {
      'save' === this.action ? this.tabLayoutService.close({
        component: 'SleeveSaveComponent',
        header: '',
      }) : this.tabLayoutService.close({
        component: 'SleeveSaveComponent',
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
      this.tabLayoutService.open({
        component: 'SleeveListComponent',
        header: 'infra.sleeves.management',
        breadcrumb: [
          { label: this.translation.translate('infra.sleeves.management') }
        ]
      });
    }
  }

  onReject(key) {
    this.messageService.clear(key);
    this.displayConfirmClose = false;
  }

  get f() {
    return this.formAdd.controls;
  }

  getIndexMax() {
    this.sleeveService.listSleeve().subscribe(res => {
      this.sleeveService.listData = res.content.listData;
      this.indexMax = this.sleeveService.listData;
      const lstIndex: any[] = [];
      for (let i = 0; i < this.indexMax.length; i++) {
        if (this.indexMax[i].sleeveCode.includes(this.laneName) && this.indexMax[i].sleeveCode.includes('.')) {
          lstIndex.push(this.indexMax[i].sleeveCode.split('.')[1]);
        }
      }
      if (Math.max(...lstIndex) < 999) {
        if (lstIndex.length === 0) {
          this.sleeveIndex = '001';
        } else if (Math.max(...lstIndex) < 99) {
          if (Math.max(...lstIndex) < 9) {
            this.sleeveIndex = '00'.concat(String(Math.max(...lstIndex) + 1));
          } else {
            this.sleeveIndex = '0'.concat(String(Math.max(...lstIndex) + 1));
          }
        } else {
          this.sleeveIndex = Math.max(...lstIndex) + 1;
        }
      } else {
        this.sleeveIndex = '';
      }
      if ('' !== this.sleeveIndex && this.formAdd.value.laneCode !== null && undefined !== this.formAdd.value.laneCode) {
        this.sleeveCode = this.sleeveCode.concat(this.formAdd.value.laneCode.concat('.').concat(this.sleeveIndex));
      } else {
        this.sleeveCode = this.formAdd.value.laneCode;
      }
      this.formAdd.patchValue({ laneCode: this.laneName });
      this.formAdd.patchValue({ sleeveCode: this.sleeveCode });
      this.formAdd.patchValue({ sleeveIndex: this.sleeveIndex });
    });
  }

  autoCompleteIndex() {
    if (this.action === 'save') {
      this.sleeveCode = '';
      this.sleeveIndex = String(this.formAdd.value.sleeveIndex);
      if (this.sleeveIndex.length < 2) {
        this.sleeveIndex = this.sleeveIndex.concat('0');
      }
      if (this.sleeveIndex.length < 3) {
        this.sleeveIndex = this.sleeveIndex.concat('0');
      }
      if (this.laneName !== '' && this.laneName != null && this.laneName !== undefined) {
        this.sleeveCode = this.laneName.concat('.').concat(this.sleeveIndex);
      } else {
        this.sleeveIndex = '';
      }
      if (this.sleeveIndex.length === 2) {
        this.sleeveIndex = '';
      }
      if (this.sleeveIndex === '') {
        this.sleeveCode = '';
      }
      if (0 === Number(this.sleeveIndex)) {
        this.sleeveIndex = '';
        this.sleeveCode = this.laneName;
      }
      this.formAdd.patchValue({ sleeveCode: this.sleeveCode });
      this.formAdd.patchValue({ sleeveIndex: this.sleeveIndex });
    }
  }

  onChangeRowSelectPillarPool(event: any, type?: string) {
    if (type === 'pillar') {
      this.eventBusService.emit({
        type: 'pillar',
        pillarDataObj: event
      });
    } else if (type === 'pool') {
      this.eventBusService.emit({
        type: 'pool',
        poolDataObj: event
      });
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter' || event.code === 'NumpadEnter') {
      this.eventBusService.dataChange.subscribe(val => {
        if (val) {
          if (this.sleeveService.action === 'edit') {
            if (val.data.component === SleeveSaveComponent.name && (val.data.tabId && val.data.tabId === this.tabId)) {
              if (!this.isFocus) {
                this.onSubmit();
              }
            }
          } else if (this.sleeveService.action === 'save') {
            if (val.data.component === SleeveSaveComponent.name) {
              if (!this.isFocus) {
                this.onSubmit();
              }
            }
          }
        }
      }).unsubscribe();
    }
  }

  parseDate(dateString: string): Date {
    if (dateString) {
      return new Date(dateString);
    }
    return null;
  }

  onFocus() {
    this.isFocus = true;
  }
  onBlur() {
    this.isFocus = false;
  }

  onCleanAutoComplete() {
    this.autoCompleteLane.displayField = { laneCode: '' };
    this.formAdd.patchValue({laneName: ''});
  }
}
