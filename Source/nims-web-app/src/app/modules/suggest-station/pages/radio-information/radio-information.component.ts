import {Component, Input, OnInit} from '@angular/core';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {FormGroup} from '@angular/forms';
import {MessageService} from 'primeng/primeng';
import {SuggestionNewSiteModel} from '@app/modules/suggest-station/model/suggestion-new-site.model';
import {SuggestionStationModel} from '@app/modules/suggest-station/model/suggestion-station.model';
import {SuggestStationDataService} from '@app/modules/suggest-station/suggest-station-data.service';
import {SUGGEST_STATUS, SUGGEST_TYPES} from '@app/modules/suggest-station/suggest.constant';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';

@Component({
  selector: 'radio-information',
  templateUrl: './radio-information.component.html',
  styleUrls: ['./radio-information.component.scss'],
  styles: [`
      :host ::ng-deep button {
          margin-right: .25em;
      }

      :host ::ng-deep .custom-toast .ui-toast-message {
          color: #ffffff;
          background: #FC466B;
          background: -webkit-linear-gradient(to right, #3F5EFB, #FC466B);
          background: linear-gradient(to right, #3F5EFB, #FC466B);
      }

      :host ::ng-deep .custom-toast .ui-toast-close-icon {
          color: #ffffff;
      }
  `],
})
export class RadioInformationComponent implements OnInit {
  @Input() suggestionNewSiteDTOView: SuggestionNewSiteModel;
  form = new FormGroup({});
  radioInforModel: SuggestionNewSiteModel;
  inforModel: SuggestionStationModel = new SuggestionStationModel();
  fieldsInformation: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'suggestType',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Loại đề xuất',
            options: SUGGEST_TYPES,
            required: true,
          },
          defaultValue: 0,
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            },
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'suggestCode',
          type: 'input',
          templateOptions: {
            label: 'Mã đề xuất',
            readonly: true,
            required: true,
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'userName',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Người đề xuất',
          },
          hideExpression: 'model.checkView != true',
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            },
          },
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'updateTime',
          type: 'calendar',
          templateOptions: {
            readonly: false,
            label: 'Ngày đề xuất',
          },
          hideExpression: 'model.checkView != true',
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
          key: 'suggestStatus',
          type: 'select',
          templateOptions: {
            placeholder: '--Lựa chọn--',
            readonly: false,
            label: 'Trạng thái đề xuất',
            options: SUGGEST_STATUS
          },
          hideExpression: 'model.checkView != true',
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
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'stationCodeApproved',
          type: 'input',
          templateOptions: {
            readonly: true,
            label: 'Mã trạm',
            maxLength: 30,
          },
          
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'stationCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã nhà trạm đề xuất',
            maxLength: 30,
            required: true,
            change: (field, $event) => {
              const type = field.formControl.parent.get('suggestType').value;
              const stationCode = field.formControl.value;
              this.suggestService.getSuggestionCodeByTypeNameAnd(type, stationCode).subscribe(res => {
                field.formControl.parent.get('suggestCode').setValue(res.body.data);
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
          key: 'lng',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Kinh độ',
            min: -180,
            max: 180,
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
          key: 'lat',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Vĩ độ',
            min: -90,
            max: 90,
            required: true
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
          key: 'auditType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Phân loại kiểm định trạm gốc',
            placeholder: '--Lựa chọn--',
            required: true,
            options: [
              {label: '--Lựa chọn--', value: null},
              {label: 'Không phải làm gì', value: 0},
              {label: 'Phải kiểm định', value: 1},
              {label: 'Phải công bố', value: 2},
            ],
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
          key: 'locationId',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Địa bàn',
            placeholder: '--Lựa chọn--',
            required: true,
            options: [
              {label: '--Lựa chọn--', value: null},
              {label: 'Địa bàn 1', value: 0},
              {label: 'Địa bàn 2', value: 1},
              {label: 'Địa bàn 3', value: 2},
            ],
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
          key: 'address',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Địa chỉ',
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
          key: 'tenant',
          type: 'select',
          templateOptions: {
            readonly: false,
            placeholder: '--Lựa chọn--',
            label: 'Phân loại địa hình Phường/Xã theo mã địa bàn',
            options: [{label: 'Chưa xác định ', value: 1}, {label: '1111111', value: 2}]
          },
          expressionProperties: {
            'templateOptions.readonly': model => {
              if (model.checkView == true) {
                return true;
              }
            }
          },
          hideExpression: true
        },
        {
          className: 'col-12 col-md-12 col-sm-12 form-group',
          key: 'note',
          type: 'textareaT',
          templateOptions: {
            readonly: false,
            label: 'Ghi chú',
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

  constructor(private messageService: MessageService,
              private dataService: SuggestStationDataService,
              private suggestService: SuggestionStationService,
              private suggestionStationService: SuggestionStationService) {
  }

  addSingle() {
    this.messageService.add({severity: 'success', summary: 'Success Message', detail: 'Order submitted'});
    console.log('aaaaaaaaaaaa');
  }

  addMultiple() {
    this.messageService.addAll([{severity: 'error', summary: 'Service Message', detail: 'Via MessageService'}]);
  }

  ngOnInit() {
    this.inforModel = this.dataService.mainData.thongTinViTri.thongTinChung;
    this.radioInforModel = this.dataService.mainData.thongTinViTri.thongTinViTri;
    this.getlistStationTypeTech();
    this.getSPECIAL_AREA_TYPE();
  }

  getlistStationTypeTech() {
    if (this.dataService.list.length === 0) {
      this.suggestionStationService.getListStationTechTypeTech().subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataService.list.push({label: re.itemName, value: +re.itemCode});
          });
        }
      });
    }
  }

  getSPECIAL_AREA_TYPE() {
    if (this.dataService.listSPECIAL_AREA_TYPE.length === 0) {
      this.suggestionStationService.getListStationTechType('SPECIAL_AREA_TYPE').subscribe(res => {
        if (res.body.data.length > 0) {
          res.body.data.forEach(re => {
            this.dataService.listSPECIAL_AREA_TYPE.push({label: re.itemName, value: +re.itemId});
          });
        }
      });
    }
  }
}
