import {Component, ElementRef, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {DynamicDialogRef, MenuItem} from 'primeng/api';
import {TranslationService} from 'angular-l10n';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {CommonUtils} from '@app/shared/services';
import {COLS_TABLE_AUTOCOMPLETE_CONTROL} from '@app/shared/services/constants';
import {Table} from 'primeng/table';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {ContextMenu} from 'primeng/contextmenu';
import {ToastService} from '@app/shared/services/toast.service';

@Component({
  selector: 'autocomplete-search-station-modal',
  templateUrl: './autocomplete-search-station-modal.component.html',
  styleUrls: ['./autocomplete-search-station-modal.component.css']
})
export class AutocompleteSearchStationModalComponent implements OnInit, OnDestroy {
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dataTable') dataTable: Table;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  form: FormGroup;
  firstFocus = true;
  first: number;
  rows: number;
  cols: any[];
  selectedColumns: any[];
  resultList: any = [];
  selectedData: any;
  tabReturn;
  dataFilterObj = {
    stationCode: undefined,
    deptName: undefined,
    locationName: undefined,
    first: 0,
    rows: undefined,
    sortField: undefined,
    sortOrder: undefined
  };
  items: MenuItem[];
  isCollapse = false;

  constructor(
    public ref: DynamicDialogRef,
    private translation: TranslationService,
    private stationService: StationService,
    private toastService: ToastService,
    private eventBusService: EventBusService
  ) {
  }

  ngOnInit() {
    this.buildForm({});
    this.onProcess({first: 0, rows: 10});
    this.cols = COLS_TABLE_AUTOCOMPLETE_CONTROL.STATION;
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    this.selectedColumns = this.cols;

    this.eventBusService.dataChange.subscribe(val => {
      this.tabReturn = val.data;
    }).unsubscribe();
    // console.log(this.tabReturn);
  }

  ngOnDestroy(): void {
    this.eventBusService.emitDataChange({
      type: 'onTabChangeActive',
      data: this.tabReturn.return
    });
    // this.dataChangeSub.unsubscribe();
  }

  private buildForm(formData: any) {
    this.form = CommonUtils.createForm(formData, {
      stationCode: [''],
      deptName: [''],
      locationName: [''],
      first: [''],
      rows: ['']
    });
  }

  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.dataFilterObj.first = event.first;
    this.dataFilterObj.rows = event.rows;
    if (event.sortField) {
      this.dataFilterObj.sortField = event.sortField;
      this.dataFilterObj.sortOrder = event.sortOrder;
    } else {
      this.dataFilterObj.sortField = undefined;
      this.dataFilterObj.sortOrder = undefined;
    }
    this.stationService.findAdvanceStation(this.dataFilterObj).subscribe(res => {
      if (res) {
        this.resultList = res.content;
      }
    });
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    // this.onLazyLoad(event);
  }

  onSearchData() {
    this.dataFilterObj.stationCode = this.form.value.stationCode ? this.form.value.stationCode : null;
    this.dataFilterObj.deptName = this.form.value.deptName ? this.form.value.deptName : null;
    this.dataFilterObj.locationName = this.form.value.locationName ? this.form.value.locationName : null;
    this.dataTable.reset();
  }

  onFilterDataTable(event, field) {
    if (field === 'stationCode') {
      this.dataFilterObj.stationCode = event;
    }
    if (field === 'deptName') {
      this.dataFilterObj.deptName = event;
    }
    if (field === 'locationName') {
      this.dataFilterObj.locationName = event;
    }
    this.dataFilterObj.first = 0;
    this.dataFilterObj.rows = 10;
    this.stationService.findAdvanceStation(this.dataFilterObj).subscribe(res => {
      if (res) {
        this.dataTable.reset();
        this.resultList = res.content;
      }
    });
  }

  onRowSelect(event: any) {
    // console.log(event);
    // console.log(this.selectedData);
  }

  onRowDblClick(event, rowData) {
    setTimeout(() => {
      this.ref.close(rowData);
    }, 100);
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
      this.toastService.openSuccessToast();
      this.items = [];
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter') {
      // if (val.key === OdfListComponent.name || (val.tabId && val.tabId === this.id )) {
      if (this.tabReturn.tabConfig.header === this.translation.translate('common.dialog.header.station')) {
        // console.log(`dt.reset()`);
        this.dataFilterObj.stationCode = this.form.value.stationCode ? this.form.value.stationCode : null;
        this.dataFilterObj.locationName = this.form.value.locationName ? this.form.value.locationName : null;
        this.dataFilterObj.deptName = this.form.value.deptName ? this.form.value.deptName : null;
        this.dataTable.reset();
      }
    }

  }

}


