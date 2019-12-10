import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { ValidationService } from '@app/shared/services';
import { AppComponent } from '@app/app.component';
import { MessageService } from 'primeng/api';
import { TranslationService } from "angular-l10n";
import { PermissionService } from "@app/shared/services/permission.service";
import { CatSleeveTypeServiceService } from '../../service/cat-sleeve-type-service.service'
import { CatSleeveType } from '@app/core/models/categoryType/cate-sleeve-type.model';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { SLEEVE_TYPE_TYPE } from '@app/shared/services/constants';

@Component({
  selector: 'cat-sleeve-type-save',
  templateUrl: './cat-sleeve-type-save.component.html',
  styleUrls: ['./cat-sleeve-type-save.component.css']
})
export class CatSleeveTypeSaveComponent implements OnInit {

  formAdd: FormGroup;
  statusCheck = false;
  action: string;
  id: number;
  object: CatSleeveType;
  isEdit: boolean = false;
  typeList: any = [];
  hide: boolean = true;

  constructor(
    private app: AppComponent,
    private translation: TranslationService,
    private permissionService: PermissionService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private dataCommon: DataCommonService,
    private sleeveTypeService: CatSleeveTypeServiceService,
  ) {
    this.buildForm({});
    this.typeList = this.dataCommon.getDropDownList(SLEEVE_TYPE_TYPE);
  }

  ngOnInit() {
    if (this.sleeveTypeService.action == 'edit' || this.sleeveTypeService.action == 'view') {
      this.isEdit = true;
      this.action = this.sleeveTypeService.action;
      this.id = this.sleeveTypeService.id;
      //this.formAdd.value.pillarTypeCode = this.pillarTypeService.id;
      this.sleeveTypeService.findById(this.sleeveTypeService.id).subscribe(res => {
        this.object = res.data;
        if(this.object){
          this.buildForm(this.object);
        }
        //this.object = object2;
      });
    }
    if(this.sleeveTypeService.action == 'view'){
      this.hide = false;
    }
    if (this.isEdit) {
      this.formAdd.get('sleeveTypeCode').disable();
    }
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      sleeveTypeCode: ['', Validators.compose([Validators.required, Validators.maxLength(30)])],
      capacity: ['', Validators.compose([Validators.required, ValidationService.positiveNumber])],
      type: [null, Validators.required],
      note: ['', Validators.maxLength(200)]
    })
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
      sleeveTypeId: this.id,
      sleeveTypeCode: this.formAdd.value.sleeveTypeCode,
      capacity: this.formAdd.value.capacity,
      note: this.formAdd.value.note,
      type: (this.formAdd.value.type),
      createTime: createTime.toISOString(),
      rowStatus: "1"
    }

    console.log('item', item);
    this.sleeveTypeService.saveOrUpdateSleeve(item).subscribe(data => {
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

  get f() {
    return this.formAdd.controls;
  }

  onSubmit() {
    console.log(this.formAdd);
    
    this.statusCheck = true;
    this.messageService.clear();
    let filters = {
      'sleeveTypeCode-EXAC': this.formAdd.value.sleeveTypeCode
    }
    if (this.formAdd.get('capacity').valid && this.formAdd.get('sleeveTypeCode').valid 
          && this.formAdd.get('note').valid && this.formAdd.get('type').valid
          && this.formAdd.value.type != null) {
      this.sleeveTypeService.searchSleeve(-1, -1, filters).subscribe(res => {
        console.log("checkExit", res)
        if (res.status === 1) {
          if (res.data && res.data.length > 0) {
            if (this.isEdit) {
              this.saveObj();
            } else {
              if (res.data[0].rowStatus === 0) {
                this.id = res.data[0].sleeveTypeId;
                this.saveObj();
              } else {
                this.messageService.add({ severity: 'error', summary: '', detail: 'Code existed' });
              }

            }

          } else {
            this.saveObj();
          }
        }
      })
    } else {
      console.log("else");
      
      if (!this.formAdd.get('capacity').valid) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'Capacity is required must be number' });
      }
      if (!this.formAdd.get('sleeveTypeCode').valid) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'sleeveTypeCode is required' });
      }
      if (!this.formAdd.get('note').valid) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'note' });
      }
      if (!this.formAdd.get('type').valid || !this.formAdd.value.type.value) {
        this.messageService.add({ severity: 'warn', summary: 'Warning:', detail: 'choose type' });
      }
    }

  }

  onClose() {
    this.tabLayoutService.close({
      component: 'CatSleeveTypeSaveComponent',
      header: "",
      breadcrumb: [
        { label: this.translation.translate('') },
        { label: this.translation.translate("") },
      ]
    })
  }

}
