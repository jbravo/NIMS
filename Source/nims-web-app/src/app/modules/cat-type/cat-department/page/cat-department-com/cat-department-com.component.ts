import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonUtils } from '@app/shared/services';
import { FormGroup } from '@angular/forms';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { DataTable, MessageService } from 'primeng/primeng';

import { CatDepartmentSerService } from '../../services/cat-department-ser.service'
import { COLS_TABLE } from '@app/shared/services/constants';
import { NodeService } from '@app/modules/controls/common/nodeservice';

import { TreeNode } from "primeng/api";

@Component({
  selector: 'cat-department-com',
  templateUrl: './cat-department-com.component.html',
  styleUrls: ['./cat-department-com.component.css']
})
export class CatDepartmentComComponent implements OnInit {

  advanceSearch = true;
  parentId: any;
  formSearch: FormGroup;
  filteredDept: any[];
  filteredDeptName: any[];
  first: number;
  rows: number;
  totalRecord: number;
  last: number;
  cols: any[];
  resultList: any = [];
  selectedResult: any = [];
  flagLazyLoad: boolean;
  @ViewChild('dt') dataTable: DataTable;

  filters = {

  }
  orders: { [s: string]: string } = {
    createTime: "desc"
  }
  catDepartments: [];
  displayDel = false;
  deleteObj = [];
  root: any[] = [];
  selectedNodes: any;
  constructor(
    private translation: TranslationService,
    private tabLayoutService: TabLayoutService,
    private dataCommon: DataCommonService,
    private messageService: MessageService,
    private departmentService: CatDepartmentSerService,
    private nodeService: NodeService
  ) {
    this.buildForm({})
  }

