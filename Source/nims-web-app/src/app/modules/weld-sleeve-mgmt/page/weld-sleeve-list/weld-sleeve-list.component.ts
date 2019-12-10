import {AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {Car} from '@app/modules/controls/common/car';
import {FormGroup} from '@angular/forms';
import {CarService} from '@app/modules/controls/carservice';
import {DataCommonService} from '@app/shared/services/data-common.service';
import {TranslationService} from 'angular-l10n';
import {PermissionService} from '@app/shared/services/permission.service';
import {MenuItem, MessageService} from 'primeng/api';
import {COLS_TABLE} from '@app/shared/services/constants';
import {CommonUtils} from '@app/shared/services';
import {WeldSleeveService} from '@app/modules/weld-sleeve-mgmt/service/weld-sleeve.service';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {AppComponent} from '@app/app.component';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {Table} from 'primeng/table';
import {SleeveService} from '@app/modules/sleeve-management/service/sleeve.service';
import {NgxSpinnerService} from 'ngx-spinner';
// import {FileSelectorComponent} from '@app/shared/components/file-selector/file-selector.component';
import {saveAs} from 'file-saver';
import {Cable} from '@app/modules/weld-sleeve-mgmt/service/cable';
import {Subscription} from 'rxjs';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {ContextMenu} from 'primeng/contextmenu';
import {KeyFilterModule} from 'primeng/keyfilter';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';
import {UploadControlComponent} from '@app/shared/components/upload-control/upload-control.component';

@Component({
  selector: 'weld-sleeve-list',
  templateUrl: './weld-sleeve-list.component.html',
  styleUrls: ['./weld-sleeve-list.component.css']
})
export class WeldSleeveListComponent implements OnInit, AfterViewInit {
  @ViewChild('fileF') fileF: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dt') table: Table;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  cols: any[];
  sourceCars: Car[];
  formSearch: FormGroup;
  advanceSearch = false;
  auditStatusList: any;
  resultList: any = [];
  first: number;
  rows: number;
  last: number;
  display = false;
  selectedResult: any = [];
  sourceCableCode: any = [];
  sleeveId: any;
  selectedColumns: any[];
  i: number;
  importDialog = false;
  file: any;
  fileEdit: any;
  resultImportAdd: any;
  resultImportEdit: any;
  @ViewChild(Table) tbl: Table;
  selectedRowData;
  paramsSearch = {
    first: 0,
    rows: 10,
    basicInfo: undefined,
    sortField: undefined,
    sortOrder: undefined,
    sleeveId: undefined,
    sleeveCode: undefined,
    sourceCableCode: undefined,
    sourceLineNo: undefined,
    destCableCode: undefined,
    destLineNo: undefined,
    createUser: undefined,
    attenuation: undefined
  };
  innerWidth;
  innerHeight;
  displayConfirmDelete = false;
  displayConflictConfirmDeleteObj = false;
  displayConflictConfirmDeleteArr = false;
  displayWarningMessDelete = false;
  messCheckDeleteWeldSleeveObj;
  messCheckDeleteWeldSleeveArr;
  warningMessDeleteWeldSleeve;
  sourceLineNo: any;
  cablesSoureName: any;
  items: MenuItem[];
  isCollapse = false;
  hrStorage: any;
  private reloadWeildSleeve: Subscription;

  constructor(
    private carService: CarService,
    private dataCommon: DataCommonService,
    private weldSleeveService: WeldSleeveService,
    private translation: TranslationService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private tabLayoutService: TabLayoutService,
    private app: AppComponent,
    private stationService: StationService,
    private sleeveService: SleeveService,
    private spinner: NgxSpinnerService,
    private eventBusService: EventBusService,
    // private dataCommon: DataCommonService
    private sysGridViewService: SysGridViewService
  ) {
  }

  ngOnDestroy(): void {
    CommonUtils.setColumns('weldsleeve', this.selectedColumns, this.sysGridViewService);
  }

  ngOnInit() {
    this.hrStorage = HrStorage.getUserToken();
    // get Cot theo user
    const columns = this.hrStorage.sysGridView;
    // this.gridId = this.hrStorage.sysGridView.filter(el =>this.se).gridId;
    this.cols = COLS_TABLE.STATION;

    // translate header
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    // set cot theo user
    this.selectedColumns = this.cols.filter((elem) => columns.find(data => {
      return elem.field === data.columnName && data.gridViewName === 'weldsleeve';
    }));
    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }
    // ------------------------------------------------
    this.cols = COLS_TABLE.WELD;
    this.sleeveId = this.sleeveService.item.sleeveId;
    this.getSleeveCode(this.sleeveId);
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    this.selectedColumns = this.cols;
    if (this.sleeveService.item) {
      // this.onProcess({rows: 10, first: 0, sleeveId: 1});
    }
    // this.onLazyLoad(event);
    // this.first = event.first;
    // this.rows = event.rows;
    // this.paramsSearch.first = event.first;
    // this.paramsSearch.rows = event.rows;
    // if (event.sortField) {
    //   this.paramsSearch.sortField = event.sortField;
    //   this.paramsSearch.sortOrder = event.sortOrder;
    // }
    // this.weldSleeveService.findBasicWeldSleeve(this.paramsSearch).subscribe(res => {
    //
    //   this.resultList = res.content;
    //   this.resultList.listData[7].sleeveId = 2;
    //   // console.log(this.paramsSearch.sleeveId);
    //   // this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
    // });
    // this.weldSleeveService.findBasicWeldSleeve(this.formSearch).subscribe(res => {
    //   this.resultList = res.content;
    //   console.log("61" + this.resultList.listData);
    //   console.log("62" + "-" + this.formSearch.value.sleeveId);
    //   console.log(this.weldSleeveService.sleeveId);
    //   this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
    // });
    // if(this.weldSleeveService.action == "SaveSucces"){
    //   this.weldSleeveService.findBasicWeldSleeve(this.formSearch).subscribe(res => {
    //     this.resultList = res.content;
    //     this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
    //   });
    // }
    // console.log(this.weldSleeveService.action);
    // console.log(this.resultList);
    // // this.auditStatusList = this.dataCommon.getDropDownList(AUDIT_STATUS);
    // // this.carService.getCarsSmall().then(cars => this.sourceCars = cars);
    // this.buildForm({});
    // this.sourceCableCode = this.dataCommon.
    this.reloadWeildSleeve = this.eventBusService.reloadWeldSleeveChange.subscribe(val => {
      this.table.reset();
    });
  }

  ngAfterViewInit(): void {
    this.onProcess({first: 0, rows: 10});
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  onReject(key) {
    this.messageService.clear(key);
  }

  showAdvance() {
    this.advanceSearch = true;
  }

  hideAdvance() {
    this.advanceSearch = false;
  }

  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.paramsSearch.first = event.first;
    this.paramsSearch.rows = event.rows;
    // this.paramsSearch.sleeveId = event.sleeveId;
    if (this.sleeveService.item) {
      this.paramsSearch.sleeveId = this.sleeveService.item.sleeveId;
    }
    if (event.sortField) {
      this.paramsSearch.sortField = event.sortField;
      this.paramsSearch.sortOrder = event.sortOrder;
    }
    this.weldSleeveService.findBasicWeldSleeve(this.paramsSearch).subscribe(res => {

      this.resultList = res.content;
      // console.log(this.paramsSearch.sleeveId);
      // this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
    });
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.260;
  }

  public totalRecordsLaneId(item) {
  }

  showDialog() {
    this.importDialog = true;
  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      basicInfo: [''],
      stationCode: [''],
      deptName: [''],
      locationName: [''],
      terrain: [''],
      stationTypeId: [null],
      auditStatus: [''],
      first: [''],
      recordsTotal: [''],
      rows: [''],
      sortField: [''],
      sortOrder: ['']
    });
  }

  onRowSelect(event: any, template?: any) {
  }

  // createdWeldPKObject(item) {
  //   const weld = new WeldModel();
  //   const list = new PK();
  //   list.sourceCableId = item.sourceCableId;
  //   list.sourceLineNo = item.sourceLineNo;
  //   list.sleeveId = item.sleeveId;
  //   list.destCableId = item.destCableId;
  //   list.destLineNo = item.destLineNo;
  //   weld.pk = list;
  //   return weld;
  // }

  onConfirm(item) {
    this.messageService.clear('c');
    if (!(item instanceof Array)) {
      item = [item];
    }
    this.weldSleeveService.deleteByFiveField(item).subscribe(s => {
      if (s.data == 1) {
        this.messageService.add({
          severity: 'success',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.delete.success')
        });
        this.onProcess({rows: 10, first: 0, sleeveId: this.sleeveService.item.sleeveId});
      } else {
        this.messageService.add({
          severity: 'error',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('weldingOdf.error.system')
        });
      }
    });
  }

  delete(item) {
    let summa;
    this.messageService.clear();
    this.weldSleeveService.checkSleeveIdLaneId(item).subscribe(check => {
      if (check.content.totalRecords == 1) {
        summa = this.translation.translate('weldingSleeve.tab.confirm', {
          source: item.sourceCableCode,
          dest: item.destCableCode,
          laneCable: item.laneCode
        });
      } else {
        summa = this.translation.translate('station.confirm.delete');
      }
      this.messageService.add({
        key: 'c',
        sticky: true,
        severity: 'warn',
        summary: summa,
        data: item,
      });
    });
  }

  // Save han noi tai mang xong
  save(action?: string) {
    this.weldSleeveService.sleeveId = this.paramsSearch.sleeveId;
    // this.weldSleeveService.sleeveId = 1;
    this.weldSleeveService.action = action;
    // this.tabLayoutService.close({
    //   component: 'WeldSleeveSaveComponent',
    // });
    this.tabLayoutService.open({
      component: 'WeldSleeveSaveComponent',
      header: 'weldingSleeve.tab.create.header',
      action: 'save',
      breadcrumb: [
        {label: this.translation.translate('infra.sleeves.management')},
        {label: this.translation.translate('weldding.tab.detail')},
        {label: this.translation.translate('weldding.tab.headder.weldsleeve')},
        {label: this.translation.translate('weldingSleeve.tab.create.header')},
      ]
    });
  }

  // Edit han noi tai mang xong
  edit(data?: any, action?: string, index?) {
    this.weldSleeveService.data = data;
    this.weldSleeveService.action = action;
    // this.tabLayoutService.close({
    //   component: 'WeldSleeveSaveComponent',
    // });
    this.weldSleeveService.index = index;
    this.tabLayoutService.open({
      component: 'WeldSleeveSaveComponent',
      header: 'weldingSleeve.tab.update.header',
      action: 'edit',
      tabId: index,
      breadcrumb: [
        {label: this.translation.translate('infra.sleeves.management')},
        {label: this.translation.translate('weldding.tab.detail')},
        {label: this.translation.translate('weldding.tab.headder.weldsleeve.at')},
        {label: this.translation.translate('weldingSleeve.tab.update.header')},
      ]
    });
  }

  deleteMultipe() {
    this.messageService.clear();
    if (this.selectedResult.length > 0) {
      this.messageService.add({
        key: 'c',
        sticky: true,
        severity: 'warn',
        summary: this.translation.translate('station.confirm.delete'),
        detail: this.translation.translate('station.confirm.delete'),
        data: this.selectedResult,
      });
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.warn.delete.multipe')
      });
    }
  }

  onDelete(selectedRowData, selectedRowDataList) {
    this.selectedRowData = selectedRowData;
    this.selectedResult = selectedRowDataList;
    if (this.selectedRowData) {
      const selectedRowDataObj = [{
        sleeveId: this.selectedRowData.sleeveId,
        laneId: this.selectedRowData.laneId,
        laneCode: this.selectedRowData.laneCode,
        sourceCableCode: this.selectedRowData.sourceCableCode,
        destCableCode: this.selectedRowData.destCableCode,
      }];
      this.spinner.show();
      this.weldSleeveService.checkSleeveIdLaneId(selectedRowDataObj).subscribe(res => {
        this.spinner.hide();
        setTimeout(() => {
          this.searchElement.nativeElement.focus();
        }, 0);
        if (res.message === 'Success') {
          this.displayConfirmDelete = true;
          this.displayConflictConfirmDeleteObj = false;
        } else {
          this.displayConfirmDelete = false;
          this.displayConflictConfirmDeleteObj = true;
          this.messCheckDeleteWeldSleeveObj = res.content[0];
        }
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      if (this.selectedResult.length === 1) {
        const selectedRowDataObj = [{
          sleeveId: this.selectedResult[0].sleeveId,
          laneId: this.selectedResult[0].laneId,
          laneCode: this.selectedResult[0].laneCode,
          sourceCableCode: this.selectedResult[0].sourceCableCode,
          destCableCode: this.selectedResult[0].destCableCode,
        }];
        this.spinner.show();
        this.weldSleeveService.checkSleeveIdLaneId(selectedRowDataObj).subscribe(res => {
          this.spinner.hide();
          setTimeout(() => {
            this.searchElement.nativeElement.focus();
          }, 0);
          if (res.message === 'Success') {
            this.displayConfirmDelete = true;
            this.displayConflictConfirmDeleteObj = false;
          } else {
            this.displayConfirmDelete = false;
            this.displayConflictConfirmDeleteObj = true;
            this.messCheckDeleteWeldSleeveObj = res.content[0];
          }
        });
      } else {
        const selectedRowDataListObj = [];
        this.selectedResult.forEach(it => {
          selectedRowDataListObj.push({
            sleeveId: it.sleeveId,
            laneId: it.laneId,
            laneCode: it.laneCode,
            sourceCableCode: it.sourceCableCode,
            destCableCode: it.destCableCode,
          });
        });
        this.spinner.show();
        this.weldSleeveService.checkSleeveIdLaneId(selectedRowDataListObj).subscribe(res => {
          this.spinner.hide();
          setTimeout(() => {
            this.searchElement.nativeElement.focus();
          }, 0);
          if (res.message === 'Success') {
            this.displayConfirmDelete = true;
            this.displayConflictConfirmDeleteArr = false;
          } else {
            this.displayConfirmDelete = false;
            this.displayConflictConfirmDeleteArr = true;
            this.messCheckDeleteWeldSleeveArr = res.content;
          }
        });
      }
    } else if ((this.selectedResult && this.selectedResult.length === 0 || this.selectedResult === undefined || this.selectedResult === null)) {
      this.displayConfirmDelete = false;
      this.messageService.add({
        key: 'weldSleeveList',
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.warn.delete.multipe')
      });
    }
  }

  onSubmitDelete() {
    if (this.selectedRowData) {
      const selectedRowDataObj = [{
        sourceCableId: this.selectedRowData.sourceCableId,
        sourceLineNo: this.selectedRowData.sourceLineNo,
        sleeveId: this.selectedRowData.sleeveId,
        destCableId: this.selectedRowData.destCableId,
        destLineNo: this.selectedRowData.destLineNo,
      }];
      this.spinner.show();
      this.weldSleeveService.deleteByFiveField(selectedRowDataObj).subscribe(res => {
        this.spinner.hide();
        this.displayConfirmDelete = false;
        this.displayConflictConfirmDeleteObj = false;
        this.displayConflictConfirmDeleteArr = false;
        this.messageService.add({
          key: 'deleteWeldSleeveSuccess',
          severity: 'success',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.delete.success')
        });
        this.table.reset();
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      if (this.selectedResult.length === 1) {
        const selectedRowDataObj = [{
          sourceCableId: this.selectedResult[0].sourceCableId,
          sourceLineNo: this.selectedResult[0].sourceLineNo,
          sleeveId: this.selectedResult[0].sleeveId,
          destCableId: this.selectedResult[0].destCableId,
          destLineNo: this.selectedResult[0].destLineNo,
        }];
        this.spinner.show();
        this.weldSleeveService.deleteByFiveField(selectedRowDataObj).subscribe(res => {
          this.spinner.hide();
          this.displayConfirmDelete = false;
          this.displayConflictConfirmDeleteObj = false;
          this.displayConflictConfirmDeleteArr = false;
          this.messageService.add({
            key: 'deleteWeldSleeveSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.table.reset();
        });
      } else {
        const selectedRowDataListObj = [];
        this.selectedResult.forEach(it => {
          selectedRowDataListObj.push({
            sourceCableId: it.sourceCableId,
            sourceLineNo: it.sourceLineNo,
            sleeveId: it.sleeveId,
            destCableId: it.destCableId,
            destLineNo: it.destLineNo
          });
        });
        this.spinner.show();
        this.weldSleeveService.deleteByFiveField(selectedRowDataListObj).subscribe(res => {
          this.spinner.hide();
          this.displayConfirmDelete = false;
          this.displayConflictConfirmDeleteObj = false;
          this.displayConflictConfirmDeleteArr = false;
          this.messageService.add({
            key: 'deleteWeldSleeveSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.table.reset();
        });
      }
    }
  }

  onHideConfirmDelete() {
    this.displayConfirmDelete = false;
  }

  onHideConflictConfirmDelete() {
    this.displayConflictConfirmDeleteObj = false;
    this.displayConflictConfirmDeleteArr = false;
    this.messCheckDeleteWeldSleeveObj = undefined;
    this.messCheckDeleteWeldSleeveArr = undefined;
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({severity: 'success', summary: '', detail: e['content'] + ' are copied!'});
    }
  }

  showDetail(data, action, index?) {
    this.weldSleeveService.data = data;
    this.weldSleeveService.action = action;

    this.tabLayoutService.open({
      component: 'WeldSleeveDetailComponent',
      header: 'wedding.sleeve.detail',
      action: 'view',
      tabId: index,
      breadcrumb: [
        {label: this.translation.translate('infra.sleeves.management')},
        {label: this.translation.translate('weldding.sleeve.view.detail')},
        {label: this.translation.translate('weldding.sleeve.headder')},
        {label: this.translation.translate('weldding.sleeve.view')},
      ]
    });
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
      this.stationService.downloadFileTemplate(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    } else {
      this.stationService.downloadFileTemplateEdit(this.selectedResult).subscribe(res => {
        if (res.body.size === 0) {
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    }
  }

  downloadResult(path) {
    if (path) {
      this.stationService.downloadFile(path).subscribe(res => {
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

  importStation() {
    this.messageService.add({
      key: 'import',
      sticky: true,
      summary: this.translation.translate('common.message.confirm.import'),
      detail: '',
      data: 1
    });
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

  clear() {
    this.messageService.clear();
  }

  doImport(type?: number) {
    if (type === 1) {
      this.spinner.show();
      if (this.file) {
        console.log(this.file);
        this.stationService.importStation(this.file).subscribe(res => {
          this.spinner.hide();
          if (res.data == null) {
            this.messageService.add({
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
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', {success: success})
              });
            } else {
              this.messageService.add({
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
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          severity: 'warn',
          summary: '',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    } else {
      if (this.fileEdit) {
        this.stationService.editStation(this.fileEdit).subscribe(res => {
          this.spinner.hide();
          if (res.data == null) {
            this.messageService.add({
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
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', {success: success})
              });
            } else {
              this.messageService.add({
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
            severity: 'error',
            summary: '',
            detail: this.translation.translate('station.import.undefined.err')
          });
        });
      } else {
        this.spinner.hide();
        this.messageService.add({
          severity: 'warn',
          summary: '',
          detail: this.translation.translate('station.import.error.require')
        });
      }
    }
  }


  searchInTable(event, field) {
    if (field === 'sleeveCode') {
      this.paramsSearch.sleeveCode = event;
    }
    if (field === 'sourceCableCode') {
      this.paramsSearch.sourceCableCode = event;
    }
    if (field === 'sourceLineNo') {
      this.paramsSearch.sourceLineNo = event;
    }
    if (field === 'destCableCode') {
      this.paramsSearch.destCableCode = event;
    }
    if (field === 'destLineNo') {
      this.paramsSearch.destLineNo = event;
    }
    if (field === 'createUser') {
      this.paramsSearch.createUser = event;
    }
    if (field === 'attenuation') {
      this.paramsSearch.attenuation = event;
    }
    this.paramsSearch.first = 0;
    this.paramsSearch.rows = 10;
    this.weldSleeveService.findBasicWeldSleeve(this.paramsSearch).subscribe(res => {
      this.spinner.hide();
      this.table.reset();
      this.resultList = res.content ? res.content : this.resultList.listData = [];
    });
  }

  filter(columns) {
    return columns.filter(val => {
      return val.typeFilter !== 'frozen';
    });
  }

// export
  exportExcel() {
    this.messageService.clear('excel');
    this.spinner.show();
    if (this.selectedResult.length > 0) {
      // dataExport = this.resultList.listData.filter((elem) => this.selectedResult.find(value => {
      //   return elem.sleeveId === value.sleeveId;
      //   return elem.sourceCableId === value.sourceCableId;
      //   return elem.sourceLineNo === value.sourceLineNo;
      //   return elem.destCableId === value.destCableId;
      //   return elem.destLineNo === value.destLineNo;
      //   return elem.sourceCableCode === value.sourceCableCode;
      //   return elem.destCableCode === value.destCableCode;
      // }));
      this.weldSleeveService.excelChose(this.selectedResult).subscribe(s => {
        this.spinner.hide();
        if (s.body.size === 0) {
          this.messageService.add({
            key: 'stationList',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.export.error')
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
      this.weldSleeveService.export(this.paramsSearch).subscribe(s => {
        this.spinner.hide();
        if (s.body.size === 0) {
          this.messageService.add({
            key: 'weldSleeveList',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('weldSleeve.export.error')
          });
          return;
        }
        saveAs(s.body, s.headers.get('File'));
        // saveAs(s.body, this.app.translation.translate(`document.download.filename`));
      });
    }
  }

  getSleeveCode(sleeveId: number) {
    this.weldSleeveService.getSleeveById(sleeveId).subscribe(res => {

      this.getListCableCode(res.holderId);
    }, er => {
    }, () => {
    });
  }

  getListCableCode(holderId) {
    this.weldSleeveService.getCableCode(holderId).subscribe(res => {
      this.cablesSoureName = [];
      this.cablesSoureName.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });

      for (let i = 0; i < res.length; i++) {
        this.cablesSoureName.push({label: res[i].cableCode, value: res[i].cableId});
      }
    });
  }

  onLinkRightClickedHeader(content: string, event: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          {label: 'Hiển thị một phần', icon: 'fa fa-compress', command: () => this.onCollapse()},
          {label: 'Hiển thị tất cả ', icon: 'fa fa-expand', command: () => this.onCollapse('expend')},
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
          {label: 'Copy', icon: 'fa fa-clipboard', command: () => this.onCopy(content)}
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
        key: 'copySuccess', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }

  onRowDblClick(event, item, index) {
    this.showDetail(item, 'view', index);
  }


}
