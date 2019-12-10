import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';
import {SuggestStationDataService} from '@app/modules/suggest-station/suggest-station-data.service';
import {
  BANDWIDTH_TYPE, catDeviceTypeId, catItemid, PURPOSETYPE,
  PURTATIONTYPE,
  RADIOPILLARTYPE, STATIONPURPOSE,
  TRANINTERFACE,
  transCapacity,
  TRANSTYPE, YES_NO_CHOSSEN
} from '@app/modules/suggest-station/suggest.constant';

@Component({
  selector: 'calloff4-g',
  templateUrl: './calloff4-g.component.html',
  styleUrls: ['./calloff4-g.component.scss']
})
export class Calloff4GComponent implements OnInit {
  private listSTATION_PURPOSE: any[] = [];
  private listSTATION_TYPE: any = [];

  constructor(private suggestionStationService: SuggestionStationService, private dataService: SuggestStationDataService) {
  }

  @Input() form = new FormGroup({});
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
          }
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
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'expectedBroadcastDate',
          type: 'calendar',
          templateOptions: {
            readonly: false,
            label: 'Dự kiến ngày phát sóng',
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'cabinetSolutionType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Phân loại trạm theo giải pháp phủ sóng',
            options: this.dataService.listCabinetSolutionTye
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'btsStationType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Phân loại nhà trạm (Indoor/Outdoor)',
            options: PURTATIONTYPE,
            showClear: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'bandwidth',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Băng tần',
            options: BANDWIDTH_TYPE,
            showClear: true,
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
            showClear: true,
            required: true,
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
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioPillarType',
          type: 'select',
          templateOptions: {
            label: 'Loại cột',
            placeholder: '--Lựa chọn--',
            options: RADIOPILLARTYPE,
            showClear: true,
            readonly: true,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'radioPillarHeight',
          type: 'input',
          templateOptions: {
            label: 'Độ cao cột',
            showClear: true,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'transType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Loại truyền dẫn',
            placeholder: '--Lựa chọn--',
            options: TRANSTYPE,
            showClear: true,
            required: true,
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
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'transCapacity',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Dung lượng truyền dẫn ',
            options: transCapacity,
            showClear: true,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'cfgHardwave',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Cấu hình phân cứng',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'cfgSoftware',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Cấu hình phân mềm',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'cfg',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Cấu hình CE',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'stationPurpose',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Mục đích đặt trạm ',
            options: STATIONPURPOSE,
            showClear: true,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'isBuildingEdge',
          type: 'select',
          templateOptions: {
            placeholder: '--Lựa chọn--',
            readonly: false,
            label: 'Triển khai cột ở mép toàn nhà',
            options: YES_NO_CHOSSEN,
            showClear: true,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'designer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Người thiết kế',
          }
        },
      ]
    }
  ];

  ngOnInit() {
    this.model = this.dataService.mainData.callOff4G;
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
