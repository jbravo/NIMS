import { ValidationService } from './../../../../../shared/services/validation.service';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from './../../../../../app.component';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { SysCatTypeService } from '@app/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-sys-cat-type-add',
  templateUrl: './sys-cat-type-add.component.html'
})
export class SysCatTypeAddComponent implements OnInit {

  formSave: FormGroup;

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private sysCatTypeService: SysCatTypeService,
    private app: AppComponent
  ) {
    this.buildForm();
  }

  ngOnInit() {
  }

  /**
   * processSaveOrUpdate
   */
  processSaveOrUpdate() {
    if (!CommonUtils.isValidForm(this.formSave)) {
      return;
    }

    this.app.confirmMessage(null, () => {// on accepted
      this.sysCatTypeService.saveOrUpdate(this.formSave.value)
      .subscribe(res => {
        if (this.sysCatTypeService.requestIsSuccess(res) ) {
          this.activeModal.close(res);
        }
      });
      }, () => {// on rejected
    });
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
      sysCatTypeId: [''],
      code: ['', [Validators.required, Validators.maxLength(50)]],
      name: ['', [Validators.required, Validators.maxLength(50)]]
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
