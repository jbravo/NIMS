import { AfterViewInit, Component, ElementRef, HostListener, OnDestroy, OnInit, Type, ViewChild } from '@angular/core';
import { CommonUtils } from '@app/shared/services';
import { FormGroup } from '@angular/forms';
import { COLS_TABLE } from '@app/shared/services/constants';
import { TranslationService } from 'angular-l10n';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { OdfService } from '../../service/odf.service';
import { SelectItem } from '@app/modules/controls/common/selectitem';
import { MenuItem, MessageService } from 'primeng/api';
import { ODFSaveComponent } from '../odf-save/odf-save.component';
import { Table } from 'primeng/components/table/table';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchStationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
import { EventBusService } from '@app/shared/services/event-bus.service';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import { ODFViewComponent } from '../odf-view/odf-view.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { AppComponent } from '@app/app.component';
import { Subscription } from 'rxjs';
import { PermissionService } from '@app/shared/services/permission.service';
import { ContextMenu } from 'primeng/contextmenu';
import { HrStorage } from '@app/core/services/auth/HrStorage';
// import { OdfStationsComponent } from '../odf-stations/odf-stations.component';
import { UploadControlComponent } from '@app/shared/components/upload-control/upload-control.component';

/**
 * Component ODF List data
 * Created by HungNV
 */
@Component({
  selector: 'odf-list',
  templateUrl: './odf-list.component.html',
  styleUrls: ['./odf-list.component.css']
})

export class OdfListComponent implements OnInit, AfterViewInit, OnDestroy {

  // ------------Properties--------------
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dt') dt: Table;
  @ViewChild('fileF') fileF: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  formSearch: FormGroup;
  cars: SelectItem[];
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  stationCodeSearch: Type<any> = AutocompleteSearchStationModalComponent;

  // ------------Variable--------------
  filteredDept: any[];
  filteredLocation: any[];
  auditStatusList: any[] = [];
  cols: any[];
  ths: any[];
  selectedColumns: any[];
  resultList: any = [];
  selectedResult: any = [];
  station: any;
  filteredStationSingle: any[];
  filteredOdfTypeCode: any[];
  filteredDeptNameSingle: any[];
  filteredVendorCode: any[];
  filteredOwnerCode: any[];
  dataSearchList: any;
  fileEdit: any;
  file: any;
  resultImportAdd: any;
  resultImportEdit: any;
  items: MenuItem[];
  isCollapse = false;

  showDetail = '';
  first: number;
  rows: number;
  last: number;
  stationId: number;

  action: string;
  typeImport: 'importOdf';

  advanceSearch = false;
  flagLazyLoad = false;
  isAdvanceSearchFunc = false;
  isStationCode = false;
  isOdfCode = false;
  isDeptName = false;
  importDialog = false;
  flagMessageSuccess = true;
  saveResult = [];
  hrStorage: any;
  innerWidth;
  innerHeight;

  doImportTypeNumber = 0;

  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  selectedRowData;
  warningMessDeleteConflictArr;
  warningMessDeleteRefCouplerArr;
  warningMessDeleteRefLineArr;
  warningMessDeleteRefCouplerLineArr;
  warningMessDeleteRefCouplerAndLineArr;
  warningMessDeleteConflictObj;
  warningMessDeleteRefCouplerObj;
  warningMessDeleteRefLineObj;
  warningMessDeleteRefCouplerLineObj;
  headerStation = this.app.translation.translate('common.dialog.header.station');
  headerDept = this.app.translation.translate('common.dialog.header.dept');

  /**
   * initial param search
   */
  paramsSearch = {
    odfCode: '',
    stationCode: '',
    stationId: '',
    deptName: '',
    deptPath: '',
    odfTypeCode: '',
    note: '',
    recordsTotal: '',
    first: 0,
    rows: 0,
    sortField: '',
    terrain: '',
    sortOrder: 1,
    rowStatus: '',
    vendorName: '',
    ownerId: '',
    installationDate: '',
    ownerName: ''
  };

  private reloadOdf: Subscription;
  private dataChangeSub: Subscription;

  /**
   * constructor
   */
  constructor(
    private app: AppComponent,
    private odfService: OdfService,
    private translation: TranslationService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private permissionService: PermissionService
  ) {
    this.buildForm({});
    this.getDataSearch();
    // begin not load data
    this.flagLazyLoad = false;
  }

  /**
   * form search control
   */
  get formControls() {
    return this.formSearch.controls;
  }

