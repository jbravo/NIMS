import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AllowanceTypeFormComponent } from '../allowance-type-form/allowance-type-form.component';
import { AllowanceTypeService } from '@app/core';
import { DEFAULT_MODAL_OPTIONS } from '@app/core/app-config';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services';

@Component({
  selector: 'allowance-type-search',
  templateUrl: './allowance-type-search.component.html',
})
export class AllowanceTypeSearchComponent implements OnInit {
  resultList: any = {};
  credentials: any = {};
  formSearch: FormGroup;

  constructor(private formBuilder: FormBuilder
            , private modalService: NgbModal
            , private allowanceTypeService: AllowanceTypeService
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
      code: ['', Validators.compose([
        Validators.maxLength(50)
        ])
      ],
      name: ['', Validators.compose([
        Validators.maxLength(200)
        ])
      ],
      description: ['', Validators.compose([
        Validators.maxLength(1000)
        ])
      ],
    });
  }
  /**
   * thuc hien tim kiem
   */
  processSearch(event) {
    this.allowanceTypeService.search(this.formSearch.value, event).subscribe(res => {
      this.resultList = res;
    });
  }
  /**
   * prepareUpdate
   * param item
   */
  prepareSaveOrUpdate(item) {
    const modalRef = this.modalService.open(AllowanceTypeFormComponent, DEFAULT_MODAL_OPTIONS);
    if (item && item.allowanceTypeId > 0) {

      this.allowanceTypeService.findOne(item.allowanceTypeId)
        .subscribe(res => {
          modalRef.componentInstance.setFormValue(res.data);
        });
    }

    modalRef.result.then((result) => {
      if (!result) {
        return;
      }
      if (this.allowanceTypeService.requestIsSuccess(result)) {
        this.processSearch(null);
      }
    });
  }
  /**
   * prepareDelete
   * param item
   */
  processDelete(item) {
    if (item && item.allowanceTypeId > 0) {
      this.app.confirmDelete(null, () => {// on accepted
        this.allowanceTypeService.deleteById(item.allowanceTypeId)
        .subscribe(res => {
          if (this.allowanceTypeService.requestIsSuccess(res)) {
            this.processSearch(null);
          }
        });
      }, () => {// on rejected

      });
    }
  }
}
