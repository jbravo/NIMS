import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from '@app/app.component';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { ValidationService } from '@app/shared/services';
import { SysPropertyService } from '../sys-property.service';

@Component({
  selector: 'app-sys-property-form',
  templateUrl: './sys-property-form.component.html'
})
export class SysPropertyFormComponent implements OnInit {
  formSave: FormGroup;
  @Input() item: any;
  menus: any = [];
  tables: any = [];
  columns: any = [];
  constructor(public activeModal: NgbActiveModal
            , private formBuilder: FormBuilder
            , private sysPropertyService: SysPropertyService
            , private app: AppComponent) {
    this.buildForm();
    this.loadReference();
  }
  ngOnInit() { }
  get f() {
    return this.formSave.controls;
  }
  private loadReference(): void {
    this.sysPropertyService.findMenus().subscribe(res => {
      this.menus = res.data;
    });
    this.sysPropertyService.findTables().subscribe(res => {
      this.tables = res.data;
    });
  }
  /**
   * processSaveOrUpdate
   */
  processSaveOrUpdate() {
    if (!this.validateBeforeSave()) {
      return;
    }
    this.app.confirmMessage(null, () => {// on accepted
      this.sysPropertyService.saveOrUpdate(this.formSave.value)
        .subscribe(res => {
          this.activeModal.close(res);
        });
    }, () => {// on rejected

    });
  }

  /****************** CAC HAM COMMON DUNG CHUNG ****/
  /**
   * buildForm
   */
  private buildForm(): void {
    this.formSave = this.formBuilder.group({
      propertyId: [''],
      code: ['', [Validators.required, Validators.maxLength(50)]],
      name: ['', [Validators.required, Validators.maxLength(200)]],
      startDate: [''],
      endDate: [''],
      menuId: [''],
      isValidator: [''],
      tableName: [''],
      columnName: [''],
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
  onChangeFieldTableName(tableName): void {
    this.sysPropertyService.findColumns(tableName).subscribe(res => {
      this.columns = [];
      for (const item of res.data) {
        this.columns.push({
          code: item,
          name: item
        });
      }
      console.log(this.columns);
    });
  }
}
