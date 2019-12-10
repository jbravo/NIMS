import { Component, ElementRef, HostListener, Input, OnDestroy, OnInit, Type, ViewChild } from '@angular/core';
import { CommonUtils } from '@app/shared/services';
import { FormGroup } from '@angular/forms';
import { AppComponent } from '@app/app.component';
import { TranslationService } from 'angular-l10n';
import { CAT_ITEM, COLS_TABLE, SLEEVE_PURPOSE, SLEEVE_STATUS } from '@app/shared/services/constants';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { SleeveService } from '../../service/sleeve.service';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MenuItem, Message, MessageService } from 'primeng/api';
import { environment } from '@env/environment';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import { PermissionService } from '@app/shared/services/permission.service';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchLocationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchPillarModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-pillar-modal/autocomplete-search-pillar-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchPoolModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-pool-modal/autocomplete-search-pool-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchLaneSleeveModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-lane-sleeve-modal/autocomplete-search-lane-sleeve-modal.component';
import { PoolService } from '@app/modules/pools-management/service/pool.service';
import { PillarService } from '@app/modules/pillars-mgmt/service/pillar.service';
import { Subscription } from 'rxjs';
import { Table } from 'primeng/table';
import { saveAs } from 'file-saver';
import { ContextMenu } from 'primeng/primeng';
import { HrStorage } from '@app/core/services/auth/HrStorage';
import { SysGridViewService } from '@app/shared/services/sys-grid-view.service';
import { UploadControlComponent } from '@app/shared/components/upload-control/upload-control.component';

@Component({
  selector: 'sleeve-list',
  templateUrl: './sleeve-list.component.html',
  styleUrls: ['./sleeve-list.component.css']
})
export class SleeveListComponent implements OnInit, OnDestroy {
  @Input() hideSearch: boolean;
  @Input() parentPool: any;
  @Input() parentPillar: any;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dt') dt: Table;
  @ViewChild('fileF') fileF: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;
  @ViewChild('contextMenu') contextMenu: ContextMenu;
  advanceSearch = false;
  formSearch: FormGroup;
  filteredDeptName: any[];
  filteredPillarName: any[];
  filteredPoolName: any[];
  filteredLocation: any[];
  filteredLaneName: any[];
  holderIdList: any[];
  sleeveTypeList: any[];
  purposeName: any[];
  statusName: any[];
  ownerNameList: any[];
  vendorNameList: any[];
  first: number;
  rows: number;
  last: number;
  cols: any[];
  resultList: any = [];
  selectedResult: any = [];
  dataSearchList: any;
  deptId: number;

  isRemoveUnit = true;
  isRemovePillar = false;
  isRemovePool = false;
  isRemoveLane = true;
  isCheckPool = false;
  isCheckPillar = false;
  isSelectPillar = true;
  isSelectPool = true;
  isLaneSearch = true;

  isSelectedPurpose = false;
  selectedColumns: any[];
  environment = environment;
  disabledLocationModal = true;
  innerWidth;
  innerHeight;
  isSearch = false;
  isShowColumn = true;
  selectedRowData;
  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  warningMessDeleteReSleeve;
  isShowColumns = true;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  laneSleeve: Type<any> = AutocompleteSearchLaneSleeveModalComponent;
  pillar: Type<any> = AutocompleteSearchPillarModalComponent;
  pool: Type<any> = AutocompleteSearchPoolModalComponent;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLaneSleeve = this.app.translation.translate('common.dialog.header.laneCode');
  headerPillar = this.app.translation.translate('common.dialog.header.pillar');
  headerPool = this.app.translation.translate('common.dialog.header.pool');
  private reloadSleeve: Subscription;
  // declaration for import
  importDialog: any;
  file: any;
  fileEdit: any;
  resultImportAdd: any;
  resultImportEdit: any;
  sleeveInPage = 'sleeveList';

  items: MenuItem[];
  isCollapse = false;
  isDisablePurpose = false;
  hrStorage: any;

