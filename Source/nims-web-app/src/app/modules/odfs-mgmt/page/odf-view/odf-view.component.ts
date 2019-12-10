import { Component, ElementRef, Input, OnInit, Type, ViewChild, HostListener } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';
import { MessageService, TreeNode } from 'primeng/api';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { CAT_ITEM, COLS_TABLE } from '@app/shared/services/constants';
import { PermissionService } from '@app/shared/services/permission.service';
import { ActivatedRoute } from '@angular/router';
import { OdfService } from '../../service/odf.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchStationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchControlComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { WeldingOdfService } from '@app/modules/welding-odf-management/service/welding-odf.service';
import { ODFEditComponent } from '../odf-edit/odf-edit.component';

/**
 * Component ODF View
 * Created by DungPH
 */
@Component({
  selector: 'odf-view',
  templateUrl: './odf-view.component.html',
  styleUrls: ['../odf-save/odf-save.component.css']
})
export class ODFViewComponent implements OnInit {

  formAdd: FormGroup;
  statusCheck = false;
  positions: any[] = [];
  action: string;
  _weldingShow: string;

  first: number;
  rows: number;
  last: number;


  vendorList: any[] = [];
  odfTypeList: any[] = [];
  ownerList: any[] = [];

  stationList: any[];
  deptList: any[];

  @ViewChild('focusButton') inputEl: ElementRef;

  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  @ViewChild('autoCompleteDept')
  autoCompleteDept: AutocompleteSearchControlComponent;

  station: Type<any> = AutocompleteSearchStationModalComponent;

  stationId: number;
  deptId: number;
  deptLabel: string;

  tableShow = false;
  // buttonSubmit = true;

  // tree
  @ViewChild('treeN')
  treeNodes: TreeNode[] = [];
  loadingTreeNodes = true;
  selectedNode: TreeNode;
  params: any;
  treeN: TreeNode;

  // ODF Detail
  odfDetail = true;

  // odf lane
  odfLane = false;
  odfLaneList: any = [];

  // Weilding-Odf
  id: any;
  odfId: any;
  couplerNo: any;
  backupList: any = [];
  cols: any[];
  resultList: any = [];
  weldingOdfList: any[] = [];
  selectedResult: any = [];
  selectedColumns: any[];
  odfList: any[];
  weldingOdf = false;

  paramsSearch = {
    odfId: '',
    odfCode: '',
    cableCode: '',
    laneCode: '',
    first: 0,
    rows: 10
  };

  constructor(private app: AppComponent,
    private odfService: OdfService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private weldingOdfService: WeldingOdfService) {
    this.buildForm({});
    this.getVendorList();
    this.getOdfTypeList();
    this.getOwnerList();
    this.openWeldingOdfDetailFirst();
    // this.initTreeNodes();
    this.id = this.odfService.id;
  }

