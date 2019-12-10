import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit, ViewChild, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import {APP_CONSTANTS, OrgSelectorService} from '@app/core';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { TreeNode } from 'primeng/api';
import { TreeNodesService } from '@app/core/services/tree-nodes/tree-nodes.service';
import { formatDate } from '@angular/common';
import { CalendarModule } from 'primeng/calendar';
import { TooltipModule } from 'primeng/tooltip';
import { Table } from 'primeng/table';
import {MessageService} from 'primeng/api';
import { EventEmitter } from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'org-selector-modal',
  templateUrl: './org-selector-modal.component.html',
})

export class OrgSelectorModalComponent implements OnInit {
  organizatonSearch: any;
  nodes: TreeNode[];
  selectedNode: TreeNode;
  resultList: any = {};
  form: FormGroup;
  params: any;
  desiredTooltip: any;

  @Output()
  public message: EventEmitter<any> = new EventEmitter<any>();

  @ViewChild(Table) tableComponent: Table;
  constructor(
    public activeModal: NgbActiveModal
    , private formBuilder: FormBuilder
    , private treeNodesService: TreeNodesService
    , private service: OrgSelectorService
    , private messageService: MessageService
  ) { }

  ngOnInit() {
    this.buildForm();
    this.organizatonSearch = {
      name: '',
      code: '',
      activityDateFromStart: null,
      activityDateFromEnd: null,
      activityDateToStart: null,
      activityDateToEnd: null,
      parentId: null,
      first: null,
      rows: null,
      recordsTotal: null,
      sortOrder: null,
      sortField: ''
    };
  }
  /**
   * set init value
   */
  setInitValue(params: { operationKey: '', adResourceKey: '', filterCondition: '' }) {
    this.params = params;
    this.actionInitAjax(null);
  }
  /**
   * action init ajax
   */
  private actionInitAjax(nodeOrganizationId: any) {
    this.treeNodesService.getTreeNodeOrganization(nodeOrganizationId)
      .subscribe((res) => {
        this.nodes = CommonUtils.toTreeNode(CommonUtils.addTreeNodes(res.data));
      });
  }

  onKeyEnter(event: any, formControlName: string) {
    this.form = CommonUtils.getValueForKeyup(this.form, formControlName, event.target.value);
    this.processSearch();
  }
  /**
   * actionLazyRead
   * @ param event
   */
  public onLazyLoad(event) {
    this.organizatonSearch.name = this.form.value.name;
    this.organizatonSearch.code = this.form.value.code;
    this.organizatonSearch.first = event.first;
    this.organizatonSearch.rows = event.rows;
    this.organizatonSearch.sortOrder = event.sortOrder;
    if (event.sortField !== undefined && event.sortField != null && event.sortField.length > 0) {
      this.organizatonSearch.sortField = event.sortField;
    }
    if (this.selectedNode) {
      this.organizatonSearch.parentId = this.selectedNode.data;
    }
    this.service.search(this.organizatonSearch).subscribe(res => {
      this.resultList = res.data;
    });
  }
  /****************** CAC HAM COMMON DUNG CHUNG ****/
  /**
   * buildForm
   */
  private buildForm(): void {
    this.form = this.formBuilder.group({
      code: [''],
      name: [''],
      effectiveStartDateFrom: [''],
      effectiveStartDateTo: [''],
      effectiveEndDateFrom: [''],
      effectiveEndDateTo: [''],
      nationId: [CommonUtils.getNationId()],
    });
  }

