import { delay } from 'rxjs/operators';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from './../../../../../app.component';
import { FormBuilder, FormGroup, FormControl, FormArray } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SysCatTypeService } from '@app/core';

@Component({
  selector: 'app-sys-cat-type-tranfer',
  templateUrl: './sys-cat-type-tranfer.component.html'
})
export class SysCatTypeTranferComponent implements OnInit {

  formSave: FormGroup;
  listNation: [];

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private sysCatTypeService: SysCatTypeService,
    private app: AppComponent
  ) {
    this.buildForm();
  }

  ngOnInit() {
    this.sysCatTypeService.findTransferNation()
      .subscribe(res => {
        this.listNation = res.data;
    });
  }

  /**
   * processSaveOrUpdate
   */
  processSaveOrUpdate() {
    if (!CommonUtils.isValidForm(this.formSave)) {
      return;
    }
    this.app.confirmMessage(null, () => {// on accepted
      this.sysCatTypeService.transferNation(this.formSave.value)
      .subscribe(res => {
        if (this.sysCatTypeService.requestIsSuccess(res) ) {
          this.activeModal.close(res);
        }
      });
      }, () => {// on rejected
    });
  }

  onChoosenNation(item, isChecked: boolean) {
    const nationFormArray = <FormArray>this.formSave.controls.lstNationId;

    if (isChecked) {
      nationFormArray.push(new FormControl(item.nationId));
    } else {
      const index = nationFormArray.controls.findIndex(x => x.value === item.nationId);
      nationFormArray.removeAt(index);
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
      sysCatTypeId: [''],
      lstNationId: this.formBuilder.array([])
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
