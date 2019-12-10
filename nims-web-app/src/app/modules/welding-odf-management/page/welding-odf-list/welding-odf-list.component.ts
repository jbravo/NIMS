import { Component, OnInit, ViewChild, ElementRef, HostListener, OnDestroy, AfterViewInit, Input } from '@angular/core';
import { PermissionService } from '@app/shared/services/permission.service';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { TranslationService } from 'angular-l10n';
import { WeldingOdfService } from '../../service/welding-odf.service';
import { FormGroup, FormControl } from '@angular/forms';
import { CommonUtils } from '@app/shared/services';
import { COLS_TABLE } from '@app/shared/services/constants';
import { Observable, from, Subscription } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BreadcrumbService } from '@app/shared/services/breadcrumb.service';
import { SelectItem, MessageService, MenuItem } from 'primeng/api';
import { AppComponent } from '@app/app.component';
import { WeldingOdfUpdateComponent } from '../welding-odf-update/welding-odf-update.component';
import { WeldingOdfDetailComponent } from '../welding-odf-detail/welding-odf-detail.component';
import { OdfService } from '@app/modules/odfs-mgmt/service/odf.service';
import { Table } from 'primeng/table';
import { NgxSpinnerService } from 'ngx-spinner';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { ContextMenu } from 'primeng/primeng';
import { HrStorage } from '@app/core/services/auth/HrStorage';

