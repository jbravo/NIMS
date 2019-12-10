import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {SuggestStationDataService} from '@app/modules/suggest-station/suggest-station-data.service';
import {YES_NO_CHOSSEN} from '@app/modules/suggest-station/suggest.constant';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';

@Component({
  selector: 'sector4g',
  templateUrl: './sector4g.component.html',
  styleUrls: ['./sector4g.component.scss']
})
export class Sector4gComponent implements OnInit {
  private listAntenType: any = [];
  private listDiplexer: any = [];
  @Input('indexSector') indexSector: any;

  constructor(private dataSerivce: SuggestStationDataService, private suggestionStationService: SuggestionStationService) {
  }

  form = new FormGroup({});
  model: any;
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'sectorCode',
          type: 'input',
          templateOptions: {
            label: 'Tên Sector',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'sectorCfg',
          type: 'input',
          templateOptions: {
            label: 'Cấu hình trạm',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'isJointSector',
          type: 'select',
          templateOptions: {
            label: 'Có ghép sector hay không?',
            placeholder: '--Lựa chọn--',
            options: YES_NO_CHOSSEN,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'numberSplitterPerSector',
          type: 'input',
          templateOptions: {
            label: 'Số splitter cần?',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'jointSectorCode',
          type: 'input',
          templateOptions: {
            label: 'Mã Sector ghép',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'isJointRru',
          type: 'select',
          templateOptions: {
            label: 'Có ghép RRU hay không?',
            placeholder: '--Lựa chọn--',
            options: YES_NO_CHOSSEN,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'numRruPerSector',
          type: 'input',
          templateOptions: {
            label: 'Số RRU/sector',
            required: true,
            maskString: '0*'
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'increaseCapacitySectorCode',
          type: 'input',
          templateOptions: {
            label: 'Sector tăng công suất',
            required: true,
            maxLength: 100,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'isJointAntena',
          type: 'select',
          templateOptions: {
            label: 'Có dùng chung anten hay không?',
            placeholder: '--Lựa chọn--',
            required: true,
            options: YES_NO_CHOSSEN
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'jointAntenaSectorCode',
          type: 'input',
          templateOptions: {
            label: 'Mã sector dùng chung anten ',
            maxLength: 100,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'diplexerType',
          type: 'select',
          templateOptions: {
            label: 'Loại diplexer',
            placeholder: '--Lựa chọn--',
            options: [],
          },
          hooks: {
            onInit: field => {
              this.suggestionStationService.getListStationTechType('DIPLEXER_TYPE').subscribe(res => {
                field.templateOptions.options = res.body.data.map(item => {
                  return {label: item.itemName, value: item.itemId};
                });
              });
            }
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'numDiplexerPerSector',
          type: 'input',
          templateOptions: {
            label: 'Số Diplexer sử dụng/sector',
            maskString: '0*',
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'antenaHeight',
          type: 'input',
          templateOptions: {
            label: 'Chiều cao anten so với chân cột',
            maskString: '0*',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'antenaHeightGround',
          type: 'input',
          templateOptions: {
            label: 'Chiều cao anten so với mặt đất',
            maskString: '0*',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'antenaTypeId',
          type: 'select',
          templateOptions: {
            label: 'Loại anten',
            placeholder: '--Lựa chọn--',
            options: [],
            required: true,
          },
          hooks: {
            onInit: field => {
              this.suggestionStationService.getListStationTechType('ANTEN_TYPE').subscribe(res => {
                field.templateOptions.options = res.body.data.map(item => {
                  return {label: item.itemName, value: item.itemId};
                });
              });
            }
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'rruHeight',
          type: 'input',
          templateOptions: {
            label: 'Khoảng cách anten đến đáy RRU ',
            maskString: '0*',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'lng',
          type: 'input',
          templateOptions: {
            label: 'Kinh độ',
            max: 180,
            min: -180,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'lat',
          type: 'input',
          templateOptions: {
            label: 'Vĩ độ',
            max: 90,
            min: -90,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'azimuth',
          type: 'input',
          templateOptions: {
            label: 'Góc azimuth ',
            min: 0,
            max: 360,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'tiltMechan',
          type: 'input',
          templateOptions: {
            label: 'Góc tilt cơ',
            max: 90,
            min: -90,
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'tiltElec',
          type: 'input',
          templateOptions: {
            label: 'Góc tilt điện',
            max: 90,
            min: -90,
            required: true,
          }
        },
      ]
    }
  ];

  ngOnInit() {
    this.model = this.dataSerivce.mainData.callOff4G.sectors[this.indexSector];
    this.getListDiplexer();
    // this.getListAntena();
  }

  getListDiplexer() {
    if (this.dataSerivce.listDiplexer.length === 0) {
      this.suggestionStationService.getListStationTechType('DIPLEXER_TYPE').subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataSerivce.listDiplexer.push({label: re.itemName, value: re.itemId});
          });
        }
      });
    }
  }

  getListAntena() {
    if (this.dataSerivce.listAntenType.length === 0) {
      this.dataSerivce.listAntenType.push({label: '--Lựa chọn--', value: null});
      this.suggestionStationService.getListStationTechType('ANTEN_TYPE').subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataSerivce.listAntenType.push({label: re.itemName, value: re.itemId});
          });
        }
      });
    }
  }
}
