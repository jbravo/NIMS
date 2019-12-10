import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { RadioSuggestionInforModel } from '@app/modules/suggest-station/model/radioSuggestionInfor.model';
import { SuggestionStationService } from '@app/modules/suggest-station/suggestion-station.service';
import { SuggestStationDataService } from '@app/modules/suggest-station/suggest-station-data.service';
import {
  concaveCode,
  radiolocationType,
  RADIOPILLARTYPE,
  stationSolutionType,
  YES_NO_CHOSSEN
} from '@app/modules/suggest-station/suggest.constant';

@Component({
  selector: 'radio-suggestion-infor',
  templateUrl: './radio-suggestion-infor.component.html',
  styleUrls: ['./radio-suggestion-infor.component.scss']
})
export class RadioSuggestionInforComponent implements OnInit {
  @Output() listType = new EventEmitter();

  constructor(private suggestionStationService: SuggestionStationService, private dataService: SuggestStationDataService) {
  }

  form = new FormGroup({});
  model: any = {};
  radioSuggestionInforModel: RadioSuggestionInforModel;
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'stationTechType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Phân loại trạm theo công nghệ triển khai',
            options: this.dataService.list,
            required: true,
          },
          hooks: {
            onInit: field => {
              field.formControl.valueChanges.subscribe(value => {
                this.listType.emit(value);
              });
            },
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.save) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'stationSolutionType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Phân loại trạm theo giải pháp phủ sóng',
            options: stationSolutionType,
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioLocationType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Phân loại địa hình theo vô tuyến',
            options: radiolocationType,
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioPillarType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Loại cột',
            options: RADIOPILLARTYPE,
            required: true,
          },
          hooks: {
            onInit: (field) => {
              field.formControl.valueChanges.subscribe(value => {
                console.log('change DCC');
                if(this.dataService.mainData.callOff2G){
                  this.dataService.mainData.callOff2G.radioPillarType = value;
                }
                if(this.dataService.mainData.callOff3G){
                  this.dataService.mainData.callOff3G.radioPillarType = value;
                }
                if (this.dataService.mainData.callOff4G) {
                  this.dataService.mainData.callOff4G.radioPillarType = value;
                }
              });
            }
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioPillarHeight',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Độ cao cột',
            showClear: true,
            required: true,
            maskString: '0*',
          },
          hooks: {
            onInit: (field) => {
              field.formControl.valueChanges.subscribe(value => {
                console.log('change DCC');
                if(this.dataService.mainData.callOff2G){
                  this.dataService.mainData.callOff2G.radioPillarHeight = value;
                }
                if(this.dataService.mainData.callOff3G){
                  this.dataService.mainData.callOff3G.radioPillarHeight = value;
                }
                if (this.dataService.mainData.callOff4G) {
                  this.dataService.mainData.callOff4G.radioPillarHeight = value;
                }
              });
            }
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'hasConcavePoint',
          type: 'select',
          defaultValue: null,
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Có xử lý vùng lõm không?',
            options: YES_NO_CHOSSEN,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'concaveCode',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Mã vùng lõm',
            options: concaveCode,
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.hasConcavePoint != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'heightWithConcavePoint',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Độ cao tương đối của vị trí đặt trạm so với vùng lõm (m)',
            showClear: true,
            max: 1000000,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'concavePointPopulation2g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm phủ được 2G',
            showClear: true,
            maskString: '0*',
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.hasConcavePoint != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'concavePointPopulation3gVoice',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm phủ được 3G thoại',
            showClear: true,
            maskString: '0*',
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.hasConcavePoint != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'concavePointPopulation3gData',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm phủ được 3G data',
            showClear: true,
            maskString: '0*',
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.hasConcavePoint != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'concavePointPopulation4g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm phủ được 4G',
            showClear: true,
            maskString: '0*',
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.hasConcavePoint != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'specialArea',
          type: 'select',
          defaultValue: null,
          templateOptions: {
            readonly: false,
            label: 'Khu vực đặc biệt hay không?',
            placeholder: '--Lựa chọn--',
            options: YES_NO_CHOSSEN,
            showClear: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'specialAreaType',
          type: 'select',
          templateOptions: {
            placeholder: '--Lựa chọn--',
            readonly: false,
            label: 'Loại khu vực đặc biệt',
            required: true,
            showClear: true,
            options: this.dataService.listSPECIAL_AREA_TYPE,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.specialArea != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'specialAreaPopulation2g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm lượng hóa tại khu vực đặc biệt 2G',
            showClear: true,
            maxLength: 10,
            max: 1000000,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.specialArea != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'specialAreaPopulation3gVoice',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm lượng hóa tại khu vực đặc biệt 3G Voice',
            showClear: true,
            maxLength: 10,
            max: 1000000,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.specialArea != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'specialAreaPopulation3gData',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm lượng hóa tại khu vực đặc biệt 3G Data',
            showClear: true,
            maxLength: 10,
            max: 1000000,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.specialArea != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'specialAreaPopulation4g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số dân lõm lượng hóa tại khu vực đặc biệt 4G',
            showClear: true,
            maxLength: 10,
            max: 1000000,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: 'model.specialArea != 1'
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'avgDbm2g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mức thu trung bình Outdoor/Indoor 2G (dBm)',
            showClear: true,
            maxLength: 20,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'avgDbm3g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mức thu trung bình Outdoor/Indoor 3G (dBm)',
            showClear: true,
            maxLength: 20,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'avgDbm4g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mức thu trung bình Outdoor/Indoor 4G (dBm)',
            showClear: true,
            maxLength: 20,
            maskString: '0*',
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'avgDbmCompetitor2g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mức thu trung bình của đối thủ mạnh nhất 2G (dBm)',
            showClear: true,
            max: 1000,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        }, {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'avgDbmCompetitor3g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mức thu trung bình của đối thủ mạnh nhất 3G (dBm)',
            showClear: true,
            max: 1000,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        }, {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'avgDbmCompetitor4g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mức thu trung bình của đối thủ mạnh nhất 4G (dBm)',
            showClear: true,
            max: 1000,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'compareWithCompetitor2g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'So sánh với đối thủ 2G',
            maxLength: 500,
            showClear: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        }, {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'compareWithCompetitor3g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'So sánh với đối thủ 3G',
            maxLength: 500,
            showClear: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        }, {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'compareWithCompetitor4g',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'So sánh với đối thủ 4G',
            maxLength: 500,
            showClear: true,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
        {
          className: 'col-12 col-md-12 col-sm-12 form-group',
          key: 'suggestReason',
          type: 'textareaT',
          templateOptions: {
            readonly: false,
            label: 'Nguyên nhân đề xuất',
            maxLength: 500,
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          }
        },
      ]
    },
  ];

  ngOnInit() {
    this.radioSuggestionInforModel = this.dataService.mainData.thongTinDeXuatVoTuyen;
  }

  getlistStationTypeTech() {
    this.suggestionStationService.getListStationTechTypeTech().subscribe(res => {
      if (res.body.data.length > 0) {
        res.body.data.forEach(re => {
          this.dataService.list.push({ label: re.itemName, value: +re.itemCode });
        });
        console.log(this.dataService.list);
      }
    });
  }

  getSPECIAL_AREA_TYPE() {
    this.suggestionStationService.getListStationTechType('SPECIAL_AREA_TYPE').subscribe(res => {
      if (res.body.data.length > 0) {
        res.body.data.forEach(re => {
          this.dataService.listSPECIAL_AREA_TYPE.push({ label: re.itemName, value: +re.itemId });
        });
      }
    });
  }

  saveData() {

  }
}
