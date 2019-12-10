import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';
import {SuggestStationDataService} from '@app/modules/suggest-station/suggest-station-data.service';
import {
  BANDWIDTH_TYPE,
  catDeviceTypeId,
  catItemid,
  PURPOSETYPE,
  PURTATIONTYPE, RADIOPILLARTYPE, STATIONPURPOSE,
  TRANINTERFACE, transCapacity,
  TRANSTYPE, YES_NO_CHOSSEN
} from '@app/modules/suggest-station/suggest.constant';

@Component({
  selector: 'calloff3-g',
  templateUrl: './calloff3-g.component.html',
  styleUrls: ['./calloff3-g.component.scss']
})
export class Calloff3GComponent implements OnInit {
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
            showClear: true,
            maxLength: 100,
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
            label: 'Dự kiến ngày phát sóng',
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
            label: 'Phân loại nhà trạm (Indoor/Outdoor)',
            placeholder: '--Lựa chọn--',
            options: PURTATIONTYPE,
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
          key: 'bandwidth',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Băng tần',
            placeholder: '--Lựa chọn--',
            options: BANDWIDTH_TYPE,
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
          key: 'itemId',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Tên thiết bị',
            options: catItemid,
            showClear: true,
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
          key: 'purposeType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Loại trạm(VP/LL)',
            placeholder: '--Lựa chọn--',
            options: PURPOSETYPE,
            showClear: true,
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
            readonly: true,
            placeholder: '--Lựa chọn--',
            label: 'Loại cột',
            options: RADIOPILLARTYPE,
            showClear: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioPillarHeight',
          type: 'input',
          templateOptions: {
            readonly: true,
            label: 'Độ cao cột',
            showClear: true,
            maskString: '0*',
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'transType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Loại truyền dẫn',
            options: TRANSTYPE,
            showClear: true,
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
          key: 'transInterface',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Giao diện truyền dẫn',
            options: TRANINTERFACE,
            showClear: true,
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
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Dung lượng truyền dẫn ',
            placeholder: '--Lựa chọn--',
            options: transCapacity,
            showClear: true,
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
            label: 'Cấu hình CE',
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
          key: 'stationPurpose',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Mục đích đặt trạm',
            options: STATIONPURPOSE,
            showClear: true,
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
            label: 'Triển khai cột ở các mep tòa nhà ',
            placeholder: '--Lựa chọn--',
            options: YES_NO_CHOSSEN,
            showClear: true,
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
      ]
    }
  ];

  ngOnInit() {
    this.model = this.dataService.mainData.callOff3G;
    this.getListstationType();
  }

  getListstationType() {
    if (this.dataService.listSTATION_TYPE.length === 0) {
      this.dataService.listSTATION_TYPE.push({label: '--Lựa chọn--', value: null});
      this.suggestionStationService.getListStationTechType('STATION_TYPE').subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataService.listSTATION_TYPE.push({label: re.itemName, value: re.itemId});
          });
        }
      });
    }
  }
}
