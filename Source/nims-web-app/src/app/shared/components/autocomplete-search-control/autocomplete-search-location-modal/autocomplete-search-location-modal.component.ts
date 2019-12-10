import {Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Table} from 'primeng/table';
import {FormGroup} from '@angular/forms';

import {DynamicDialogRef, MenuItem, TreeNode} from 'primeng/api';
import {ContextMenu} from 'primeng/contextmenu';
import {TranslationService} from 'angular-l10n';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {PermissionService} from '@app/shared/services/permission.service';
import {COLS_TABLE_AUTOCOMPLETE_CONTROL} from '@app/shared/services/constants';
import {CommonUtils} from '@app/shared/services';
import {Subscription} from 'rxjs';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {ToastService} from '@app/shared/services/toast.service';
import {el} from '@angular/platform-browser/testing/src/browser_util';

@Component({
  selector: 'autocomplete-search-location-modal',
  templateUrl: './autocomplete-search-location-modal.component.html',
  styleUrls: ['./autocomplete-search-location-modal.component.css']
})
export class AutocompleteSearchLocationModalComponent implements OnInit, OnDestroy {
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
  // tree
  treeNodes: TreeNode[] = [];
  loadingTreeNodes = true;
  selectedNode: TreeNode;
  params: any;
  deptDataObj;
  private deptDataObjSub: Subscription;
  deptId: number;
  locationId: number;
  dataFilterObj = {
    deptId: undefined,
    locationId: undefined,
    locationCode: undefined,
    locationName: undefined,
    pathLocalName: undefined,
    parentId: null,
    first: 0,
    rows: undefined,
    isTree: 0,
    sortField: undefined,
    sortOrder: undefined
  };
  items: MenuItem[];
  isCollapse = false;
  tabReturn;

  constructor(
    public ref: DynamicDialogRef,
    private translation: TranslationService,
    private stationService: StationService,
    private permissionService: PermissionService,
    private eventBusService: EventBusService,
    private toastService: ToastService
  ) {
  }

  ngOnInit() {
    this.buildForm({});
    this.onProcess({first: 0, rows: 10});
    this.cols = COLS_TABLE_AUTOCOMPLETE_CONTROL.LOCATION;
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    this.selectedColumns = this.cols;
    this.deptDataObjSub = this.eventBusService.deptDataObjChange.subscribe(val => {
      if (val && val['deptDataObj']) {
        this.deptDataObj = val['deptDataObj'];
      } else {
        this.deptDataObj = null;
      }
    });
    this.deptDataObjSub = this.eventBusService.deptDataObjChange.subscribe(val => {
      if (val && val['selectedDeptData']) {
        this.deptDataObj = val['selectedDeptData'];
      }
    });
    this.initTreeNodes();
    this.selectedColumns = this.cols;
    this.eventBusService.dataChange.subscribe(val => {
      this.tabReturn = val.data;
    }).unsubscribe();
  }

  ngOnDestroy(): void {
    if (this.deptDataObjSub) {
      this.deptDataObjSub.unsubscribe();
    }
    this.eventBusService.emitDataChange({
      type: 'onTabChangeActive',
      data: this.tabReturn.return
    });
  }

  private buildForm(formData: any) {
    this.form = CommonUtils.createForm(formData, {
      deptId: [null],
      locationId: [null],
      locationCode: [''],
      locationName: [''],
      pathLocalName: [''],
      parentId: [null],
      first: [''],
      rows: [''],
      isTree: [0]
    });
  }

  initTreeNodes() {
    const dataObj = {
      locationId: null,
      deptId: (this.deptDataObj && this.deptDataObj.deptId) ? this.deptDataObj.deptId : null,
      isTree: 1
    };
    this.permissionService.getTreeNodeLocationList(dataObj).subscribe((res) => {
      if (res) {
        // this.treeNodes = [{
        //   data: res.content.listData,
        //   expandedIcon: 'fa fa-folder-open',
        //   collapsedIcon: 'fa fa-folder'
        // }];
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
    this.deptId = event.node.nodeId;
    this.locationId = event.node.locationId;
    this.dataFilterObj = {
      deptId: this.deptId,
      locationId: this.locationId,
      locationCode: undefined,
      locationName: undefined,
      pathLocalName: undefined,
      parentId: null,
      first: 0,
      rows: undefined,
      isTree: 0,
      sortField: undefined,
      sortOrder: undefined
    };
    this.form.patchValue({'deptId': event.node.nodeId});
    this.form.patchValue({'locationId': event.node.locationId});
    this.dataTable.reset();
  }

  onDblclickNode(event, node) {
    if (node) {
      setTimeout(() => {
        this.ref.close(node);
      }, 100);
    }
  }

  onNodeExpand(event) {
    debugger
    if (event.node) {
      const dataObj = {
        locationId: event.node.locationId,
        deptId: event.node.nodeId,
        isTree: 1
      };
      this.loadingTreeNodes = !this.loadingTreeNodes;
      this.permissionService.getTreeNodeLocationList(dataObj).subscribe((res) => {
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

  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.dataFilterObj.first = event.first;
    this.dataFilterObj.rows = event.rows;
    this.dataFilterObj.deptId = (this.deptDataObj && this.deptDataObj.deptId) ? this.deptDataObj.deptId : null;
    if (event.sortField) {
      this.dataFilterObj.sortField = event.sortField;
      this.dataFilterObj.sortOrder = event.sortOrder;
    } else {
      this.dataFilterObj.sortField = undefined;
      this.dataFilterObj.sortOrder = undefined;
    }
    this.permissionService.getTreeNodeLocationList(this.dataFilterObj).subscribe(res => {
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
    this.dataFilterObj.locationCode = this.form.value.locationCode ? this.form.value.locationCode : null;
    this.dataFilterObj.locationName = this.form.value.locationName ? this.form.value.locationName : null;
    this.dataTable.reset();
  }

  onFilterDataTable(event, field) {
    if (field === 'locationCode') {
      this.dataFilterObj.locationCode = event;
    }
    if (field === 'locationName') {
      this.dataFilterObj.locationName = event;
    }
    if (field === 'pathLocalName') {
      this.dataFilterObj.pathLocalName = event;
    }
    this.dataFilterObj.first = 0;
    this.dataFilterObj.rows = 10;
    this.permissionService.getTreeNodeLocationList(this.dataFilterObj).subscribe(res => {
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
      if (this.tabReturn.tabConfig.header === this.translation.translate('common.dialog.header.location')) {
        this.dataFilterObj.locationCode = this.form.value.locationCode ? this.form.value.locationCode : null;
        this.dataFilterObj.locationName = this.form.value.locationName ? this.form.value.locationName : null;
        this.dataTable.reset();
      }
    }
  }


}
