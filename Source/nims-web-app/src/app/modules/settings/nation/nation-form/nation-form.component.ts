import { CommonUtils } from '@app/shared/services/common-utils.service';
import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AppComponent } from '@app/app.component';
import { ValidationService } from '@app/shared/services/validation.service';
import { NationService } from '@app/core';

@Component({
  selector: 'nation-form',
  templateUrl: './nation-form.component.html',
})
export class NationFormComponent implements OnInit {
  formSave: FormGroup;
  @Input() item: any;
  constructor(public activeModal: NgbActiveModal
    , private formBuilder: FormBuilder
    , private nationService: NationService
    , private app: AppComponent) {
  }

  /**
   * ngOnInit
   */
  ngOnInit() {
    this.initForm();
  }

  /**
   * processSaveOrUpdate
   */
  processSaveOrUpdate() {
    if (!this.validateBeforeSave()) {
      return;
    }
    this.app.confirmMessage(null, () => {// on accepted
      this.nationService.saveOrUpdate(this.formSave.value)
        .subscribe(res => {
            this.activeModal.close(res);
        });
    }, () => {
      // on rejected
    });
  }

  get f () {
    return this.formSave.controls;
  }

  initForm() {
    this.formSave = this.formBuilder.group({
      nationId: [''],
      code: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      name: ['', Validators.compose([Validators.required, Validators.maxLength(200)])],
      isDefault: [''],
      requirePersionalId: [''],
      phoneAreaCode: ['', Validators.compose([Validators.required, Validators.maxLength(50)])],
      description: ['', Validators.compose([Validators.required, Validators.maxLength(1000)])],
    });
  }

  /**
   * validate save
   */
  private validateBeforeSave(): boolean {
    return CommonUtils.isValidForm(this.formSave);
  }

  /**
   * setFormValue
   * param data
   */
  public setFormValue(data: any) {
    this.formSave.setValue(CommonUtils.copyProperties(this.formSave.value, data));
  }
}

