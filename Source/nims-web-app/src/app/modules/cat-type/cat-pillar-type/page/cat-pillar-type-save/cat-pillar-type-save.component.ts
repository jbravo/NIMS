import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { ValidationService } from '@app/shared/services';
import { AppComponent } from '@app/app.component';
import { MessageService } from 'primeng/api';
import { TranslationService } from "angular-l10n";
import { PermissionService } from "@app/shared/services/permission.service";
import { CatPillarTypeServiceService } from '../../service/cat-pillar-type-service.service'
import { CatePillarType } from '@app/core/models/categoryType/cate-pillar-type.model';
import { TabLayoutService } from '@app/layouts/tab-layout';


@Component({
  selector: 'cat-pillar-type-save',
  templateUrl: './cat-pillar-type-save.component.html',
  styleUrls: ['./cat-pillar-type-save.component.css']
})
export class CatPillarTypeSaveComponent implements OnInit {

  formAdd: FormGroup;
  statusCheck = false;
  action: string;
  id: number;
  object: CatePillarType;
  isEdit: boolean = false;
  hide: boolean = true;

  constructor(
    private app: AppComponent,
    private translation: TranslationService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private pillarTypeService: CatPillarTypeServiceService,

  ) {
    this.buildForm({})
  }

  ngOnInit() {
    if (this.pillarTypeService.action == 'edit' || this.pillarTypeService.action == 'view') {
      this.isEdit = true;
      this.action = this.pillarTypeService.action;
      this.id = this.pillarTypeService.id;
      //this.formAdd.value.pillarTypeCode = this.pillarTypeService.id;
      this.pillarTypeService.findById(this.pillarTypeService.id).subscribe(res => {
        this.object = res.data;
        console.log(this.object);
        this.buildForm(this.object);
      });
    }

    if(this.pillarTypeService.action == 'view'){
      this.hide = false;
    }

    if (this.isEdit) {
      this.formAdd.get('pillarTypeCode').disable();
    }
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      pillarTypeCode: ['', Validators.compose([Validators.required, Validators.maxLength(30)])],
      height: ['', Validators.compose([Validators.required, ValidationService.positiveNumber])],
      note: ['', Validators.maxLength(200)]
    })
  }

  getFormValidationErrors(success: () => void) {
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'station')) {
      success();
    }
  }
  checkCode() {

  }

  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    let filters = {
      'pillarTypeCode-EXAC': this.formAdd.value.pillarTypeCode
    }
    if (this.formAdd.get('height').valid && this.formAdd.get('pillarTypeCode').valid && this.formAdd.get('note').valid) {
      this.pillarTypeService.searchPillar(-1,-1,filters).subscribe(res => {
        console.log("checkExit", res)
        if (res.status === 1) {
          if(res.data && res.data.length > 0){
            if(this.isEdit){
              this.saveObj();
            }else{
              if(res.data[0].rowStatus === 0){
                this.id = res.data[0].pillarTypeId;
                this.saveObj();
              }else{
                this.messageService.add({ severity: 'error', summary: '', detail: 'Code existed' });
              }
              
            }
            
          }else{
            this.saveObj();
          }
          //this.saveObj();
        }
      })

    } else {
      if (!this.formAdd.get('height').valid) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Height is required must be number' });
      }
      if (!this.formAdd.get('pillarTypeCode').valid) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Pillar type code is required' });
      }
      if (!this.formAdd.get('note').valid) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'note' });
      }
    }

  }
  saveObj() {
    let createTime: Date = new Date();
    let updateTime: Date;
    let item = {};
    if (this.object) {
      if (this.object.createTime) {
        createTime = new Date(this.object.createTime);
      }
      updateTime = new Date();
      item = {
        updateTime: updateTime.toISOString(),
      }
    }
    item = {
      ...item,
      pillarTypeId: this.id,
      height: this.formAdd.value.height,
      pillarTypeCode: this.formAdd.value.pillarTypeCode,
      note: this.formAdd.value.note,
      createTime: createTime.toISOString(),
      rowStatus: "1"
    }

    console.log('item', item);
    this.pillarTypeService.saveOrUpdatePillar(item).subscribe(data => {
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
      component: 'CatPillarTypeSaveComponent',
      header: "",
      breadcrumb: [
        { label: this.translation.translate('') },
        { label: this.translation.translate("") },
      ]
    })
  }

}
