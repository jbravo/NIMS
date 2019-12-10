import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonUtils } from '@app/shared/services';
import { FormGroup } from '@angular/forms';
import { AUDIT_STATUS, CAT_ITEM, COLS_TABLE, SLEEVE_TYPE_TYPE } from '@app/shared/services/constants';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { PermissionService } from '@app/shared/services/permission.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { MessageService, ConfirmationService, SortEvent } from 'primeng/api';
import { CatSleeveTypeServiceService } from '../../service/cat-sleeve-type-service.service'
import { DataTable } from 'primeng/primeng';

@Component({
  selector: 'cat-sleeve-type-com',
  templateUrl: './cat-sleeve-type-com.component.html',
  styleUrls: ['./cat-sleeve-type-com.component.css']
})
export class CatSleeveTypeComComponent implements OnInit {

  advanceSearch = false;
  formSearch: FormGroup;
  first: number;
  rows: number;
  totalRecord: number;
  last: number;
  cols: any[];
  resultList: any = [];
  selectedResult: any = [];
  flagLazyLoad: boolean;

  @ViewChild('dt') dataTable: DataTable;
  filters = {};
  displayDel = false;
  deleteObj = [];
  isMultiDel: boolean = false;
  typeList: any = [];
  orders: { [s: string]: string; } = {
    createTime: "desc",
  }
  constructor(
    private translation: TranslationService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private dataCommon: DataCommonService,
    private sleeveTypeService: CatSleeveTypeServiceService,
  ) {
    this.buildForm({});
    this.resultList = [];
  }

