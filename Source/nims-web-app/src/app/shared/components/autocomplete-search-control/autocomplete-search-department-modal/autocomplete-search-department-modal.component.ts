import {Component, HostListener, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Table} from 'primeng/table';
import {FormGroup} from '@angular/forms';
import {DynamicDialogRef, MenuItem, MessageService, TreeNode} from 'primeng/api';
import {TranslationService} from 'angular-l10n';
import {StationService} from '@app/modules/stations-management/service/station.service';
import {PermissionService} from '@app/shared/services/permission.service';
import {COLS_TABLE_AUTOCOMPLETE_CONTROL} from '@app/shared/services/constants';
import {CommonUtils} from '@app/shared/services';
import {ContextMenu} from 'primeng/contextmenu';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {ToastService} from '@app/shared/services/toast.service';

@Component({
  selector: 'autocomplete-search-department-modal',
  templateUrl: './autocomplete-search-department-modal.component.html',
  styleUrls: ['./autocomplete-search-department-modal.component.css']
})
export class AutocompleteSearchDepartmentModalComponent implements OnInit, OnDestroy {
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
  parentId: number;
  dataFilterObj = {
    deptCode: undefined,
    deptName: undefined,
    parentId: null,
    pathName: undefined,
    first: 0,
    rows: undefined,
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
    private messageService: MessageService,
    private toastService: ToastService
  ) {
  }

  ngOnInit() {
    this.buildForm({});
    this.onProcess({first: 0, rows: 10});
    this.cols = COLS_TABLE_AUTOCOMPLETE_CONTROL.DEPARTMENT;
    this.cols.forEach(col => {
      col.headerTranslate = this.translation.translate(col.header);
    });
    this.selectedColumns = this.cols;
    this.initTreeNodes();
    // if (this.eventBusService.dataChange) {
    this.eventBusService.dataChange.subscribe(val => {
      this.tabReturn = val.data;
    }).unsubscribe();
    // }
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
      deptId: [null],
      deptCode: [''],
      deptName: [''],
      path: [''],
      parentId: [null],
      pathName: [''],
      first: [''],
      rows: ['']
    });
  }

  initTreeNodes() {
    this.permissionService.getTreeNodeDepartmentList().subscribe((res) => {
      if (res) {
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
    this.parentId = event.node.nodeId;
    this.dataFilterObj = {
      deptCode: undefined,
      deptName: undefined,
      parentId: this.parentId,
      pathName: undefined,
      first: 0,
      rows: undefined,
      sortField: undefined,
      sortOrder: undefined
    };
    this.form.patchValue({'parentId': event.node.nodeId});
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
    this.permissionService.findCatDeptByPost(this.dataFilterObj).subscribe(res => {
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
    this.dataFilterObj.deptCode = this.form.value.deptCode ? this.form.value.deptCode : null;
    this.dataFilterObj.deptName = this.form.value.deptName ? this.form.value.deptName : null;
    this.dataTable.reset();
  }

  onFilterDataTable(event, field) {
    if (field === 'deptCode') {
      this.dataFilterObj.deptCode = event;
    }
    if (field === 'deptName') {
      this.dataFilterObj.deptName = event;
    }
    if (field === 'pathName') {
      this.dataFilterObj.pathName = event;
    }
    this.dataFilterObj.first = 0;
    this.dataFilterObj.rows = 10;
    this.permissionService.findCatDeptByPost(this.dataFilterObj).subscribe(res => {
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
      if (this.tabReturn.tabConfig.header === this.translation.translate('common.dialog.header.dept')) {
        this.dataFilterObj.deptCode = this.form.value.deptCode ? this.form.value.deptCode : null;
        this.dataFilterObj.deptName = this.form.value.deptName ? this.form.value.deptName : null;
        this.dataTable.reset();
      }
    }
  }

  // @HostListener('document:keydown', ['$event'])
  // handleZoomEvent(event: KeyboardEvent): void {
  //   if (event.ctrlKey === true && (event.code === '61' || event.code === '107' || event.code === '173' || event.code === '109'
  //     || event.code === '187' || event.code === '189')) {
  //     event.preventDefault();
  //     event.stopPropagation();
  //   }
  // }

  // @HostListener('DOMMouseScroll', ['$event'])
  // handleZoomMouseScrollEvent(event: KeyboardEvent): void {
  //   if (event.ctrlKey === true) {
  //     event.preventDefault();
  //     event.stopPropagation();
  //   }
  // }

  // @HostListener('mousewheel', ['$event']) onMouseWheelChrome(event: any) {
  //   this.mouseWheelFunc(event);
  // }
  //
  // @HostListener('DOMMouseScroll', ['$event']) onMouseWheelFirefox(event: any) {
  //   this.mouseWheelFunc(event);
  // }
  //
  // @HostListener('onmousewheel', ['$event']) onMouseWheelIE(event: any) {
  //   this.mouseWheelFunc(event);
  // }
}
