import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommonUtils} from "@app/shared/services";
import {CommonParam} from "@app/core/app-common-param";
import {AppComponent} from "@app/app.component";
import {MessageService} from "primeng/api";

@Component({
  selector: 'locate',
  templateUrl: './locate.component.html',
  styleUrls: ['./locate.component.css']
})
export class LocateComponent implements OnInit {

  formLocate: FormGroup;

  constructor(private fb: FormBuilder,
              private commonParam: CommonParam,
              private app: AppComponent,
              private messageService: MessageService) {
    this.buildForm();
  }

  ngOnInit() {
  }

  // dinh vi
  locateFunction(){
    this.messageService.clear();
    this.getFormValidationErrors(() => {
      this.commonParam.locateFunction(this.formLocate.controls['longitude'].value,this.formLocate.controls['latitude'].value);
    });
  }

  buildForm() {
    this.formLocate = this.fb.group({
      longitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('locate.longitude')])],
      latitude: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('locate.latitude')])],
    });
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    if (CommonUtils.getFormValidationErrors(this.formLocate, this.app, 'locate')) {
      success();
    }
  }

  closePopupLeft() {
    this.commonParam.closePopupLeft();
  }


}