@Component({
  selector: 'welding-odf-list',
  templateUrl: './welding-odf-list.component.html',
  styleUrls: ['./welding-odf-list.component.css']
})
export class WeldingOdfListComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('dt') private _table: Table;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('createDate') createDate: ElementRef;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  private reloadWeldingOdfList: Subscription;
  @Input() data;
  id: any;
  odfId: any;
  couplerNo: any;
  backupList: any = [];
  cols: any[];
  resultList: any[] = [];
  weldingOdfList: any[] = [];
  selectedResult: any = [];
  selectedColumns: any[];
  formAdd: FormGroup;
  types: any[];
  selectedRowData;
  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  warningMessDeleteWeldingOdf;
  items: MenuItem[];
  isCollapse = false;
  hrStorage: any;
  private weldingCreate: Subscription;
  private weldingUpdate: Subscription;

  constructor(
    private weldingOdfService: WeldingOdfService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private breadcrumbService: BreadcrumbService,
    private messageService: MessageService,
    private app: AppComponent,
    private odfService: OdfService,
    private spinner: NgxSpinnerService,
    private eventBusService: EventBusService
  ) {

  }

  ngOnInit() {
    if (this.data) {
      this.id = this.data;
      console.log(this.id);
    }
    this.getWeldingOdfList();
    this.hrStorage = HrStorage.getUserToken();
    this.selectedColumns = this.hrStorage.sysGridView;
    const dm = COLS_TABLE.WELDING_ODF;
    const colsShow = [];
    this.cols = [];
    this.cols = dm.filter((elem) => this.selectedColumns.find(data => {
      return elem.field === data.columnName && data.gridViewName === 'weldingOdf';
    }));
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
      if (col.id !== 1) {
        colsShow.push(col);
      }
    });
    this.selectedColumns = colsShow;



    this.reloadWeldingOdfList = this.eventBusService.reloadWeldingOdfsChange.subscribe(val => {
      this._table.reset();
    });
    this.types = [];
    this.types.push({ label: this.translation.translate('common.label.all'), value: null });
    COLS_TABLE.TYPES.forEach(type => {
      this.types.push({ label: this.translation.translate(type.label), value: this.translation.translate(type.value) });
    });
    this.weldingCreate = this.eventBusService.weldingOdfCreateChange.subscribe(val => {
      console.log(val);
      if (val.data && val.data === 'reload') {
        this.getWeldingOdfList();
        console.log('reload create');
      }

    });
    this.weldingUpdate = this.eventBusService.weldingOdfUpdateChange.subscribe(val => {
      console.log(val);
      if (val.data && val.data === 'reload') {
        this.getWeldingOdfList();
        console.log('reload update');
      }

    });
    // console.log('ahihi work!!');
  }
  ngAfterViewInit() {
    // console.log(4234);
  }
  ngOnDestroy(): void {
    if (this.weldingCreate) {
      this.weldingCreate.unsubscribe();
      this.eventBusService.emitDataChange({
        type: 'weldingOdfCreate',
        data: ''
      });
    }
    if (this.weldingUpdate) {
      this.weldingUpdate.unsubscribe();
      this.eventBusService.emitDataChange({
        type: 'weldingOdfUpdate',
        data: ''
      });
    }
    if (this.reloadWeldingOdfList) {
      this.reloadWeldingOdfList.unsubscribe();
    }
  }

  filterDate(value, field, data) {
    let valueConverted: string;
    valueConverted = value !== null ? ((typeof value === 'string') ? value : CommonUtils.dateToString(value)) : null;
    this._table.filterConstraints['equals'] = (valueConverted, filter): boolean => {
      return valueConverted === filter;
    };
    data.filter(valueConverted, field, 'equals');
  }

  filterByType(value, field, data) {
    this._table.filterConstraints['equals'] = (value, filter): boolean => {
      return value === filter;
    };
    data.filter(value, field, 'equals');
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  getWeldingOdfList() {
    this.weldingOdfService.getAllWeldingOdfs(this.id).subscribe(response => {
      this.weldingOdfList = response.content.listData;
    });
  }

  // @HostListener('document:keydown', ['$event'])
  // enterToOpenCreateTab(event: KeyboardEvent): void {
  //   if (event.code === 'Enter') {
  //     this.createTab(this.id);
  //     event.stopPropagation();
  //   }
  // }

  createTab(id) {
    this.weldingOdfService.id = id;
    this.weldingOdfService.action = 'create';
    this.tabLayoutService.open({
      component: 'WeldingOdfCreateComponent',
      header: 'welding.odf.create.label',
      action: 'create',
      tabId: id,
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        { label: this.translation.translate('odf.tab.detail') },
        { label: this.translation.translate('weldingOdf.tab') },
        { label: this.translation.translate('welding.odf.create.label') },
      ]
    });
  }

  updateTab(item) {
    this.weldingOdfService.action = 'update';
    this.weldingOdfService.id = item.odfId;
    this.weldingOdfService.coupler = item.couplerNo;
    this.tabLayoutService.open({
      component: 'WeldingOdfUpdateComponent',
      header: 'welding.odf.update.label',
      action: 'update',
      tabId: Number.parseFloat(item.odfId + '.' + item.couplerNo),
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        { label: this.translation.translate('odf.tab.detail') },
        { label: this.translation.translate('weldingOdf.tab') },
        { label: this.translation.translate('welding.odf.update.label') },
      ]
    });
    this.weldingOdfService.item = item;
  }

  showDetail(item) {
    this.weldingOdfService.action = 'view';
    this.weldingOdfService.item = item;
    this.tabLayoutService.open({
      component: 'WeldingOdfDetailComponent',
      header: 'welding.odf.info.label',
      action: 'view',
      tabId: Number.parseFloat(item.odfId + '.' + item.couplerNo),
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        { label: this.translation.translate('odf.tab.detail') },
        { label: this.translation.translate('weldingOdf.tab') },
        { label: this.translation.translate('welding.odf.info.label') },
      ]
    });
  }

  // Export excel
  exportExcel() {
    this.messageService.clear('excel');
    const map = new Map<String, Object>();
    if (this.weldingOdfList.length === 0 || (this._table.filteredValue !== undefined && this._table.filteredValue !== null && this._table.filteredValue.length === 0)) {
      this.messageService.add({
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('common.label.noDataExport')
      });
    } else if (this.weldingOdfList.length > 0 && this._table.filteredValue !== undefined && this._table.filteredValue !== null) {
      const items = [];
      const values = [];
      map.set('odfId', this.id);
      this._table.filteredValue.forEach(value => {
        values.push(value.couplerNo);
      });
      this.selectedResult.forEach(item => {
        if (values.includes(item.couplerNo)) {
          items.push(item.couplerNo);
        }
      });
      map.set('couplers', items.length !== 0 ? items : values);
    } else if (this.weldingOdfList.length > 0 && (this._table.filteredValue === undefined || this._table.filteredValue === null)) {
      map.set('odfId', this.id);
      if (this.selectedResult.length > 0) {
        const values = [];
        this.selectedResult.forEach(element => {
          values.push(element.couplerNo);
        });
        map.set('couplers', values);
      } else {
        map.set('couplers', null);
      }
    }
    this.weldingOdfService.exportExcel(map).subscribe(res => {
      if (res.body.size === 0) {
        this.messageService.add({
          severity: 'warn',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.export.error')
        });
        return;
      }
      saveAs(res.body, res.headers.get('File'));
    });
  }

  onRowDblClick(item) {
    this.showDetail(item);
  }

  onRowSelect(event: any, template?: any) {
    console.log(event);
    console.log(template);
    console.log(this.selectedResult);
  }

  onRowUnselect(event: any) {
  }

  onReject() {
    this.messageService.clear('c');
  }

  clear() {
    this.messageService.clear();
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({ key: 'cp', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied') });
    }
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

  onCollapse(type?: string) {
    this.isCollapse = type === 'expend';
  }

  onCopy(event) {
    if (event) {
      this.messageService.add({
        key: 'cp', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
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
    } else if ((this.selectedResult && this.selectedResult.length === 0 || this.selectedResult === undefined || this.selectedResult === null)) {
      this.displayConfirmDelete = false;
      this.messageService.add({
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('common.message.delete.noselect')
      });
    }
  }

  onSubmitDelete() {
    this.spinner.show();
    if (this.selectedRowData) {
      const selectedRowData = [{
        odfId: this.selectedRowData.odfId,
        couplerNo: this.selectedRowData.couplerNo,
        cableId: this.selectedRowData.cableId,
        lineNo: this.selectedRowData.lineNo,
        destOdfId: this.selectedRowData.destOdfId,
        destCouplerNo: this.selectedRowData.destCouplerNo
      }];
      this.weldingOdfService.delete(selectedRowData).subscribe(res => {
        this.spinner.hide();
        const deleteRefWeldSleeveData = res.data.weldSleeve;
        if (deleteRefWeldSleeveData && deleteRefWeldSleeveData.length > 0) {
          this.warningMessDeleteWeldingOdf = deleteRefWeldSleeveData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.getWeldingOdfList();
        }
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      const selectedRowDataList = [];
      this.selectedResult.forEach(it => {
        selectedRowDataList.push({
          odfId: it.odfId,
          couplerNo: it.couplerNo,
          cableId: it.cableId,
          lineNo: it.lineNo,
          destOdfId: it.destOdfId,
          destCouplerNo: it.destCouplerNo
        });
      });
      this.weldingOdfService.delete(selectedRowDataList).subscribe(res => {
        this.spinner.hide();
        const deleteRefWeldSleeveData = res.data.weldSleeve;
        if (deleteRefWeldSleeveData && deleteRefWeldSleeveData.length > 0) {
          this.warningMessDeleteWeldingOdf = deleteRefWeldSleeveData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.getWeldingOdfList();
        }
      });
    }
  }

  onHideConfirmDelete() {
    this.displayConfirmDelete = false;
  }

  onHideWarningDelete() {
    this.displayWarningMessDelete = false;
    this.warningMessDeleteWeldingOdf = undefined;
  }
}
