import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EmpTypeFormComponent } from '../emp-type-form/emp-type-form.component';
import { EmpTypeService } from '@app/core';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services';

@Component({
  selector: 'emp-type-search',
  templateUrl: './emp-type-search.component.html'
})
export class EmpTypeSearchComponent implements OnInit {
  resultList: any = {};
  credentials: any = {};
  formSearch: FormGroup;

  constructor(private formBuilder: FormBuilder
            , private modalService: NgbModal
            , private empTypeService: EmpTypeService
            , private app: AppComponent) {
    this.buildForm();
  }

  ngOnInit() {
  }
  get f () {
    return this.formSearch.controls;
  }
  private buildForm(): void {
    this.formSearch = this.formBuilder.group({
      organizationId: [''],
      empTypeId: ['51'],
      employeeId: ['1'],
      startDate: [1543597200000],
      endDate: [''],
      code: [''],
      name: [''],
      isUsed: [''],
      hasSoldierInfo: [''],
      hasLabourContractInfo: [''],
    });
  }
  public onChangeDate() {
    console.log('onDateChanged');
  }
  /**
   * thuc hien tim kiem
   */
  processSearch(event) {
    this.app.isProcessing(true);
    if (CommonUtils.isValidForm(this.formSearch)) {
      this.empTypeService.search(this.formSearch.value, event).subscribe(res => {
        this.resultList = res;
        this.app.isProcessing(false);
      });
    }
  }
  /**
   * prepareUpdate
   * param item
   */
  prepareSaveOrUpdate(item) {
    const modalRef = this.modalService.open(EmpTypeFormComponent, DEFAULT_MODAL_OPTIONS);
    if (item && item.empTypeId > 0) {
      this.empTypeService.findOne(item.empTypeId)
        .subscribe(res => {
          modalRef.componentInstance.setFormValue(res.data);
        });
    }

    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.empTypeService.requestIsSuccess(result)) {
        this.processSearch(null);
      }
    });
  }
  /**
   * prepareDelete
   * param item
   */
  processDelete(item) {
    if (item && item.empTypeId > 0) {
      this.app.confirmDelete(null, () => {// on accepted
        this.empTypeService.deleteById(item.empTypeId)
        .subscribe(res => {
          if (this.empTypeService.requestIsSuccess(res)) {
            this.processSearch(null);
          }
        });
      }, () => {// on rejected

      });
    }
  }

  onChange(event) {
    console.log(event);
    console.log(this.formSearch.value);
  }
}
