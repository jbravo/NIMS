import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { EmpTypeService } from '@app/core';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services';

@Component({
  selector: 'emp-type-form',
  templateUrl: './emp-type-form.component.html',
})
export class EmpTypeFormComponent implements OnInit {
  formSave: FormGroup;
  @Input() item: any;
  constructor(public activeModal: NgbActiveModal
            , private formBuilder: FormBuilder
            , private empTypeService: EmpTypeService
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
      this.app.confirmMessage(null, () => {// on accepted
        this.empTypeService.saveOrUpdate(this.formSave.value)
          .subscribe(res => {
            this.activeModal.close(res);
          });
      }, () => {// on rejected
      });
    }

  }

  /****************** CAC HAM COMMON DUNG CHUNG ****/

  /**
   * f
   */
  get f () {
    return this.formSave.controls;
  }
  /**
   * buildForm
   */
  private buildForm(): void {
    this.formSave = this.formBuilder.group({
      empTypeId: [''],
      code: ['', [Validators.required, Validators.maxLength(255)]],
      name: ['', [Validators.required, Validators.maxLength(255)]],
      isUsed: [''],
      hasSoldierInfo: [''],
      hasLabourContractInfo: [''],
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
