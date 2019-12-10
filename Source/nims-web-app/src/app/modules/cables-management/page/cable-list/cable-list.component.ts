import {
  Component,
  OnInit,
  Type,
  ElementRef,
  ViewChild,
  HostListener
} from '@angular/core';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { BreadcrumbService } from '@app/shared/services/breadcrumb.service';
import { CommonUtils } from '@app/shared/services';
import { CableService } from '@app/modules/cables-management/service/cable.service';
import { FormGroup } from '@angular/forms';
import { CABLE_STATUS, COLS_TABLE } from '@app/shared/services/constants';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { PermissionService } from '@app/shared/services/permission.service';
import { AppComponent } from '@app/app.component';
import { MenuItem, MessageService } from 'primeng/api';
import { StationService } from '@app/modules/stations-management/service/station.service';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import { environment } from '@env/environment';
import { AutocompleteSearchStationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { Table } from 'primeng/table';
import { SysGridViewService } from '@app/shared/services/sys-grid-view.service';
import { HrStorage } from '@app/core/services/auth/HrStorage';
import { Subscription } from 'rxjs';
import { saveAs } from 'file-saver';
import { ContextMenu } from 'primeng/contextmenu';
import { UploadControlComponent } from '@app/shared/components/upload-control/upload-control.component';

@Component({
  selector: 'cable-list',
  templateUrl: './cable-list.component.html',
  styleUrls: ['./cable-list.component.css']
})
export class CableListComponent implements OnInit {
  @ViewChild('fileF') fileF: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dt') table: Table;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  advanceSearch = false;
  formSearch: FormGroup;
  filteredDept: any[];
  filteredStation: any[];
  statusList: any[] = [];
  listODFFist: any = new Array();
  listODFEnd: any = new Array();
  listCable: any = new Array();
  filteredStationSingle: any[];
  filteredDeptNameSingle: any[];
  disabledLocationModal = true;
  selectedColumns: any[];
  resultList: any = [];
  totalRecord: number;
  selectedResult: any = [];
  isStationCode = false;
  isDeptName = false;
  isSort = false;
  first: number;
  rows: number;
  last: number;
  cols: any[];
  deptId: number;
  stationId: number;
  environments = environment;
  importDialog = false;
  file: any;
  fileEdit: any;
  resultImportAdd: any;
  resultImportEdit: any;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  station: Type<any> = AutocompleteSearchStationModalComponent;
  headerStation = this.app.translation.translate(
    'common.dialog.header.station'
  );
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  isLazyLoad: boolean = false;
  flagLazyLoad = false;
  colsHideShow: any[];
  paramsSearch = {
    first: 0,
    rows: undefined,
    basicInfo: undefined,
    cableCode: undefined,
    sourceId: undefined,
    destId: undefined,
    stationId: undefined,
    stationCode: undefined,
    deptId: undefined,
    deptName: undefined,
    pathName: undefined,
    cableTypeId: undefined,
    cableTypeCode: undefined,
    status: undefined,
    lengthStr: undefined,
    installationDate: undefined,
    note: undefined,
    sortField: undefined,
    sortOrder: undefined,
    fillerStationCode: undefined,

    cableCodeTable: undefined,
    stationCodeTable: undefined,
    stationIdTable: undefined,
    sourceIdTable: undefined,
    destIdTable: undefined,
    cableTypeIdTable: undefined,
    deptNameTable: undefined,
    deptIdTable: undefined
  };
  dataDelete = {
    cableId: undefined,
    cableCode: undefined,
    sourceId: undefined,
    destId: undefined,
    sourceCode: undefined,
    destCode: undefined
  };
  innerWidth;
  innerHeight;
  selectedRowData;
  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  warningMessDeleteCable;
  items: MenuItem[];
  hrStorage: any;
  private reloadCable: Subscription;
  isCollapse = false;

  constructor(
    private app: AppComponent,
    private tabLayoutService: TabLayoutService,
    private breadcrumbService: BreadcrumbService,
    private cableService: CableService,
    private translation: TranslationService,
    private permissionService: PermissionService,
    private stationService: StationService,
    private messageService: MessageService,
    private dataCommon: DataCommonService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private sysGridViewService: SysGridViewService
  ) {
    this.buildForm({});
  }

  ngOnInit() {
    // this.hrStorage = HrStorage.getUserToken();
    // //an hien cot theo user dang nhap
    // this.selectedColumns = this.hrStorage.sysGridView;
    // const dm = COLS_TABLE.CABLE;
    // const colsShow = [];
    // this.cols = [];
    // this.colsHideShow = [];
    // // set cot theo user
    // this.cols = dm.filter((elem) => this.selectedColumns.find(data => {
    //   return elem.field === data.columnName && data.gridViewName === 'cableinstation';
    // }));
    // this.colsHideShow = dm.filter((elem) => this.selectedColumns.find(data => {
    //   if(elem.field != 'cableCode')
    //   return elem.field === data.columnName && data.gridViewName === 'cableinstation';
    // }));
    // // this.selectedColumns = this.cols;

    // this.selectedColumns = [];
    // this.cols.forEach(col => {
    //   col.headerTranslate = this.translation.translate(col.header);
    //   if (col.field == 'cableCode' || col.field == 'stationCode' || col.field == 'sourceCode' || col.field == 'destCode' || col.field == 'cableTypeCode' || col.field == 'length' || col.field == 'deptName' || col.field == 'note') {
    //     // this.selectedColumns.push(col);
    //     colsShow.push(col);
    //   }
    // });
    // this.selectedColumns = colsShow;

    this.hrStorage = HrStorage.getUserToken();
    //get Cot theo user
    const columns = this.hrStorage.sysGridView;
    // this.gridId = this.hrStorage.sysGridView.filter(el =>this.se).gridId;
    this.cols = COLS_TABLE.CABLE;
    const dm = this.cols;
    this.colsHideShow = [];
    // translate header
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    // set cot theo user
    this.selectedColumns = this.cols.filter(elem =>
      columns.find(data => {
        return (
          elem.field === data.columnName &&
          data.gridViewName === 'cableinstation'
        );
      })
    );
    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }
    dm.forEach(el => {
      if (el.field != 'cableCode') this.colsHideShow.push(el);
    });

    this.cableService.findListCableType().subscribe(res => {
      this.listCable = [];
      this.listCable.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      res.content.listData.forEach(element => {
        this.listCable.push({ label: element[1], value: element[0] });
      });
    });

    this.cableService.findListODFFist({ stationId: null }).subscribe(res => {
      this.listODFFist = [];
      this.listODFFist.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      res.content.listData.forEach(element => {
        this.listODFFist.push({ label: element[1], value: element[0] });
      });
    });

    this.cableService.findListODFEnd({ stationId: null }).subscribe(res => {
      this.listODFEnd = [];
      this.listODFEnd.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      res.content.listData.forEach(element => {
        this.listODFEnd.push({ label: element[1], value: element[0] });
      });
    });
    // Lay list status
    this.statusList = this.dataCommon.getDropDownList(CABLE_STATUS);
    this.setInnerWidthHeightParameters();
    // this.advanceSearchFunc('basic');
    this.reloadCable = this.eventBusService.reloadCableChange.subscribe(val => {
      this.table.reset();
    });

    this.items = [];
  }

  onLinkRightClickedHeader(content: string, event: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          {
            label: 'Hiển thị một phần',
            icon: 'fa fa-compress',
            command: () => this.onCollapse()
          },
          {
            label: 'Hiển thị tất cả ',
            icon: 'fa fa-expand',
            command: () => this.onCollapse('expend')
          }
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
          {
            label: 'Hiển thị một phần',
            icon: 'fa fa-compress',
            command: () => this.onCollapse()
          },
          {
            label: 'Hiển thị tất cả ',
            icon: 'fa fa-expand',
            command: () => this.onCollapse('expend')
          },
          {
            label: 'Copy',
            icon: 'fa fa-clipboard',
            command: () => this.onCopy(content)
          }
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
      this.messageService.add({
        key: 'cablelist',
        severity: 'success',
        summary: '',
        detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }

  ngOnDestroy(): void {
    CommonUtils.setColumns(
      'cableinstation',
      this.selectedColumns,
      this.sysGridViewService
    );
    if (this.reloadCable) {
      this.reloadCable.unsubscribe();
    }
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  /**
   * Filter deptname
   */
  filteredDeptFunc(event) {
    const query = event.query;
    this.permissionService.filterDept(query).subscribe(res => {
      this.filteredDept = [];

      for (let i = 0; i < res.content.listData.length; i++) {
        this.filteredDept.push({
          label: res.content.listData[i].pathName,
          value: res.content.listData[i].deptId
        });
      }
      // let depts = res.content.listData;
      // this.filteredDept = depts;
      // depts.forEach(element => {
      //   this.filteredDept.push(element.deptName);
      // });
    });
  }

  /**
   * Filter stationcode
   */
  filterStationSingle(event) {
    const stationCode = event.query ? event.query : '';
    const stationId = event.stationId ? event.stationId : '';

    this.cableService
      .getDataSearchAdvance({ stationId, stationCode })
      .subscribe(res => {
        console.log(res);
        this.filteredStationSingle = [];
        res.content.listData.forEach(element => {
          this.filteredStationSingle.push({
            label: element.stationCode,
            value: element.stationId
          });
        });
      });
  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      basicInfo: [''],
      _deptId: [''],
      _stationId: [''],
      cableCode: [''],
      sourceId: [''],
      sourceCode: [''],
      destId: [''],
      destCode: [''],
      stationId: [''],
      stationCode: [''],
      deptId: [''],
      deptName: [''],
      cableTypeId: [''],
      cableTypeCode: [''],
      status: [],
      statusName: [],
      length: [],
      installationDate: [''],
      note: [''],
      fileCheck: [''],
      fileListed: [''],
      first: [''],
      recordsTotal: [''],
      rows: [''],
      sortField: [''],
      sortOrder: [''],

      cableCodeTable: [''],
      stationCodeTable: [''],
      stationIdTable: [''],
      sourceIdTable: [''],
      destIdTable: [''],
      cableTypeIdTable: [''],
      deptNameTable: [''],
      deptIdTable: ['']
    });
  }

  get formControls() {
    return this.formSearch.controls;
  }

  // return form
  get f() {
    return this.formSearch.controls;
  }

  // load data table
  public onLazyLoad(event) {
    this.rows = event.rows;
    this.first = event.first;
    if (this.isLazyLoad === true) {
      this.paramsSearch.first = event.first;
      this.paramsSearch.rows = event.rows;
      if (event.sortField) {
        this.paramsSearch.sortField = event.sortField;
        this.paramsSearch.sortOrder = event.sortOrder;
      }
      this.searchStatus();
    }
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.5;
  }

  // Tim kiem status
  searchStatus() {
    this.spinner.show();
    this.cableService.findAdvanceCable(this.paramsSearch).subscribe(res => {
      this.spinner.hide();

      this.resultList = res.content;
      for (let i = 0; i < this.resultList.listData.length; i++) {
        if (this.resultList.listData[i].status === 0) {
          this.resultList.listData[i].statusName = this.translation.translate(
            'cable.status.zero'
          );
        }
        if (this.resultList.listData[i].status === 1) {
          this.resultList.listData[i].statusName = this.translation.translate(
            'cable.status.one'
          );
        }
        if (this.resultList.listData[i].status === 2) {
          this.resultList.listData[i].statusName = this.translation.translate(
            'cable.status.two'
          );
        }
      }
    });
  }

  // Xoa trang thai status
  removeStatusName(item: any[]) {
    item.forEach(element => {
      element.status = null;
    });
  }

  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formSearch.controls[element].setValue(event.value.value);
    } else {
      this.formSearch.controls[element].setValue('');
    }
  }

  setFilteredStationSingle(filteredStationSingle) {
    this.filteredStationSingle = filteredStationSingle;
  }

  setListCable(listCable) {
    this.listCable = listCable;
  }

  setListODFFist(listODFFist) {
    this.listODFFist = listODFFist;
  }

  setListODFEnd(listODFEnd) {
    this.listODFEnd = listODFEnd;
  }

  setFilteredDept(filteredDept) {
    this.filteredDept = filteredDept;
  }

  showAdvance() {
    this.formSearch.value.basicInfo = null;
    this.advanceSearch = true;
  }

  hideAdvance() {
    this.advanceSearch = false;
  }

  // Tim kiem
  advanceSearchFunc(type?: string) {
    this.isLazyLoad = true;
    this.paramsSearch.rows = this.rows;
    if (type === 'basic') {
      this.selectedResult = [];
      this.paramsSearch.basicInfo = this.formSearch.value.basicInfo
        ? this.formSearch.value.basicInfo
        : null;
      this.paramsSearch.cableCode = undefined;
    }
    if (type === 'advance') {
      this.selectedResult = [];
      this.paramsSearch.basicInfo = undefined;
      // this.paramsSearch.stationId = this.formSearch.value.stationId.value ? this.formSearch.value.stationId.value : null;
      this.paramsSearch.stationId = this.formSearch.value._stationId
        ? this.formSearch.value._stationId.stationId
        : null;
      this.paramsSearch.sourceId = this.formSearch.value.sourceId
        ? this.formSearch.value.sourceId
        : null;
      this.paramsSearch.destId = this.formSearch.value.destId
        ? this.formSearch.value.destId
        : null;
      this.paramsSearch.cableCode = this.formSearch.value.cableCode
        ? this.formSearch.value.cableCode
        : null;
      this.paramsSearch.cableTypeId = this.formSearch.value.cableTypeId
        ? this.formSearch.value.cableTypeId
        : null;
      // this.paramsSearch.deptName = this.formSearch.value.deptName.label  ? this.formSearch.value.deptName.label: null;
      this.paramsSearch.deptId = this.formSearch.value._deptId
        ? this.formSearch.value._deptId.deptId
        : null;
    }
    this.searchStatus();
  }

  // tim kiem trong girdview
  searchInTable(event, field) {
    console.log(field + ': ' + event);
    debugger
    this.selectedResult = [];
    if (field === 'cableCode') {
      this.paramsSearch.cableCode = event;
    }
    if (field === 'stationCode') {
      this.paramsSearch.stationId = event;
    }
    if (field === 'sourceCode') {
      this.paramsSearch.sourceId = event;
    }
    if (field === 'destCode') {
      this.paramsSearch.destId = event;
    }
    if (field === 'cableTypeCode') {
      this.paramsSearch.cableTypeCode = event;
    }
    if (field === 'length') {
      if (event) {
        this.paramsSearch.lengthStr = event;
      } else {
        this.paramsSearch.lengthStr = null;
      }
    }
    if (field === 'installationDate') {
      this.paramsSearch.installationDate = event;
    }
    if (field === 'note') {
      this.paramsSearch.note = event;
    }
    if (field === 'status') {
      this.paramsSearch.status = event;
    }
debugger
    if (field === 'stationCodeTable') {
      this.paramsSearch.stationIdTable = event;
    }
    if (field === 'sourceIdTable') {
      this.paramsSearch.sourceIdTable = event;
    }
    if (field === 'destIdTable') {
      this.paramsSearch.destIdTable = event;
    }
    if (field === 'cableTypeIdTable') {
      this.paramsSearch.cableTypeIdTable = event;
    }
    if (field === 'cableCodeTable') {
      this.paramsSearch.cableCodeTable = event;
    }
    if (field === 'deptIdTable') {
      this.paramsSearch.deptIdTable = event;
    }


    this.searchStatus();
    this.paramsSearch.first = 0;
    this.paramsSearch.rows = 10;
    this.cableService.findAdvanceCable(this.paramsSearch).subscribe(
      res => {
        this.spinner.hide();
        this.table.reset();
        this.resultList = res.content
          ? res.content
          : (this.resultList.listData = []);
      },
      error => {
        console.log(error);
        this.resultList = [];
      }
    );
  }

  onInputDateFilter(event) {
    if (event) {
      // if (event.currentTarget.value === '') {
      //   this.paramsSearch.constructionDate = null;
      //   this.onProcess({first: 0, rows: 10});
      // }
    }
  }

  onBlurDateFilter(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        console.log(event.currentTarget.value);
        this.formSearch.controls['installationDate'].setValue(null);
        this.paramsSearch.installationDate = null;
        this.onProcess({ first: 0, rows: 10 });
      }
    }
  }

  onClearClickDateFilter(event) {
    this.formSearch.controls['installationDate'].setValue(null);
    if (event) {
      this.paramsSearch.installationDate = null;
      this.onProcess({ first: 0, rows: 10 });
    }
  }

  callFunctionFilter(event, field) {
    if (field === 'deptName') {
      this.filteredDeptFunc(event);
    }
    if (field === 'stationCode') {
      this.filterStationSingle(event);
    }
  }

  onInputSearch(event, field) {
    debugger
    const keyInput = event.target.value;
    if ('deptNameTable' === field) {
      this.paramsSearch.deptNameTable = keyInput;
      if (keyInput === '') {
        this.paramsSearch.deptIdTable = null;
      }
    }
    if ('cableCodeTable' === field) {
      this.paramsSearch.cableCodeTable = keyInput;
    }
    if ('stationCodeTable' === field) {
      this.paramsSearch.stationCodeTable = keyInput;
      if (keyInput === '') {
        this.paramsSearch.stationIdTable = null;
      }
    }
    this.advanceSearchFunc();
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

  saveOrEdit(id?: number, action?: string) {
    this.cableService.id = id;
    this.cableService.action = action;
    this.tabLayoutService.open({
      component: 'CableSaveComponent',
      header: id ? 'cable.update.label' : 'cable.create.label',
      action: id ? 'edit' : '',
      tabId: id,
      breadcrumb: [
        { label: this.translation.translate('cable.manage.label') },
        {
          label: this.translation.translate(
            id ? 'cable.update.label' : 'cable.create.label'
          )
        }
      ]
    });
  }

  getFormValidationErrors(success: () => void) {
    if (
      CommonUtils.getFormValidationErrors(this.formSearch, this.app, 'cable')
    ) {
      success();
    }
  }

  delete(item) {
    this.dataDelete.cableId = item.cableId;
    this.dataDelete.cableCode = item.cableCode;
    this.dataDelete.sourceId = item.sourceId;
    this.dataDelete.destId = item.destId;
    this.dataDelete.sourceCode = item.sourceCode;
    this.dataDelete.destCode = item.destCode;
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      summary: this.translation.translate('common.message.confirm'),
      detail: this.translation.translate('station.confirm.delete'),
      data: this.dataDelete
    });
  }

  deleteMultiple() {
    if (this.selectedResult.length > 0) {
      this.messageService.clear();
      this.messageService.add({
        key: 'c',
        sticky: true,
        summary: this.translation.translate('common.message.confirm'),
        detail: this.translation.translate('station.confirm.delete'),
        data: this.selectedResult
      });
    } else {
      this.messageService.add({
        key: 'cablelist',
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('common.message.delete.noselect')
      });
    }
  }

  showDetail(id, action) {
    this.cableService.id = id;
    this.cableService.action = action;
    this.tabLayoutService.open({
      component: 'CableSaveComponent',
      header:
        action === 'edit'
          ? 'cable.update.label'
          : action === 'view'
          ? 'cable.detail.label'
          : 'cable.create.label',
      action: id ? 'view' : '',
      tabId: id,
      breadcrumb: [
        { label: this.translation.translate('cable.manage.label') },
        {
          label: this.translation.translate(
            action === 'edit'
              ? 'cable.update.label'
              : action === 'view'
              ? 'cable.detail.label'
              : 'cable.create.label'
          )
        }
      ]
    });
  }

  onReject(key) {
    this.messageService.clear(key);
  }

  // onConfirm(item) {
  //   this.messageService.clear('c');
  //   if (!(item instanceof Array)) {
  //     item = [item];
  //   }
  //   this.removeStatusName(item);
  //   this.cableService.delete(item).subscribe(s => {
  //     if (s.data.code === 0) {
  //       const dataMessage = [];
  //       dataMessage.push(this.translation.translate('cable.delete.error.odf', {cableCode: s.data.message}));

  //       this.messageService.add({
  //         key: 'messageDelete',
  //         sticky: true,
  //         summary: this.translation.translate('common.label.NOTIFICATIONS'),
  //         data: dataMessage
  //       });
  //       setTimeout(() => {
  //         this.messageService.clear('messageDelete');
  //       }, 3000);

  //     } else {
  //       this.onProcess({first: 0, rows: 10});
  //       this.messageService.add({
  //         severity: 'success',
  //         key: 'success',
  //         summary: this.translation.translate('common.label.NOTIFICATIONS'),
  //         detail: this.translation.translate('station.delete.success')
  //       });
  //       setTimeout(() => {
  //         this.messageService.clear('success');
  //       }, 3000);
  //     }
  //   });
  // }

  onDelete(selectedRowData, selectedRowDataList) {
    this.selectedRowData = selectedRowData;
    this.selectedResult = selectedRowDataList;
    if (
      this.selectedRowData ||
      (this.selectedResult && this.selectedResult.length > 0)
    ) {
      this.displayConfirmDelete = true;
      setTimeout(() => {
        this.searchElement.nativeElement.focus();
      }, 0);
    } else if (
      (this.selectedResult && this.selectedResult.length === 0) ||
      this.selectedResult === undefined ||
      this.selectedResult === null
    ) {
      this.displayConfirmDelete = false;
      this.messageService.add({
        key: 'cablelist',
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.warn.delete.multipe')
      });
    }
  }

  onSubmitDelete() {
    this.spinner.show();
    if (this.selectedRowData) {
      const selectedRowData = [
        {
          cableId: this.selectedRowData.cableId,
          cableCode: this.selectedRowData.cableCode,
          sourceId: this.selectedRowData.sourceId,
          destId: this.selectedRowData.destId
        }
      ];
      this.cableService.delete(selectedRowData).subscribe(res => {
        this.spinner.hide();
        if (res.data.code === 0) {
          const deleteRefCableData = res.data.message;
          if (deleteRefCableData && deleteRefCableData.length > 0) {
            this.warningMessDeleteCable = deleteRefCableData;
            this.displayConfirmDelete = false;
            this.displayWarningMessDelete = true;
          }
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            key: 'deleteCableSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.table.reset();
          this.onProcess({ first: 0, rows: 10 });
        }
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      const selectedRowDataList = [];
      this.selectedResult.forEach(it => {
        selectedRowDataList.push({
          cableId: it.cableId,
          cableCode: it.cableCode,
          sourceId: it.sourceId,
          destId: it.destId
        });
      });
      this.cableService.delete(selectedRowDataList).subscribe(res => {
        this.spinner.hide();
        if (res.data.code === 0) {
          const deleteRefCableData = res.data.message;
          if (deleteRefCableData && deleteRefCableData.length > 0) {
            this.warningMessDeleteCable = deleteRefCableData;
            this.displayConfirmDelete = false;
            this.displayWarningMessDelete = true;
          }
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            key: 'deleteCableSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.table.reset();
          this.onProcess({ first: 0, rows: 10 });
        }
      });
    }
  }

  onHideConfirmDelete() {
    this.displayConfirmDelete = false;
  }

  onHideWarningDelete() {
    this.displayWarningMessDelete = false;
    this.warningMessDeleteCable = undefined;
  }

  onBlurInput(data) {
    if (data === 'stationCode' && this.isStationCode === false) {
      this.formSearch.controls['stationId'].setValue('');
    }

    if (data === 'deptName' && this.isDeptName === false) {
      this.formSearch.controls['deptName'].setValue('');
    }
  }

  clearStationCode() {
    debugger
    this.paramsSearch.stationId = undefined;
    this.cableService.findAdvanceCable(this.paramsSearch).subscribe(res => {
      this.resultList = res.content;
      this.last =
        this.rows + this.first < this.resultList.totalRecords
          ? this.first + this.rows
          : this.resultList.totalRecords;
      this.searchStatus();
    });
  }

  clearDeptName() {
    this.paramsSearch.deptId = undefined;
    this.cableService.findAdvanceCable(this.paramsSearch).subscribe(res => {
      this.resultList = res.content;
      this.last =
        this.rows + this.first < this.resultList.totalRecords
          ? this.first + this.rows
          : this.resultList.totalRecords;
      this.searchStatus();
    });
  }

  clearInstallationDate() {
    this.formSearch.controls['installationDate'].setValue('');
  }

  onChangeRowSelectDept(event: any) {
    this.disabledLocationModal = !event;
    this.deptId = event.deptId;
    this.eventBusService.emit({
      value: event
    });
  }

  onClearRowSelect() {
    this.formSearch.controls['_deptId'].setValue(null);
    this.formSearch.controls['_stationId'].setValue(null);
    this.cableService.findListODFFist({ stationCode: null }).subscribe(res => {
      this.listODFFist = [];
      this.listODFFist.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      res.content.listData.forEach(element => {
        this.listODFFist.push({ label: element[1], value: element[0] });
      });
      this.cableService.findListODFEnd({ stationCode: null }).subscribe(res => {
        this.listODFEnd = [];
        this.listODFEnd.push({
          label: this.translation.translate('common.label.cboSelect'),
          value: null
        });
        res.content.listData.forEach(element => {
          this.listODFEnd.push({ label: element[1], value: element[0] });
        });
      });
    });
    this.isStationCode = false;
  }

  onRowDblClick(event, item) {
    this.showDetail(item.cableId, 'view');
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({
        severity: 'success',
        summary: '',
        detail: e['content'] + ' are copied!'
      });
    }
  }

  onChangeRowSelectStation(event: any) {
    this.stationId = event.stationId;
    this.isStationCode = true;
    this.cableService
      .findListODFFist({ stationId: event.stationId })
      .subscribe(res => {
        this.listODFFist = [];
        this.listODFFist.push({
          label: this.translation.translate('common.label.cboSelect'),
          value: null
        });
        res.content.listData.forEach(element => {
          this.listODFFist.push({ label: element[1], value: element[0] });
        });
      });

    this.cableService
      .findListODFEnd({ stationId: event.stationId })
      .subscribe(res => {
        this.listODFEnd = [];
        this.listODFEnd.push({
          label: this.translation.translate('common.label.cboSelect'),
          value: null
        });
        res.content.listData.forEach(element => {
          this.listODFEnd.push({ label: element[1], value: element[0] });
        });
      });
  }

  confirmExportExcel() {
    this.messageService.clear();
    this.messageService.add({
      key: 'excel',
      sticky: true,
      summary: this.translation.translate('common.message.confirm'),
      detail:
        this.selectedResult.length > 0
          ? this.translation.translate('station.confirm.export')
          : this.translation.translate('station.confirm.export.all')
    });
  }

  exportExcel() {
    this.messageService.clear('excel');
    this.spinner.show();
    if (this.selectedResult.length > 0) {
      let dataExport = [];
      dataExport = this.resultList.listData.filter(elem =>
        this.selectedResult.find(value => {
          return elem.cableId === value.cableId;
        })
      );
      this.cableService.excelChose(this.selectedResult).subscribe(s => {
        this.spinner.hide();
        if (s.body.size === 0) {
          this.messageService.add({
            key: 'cablelist',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('cable.export.error')
          });
          return;
        }
        saveAs(s.body, s.headers.get('File'));
        // saveAs(s.body, this.app.translation.translate(`document.download.filename`));
      });
    } else {
      // Export excel
      this.paramsSearch.first = null;
      this.paramsSearch.rows = null;
      this.cableService.export(this.paramsSearch).subscribe(s => {
        this.spinner.hide();
        if (s.body.size === 0) {
          this.messageService.add({
            key: 'cablelist',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('cable.export.error')
          });
          return;
        }
        saveAs(s.body, s.headers.get('File'));
        // saveAs(s.body, this.app.translation.translate(`document.download.filename`));
      });
    }
  }

  showDialog() {
    this.importDialog = true;
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

  downloadFileTemplate(type: any) {
    if (type === 1) {
      this.cableService
        .downloadFileTemplate(this.selectedResult)
        .subscribe(res => {
          if (res.body.size === 0) {
            return;
          }
          saveAs(res.body, res.headers.get('File'));
        });
    } else {
      this.cableService
        .downloadFileTemplateEdit(this.selectedResult)
        .subscribe(res => {
          if (res.body.size === 0) {
            return;
          }
          saveAs(res.body, res.headers.get('File'));
        });
    }
  }

  // importCableInStation() {
  //   if (this.file) {
  //     this.messageService.add({
  //       key: 'import',
  //       sticky: true,
  //       summary: this.translation.translate('common.message.confirm.import'),
  //       detail: '',
  //       data: 1
  //     });
  //   } else {
  //     this.messageService.add({
  //       key : 'cablelist',
  //       severity: 'error',
  //       summary: '',
  //       detail: this.translation.translate('station.import.error.require')
  //     });
  //   }
  // }

  changeFile(event, type) {
    if (type === 1) {
      this.file = event;
    } else {
      this.fileEdit = event;
    }
  }

  doImport(type?: number) {
    if (type === 1) {
      this.spinner.show();
      if (this.file) {
        this.cableService.importCableInStation(this.file).subscribe(
          res => {
            this.spinner.hide();
            if (res && res.status === 0) {
              this.messageService.add({
                key: 'cablelist',
                severity: 'error',
                summary: '',
                detail: this.translation.translate(
                  'station.import.error.file.type'
                )
              });
              return;
            }
            if (res == null || res.data == null) {
              this.messageService.add({
                key: 'cablelist',
                severity: 'error',
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
                  key: 'cablelist',
                  severity: 'success',
                  summary: '',
                  detail: this.translation.translate('station.import.success', {
                    success: success
                  })
                });
              } else {
                this.messageService.add({
                  key: 'cablelist',
                  severity: 'warn',
                  summary: '',
                  detail: this.translation.translate('station.import.warning', {
                    success: success,
                    error: err
                  })
                });
              }
              this.onReject('import');
            }
          },
          error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'cablelist',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          }
        );
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'cablelist',
          severity: 'warn',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.import.error.require')
        });
      }
    } else {
      if (this.fileEdit) {
        this.cableService.editCableInStation(this.fileEdit).subscribe(
          res => {
            this.spinner.hide();
            if (res && res.status === 0) {
              this.messageService.add({
                key: 'cablelist',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate(
                  'station.import.error.file.type'
                )
              });
              return;
            }
            if (res == null || res.data == null) {
              this.messageService.add({
                key: 'cablelist',
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
                  key: 'cablelist',
                  severity: 'success',
                  summary: '',
                  detail: this.translation.translate(
                    'cable.importEdit.success',
                    { success: success }
                  )
                });
              } else {
                this.messageService.add({
                  key: 'cablelist',
                  severity: 'warn',
                  summary: '',
                  detail: this.translation.translate(
                    'cable.importEdit.warning',
                    { success: success, error: err }
                  )
                });
              }
              this.onReject('import');
            }
          },
          error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'cablelist',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          }
        );
      } else {
        this.spinner.hide();
        this.messageService.add({
          key: 'cablelist',
          severity: 'warn',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.import.error.require')
        });
      }
    }
  }

  downloadResult(path) {
    if (path) {
      this.cableService.downloadFile(path).subscribe(res => {
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

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.keyCode === 13) {
      this.eventBusService.dataChange
        .subscribe(val => {
          console.log(val);
          if (val) {
            if (val.data.key === CableListComponent.name) {
              this.advanceSearchFunc();
            }
          }
        })
        .unsubscribe();
    }
  }
}
