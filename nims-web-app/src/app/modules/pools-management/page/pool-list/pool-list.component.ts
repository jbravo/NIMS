import {Component, ElementRef, OnDestroy, OnInit, Type, ViewChild} from '@angular/core';
import {CommonUtils} from '@app/shared/services';
import {FormGroup} from '@angular/forms';
import {AppComponent} from '@app/app.component';
import {TranslationService} from 'angular-l10n';
import {CAT_ITEM, COLS_TABLE, STATUS_POOL} from '@app/shared/services/constants';
import {SelectItem} from '@app/modules/controls/common/selectitem';
import {PoolService} from '../../service/pool.service';
import {PermissionService} from '@app/shared/services/permission.service';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {PoolSaveComponent} from '../pool-save/pool-save.component';
import {Table} from 'primeng/components/table/table';
import {MenuItem, MessageService} from 'primeng/api';
import {AutocompleteSearchLocationModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {AutocompleteSearchDepartmentModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {DataCommonService} from '@app/shared/services/data-common.service';
import {NgxSpinnerService} from 'ngx-spinner';
import {PoolDetailComponent} from '../pool-detail/pool-detail.component';
import {environment} from '@env/environment';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {Subscription} from 'rxjs';
import {saveAs} from 'file-saver';
import {ContextMenu} from 'primeng/contextmenu';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';
import {UploadControlComponent} from '@app/shared/components/upload-control/upload-control.component';

@Component({
  selector: 'pool-list',
  templateUrl: './pool-list.component.html',
  styleUrls: ['./pool-list.component.css']
})
export class PoolListComponent implements OnInit, OnDestroy {
  @ViewChild('dt') dt: Table;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('focusExport') focusExportElement: ElementRef;
  @ViewChild('contextMenu') contextMenu: ContextMenu;
  @ViewChild('fileA') fileA: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;

  advanceSearch = false;
  formSearch: FormGroup;
  filteredDept: any[];
  filteredLocation: any[];
  auditStatusList: any[] = [];
  first: number;
  rows: number;
  last: number;
  cols: any[];
  selectedColumns: any[];
  resultList: any = [];
  selectedResult: any = [];
  selectedPoolType: any;
  selectedOwner: any;

  poolTypeList: any[];
  ownerList: any[];
  itemDelete: any[];

  cars: SelectItem[];
  filteredDeptsSingle: any[];
  filteredLocationSingle: any[];
  isDeptAdvance: any = false;
  isLocationAdvance: any = false;
  isBasicSearchFunc: any = false;
  isAdvanceSearchFunc: any = false;
  isSearchType = 0;
  // isFilter: any = false;
  // disabledLocationModal = true;
  environments = environment;
  paramsSearch = {
    first: 0,
    rows: undefined,
    basicInfo: undefined,
    deptId: undefined,
    locationId: undefined,
    poolCode: undefined,
    poolTypeId: undefined,
    deptName: undefined,
    locationName: undefined,
    recordsTotal: undefined,
    address: undefined,
    ownerId: undefined,
    constructionDate: undefined,
    acceptanceDate: undefined,
    deliveryDate: undefined,
    status: undefined,
    note: undefined,
    sortField: undefined,
    sortOrder: undefined
  };
  paramsSearchGRIDVIEW = {
    first: 0,
    rows: undefined,
    basicInfo: undefined,
    deptId: undefined,
    locationId: undefined,
    poolCode: undefined,
    poolTypeId: undefined,
    deptName: undefined,
    locationName: undefined,
    recordsTotal: undefined,
    address: undefined,
    ownerId: undefined,
    constructionDate: undefined,
    acceptanceDate: undefined,
    deliveryDate: undefined,
    status: undefined,
    note: undefined,
    longitude: undefined,
    latitude: undefined,
    longitudeText: undefined,
    latitudeText: undefined
  };
  statusList: any[] = [];
  innerWidth;
  innerHeight;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;

  selectedRowData: any;
  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  displayConfirmExport = false;
  warningMessPool: String[] = [];
  exportAll = true;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLocation = this.app.translation.translate('common.dialog.header.location');
  // import variable
  importDialog: any;
  resultImportAdd: any;
  resultImportEdit: any;
  file: any;
  fileEdit: any;

  private reloadPool: Subscription;
  statusOne = 1;
  statusZero = 0;

  isCollapse = false;
  items: MenuItem[];
  isConfirmDelHang = false;
  hrStorage: any;

  constructor(
    private app: AppComponent,
    private translation: TranslationService,
    private poolService: PoolService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private eventBusService: EventBusService,
    private dataCommon: DataCommonService,
    private spinner: NgxSpinnerService,
    private stationService: StationService,
    private sysGridViewService: SysGridViewService) {
    this.buildForm({});
  }

  ngOnInit() {
    // this.cols = COLS_TABLE.POOL;
    // this.cols.forEach(col => {
    //   col.headerTranslate = this.translation.translate(col.header);
    // });
    // old
    // this.hrStorage = HrStorage.getUserToken();
    // this.selectedColumns = this.hrStorage.sysGridView;
    // const dm = COLS_TABLE.POOL;
    // const colsShow = [];
    // this.cols = [];
    // this.cols = dm.filter((elem) => this.selectedColumns.find(data => {
    //   return elem.field === data.columnName && data.gridViewName === 'pool';
    // }));

    // this.selectedColumns = [];
    // // intit with columns defaul
    // this.cols.forEach(col => {
    //   col.headerTranslate = this.translation.translate(col.header);
    //   if (col.field !== 'constructionDate' && col.field !== 'address' && col.field !== 'longitude'
    //     && col.field !== 'latitude' && col.field !== 'statusName') {
    //     colsShow.push(col);
    //   }
    // });
    // this.selectedColumns = colsShow;
    // old

    // new

    this.hrStorage = HrStorage.getUserToken();
    // get Cot theo user
    const columns = this.hrStorage.sysGridView;
    // this.gridId = this.hrStorage.sysGridView.filter(el =>this.se).gridId;
    this.cols = COLS_TABLE.POOL;

    // translate header
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    // set cot theo user
    this.selectedColumns = this.cols.filter((elem) => columns.find(data => {
      return elem.field === data.columnName && data.gridViewName === 'pool';
    }));
    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }

    // new
    this.getPoolTypeList();
    this.getownerList();
    this.statusList = this.dataCommon.getDropDownList(STATUS_POOL);
    this.setInnerWidthHeightParameters();

    this.reloadPool = this.eventBusService.reloadPoolChange.subscribe(val => {
      if (val) {
        this.dt.reset();
      }
    });
    this.items = [];
  }

  ngOnDestroy(): void {
    if (this.reloadPool) {
      this.reloadPool.unsubscribe();
    }
    CommonUtils.setColumns('pool', this.selectedColumns, this.sysGridViewService);
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.30;
  }

  get formControls() {
    return this.formSearch.controls;
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }


  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      basicInfo: [''],
      poolCode: [''],
      poolTypeId: [''],
      ownerId: [''],
      deptName: [''],
      locationName: [''],
      first: [''],
      recordsTotal: [''],
      rows: [''],
      sortField: [''],
      sortOrder: [''],
      acceptanceDate: [''],
      deliveryDate: [''],
      constructionDate: [''],
      ownerName: [''],
      poolTypeCode: [''],
      status: ['']
    });
  }

  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.formSearch.controls['first'].setValue(event.first);
    this.formSearch.controls['rows'].setValue(event.rows);
    this.paramsSearch.first = event.first;
    this.paramsSearch.rows = event.rows;
    if (event.sortField) {
      // if (event.sortField === 'longitude' || event.sortField === 'latitude') {
      //   return;
      // }
      if (event.sortField === 'statusName') {
        this.paramsSearch.sortField = 'status';
      } else {
        this.paramsSearch.sortField = event.sortField;
      }
      this.paramsSearch.sortOrder = event.sortOrder;
    } else {
      this.paramsSearch.sortField = undefined;
      this.paramsSearch.sortOrder = undefined;
    }
    if (this.isBasicSearchFunc === true) {
      this.selectedResult = [];
      this.paramsSearch.basicInfo = this.formSearch.value.basicInfo;
      this.paramsSearch.poolCode = undefined;
      this.paramsSearch.deptName = undefined;
      this.paramsSearch.locationName = undefined;
      this.paramsSearch.ownerId = undefined;
      this.paramsSearch.poolTypeId = undefined;
      this.paramsSearch.address = undefined;
      this.paramsSearch.note = undefined;
      this.paramsSearch.status = undefined;
      this.paramsSearch.constructionDate = undefined;
      this.paramsSearch.deliveryDate = undefined;
      this.paramsSearch.acceptanceDate = undefined;

      const requestData = [];
      requestData.push(this.paramsSearch);
      requestData.push(this.paramsSearchGRIDVIEW);
      this.spinner.show();
      this.poolService.findAdvancePool(requestData).subscribe(res => {
        this.spinner.hide();
        this.resultList = res.content;
        this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;

        for (let i = 0; i < this.resultList.listData.length; i++) {
          if (this.resultList.listData[i].status === this.statusZero) {
            this.resultList.listData[i].statusName = this.translation.translate('cable.status.zero');
          }
          if (this.resultList.listData[i].status === this.statusOne) {
            this.resultList.listData[i].statusName = this.translation.translate('cable.status.one');
          }
          this.resultList.listData[i].longitude = this.poolService.formatLongLat(this.resultList.listData[i].longitude);
          this.resultList.listData[i].latitude = this.poolService.formatLongLat(this.resultList.listData[i].latitude);
        }
      }, error => {
        this.spinner.hide();
      });
    }

    if (this.isAdvanceSearchFunc === true) {
      this.selectedResult = [];

      this.paramsSearch.basicInfo = undefined;
      this.paramsSearch.address = undefined;
      this.paramsSearch.note = undefined;
      this.paramsSearch.status = undefined;
      this.paramsSearch.note = undefined;
      this.paramsSearch.constructionDate = undefined;
      this.paramsSearch.deliveryDate = undefined;
      this.paramsSearch.acceptanceDate = undefined;
      this.paramsSearch.poolCode = this.formSearch.value.poolCode ? this.formSearch.value.poolCode : undefined;
      this.paramsSearch.deptName = this.formSearch.value.deptName ?
        (this.formSearch.value.deptName.deptName ? this.formSearch.value.deptName.deptName : undefined) : undefined;
      this.paramsSearch.locationName = this.formSearch.value.locationName ?
        (this.formSearch.value.locationName.locationName ? this.formSearch.value.locationName.locationName : undefined) : undefined;
      this.paramsSearch.ownerId = this.formSearch.value.ownerId ? this.formSearch.value.ownerId : undefined;
      this.paramsSearch.poolTypeId = this.formSearch.value.poolTypeId ? this.formSearch.value.poolTypeId : undefined;

      const requestData = [];
      requestData.push(this.paramsSearch);
      requestData.push(this.paramsSearchGRIDVIEW);
      this.spinner.show();
      this.poolService.findAdvancePool(requestData).subscribe(res => {
        this.spinner.hide();
        this.resultList = res.content;
        this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;

        for (let i = 0; i < this.resultList.listData.length; i++) {
          if (this.resultList.listData[i].status === this.statusZero) {
            this.resultList.listData[i].statusName = this.translation.translate('cable.status.zero');
          }
          if (this.resultList.listData[i].status === this.statusOne) {
            this.resultList.listData[i].statusName = this.translation.translate('cable.status.one');
          }
          this.resultList.listData[i].longitude = this.poolService.formatLongLat(this.resultList.listData[i].longitude);
          this.resultList.listData[i].latitude = this.poolService.formatLongLat(this.resultList.listData[i].latitude);
        }
      }, error => {
        this.spinner.hide();
      });
    }
  }


  showAdvance() {
    this.advanceSearch = true;
  }

  hideAdvance() {
    this.advanceSearch = false;
    // this.formSearch.value.deptName = undefined;
    // this.formSearch.value.locationName = undefined;
    this.formSearch.controls['deptName'].setValue(null);
    this.formSearch.controls['locationName'].setValue(null);
  }

  basicSearchFunc() {
    this.isSearchType = 1;
    this.isBasicSearchFunc = true;
    this.isAdvanceSearchFunc = false;
    this.dt.reset();
  }

  get f() {
    return this.formSearch.controls;
  }

  advanceSearchFunc() {
    this.isSearchType = 1;
    this.isBasicSearchFunc = false;
    this.isAdvanceSearchFunc = true;
    this.dt.reset();
  }

  // lay list PoolType
  getPoolTypeList() {
    this.permissionService.getPoolTypeList().subscribe(data => {
      const res = data.content;
      this.poolTypeList = [];
      this.poolTypeList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.poolTypeList.push({label: res[i].poolTypeCode, value: +res[i].poolTypeId});
      }
    });
  }

  getownerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      this.ownerList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  saveOrEdit(id?: number, action?: string) {
    this.poolService.id = id;
    this.poolService.action = action;
    this.tabLayoutService.open({
      component: PoolSaveComponent.name,
      header: id ? 'pool.manage.update.label' : 'pool.manage.create.label',
      breadcrumb: [
        {label: this.translation.translate('pool.header')},
        {label: this.translation.translate(id ? 'pool.manage.update.label' : 'pool.manage.create.label')}
      ],
      action: id ? 'edit' : '',
      tabId: id
    });
  }

  delete(item) {
    this.messageService.clear('DelPools');
    this.messageService.add({
      key: 'DelPools',
      sticky: true,
      severity: 'warn',
      summary: this.translation.translate('pool.confirm.delete'),
      data: item
    });
  }

  deleteMultiple() {
    if (this.selectedResult.length > 0) {
      this.messageService.clear('DelPools');
      this.messageService.add({
        key: 'DelPools',
        sticky: true,
        severity: 'warn',
        summary: this.translation.translate('pool.confirm.delete'),
        data: this.selectedResult
      });
    } else {
      this.messageService.add({
        key: 'rightDown',
        severity: 'error',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('odf.delete.error.warn.noselect')
      });
    }
  }

  onDelete(selectedRowData, selectedRowDataList) {
    this.selectedRowData = selectedRowData;
    this.selectedResult = selectedRowDataList;
    if (this.selectedRowData || (this.selectedResult && this.selectedResult.length > 0)) {
      this.displayConfirmDelete = true;
      setTimeout(() => {
        this.searchElement.nativeElement.focus();
      }, 0);
    } else if ((this.selectedResult && this.selectedResult.length === 0 || this.selectedResult === null)) {
      this.displayConfirmDelete = false;
      this.messageService.add({
        key: 'rightDown',
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('pool.delete.warn.delete.multipe')
      });
    }
  }

  onSubmitDelete() {
    let dataObj;
    this.displayConfirmDelete = false;
    this.isConfirmDelHang = false;
    if (this.selectedRowData) {
      dataObj = [this.selectedRowData];
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      dataObj = this.selectedResult;
    }

    const requestData = [];
    dataObj.forEach(element => {
      requestData.push({
        poolId: element.poolId,
        poolCode: element.poolCode
      });
    });
    this.spinner.show();
    this.poolService.delete(requestData).subscribe(res => {
        this.spinner.hide();
        if (res.content && res.content.length === 0) {
          this.messageService.add({
            key: 'rightDown',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = [];
          this.dt.reset();
        } else if (res.content && res.content.length > 0) {
          this.displayWarningMessDelete = true;
          this.warningMessPool = [];
          for (const pool of res.content) {
            if (pool.other) {
              this.warningMessPool = [];
              this.warningMessPool.push(this.translation.translate('pool.message.error'));
              break;
            } else {
              if (pool.cable && pool.cable.length > 0) {
                let mescable = '';
                for (const a of pool.cable) {
                  mescable += '[' + a + ']; ';
                }
                this.warningMessPool.push(this.translation.translate('pool.message.error.cableOnPool', {
                  listCables: mescable,
                  poolCode: pool.poolCode
                }));
              }
              if (pool.sleeve && pool.sleeve.length > 0) {
                let mesSleeve = '';
                for (const a of pool.sleeve) {
                  mesSleeve += '[' + a + ']; ';
                }
                this.warningMessPool.push(this.translation.translate('pool.message.error.sleeveInPool', {
                  listSleeves: mesSleeve,
                  poolCode: pool.poolCode
                }));
              }
            }
          }
          if (this.warningMessPool.length === 0) {
            this.isConfirmDelHang = true;
            for (const pool of res.content) {
              this.itemDelete = pool.listDeletePoolId;
              let mes = '';
              for (const a of pool.hang) {
                mes += '[' + a + ']; ';
              }
              this.warningMessPool.push(this.translation.translate('pool.message.error.cableHangPool', {
                listCables: mes,
                poolCode: pool.poolCode
              }));
            }
          }
        }

      },
      (error) => {
        this.spinner.hide();
        this.warningMessPool = [];
        this.displayWarningMessDelete = true;
        this.warningMessPool.push(this.translation.translate('pool.message.error'));
      }
    );
  }

  onHideConfirmDelete() {
    this.displayConfirmDelete = false;
  }

  onHideConfirmDeleteHang() {
    this.displayWarningMessDelete = false;
  }

  onSubmitDeleteHang() {
    this.displayWarningMessDelete = false;

    const requestData = [];
    this.itemDelete.forEach(element => {
      requestData.push({
        poolId: element
      });
    });
    this.spinner.show();
    this.poolService.deleteHangConfirm(requestData).subscribe(res => {
        this.spinner.hide();
        this.messageService.add({
          key: 'rightDown',
          severity: 'success',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.delete.success')
        });
        this.selectedResult = [];
        this.dt.reset();
      },
      (error) => {
        this.spinner.hide();
        this.warningMessPool = [];
        this.displayWarningMessDelete = true;
        this.warningMessPool.push(this.translation.translate('pool.message.error'));
      }
    );

  }

  onReject(key?) {
    if (key) {
      this.messageService.clear('DelPools');
    } else {
      this.messageService.clear('DelPools');
    }
  }

  onConfirm(item) {
    this.messageService.clear('DelPools');

    if (!(item instanceof Array)) {
      item = [item];
    }
    const requestData = [];
    item.forEach(element => {
      requestData.push({
        poolId: element.poolId
      });
    });
    this.spinner.show();
    this.poolService.delete(requestData).subscribe(s => {
      this.spinner.hide();
      this.dt.reset();
    });
  }


  showDetail(id, action) {
    this.poolService.id = id;
    this.poolService.action = action;
    //   this.eventBusService.emit({
    //     type: 'poolRowData',
    //     poolDataObj: id,
    //   }
    // );
    this.tabLayoutService.open({
      component: PoolDetailComponent.name,
      header: action === 'edit' ? 'pool.manage.update.label' : action === 'view' ? 'pool.manage.detail.label' : 'pool.manage.create.label',
      breadcrumb: [
        {label: this.translation.translate('pool.header')},
        {
          label: this.translation.translate(action === 'edit' ? 'pool.manage.update.label' :
            action === 'view' ? 'pool.manage.detail.label' : 'pool.manage.create.label')
        }
      ],
      action: action,
      tabId: id
    });
  }

  onChangeRowSelectDept(event: any) {
    this.eventBusService.emit({
      type: 'poolList',
      deptDataObj: event
    });
  }

  onClearRowSelect() {
    this.formSearch.controls['deptName'].setValue(null);
    this.formSearch.controls['locationName'].setValue('');
  }

  copied(e) {
    if (e) {
      this.messageService.add({
        key: 'rightDown', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }

  searchInTable(event, field) {
    if (!this.isBasicSearchFunc && !this.isAdvanceSearchFunc) {
      return;
    }
    this.isSearchType = 2;
    this.paramsSearchGRIDVIEW.rows = this.rows;
    this.selectedResult = [];

    if (field === 'poolCode') {
      this.paramsSearchGRIDVIEW.poolCode = event;
    }
    if (field === 'poolTypeCode') {
      this.paramsSearchGRIDVIEW.poolTypeId = event.value;
    }
    if (field === 'deptName') {
      this.paramsSearchGRIDVIEW.deptName = event;
    }
    if (field === 'locationName') {
      this.paramsSearchGRIDVIEW.locationName = event;
    }
    if (field === 'ownerName') {
      this.paramsSearchGRIDVIEW.ownerId = event;
    }
    if (field === 'address') {
      this.paramsSearchGRIDVIEW.address = event;
    }
    if (field === 'constructionDate') {
      this.paramsSearchGRIDVIEW.constructionDate = event;
    }
    if (field === 'deliveryDate') {
      this.paramsSearchGRIDVIEW.deliveryDate = event;
    }
    if (field === 'acceptanceDate') {
      this.paramsSearchGRIDVIEW.acceptanceDate = event;
    }
    if (field === 'statusName') {
      this.paramsSearchGRIDVIEW.status = event;
    }
    if (field === 'note') {
      this.paramsSearchGRIDVIEW.note = event;
    }
    if (field === 'longitude') {
      if (event === '') {
        this.paramsSearchGRIDVIEW.longitudeText = undefined;
      } else {
        this.paramsSearchGRIDVIEW.longitudeText = event;
      }
      // if (event === '') {
      //   this.paramsSearchGRIDVIEW.longitude = undefined;
      // } else if (event === '0') {
      //   this.paramsSearchGRIDVIEW.longitude = 0;
      // } else if (parseFloat(event)) {
      //   this.paramsSearchGRIDVIEW.longitude = parseFloat(event);
      // } else {
      //   return;
      // }

    }
    if (field === 'latitude') {
      if (event === '') {
        this.paramsSearchGRIDVIEW.latitudeText = undefined;
      } else {
        this.paramsSearchGRIDVIEW.latitudeText = event;
      }
      // if (event === '') {
      //   this.paramsSearchGRIDVIEW.latitude = undefined;
      // } else if (event === '0') {
      //   this.paramsSearchGRIDVIEW.latitude = 0;
      // } else if (parseFloat(event)) {
      //     this.paramsSearchGRIDVIEW.latitude = parseFloat(event);
      //   } else {
      //     return;
      //   }
    }
    const requestData = [];
    requestData.push(this.paramsSearch);
    requestData.push(this.paramsSearchGRIDVIEW);
    this.poolService.findAdvancePool(requestData).subscribe(res => {
      this.resultList = res.content;
      this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;

      for (let i = 0; i < this.resultList.listData.length; i++) {
        if (this.resultList.listData[i].status === this.statusZero) {
          this.resultList.listData[i].statusName = this.translation.translate('cable.status.zero');
        }
        if (this.resultList.listData[i].status === this.statusOne) {
          this.resultList.listData[i].statusName = this.translation.translate('cable.status.one');
        }
        this.resultList.listData[i].longitude = this.poolService.formatLongLat(this.resultList.listData[i].longitude);
        this.resultList.listData[i].latitude = this.poolService.formatLongLat(this.resultList.listData[i].latitude);
      }
    });
  }

  onRowSelect(event: any, template?: any) {
    // console.log('vtData : ', event);
    // console.log(this.selectedResult);
  }

  confirmExportExcel() {
    if (this.selectedResult.length > 0) {
      this.exportAll = false;
    } else {
      this.exportAll = true;
    }
    // this.displayConfirmExport = true;
    this.onSubmitExport();
    setTimeout(() => {
      this.focusExportElement.nativeElement.focus();
    }, 0);
  }

  onHideConfirmExport() {
    this.displayConfirmExport = false;
  }

  onSubmitExport() {
    this.messageService.clear('rightDown');
    this.displayConfirmExport = false;
    this.spinner.show();
    if (this.selectedResult.length === 0) {
      const requestData = [];
      requestData.push(this.paramsSearch);
      requestData.push(this.paramsSearchGRIDVIEW);
      if (this.isSearchType === 1) {
        this.poolService.excel(requestData).subscribe(s => {
          this.spinner.hide();
          if (s.body.size === 0) {
            this.messageService.add({
              key: 'rightDown',
              severity: 'warn',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('pool.export.error')
            });
            return;
          }
          saveAs(s.body, s.headers.get('File'));
        });
      } else if (this.isSearchType === 2) {
        this.poolService.excel(requestData).subscribe(s => {
          this.spinner.hide();

          if (s.body.size === 0) {
            this.messageService.add({
              key: 'rightDown',
              severity: 'warn',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('pool.export.error')
            });
            return;
          }

          saveAs(s.body, s.headers.get('File'));
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'rightDown',
          severity: 'warn',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('pool.export.error')
        });
      }
    } else {
      this.poolService.excelChoose(this.selectedResult).subscribe(s => {
        this.spinner.hide();

        saveAs(s.body, s.headers.get('File'));
      });
    }
  }


  onClearCalenDa(field) {
    this.formSearch.controls[field].setValue('');
    this.searchInTable('', field);
  }

  callFunctionFilter(event, field) {
    if (field === 'deptName') {
      this.filteredDeptFunc(event);
    }
    if (field === 'locationName') {
      this.filteredLocationFunc(event);
    }
  }

  // tra cuu vs list dia hinh trong autocomplete
  filteredLocationFunc(event) {
    // this.permissionService.filterLocation(event.query).subscribe(s => {
    //   this.filteredLocation = [];
    //   for (let i = 0; i < s.content.listData.length; i++) {
    //     this.filteredLocation.push({ label: s.content.listData[i].locationName, value: s.content.listData[i].locationId });
    //   }
    // });
    const obj = {
      deptId: null,
      locationId: null,
      locationName: event.query,
      isTree: 0
    };
    this.permissionService.getTreeNodeLocationList(obj).subscribe(s => {
      this.filteredLocation = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.filteredLocation.push({label: s.content.listData[i].pathLocalName, value: s.content.listData[i].locationName});
      }
    });
  }

  filteredDeptFunc(event) {
    // this.permissionService.filterDept(event.query).subscribe(s => {
    //   this.filteredDept = [];
    //   for (let i = 0; i < s.content.listData.length; i++) {
    //     this.filteredDept.push({ label: s.content.listData[i].deptName, value: s.content.listData[i].deptId });
    //   }
    // });
    this.permissionService.filterDept(event.query).subscribe(s => {
      this.filteredDept = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.filteredDept.push({label: s.content.listData[i].pathName, value: s.content.listData[i].deptName});
      }
    });
  }

  // download result file import
  downloadResult(path) {
    if (path) {
      this.stationService.downloadFile(path).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
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

  closeImportPopup() {
    this.fileA.setEmptyFile();
    this.fileE.setEmptyFile();
    this.importDialog = false;
    this.file = null;
    this.fileEdit = null;
    this.resultImportAdd = null;
    this.resultImportEdit = null;
  }

  downloadFileTemplate(type: any) {
    if (type === 1) {
      this.poolService.downloadFileTemplate(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    } else {
      this.poolService.downloadFileTemplateEdit(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    }
  }

  doImport(type?: number) {
    if (type === 1) {
      this.spinner.show();
      if (this.file) {
        this.poolService.importPools(this.file).subscribe(res => {
          this.spinner.hide();
          if (res.content == null) {
            this.messageService.add({
              key: 'rightDown',
              severity: 'warning',
              summary: '',
              detail: this.translation.translate('station.import.error')
            });
          } else {
            const path = res.content.split('+', 3);
            this.resultImportAdd = path[0];
            const success = +path[1];
            const err = +path[2];
            if (err === 0 && success !== 0) {
              this.messageService.add({
                key: 'rightDown',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', {success: success})
              });
            } else {
              this.messageService.add({
                key: 'rightDown',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.warning', {success: success, error: err})
              });
            }
            this.onReject('import');
          }
        }, error => {
          this.spinner.hide();
          this.messageService.add({
            key: 'rightDown',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'rightDown',
          severity: 'warn',
          summary: '',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    } else {
      if (this.fileEdit) {
        this.poolService.importEditPools(this.fileEdit).subscribe(res => {
          this.spinner.hide();
          if (res.content == null) {
            this.messageService.add({
              key: 'rightDown',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error')
            });
          } else {
            const path = res.content.split('+', 3);
            this.resultImportEdit = path[0];
            const success = +path[1];
            const err = +path[2];
            if (err === 0 && success !== 0) {
              this.messageService.add({
                key: 'rightDown',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', {success: success})
              });
            } else {
              this.messageService.add({
                key: 'rightDown',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.warning', {success: success, error: err})
              });
            }
            this.onReject('import');
          }
        }, error => {
          this.spinner.hide();
          this.messageService.add({
            key: 'rightDown',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'rightDown',
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

  // onInputDate(event, field) {
  //   this.searchInTable(new Date('09/11/2019',), field);
  //   console.log(event)
  //   const regex = new RegExp('^[0-9]+$');
  //   if (regex.test(event.path[0].value)) {
  //     const example = '01012019';
  //     if (event.path[0].value.length === example.length) {
  //       this.searchInTable(event, field);
  //     }
  //   }
  // }

  onCollapse(type?: string) {
    this.isCollapse = type === 'expend';
  }

  onLinkRightClickedHeader(content: string, event: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          {label: 'Hiển thị một phần', icon: 'fa fa-compress', command: () => this.onCollapse()},
          {label: 'Hiển thị tất cả ', icon: 'fa fa-expand', command: () => this.onCollapse('expend')}
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
          {label: 'Hiển thị một phần', icon: 'fa fa-compress', command: () => this.onCollapse()},
          {label: 'Hiển thị tất cả ', icon: 'fa fa-expand', command: () => this.onCollapse('expend')},
          {label: 'Copy', icon: 'fa fa-clipboard', command: () => this.copied(content)}
        );
        this.contextMenu.model = this.items;
        this.contextMenu.show(event);
      }
    }
  }
}