  ngOnInit() {
    this.cols = COLS_TABLE.SLEEVE_TYPE;
    this.typeList = this.dataCommon.getDropDownList(SLEEVE_TYPE_TYPE);
  }

  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      sleeveTypeCode: [null],
      type: [null],
      capacity: [null],
      note: [null],
      basicInfo: [null],

    });
  }

  showAdvance() {
    this.buildForm({});
    this.flagLazyLoad = true;
    this.advanceSearch = !this.advanceSearch;
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }

  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    console.log("lazyload", event);
    let field = event.sortField;
    let order: string = (event.sortOrder === 1) ? "asc" : "desc";
    // 
    // //this.search();
    if (field) {
      this.orders = {
        [field]: order,
        createTime: "desc",
      }
    }
    //this.search();

    this.filters = {
      ...this.filters,
      rowStatus: "1L",
    }
    this.sleeveTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
      this.sleeveTypeService.searchSleeve(this.first, this.rows, this.filters, this.orders).subscribe(data => {
        console.log(data.data);
        this.resultList.data = data.data;
        this.resultList.first = this.first;
        this.resultList.rows = this.rows;
        this.resultList.totalRecords = this.totalRecord;
        console.log(this.resultList)
        this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      })
    })

  }

  search() {
    this.filters = {
      rowStatus: "1L",
    }
    if (this.advanceSearch === false) {
      if (this.formSearch.value.basicInfo && this.formSearch.value.basicInfo != "") {
        this.filters = {
          ...this.filters,
          sleeveTypeCode: this.formSearch.value.basicInfo

        }
      }
    } else {
      if (this.formSearch.value.sleeveTypeCode && this.formSearch.value.sleeveTypeCode != "") {
        this.filters = {
          ...this.filters,
          sleeveTypeCode: this.formSearch.value.sleeveTypeCode

        }
      }
      if (this.formSearch.value.type && this.formSearch.value.type != null) {
        this.filters = {
          ...this.filters,
          type: (this.formSearch.value.type.value)

        }
      }
      if (this.formSearch.value.note && this.formSearch.value.note != "") {
        this.filters = {
          ...this.filters,
          note: this.formSearch.value.note

        }
      }
      if (this.formSearch.value.capacity && this.formSearch.value.capacity != "") {
        this.filters = {
          ...this.filters,
          capacity: this.formSearch.value.capacity

        }
      }
    }
    console.log("formSearch", this.filters)
    this.sleeveTypeService.getTotalRecord(this.filters).subscribe(res => {
      //console.log("total", res.data)
      this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
      this.sleeveTypeService.searchSleeve(this.first, this.rows, this.filters, this.orders).subscribe(data => {
        console.log("data", data.data);
        this.resultList.data = data.data;
        this.resultList.first = this.first;
        this.resultList.rows = this.rows;
        this.resultList.totalRecords = this.totalRecord;
        //this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
        //console.log(this.resultList)

      })
    })

  }

  basicSearchFunc() {
    this.flagLazyLoad = true;
    this.first = 0;
    this.dataTable.first = this.first;
    this.search();

  }

  filterSleeveTypeCode(event) {
    this.formSearch.get('sleeveTypeCode').setValue(event);
    this.formSearch.get('basicInfo').setValue(event);
    console.log("event", event)
    this.first = 0;
    if (event) {
      this.filters = {
        ...this.filters,
        sleeveTypeCode: event
      }
    } else {
      this.basicSearchFunc();
    }
    this.sleeveTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      //this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
    })
    this.sleeveTypeService.searchSleeve(this.first, this.rows, this.filters, this.orders).subscribe(data => {
      console.log(data.data);
      this.resultList.data = data.data;
      this.resultList.first = this.first;
      this.resultList.rows = this.rows;
      this.resultList.totalRecords = this.totalRecord;
      console.log(this.resultList)
      this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      this.dataTable.first = 0;
    })
  }

  filterCapacity(event) {
    this.formSearch.get('capacity').setValue(event);
    console.log("event", event)
    this.first = 0;
    if (event) {
      this.filters = {
        ...this.filters,
        capacity: event
      }
    } else {
      this.basicSearchFunc();
    }
    this.sleeveTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      //this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
    })
    this.sleeveTypeService.searchSleeve(this.first, this.rows, this.filters, this.orders).subscribe(data => {
      console.log(data.data);
      this.resultList.data = data.data;
      this.resultList.first = this.first;
      this.resultList.rows = this.rows;
      this.resultList.totalRecords = this.totalRecord;
      console.log(this.resultList)
      this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      this.dataTable.first = 0;
    })
  }

  filterType(event) {
    this.formSearch.get('type').setValue(event);
    this.first = 0;
    if (event != null) {
      this.filters = {
        ...this.filters,
        type: (event)
      }
    } else {
      this.basicSearchFunc();
    }
    this.sleeveTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      //this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
    })
    this.sleeveTypeService.searchSleeve(this.first, this.rows, this.filters, this.orders).subscribe(data => {
      console.log(data.data);
      this.resultList.data = data.data;
      this.resultList.first = this.first;
      this.resultList.rows = this.rows;
      this.resultList.totalRecords = this.totalRecord;
      console.log(this.resultList)
      this.last = (this.rows + this.first) < this.resultList.totalRecords ? this.first + this.rows : this.resultList.totalRecords;
      this.dataTable.first = 0;
    })
  }

  customSort(event: SortEvent) {
    console.log("eventSort", event);
    this.orders = {
      createTime: "desc"
    }
    // event.data.sort((data1, data2) => {
    //   let value1 = data1[event.field];
    //   let value2 = data2[event.field];
    //   let result = null;

    //   if (value1 == null && value2 != null)
    //     result = -1;
    //   else if (value1 != null && value2 == null)
    //     result = 1;
    //   else if (value1 == null && value2 == null)
    //     result = 0;
    //   else if (typeof value1 === 'string' && typeof value2 === 'string')
    //     result = value1.localeCompare(value2);
    //   else
    //     result = (value1 < value2) ? -1 : (value1 > value2) ? 1 : 0;

    //   return (event.order * result);
    // });

  }


  setSelectedValue(event, element: string) {
    console.log(event.value);
    if (event.value != null && event.value !== '') {
      this.formSearch.controls[element].setValue(event.value.value);
    } else {
      this.formSearch.controls[element].setValue('');
    }
  }

  // clearDeptId() {
  //   this.station = '';
  // }


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
    this.sleeveTypeService.id = id;
    this.sleeveTypeService.action = action;
    this.tabLayoutService.open({
      component: 'CatSleeveTypeSaveComponent',
      header: id ? "sleeve.type.update.label" : "sleeve.type.add.label",
      breadcrumb: [
        { label: this.translation.translate('menu.header.cate') },
        { label: this.translation.translate(id ? 'sleeve.type.update.label' : 'sleeve.type.add.label') },
      ]
    });
    // ['/station/station-save',{id:rowData.stationId, action:'edit'}]
  }

  async onConfirm() {
    console.log(this.deleteObj);
    let success: number = 0;
    let fail: number = 0;
    for (let i = 0; i < this.deleteObj.length; i++) {
      let obj = this.deleteObj[i];
      await this.sleeveTypeService.saveOrUpdateSleeve(obj).subscribe(async data => {
        //console.log(data);
        if (data.status === 1) {
          await success++;
          //this.messageService.add({ severity: 'info', summary: 'Delete', detail: 'Delete successfully' });
        } else {
          await fail++;
          //this.messageService.add({ severity: 'error', summary: 'Delete', detail: 'Delete fail' });
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

}
