import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {SuggestStationDataService} from '@app/modules/suggest-station/suggest-station-data.service';
import {YES_NO_CHOSSEN} from '@app/modules/suggest-station/suggest.constant';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';

@Component({
  selector: 'sector',
  templateUrl: './sector.component.html',
  styleUrls: ['./sector.component.scss']
})
export class SectorComponent implements OnInit {
  private listDiplexer: any = [];
  private listAntenType: any = [];

  @Input('indexSector') indexSector: any;
  private listAntena: any = [];

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
            readonly: false,
            label: 'Tên Sector',
            maskString: '0*',
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
          key: 'sectorCfg',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Cấu hình Sector',
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
          key: 'isJointSector',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Có ghép sector hay không?',
            placeholder: '--Lựa chọn--',
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
          key: 'numberSplitterPerSector',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số splitter cần/sector?',
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
          key: 'jointSectorCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã Sector ghép',
            maxLength: 100,
            pattern: /[a_zA_Z0-9]/,
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
          key: 'isJointRru',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Có ghép RRU hay không?',
            placeholder: '--Lựa chọn--',
            required: true,
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
          key: 'numRruPerSector',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số RRU/sector',
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
          key: 'increaseCapacitySectorCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Sector tăng công suất',
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
          key: 'isJointAntena',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Có dùng chung anten hay không?',
            placeholder: '--Lựa chọn--',
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
          key: 'jointAntenaSectorCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã sector dùng chung anten ',
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
          key: 'diplexerType',
          type: 'select',
          templateOptions: {
            readonly: false,
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
          key: 'numDiplexerPerSector',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Số Diplexer sử dụng/sector',
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
          key: 'antenaHeight',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Chiều cao anten so với chân cột',
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
          key: 'antenaHeightGround',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Chiều cao anten so với mặt đất',
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
          key: 'antenaTypeId',
          type: 'select',
          templateOptions: {
            readonly: false,
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
          key: 'rruHeight',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Khoảng cách anten đến đáy RRU ',
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
          key: 'lng',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Kinh độ',
            required: true,
            max: 180,
            min: -180,
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
          key: 'lat',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Vĩ độ',
            max: 90,
            min: -90,
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
          key: 'azimuth',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Góc azimuth ',
            min: 0,
            max: 360,
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
          key: 'tiltMechan',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Góc tilt cơ',
            max: 90,
            min: -90,
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
          key: 'tiltElec',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Góc tilt điện',
            max: 90,
            min: -90,
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
      ]
    }
  ];

  ngOnInit() {
    this.model = this.dataSerivce.mainData.callOff2G.sectors[this.indexSector];
    this.getListAntena();
    this.getListDiplexer();
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

  getListDiplexer() {
    if (this.dataSerivce.listDiplexer.length === 0) {
      this.dataSerivce.listDiplexer.push({label: '--Lựa chọn--', value: null});
      this.suggestionStationService.getListStationTechType('DIPLEXER_TYPE').subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataSerivce.listDiplexer.push({label: re.itemName, value: re.itemId});
          });
        }
      });
    }
  }
}