  /**
   * angular on initial
   */
  ngOnInit() {

    // set value for list column
    this.hrStorage = HrStorage.getUserToken();
    const columns = this.hrStorage.sysGridView;
    // this.selectedColumns = [];
    this.cols = COLS_TABLE.ODF;

    // translate header
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });

    // set cot theo user
    this.selectedColumns = this.cols.filter((elem) => columns.find(data => {
      return elem.field === data.columnName && data.gridViewName === 'odfList';
    }));

    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }

    // intit with columns default       DungPH
    this.setInnerWidthHeightParameters();
    this.messageService.clear('deleteOdfSuccess');
    if (this.odfService.listAction === 'reload') {
      console.log(123);
      if (this.odfService.saveOrEdit === 'save') {
        setTimeout(() => {
          this.messageService.add({
            severity: 'success',
            key: 'deleteOdfSuccess',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('odf.save.success')
          });
        }, 800);
      }
      if (this.odfService.saveOrEdit === 'edit') {
        setTimeout(() => {
          this.messageService.add({
            severity: 'success',
            key: 'deleteOdfSuccess',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('odf.update.success')
          });
        }, 800);
      }


      this.flagLazyLoad = true;
      this.onProcess({ first: 0, rows: 10 });
      this.odfService.listAction = '';
    }
    // this.typeImport = 'importOdf';

  }

  ngAfterViewInit(): void {
    if (this.odfService.listAction === 'reload') {
      this.onProcess({ first: 0, rows: 10 });
      this.odfService.listAction = '';
    }
  }

  ngOnDestroy(): void {
    if (this.dataChangeSub) {
      this.dataChangeSub.unsubscribe();
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter') {
      this.eventBusService.dataChange.subscribe(val => {
        console.log(val.data);
        if (val) {
          if (val.data.component === OdfListComponent.name) {
            if (this.importDialog) {
              this.doImport(this.doImportTypeNumber);
            } else {
              this.search();
            }

          }
        }
      }).unsubscribe();
    }
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.40;
  }

  search() {
    this.flagLazyLoad = true;
    this.dt.reset();
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  /**
   * Building form
   */
  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      odfCode: [''],
      stationCode: [''],
      stationId: [''],
      deptName: [''],
      deptPath: [''],
      odfTypeCode: [''],
      note: [''],
      recordsTotal: [''],
      first: [''],
      rows: [''],
      sortField: [''],
      terrain: [''],
      sortOrder: [1],
      rowStatus: [''],
      vendorName: [''],
      ownerId: [''],
      installationDate: [''],
      ownerName: ['']
    });
  }

  /**
   * Load data table
   */
  public onLazyLoad(event) {
    if (this.flagLazyLoad) {
      this.first = event.first;
      this.rows = event.rows;
      this.formSearch.controls['first'].setValue(event.first);
      this.formSearch.controls['rows'].setValue(event.rows);
      this.paramsSearch.rows = event.rows;
      this.paramsSearch.first = event.first;
      // click in sort in gridview
      if (event.sortField) {
        this.paramsSearch.sortField = event.sortField;
        this.paramsSearch.sortOrder = event.sortOrder;
        this.paramsSearch.odfTypeCode = event.odfTypeCode;
      }
      // in advance search
      // set data for paramsSearch
      this.createParamSearch();
      this.spinner.show();

      // call funct find advance search
      this.odfService.findAdvanceOdf(this.paramsSearch).subscribe(res => {
        this.spinner.hide();
        this.resultList = res.content;
        this.last =
          (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      });
      // this.paramsSearch.first = 0;
      // this.paramsSearch.rows = -1;
      // this.odfService.findAdvanceOdf(this.paramsSearch).subscribe(res => {
      //   this.saveResult = [];
      //   this.saveResult = res.content.listData;
      // });
    }

  }

  createParamSearch() {
    this.paramsSearch.sortField = this.paramsSearch.sortField ? this.paramsSearch.sortField : undefined;
    this.paramsSearch.sortOrder = this.paramsSearch.sortOrder ? this.paramsSearch.sortOrder : undefined;
    this.paramsSearch.deptName = this.formSearch.value.deptName ? this.formSearch.value.deptName : '';
    this.paramsSearch.deptPath =
      this.formSearch.value.deptPath.pathName
        ? this.formSearch.value.deptPath.pathName
        : '';
    this.paramsSearch.stationCode
      = this.formSearch.value.stationId.stationCode ? this.formSearch.value.stationId.stationCode
        : this.formSearch.value.stationCode ? this.formSearch.value.stationCode
          : this.paramsSearch.stationCode ? this.paramsSearch.stationCode
            : '';
    this.paramsSearch.installationDate = this.formSearch.value.installationDate ? this.formSearch.value.installationDate : '';
    this.paramsSearch.note = this.formSearch.value.note ? this.formSearch.value.note : '';
    this.paramsSearch.odfCode = this.formSearch.value.odfCode ? this.formSearch.value.odfCode : '';
    this.paramsSearch.odfTypeCode = this.formSearch.value.odfTypeCode ? this.formSearch.value.odfTypeCode : '';
    this.paramsSearch.ownerId = this.formSearch.value.ownerId ? this.formSearch.value.ownerId : '';
    this.paramsSearch.ownerName
      = this.formSearch.value.ownerName.value ? this.formSearch.value.ownerName.value
        : this.formSearch.value.ownerName ? this.formSearch.value.ownerName : '';
    this.paramsSearch.recordsTotal = this.formSearch.value.recordsTotal ? this.formSearch.value.recordsTotal : '';
    this.paramsSearch.rowStatus = this.formSearch.value.rowStatus ? this.formSearch.value.rowStatus : '';
    this.paramsSearch.terrain = this.formSearch.value.terrain ? this.formSearch.value.terrain : '';
    this.paramsSearch.vendorName
      = this.formSearch.value.vendorName.value ? this.formSearch.value.vendorName.value
        : this.formSearch.value.vendorName ? this.formSearch.value.vendorName : '';
  }

  /**
   * Show search advance
   */
  public showAdvance() {
    this.buildForm({});
    this.flagLazyLoad = true;
    this.advanceSearch = !this.advanceSearch;
    this.formSearch.controls['first'].setValue(this.first);
    this.formSearch.controls['rows'].setValue(this.rows);
    this.getDataSearch();
    this.dt.reset();
  }

  /**
   * get data for form seach advance
   */
  getDataSearch() {
    this.odfService.getDataSearchAdvance().subscribe(res => {
      const resultListOdfType = [];
      const resultListOwner = [];
      const resultListVendor = [];
      this.dataSearchList = res;
      res.content.listData.map(element => {
        resultListOdfType.push(({ label: element.odfTypeCode, value: element.odfTypeId }));
        resultListOwner.push(({ label: element.ownerName, value: element.ownerId }));
        resultListVendor.push(({ label: element.vendorName, value: element.vendorId }));
      });

      this.filteredOdfTypeCode = this.removeDuplicate(resultListOdfType, true);
      this.filteredOwnerCode = this.removeDuplicate(resultListOwner, true);
      this.filteredVendorCode = this.removeDuplicate(resultListVendor, true);
    });
  }

  removeDuplicate(listData, hasOption?: boolean, hasValue?: boolean) {
    const result = [];
    const map = new Map();
    if (hasOption) {
      result.push({ label: this.translation.translate('common.label.cboSelect'), value: '' });
    }
    listData.sort(
      function (a, b) {
        return a.value - b.value;
      }).map(function (item) {
        if (!map.has(item.label) && item.label !== null) {
          map.set(item.label, true);
          if (hasValue) {
            result.push({ label: item.label, value: item.value });
          } else {
            result.push({ label: item.label, value: item.label });
          }
        }
      });
    console.log(result);
    return result;
  }

  /**
   * Filter station
   */
  filterStationSingle(event) {
    const query = event.query;
    this.filterStation(query, this.dataSearchList.content.listData);
  }

  filterStation(query, stations: any[]) {
    const filtered: any[] = [];
    for (let i = 0; i < stations.length; i++) {
      const station = stations[i];
      if (null === station.stationCode) {
        continue;
      }
      if (station.stationCode.toLowerCase().includes(query.toLowerCase())) {
        filtered.push({ label: station.stationCode, value: station.StationCode });
      }
    }
    this.filteredStationSingle = this.removeDuplicate(filtered, false, true);
  }

  /**
   * Filter department name
   */
  filterDeptNameSingle(event) {
    const query = event.query;
    this.filteredDeptNameSingle = this.filterDeptName(query, this.dataSearchList.content.listData);
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

  /**
   * set value for select value
   */
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formSearch.controls[element].setValue(event.value.value);
    } else {
      this.formSearch.controls[element].setValue('');
    }
  }

  onRowSelect(event: any, template?: any) {
  }

  onRowUnselect(event: any) {
  }

  open(component: string) {
    this.tabLayoutService.open({
      component: component,
      header: 'ODF Add'
    });
  }

  delete(item) {
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: this.translation.translate('odf.confirm.delete'),
      data: item
    });
  }

  /**
   * message delete success
   */
  messageDeleteSuccess() {
    this.flagMessageSuccess = true;
    this.messageService.add({
      severity: 'success',
      key: 'deleteOdfSuccess',
      summary: this.translation.translate('common.label.NOTIFICATIONS'),
      detail: this.translation.translate('odf.delete.success')
    });
    this.dt.reset();
  }

  /**
   * message error when odf deleted
   */
  messageDeletedError(status) {
    this.messageService.add({
      severity: 'error',
      key: 'align',
      life: 20000,
      sticky: true,
      summary: this.translation.translate('common.label.NOTIFICATIONS'),
      detail: this.translation.translate('odf.delete.error') + status.odfCode
    });
  }

  /**
   * message delete infomation when error
   */
  messageDeleteInfoError(status) {
    let messageInfoError = '';
    if (!status.notRefCoupler) {
      messageInfoError += `
                ${this.translation.translate('odf.delete.error.coppler1')} ${status.infoCouplerRef}
                ${this.translation.translate('odf.delete.error.coppler2')} ${status.infoCouplerSourceOdfRef}
                ${this.translation.translate('odf.delete.error.coppler3')} ${status.infoCouplerDisOdfRef}
                ${this.translation.translate('odf.delete.error.coppler4')}
                `;
    }

    if (!status.notRefLine) {

      messageInfoError += `
                ${this.translation.translate('odf.delete.error.line1')} ${status.infoLineRef}
                ${this.translation.translate('odf.delete.error.line2')} ${status.odfCode}
                ${this.translation.translate('odf.delete.error.line3')}
                `;
    }
    return messageInfoError;
  }

  onConfirm(item) {
    let countStatus = 0;
    this.messageService.clear('c');
    if (!(item instanceof Array)) {
      item = [item];
    }
    this.odfService.delete(item).subscribe(s => {
      this.messageService.clear('c');
      let messageDetail = '';
      s.data.forEach(status => {
        if (true === status.notRefCoupler && true === status.notRefLine) {
          this.flagMessageSuccess = status.infoErrorDelete;
          if (this.flagMessageSuccess) {
            //  s
          } else {
            countStatus++;
            this.messageDeletedError(status);
          }
        } else {
          countStatus++;
          // Conflict coupler/ref
          messageDetail += this.messageDeleteInfoError(status);
        }
      });
      if (countStatus === 0) {
        this.messageDeleteSuccess();
      } else {
        this.messageService.add({
          severity: 'error',
          key: 'align',
          life: (20000 * 9999999),
          sticky: true,
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: messageDetail
        });
      }
    });
    if (this.flagMessageSuccess === true) {
    } else {
    }
  }

  onReject() {
    this.messageService.clear('c');
  }

  deleteMultiple() {
    if (this.selectedResult.length > 0) {
      this.messageService.clear();
      this.messageService.add({
        key: 'c',
        sticky: true,
        severity: 'warn',
        summary: this.translation.translate('odf.confirm.delete'),
        data: this.selectedResult
      });
    } else {
      this.messageService.add({
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
      // tslint:disable-next-line:max-line-length
    } else if (this.selectedResult && this.selectedResult.length === 0 || this.selectedResult === undefined || this.selectedResult === null) {
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
    if (this.selectedRowData) {
      this.displayWarningMessDelete = false;
      const dataObj = [this.selectedRowData];
      this.odfService.delete(dataObj).subscribe(res => {
        this.spinner.hide();
        const deleteSuccessData = res.data.find(it => it.notRefCoupler === true && it.notRefLine === true && it.infoErrorDelete === true);
        const deleteConflictData = res.data.find(it => it.notRefCoupler === true && it.notRefLine === true && it.infoErrorDelete === false);
        const deleteRefCouplerData = res.data.find(it => it.notRefCoupler === false && it.notRefLine === true);
        const deleteRefLineData = res.data.find(it => it.notRefCoupler === true && it.notRefLine === false);
        const deleteRefLineAndCouplerData = res.data.find(it => it.notRefCoupler === false && it.notRefLine === false);
        if (deleteConflictData) {
          this.warningMessDeleteConflictObj = deleteConflictData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if (deleteRefCouplerData) {
          this.warningMessDeleteRefCouplerObj = deleteRefCouplerData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if (deleteRefLineData) {
          this.warningMessDeleteRefLineObj = deleteRefLineData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if (deleteRefLineAndCouplerData) {
          this.warningMessDeleteRefCouplerLineObj = deleteRefLineAndCouplerData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if (deleteSuccessData) {
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = false;
          this.messageService.add({
            key: 'deleteOdfSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.onProcess({ first: 0, rows: 10 });
        }
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      this.odfService.delete(this.selectedResult).subscribe(res => {
        this.spinner.hide();
        const deleteSuccessData = res.data.filter(it => it.notRefCoupler === true && it.notRefLine === true && it.infoErrorDelete === true);
        const deleteConflictData
          = res.data.filter(it => it.notRefCoupler === true && it.notRefLine === true && it.infoErrorDelete === false);
        const deleteRefCouplerData = res.data.filter(it => it.notRefCoupler === false && it.notRefLine === true);
        const deleteRefLineData = res.data.filter(it => it.notRefCoupler === true && it.notRefLine === false);
        const deleteRefCouplerAndLineData = res.data.filter(it => it.notRefCoupler === false && it.notRefLine === false);
        if (deleteSuccessData.length > 0 && deleteConflictData.length > 0) {
          this.warningMessDeleteConflictArr = deleteConflictData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        }
        if (deleteSuccessData.length > 0 && deleteRefCouplerData.length > 0 && deleteRefLineData.length === 0) {
          this.warningMessDeleteRefCouplerArr = deleteRefCouplerData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        }
        if (deleteRefCouplerData.length > 0 && deleteRefLineData.length === 0) {
          this.warningMessDeleteRefCouplerArr = deleteRefCouplerData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        }
        if (deleteSuccessData.length > 0 && deleteRefLineData.length > 0 && deleteRefCouplerData.length === 0) {
          this.warningMessDeleteRefLineArr = deleteRefLineData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        }
        if (deleteRefLineData.length > 0 && deleteRefCouplerData.length === 0) {
          this.warningMessDeleteRefLineArr = deleteRefLineData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        }
        if (deleteRefCouplerData.length > 0 && deleteRefLineData.length > 0) {
          this.warningMessDeleteRefCouplerLineArr = deleteRefCouplerData.concat(deleteRefLineData);
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        }
        if (deleteRefCouplerAndLineData.length > 0) {
          this.warningMessDeleteRefCouplerAndLineArr = deleteRefCouplerAndLineData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if (deleteSuccessData.length > 0) {
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = false;
          this.messageService.add({
            key: 'deleteOdfSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
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
    this.warningMessDeleteConflictArr = undefined;
    this.warningMessDeleteRefCouplerArr = undefined;
    this.warningMessDeleteRefLineArr = undefined;
    this.warningMessDeleteRefCouplerLineArr = undefined;
    this.warningMessDeleteRefCouplerAndLineArr = undefined;
    this.warningMessDeleteConflictObj = undefined;
    this.warningMessDeleteRefCouplerObj = undefined;
    this.warningMessDeleteRefLineObj = undefined;
    this.warningMessDeleteRefCouplerLineObj = undefined;
  }

  clear() {
    this.messageService.clear();
  }

  saveOrEdit(id?: number, action?: string) {
    this.odfService.id = id;
    this.odfService.action = action;

    let selectComponent = ODFSaveComponent.name;
    let selectHeader = 'odf.tab.save';
    let actionS;

    if (this.odfService.action === 'view') {
      selectComponent = ODFViewComponent.name;
      selectHeader = 'odf.tab.detail';
      // this.tabLayoutService.close({
      //   component: selectComponent,
      //   header: selectHeader,
      //   action: 'edit',
      //   // key: selectComponent,
      //   tabId: (id * 121),
      //   breadcrumb: [
      //     { label: this.translation.translate('odf.tab.list') },
      //     { label: this.translation.translate(selectHeader) }
      //   ]
      // });
    }

    if (this.odfService.action === 'edit') {
      selectComponent = ODFSaveComponent.name;
      selectHeader = 'odf.tab.update';
      actionS = 'edit';
      // this.tabLayoutService.close({
      //   component: selectComponent,
      //   header: selectHeader,
      //   action: 'edit',
      //   // key: selectComponent,
      //   tabId: (id * 121),
      //   breadcrumb: [
      //     { label: this.translation.translate('odf.tab.list') },
      //     { label: this.translation.translate(selectHeader) }
      //   ]
      // });
    }

    this.tabLayoutService.open({
      component: selectComponent,
      header: selectHeader,
      action: actionS,
      // key: selectComponent,
      tabId: (id * 121),
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        { label: this.translation.translate(selectHeader) }
      ]
    });
  }

  onBlurInput(data) {
    if (data === 'stationCode' && this.isStationCode === false) {
      this.formSearch.controls['stationCode'].setValue('');
    }

    if (data === 'deptPath' && this.isDeptName === false) {
      this.formSearch.controls['deptPath'].setValue('');
    }
  }

  onSelect(data) {
    if (data === 'stationCode') {
      this.isStationCode = true;
    }
    if (data === 'deptPath') {
      this.isDeptName = true;
    }
  }

  onClear(data) {
    if (data === 'stationCode') {
      this.isStationCode = false;
    }
    if (data === 'deptPath') {
      this.isDeptName = false;
    }
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({ severity: 'success', summary: '', detail: this.translation.translate('common.label.copied') });
    }
  }


  confirmExportExcel() {
    this.messageService.clear();
    this.messageService.add({
      key: 'excelExport',
      sticky: true,
      summary: this.translation.translate('common.message.confirm'),
      detail: this.selectedResult.length > 0 ?
        this.translation.translate('station.confirm.export') :
        this.translation.translate('station.confirm.export.all')
    });
  }

  exportExcel() {
    this.paramsSearch.first = 0;
    this.paramsSearch.rows = -1;
    this.saveResult = [];
    if (this.selectedResult && this.selectedResult.length > 0) {
      this.sendListExport(this.selectedResult);
    } else {
      this.odfService.findAdvanceOdf(this.paramsSearch).subscribe(res => {
        if (res.content && res.content.listData.length > 0) {
          this.saveResult = res.content.listData;
          this.sendListExport(this.saveResult);
        } else {
          this.messageService.clear('deleteOdfSuccess');
          this.messageService.add({
            key: 'deleteOdfSuccess',
            severity: 'error',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('odf.export.noData')
          });
        }
      });
    }
  }

  sendListExport(item) {
    const sendItem = [];
    sendItem.push(item);
    this.odfService.excelChoose(item).subscribe(s => {
      if (s.body.size === 0) {
        this.messageService.add({
          severity: 'warn',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.export.error')
        });
        return;
      }
      saveAs(s.body, s.headers.get('File'));
      // saveAs(s.body, this.app.translation.translate(`document.download.filename`));
    });
  }

  searchInTable(event, field) {
    // this.advanceSearchFunc(false);
    console.log(event);
    this.flagLazyLoad = false;
    if (field === 'odfCode') {
      this.paramsSearch.odfCode = event;
    }
    if (field === 'stationCode') {
      this.paramsSearch.stationCode = event;
    }
    if (field === 'deptPath') {
      console.log(event);
      this.paramsSearch.deptName = event;
    }
    if (field === 'odfTypeCode') {
      this.paramsSearch.odfTypeCode = event.value;
    }
    if (field === 'ownerName') {
      this.paramsSearch.ownerName = event.value;
    }
    if (field === 'vendorName') {
      this.paramsSearch.vendorName = event.value;
    }
    if (field === 'note') {
      this.paramsSearch.note = event;
    }
    if (field === 'installationDate') {
      this.paramsSearch.installationDate = event;
      // this.paramsSearch.installationDate = event.toLocaleDateString();
    }
    this.spinner.show();
    // this.onLazyLoad({ first: 0, rows: 10 });
    this.odfService.findAdvanceOdf(this.paramsSearch).subscribe(res => {
      if (res.content) {
        this.resultList = res.content;
      }
      this.spinner.hide();
    });
    // this.exportExcel();
  }

  onChangeRowSelectStation(event: any) {
    this.stationId = event.stationId;
  }

  onClearRowSelect(event) {
    this.formSearch.controls[event].setValue('');
  }

  onChangeRowSelectDept(event: any) {
    this.eventBusService.emit({
      type: 'stationList',
      deptDataObj: event
    });
  }

  showDialog(type?: string) {
    this.typeImport = 'importOdf';
    this.importDialog = true;
    // this.resultImportAdd = true;
  }

  closeImportPopup() {
    this.fileF.setEmptyFile();
    this.importDialog = false;
    this.file = null;
    this.fileEdit = null;
    this.resultImportAdd = null;
    this.resultImportEdit = null;
  }

  downloadFileTemplate(type: any) {
    if (type === 1) {
      this.odfService.downloadFileTemplate({ result: this.selectedResult, type: this.typeImport }).subscribe(res => {
        if (res.body.size === 0) {
          this.app.warningMessage('messages.warn.organizationcontroller.download');
          return;
        }
        saveAs(res.body, res.headers.get('File'));
        // console.log(this.typeImport);
      });
    } else if (type === 2) {
      this.odfService.downloadFileTemplateEdit({ result: this.selectedResult, type: this.typeImport }).subscribe(res => {
        if (res.body.size === 0) {
          this.app.warningMessage('messages.warn.organizationcontroller.download');
          return;
        }
        saveAs(res.body, res.headers.get('File'));
        // console.log(this.typeImport);
      });
    }

  }

  onImportTabChange(event) {
    // console.log(event);
    this.doImportTypeNumber = event.index;
    this.typeImport = 'importOdf';
    console.log('on this day');
    this.changeFile(null, 0);
    // if (event.index === 0) {
    //   this.doImportTypeNumber = 0;
    // }
  }

  doImport(type?: number) {
    // this.doImportTypeNumber = type;
    if (this.doImportTypeNumber === 0) {
      console.log('Thêm mới');
      this.spinner.show();
      if (this.file) {
        if (this.typeImport === 'importOdf') {
          this.odfService.importOdf(this.file).subscribe(res => {
            this.spinner.hide();
            if (res == null || res.data == null) {
              this.messageService.add({
                severity: 'warn',
                summary: '',
                key: 'deleteOdfSuccess',
                detail: this.translation.translate('station.import.error')
              });
              return;
            }
            if (res.status === 0) {
              this.messageService.add({
                key: 'deleteOdfSuccess',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.error.file.type')
              });
              return;
            } else {
              const path = res.data.split('+', 3);
              this.resultImportAdd = path[0];
              const success = +path[1];
              const err = +path[2];
              if (err === 0 && success !== 0) {
                this.messageService.add({
                  severity: 'success',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.success', { success: success })
                });
              } else {
                this.messageService.add({
                  severity: 'warn',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.warning', { success: success, error: err })
                });
              }
              this.onReject();
            }

          }, error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'deleteOdfSuccess',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          });
        } else if (this.typeImport === 'importCoupleToLineOdf') {
          this.odfService.importCoupleToLineOdf(this.file).subscribe(res => {
            this.spinner.hide();
            if (res == null || res.data == null) {
              this.messageService.add({
                severity: 'warn',
                summary: '',
                key: 'deleteOdfSuccess',
                detail: this.translation.translate('station.import.error')
              });
              return;
            }
            if (res.status === 0) {
              this.messageService.add({
                key: 'deleteOdfSuccess',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.error.file.type')
              });
              return;
            } else {
              const path = res.data.split('+', 3);
              this.resultImportAdd = path[0];
              const success = +path[1];
              const err = +path[2];
              if (err === 0 && success !== 0) {
                this.messageService.add({
                  severity: 'success',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.success', { success: success })
                });
              } else {
                this.messageService.add({
                  severity: 'warn',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.warning', { success: success, error: err })
                });
              }
              this.onReject();
            }
          }, error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'deleteOdfSuccess',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          });
        } else if (this.typeImport === 'importCoupleToCoupleOdf') {
          this.odfService.importCoupleToCoupleOdf(this.file).subscribe(res => {
            this.spinner.hide();
            if (res == null || res.data == null) {
              this.messageService.add({
                severity: 'warn',
                summary: '',
                key: 'deleteOdfSuccess',
                detail: this.translation.translate('station.import.error')
              });
              return;
            }
            if (res.status === 0) {
              this.messageService.add({
                key: 'deleteOdfSuccess',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.error.file.type')
              });
              return;
            } else {
              const path = res.data.split('+', 3);
              this.resultImportAdd = path[0];
              const success = +path[1];
              const err = +path[2];
              if (err === 0 && success !== 0) {
                this.messageService.add({
                  severity: 'success',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.success', { success: success })
                });
              } else {
                this.messageService.add({
                  severity: 'warn',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.warning', { success: success, error: err })
                });
              }
              this.onReject();
            }
          }, error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'deleteOdfSuccess',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          });
        }

      } else {
        this.spinner.hide();
        this.messageService.add({
          severity: 'warn',
          summary: '',
          key: 'deleteOdfSuccess',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    } else if (this.doImportTypeNumber === 1) {
      console.log('Cập nhật');
      this.spinner.show();
      if (this.file) {
        if (this.typeImport === 'importOdf') {
          this.odfService.importUpdateOdf(this.file).subscribe(res => {
            this.spinner.hide();
            if (res == null || res.data == null) {
              this.messageService.add({
                severity: 'warn',
                summary: '',
                key: 'deleteOdfSuccess',
                detail: this.translation.translate('station.import.error')
              });
              return;
            }
            if (res.status === 0) {
              this.messageService.add({
                key: 'deleteOdfSuccess',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.error.file.type')
              });
              return;
            } else {
              const path = res.data.split('+', 3);
              this.resultImportEdit = path[0];
              const success = +path[1];
              const err = +path[2];
              if (err === 0 && success !== 0) {
                this.messageService.add({
                  severity: 'success',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.success', { success: success })
                });
              } else {
                this.messageService.add({
                  severity: 'warn',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.warning', { success: success, error: err })
                });
              }
              this.onReject();
            }
          }, error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'deleteOdfSuccess',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          });
        } else if (this.typeImport === 'importCoupleToLineOdf') {
          this.odfService.importUpdateCoupleToLineOdf(this.file).subscribe(res => {
            this.spinner.hide();
            if (res == null || res.data == null) {
              this.messageService.add({
                severity: 'warning',
                summary: '',
                key: 'deleteOdfSuccess',
                detail: this.translation.translate('station.import.error')
              });
              return;
            }
            if (res.status === 0) {
              this.messageService.add({
                key: 'deleteOdfSuccess',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.error.file.type')
              });
              return;
            } else {
              const path = res.data.split('+', 3);
              this.resultImportEdit = path[0];
              const success = +path[1];
              const err = +path[2];
              if (err === 0 && success !== 0) {
                this.messageService.add({
                  severity: 'success',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.success', { success: success })
                });
              } else {
                this.messageService.add({
                  severity: 'warn',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.warning', { success: success, error: err })
                });
              }
              this.onReject();
            }
          }, error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'deleteOdfSuccess',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          });
        } else if (this.typeImport === 'importCoupleToCoupleOdf') {
          this.odfService.importUpdateCoupleToCoupleOdf(this.file).subscribe(res => {
            this.spinner.hide();
            if (res == null || res.data == null) {
              this.messageService.add({
                severity: 'warning',
                summary: '',
                key: 'deleteOdfSuccess',
                detail: this.translation.translate('station.import.error')
              });
              return;
            }
            if (res.status === 0) {
              this.messageService.add({
                key: 'deleteOdfSuccess',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('station.import.error.file.type')
              });
              return;
            } else {
              const path = res.data.split('+', 3);
              this.resultImportEdit = path[0];
              const success = +path[1];
              const err = +path[2];
              if (err === 0 && success !== 0) {
                this.messageService.add({
                  severity: 'success',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.success', { success: success })
                });
              } else {
                this.messageService.add({
                  severity: 'warn',
                  summary: '',
                  key: 'deleteOdfSuccess',
                  detail: this.translation.translate('station.import.warning', { success: success, error: err })
                });
              }
              this.onReject();
            }
          }, error => {
            this.spinner.hide();
            this.messageService.add({
              key: 'deleteOdfSuccess',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('station.import.undefined.err')
            });
          });
        }

      } else {
        this.spinner.hide();
        this.messageService.add({
          severity: 'warn',
          summary: '',
          key: 'deleteOdfSuccess',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    }
  }


  changeFile(event, type) {

    if (type === 0) {
      this.file = null;
      this.fileEdit = null;
    } else if (type === 1 || type === 2) {

        this.file = event;
      } else {
        this.fileEdit = event;
      }

  }


  downloadResult(path) {
    if (path) {
      this.odfService.downloadFile(path).subscribe(res => {
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

  onBlurDateFilter(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formSearch.controls['installationDate'].setValue(null);
        this.paramsSearch.installationDate = null;
        this.onProcess({ first: 0, rows: 10 });
      }
    }
  }

  onClearClickDateFilter(event) {
    this.searchInTable('', 'installationDate');
    // this.formSearch.controls['installationDate'].setValue(null);
    // if (event) {
    //   this.paramsSearch.installationDate = null;
    //   this.onProcess({ first: 0, rows: 10 });
    // }
  }

  // =====================
  clearDeptId() {
    this.station = '';
  }

  onClearDatePicker() {
    this.paramsSearch.installationDate = '';
  }


  onLinkRightClickedHeader(content: string, event: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          { label: 'Hiển thị một phần', icon: 'fa fa-compress', command: () => this.onCollapse() },
          { label: 'Hiển thị tất cả ', icon: 'fa fa-expand', command: () => this.onCollapse('expend') }
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

  onCollapse(type?: string) {
    this.isCollapse = type === 'expend';
  }

  onCopy(event) {
    if (event) {
      this.messageService.add({
        key: 'deleteOdfSuccess', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }

  callFunctionFilter(event, field) {
    console.log(event);
    if (field === 'deptPath') {
      this.filteredDeptFunc(event);
    }
    if (field === 'locationName') {
      this.filteredStationFunc(event);
    }
  }

  filteredDeptFunc(event) {
    this.permissionService.filterDept(event.query).subscribe(result => {
      this.filteredDept = [];
      result.content.listData.map(item => {
        this.filteredDept.push({ label: item.pathName, value: item.deptName });
      });
      console.log(this.filteredDept);
    });
  }

  filteredStationFunc(event) {
    this.permissionService.findStation({ stationCode: event.query, first: 0, rows: 10 }).subscribe(result => {
      this.filteredStationSingle = [];
      result.content.listData.map(item => {
        this.filteredStationSingle.push({ label: item.stationCode, value: item.stationCode });
      });
    });
  }
}
