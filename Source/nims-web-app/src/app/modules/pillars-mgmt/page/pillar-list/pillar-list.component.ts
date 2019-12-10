import { Component, ElementRef, OnInit, Type, ViewChild, OnDestroy, HostListener } from '@angular/core';
import { CommonUtils } from '@app/shared/services';
import { PillarService } from '@app/modules/pillars-mgmt/service/pillar.service';
import { FormGroup } from '@angular/forms';
import { CAT_ITEM, COLS_TABLE, PILLAR_STATUS, PILLAR_STATUS_CABLE } from '@app/shared/services/constants';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { PermissionService } from '@app/shared/services/permission.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { SelectItem } from 'primeng/components/common/api';
import { MenuItem, MessageService } from 'primeng/api';
import { AppComponent } from '@app/app.component';
import { AutocompleteSearchLocationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { StationService } from '@app/modules/stations-management/service/station.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Table } from 'primeng/table';
import {UploadControlComponent} from '@app/shared/components/upload-control/upload-control.component';
import { Subscription } from 'rxjs';
import { saveAs } from 'file-saver';
import { PoolService } from '@app/modules/pools-management/service/pool.service';
import { ContextMenu } from 'primeng/contextmenu';
import { SleeveService } from '@app/modules/sleeve-management/service/sleeve.service';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';

@Component({
  selector: 'pillar-list',
  templateUrl: './pillar-list.component.html',
  styleUrls: ['./pillar-list.component.css']
})
export class PillarListComponent implements OnInit, OnDestroy {
  @ViewChild('fileF') fileF: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dt') table: Table;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  advanceSearch = false;
  formSearch: FormGroup;
  filteredDept: any[];
  filteredLocation: any[];
  filteredPillarTypeCode: any[];
  filteredOwnerName: any[];
  filteredCableStatus: any[];
  auditStatusList: any[] = [];
  pillarStatusCable: any[] = [];
  first: number;
  rows: number;
  last: number;
  cols: any[];
  resultList: any = [];
  tempResult: any = [];
  filterList: any = [];
  selectedResult: any = [];
  // temp: any = [];
  frozenCols: any = [];
  column: SelectItem[] = [];
  selectedCars1: string[] = [];
  selectedLabel: any;
  filter: any = [];
  count = 0;
  selectedColumns: any[];
  isSelectDept = true;
  isSelectLocation = true;
  filteredLaneCode: any[];
  statusList: any[];
  disabledLocationModal = true;
  deptId: number;
  locationId: number;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  selectedRowData;
  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  warningMessPillar: String[] = [];
  isLazyLoad = false;
  errorDelete = false;
  filterInTable = false;
  itemDelete: any[];
  items: MenuItem[];
  isCollapse = false;
  hrStorage: any;

  // ---------------KienNNT---------------------
  importDialog = false;
  file: any;
  fileEdit: any;
  resultImportAdd: any;
  resultImportEdit: any;
  // ---------------KienNT----------------------
  paramsSearch = {
    first: 0,
    rows: undefined,
    basicInfo: undefined,
    pillarCode: undefined,
    laneCode: undefined,
    pillarTypeId: undefined,
    pillarTypeIdTemp: undefined,
    deptId: undefined,
    locationId: undefined,
    ownerId: undefined,
    constructionDate: undefined,
    status: undefined,
    longitude: undefined,
    latitude: undefined,
    longString: undefined,
    laString: undefined,
    address: undefined,
    pillarStatusCable: undefined,
    note: undefined,
    laneId: undefined,
    sortField: undefined,
    sortOrder: undefined,
    ownerIdTemp: undefined,
  };
  innerWidth;
  innerHeight;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLocation = this.app.translation.translate('common.dialog.header.location');
  private reloadPillar: Subscription;

  constructor(
    private app: AppComponent,
    private pillarService: PillarService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private poolService: PoolService,
    private sleeveService: SleeveService,
    private sysGridViewService: SysGridViewService,
    // --------------KienNT-------------------
    private stationService: StationService
    // -------------KienNT--------------------
  ) {
    this.buildForm({});
  }

  ngOnInit() {
    this.hrStorage = HrStorage.getUserToken();
    //get Cot theo user
    const columns = this.hrStorage.sysGridView;
    // this.gridId = this.hrStorage.sysGridView.filter(el =>this.se).gridId;
    this.cols = COLS_TABLE.PILLAR;

    // translate header
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    // set cot theo user
    this.selectedColumns = this.cols.filter((elem) => columns.find(data => {
      return elem.field === data.columnName && data.gridViewName === 'pillar';
    }));
    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }
    // this.cols = COLS_TABLE.PILLAR;
    // this.selectedColumns = [];
    // this.cols.forEach(col => {
    //   col.headerTranslate = this.translation.translate(col.header);
    //   if (col.field == 'pillarCode' || col.field == 'pillarTypeCode' || col.field == 'pathName' || col.field == 'pathLocalName' || col.field == 'note') {
    //     this.selectedColumns.push(col);
    //   }
    // });
    this.pillarStatusCable = this.dataCommon.getDropDownList(PILLAR_STATUS_CABLE);
    this.getOwnerList();
    this.getPillarTypeCodeList();
    this.statusList = this.dataCommon.getDropDownList(PILLAR_STATUS);
    this.setInnerWidthHeightParameters();
    this.reloadPillar = this.eventBusService.reloadPillarChange.subscribe(val => {
      this.table.reset();
    });
  }

  ngOnDestroy(): void {
    CommonUtils.setColumns('pillar', this.selectedColumns, this.sysGridViewService);
    if (this.reloadPillar) {
      this.reloadPillar.unsubscribe();
    }
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  // tra cuu vs list dia ban trong autocomplete
  filteredLocationFunc(event) {
    let obj = {
      deptId: null,
      locationId: null,
      locationName: event.query,
      isTree: 0
    }
    this.permissionService.getTreeNodeLocationList(obj).subscribe(s => {
      this.filteredLocation = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.filteredLocation.push({pathLocalName: s.content.listData[i].pathLocalName, value: s.content.listData[i].locationId});
      }
    });
  }

  filteredDeptFunc(event) {
    const query = event.query;
    this.permissionService.filterDept(query).subscribe(res => {
      this.filteredDept = [];
      for (let i = 0; i < res.content.listData.length; i++) {
        this.filteredDept.push({label: res.content.listData[i].pathName, value: res.content.listData[i].deptId});
      }
    });
  }

  onRowDblClick(event, item) {
    this.showDetail(item, 'view');
  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      basicInfo: [''],
      pillarCode: [''],
      deptId: [''],
      locationId: [''],
      terrain: [''],
      auditStatus: [''],
      first: [''],
      recordsTotal: [''],
      rows: [''],
      sortField: [''],
      sortOrder: [''],
      pillarTypeId: [''],
      ownerId: [''],
      pillarStatusCable: [''],
      pillarTypeCode: [''],
      deptName: [''],
      locationName: [''],
      ownerName: [''],
      pathName: [''],
      pathLocalName: [''],
      constructionDate: [''],
      laneCode: ['']
    });
  }

  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.paramsSearch.first = event.first;
    this.paramsSearch.rows = event.rows;
    if (this.isLazyLoad === true) {
      if (event.sortField) {
        if (event.sortField === 'statusName') {
          this.paramsSearch.sortField = 'status';
        } else {
          this.paramsSearch.sortField = event.sortField;
        }
        this.paramsSearch.sortOrder = event.sortOrder;
      }
      this.search();
    }
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.30;
  }

  showAdvance() {
    this.advanceSearch = true;
  }

  getPillarTypeCodeList() {
    this.pillarService.getPillarTypeCodeList().subscribe(res => {
      this.filterList = [];
      this.filterList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      res.content.listData.forEach(element => {
        this.filterList.push(({ label: element.pillarTypeCode, pillarTypeId: element.pillarTypeId }));
      });
      this.filteredPillarTypeCode = this.filterList;
    });
  }

  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.filteredOwnerName = [];
      // truong hop them moi
      this.filteredOwnerName.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.filteredOwnerName.push({ label: res[i].itemName, value: +res[i].itemId });
      }
    });
  }

  filteredLaneCodeFunc(event) {
    this.pillarService.getLaneCodeList({ laneCode: event.query }).subscribe(res => {
      this.filteredLaneCode = [];
      res.content.listData.forEach(element => {
        this.filteredLaneCode.push(element);
      });
    });
  }

  onChangeRowSelectDept(event: any) {
    this.eventBusService.emit({
      type: 'pillarList',
      deptDataObj: event
    });
  }

  onClearRowSelect() {
    this.formSearch.controls['deptId'].setValue(null);
    this.formSearch.controls['locationId'].setValue('');
    this.disabledLocationModal = true;
  }

  onClearLocation() {
    this.formSearch.controls['locationId'].setValue('');
  }

  clearDeptName() {
    this.onProcess({ first: 0, rows: 10 });
    this.paramsSearch.deptId = undefined;
  }

  clearLocation() {
    this.onProcess({ first: 0, rows: 10 });
    this.paramsSearch.locationId = undefined;
  }

  get formControls() {
    return this.formSearch.controls;
  }

  onChangeRowSelectLocation(event: any) {
    this.locationId = event.locationId;
  }

  searchInTable(event, field) {
    if (field === 'laneCode') {
      this.paramsSearch.laneCode = event;
    }
    if (field === 'pillarCode') {
      this.paramsSearch.pillarCode = event;
    }
    if (field === 'pillarTypeCode') {
      this.paramsSearch.pillarTypeIdTemp = event.pillarTypeId;
    }
    if (field === 'pathName') {
      this.paramsSearch.deptId = event;
    }
    if (field === 'pathLocalName') {
      this.paramsSearch.locationId = event;
    }
    if (field === 'ownerName') {
      this.paramsSearch.ownerIdTemp = event;
    }
    if (field === 'address') {
      this.paramsSearch.address = event;
    }
    if (field === 'constructionDate') {
      this.paramsSearch.constructionDate = event;
    }
    if (field === 'statusName') {
      this.paramsSearch.status = event;
    }
    if (field === 'longitude') {
      this.paramsSearch.longString = event;
    }
    if (field === 'latitude') {
      this.paramsSearch.laString = event;
    }
    if (field === 'note') {
      this.paramsSearch.note = event;
    }
    this.paramsSearch.first = 0;
    this.filterInTable = true;
    this.selectedResult = [];
    this.search();
  }

  hideAdvance() {
    this.advanceSearch = false;
  }

  advanceSearchFunc(type?: string) {
    this.isLazyLoad = true;
    // this.paramsSearch.rows = 10;
    if (type === 'basic') {
      this.paramsSearch.basicInfo = this.formSearch.value.basicInfo ? this.formSearch.value.basicInfo : null;
      this.paramsSearch.deptId = undefined;
      this.paramsSearch.locationId = undefined;
      this.paramsSearch.ownerId = undefined;
      this.paramsSearch.pillarTypeId = undefined;
      this.paramsSearch.pillarStatusCable = undefined;
      this.paramsSearch.pillarCode = undefined;
    }
    if (type === 'advance') {
      if (this.formSearch.controls['deptId'].value == '') {
        this.onClearRowSelect();
      }
      if (typeof this.formSearch.value.locationId.locationId == 'undefined') {
        this.resetLocation();
      }
      this.paramsSearch.basicInfo = undefined;
      this.paramsSearch.pillarCode = this.formSearch.value.pillarCode ? this.formSearch.value.pillarCode : null;
      this.paramsSearch.deptId = this.formSearch.value.deptId && this.formSearch.value.deptId.deptId ? this.formSearch.value.deptId.deptId : null;
      this.paramsSearch.locationId = this.formSearch.value.locationId && this.formSearch.value.locationId.locationId ? this.formSearch.value.locationId.locationId : null;
      this.paramsSearch.ownerId = this.formSearch.value.ownerId && this.formSearch.value.ownerId.value ? this.formSearch.value.ownerId.value : null;
      this.paramsSearch.pillarTypeId = this.formSearch.value.pillarTypeId && this.formSearch.value.pillarTypeId.pillarTypeId ? this.formSearch.value.pillarTypeId.pillarTypeId : null;
      this.paramsSearch.pillarStatusCable = this.formSearch.value.pillarStatusCable ? this.formSearch.value.pillarStatusCable.value : null;
    }
    this.search();
  }

  search() {
    this.spinner.show();
    this.pillarService.findAdvancePillar(this.paramsSearch).subscribe(res => {
      this.spinner.hide();
      if (this.filterInTable) {
        this.table.reset();
        this.filterInTable = false;
      }
      this.resultList = res.content;
      for (let i = 0; i < this.resultList.listData.length; i++) {
        if (this.resultList.listData[i].status == 1) {
          this.resultList.listData[i].statusName = this.translation.translate('pillar.status.one');
        }
        if (this.resultList.listData[i].status == 0) {
          this.resultList.listData[i].statusName = this.translation.translate('pillar.status.two');
        }
        this.resultList.listData[i].longitude = this.poolService.formatLongLat(this.resultList.listData[i].longitude);
        this.resultList.listData[i].latitude = this.poolService.formatLongLat(this.resultList.listData[i].latitude);
      }
    });
    // this.selectedResult = [];
  }

  onBlurDateFilter(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.paramsSearch.constructionDate = null;
        this.onProcess({ first: 0, rows: 10 });
      }
    }
  }

  onClearClickDateFilter(event) {
    if (event) {
      this.paramsSearch.constructionDate = null;
      this.searchInTable('', event.field);
    }
  }

  onInputDateFilter(event) {
    if (event) {
      // if (event.currentTarget.value === '') {
      //   this.paramsSearch.constructionDate = null;
      //   this.onProcess({first: 0, rows: 10});
      // }
    }
  }

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
          { label: 'Hiển thị một phần', icon: 'fa fa-compress', command: () => this.onCollapse() },
          { label: 'Hiển thị tất cả ', icon: 'fa fa-expand', command: () => this.onCollapse('expend') },
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
          { label: 'Hiển thị một phần', icon: 'fa fa-compress', command: () => this.onCollapse() },
          { label: 'Hiển thị tất cả ', icon: 'fa fa-expand', command: () => this.onCollapse('expend') },
          { label: 'Copy', icon: 'fa fa-clipboard', command: () => this.onCopy(content) }
        );
        this.contextMenu.model = this.items;
        this.contextMenu.show(event);
      }
    }
  }

  onCopy(event) {
    if (event) {
      this.messageService.add({
        key: 'kkkk', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }

  confirmExportExcel() {
    this.messageService.clear();
    this.messageService.add({
      key: 'excel',
      sticky: true,
      summary: this.translation.translate('common.message.confirm'),
      detail: this.selectedResult.length > 0 ? this.translation.translate('station.confirm.export') : this.translation.translate('station.confirm.export.all')
    });
  }

  exportExcel() {
    this.messageService.clear('excel');
    this.spinner.show();
    if (this.selectedResult && this.selectedResult.length > 0) {
      // for (let i = 0; i < this.selectedResult.length; i++) {
      //   this.selectedResult[i].geometry = null;
      // }
      this.pillarService.excelChose(this.selectedResult).subscribe(s => {
        this.spinner.hide();
        saveAs(s.body, s.headers.get('File'));
      });
    } else {
      // Export excel
      this.paramsSearch.first = null;
      this.paramsSearch.rows = null;
      this.pillarService.export(this.paramsSearch).subscribe(s => {
        this.spinner.hide();
        if (this.resultList.listData.length === 0) {
          this.messageService.add({
            key: 'kkkk',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('pillar.noData.export')
          });
          return;
        }
        saveAs(s.body, s.headers.get('File'));
      });
    }
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formSearch.controls[element].setValue(event.value.value);
    } else {
      this.formSearch.controls[element].setValue('');
    }
  }

  saveOrEdit(id?: number, action?: string) {
    this.pillarService.id = id;
    this.pillarService.action = action;
    this.tabLayoutService.open({
      component: 'PillarSaveComponent',
      header: id ? 'pillar.manage.update.label' : 'pillar.manage.create.label',
      action: id ? 'edit' : '',
      tabId: id,
      breadcrumb: [
        { label: this.translation.translate('pillar.manage.label') },
        { label: this.translation.translate(id ? 'pillar.manage.update.label' : 'pillar.manage.create.label') }
      ]
    });
  }

  sortDataPillar(event) {

  }

  resetLocation() {
    this.formSearch.controls['locationId'].setValue('');
  }

  resetDept() {
    this.formSearch.controls['deptId'].setValue('');
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

  // copied(e) {
  //   if (e['copied']) {
  //     this.messageService.add({key: 'kkkk', severity: 'success', summary: '', detail: e['content'] + ' are copied!'});
  //   }
  // }

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
      this.formSearch.controls['deptId'].setValue('');
    }
    if (data === 'locationId' && this.isSelectLocation) {
      this.formSearch.controls['locationId'].setValue('');
    }
  }

  onConfirm(item) {
    item.status = null;
    this.messageService.clear('pillar');
    if (!(item instanceof Array)) {
      item = [item];
    }
    for (let i = 0; i < item.length; i++) {
      item[i].geometry = null;
    }
    this.pillarService.delete(item).subscribe(s => {
      if (s.data[0] == 1) {
        this.messageService.add({
          severity: 'success',
          key: 'kkkk',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('message.success.deleted.success')
        });
        this.advanceSearchFunc();
      } else {
        item.forEach(element => {
          if (s.data.odf && s.data.odf.length > 0) {
            this.messageService.add({
              severity: 'error',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('station.delete.error.odf1') + s.data.odf + this.translation.translate('station.delete.error.odf2') + element.stationCode + this.translation.translate('station.delete.error.odf3')
            });
          }
          if (s.data.cables && s.data.cables.length > 0) {
            this.messageService.add({
              severity: 'error',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('station.delete.error.cables1') + s.data.cables + this.translation.translate('station.delete.error.cables2') + element.stationCode + this.translation.translate('station.delete.error.cables3')
            });
          }
          if (s.data.cablelanes && s.data.cablelanes.length > 0) {
            this.messageService.add({
              severity: 'error',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('station.delete.error.cablelanes1') + s.data.cablelanes + this.translation.translate('station.delete.error.cablelanes2') + element.stationCode + this.translation.translate('station.delete.error.cablelanes3')
            });
          }
        });
      }
    });
  }

  onReject(key) {
    this.messageService.clear(key);
  }

  delete(item) {
    this.messageService.clear();
    this.messageService.add({
      key: 'pillar',
      sticky: true,
      summary: this.translation.translate('common.message.confirm'),
      detail: this.translation.translate('common.confirm.delete'),
      data: item
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
    } else if ((this.selectedResult && this.selectedResult.length === 0 || this.selectedResult === null)) {
      this.displayConfirmDelete = false;
      this.messageService.add({
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.warn.delete.multipe')
      });
    }
  }

  onSubmitDelete() {
    this.spinner.show();
    let dataObj;
    this.displayConfirmDelete = false;
    if (this.selectedRowData) {
      dataObj = [this.selectedRowData];
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      dataObj = this.selectedResult;
    }
    const requestData = [];
    dataObj.forEach(element => {
      requestData.push({
        pillarId: element.pillarId,
        pillarCode: element.pillarCode,
        status: null
      });
    });
    this.pillarService.delete(requestData).subscribe(res => {
      this.spinner.hide();
      if (res.content && res.content.length === 0) {

        requestData.forEach(el => {
          if (this.selectedResult) {
            for (let i = 0; i < this.selectedResult.length; i++) {
              if (el.pillarId === this.selectedResult[i].pillarId) {
                this.selectedResult.splice(i, 1);
                i--;
              }
            }
          }
        });

        this.messageService.add({
          key: 'kkkk',
          severity: 'success',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.delete.success')
        });
        this.advanceSearchFunc();
      } else if (res.content && res.content.length > 0) {
        this.displayWarningMessDelete = true;
        this.warningMessPillar = [];
        for (const pillar of res.content) {
          if (pillar.other) {
            this.warningMessPillar.push(this.translation.translate('common.message.error.system'));
            this.errorDelete = true;
          } else {
            if (pillar.cable && pillar.cable.length > 0) {
              let mescable = '';
              for (const a of pillar.cable) {
                mescable += '[' + a + '], ';
              }
              this.warningMessPillar.push(this.translation.translate('pillar.error.delete.cable', {
                cablesCode: mescable,
                pillarCode: pillar.pillarCode
              }));
            } else if (pillar.sleeve && pillar.sleeve.length > 0) {
              let mesSleeve = '';
              for (const a of pillar.sleeve) {
                mesSleeve += '[' + a + '], ';
              }
              this.warningMessPillar.push(this.translation.translate('pillar.error.delete.sleeve', {
                sleevesCode: mesSleeve,
                pillarCode: pillar.pillarCode
              }));
            }
            this.errorDelete = true;
          }
        }
        if (this.warningMessPillar.length === 0) {
          this.errorDelete = false;
          for (const pillar of res.content) {
            this.itemDelete = pillar.listDeletePillarId;
            let mes = '';
            for (const a of pillar.hang) {
              mes += '[' + a + ']; ';
            }
            this.warningMessPillar.push(this.translation.translate('pillar.confirm.delete', {
              listCables: mes,
              pillarCode: pillar.pillarCode
            }));
          }
        }
      }
    },
      (error) => {
        this.spinner.hide();
        this.warningMessPillar = [];
        this.displayWarningMessDelete = true;
        this.warningMessPillar.push(this.translation.translate('pool.message.error'));
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
    this.spinner.show();
    this.displayWarningMessDelete = false;
    const requestData = [];
    this.itemDelete.forEach(element => {
      requestData.push({
        pillarId: element
      });
    });
    this.pillarService.deleteHangConfirm(requestData).subscribe(res => {
      this.spinner.hide();
      this.messageService.add({
        key: 'rightDown',
        severity: 'success',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.success')
      });
      this.search();
    },
      (error) => {
        this.spinner.hide();
        this.warningMessPillar = [];
        this.displayWarningMessDelete = true;
        this.warningMessPillar.push(this.translation.translate('pool.message.error'));
      }
    );

  }

  showDetail(selectedRowData, action) {
    this.selectedRowData = selectedRowData;
    this.pillarService.item = selectedRowData;
    this.pillarService.action = action;
    this.tabLayoutService.open({
      component: 'PillarDetailComponent',
      header: action == 'edit' ? 'pillar.manage.update.label' : action == 'view' ? 'pillar.manage.detail.label' : 'pillar.manage.create.label',
      breadcrumb: [
        { label: this.translation.translate('pillar.manage.label') },
        { label: this.translation.translate(action == 'edit' ? 'pillar.manage.update.label' : action == 'view' ? 'pillar.manage.detail.label' : 'pillar.manage.create.label') }
      ],
      action: action,
      tabId: this.selectedRowData.pillarId,
    });
  }

    @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter' || event.code === 'NumpadEnter') {
      this.eventBusService.dataChange.subscribe(val => {
        if (val) {
          if (val.data.key === PillarListComponent.name) {
            // if (this.advanceSearch)
            //   this.advanceSearchFunc('advance');
            // else
            //   this.advanceSearchFunc('basic');
            this.advanceSearchFunc();
          }
        }
      }).unsubscribe();
    }
  }

  // ----------------------------------------KienNT------------------------------
  changeFile(event, type) {
    if (type === 1) {
      this.file = event;
    } else {
      this.fileEdit = event;
    }
  }

  downloadFileTemplate(type: any) {
    const data = this.selectedResult;
    if (data && data.length > 0) {
      data.forEach(el => {
        el.geometry = null;
      });
    }
    if (type === 1) {
      this.pillarService.downloadFileTemplate(data).subscribe(res => {
        if (res.body.size === 0) {
          this.app.warningMessage('messages.warn.organizationcontroller.download');
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    } else {
      this.pillarService.downloadFileTemplateEdit(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    }
  }

  downloadResult(path) {
    if (path) {
      this.pillarService.downloadFile(path).subscribe(res => {
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

  importPillar() {
    if (this.file) {
      this.messageService.add({
        key: 'import',
        sticky: true,
        summary: this.translation.translate('common.message.confirm.import'),
        detail: '',
        data: 1
      });
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: '',
        detail: this.translation.translate('station.import.error.require')
      });
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

  importEdit() {
    this.messageService.add({
      key: 'import',
      sticky: true,
      summary: this.translation.translate('common.message.confirm.import'),
      detail: '',
      data: 2
    });
  }

  // doImport(flag) {
  //   if (flag === 1) {
  //     this.pillarService.importPillar(this.file).subscribe(res => {
  //       if (res.data == null) {
  //         this.messageService.add({
  //           severity: 'warning',
  //           summary: '',
  //           detail: this.translation.translate('station.import.error')
  //         });
  //       } else {
  //         const path = res.data.split('+', 3);
  //         this.resultImportAdd = path[0];
  //         const success = +path[1];
  //         const err = +path[2];
  //         if (err === 0) {
  //           this.messageService.add({
  //             severity: 'success',
  //             summary: '',
  //             detail: this.translation.translate('station.import.success', {success: success})
  //           });
  //         } else {
  //           this.messageService.add({
  //             severity: 'warn',
  //             summary: '',
  //             detail: this.translation.translate('station.import.warning', {success: success, error: err})
  //           });
  //         }
  //         this.onReject('import');
  //       }
  //     });
  //   } else {
  //     this.pillarService.editPillar(this.fileEdit).subscribe(res => {
  //       if (res.data == null) {
  //         this.messageService.add({
  //           severity: 'warning',
  //           summary: '',
  //           detail: this.translation.translate('station.import.error')
  //         });
  //       } else {
  //         const path = res.data.split('+', 3);
  //         this.resultImportEdit = path[0];
  //         const success = +path[1];
  //         const err = +path[2];
  //         if (err === 0) {
  //           this.messageService.add({
  //             severity: 'success',
  //             summary: '',
  //             detail: this.translation.translate('station.import.success', {success: success})
  //           });
  //         } else {
  //           this.messageService.add({
  //             severity: 'warn',
  //             summary: '',
  //             detail: this.translation.translate('station.import.warning', {success: success, error: err})
  //           });
  //         }
  //         this.onReject('import');
  //       }
  //     });
  //   }
  // }

  doImport(type?: number) {
    if (type === 1) {
      this.spinner.show();
      if (this.file) {
        console.log(this.file);
        this.pillarService.importPillar(this.file).subscribe(res => {
          this.spinner.hide();
          if (res.status === 0) {
            this.messageService.add({
              key: 'stationList',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error.file.type')
            });
            return;
          }
          if (res == null && res.data == null) {
            this.messageService.add({
              key: 'stationList',
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
                key: 'stationList',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', { success: success })
              });
            } else {
              this.messageService.add({
                key: 'stationList',
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
            key: 'stationList',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'stationList',
          severity: 'warn',
          // summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.import.error.require')
        });
      }
    } else {
      if (this.fileEdit) {
        this.pillarService.editPillar(this.fileEdit).subscribe(res => {
          this.spinner.hide();
          if (res.status === 0) {
            this.messageService.add({
              key: 'stationList',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error.file.type')
            });
            return;
          }
          if (res == null && res.status === 0) {
            this.messageService.add({
              key: 'stationList',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error.file.type')
            });
            return;
          }
          if (res.data == null) {
            this.messageService.add({
              key: 'stationList',
              severity: 'warning',
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
                key: 'stationList',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('cable.importEdit.success', { success: success })
              });
            } else {
              this.messageService.add({
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('cable.importEdit.warning', { success: success, error: err })
              });
            }
            this.onReject('import');
          }
        }, error => {
          this.spinner.hide();
          this.messageService.add({
            key: 'stationList',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'stationList',
          severity: 'warn',
          // summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.import.error.require')
        });
      }
    }
  }

  showDialog() {
    this.importDialog = true;
  }


  // --------------------------------KienNT--------------------------------------------
}
