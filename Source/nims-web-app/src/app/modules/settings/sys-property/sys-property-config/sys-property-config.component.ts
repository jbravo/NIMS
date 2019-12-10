import { AppComponent } from './../../../../app.component';
import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChanges,  } from '@angular/core';
import { Nation } from '@app/core/models/nation.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { SysPropertyDetailBean } from '@app/core';
import { SysPropertyService } from '../sys-property.service';

@Component({
  selector: 'sys-property-config',
  templateUrl: './sys-property-config.component.html',
  styles: []
})
export class SysPropertyConfigComponent implements OnInit, OnChanges {
  formConfig: FormGroup;
  @Input() selectedProperty: any;

  positions = [
    { label: 'Trái', value: 'left', icon: 'fa fa-align-left' },
    { label: 'Giữa', value: 'center', icon: 'fa fa-align-center' },
    { label: 'Phải', value: 'right', icon: 'fa fa-align-right' }
  ];
  nations: any = [];
  menus: any = [];
  propDetails: any = [];
  selectedNation: Nation;
  selectedNationCode: string;
  constructor(private sysPropertyService: SysPropertyService
            , private formBuilder: FormBuilder
            , private app: AppComponent) {
    this.loadReference();
    this.buildForm();
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes.selectedProperty.currentValue) {
      this.selectedProperty = changes.selectedProperty.currentValue;
      this.findPropertyDetails(this.selectedProperty.propertyId);
    }
  }
  ngOnInit() {
    this.findPropertyDetails(this.selectedProperty.propertyId);
  }
  private loadReference(): void {
    this.sysPropertyService.findNations().subscribe(res => {
      this.nations = res.data;
    });
  }
  private buildForm(): void {
    this.formConfig = this.formBuilder.group({
      nationName: [''],
      nationId: [''],
      nationCode: [''],
      propertyId: [''],
      propertyName: [''],
      propertyCode: [''],
      isHide: [''],
      isTranslation: [''],
      align: [''],
      dateFormat: [''],
      numberFormat: [''],
      moneyFormat: [''],
      css: [''],
      js: [''],
      isRequire: [''],
      isUrl: [''],
      isNumber: [''],
      isEmail: [''],
      isIp: [''],
      minLength: [''],
      maxLength: [''],
      numberMin: [''],
      numberMax: ['']
    });
  }
  private findPropertyDetails(propertyId): void {
    this.selectedProperty.dataDetails = [];
    this.propDetails = this.selectedProperty.dataDetails;
    this.sysPropertyService.findPropertyDetails(propertyId).subscribe(res => {
      if (res.data.length > 0) {
        for (const item of res.data) {
          this.propDetails[item.nationBO.code] = new SysPropertyDetailBean(item.sysPropertyBO, item.nationBO);
          this.propDetails[item.nationBO.code] = CommonUtils.cloneObject(this.propDetails[item.nationBO.code], item);
        }
      }
      if (!this.selectedNationCode) {
        this.changePropertyNation(this.nations[0]);
      }
      this.setValueForm();
    });
  }
  private setValueForm(): void {
    if (this.propDetails[this.selectedNationCode] === undefined || this.propDetails[this.selectedNationCode] === null) {
      this.propDetails[this.selectedNationCode] = new SysPropertyDetailBean(this.selectedProperty, this.selectedNation);
    }
    this.buildForm();
    this.formConfig.setValue(CommonUtils.copyProperties(this.formConfig.value, this.propDetails[this.selectedNationCode]));
  }
  private onchangeNation(event: any): void {
    this.propDetails[this.selectedNationCode] = CommonUtils.cloneObject(this.propDetails[this.selectedNationCode], this.formConfig.value);
    this.changePropertyNation(this.nations[event.index]);
  }
  private changePropertyNation(item: Nation): void {
    if (!this.selectedProperty) {
      // toaster.pop("error","","Vui lòng chọn thuộc tính cần cấu hình ");
      return;
    }
    this.selectedNation = item;
    this.selectedNationCode = item.code;
    this.setValueForm();
  }
  processSaveOrUpdate() {
    if (!CommonUtils.isValidForm(this.formConfig)) {
      return;
    }
    this.app.confirmMessage(null, () => {// on accepted
      this.propDetails[this.selectedNationCode] = CommonUtils.cloneObject(this.propDetails[this.selectedNationCode], this.formConfig.value);
      const data = [];
      for (const key in this.propDetails) {
        const obj = this.propDetails[key];
          if (obj.propertyId && obj.nationId) {
            data.push(obj);
          }
      }
      this.sysPropertyService.savePropertyDetail(data).subscribe(res => {
        if (this.sysPropertyService.requestIsSuccess(res)) {
          this.findPropertyDetails(this.selectedProperty.propertyId);
        }
        });
    }, () => {// on rejected

    });
  }
  refreshPropertyDetails(): void {
    this.findPropertyDetails(this.selectedProperty.propertyId);
  }
  cancelPropertyDetails(): void {
    this.app.confirmMessage(null, () => {// on accepted
      this.selectedProperty = undefined;
    }, () => {// on rejected

    });
  }
}