  ngOnInit() {
    this.initTree();
    this.cols = COLS_TABLE.CAT_DEPARTMENT;

  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      deptCode: [''],
      deptName: [''],
    });
  }

  onLazyLoad(event) {
    console.log(event);
    this.first = event.first;
    this.rows = event.rows;
    let field = event.sortField;
    let order: string = (event.sortOrder === 1) ? "asc" : "desc";
    // 
    // //this.search();
    this.filters = {
      ...this.filters,
      rowStatus: "1L",

    }
    if (this.parentId) {
      this.filters = {
        parentId: this.parentId + "L",
        ...this.filters
      }
    }
    if (field && "fullName" != field) {
      this.orders = {
        [field]: order,
        createTime: order,
      }
    }
    this.search();

  }

  search() {
    this.departmentService.getTotalRecord(this.filters).subscribe(res => {
      //console.log(res.data)
      this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
      this.departmentService.searchDept(this.first, this.rows, this.filters, this.orders).subscribe(data => {
        //console.log(data.data);
        this.resultList.data = data.data;
        this.resultList.first = this.first;
        this.resultList.rows = this.rows;
        this.resultList.totalRecords = this.totalRecord;

        for (let i = 0; i < this.resultList.data.length; i++) {
          let obj = this.resultList.data[i];
          this.resultList.data[i].fullName = this.resultList.data[i].deptName;
          while (obj.catDepartmentBO) {
            this.resultList.data[i].fullName = obj.catDepartmentBO.deptName + "/" + this.resultList.data[i].fullName;
            obj = obj.catDepartmentBO;
          }
        }
        console.log(this.resultList)
        this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      })
    })
  }

  searchFunc() {
    console.log("formS", this.formSearch);
    
    this.first = 0;
    this.dataTable.first = this.first;
    this.filters = {
      rowStatus: "1L",
    }
    if (this.formSearch.value.deptCode.label) {
      this.filters = {
        ...this.filters,
        'deptCode': this.formSearch.value.deptCode.label,
      }
    }else{
      this.filters = {
        ...this.filters,
        'deptCode': this.formSearch.value.deptCode,
      }
    }
    if (this.formSearch.value.deptName.label) {
      this.filters = {
        ...this.filters,
        deptName: this.formSearch.value.deptName.label,
      }
    }else{
      this.filters = {
        ...this.filters,
        'deptName': this.formSearch.value.deptName,
      }
    }

    if (this.parentId) {
      this.filters = {
        parentId: this.parentId + "L",
        ...this.filters
        //parentId: "341L"
      }
    }

    this.search();

  }

  filteredDeptFunc(event) {
    //console.log("autocomplete", event);
    let filters = {}
    filters = {
      deptCode: event.query,
      rowStatus: "1L"
    }
    if (this.parentId) {
      filters = {
        ...filters,
        parentId: this.parentId + "L",

      }
    }
    // this.dataCommon.filteredDeptFunc(this.formSearch.controls['deptName'], event, this.filteredDept);
    this.departmentService.searchDept(-1, -1, filters).subscribe(data => {
      this.filteredDept = [];
      //console.log("autocomplete", data);
      for (let i = 0; i < data.data.length; i++) {
        this.filteredDept.push({ label: data.data[i].deptCode, value: data.data[i].deptId });
      }
    });
  }

  filteredDeptNameFunc(event) {
    //console.log("autocomplete", event);
    let filters = {}
    filters = {
      deptName: event.query,
      rowStatus: "1L"
    }
    if (this.parentId) {
      filters = {
        ...filters,
        parentId: this.parentId + "L",

      }
    }
    // this.dataCommon.filteredDeptFunc(this.formSearch.controls['deptName'], event, this.filteredDept);
    this.departmentService.searchDept(-1, -1, filters).subscribe(data => {
      this.filteredDeptName = [];
      //console.log("autocomplete", data);
      for (let i = 0; i < data.data.length; i++) {
        this.filteredDeptName.push({ label: data.data[i].deptName, value: data.data[i].deptId });
      }
    });
  }

  filterDeptCode(value) {
    if (value) {
      this.filters = {
        ...this.filters,
        deptCode: value,
        rowStatus: "1L"
      }
    } else {
      this.searchFunc();
    }
    this.search();

  }

  filterDeptName(value) {
    if (value) {
      this.filters = {
        ...this.filters,
        deptName: value,
        rowStatus: "1L"
      }
    } else {
      this.searchFunc();
    }
    this.search();
  }

  setSelectedValue(event, element: string) {
    console.log(event.value);
    if (event.value != null && event.value !== '') {
      this.formSearch.controls[element].setValue(event.value.value);
    } else {
      this.formSearch.controls[element].setValue('');
    }
  }
  onRowSelect(event: any, template?: any) {
    // u can do something else with the data
    console.log('vtData : ', event);
    console.log(this.selectedResult);
  }
  onRowUnselect(event: any) {
    // simply logging the event
    console.log('row unselect : ', event);
  }

  preDelete(rowData) {
    let updateTime = new Date();
    if (rowData.createTime) {
      rowData.createTime = CommonUtils.stringToDate(rowData.createTime, 'yyyy/MM/dd hh:mm:ss.SSS');
    }
    rowData.updateTime = updateTime;
    rowData.updateTime = CommonUtils.stringToDate(rowData.updateTime, 'yyyy/MM/dd hh:mm:ss.SSS');
    rowData.rowStatus = "0"
    this.deleteObj.push(rowData)
  }

  delete(rowData) {
    //console.log(rowData);
    //isMultiDel
    this.deleteObj = [];
    this.preDelete(rowData);
    this.displayDel = true;

  }

  saveOrEdit(id?: number, action?: string) {
    this.departmentService.id = id;
    this.departmentService.action = action;
    this.tabLayoutService.open({
      component: 'CatDepartmentSaveComponent',
      header: id ? "cat.department.update.label" : "cat.department.add.label",
      breadcrumb: [
        { label: this.translation.translate('menu.header.cate') },
        { label: this.translation.translate(id ? 'cat.department.update.label' : 'cat.department.add.label') },
      ]
    });
  }

  async onConfirm() {
    console.log(this.deleteObj);
    let success: number = 0;
    let fail: number = 0;
    for (let i = 0; i < this.deleteObj.length; i++) {
      let obj = this.deleteObj[i];
      await this.departmentService.saveOrUpdateDept(obj).subscribe(async data => {
        if (data.status === 1) {
          await success++;
        } else {
          await fail++;
        }

        if ((await this.deleteObj.length - 1) === i) {
          this.selectedResult = [];
          await this.messageService.add({ severity: 'info', summary: 'Delete', detail: success + ' Delete successfully and ' + fail + ' Delete fail' });
          this.displayDel = false;
          this.search();
        }
      })


    }

  }

  onReject() {
    this.displayDel = false;
  }

  deleteList() {
    this.deleteObj = [];
    for (let rowData of this.selectedResult) {
      this.preDelete(rowData);
    }
    console.log(this.deleteObj)
    this.displayDel = true;
  }

  initTree() {
    this.departmentService.findById("337").subscribe(res => {
      //console.log("INIT ROOT ----------------------", res);
      let rootVt = {
        "data": res.data,
        "expandedIcon": "fa fa-folder-open",
        "collapsedIcon": "fa fa-folder",
        "leaf": false,
        "children": []
      }
      this.root.push(rootVt);
    })
  }

  onNodeExpand(event) {
    console.log("root", event);

    let childFilters = {}
    childFilters = {
      ...childFilters,
      rowStatus: "1L",
      parentId: event.node.data.deptId + "L",
    }
    event.node.children = [];
    this.departmentService.searchDept(-1, -1, childFilters).subscribe(res => {
      //console.log(res);
      for (let i = 0; i < res.data.length; i++) {
        let child = {
          "data": res.data[i],
          "expandedIcon": "fa fa-folder-open",
          "collapsedIcon": "fa fa-folder",
          "leaf": false,
          "children": []
        }
        event.node.children.push(child)

      }
    })
  }

  onNodeSelect(event) {
    console.log("Select", event);
    console.log("Node select", this.selectedNodes);
    this.parentId = event.node.data.deptId;
    this.searchFunc();

  }

}
