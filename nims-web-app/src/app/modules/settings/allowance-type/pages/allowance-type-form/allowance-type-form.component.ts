import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AllowanceTypeService } from '@app/core';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services';

@Component({
  selector: 'allowance-type-form',
  templateUrl: './allowance-type-form.component.html',
})
export class AllowanceTypeFormComponent implements OnInit {
  formSave: FormGroup;
  @Input() item: any;
  constructor(public activeModal: NgbActiveModal
            , private formBuilder: FormBuilder
            , private allowanceTypeService: AllowanceTypeService
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
        this.allowanceTypeService.saveOrUpdate(this.formSave.value)
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
      allowanceTypeId: [''],
      code: ['', Validators.compose([
        Validators.required
        , Validators.maxLength(50)])],
      name: ['', Validators.compose([
        Validators.required
        , Validators.maxLength(200)])],
      description : ['', Validators.compose([
        ValidationService.isValidInput
        , Validators.maxLength(1000)])]
    });
  }

  /**
   * setFormValue
   * param data
   */
  public setFormValue(data: any) {

    this.formSave.setValue(CommonUtils.copyProperties(this.formSave.value, data));
  }

}
