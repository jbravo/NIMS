import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {DataCommonService} from "@app/shared/services/data-common.service";
import {
  CLASS_CHECKBOX, CONFIG_TYPE, MAP_TYPE, OBJECT_CHECKBOX, OBJECT_OUTDOOR_CHECKBOX,
  ZOOM_TYPE
} from "@app/shared/services/constants";
import {DialogService, MessageService} from "primeng/api";
import {ObjectAddComponent} from "@app/modules/map/config-map/object-add/object-add.component";
import {ConfigMapService} from "@app/modules/map/config-map/config-map.service";
import {CommonParam} from "@app/core/app-common-param";

@Component({
  selector: 'config-map',
  templateUrl: './config-map.component.html',
  styleUrls: ['./config-map.component.css']
})
export class ConfigMapComponent implements OnInit {

  @Input() data;
  formConfig: FormGroup;
  configTypes: any[] = [];
  zoomTypes: any[] = [];
  mapTypes: any[] = [];
  commonConfig: string;
  listObjectCheckBoxes : any[] = [];
  listClassCheckBoxes : any[] = [];
  listObjOutdoorCheckBoxes : any[] = [];
  listCommon : any[] = [];
  groupObjects;
  selectedRecord: any[];
  selectedNames: string[] = [];

  constructor(private fb: FormBuilder,
              private dataCommon: DataCommonService,
              private dialogService: DialogService,
              private messageService: MessageService,
              private configMapService : ConfigMapService,
              private commonParam: CommonParam) {
    this.buildForm();
  }

  ngOnInit() {
    // list loai cau hinh
    this.configTypes = this.dataCommon.getDropDownList(CONFIG_TYPE);
    //list muc zoom
    this.zoomTypes = this.dataCommon.getDropDownList(ZOOM_TYPE);
    // list kieu ban do
    this.mapTypes = this.dataCommon.getDropDownList(MAP_TYPE);

    this.listObjectCheckBoxes = OBJECT_CHECKBOX;
    this.listClassCheckBoxes = CLASS_CHECKBOX;
    this.listObjOutdoorCheckBoxes = OBJECT_OUTDOOR_CHECKBOX;
    this.listCommon = this.listObjectCheckBoxes.concat(this.listClassCheckBoxes,this.listObjOutdoorCheckBoxes);
    // lay thong tin cau hinh ban do
    this.configMapService.getInfoConfigMap(5).subscribe(res =>{
      let object:any = res.content.mapConfig;
      console.log(object);
      if(object){
        for(let i = 0 ; i < object.length; i++){
          if(object[i].isShow == 1){
            this.selectedNames.push(object[i].id.objectCode);
          }
        }
      }
      this.formConfig.reset(res.content);
    });
  }

  buildForm(){
    this.formConfig = this.fb.group({
      cfgMapUserId: [null],
      lat: [null],
      lng: [null],
      zoom: [null],
      mapType: [null],
      mapConfig: [null]
    });
  }

  changeSelected(event){
    if(event.value.value == 0){
      this.commonConfig = 'common';
    }else if(event.value.value == 1){
      this.commonConfig = 'object';
    } else {
      this.commonConfig = '';
    }

  }

  get f() {
    return this.formConfig.controls;
  }
  showDiaLog(){
    const ref = this.dialogService.open(ObjectAddComponent, {
      width: '50%',
      contentStyle: {"overflow": "auto"}
    });

    ref.onClose.subscribe(res =>{
      console.log(res);
    });
  }
  save(){
    let listItem = [];
    let listObj = [];
    console.log(this.selectedNames);
    for(let i = 0; i < this.listCommon.length; i++){
      listObj.push(this.listCommon[i].name);
    }
    if(this.selectedNames.length > 0){
      for(let i = 0; i < this.selectedNames.length; i++){
        if(listObj.indexOf(this.selectedNames[i]) !== -1){
          listObj.splice(listObj.indexOf(this.selectedNames[i]), 1);
        }
         listItem.push({property : this.selectedNames[i],isShow:1});
      }
    }
    if(listObj.length > 0){
      for(let i = 0 ; i < listObj.length; i++){
        listItem.push({property : listObj[i], isShow:0});
      }
    }
     let data : any = this.formConfig.value;
     data.mapConfig = listItem;
     this.configMapService.saveConfigCommon(data).subscribe(res => {
       console.log(res);
     });
  }

  closePopup(){
    this.commonParam.closePopupLeft();
  }
}
