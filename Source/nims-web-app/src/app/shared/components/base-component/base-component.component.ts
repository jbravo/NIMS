import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SysPropertyDetailBean } from '@app/core/models/sys-property-details.model';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { ValidationService } from '@app/shared/services/validation.service';

@Component({
  selector: 'base-component',
  templateUrl: './base-component.component.html',
  styles: []
})
export class BaseComponent implements OnInit {
  public propertyConfigs = new Array<SysPropertyDetailBean>();
  public getPropery(code: string): SysPropertyDetailBean {
    const data =  this.propertyConfigs.filter(item => item.propertyCode === code);
    return data[0];
  }
  constructor(public actr: ActivatedRoute
            , public formBuilder: FormBuilder) {
    this.findPropertyDetails();
  }

  ngOnInit() {
  }
  /**
   * Lay cau hinh cac thuoc tinh
   */
  private findPropertyDetails() {
    this.actr.data.subscribe(res => {
      this.propertyConfigs = CommonUtils.toPropertyDetails(res.props.data);
    });
  }
  /**
   * Khoi tao FormGroup
   */
  public buildForm(formData: any, formConfig: any, valiDateForm?: any): FormGroup {
    return CommonUtils.createFormNew(formData, formConfig, this.propertyConfigs, valiDateForm );
  }

}
