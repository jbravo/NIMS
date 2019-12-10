import {Component, Input, OnInit} from '@angular/core';
import {CommonUtils} from "@app/shared/services";
import {FormGroup} from "@angular/forms";
import {InfraCablesService} from "@app/modules/map/cables/cables-management/service/infra-cables.service";
import {DataCommonService} from "@app/shared/services/data-common.service";
import {CABLE_STATUS} from "@app/shared/services/constants";
import {AppComponent} from "@app/app.component";
import {StationService} from "@app/modules/stations-management/service/station.service";
import {MessageService} from "primeng/api";
import {PermissionService} from "@app/shared/services/permission.service";
import {TranslationService} from "angular-l10n";
import {CommonParam} from "@app/core/app-common-param";

@Component({
  selector: 'cable-detail',
  templateUrl: './cable-detail.component.html',
  styleUrls: ['./cable-detail.component.css']
})
export class CableDetailComponent implements OnInit {

  @Input() data;
  formGroup: FormGroup;
  statusList;
  cable_id;

  constructor(private cablesService: InfraCablesService,
              private dataCommon: DataCommonService,
              private permissionService: PermissionService,
              private commonParam: CommonParam) {
    this.buildForm({});
  }

  ngOnInit() {
    this.onChange(this.data);
    // list trang thai
    this.statusList = this.dataCommon.getDropDownList(CABLE_STATUS);
    // this.getCableTypeList();
    // this.buildForm(this.data);
  }

  onChange(data: any) {
    console.log(data);
    this.cablesService.findCableById(data.data.properties.cable_id || data.id).subscribe(res => {
      if (res.content != undefined) {
        this.cable_id = res.content.cableId;
        res.content.installationDate = CommonUtils.dateToString(res.content.installationDate);
        this.buildForm(res.content);
      } else {
        this.buildForm({});
        this.formGroup.controls['cableCode'].setValue(data.data.properties.cable_code);
      }
    });
  }

  buildForm(formData: any) {
    this.formGroup = CommonUtils.createForm(formData, {
      cableId: [null],
      sourceId: [null],
      destId: [null],
      sourceCode: [null],
      destCode: [null],
      index: [null],
      cableCode: [null],
      constructionCode: [null],
      cableTypeId: [null],
      length: [null],
      status: [null],
      installationDate: [null],
      note: [null],
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  clickItem() {
    debugger;
    let data = {cable_id: this.cable_id} ;
    let item = {
      action: 'edit',
      data: data
    };
    this.commonParam.showProperties('cableEdit', item);
    this.commonParam.onChange(false);
  }

  // lay danh sach loai cap
  // getCableTypeList() {
  //   this.permissionService.getCableTypeList().subscribe(res => {
  //     this.cableTypeList = [];
  //     // truong hop them moi
  //     this.cableTypeList.push({label: this.translation.translate('common.label.cboSelect'), value: null});
  //     for (let i = 0; i < res.length; i++) {
  //       this.cableTypeList.push({label: res[i].note, value: res[i].cableTypeId});
  //     }
  //
  //     console.log(this.cableTypeList);
  //   });
  //
  // }


}
