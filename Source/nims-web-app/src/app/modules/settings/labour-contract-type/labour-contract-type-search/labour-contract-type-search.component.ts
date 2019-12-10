import { ValidationService } from './../../../../shared/services/validation.service';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { LabourContractTypeService } from '@app/core';
import { LabourContractTypeFormComponent } from '../labour-contract-type-form/labour-contract-type-form.component';
import { AppComponent } from '@app/app.component';

@Component({
    selector: 'labour-contract-type-search',
    templateUrl: './labour-contract-type-search.component.html',
})
export class LabourContractTypeSearchComponent implements OnInit {
  resultList: any = {};
  credentials: any = {};
  formSearch: FormGroup;

  constructor(private formBuilder: FormBuilder
            , private modalService: NgbModal
            , private labourContractTypeService: LabourContractTypeService
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
      code: ['' , Validators.compose([
          Validators.maxLength(50)

      ]) ],
      name: ['' , Validators.compose([
          Validators.maxLength(200)

      ])],
      status: [''],
    });
  }
  /**
   * thuc hien tim kiem
   */
  processSearch(event) {
    if (CommonUtils.isValidForm(this.formSearch)) {
      this.labourContractTypeService.search(this.formSearch.value, event).subscribe(res => {
        this.resultList = res;
      });
    }
  }
  /**
   * prepareUpdate
   * param item
   */
  prepareSaveOrUpdate(item) {
    const modalRef = this.modalService.open(LabourContractTypeFormComponent, DEFAULT_MODAL_OPTIONS);
    if (item && item.labourContractTypeId > 0) {
      this.labourContractTypeService.findOne(item.labourContractTypeId)
        .subscribe(res => {
          modalRef.componentInstance.setFormValue(res.data);
        });
    }
    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.labourContractTypeService.requestIsSuccess(result)) {
        this.processSearch(null);
      }
      });
    }
    /**
     * prepareDelete
     * param item
     */
    processDelete(item) {
      if (item && item.labourContractTypeId > 0) {
        this.app.confirmDelete(null, () => {// on accepted
          this.labourContractTypeService.deleteById(item.labourContractTypeId)
          .subscribe(res => {
            if (this.labourContractTypeService.requestIsSuccess(res)) {
              this.processSearch(null);
            }
          });
        }, () => {// on rejected

        });
      }
    }
}
