import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { ValidationService } from '@app/shared/services';
import { AppComponent } from '@app/app.component';
import { MessageService } from 'primeng/api';
import { TranslationService } from "angular-l10n";
import { PermissionService } from "@app/shared/services/permission.service";
import { TabLayoutService } from '@app/layouts/tab-layout';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { SLEEVE_TYPE_TYPE } from '@app/shared/services/constants';
import { CatDepartmentSerService } from '../../services/cat-department-ser.service';
import { CatTerantSerService } from '../../services/cat-terant-ser.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'cat-department-save',
  templateUrl: './cat-department-save.component.html',
  styleUrls: ['./cat-department-save.component.css']
})
export class CatDepartmentSaveComponent implements OnInit {

  formAdd: FormGroup;
  statusCheck = false;
  action: string;
  id: number;
  isEdit: boolean = false;
  tenantList: any[] = [];
  parentDepts:any = [];
  object: any;
  hide: boolean = true;


  constructor(
    private app: AppComponent,
    private translation: TranslationService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private dataCommon: DataCommonService,
    private departmentService: CatDepartmentSerService,
    private terantService: CatTerantSerService
  ) {
    this.buildForm({});
    this.getTenantsList();
  }

  ngOnInit() {
    if(this.departmentService.action == 'view'){
      this.hide = false;
    }
    //this.tenantList= this.dataCommon.getDropDownList(SLEEVE_TYPE_TYPE);
    if (this.departmentService.action == 'edit' || this.departmentService.action == 'view') {
      this.isEdit = true;
      this.action = this.departmentService.action;
      this.id = this.departmentService.id;
      //this.formAdd.value.pillarTypeCode = this.pillarTypeService.id;
      this.departmentService.findById(this.departmentService.id).subscribe(res => {
        console.log(res);
        this.object = res.data;
        this.departmentService.findById(this.object.parentId).subscribe(res => {
          console.log(res);
          
          if(res.status === 1){
            this.object.parentId = { label: res.data.deptName, value: res.data.deptId };
            if(this.object){
              this.buildForm(this.object);
            }
          }
          console.log(this.formAdd);
        })
        this.formAdd.get('deptCode').disable();
      });
    }

    
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      deptCode: ['', Validators.compose([Validators.required, Validators.maxLength(30)])],
      tenantId: ['', Validators.required],
      parentId: [null, Validators.required],
      deptName: [null, Validators.compose([Validators.required, Validators.maxLength(100)])],
      note: ['', Validators.maxLength(200)],
      createTime: [''],
      updateTime: ['']
    })
  }

  get f() {
    return this.formAdd.controls;
  }

  getDept(event){
    console.log(event.query);
    let filters = {
      deptName: event.query,
      rowStatus: "1L"
    }
    this.departmentService.searchDept(-1, -1, filters).subscribe(data => {
      this.parentDepts = [];
      //console.log("autocomplete", data);
      for (let i = 0; i < data.data.length; i++) {
        this.parentDepts.push({ label: data.data[i].deptName, value: data.data[i].deptId });
      }
    });
  }

  getTenantsList(){
    
    this.terantService.searchTerant(-1,-1).subscribe(data => {
      this.tenantList = []
      if(data.status === 1){
        this.tenantList.push({ label: this.translation.translate('common.label.cboSelect'), value: null });
        for (let i = 0; i < data.data.length; i++) {
          this.tenantList.push({label: data.data[i].tenantName, value: +data.data[i].tenantId });
        }
        console.log("tenantList",this.tenantList);
      }
    })
  }

  onSubmit(){
    console.log(this.formAdd);
    console.log(this.object);
    this.messageService.clear();

    let filters = {
      'deptCode-EXAC': this.formAdd.value.deptCode
    }
    if(this.formAdd.get('deptCode').valid && this.formAdd.get('deptName').valid 
      && this.formAdd.get('tenantId').valid && this.formAdd.get('parentId').valid){
      this.departmentService.searchDept(-1,-1, filters).subscribe(res => {
        console.log(res);
        if(res.status === 1){
          if(res.data && res.data.length > 0){
            if (this.isEdit) {
              this.saveObj();
            }else{
              if (res.data[0].rowStatus === 0) {
                this.id = res.data[0].deptId;
                this.saveObj();
              } else {
                this.messageService.add({ severity: 'error', summary: '', detail: 'Code existed' });
              }
            }
          }else {
            this.saveObj();
          }
        }
      })
    }
    else{
      if(!this.formAdd.get('deptCode').valid){
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Mã đơn vị không được để trống' });
      }
      if(!this.formAdd.get('deptName').valid){
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Tên đơn vị không được để trống' });
      }
      if(!this.formAdd.get('tenantId').valid){
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Quốc gia không được để trống' });
      }
      if(!this.formAdd.get('parentId').valid){
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Đơn vị cha không được để trống' });
      }
    }
    

  }

  saveObj() {
    let createTime: Date = new Date();
    let updateTime: Date;
    if (this.isEdit) {
      this.object = this.formAdd.value;
      this.object.deptId = this.id;
      if (this.object.createTime) {
        createTime = new Date(this.object.createTime);
      }
      updateTime = new Date();
      this.object.updateTime = updateTime;
      this.object.createTime = CommonUtils.stringToDate(this.object.createTime, 'yyyy/MM/dd hh:mm:ss.SSS');
      this.object.updateTime = CommonUtils.stringToDate(this.object.updateTime, 'yyyy/MM/dd hh:mm:ss.SSS');
      this.object.parentId = this.formAdd.value.parentId.value;
      this.object.rowStatus = "1"
    }else{
      this.object = this.formAdd.value;
      this.object = {
        ...this.object,
        createTime: createTime,
      }
      this.object.createTime = CommonUtils.stringToDate(this.object.createTime, 'yyyy/MM/dd hh:mm:ss.SSS');
      this.object.parentId = this.formAdd.value.parentId.value;
      this.object.rowStatus = "1"
    }

    console.log('item', this.object);
    this.departmentService.saveOrUpdateDept(this.object).subscribe(data => {
      console.log(data);
      let message = this.translation.translate('message.success.created.success');
      if (this.isEdit) {
        message = this.translation.translate('message.success.updated.success');
      }
      if (data.status === 1) {
        this.messageService.add({ severity: 'info', summary: '', detail: message });
      } else {
        this.messageService.add({ severity: 'error', summary: '', detail: 'Add fail' });
      }
    })
  }

  onClose() {
    this.tabLayoutService.close({
      component: 'CatDepartmentSaveComponent',
      header: "",
      breadcrumb: [
        { label: this.translation.translate('') },
        { label: this.translation.translate("") },
      ]
    })
  }


}
