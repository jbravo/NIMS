import { AfterViewInit, Component, HostListener, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonUtils, ValidationService } from '@app/shared/services';
import { AppComponent } from '@app/app.component';
import { StationService } from '@app/modules/stations-management/service/station.service';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { PermissionService } from '@app/shared/services/permission.service';
import { MessageService, TreeNode } from 'primeng/api';
import { ActivatedRoute } from '@angular/router';
import { TabLayoutComponent, TabLayoutService } from '@app/layouts/tab-layout';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { AUDIT_STATUS, AUDIT_TYPE, BACKUP_STATUS, CAT_ITEM, POSITION, STATUS } from '@app/shared/services/constants';
import { OdfService } from '@app/modules/odfs-mgmt/service/odf.service';

@Component({
  selector: 'station-detail',
  templateUrl: './station-detail.component.html',
  styleUrls: ['./station-detail.component.css']
})
export class StationDetailComponent implements OnInit, OnDestroy, AfterViewInit {
  @Input() data;
  formAdd: FormGroup;
  private mapValueObjSub: Subscription;
  private contentHasChangedSub: Subscription;
  isTabChanged: string;
  statusList: any[] = [];
  houseStationTypeList: any[] = [];
  stationTypeList: any[] = [];
  ownerList: any[] = [];
  stationFeatureList: any[] = [];
  positions: any[] = [];
  auditTypeList: any[] = [];
  auditStatusList: any[] = [];
  backupStatusList: any[] = [];
  deptList: any[] = [];
  locationList: any[] = [];
  // tree
  treeNodes: TreeNode[] = [];
  loadingTreeNodes = true;
  selectedNode: TreeNode;
  params: any;
  // view
  station: boolean;
  odf: boolean;
  device: boolean;
  private _genKey = '_';
  action: string;
  tabId: number;

  constructor(
    private fb: FormBuilder,
    private app: AppComponent,
    private stationService: StationService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private spinner: NgxSpinnerService,
    private tabLayoutComponent: TabLayoutComponent,
    private odfService: OdfService
  ) {
    this.station = true;
    this.buildForm();
    this.mapValueObjSub = this.eventBusService.coordinatesChange.subscribe(val => {
      if (val && val['value']) {
        this.formAdd.patchValue({
          latitude: val['value'].lat,
          longitude: val['value'].lng
        });
      }
    });
  }