  ngOnInit() {
    console.log(this.id);
    this.odfService.findOdfById(this.id).subscribe(res => {
      const object = res.content;
      if (object) {
        this.eventBusService.emit({
          type: this.dept,
          value: object.deptId
        });
        object.installationDate = CommonUtils.stringToDate(object.installationDate, 'dd/MM/yyyy');
        this.getStation(object.stationId);
        this.vendorList.forEach(item => {
          if (item.value === object.vendorId) {
            object.vendorId = item.label;
          }
        });
        this.ownerList.forEach(item => {
          if (item.value === object.ownerId) {
            object.ownerId = item.label;
          }
        });
        this.odfTypeList.forEach(item => {
          if (item.value === object.odfTypeId) {
            object.odfTypeId = item.label;
          }
        });
        const odfIndex = object.odfCode.split('-')[2];
        // if (odfIndex < 10) {
        //   odfIndex = `0${odfIndex}`;
        // }
        this.formAdd.value.odfIndex = odfIndex;
        // console.log(odfIndex);
        this.buildForm(object);
        this.formAdd.reset(object);
        this.formAdd.patchValue({
          stationId: object.stationId,
          odfIndex: odfIndex,
          unusedCoupler: object.unusedCoupler,
          usedCoupler: object.usedCoupler
        });

        this.permissionService.findDepartmentById(object.deptId).subscribe(rs => {
          this.formAdd.patchValue({ deptId: rs.deptName });
          // this.autoCompleteDept.displayField = { deptName: rs.pathName };
          this.eventBusService.emit({
            deptDataObj: rs
          });
        });
      }
    });

    setTimeout(() => this.inputEl.nativeElement.focus());
    this.action = this.odfService.action;
    this.formAdd.value.odfId = this.id;
    this.odfId = this.id;
    this.selectedNode = {
      'label': this.translation.translate('odf.info.detail'),
      'data': 'detail',
      'expandedIcon': 'fa fa-folder-o',
      'collapsedIcon': 'fa fa-folder-o',
      'expanded': true
    };
    this.treeNodes = [{
      'label': this.translation.translate('odf.info.detail'),
      'data': 'detail',
      'expandedIcon': 'fa fa-folder-o',
      'collapsedIcon': 'fa fa-folder-o',
      'expanded': true,
      'children': [{
        'label': this.translation.translate('odf.info.odfLane'),
        'data': 'odfLane',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }, {
        'label': this.translation.translate('odf.info.weldingOdf'),
        'data': 'weldingOdf',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }]
    }];
    // this.odfLaneLoad();
    // this.weldingOdfLoad();
  }


  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.keyCode === 13) {
      this.eventBusService.dataChange.subscribe(val => {
        // console.log(val);
        if (val) {
          if (val.data.key === `${ODFViewComponent.name}_${this.odfId * 121}`) {
            // if (val.data.key === ODFEditComponent.name) {
            if (this.odfDetail) {
              this.onSubmit();
            } else if (this.odfLane) {
              this.onClosed();
            }

          }
        }

      }).unsubscribe();
    }
  }


  openWeldingOdfDetailFirst() {
    this.eventBusService.on('weldingShow').subscribe(val => {
      if (val && val['deptDataObj']) {
        const abc = val['deptDataObj'];
        // console.log(abc);
        if (abc === 'weldingFirst') {
          this.onNodeSelect({
            'label': this.translation.translate('odf.info.weldingOdf'),
            'data': 'weldingOdf',
            'expandedIcon': '',
            'collapsedIcon': '',
          });
        }
      }
    });
  }

  // weldingOdfLoad() {
  //   this.cols = [];
  //   this.cols = COLS_TABLE.WELDING_ODF;
  //   this.cols.forEach(col => {
  //     col.headerTranslate = this.translation.translate(col.header);
  //   });
  //   this.selectedColumns = this.cols;
  //   //
  //   this.getWeldingOdfList();
  // }

  odfLaneLoad() {
    this.cols = [];
    this.cols = COLS_TABLE.ODF_LANE;
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    this.selectedColumns = this.cols;
    this.odfLaneDataLoad();
  }

  odfLaneDataLoad() {
    this.odfService.getOdfLaneByOdfId(this.odfId).subscribe(res => {
      // console.log(res);
      this.odfLaneList = res.content.listData;
      this.resultList = res.content.listData;
      this.last =
        (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
    });
  }

  onLazyLoad(event) {
    // console.log(event);
    // console.log(1);
    if (!this.resultList) {
      this.odfLaneDataLoad();
    }

  }

  test(event) {
    // console.log(this.formAdd.value.houseStationTypeId);
  }

  initTreeNodes() {
    this.permissionService.getTreeNodeDepartmentList().subscribe((res) => {
      if (res) {
        // console.log(res);
        this.treeNodes = res.content.listData;
        this.treeNodes.forEach(node => {
          node.leaf = false;
        });
      }
    }, err => {
    }, () => {
      this.loadingTreeNodes = !this.loadingTreeNodes;
    });
  }

  onNodeSelect(event) {
    // console.log(event);
    // console.log(event.node);
    // console.log(event.node.data);
    const breadcrumb = [
      { label: this.translation.translate('odf.tab.list') },
      {
        label: this.translation.translate('odf.tab.detail')
      }
    ];

    if (event.node.data === 'odfLane') {
      // this.buttonSubmit = true;
      this.odfLane = true;
      this.odfDetail = false;
      this.weldingOdf = false;
      this.odfLaneLoad();
      breadcrumb.push({
        label: this.translation.translate('odf.info.odfLane')
      });

    } else if (event.node.data === 'weldingOdf') {
      // this.buttonSubmit = false;
      this.odfLane = false;
      this.odfDetail = false;
      this.weldingOdf = true;
      breadcrumb.push({
        label: this.translation.translate('odf.info.weldingOdf')
      });
      // this.weldingOdfLoad();
    } else {
      // this.buttonSubmit = false;
      this.odfLane = false;
      this.odfDetail = true;
      this.weldingOdf = false;
      // this.odfDetailLoad(this.odfData);
    }
    this.tabLayoutService.open({
      component: ODFViewComponent.name,
      header: 'odf.tab.detail',
      breadcrumb: breadcrumb,
      tabId: (this.odfId * 121),
    });

  }

  onNodeExpand(event) {
    if (event.node) {
      this.loadingTreeNodes = !this.loadingTreeNodes;
      this.permissionService.getTreeNodeDepartmentList(event.node.nodeId).subscribe((res) => {
        event.node.children = res.content.listData || [];
        event.node.children.forEach(node => {
          node.leaf = false;
          node.parent = event.node;
        });
        event.node.leaf = event.node.children.length === 0;
      }, err => {
      }, () => {
        this.loadingTreeNodes = !this.loadingTreeNodes;
      });
    }
  }

  // lay danh sach hang san xuat
  getVendorList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.VENDOR).subscribe(res => {

      this.vendorList = [];
      // truong hop them moi
      this.vendorList.push({
        label: null,
        value: null
      });
      for (let i = 0; i < res.length; i++) {
        this.vendorList.push({
          label: res[i].itemName,
          value: +res[i].itemId
        });
      }
      if (this.odfService.action === 'view') {
        this.vendorList.forEach(item => {
          if (item.value === this.formAdd.value.vendorId) {

            this.formAdd.patchValue({ vendorId: item.label });
            this.formAdd.value.vendorId = item.label;
          }
        });
      }
      // console.log(this.vendorList);
    });

    // console.log(this.vendorList);
  }

  // lay danh sach loai ODF
  getOdfTypeList() {
    this.permissionService.getCatOdfTypes().subscribe(res => {

      this.odfTypeList = [];
      // truong hop them moi
      this.odfTypeList.push({
        label: null,
        value: null
      });
      for (let i = 0; i < res.content.listData.length; i++) {
        this.odfTypeList.push({
          label: res.content.listData[i].odfTypeCode,
          value: +res.content.listData[i].odfTypeId
        });
      }

      if (this.odfService.action === 'view') {
        this.odfTypeList.forEach(item => {
          if (item.value === this.formAdd.value.odfTypeId) {
            // console.log(item);

            this.formAdd.patchValue({ odfTypeId: item.label });
            this.formAdd.value.odfTypeId = item.label;
          }
        });
      }
    });
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      // truong hop them moi
      this.ownerList.push({
        label: null,
        value: null
      });
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({
          label: res[i].itemName,
          value: +res[i].itemId
        });
      }

      if (this.odfService.action === 'view') {
        this.ownerList.forEach(item => {
          if (item.value === this.formAdd.value.ownerId) {
            // console.log(item);

            this.formAdd.patchValue({ ownerId: item.label });
            this.formAdd.value.ownerId = item.label;
          }
        });
      }
    });
  }

  // lay gia tri cua dropdown roi set vao form
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formAdd.controls[element].setValue(event.value.value);
    } else {
      this.formAdd.controls[element].setValue('');
    }
  }

  setAuditStatus(event, element: string) {
    this.setSelectedValue(event, element);
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      odfId: [null],

      stationId: ['', Validators.compose([
        Validators.required,
        CommonUtils.customValidateReturnLabel('station.stationCode')
      ])],

      odfIndex: [null, Validators.compose([
        Validators.required,
        CommonUtils.customValidateReturnLabel('odf.odfIndex')
      ])],

      odfCode: ['', Validators.compose([
        Validators.maxLength(200),
        CommonUtils.customValidateReturnLabel('odf.Code')
      ])],

      deptId: [null, Validators.compose([
        Validators.required,
        CommonUtils.customValidateReturnLabel('odf.deptName')
      ])],

      odfTypeId: [null, Validators.compose([
        Validators.required,
        CommonUtils.customValidateReturnLabel('odf.odfTypeCode')
      ])],

      ownerId: [null, Validators.compose([
        Validators.required,
        CommonUtils.customValidateReturnLabel('odf.ownerCode')
      ])],

      vendorId: [null, Validators.compose([
        Validators.required,
        CommonUtils.customValidateReturnLabel('odf.vendor')
      ])],


      installationDate: ['', Validators.compose([
        Validators.pattern('')
      ])],

      note: ['', Validators.maxLength(500)],

      unusedCoupler: [null],

      usedCoupler: [null]
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  get formControls() {
    return this.formAdd.controls;
  }

  // validate form va show message

  getStation(event) {
    this.permissionService.findStation({ stationId: event, first: 0, rows: 10 }).subscribe(result => {
      // console.log(result.content.listData);
      result.content.listData.forEach(item => {
        if (item.stationId === event) {
          this.formAdd.patchValue({ stationId: item.stationCode });
          this.formAdd.value.stationId = item.stationCode;
        }
      });
    });
  }

  onClosed() {
    this.tabLayoutService.close({
      component: ODFViewComponent.name,
      tabId: this.id * 121
    });
  }

  getWeldingOdfList() {
    this.weldingOdfService.getAllWeldingOdfs(this.odfId).subscribe(response => {
      this.weldingOdfList = response.content.listData;
      for (let index = 0; index < this.weldingOdfList.length; index++) {
        const item = this.weldingOdfList[index];
        if (item.lineNo === 0) {
          item.lineNo = '';
        }
        if (item.destCouplerNo === 0) {
          item.destCouplerNo = '';
        }
        item.createTime = CommonUtils.dateToString(item.createTime);
        item.odfConnectType = (item.odfConnectType === 1)
          ? this.translation.translate('welding.odf.weld')
          : this.translation.translate('welding.odf.joint');
        // this.resultList.push(item);
      }
    });
  }

  deleteMultiple() {
    if (this.selectedResult.length > 0) {
      this.messageService.clear();
      this.messageService.add({
        key: 'c',
        sticky: true,
        severity: 'warn',
        summary: this.translation.translate('station.confirm.delete'),
        data: this.selectedResult,
      });
    } else {
      this.messageService.add({
        severity: 'error',
        summary: this.translation.translate('common.label.NOTIFICATIONS'),
        detail: this.translation.translate('common.message.delete.noselect')
      });
    }
  }

  // openTab(type: string, odfId?: number, couplerNo?: number, item?: any) {
  //   let component = '';
  //   let header = '';
  //   if (type === 'create') {

  //     this.weldingOdfService.id = this.odfId;
  //     component = WeldingOdfCreateComponent.name;
  //     header = 'welding.odf.create.label';
  //     // console.log(`${type} ${odfId} ${couplerNo} ${item}`);

  //   } else if (type === 'edit') {
  //     // console.log(`${type} ${odfId} ${couplerNo} ${item}`);
  //     component = WeldingOdfUpdateComponent.name;
  //     header = 'welding.odf.update.label';
  //     this.resultList.forEach(odf => {
  //       if (odf.odfId === odfId && odf.couplerNo === couplerNo) {
  //         this.weldingOdfService.item = odf;
  //       }
  //     });

  //   } else if (type === 'view') {
  //     // console.log(`${type} ${odfId} ${couplerNo} ${item}`);
  //     // console.log(item);
  //     this.weldingOdfService.item = item;
  //     component = WeldSleeveDetailComponent.name;
  //     header = 'welding.odf.info.label';

  //   }
  //   this.tabLayoutService.open({
  //     component: component,
  //     header: header,
  //     breadcrumb: [
  //       { label: this.translation.translate('weldingOdf.tab') },
  //       { label: this.translation.translate(header) },
  //     ]
  //   });
  // }
  onSubmit() {
    // this.tabLayoutService.close({
    //   component: ODFEditComponent.name
    // });
    // this.odfService.id = this.odfId;
    this.odfService.action = 'edit';
    this.tabLayoutService.open({
      component: ODFEditComponent.name,
      header: 'odf.tab.update',
      tabId: this.id * 121,
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        { label: this.translation.translate('odf.tab.update') }
      ]
    });
  }
  searchInTable(event, field) {

    if (field === 'odfCode') {
      this.paramsSearch.odfCode = event;
    }

    if (field === 'cableCode') {
      this.paramsSearch.cableCode = event;
    }

    if (field === 'laneCode') {
      this.paramsSearch.laneCode = event;
    }
    this.paramsSearch.odfId = this.odfId;
    this.odfService.searchOdfLane(this.paramsSearch).subscribe(res => {
      this.odfLaneList = res.content;
    });
    // this.onLazyLoad({});
  }

}
