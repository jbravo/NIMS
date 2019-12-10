import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder , Validators} from '@angular/forms';
import { LabourContractTypeService } from '@app/core';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services';

@Component({
    selector: 'labour-contract-type-form',
    templateUrl: './labour-contract-type-form.component.html',
  })
export class LabourContractTypeFormComponent implements OnInit {
    formSave: FormGroup;
    @Input() item: any;
  constructor(public activeModal: NgbActiveModal
            , private formBuilder: FormBuilder
            , private labourContractTypeService: LabourContractTypeService
            , private app: AppComponent) {
    this.buildForm();
  }
  /**
   * ngOnInit
   */
  ngOnInit() {
  }
  /**
   * processSaveOrUpdate
   */
  processSaveOrUpdate() {
    if (!CommonUtils.isValidForm(this.formSave)) {
      return;
    } else {
      this.app.confirmMessage(null, () => {// on rejected
        this.labourContractTypeService.saveOrUpdate(this.formSave.value)
        .subscribe(res => {
          this.activeModal.close(res);
        });
      }, () => {// on rejected

      });
    }
  }

  /****************** CAC HAM COMMON DUNG CHUNG ****/
  get f () {
    return this.formSave.controls;
  }
  /**
   * buildForm
   */
  private buildForm(): void {
    this.formSave = this.formBuilder.group({
      labourContractTypeId: [''],
      code: ['' , Validators.compose([
        Validators.required
        , Validators.maxLength(50)
        , Validators.max(50)
      ]) ],
      name: ['' , Validators.compose([
        Validators.required
        , Validators.maxLength(200)
        , Validators.max(200)
      ])],
      status: [''],
    });
  }
  /**
   * validate save
   */
  private validateBeforeSave(): boolean {
    return true;
  }
  /**
   * setFormValue
   * param data
   */
  public setFormValue(data: any) {
    this.formSave.setValue(CommonUtils.copyProperties(this.formSave.value, data));
  }
}
