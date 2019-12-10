import { Component, OnInit, QueryList, ViewChildren, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControl, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { AppParamsService } from '@app/core/services/app-param/app-param.service';
import { AppComponent } from '@app/app.component';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Table } from 'primeng/components/table/table';
import { formatDate } from '@angular/common';
import { AutoComplete, Calendar } from 'primeng/primeng';

@Component({
  selector: 'list-app-param',
  templateUrl: './list-app-param.component.html',
})
export class ListAppParamComponent implements OnInit {

  /* DECLARE VARIABLE */
  @ViewChild(Table) tableComponent: Table;
  resultList: any = {};
  formSearch: FormGroup;

  listObjectTypeFromConstants = new Array();
  listObjectType = new Array();

  @ViewChild('birthdayFrom') birthdayFromF: Calendar;
  @ViewChild('birthdayTo') birthdayToF: Calendar;

  /* CONSTRUCTOR AND ngOnInit */
  constructor(
    private app: AppComponent,
    private appParamService: AppParamsService) {

    this.buildForm({});
  }

  ngOnInit() {
    this.formSearch.valueChanges.subscribe();
  }
  /* END CONSTRUCTOR AND ngOnInit */

  // FORM
  // build form
  private buildForm(formData: any) {
    this.formSearch = CommonUtils.createForm(formData, {
      parCode: [''],
      parName: [''],
      first: [0],
      recordsTotal: [null],
      rows: [10],
      sortField: [null],
      sortOrder: [1]
    });
  }

  // return form
  get f() {
    return this.formSearch.controls;
  }
  // END FORM

  // Search
  processSearch() {
      this.tableComponent.reset();
  }

  public onLazyLoad(event) {
    // Setting paging
    this.formSearch.controls["first"].setValue(event.first);
    this.formSearch.controls["rows"].setValue(event.rows);

    // Search
    this.appParamService.searchAppParam(this.formSearch.value).subscribe(res => {
      this.resultList = res.data;
    });
  }

}
