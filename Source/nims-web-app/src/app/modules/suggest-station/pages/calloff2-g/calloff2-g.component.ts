import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { SuggestionCallOff2gDTOModel } from '@app/modules/suggest-station/model/Call-off/SuggestionCallOff2gDTO.model';
import { SuggestStationDataService } from '@app/modules/suggest-station/suggest-station-data.service';
import { BANDWIDTH_TYPE, catDeviceTypeId, catItemid, PURPOSETYPE, PURTATIONTYPE, RADIOPILLARTYPE, STATIONPURPOSE, TRANINTERFACE, TRANSTYPE, YES_NO_CHOSSEN } from '@app/modules/suggest-station/suggest.constant';
import { SuggestionStationService } from '@app/modules/suggest-station/suggestion-station.service';
import { FormlyFieldConfig } from '@ngx-formly/core';

@Component({
  selector: 'calloff2-g',
  templateUrl: './calloff2-g.component.html',
  styleUrls: ['./calloff2-g.component.scss']
})
export class Calloff2GComponent implements OnInit {
  suggestionCallOff2g: SuggestionCallOff2gDTOModel;
  constructor(private suggestionStationService: SuggestionStationService, private dataService: SuggestStationDataService) {
  }

  form = new FormGroup({});
  model: any;
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'cabinetCode',
          type: 'input',
          templateOptions: {
            readonly: true,
            label: 'Mã trạm',
            maxLength: 100
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'cabinetCodeSuggest',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã trạm đề xuất',
            maxLength: 100,
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
          key: 'expectedBroadcastDate',
          type: 'calendar',
          templateOptions: {
            readonly: false,
            label: 'Ngày dự kiến phát sóng',
            maxLength: 100,
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
          key: 'cabinetSolutionType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Phân loại trạm theo giải pháp phủ sóng',
            placeholder: '--Lựa chọn--',
            options: this.dataService.listCabinetSolutionTye,
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
          key: 'btsStationType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Loại nhà trạm (Indoor/Outdoor)',
            placeholder: '--Lựa chọn--',
            options: PURTATIONTYPE,
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
          key: 'bandwidth',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Băng tần',
            placeholder: '--Lựa chọn--',
            options: BANDWIDTH_TYPE,
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
          key: 'itemId',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Tên thiết bị',
            options: catItemid,
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
          key: 'deviceTypeId',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Loại thiết bị (Tập trung/Phân tán)',
            options: catDeviceTypeId,
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
          key: 'purposeType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Loại trạm',
            placeholder: '--Lựa chọn--',
            options: PURPOSETYPE,
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
          defaultValue: null,
          templateOptions: {
            readonly: true,
            label: 'Loại cột',
            placeholder: '--Lựa chọn--',
            options: RADIOPILLARTYPE,
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioPillarHeight',
          type: 'input',
          templateOptions: {
            readonly: true,
            label: 'Độ cao cột',
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'transType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Loại truyền dẫn',
            placeholder: '--Lựa chọn--',
            required: true,
            options: TRANSTYPE,
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
          key: 'transInterface',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Giao diện truyền dẫn',
            placeholder: '--Lựa chọn--',
            options: TRANINTERFACE,
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
          key: 'transCapacity',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Dung lượng truyền dẫn',
            maskString: '0*',
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
          key: 'cfg',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Cấu hình',
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
          key: 'stationPurpose',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Mục đích đặt trạm',
            placeholder: '--Lựa chọn--',
            options: STATIONPURPOSE,
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
          key: 'isBuildingEdge',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Triển khai cột ở các mép tòa nhà',
            options: YES_NO_CHOSSEN,
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
          key: 'designer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Người thiết kế',
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
    }
  ];

  getListCabinetSolutionTye() {
    if (this.dataService.listCabinetSolutionTye.length === 0) {
      this.dataService.listCabinetSolutionTye.push({label: '--Lựa chọn--', value: null});
      this.suggestionStationService.getListStationTechType('STATION_TYPE ').subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataService.listCabinetSolutionTye.push({label: re.itemName, value: re.itemId});
          });
        }
      });
    }
  }


  ngOnInit() {
    this.suggestionCallOff2g = this.dataService.mainData.callOff2G;
    console.log(this.suggestionCallOff2g);
    this.getListCabinetSolutionTye();

  }
}
