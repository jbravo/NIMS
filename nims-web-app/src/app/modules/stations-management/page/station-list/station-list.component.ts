import {Component, ElementRef, HostListener, OnDestroy, OnInit, Type, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {TranslationService} from 'angular-l10n';
import {CommonUtils} from '@app/shared/services';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {
  ATTACH_FILE_TYPE,
  AUDIT_STATUS,
  AUDIT_TYPE,
  BACKUP_STATUS,
  CAT_ITEM,
  COLS_TABLE,
  POSITION,
  STATUS,
  TERRAINS
} from '@app/shared/services/constants';
import {DataCommonService} from '@app/shared/services/data-common.service';
import {PermissionService} from '@app/shared/services/permission.service';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {AppComponent} from '@app/app.component';
import {MenuItem, MessageService} from 'primeng/api';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {NgxSpinnerService} from 'ngx-spinner';
import {AutocompleteSearchLocationModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-location-modal/autocomplete-search-location-modal.component';
import {AutocompleteSearchDepartmentModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {Table} from 'primeng/table';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {Subscription} from 'rxjs';
import {saveAs} from 'file-saver';
import {ContextMenu} from 'primeng/contextmenu';
import {UploadControlComponent} from '@app/shared/components/upload-control/upload-control.component';

@Component({
  selector: 'station-list',
  templateUrl: './station-list.component.html',
  styleUrls: ['./station-list.component.css']
})
export class StationListComponent implements OnInit, OnDestroy {
  @ViewChild('fileF') fileF: UploadControlComponent;
  @ViewChild('fileE') fileE: UploadControlComponent;
  @ViewChild('fileAttach') fileAttach: UploadControlComponent;
  @ViewChild('focus') searchElement: ElementRef;
  @ViewChild('dt') table: Table;
  @ViewChild('contextMenu') contextMenu: ContextMenu;

  advanceSearch = false;
  formSearch: FormGroup;
  attachFileForm: FormGroup;
  filteredDept: any[];
  filteredLocation: any[];
  first: number;
  rows: number;
  // last: number;
  cols: any[];
  selectedColumns: any[];
  resultList: any = [];
  selectedResult: any = [];
  terrainsList: any = [];
  ownerList: any = [];
  statusList: any[] = [];
  houseStationTypeList: any[] = [];
  stationFeatureList: any[] = [];
  auditTypeList: any[] = [];
  auditStatusList: any[] = [];
  stationTypeList: any[] = [];
  backupStatusList: any[] = [];
  positions: any[] = [];
  importDialog = false;
  attachFileDialog = false;
  file: any;
  fileEdit: any;
  resultImportAdd: any;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  location: Type<any> = AutocompleteSearchLocationModalComponent;
  resultImportEdit: any;
  deptId: number;
  locationId: number;
  paramsSearch = {
    first: 0,
    rows: undefined,
    basicInfo: undefined,
    stationCode: undefined,
    deptId: undefined,
    locationId: undefined,
    terrain: undefined,
    houseOwnerName: undefined,
    houseOwnerPhone: undefined,
    address: undefined,
    ownerId: undefined,
    constructionDate: undefined,
    status: undefined,
    houseStationTypeId: undefined,
    stationTypeId: undefined,
    stationFeatureId: undefined,
    backupStatus: undefined,
    position: undefined,
    lengthStr: undefined,
    widthStr: undefined,
    heightStr: undefined,
    heightestBuildingStr: undefined,
    longString: undefined,
    laString: undefined,
    auditType: undefined,
    auditStatus: undefined,
    auditReason: undefined,
    fileCheck: undefined,
    fileListed: undefined,
    note: undefined,
    sortField: undefined,
    sortOrder: undefined,
    fillerStationCode: undefined
  };
  dataDelete = {
    stationId: undefined,
    stationCode: undefined
  };
  innerWidth;
  innerHeight;
  selectedRowData;
  stationCode;
  displayConfirmDelete = false;
  displayWarningMessDelete = false;
  warningMessDeleteRefOdf;
  warningMessDeleteRefCables;
  warningMessDeleteRefCablelanes;
  hrStorage: any;
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  headerLocation = this.app.translation.translate('common.dialog.header.location');
  items: MenuItem[];
  isLazy = false;
  private reloadStation: Subscription;
  isCollapse = false;
  gridId: any;

  attachFileTypeList: any[];
  acceptAttachKD = '.pdf';
  acceptAttachNY = '.png,.jpg';
  acceptAttach = '.pdf,.png,.jpg';
  attach: any;
  isAttach = false;
  stCode: any;
  isKD = false;

  constructor(
    private formBuilder: FormBuilder,
    private app: AppComponent,
    private stationService: StationService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private sysGridViewService: SysGridViewService
  ) {
    this.buildForm({});
    // this.onProcess({first: 0, rows: 10});
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.keyCode === 13) {
      this.eventBusService.dataChange.subscribe(val => {
        console.log(val);
        if (val) {
          if (val.data.key === StationListComponent.name) {
            this.advanceSearchFunc();
          }
        }
      }).unsubscribe();
    }
  }

  ngOnDestroy(): void {
    CommonUtils.setColumns('station', this.selectedColumns, this.sysGridViewService);
    // let listRs = [];
    // this.selectedColumns.forEach(el => {
    //   listRs.push({gridViewName: 'station', columnName: el.field});
    // });
    // if (listRs.length === 0) {
    //   listRs.push({gridViewName: 'station', columnName: 'null'});
    // }
    // this.sysGridViewService.saveGridView(listRs).subscribe(res => {
    // }, er => {
    // }, () => {
    //   this.sysGridViewService.getGridView({userId: this.hrStorage.userId}).subscribe(rs => {
    //     this.hrStorage.sysGridView = rs.data;
    //     HrStorage.setUserToken(this.hrStorage);
    //   });
    // });
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
      return elem.field === data.columnName && data.gridViewName === 'station';
    }));
    // set all column if new user or user none select colums
    if (!this.selectedColumns || this.selectedColumns.length === 0) {
      this.selectedColumns = this.cols;
    }

    // list dia hinh
    this.terrainsList = this.dataCommon.getDropDownList(TERRAINS);
    this.auditStatusList = this.dataCommon.getDropDownList(AUDIT_STATUS);
    // list loai tram
    this.getStationTypeList();
    // list chu so huu
    this.getOwnerList();
    // list trang thai
    this.statusList = this.dataCommon.getDropDownList(STATUS);
    // list loai nha tram
    this.getHouseStationTypeList();
    // list loai tram
    this.getStationTypeList();
    // list tinh chat nha tram
    this.getStationFeature();
    // list vu hoi
    this.backupStatusList = this.dataCommon.getDropDownList(BACKUP_STATUS);
    // list vi tri
    this.positions = this.dataCommon.getDropDownList(POSITION);
    // list phan loai  kiem dinh
    this.auditTypeList = this.dataCommon.getDropDownList(AUDIT_TYPE);
    this.setInnerWidthHeightParameters();
    // this.advanceSearchFunc('basic');
    this.reloadStation = this.eventBusService.reloadStationChange.subscribe(val => {
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
      this.messageService.add({
        key: 'copySuccess', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  onChangeCols(event) {
    // sort asc
    this.selectedColumns.sort((a, b) => {
      return a.id - b.id;
    });
  }

  callFunctionFilter(event, field) {
    if (field === 'pathName') {
      this.filteredDeptFunc(event);
    }
    if (field === 'pathLocalName') {
      this.filteredLocationFunc(event);
    }
  }

  // tra cuu vs list dia hinh trong autocomplete
  filteredLocationFunc(event) {
    // this.dataCommon.filteredLocationFunc(this.formSearch.controls['locationName'], event, this.filteredLocation);
    let obj = {
      deptId: null,
      locationId: null,
      locationName: event.query,
      isTree: 0
    };
    this.permissionService.getTreeNodeLocationList(obj).subscribe(s => {
      this.filteredLocation = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.filteredLocation.push({label: s.content.listData[i].pathLocalName, value: s.content.listData[i].locationId});
      }
    });
  }

  filteredDeptFunc(event) {
    // this.dataCommon.filteredDeptFunc(this.formSearch.controls['deptName'], event, this.filteredDept);
    this.permissionService.filterDept(event.query).subscribe(s => {
      this.filteredDept = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.filteredDept.push({label: s.content.listData[i].pathName, value: s.content.listData[i].deptId});
      }
    });
  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      basicInfo: [''],
      _stationCode: [''],
      _deptId: [''],
      _locationId: [''],
      _stationTypeId: [null],
      _auditStatus: [''],
      first: [''],
      recordsTotal: [''],
      rows: [''],
      sortField: [''],
      sortOrder: [''],
      stationCode: [''],
      deptId: [''],
      deptName: [''],
      pathName: [''],
      locationId: [''],
      locationName: [''],
      pathLocalName: [''],
      terrain: [''],
      houseOwnerName: [''],
      houseOwnerPhone: [''],
      address: [''],
      ownerName: [''],
      ownerId: [''],
      constructionDate: [''],
      status: [''],
      houseStationTypeName: [''],
      houseStationTypeId: [''],
      stationTypeName: [''],
      stationTypeId: [''],
      stationFeatureName: [''],
      stationFeatureId: [''],
      backupStatus: [''],
      position: [''],
      length: [''],
      width: [''],
      height: [''],
      heightestBuilding: [''],
      longitude: [''],
      latitude: [''],
      auditType: [''],
      auditStatus: [''],
      auditReason: [''],
      fileCheck: [''],
      fileListed: [''],
      note: ['']
    });
  }

  private buildAttachFileForm() {
    this.attachFileForm = this.formBuilder.group({
      attachFileType: [null],
      sgcnkdNumber: [null],
      provideStartDate: [''],
      provideEndDate: [''],
      fileName: [''],
      documentId: [null],
    });
  }

  get formControls() {
    return this.formSearch.controls;
  }

  // attach-file
  get formAttachControls() {
    return this.attachFileForm.controls;
  }

  setAttachFileType(attachFileType) {
    this.attachFileTypeList = attachFileType;
  }

  onAttachFile(e) {
    this.messageService.clear('msgAttach');
    this.stCode = e;
    this.attachFileDialog = true;
    this.isAttach = false;
    this.isKD = false;
    this.buildAttachFileForm();
    this.acceptAttach = '.pdf,.png,.jpg';
    if (this.fileAttach) {
      this.fileAttach.setEmptyFile();
    }
    this.setAttachFileType(this.dataCommon.getDropDownList(ATTACH_FILE_TYPE));
  }

  onInputProvideDate(event, type?: string) {
    if (event && event.currentTarget.value === '') {
      if (type === 'start') {
        this.attachFileForm.controls['provideStartDate'].setValue(null);
      } else {
        this.attachFileForm.controls['provideEndDate'].setValue(null);
      }
    }
  }

  onClearProvideDate(type?: string) {
    if (type === 'start') {
      this.attachFileForm.controls['provideStartDate'].setValue(null);
    } else {
      this.attachFileForm.controls['provideEndDate'].setValue(null);
    }
  }

  onHideAttachFile() {
    this.attachFileDialog = false;
    this.messageService.clear('msgAttach');
  }

  // load data table
  public onLazyLoad(event) {
    if (this.isLazy === true) {
      this.first = event.first;
      this.rows = event.rows;
      this.paramsSearch.first = event.first;
      this.paramsSearch.rows = event.rows;
      if (event.sortField) {
        this.paramsSearch.sortField = event.sortField;
        this.paramsSearch.sortOrder = event.sortOrder;
      }
      this.spinner.show();
      this.stationService.findAdvanceStation(this.paramsSearch).subscribe(res => {
        this.spinner.hide();
        this.resultList = res.content;
        // this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      });
    }
  }

  setInnerWidthHeightParameters() {
    this.innerWidth = window.innerWidth;
    this.innerHeight = window.innerHeight * 0.50;
  }

  showAdvance() {
    this.advanceSearch = true;
  }

  hideAdvance() {
    this.advanceSearch = false;
  }

  // basicSearchFunc() {
  //   this.paramsSearch.rows = 10;
  //   this.paramsSearch.basicInfo = this.formSearch.value.basicInfo ? this.formSearch.value.basicInfo : null;
  //   this.stationService.findAdvanceStation(this.paramsSearch).subscribe(res => {
  //     this.resultList = res.content ? res.content : this.resultList.listData = [];
  //   });
  // }

  advanceSearchFunc(type?: string) {
    this.isLazy = true;
    this.paramsSearch.rows = 10;
    if (type === 'basic') {
      this.paramsSearch.basicInfo = this.formSearch.value.basicInfo ? this.formSearch.value.basicInfo : null;
      this.paramsSearch.stationCode = undefined;
      this.paramsSearch.deptId = undefined;
      this.paramsSearch.locationId = undefined;
      this.paramsSearch.stationTypeId = undefined;
      this.paramsSearch.auditStatus = undefined;
    }
    if (type === 'advance') {
      this.paramsSearch.basicInfo = undefined;
      this.paramsSearch.stationCode = this.formSearch.value._stationCode ? this.formSearch.value._stationCode : null;
      this.paramsSearch.deptId = this.formSearch.value._deptId && this.formSearch.value._deptId ? this.formSearch.value._deptId.deptId : null;
      this.paramsSearch.locationId = this.formSearch.value._locationId && this.formSearch.value._locationId ? this.formSearch.value._locationId.locationId : null;
      this.paramsSearch.stationTypeId = this.formSearch.value._stationTypeId && this.formSearch.value._stationTypeId.value ? this.formSearch.value._stationTypeId.value : null;
      this.paramsSearch.auditStatus = this.formSearch.value._auditStatus && this.formSearch.value._auditStatus.value || this.formSearch.value._auditStatus.value === 0 ? this.formSearch.value._auditStatus.value : null;
    }
    this.spinner.show();
    this.stationService.findAdvanceStation(this.paramsSearch).subscribe(res => {
      this.spinner.hide();
      this.resultList = res.content ? res.content : this.resultList.listData = [];
    });
  }

  searchInTable(event, field) {
    if (field === 'stationCode') {
      this.paramsSearch.fillerStationCode = event;
    }
    if (field === 'pathName') {
      this.paramsSearch.deptId = event;
    }
    if (field === 'pathLocalName') {
      this.paramsSearch.locationId = event;
    }
    if (field === 'terrainName') {
      this.paramsSearch.terrain = event;
    }
    if (field === 'houseOwnerName') {
      this.paramsSearch.houseOwnerName = event;
    }
    if (field === 'houseOwnerPhone') {
      this.paramsSearch.houseOwnerPhone = event;
    }
    if (field === 'address') {
      this.paramsSearch.address = event;
    }
    if (field === 'ownerName') {
      this.paramsSearch.ownerId = event;
    }
    if (field === 'constructionDate') {
      this.paramsSearch.constructionDate = event;
    }
    if (field === 'statusName') {
      this.paramsSearch.status = event;
    }
    if (field === 'houseStationTypeName') {
      this.paramsSearch.houseStationTypeId = event;
    }
    if (field === 'stationTypeName') {
      this.paramsSearch.stationTypeId = event;
    }
    if (field === 'stationFeatureName') {
      this.paramsSearch.stationFeatureId = event;
    }
    if (field === 'backupStatusName') {
      this.paramsSearch.backupStatus = event;
    }
    if (field === 'positionName') {
      this.paramsSearch.position = event;
    }
    if (field === 'length' && event !== '') {
      this.paramsSearch.lengthStr = event;
    }
    if (field === 'width' && event !== '') {
      this.paramsSearch.widthStr = event;
    }
    if (field === 'height' && event !== '') {
      this.paramsSearch.heightStr = event;
    }
    if (field === 'heightestBuilding' && event !== '') {
      this.paramsSearch.heightestBuildingStr = event;
    }
    if (field === 'longitude') {
      this.paramsSearch.longString = event;
    }
    if (field === 'latitude') {
      this.paramsSearch.laString = event;
    }
    if (field === 'auditTypeName') {
      this.paramsSearch.auditType = event;
    }
    if (field === 'auditStatusName') {
      this.paramsSearch.auditStatus = event;
    }
    if (field === 'auditReason') {
      this.paramsSearch.auditReason = event;
    }
    if (field === 'fileCheck') {
      this.paramsSearch.fileCheck = event;
    }
    if (field === 'fileListed') {
      this.paramsSearch.fileListed = event;
    }
    if (field === 'note') {
      this.paramsSearch.note = event;
    }
    if (field === 'constructionDate') {
      this.paramsSearch.constructionDate = event;
    }
    this.paramsSearch.first = 0;
    this.paramsSearch.rows = 10;
    this.stationService.findAdvanceStation(this.paramsSearch).subscribe(res => {
      // this.spinner.hide();
      this.table.reset();
      this.resultList = res.content ? res.content : this.resultList.listData = [];
    }, error => {
      console.log(error);
      this.resultList = [];
    });
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
        this.formSearch.controls['constructionDate'].setValue(null);
        this.paramsSearch.constructionDate = null;
        this.onProcess({first: 0, rows: 10});
      }
    }
  }

  onClearClickDateFilter(event) {
    this.formSearch.controls['constructionDate'].setValue(null);
    if (event) {
      this.paramsSearch.constructionDate = null;
      this.onProcess({first: 0, rows: 10});
    }
  }

  // setSelectedValue(event, element: string) {
  //   if (event.value != null && event.value !== '') {
  //     this.formSearch.controls[element].setValue(event.value.value);
  //   } else {
  //     this.formSearch.controls[element].setValue('');
  //   }
  // }

  // lay list loai tram
  getStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_TYPE).subscribe(res => {
      this.stationTypeList = [];
      // truong hop them moi
      this.stationTypeList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.stationTypeList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      this.ownerList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  // lay danh sach loai nha tram
  getHouseStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.HOUSE_STATION_TYPE).subscribe(res => {
      this.houseStationTypeList = [];
      this.houseStationTypeList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.houseStationTypeList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  // lay list tinh chat nha tram
  getStationFeature() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_FEATURE).subscribe(res => {
      this.stationFeatureList = [];
      // truong hop them moi
      this.stationFeatureList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.stationFeatureList.push({label: res[i].itemName, value: +res[i].itemId});
      }
    });
  }

  onRowSelect(event: any, template?: any) {
    // u can do something else with the data
  }

  saveOrEdit(id?: number, action?: string) {
    this.stationService.id = id;
    this.stationService.action = action;
    this.tabLayoutService.open({
      component: 'StationSaveComponent',
      header: id ? 'station.manage.update.label' : 'station.manage.create.label',
      action: id ? 'edit' : '',
      tabId: id,
      breadcrumb: [
        {label: this.translation.translate('station.manage.label')},
        {label: this.translation.translate(id ? 'station.manage.update.label' : 'station.manage.create.label')}
      ]
    });
  }

  getFormValidationErrors(success: () => void) {
    if (CommonUtils.getFormValidationErrors(this.formSearch, this.app, 'station')) {
      success();
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
        key: 'stationList',
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.warn.delete.multipe')
      });
    }
  }

  onSubmitDelete() {
    this.spinner.show();
    if (this.selectedRowData) {
      const selectedRowData = [{
        stationId: this.selectedRowData.stationId,
        stationCode: this.selectedRowData.stationCode
      }];
      this.stationService.delete(selectedRowData).subscribe(res => {
        this.spinner.hide();
        this.stationCode = res.data.stationCode;
        const deleteRefOdfData = res.data.odf;
        const deleteRefCablesData = res.data.cables;
        const deleteRefCablelanesData = res.data.cablelanes;
        if ((deleteRefOdfData && deleteRefOdfData.length > 0) && deleteRefCablesData === undefined && deleteRefCablelanesData === undefined) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefCablesData && deleteRefCablesData.length > 0) && deleteRefOdfData === undefined && deleteRefCablelanesData === undefined) {
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefCablelanesData && deleteRefCablelanesData.length > 0) && deleteRefOdfData === undefined && deleteRefCablesData === undefined) {
          this.warningMessDeleteRefCables = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefOdfData && deleteRefOdfData.length > 0) && (deleteRefCablesData && deleteRefCablesData.length > 0)
          && deleteRefCablelanesData === undefined) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefOdfData && deleteRefOdfData.length > 0) && (deleteRefCablelanesData && deleteRefCablelanesData.length > 0)
          && deleteRefCablesData === undefined) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.warningMessDeleteRefCablelanes = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefCablesData && deleteRefCablesData.length > 0) && (deleteRefCablelanesData && deleteRefCablelanesData.length > 0)
          && deleteRefOdfData === undefined) {
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.warningMessDeleteRefCablelanes = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefOdfData && deleteRefOdfData.length > 0) && (deleteRefCablesData && deleteRefCablesData.length > 0)
          && (deleteRefCablelanesData && deleteRefCablelanesData.length > 0)) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.warningMessDeleteRefCablelanes = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            key: 'deleteStationSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.onProcess({first: 0, rows: 10});
        }
      });
    } else if (this.selectedResult && this.selectedResult.length > 0) {
      const selectedRowDataList = [];
      this.selectedResult.forEach(it => {
        selectedRowDataList.push({
          stationId: it.stationId,
          stationCode: it.stationCode
        });
      });
      this.stationService.delete(selectedRowDataList).subscribe(res => {
        this.spinner.hide();
        this.stationCode = res.data.stationCode;
        const deleteRefOdfData = res.data.odf;
        const deleteRefCablesData = res.data.cables;
        const deleteRefCablelanesData = res.data.cablelanes;
        if ((deleteRefOdfData && deleteRefOdfData.length > 0) && deleteRefCablesData === undefined && deleteRefCablelanesData === undefined) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefCablesData && deleteRefCablesData.length > 0) && deleteRefOdfData === undefined && deleteRefCablelanesData === undefined) {
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefCablelanesData && deleteRefCablelanesData.length > 0) && deleteRefOdfData === undefined && deleteRefCablesData === undefined) {
          this.warningMessDeleteRefCables = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefOdfData && deleteRefOdfData.length > 0) && (deleteRefCablesData && deleteRefCablesData.length > 0)
          && deleteRefCablelanesData === undefined) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefOdfData && deleteRefOdfData.length > 0) && (deleteRefCablelanesData && deleteRefCablelanesData.length > 0)
          && deleteRefCablesData === undefined) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.warningMessDeleteRefCablelanes = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefCablesData && deleteRefCablesData.length > 0) && (deleteRefCablelanesData && deleteRefCablelanesData.length > 0)
          && deleteRefOdfData === undefined) {
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.warningMessDeleteRefCablelanes = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else if ((deleteRefOdfData && deleteRefOdfData.length > 0) && (deleteRefCablesData && deleteRefCablesData.length > 0)
          && (deleteRefCablelanesData && deleteRefCablelanesData.length > 0)) {
          this.warningMessDeleteRefOdf = deleteRefOdfData;
          this.warningMessDeleteRefCables = deleteRefCablesData;
          this.warningMessDeleteRefCablelanes = deleteRefCablelanesData;
          this.displayConfirmDelete = false;
          this.displayWarningMessDelete = true;
        } else {
          this.displayConfirmDelete = false;
          this.messageService.add({
            key: 'deleteStationSuccess',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.delete.success')
          });
          this.selectedResult = undefined;
          this.onProcess({first: 0, rows: 10});
        }
      });
    }
  }

  onHideConfirmDelete() {
    this.displayConfirmDelete = false;
  }

  onHideWarningDelete() {
    this.displayWarningMessDelete = false;
    this.warningMessDeleteRefOdf = undefined;
    this.warningMessDeleteRefCables = undefined;
    this.warningMessDeleteRefCablelanes = undefined;
  }

  delete(item) {
    this.dataDelete.stationId = item.stationId;
    this.dataDelete.stationCode = item.stationCode;
    this.messageService.clear();
    this.messageService.add({
      key: 'c',
      sticky: true,
      summary: this.translation.translate('common.message.confirm'),
      detail: this.translation.translate('station.confirm.delete'),
      data: this.dataDelete
    });
  }

  onConfirm(item) {
    this.messageService.clear('c');
    if (!(item instanceof Array)) {
      item = [item];
    }
    this.stationService.delete(item).subscribe(s => {
      if (s.data.code === 1) {
        this.onProcess({first: 0, rows: 10});
        this.messageService.add({
          key: 'messageDelete',
          sticky: true,
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.delete.success')
        });
        setTimeout(() => {
          this.messageService.clear('messageDelete');
        }, 2000);
      } else {
        const dataMessage = [];
        item.forEach(element => {
          if (s.data.odf && s.data.odf.length > 0) {
            dataMessage.push(this.translation.translate('station.delete.error.odf', {odf: s.data.odf, station: element.stationCode}));
          }
          if (s.data.cables && s.data.cables.length > 0) {
            dataMessage.push(this.translation.translate('station.delete.error.cables', {
              cables: s.data.cables,
              station: element.stationCode
            }));
          }
          if (s.data.cablelanes && s.data.cablelanes.length > 0) {
            dataMessage.push(this.translation.translate('station.delete.error.cableLanes', {
              cableLanes: s.data.cableLanes,
              station: element.stationCode
            }));
          }
          this.messageService.add({
            key: 'messageDelete',
            sticky: true,
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            data: dataMessage
          });
          setTimeout(() => {
            this.messageService.clear('messageDelete');
          }, 2000);
        });
      }
    });
  }

  onReject(key) {
    this.messageService.clear(key);
  }

  clear() {
    this.messageService.clear();
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
        key: 'stationList',
        severity: 'warn',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('station.delete.warn.delete.multipe')
      });
    }
  }

  showDetail(id, action) {
    this.stationService.id = id;
    this.stationService.action = action;
    this.tabLayoutService.open({
      component: action === 'edit' ? 'StationSaveComponent' : action === 'view' ? 'StationDetailComponent' : 'StationSaveComponent',
      header: action === 'edit' ? 'station.manage.update.label' : action === 'view' ? 'station.manage.detail.label' : 'station.manage.create.label',
      action: action,
      tabId: id,
      breadcrumb: [
        {label: this.translation.translate('station.manage.label')},
        {label: this.translation.translate(action === 'edit' ? 'station.manage.update.label' : action === 'view' ? 'station.manage.detail.label' : 'station.manage.create.label')}
      ]
    });
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

  doImport(type?: number) {
    if (type === 1) {
      this.spinner.show();
      if (this.file) {
        console.log(this.file);
        this.stationService.importStation(this.file).subscribe(res => {
          this.spinner.hide();

          if (res == null || res.data == null) {
            this.messageService.add({
              key: 'stationList',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error')
            });
            return;
          }
          if (res.status === 0) {
            this.messageService.add({
              key: 'stationList',
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
                key: 'stationList',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('station.import.success', {success: success})
              });
            } else {
              this.messageService.add({
                key: 'stationList',
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
        this.stationService.editStation(this.fileEdit).subscribe(res => {
          this.spinner.hide();
          if (res == null || res.data == null) {
            this.messageService.add({
              key: 'stationList',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('station.import.error')
            });
            return;
          }
          if (res.status === 0) {
            this.messageService.add({
              key: 'stationList',
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
                key: 'stationList',
                severity: 'success',
                summary: '',
                detail: this.translation.translate('cable.importEdit.success', {success: success})
              });
            } else {
              this.messageService.add({
                key: 'stationList',
                severity: 'warn',
                summary: '',
                detail: this.translation.translate('cable.importEdit.warning', {success: success, error: err})
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
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('station.import.error.require')
        });
      }
    }
  }

  exportExcel() {
    this.messageService.clear('excel');
    this.spinner.show();
    if (this.selectedResult.length > 0) {
      let dataExport = [];
      dataExport = this.resultList.listData.filter((elem) => this.selectedResult.find(value => {
        return elem.stationId === value.stationId;
      }));
      this.stationService.excelChose(dataExport).subscribe(res => {
        this.spinner.hide();
        if (res == null) {
          this.messageService.add({
            key: 'stationList',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.export.error')
          });
          return;
        }
        saveAs(res.body, res.headers.get('File'));
        // saveAs(s.body, this.app.translation.translate(`document.download.filename`));
      });
    } else {
      // Export excel
      this.paramsSearch.first = null;
      this.paramsSearch.rows = null;
      this.stationService.export(this.paramsSearch).subscribe(res => {
        this.spinner.hide();
        if (res == null) {
          this.messageService.add({
            key: 'stationList',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('station.export.error')
          });
          return;
        }
        if (res.body.size === 0 && res.content == null) {
          this.messageService.add({
            key: 'stationList',
            severity: 'warn',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate('cable.export.error')
          });

          return;
        }
        saveAs(res.body, res.headers.get('File'));
        // saveAs(s.body, this.app.translation.translate(`document.download.filename`));
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

  onRowDblClick(event, item) {
    this.showDetail(item.stationId, 'view');
  }

  filter(columns) {
    return columns.filter(val => {
      return val.typeFilter !== 'frozen';
    });
  }

  clearDeptName() {
    this.onProcess({first: 0, rows: 10});
    this.paramsSearch.deptId = undefined;
  }

  clearLocation() {
    this.onProcess({first: 0, rows: 10});
    this.paramsSearch.locationId = undefined;
  }

  onChangeRowSelectDept(event: any) {
    this.eventBusService.emit({
      type: 'stationDept',
      deptDataObj: event
    });
  }

  onClearRowSelect() {
    this.formSearch.controls['_deptId'].setValue('');
    this.formSearch.controls['_locationId'].setValue(null);
  }

  changeFile(event, type) {
    if (type === 1) {
      this.file = event;
    } else {
      this.fileEdit = event;
    }
  }

  messageDelete(key, warningMessDelete, station) {
    let mes = '';
    if (key === 'odf') {
      mes = this.translation.translate('station.delete.error.odf', {odf: warningMessDelete, station});
    }
    if (key === 'cables') {
      mes = this.translation.translate('station.delete.error.cables', {cables: warningMessDelete, station});
    }
    if (key === 'cableLanes') {
      mes = this.translation.translate('station.delete.error.cableLanes', {cableLanes: warningMessDelete, station});
    }
    return mes;
  }

  cutLongLat(item) {
    return CommonUtils.cutLongLat(item);
  }

  changeAcceptAttach(e) {
    if (e === 0) {
      this.isKD = true;
      this.acceptAttach = this.acceptAttachKD;
    } else if (e === 1) {
      this.isKD = false;
      this.acceptAttach = this.acceptAttachNY;
      this.attachFileForm.controls['provideStartDate'].setValue(null);
      this.attachFileForm.controls['provideEndDate'].setValue(null);
      this.attachFileForm.controls['sgcnkdNumber'].setValue(null);
    } else {
      this.isKD = false;
      this.attachFileForm.controls['provideStartDate'].setValue(null);
      this.attachFileForm.controls['provideEndDate'].setValue(null);
      this.attachFileForm.controls['sgcnkdNumber'].setValue(null);
    }
  }

  changeFileAttach(event) {
    debugger
    if (event) {
      this.attach = event;
      this.isAttach = true;
    }
  }

  submitAttach() {
    this.messageService.clear('msgAttach');
    // const name: String = this.attach.name;
    // const t = name.substring(0, name.lastIndexOf('.')) + 'abc' + name.substring(name.lastIndexOf('.'));
    // this.attach.file.name = t ;
    // debugger
    if (!this.isAttach
    // || this.attachFileForm.value.sgcnkdNumber || this.attachFileForm.value.provideStartDate
    // || this.attachFileForm.value.provideEndDate
    ) {
      ///////////


      // console.log("chua chon file");
      this.messageService.add({
        key: 'copySuccess',
        severity: 'error',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('common.button.attach.file.choose')
      });
    } else if (this.attachFileForm.value.attachFileType !== 0 && this.attachFileForm.value.attachFileType !== 1) {
      this.messageService.add({
        key: 'msgAttach',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('station.input.required.attach'),
      });
    } else if (this.attachFileForm.value.attachFileType === 0 &&
      (!this.attachFileForm.value.provideStartDate || !this.attachFileForm.value.sgcnkdNumber ||
        !this.attachFileForm.value.provideEndDate ||
        (this.attach && this.attach.name &&
          this.attach.name.substring(this.attach.name.lastIndexOf('.') + 1).toUpperCase() !== 'PDF'))) {

      if (this.attach && this.attach.name &&
        this.attach.name.substring(this.attach.name.lastIndexOf('.') + 1).toUpperCase() !== 'PDF') {
        this.messageService.add({
          key: 'msgAttach',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.input.validate.file'),
        });
      }
      if (!this.attachFileForm.value.sgcnkdNumber) {
        this.messageService.add({
          key: 'msgAttach',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.input.required.GCNKD'),
        });
      }
      if (!this.attachFileForm.value.provideStartDate) {
        this.messageService.add({
          key: 'msgAttach',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.input.required.provideDate'),
        });
      }
      if (!this.attachFileForm.value.provideEndDate) {
        this.messageService.add({
          key: 'msgAttach',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('station.input.required.expiredDate'),
        });
      }

    } else if (this.attachFileForm.value.attachFileType === 1 &&
      (this.attach && this.attach.name &&
        this.attach.name.substring(this.attach.name.lastIndexOf('.') + 1).toUpperCase() === 'PDF')) {
      this.messageService.add({
        key: 'msgAttach',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('station.input.validate.file'),
      });
    } else {
      this.stationService.attachFile(this.attach, this.stCode.stationCode, this.stCode.stationId,
        this.attachFileForm.value.attachFileType).subscribe(res => {
        if (res.content === 'valid' || res.content === 'error') {
          this.onHideAttachFile();
          this.spinner.hide();
          setTimeout(() => {
            this.messageService.add({
              key: 'copySuccess',
              severity: 'error',
              summary: this.translation.translate('common.label.NOTIFICATIONS'),
              detail: this.translation.translate('common.message.error.system.save')
            });
          }, 1000);
        } else {

          this.attachFileForm.value.documentId = this.stCode.stationId;
          this.attachFileForm.value.fileName = res.content;
          // debugger
          const obj = {
            attachFileType: this.attachFileForm.value.attachFileType,
            sgcnkdNumber: this.attachFileForm.value.sgcnkdNumber,
            provideStartDate: this.attachFileForm.value.provideStartDate,
            provideEndDate: this.attachFileForm.value.provideEndDate,
            fileName: res.content,
            documentId: this.stCode.stationId,
          };
          this.stationService.saveDocument(obj).subscribe(data => {
            this.onHideAttachFile();
            this.spinner.hide();
            setTimeout(() => {
              this.messageService.add({
                key: 'copySuccess',
                severity: 'success',
                summary: this.translation.translate('common.label.NOTIFICATIONS'),
                detail: this.translation.translate('common.button.attach.file.success')
              });
            }, 1000);
            this.table.reset();
            this.messageService.clear('msgAttach');
          }, error => {
            this.onHideAttachFile();
            this.spinner.hide();
            this.messageService.clear('msgAttach');
          });
        }
      }, error => {
        this.onHideAttachFile();
        this.spinner.hide();
        this.messageService.clear('msgAttach');
      });
    }
  }

  downloadFile(path) {
    if (path) {
      this.stationService.downloadFile('./upload-dir/' + path).subscribe(res => {
        if (res.body.size === 0) {
          // this.app.warningMessage('messages.warn.common.download');
          return;
        }
        saveAs(res.body, res.headers.get('File'));
      });
    }
  }
}