  constructor(
    private app: AppComponent,
    private sleeveService: SleeveService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private tabLayoutService: TabLayoutService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private poolService: PoolService,
    private pillarService: PillarService,
    private sysGridViewService: SysGridViewService) {
    this.buildForm({});
  }

  ngOnInit() {

    this.hrStorage = HrStorage.getUserToken();
    // get Cot theo user
    const columns = this.hrStorage.sysGridView;
    this.cols = COLS_TABLE.SLEEVE;
    // translate header
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    // set cot theo user
    this.selectedColumns = this.cols.filter((elem) => columns.find(data => {
      return elem.field === data.columnName && data.gridViewName === 'sleeve';
    }));
    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }
    // this.cols = COLS_TABLE.SLEEVE;
    // this.hideColumn();
    this.purposeName = this.dataCommon.getDropDownList(SLEEVE_PURPOSE);
    this.statusName = this.dataCommon.getDropDownList(SLEEVE_STATUS);
    this.setInnerWidthHeightParameters();
    this.getListSleeve();
    this.getOwnerName();
    this.getVandorName();
    this.getSleeveTypeList();

    if (this.parentPillar) {
      this.sleeveInPage = 'pillarSleeveList';
    } else if (this.parentPool) {
      this.sleeveInPage = 'poolSleeveList';
    }
    this.reloadSleeve = this.eventBusService.reloadSleeveChange.subscribe(val => {
      if (val && val.type) {
        this.dt.reset();
      }
    });
  }

  ngOnDestroy(): void {
    CommonUtils.setColumns('sleeve', this.selectedColumns, this.sysGridViewService);
    if (this.reloadSleeve) {
      this.reloadSleeve.unsubscribe();
    }
  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      basicInfo: [''],
      sleeveCode: [''],
      deptId: [''],
      locationName: [''],
      sleeveTypeId: [''],
      serial: [''],
      laneCode: [''],
      holderId: [''],
      purpose: [''],
      pillarCode: [undefined],
      poolCode: [undefined],
      location: [''],
      first: [''],
      rows: [''],
      // ----------------- sortfield
      sortField: [''],
      sortOrder: [''],
      keySort: [''],
      longitude: [''],
      latitude: [''],
      sortName: [''],
      sSleeveCode: [''],
      sSleeveTypeId: [''],
      sPillarCode: [''],
      sPoolCode: [''],
      sLaneName: [''],
      sDeptPath: [''],
      sPurpose: [''],
      sStatus: [''],
      note: [''],
      sSerial: [''],
      ownerName: [''],
      vendorName: [''],
      installationDate: [''],
      updateTime: [''],
      // ----------------- search like-keyup
      likePillarCode: [''],
      likePoolCode: [''],
      likeDeptPath: [''],
      likeLaneCode: ['']
    });
  }

  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    // this.rows = event.rows;
    this.formSearch.controls['first'].setValue(event.first);
    this.formSearch.controls['rows'].setValue(event.rows);
    this.formSearch.controls['sortField'].setValue(event.sortField);
    this.formSearch.controls['sortOrder'].setValue(event.sortOrder);
    // create by vanba - ThieuNV start
    if (this.hideSearch) {
      this.formSearch.controls['basicInfo'].setValue(null);
      this.formSearch.controls['pillarCode'].setValue(this.parentPillar ? (this.parentPillar.pillarCode ? this.parentPillar.pillarCode : '') : '');
      this.formSearch.controls['poolCode'].setValue(this.parentPool ? (this.parentPool.poolCode ? this.parentPool.poolCode : '') : '');
      this.advanceSearchFunc(this.first);
    } else
      // create by vanba - ThieuNV end
      if (this.isSearch) {
        this.formSearch.controls['basicInfo'].setValue(null);
        this.sleeveService.findAdvanceSleeve(this.formSearch.value).subscribe(res => {
          this.resultList = res.content;
          res.content.listData.forEach(item => {
            this.convertStatusAndPurposeToString(item);
          });
        });
      }
    this.isSearch = true;
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.260;
  }

  get formControls() {
    return this.formSearch.controls;
  }

  // setter start
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formSearch.controls[element].setValue(event.value.value);
      if (element === 'purpose' && event.value.value != null) {
        this.isDisablePurpose = true;
      } else {
        this.isDisablePurpose = false;
      }
    } else {
      this.formSearch.controls[element].setValue('');
    }
  }

  onInputSearch(event, element: string) {
    this.formSearch.controls['keySort'].setValue('true');
    const keyInput = event.target.value;
    this.formSearch.controls[element].setValue(keyInput);

    if ('sPillarCode' === element) {
      this.formSearch.controls['likePillarCode'].setValue(keyInput);
    }
    if ('sPoolCode' === element) {
      this.formSearch.controls['likePoolCode'].setValue(keyInput);
    }
    if ('sDeptPath' === element) {
      this.formSearch.controls['likeDeptPath'].setValue(keyInput);
    }
    if ('sLaneName' === element) {
      this.formSearch.controls['likeLaneCode'].setValue(keyInput);
    }

    this.advanceSearchFunc();
    this.selectedResult = [];
  }

  onClearCalenDa(element: string) {
    this.formSearch.controls[element].setValue('');
    this.formSearch.controls['keySort'].setValue(element);
    this.advanceSearchFunc();
    this.selectedResult = [];
  }

  setValueAndSearch(event, element: string) {
    if ('sPillarCode' === element) {
      this.formSearch.controls['likeLaneCode'].setValue('');
    }
    if ('sPoolCode' === element) {
      this.formSearch.controls['likePoolCode'].setValue('');
    }
    if ('sDeptPath' === element) {
      this.formSearch.controls['likeDeptPath'].setValue('');
    }
    if ('sLaneName' === element) {
      this.formSearch.controls['likeLaneCode'].setValue('');
    }
    if ('sSleeveTypeId' === element) {
      this.formSearch.controls[element].setValue(event.value.value);
    } else if ('sPurpose' === element) {
      this.formSearch.controls[element].setValue(event.value);
    } else if ('sStatus' === element) {
      this.formSearch.controls[element].setValue(event.value);
    } else if ('ownerName' === element) {
      this.formSearch.controls[element]
        .setValue(this.translation.translate('common.label.cboSelect') === event.value.label ? '' : event.value.label);
    } else if ('vendorName' === element) {
      this.formSearch.controls[element]
        .setValue(this.translation.translate('common.label.cboSelect') === event.value.label ? '' : event.value.label);
    } else {
      this.formSearch.controls[element].setValue(event);
    }
    this.formSearch.controls['keySort'].setValue(element);
    this.formSearch.controls['first'].setValue(0);
    this.advanceSearchFunc();
    this.selectedResult = [];
  }

  setFilteredDeptName(filteredDeptName) {
    this.filteredDeptName = filteredDeptName;
  }

  setFilteredPillarName(filteredPillarName) {
    this.filteredPillarName = filteredPillarName;
  }

  setFilteredPoolName(filteredPoolName) {
    this.filteredPoolName = filteredPoolName;
  }

  setFilteredLaneName(filteredLaneName) {
    this.filteredLaneName = filteredLaneName;
  }

  // setter end

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  // don vi
  filteredDeptFunc(event) {
    const query = event.query;
    this.setFilteredDeptName(this.filterDeptName(query, this.sleeveService.listData));
  }

  filterDeptName(query, depts: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < depts.length; i++) {
      const dept = depts[i];
      if (null === dept.deptPath) {
        continue;
      }
      if (dept.deptPath.toLowerCase().includes(query.toLowerCase())) {
        filtered.push(dept);
      }
    }
    const result = [];
    const map = new Map();
    for (const item of filtered) {
      if (!map.has(item.deptPath)) {
        map.set(item.deptPath, true);
        result.push(item.deptPath);
      }
    }
    return result;
  }

  // cot chua mang xong
  filteredPillarFunc(event) {
    const query = event.query;
    this.sleeveService.getDataSearchAdvance().subscribe(pillar => {
      this.setFilteredPillarName(this.filterPillarName(query, pillar.content.listData));
    });
  }

  filterPillarName(query, pillars: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < pillars.length; i++) {
      const pi = pillars[i];
      if (null === pi.pillarCode) {
        continue;
      }
      if (pi.pillarCode.toLowerCase().includes(query.toLowerCase())) {
        filtered.push(pi);
      }
    }
    const result = [];
    const map = new Map();
    for (const item of filtered) {
      if (!map.has(item.pillarCode)) {
        map.set(item.pillarCode, true);
        result.push(item.pillarCode);
      }
    }
    return result;
  }

  // be chua mang xong
  filteredPoolFunc(event) {
    const query = event.query;
    this.sleeveService.getDataSearchAdvance().subscribe(pool => {
      this.setFilteredPoolName(this.filterPoolName(query, pool.content.listData));
    });
  }

  filterPoolName(query, pools: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < pools.length; i++) {
      const po = pools[i];
      if (null === po.poolCode) {
        continue;
      }
      if (po.poolCode.toLowerCase().includes(query.toLowerCase())) {
        filtered.push(po);
      }
    }
    const result = [];
    const map = new Map();
    for (const item of filtered) {
      if (!map.has(item.poolCode)) {
        map.set(item.poolCode, true);
        result.push(item.poolCode);
      }
    }
    return result;
  }

  // tuyen chua mang xong
  filteredLaneFunc(event) {
    const query = event.query;
    this.sleeveService.getDataSearchAdvance().subscribe(lane => {
      this.setFilteredLaneName(this.filterLaneName(query, lane.content.listData));
    });
  }

  filterLaneName(query, lanes: any[]): any[] {
    const filtered: any[] = [];
    for (let i = 0; i < lanes.length; i++) {
      const lane = lanes[i];
      if (null === lane.laneCode) {
        continue;
      }
      if (lane.laneCode.toLowerCase().includes(query.toLowerCase())) {
        filtered.push(lane);
      }
    }
    const result = [];
    const map = new Map();
    for (const item of filtered) {
      if (!map.has(item.laneCode)) {
        map.set(item.laneCode, true);
        result.push(item.laneCode);
      }
    }
    return result;
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

  // danh sach tat ca mang xong
  getListSleeve() {
    this.sleeveService.listSleeve().subscribe(res => {
      this.sleeveService.listData = res.content.listData;
      this.sleevesTypeList(res);
    });
  }

  // danh ma loai mang xong
  getSleeveTypeList() {
    this.sleeveService.getSleeveTypeCodeList().subscribe(res => {
      this.sleeveTypeList = [];
      this.sleeveTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: '' });
      const element: any = res.content.listData;
      for (let i = 0; i < element.length; i++) {
        this.sleeveTypeList.push({ label: element[i].sleeveTypeCode, value: +element[i].sleeveTypeId });
      }
    });
  }

  // hien thi phan tim kiem nang cao
  showAdvance() {
    this.formSearch.controls['basicInfo'].setValue(null);
    this.advanceSearch = true;
  }

  // an phan tim kiem nang cao
  hideAdvance() {
    this.clearFormSearchAdvance();
    this.advanceSearch = false;
  }

  clearFormSearchAdvance() {
    this.formSearch.controls['basicInfo'].setValue('');
    this.formSearch.controls['sleeveTypeId'].setValue('');
    this.formSearch.controls['serial'].setValue('');
    this.formSearch.controls['purpose'].setValue('');
    this.formSearch.controls['pillarCode'].setValue(undefined);
    this.formSearch.controls['poolCode'].setValue(undefined);
    this.formSearch.controls['deptId'].setValue('');
    this.formSearch.controls['laneCode'].setValue('');
    this.formSearch.controls['sleeveCode'].setValue('');
  }

  basicSearchFunc() {
    // if (this.isShowColumn && this.isShowColumns) {
    //   this.showColumn();
    //   this.isShowColumn = false;
    // }
    this.formSearch.controls['first'].setValue(0);
    this.spinner.show();
    this.sleeveService.findBasicSleeve(this.formSearch.value).subscribe(res => {
      this.spinner.hide();
      this.sleevesTypeList(res);
      this.resultList = res.content;
      res.content.listData.forEach(item => {
        this.convertStatusAndPurposeToString(item);
      });
    });
  }

  advanceSearchFunc(first?: number) {
    if (this.formSearch.value.pillarCode instanceof Object) {
      this.formSearch.controls['pillarCode'].setValue(this.formSearch.value.pillarCode.pillarCode);
    } else {
      this.formSearch.controls['pillarCode'].setValue(this.formSearch.value.pillarCode);
    }
    if ('' === this.formSearch.value.pillarCode) {
      this.formSearch.controls['pillarCode'].setValue(null);
    }
    if (this.formSearch.value.poolCode instanceof Object) {
      this.formSearch.controls['poolCode'].setValue(this.formSearch.value.poolCode.poolCode);
    } else {
      this.formSearch.controls['poolCode'].setValue(this.formSearch.value.poolCode);
    }
    if ('' === this.formSearch.value.poolCode) {
      this.formSearch.controls['poolCode'].setValue(null);
    }
    if (this.formSearch.value.deptId instanceof Object) {
      this.formSearch.controls['deptId'].setValue(this.formSearch.value.deptId.deptId ? this.formSearch.value.deptId.deptId : '');
    } else {
      this.formSearch.controls['deptId'].setValue(this.formSearch.value.deptId ? this.formSearch.value.deptId : '');
    }
    if (this.formSearch.value.laneCode instanceof Object) {
      this.formSearch.controls['laneCode'].setValue(this.formSearch.value.laneCode.laneCode ? this.formSearch.value.laneCode.laneCode : '');
    } else {
      this.formSearch.controls['laneCode'].setValue(this.formSearch.value.laneCode ? this.formSearch.value.laneCode : '');
    }
    // if (this.isShowColumns && this.isShowColumn) {
    //   this.showColumn();
    //   this.isShowColumns = false;
    // }
    if (first) {
      this.formSearch.controls['first'].setValue(first);
    } else {
      this.formSearch.controls['first'].setValue(0);
    }
    this.spinner.show();
    this.sleeveService.findAdvanceSleeve(this.formSearch.value).subscribe(res => {
      this.spinner.hide();
      res.content.listData.forEach(item => {
        this.convertStatusAndPurposeToString(item);
      });
      this.resultList = res.content;
    });
  }

  onRowSelect(event: any, template?: any) {
    // u can do something else with the data
    console.log('vtData : ', event);
    console.log(this.selectedResult);
  }

  onRowUnselect(event: any) {
    // simply logging the event
    console.log('row unselect : ', event);
  }

  onRemoveDeptAutocomplete() {
    this.formSearch.controls['deptId'].setValue('');
    this.disabledLocationModal = true;
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

  saveOrEdit(action?: string, id?: number) {
    // create by vanba - ThieuNV
    if (this.parentPool) {
      this.poolService.id = this.parentPool.poolId;
      this.poolService.poolCode = this.parentPool.poolCode;
      this.poolService.poolObj = this.parentPool;
    } else if (this.parentPillar) {
      this.pillarService.id = this.parentPillar.pillarId;
      this.pillarService.pillarCode = this.parentPillar.pillarCode;
    }
    // create by vanba - ThieuNV
    this.sleeveService.id = id;
    this.sleeveService.action = action;
    if ('edit' === action) {
      this.tabLayoutService.open({
        component: 'SleeveSaveComponent',
        header: 'sleeve.manage.update.label',
        action: 'edit',
        tabId: id,
        breadcrumb: [
          { label: this.translation.translate('infra.sleeves.management') },
          { label: this.translation.translate('sleeve.manage.update.label') }
        ]
      });
    } else {
      this.tabLayoutService.open({
        component: 'SleeveSaveComponent',
        header: 'sleeve.manage.create.label',
        breadcrumb: [
          { label: this.translation.translate('infra.sleeves.management') },
          { label: this.translation.translate('sleeve.manage.create.label') }
        ]
      });
    }
  }

  onChangeCols(event) {
    // sort asc theo dung thu tu cot (theo id)
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  onDelete(selectedRowData, selectedRowDataList) {
    this.selectedRowData = selectedRowData;
    this.selectedResult = selectedRowDataList;
    if (this.selectedRowData || (this.selectedResult && this.selectedResult.length > 0)) {
      this.displayConfirmDelete = true;
      setTimeout(() => {
        this.searchElement.nativeElement.focus();
      }, 0);
      // tslint:disable-next-line:max-line-length
    } else if ((this.selectedResult && this.selectedResult.length === 0 || this.selectedResult === undefined || this.selectedResult === null)) {
      this.displayConfirmDelete = false;
      this.messageService.add({
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('sleeves.delete.warn.delete.multipe')
      });
    }
  }

  onSubmitDelete() {
    if (this.selectedRowData) {
      const selectedRowData = [{
        sleeveId: this.selectedRowData.sleeveId,
        sleeveCode: this.selectedRowData.sleeveCode
      }];
      this.spinner.show();
      this.sleeveService.delete(selectedRowData).subscribe(res => {
        this.spinner.hide();
        const deleteRefWeldSleeveData = res.data.weldSleeve;
        if (deleteRefWeldSleeveData && deleteRefWeldSleeveData.length > 0) {
          this.warningMessDeleteReSleeve = deleteRefWeldSleeveData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = [];
          this.advanceSearchFunc();
        }
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      const selectedRowDataList = [];
      this.selectedResult.forEach(it => {
        selectedRowDataList.push({
          sleeveId: it.sleeveId,
          sleeveCode: it.sleeveCode
        });
      });
      this.spinner.show();
      this.sleeveService.delete(selectedRowDataList).subscribe(res => {
        this.spinner.hide();
        const deleteRefWeldSleeveData = res.data.weldSleeve;
        if (deleteRefWeldSleeveData && deleteRefWeldSleeveData.length > 0) {
          this.warningMessDeleteReSleeve = deleteRefWeldSleeveData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = [];
          this.advanceSearchFunc();
        }
      });
    }
  }

  onHideWarningDelete() {
    this.displayWarningMessDelete = false;
    this.warningMessDeleteReSleeve = undefined;
  }

  deleteMultiple() {
    if (this.selectedResult.length > 0) {
      this.messageService.clear();
      this.messageService.add({
        key: 'k',
        sticky: true,
        summary: this.translation.translate('common.message.confirm'),
        detail: this.translation.translate('common.confirm.delete'),
        data: this.selectedResult
      });
    } else {
      this.messageService.add({

        severity: 'warn', key: 'align',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('sleeves.delete.warn.delete.multipe')
      });
    }
  }

  onConfirm(item) {
    this.messageService.clear('k');
    if (!(item instanceof Array)) {
      item = [item];
    }
    this.sleeveService.delete(item).subscribe(res => {
      if (res.data.code === 1) {
        this.messageService.add({
          severity: 'success',
          key: 'toa',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('message.success.deleted.success')
        });
        this.advanceSearchFunc();
      } else {
        let weldSleeve = '';
        const ms: Message = {};
        ms.severity = 'error';
        ms.key = 'align';
        ms.summary = this.translation.translate('common.label.NOTIFICATIONS');
        ms.detail = '';
        for (let i = 0; i < res.data.weldSleeve.length; i++) {
          if (res.data.weldSleeve && res.data.weldSleeve.length > 0) {
            if (i < res.data.weldSleeve.length - 1) {
              weldSleeve += res.data.weldSleeve[i] + ' ] [ ';
            } else {
              weldSleeve += res.data.weldSleeve[i];
            }
          } else {
            this.messageService.add({
              severity: 'error',
              key: 'align',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('sleeves.delete.error')
            });
          }
        }
        ms.detail += this.translation.translate('sleeves.delete.error.weld.sleeve1') + weldSleeve +
          this.translation.translate('sleeves.delete.error.weld.sleeve2');
        this.messageService.add(ms);
      }
    });
  }

  onReject(key) {
    this.messageService.clear(key);
    if ('delete') {
      this.displayConfirmDelete = false;
    }
  }

  clear() {
    this.messageService.clear();
  }

  showDetail(data, action) {
    this.sleeveService.item = data;
    this.sleeveService.action = action;
    if ('view' === action) {
      this.tabLayoutService.open({
        component: 'SleeveDatailComponent',
        header: 'sleeve.manage.detail.label',
        action: 'view',
        tabId: data.sleeveId,
        breadcrumb: [
          { label: this.translation.translate('infra.sleeves.management') },
          { label: this.translation.translate('sleeve.manage.detail.label') }
        ]
      });
    }
  }

  // An bot column trong gripView
  hideColumn() {
    this.selectedColumns = [];
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
      if (col.field === 'sleeveCode' || col.field === 'sleeveTypeCode' || col.field === 'pillarCode'
        || col.field === 'poolCode' || col.field === 'laneCode' || col.field === 'deptPath'
        || col.field === 'location' || col.field === 'purposeName' || col.field === 'note'
        || col.field === 'installation' || col.field === 'modifyDate') {
        this.selectedColumns.push(col);
      }
    });
  }

  // Hien thi them column trong gripView
  showColumn() {
    this.selectedColumns = [];
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
      if (col.field === 'sleeveCode' || col.field === 'sleeveTypeCode' || col.field === 'pillarCode'
        || col.field === 'poolCode' || col.field === 'laneCode' || col.field === 'deptPath'
        || col.field === 'longitude' || col.field === 'latitude'
        || col.field === 'purposeName' || col.field === 'note'
        || col.field === 'installation' || col.field === 'modifyDate') {
        this.selectedColumns.push(col);
      }
    });
  }

  convertStatusAndPurposeToString(item) {
    if (item.purpose != null) {
      if (item.purpose === 0) {
        item.purposeName = this.translation.translate('infra.sleeves.normal.sleeve');
      }
      if (item.purpose === 1) {
        item.purposeName = this.translation.translate('infra.sleeves.trouble.sleeve');
      }
    }
    if (item.status != null) {
      if (item.status === 1) {
        item.statusName = this.translation.translate('sleeves.status.using');
      }
      if (item.status === 0) {
        item.statusName = this.translation.translate('sleeves.status.non.using');
      }
    }
  }

  sleevesTypeList(res) {
    if (res.content != null) {
      const resultListSleeveType = [];
      this.dataSearchList = res;
      resultListSleeveType.push({ label: this.translation.translate('common.label.cboSelect'), value: '' });
      res.content.listData.forEach(element => {
        resultListSleeveType.push(({ label: element.sleeveTypeCode, value: element.sleeveTypeId }));
      });
    }
  }

  exportExcel() {
    this.spinner.show();
    if (this.selectedResult.length > 0) {
      this.sleeveService.excelChose(this.selectedResult).subscribe(s => {
        this.spinner.hide();
        if (s.body.size === 0) {
          this.messageService.add({
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('common.label.noDataExport')
          });
          return;
        }
        saveAs(s.body, s.headers.get('File'));
      });
    } else {
      this.formSearch.value.first = 0;
      this.formSearch.value.rows = this.resultList.totalRecords ? this.resultList.totalRecords : 0;
      this.sleeveService.export(this.formSearch.value).subscribe(s => {
        this.spinner.hide();
        if (s.body.size === 0) {
          this.messageService.add({
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('common.label.noDataExport')
          });
          return;
        }
        saveAs(s.body, s.headers.get('File'));
      });
    }
  }

  changeFile(event, type) {
    if (type === 1) {
      this.file = event;
    } else {
      this.fileEdit = event;
    }
  }

  downloadFileTemplate(type: any) {
    if (type === 1) {
      this.sleeveService.downloadFileTemplate(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    } else {
      this.sleeveService.downloadFileTemplateEdit(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    }
  }

  downloadResult(path) {
    if (path) {
      this.sleeveService.downloadFile(path).subscribe(res => {
        if (res.body.size === 0) {
          this.app.warningMessage('messages.warn.common.download');
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    } else {
      this.app.errorMessage('partymember.download.filebug.notexits');
    }
  }

  closeImportPopup() {
    this.fileF.setEmptyFile();
    this.fileE.setEmptyFile();
    this.importDialog = false;
    this.file = null;
    this.fileEdit = null;
    this.resultImportAdd = null;
    this.resultImportEdit = null;
  }

  doImport(type?: number) {
    if (type === 1) {
      this.spinner.show();
      if (this.file) {
        this.sleeveService.importSleeve(this.file).subscribe(res => {
          this.spinner.hide();
          if (res.status === 0) {
            this.messageService.add({
              key: 'copy',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.error.file.type')
            });
            return;
          }
          if (res.data == null) {
            this.messageService.add({
              key: 'copy',
              severity: 'warning',
              summary: '',
              detail: this.translation.translate('station.import.error')
            });
          } else {
            const path = res.data.split('+', 3);
            this.resultImportAdd = path[0];
            const success = +path[1];
            const err = +path[2];
            if (err === 0 && success !== 0) {
              this.messageService.add({
                key: 'copy',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', { success: success })
              });
              this.advanceSearchFunc();
            } else {
              this.messageService.add({
                key: 'copy',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.warning', { success: success, error: err })
              });
            }
            this.onReject('import');
          }
        }, error => {
          this.spinner.hide();
          this.messageService.add({
            key: 'copy',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'copy',
          severity: 'warn',
          summary: '',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    } else {
      if (this.fileEdit) {
        this.sleeveService.editSleeve(this.fileEdit).subscribe(res => {
          this.spinner.hide();
          if (res.status === 0) {
            this.messageService.add({
              key: 'copy',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.error.file.type')
            });
            return;
          }
          if (res.data == null) {
            this.messageService.add({
              key: 'copy',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error')
            });
          } else {
            const path = res.data.split('+', 3);
            this.resultImportEdit = path[0];
            const success = +path[1];
            const err = +path[2];
            if (err === 0 && success !== 0) {
              this.messageService.add({
                key: 'copy',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', { success: success })
              });
              this.advanceSearchFunc();
            } else {
              this.messageService.add({
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.warning', { success: success, error: err })
              });
            }
            this.onReject('import');
          }
        }, error => {
          this.spinner.hide();
          this.messageService.add({
            key: 'copy',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'copy',
          severity: 'warn',
          summary: '',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    }
  }

  showDialog() {
    this.importDialog = true;
  }

  onLinkRightClickedHeader(content: string, event: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          { label: this.translation.translate('common.label.show.a.little'), icon: 'fa fa-compress', command: () => this.onCollapse() },
          { label: this.translation.translate('common.label.show.all'), icon: 'fa fa-expand', command: () => this.onCollapse('expend') }
        );
        this.contextMenu.model = this.items;
        this.contextMenu.show(event);
      }
    }
  }

  onLinkRightClickedRow(content: string, event: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          { label: this.translation.translate('common.label.show.a.little'), icon: 'fa fa-compress', command: () => this.onCollapse() },
          { label: this.translation.translate('common.label.show.all'), icon: 'fa fa-expand', command: () => this.onCollapse('expend') },
          { label: this.translation.translate('common.label.copy'), icon: 'fa fa-clipboard', command: () => this.onCopy(content) }
        );
        this.contextMenu.model = this.items;
        this.contextMenu.show(event);
      }
    }
  }

  onCollapse(type?: string) {
    this.isCollapse = type === 'expend';
  }

  onCopy(event) {
    if (event) {
      this.messageService.add({ key: 'copy', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied') });
      this.items = [];
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter' || event.code === 'NumpadEnter') {
      this.eventBusService.dataChange.subscribe(val => {
        if (val) {
          if (val.data.key === SleeveListComponent.name) {
            this.advanceSearchFunc();
          }
        }
      }).unsubscribe();
    }
  }
}
