import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SysCatService } from '@app/core/services';
import { ValidationService } from '@app/shared/services';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';

@Component({
  selector: 'app-sys-cat-add',
  templateUrl: './sys-cat-add.component.html'
})
export class SysCatAddComponent implements OnInit {

  formSave: FormGroup;

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private sysCatService: SysCatService,
    private app: AppComponent
  ) {
    this.buildForm();
  }

  ngOnInit() { }

  /**
   * processSaveOrUpdate
   */
  processSaveOrUpdate() {
    if (!CommonUtils.isValidForm(this.formSave)) {
      return;
    }

    this.app.confirmMessage(null, () => {// on accepted
      this.sysCatService.saveOrUpdate(this.formSave.value)
      .subscribe(res => {
          this.activeModal.close(res);
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
      sysCatId: [''],
      code: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      name: ['', Validators.compose([Validators.required, Validators.maxLength(500)])],
      sysCatTypeName: [''],
      sysCatTypeId: [''],
      sortOrder: ['', Validators.compose([ValidationService.positiveInteger, Validators.max(9999999999)])],
      status: [''],
      description: ['', Validators.maxLength(1000)]
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
