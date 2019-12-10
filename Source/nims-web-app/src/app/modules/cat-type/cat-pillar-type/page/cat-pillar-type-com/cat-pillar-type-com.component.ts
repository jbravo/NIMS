import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonUtils } from '@app/shared/services';
import { FormGroup } from '@angular/forms';
import { AUDIT_STATUS, CAT_ITEM, COLS_TABLE } from '@app/shared/services/constants';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { PermissionService } from '@app/shared/services/permission.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { AppComponent } from '@app/app.component';
import { MessageService, ConfirmationService, SortEvent } from 'primeng/api';
import { SelectItem } from '@app/modules/controls/common/selectitem';
import { CatPillarTypeServiceService } from '../../service/cat-pillar-type-service.service'
import { DataTable } from 'primeng/primeng';
import { ValidationService } from '@app/shared/services';

@Component({
  selector: 'cat-pillar-type-com',
  templateUrl: './cat-pillar-type-com.component.html',
  styleUrls: ['./cat-pillar-type-com.component.css'],
  providers: [ConfirmationService]
})
export class CatPillarTypeComComponent implements OnInit {

  advanceSearch = false;
  formSearch: FormGroup;
  first: number;
  rows: number;
  totalRecord: number;
  last: number;
  cols: any[];
  selectedColumns: any[];
  resultList: any = [];
  selectedResult: any = [];

  flagLazyLoad: boolean;

  @ViewChild('dt') dataTable: DataTable;
  filters = {};
  orders: { [s: string]: string; } = {
    createTime: "desc",
  }
  displayDel = false;
  deleteObj = [];
  isMultiDel: boolean = false;

  constructor(
    private app: AppComponent,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private pillarTypeService: CatPillarTypeServiceService,
  ) {
    this.buildForm({});
    this.resultList = [];
  }

  ngOnInit() {
    this.cols = COLS_TABLE.PILLAR_TYPE;
    this.selectedColumns = this.cols;
  }

  onProcess(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.onLazyLoad(event);
  }


  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      pillarTypeCode: [null],
      height: [null, ValidationService.positiveNumber],
      note: [null],
      basicInfo: [null],

    });
  }
  // load data table
  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
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

    console.log("lazyload", event);
    console.log(this.orders)
    this.filters = {
      ...this.filters,
      rowStatus: "1L",
    }
    this.pillarTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
      this.pillarTypeService.searchPillar(this.first, this.rows, this.filters, this.orders).subscribe(data => {
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

  showAdvance() {
    this.buildForm({});
    this.flagLazyLoad = true;
    this.advanceSearch = !this.advanceSearch;
  }

  search() {
    this.filters = {
      rowStatus: "1L",
    }
    if (this.advanceSearch === false) {
      if (this.formSearch.value.basicInfo && this.formSearch.value.basicInfo != "") {
        this.filters = {
          ...this.filters,
          pillarTypeCode: this.formSearch.value.basicInfo

        }
      }
    } else {
      if (this.formSearch.value.pillarTypeCode && this.formSearch.value.pillarTypeCode != "") {
        this.filters = {
          ...this.filters,
          pillarTypeCode: this.formSearch.value.pillarTypeCode

        }
      }
      if (this.formSearch.value.height && this.formSearch.value.height != "") {
        this.filters = {
          ...this.filters,
          height: this.formSearch.value.height

        }
      }
      if (this.formSearch.value.note && this.formSearch.value.note != "") {
        this.filters = {
          ...this.filters,
          note: this.formSearch.value.note

        }
      }
    }
    console.log("formSearch", this.formSearch.value.basicInfo)
    this.pillarTypeService.getTotalRecord(this.filters).subscribe(res => {
      //console.log("total", res.data)
      this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
      this.pillarTypeService.searchPillar(this.first, this.rows, this.filters, this.orders).subscribe(data => {
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

  filterHeight(event) {
    this.formSearch.get('height').setValue(event);
    this.first = 0;
    if (event) {
      this.filters = {
        ...this.filters,
        height: event
      }
    } else {
      this.basicSearchFunc();
    }
    this.pillarTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      //this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
    })
    this.pillarTypeService.searchPillar(this.first, this.rows, this.filters, this.orders).subscribe(data => {
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

  filterNote(event) {
    this.formSearch.get('note').setValue(event);
    this.first = 0;
    if (event) {
      this.filters = {
        ...this.filters,
        note: event
      }
    } else {
      this.basicSearchFunc();
    }
    this.pillarTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      //this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
    })
    this.pillarTypeService.searchPillar(this.first, this.rows, this.filters, this.orders).subscribe(data => {
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
  filterPillarTypeCode(event) {
    this.formSearch.get('basicInfo').setValue(event);
    this.formSearch.get('pillarTypeCode').setValue(event);
    this.first = 0;
    if (event) {
      this.filters = {
        ...this.filters,
        pillarTypeCode: event
      }
    } else {
      this.basicSearchFunc();
    }
    this.pillarTypeService.getTotalRecord(this.filters).subscribe(res => {
      console.log(res.data)
      //this.last = (this.rows + this.first) < res.data ? this.first + this.rows : res.data;
      this.totalRecord = res.data;
    })
    this.pillarTypeService.searchPillar(this.first, this.rows, this.filters, this.orders).subscribe(data => {
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
    let createTime: Date = new Date();
    let updateTime = new Date();
    if (rowData.createTime) {
      createTime = new Date(rowData.createTime);
    }
    if (rowData.updateTime) {
      updateTime = new Date(rowData.updateTime);
    }
    this.deleteObj.push({
      pillarTypeId: rowData.pillarTypeId,
      height: rowData.height,
      pillarTypeCode: rowData.pillarTypeCode,
      note: rowData.note,
      createTime: createTime.toISOString(),
      updateTime: updateTime.toISOString(),
      rowStatus: "0"
    })
  }

  delete(rowData) {
    //console.log(rowData);
    //isMultiDel
    this.deleteObj = [];
    this.preDelete(rowData);
    this.displayDel = true;

  }

  saveOrEdit(id?: number, action?: string) {
    this.pillarTypeService.id = id;
    this.pillarTypeService.action = action;
    this.tabLayoutService.open({
      component: 'CatPillarTypeSaveComponent',
      header: id ? "pillar.type.update.label" : "pillar.type.add.label",
      breadcrumb: [
        { label: this.translation.translate('menu.header.cate') },
        { label: this.translation.translate(id ? 'pillar.type.update.label' : 'pillar.type.add.label') },
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
      await this.pillarTypeService.saveOrUpdatePillar(obj).subscribe(async data => {
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