  get f() {
    return this.form.controls;
  }
  /**
   * processSearch
   * @ param event
   */
  public processSearch() {
    this.organizatonSearch.name = this.form.value.name;
    this.organizatonSearch.code = this.form.value.code;
    this.organizatonSearch.effectiveStartDateFrom = this.form.value.effectiveStartDateFrom != null
      && this.form.value.effectiveStartDateFrom !== '' ?
      formatDate(this.form.value.effectiveStartDateFrom, 'dd/MM/yyyy', 'en') : null;
    this.organizatonSearch.effectiveStartDateTo = this.form.value.effectiveStartDateTo != null
      && this.form.value.effectiveStartDateTo !== '' ?
      formatDate(this.form.value.effectiveStartDateTo, 'dd/MM/yyyy', 'en') : null;
    this.organizatonSearch.effectiveEndDateFrom = this.form.value.effectiveEndDateFrom != null
      && this.form.value.effectiveEndDateFrom !== '' ?
      formatDate(this.form.value.effectiveEndDateFrom, 'dd/MM/yyyy', 'en') : null;
    this.organizatonSearch.effectiveEndDateTo = this.form.value.effectiveEndDateTo != null
      && this.form.value.effectiveEndDateTo !== '' ?
      formatDate(this.form.value.effectiveEndDateTo, 'dd/MM/yyyy', 'en') : null;
    this.organizatonSearch.first = APP_CONSTANTS.PAGE_DEFAULT['FIRST'];
    this.organizatonSearch.rows = APP_CONSTANTS.PAGE_DEFAULT['ROWS'];
    this.organizatonSearch.sortOrder = APP_CONSTANTS.PAGE_DEFAULT['SORT_ORDER'];
    this.organizatonSearch.sortField = APP_CONSTANTS.PAGE_DEFAULT['SORT_FIELD'];
    if( this.form.value.effectiveStartDateFrom != null && this.form.value.effectiveStartDateTo != null){
      if(this.form.value.effectiveStartDateFrom > this.form.value.effectiveStartDateTo){
        this.message.emit({ code: 'ERROR_5' });
        return;
      }
    }
    if( this.form.value.effectiveEndDateFrom != null && this.form.value.effectiveEndDateTo != null){
      if(this.form.value.effectiveEndDateFrom > this.form.value.effectiveEndDateTo){
        this.message.emit({ code: 'ERROR_6' });
        return;
      }
    }


    if (this.selectedNode) {
      this.organizatonSearch.parentId = this.selectedNode.data;
    }
    this.service.search(this.organizatonSearch).subscribe(res => {

      this.resultList = res.data;
    });
  }
  /**
   * chose
   * @ param item
   */
  public chose(item) {
    this.activeModal.close(item);
  }
  /**
   * nodeSelect
   * @ param event
   */
  public nodeSelect(event) {
    this.treeNodesService.getTreeNodeOrganization({ nodeOrganizationId: event.node.nodeId })
      .subscribe((res) => {
        let parentNode = CommonUtils.searchTree(this.nodes, 'nodeId', event.node.nodeId, 'children');
        parentNode.children = res.data;
      });
  }

  public resetTree() {
    this.selectedNode = null;
    this.organizatonSearch.parentId = null;
    this.form.controls['name'].setValue('');
    this.form.controls['code'].setValue('');
    this.form.controls['effectiveStartDateFrom'].setValue(null);
    this.form.controls['effectiveStartDateTo'].setValue(null);
    this.form.controls['effectiveEndDateFrom'].setValue(null);
    this.form.controls['effectiveEndDateTo'].setValue(null);
    this.tableComponent.reset();
  }
  public onBlur(event, clazz, number) {
    let dateStr = $("." + clazz + " span input").val().toString();
    let thiz = this;
    setTimeout(function () {
      let dateStrCur = $("." + clazz + " span input").val().toString();
      if (dateStr.length != 0) {
        let date = dateStrCur.split("/");
        let d = parseInt(date[0], 10),
          m = parseInt(date[1], 10),
          y = parseInt(date[2], 10);
        if (isNaN((new Date(y, m - 1, d)).getTime())) {
          switch(number){
            case 1:{
              thiz.message.emit({ code: 'ERROR_1' });
              break;
            }
            case 2:{
              thiz.message.emit({ code: 'ERROR_2' });
              break;
            }
            case 3:{
              thiz.message.emit({ code: 'ERROR_3' });
              break;
            }
            case 4:{
              thiz.message.emit({ code: 'ERROR_4' });
              break;
            }
          }
        }
      }
    }, 200);
  }
}
