import {Component, Input, OnInit} from '@angular/core';
import {CommonUtils} from "@app/shared/services";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {InfraCablesService} from "@app/modules/map/cables/cables-management/service/infra-cables.service";
import {DataCommonService} from "@app/shared/services/data-common.service";
import {CABLE_STATUS} from "@app/shared/services/constants";
import {AppComponent} from "@app/app.component";
import {StationService} from "@app/modules/stations-management/service/station.service";
import {MessageService} from "primeng/api";
import {PermissionService} from "@app/shared/services/permission.service";
import {TranslationService} from "angular-l10n";
import {MapInfraCableService} from "@app/core/services/map/map-infraCable.service";

@Component({
  selector: 'cables-management',
  templateUrl: './cables-management.component.html',
  styleUrls: ['./cables-management.component.css']
})
export class CablesManagementComponent implements OnInit {

  @Input() data;
  formAdd: FormGroup;
  result: any;
  statusList;
  cableTypeList;
  action;
  // demo test
  dataGeo;

  constructor(private app: AppComponent,
              private fb: FormBuilder,
              private cablesService: InfraCablesService,
              private dataCommon: DataCommonService,
              private messageService: MessageService,
              private permissionService: PermissionService,
              private translation: TranslationService,
              private infraCableService: MapInfraCableService) {
    this.buildForm();
  }

  ngOnInit() {
    this.onChange(this.data);
    // list trang thai
    this.statusList = this.dataCommon.getDropDownList(CABLE_STATUS);
    this.getCableTypeList();
    // this.buildForm(this.data);
  }

  onChange(data: any) {
    debugger;
    console.log(data);
    this.action = data.action;
    // demo test start
    this.dataGeo = {
      cableId: null,
      cableCode:null,
      deptId:null,
      cableTypeId:null,
      status: null,
      lngLats:[]

    };
    this.dataGeo.status = data.action;
    // demo test end
    if (data.action == 'edit' || data.action == 'view') {
      this.data = data.data;
      this.cablesService.findCableById(this.data.cable_id || this.data.properties.cable_id).subscribe(res => {
        if (res.content != undefined) {
          res.content.installationDate = CommonUtils.stringToDate(
            res.content.installationDate,
            'yyyy/MM/dd'
          );
          this.formAdd.reset(res.content);
        } else {
          this.formAdd.reset(data);
        }
      });
    } else {
      this.data = data.data;
      this.formAdd.reset(data.data);
      this.permissionService.getNumberOfCable(this.formAdd.value).subscribe(res => {
        res = res + 1;
        let index = '';
        let cableCode = data.data.sourceCode + '-' + data.data.destCode;
        if (res > 0 && res <= 9) {
          index = '0' + res;
        }
        if (res > 0 && res <= 9) {
          index = '0' + res;
        }
        if (res > 9 && res <= 99) {
          index = res;
        }
        if (index != '') {
          cableCode = cableCode + '_' + index;
        }
        this.formAdd.controls['index'].setValue(index);
        this.formAdd.controls['cableCode'].setValue(cableCode);
      });
    }
    data.data.cableId = null;
    // if(data.cableId != null){
    //   console.log('test',data);
    // }else{
    //   this.buildForm(data);
    // }
  }

  getFormValidationErrors(success: () => void){
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'cable')){
      success();
    }
  }

  buildForm() {
    this.formAdd = this.fb.group({
      cableId: [null],
      sourceId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.source_id')])],
      destId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.dest_id')])],
      sourceCode: [null],
      destCode: [null],
      index: [null],
      cableCode: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.cable_code')])],
      constructionCode: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.construction_code')])],
      cableTypeId: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.cable_type_code')])],
      length: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.length')])],
      status: [null, Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('cable.status')])],
      installationDate: [null],
      note: [null]
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  // lay danh sach loai cap
  getCableTypeList() {
    this.permissionService.getCableTypeList().subscribe(res => {
      this.cableTypeList = [];
      // truong hop them moi
      this.cableTypeList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
      for (let i = 0; i < res.length; i++) {
        this.cableTypeList.push({label: res[i].note, value: res[i].cableTypeId});
      }

      console.log(this.cableTypeList);
    });

  }

  onSubmit() {
    this.messageService.clear();
    this.getFormValidationErrors(() => {
      this.cablesService.saveCables(this.formAdd.value).subscribe(res => {
        if (res.status === '200') {
          // test demo start
          debugger;
          this.dataGeo.cableId = res.content.cableId;
          this.dataGeo.cableCode = res.content.cableCode;
          this.dataGeo.deptId = res.content.deptId;
          this.dataGeo.cableTypeId = res.content.cableTypeId;
          this.dataGeo.lngLats.push(this.data.dest);
          this.dataGeo.lngLats.push(this.data.source);
          this.infraCableService.saveOrUpdate(this.dataGeo).subscribe(res => {

          });
          // test demo end

          // this.app.messageProcess(res.status, res.content);
          this.messageService.add({
            key: 'cables',
            severity: 'success',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.action === 'edit' ? this.translation.translate('cable.update.success') : this.translation.translate('cable.add.success'),
          });
          // this.onClosed();
        }
        if (res.status === '404' || res.status === '500' || res.status === '400') {
          // this.app.messageProcess(res.status, res.content);
        }
      }, error => {
        console.log(error);
      });
    });

  }

}