  ngOnInit() {
    this.formAdd.patchValue({ constructionDate: new Date() });
    this.formAdd.value.stationId = this.stationService.id;
    this.tabId = this.stationService.id;
    this.action = this.stationService.action;
    this.stationService.findStationById(this.stationService.id || this.data.data.station_id).subscribe(res => {
      if (res.content) {
        res.content.longitude = CommonUtils.cutLongLat(res.content.longitude);
        res.content.latitude = CommonUtils.cutLongLat(res.content.latitude);
        res.content.constructionDate = CommonUtils.stringToDate(res.content.constructionDate, 'yyyy/MM/dd');
        res.content.length = res.content.length ? CommonUtils.cutLongLat(res.content.length) : res.content.length;
        res.content.width = res.content.width ? CommonUtils.cutLongLat(res.content.width) : res.content.width;
        res.content.height = res.content.height ? CommonUtils.cutLongLat(res.content.height) : res.content.height;
        res.content.heightestBuilding = res.content.heightestBuilding ? CommonUtils.cutLongLat(res.content.heightestBuilding) : res.content.heightestBuilding;
        // list lao nha tram
        this.getHouseStationTypeList();
        // list chu so huu
        this.getOwnerList();
        // list tinh chat nha tram
        this.getStationFeature();
        // list loai tram
        this.getStationTypeList();
        // list trang thai
        this.statusList = this.dataCommon.getDropDownList(STATUS);
        // list vu hoi
        this.backupStatusList = this.dataCommon.getDropDownList(BACKUP_STATUS);
        // list vi tri
        this.positions = this.dataCommon.getDropDownList(POSITION);
        // list phan loai kiem dinh
        this.auditTypeList = this.dataCommon.getDropDownList(AUDIT_TYPE);
        // list trang thai kiem dinh
        this.auditStatusList = this.dataCommon.getDropDownList(AUDIT_STATUS);
        this.formAdd.reset(res.content);
        this.formAdd.patchValue({ houseStationTypeId: +res.content.houseStationTypeId });
        this.permissionService.findDepartmentById(res.content.deptId).subscribe(dept => {
          this.formAdd.patchValue({ deptId: dept });
        });
        this.permissionService.findCatLocationById(res.content.locationId).subscribe(location => {
          this.formAdd.patchValue({ locationId: location });
        });
      }
    });
    this.treeNodes = [{
      'label': this.translation.translate('station.manage.info'),
      'data': 'detail',
      'expandedIcon': 'fa fa-folder-o',
      'collapsedIcon': 'fa fa-folder-o',
      'expanded': true,
      'children': [
        {
          'label': this.translation.translate('station.label.tree.detail.odf'),
          'data': 'odf',
          'expandedIcon': 'fa fa-folder-o',
          'collapsedIcon': 'fa fa-folder-o',
        },
        // {
        //   'label': this.translation.translate('station.label.tree.detail.device'),
        //   'data': 'device',
        //   'expandedIcon': '',
        //   'collapsedIcon': '',
        // }
      ]
    }];
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      this.ownerList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
      this.ownerList.forEach(item => {
        if (item.value === this.formAdd.value.ownerId) {
          this.formAdd.value.ownerName = item.label;
          this.formAdd.patchValue({ ownerName: item.label });
        }
      });
    });
  }

  // lay danh sach loai nha tram
  getHouseStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.HOUSE_STATION_TYPE).subscribe(res => {
      this.houseStationTypeList = [];
      this.houseStationTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.houseStationTypeList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
      this.houseStationTypeList.forEach(item => {
        if (item.value === this.formAdd.value.houseStationTypeId) {
          this.formAdd.value.houseStationTypeName = item.label;
          this.formAdd.patchValue({ houseStationTypeName: item.label });
        }
      });
    });
  }

  // lay list loai tram
  getStationTypeList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_TYPE).subscribe(res => {
      this.stationTypeList = [];
      this.stationTypeList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.stationTypeList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
      this.stationTypeList.forEach(item => {
        if (item.value === this.formAdd.value.stationTypeId) {
          this.formAdd.value.stationTypeName = item.label;
          this.formAdd.patchValue({ stationTypeName: item.label });
        }
      });
    });
  }

  // lay list tinh chat nha tram
  getStationFeature() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.STATION_FEATURE).subscribe(res => {
      this.stationFeatureList = [];
      // truong hop them moi
      this.stationFeatureList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
      for (let i = 0; i < res.length; i++) {
        this.stationFeatureList.push({ label: res[i].itemName, value: +res[i].itemId });
      }
      this.stationFeatureList.forEach(item => {
        if (item.value === this.formAdd.value.stationFeatureId) {
          this.formAdd.value.stationFeatureName = item.label;
          this.formAdd.patchValue({ stationFeatureName: item.label });
        }
      });
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  get formControls() {
    return this.formAdd.controls;
  }

  ngAfterViewInit(): void {
    // this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
    //   this.isTabChanged = val;
    //   const action = this.action ? this._genKey + this.action : '';
    //   const tabId = this.tabId ? this._genKey + this.tabId : '';
    //   const key = StationDetailComponent.name + action + tabId;
    //   this.tabLayoutService.isTabContentHasChanged({component: StationDetailComponent.name, key});
    // });
  }

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
    if (this.mapValueObjSub) {
      this.mapValueObjSub.unsubscribe();
    }
  }

  buildForm() {
    this.formAdd = this.fb.group({
      stationId: [null],
      stationCode: [null,
        Validators.compose([Validators.required, Validators.maxLength(30),
        CommonUtils.customValidateReturnLabel('station.stationCode')])
      ],
      deptId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.dept')])],
      locationId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.location')])],
      terrainName: [null],
      houseOwnerName: [null, Validators.compose([Validators.maxLength(200)])],
      houseOwnerPhone: [null,
        Validators.compose([Validators.required, Validators.maxLength(20),
        ValidationService.phone, CommonUtils.customValidateReturnLabel('station.houseOwnerPhone')])
      ],
      address: [null, Validators.maxLength(500)],
      ownerId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.owner')])],
      ownerName: [null],
      constructionDate: [null],
      status: [null],
      statusName: [null],
      houseStationTypeId: [null],
      houseStationTypeName: [null],
      stationTypeId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.stationType')])],
      stationTypeName: [null],
      stationFeatureId: [null, Validators.required],
      stationFeatureName: [null],
      backupStatus: [null],
      backupStatusName: [null],
      position: [null],
      positionName: [null],
      length: [null, ValidationService.positiveNumber],
      width: [null, ValidationService.positiveNumber],
      height: [null, ValidationService.positiveNumber],
      heightestBuilding: [null, ValidationService.positiveNumber],
      longitude: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.longitude')])],
      latitude: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('station.latitude')])],
      auditType: [null],
      auditTypeName: [null],
      auditStatus: [null],
      auditStatusName: [null],
      note: [null, Validators.maxLength(500)],
      createTime: [null],
      rowStatus: [null]
    });
  }

  onClosed() {
    this.tabLayoutService.close({
      component: 'StationDetailComponent',
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
  }

  goToUpdate() {
    this.stationService.action = 'edit';
    this.tabLayoutService.open({
      component: 'StationSaveComponent',
      action: 'edit',
      tabId: this.tabId,
      header: 'station.manage.update.label',
      breadcrumb: [
        { label: this.translation.translate('station.manage.label') },
        { label: this.translation.translate('station.manage.update.label') }
      ]
    });
  }

  onClosedTab() {
    if (this.isTabChanged === 'INVALID') {
      this.tabLayoutService.isTabContentHasChanged({ component: StationDetailComponent.name });
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(StationDetailComponent.name));
    } else {
      this.tabLayoutService.close({
        component: StationDetailComponent.name,
        header: ''
      });
    }
  }

  onNodeSelect(event) {
    const breadcrumb = [
      { label: this.translation.translate('station.manage.label') },
      { label: this.translation.translate('station.manage.detail.label') }
    ];
    if (event.node.data === 'detail') {
      this.station = true;
      this.odf = false;
      this.device = false;

    }
    if (event.node.data === 'odf') {
      this.station = false;
      this.odf = true;
      this.device = false;
      breadcrumb.push({ label: this.translation.translate('map.label.station.view.info.odf') });
    }
    if (event.node.data === 'device') {
      this.station = false;
      this.odf = false;
      this.device = true;
      // breadcrumb.push({ label: this.translation.translate('station.manage.detail.label') });
    }
    this.tabLayoutService.open({
      component: 'StationDetailComponent',
      header: 'station.manage.detail.label',
      tabId: this.tabId,
      action: 'view',
      breadcrumb: breadcrumb
    });
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.code === 'Enter') {
      this.eventBusService.dataChange.subscribe(val => {
        console.log(val.data);
        if (val) {
          if (val.data.key === `${StationDetailComponent.name}_${this.tabId}`) {
            this.goToUpdate();
          }
        }
      }).unsubscribe();
    }
  }
}
