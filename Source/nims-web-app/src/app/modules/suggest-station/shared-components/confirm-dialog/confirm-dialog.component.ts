import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { SuggestionStationService } from '../../suggestion-station.service';

const STATUS_WITH_CALLOFF = [0, 3];

@Component({
  selector: 'confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

  data;
  form = new FormGroup({});
  model: any = {};
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 form-group',
          key: 'trangThaiPheDuyet',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Trạng thái',
            options: [
              // { label: '-- Lựa chọn --', value: null },
              { label: 'Đồng ý', value: 1 },
              { label: 'Không đồng ý', value: 0 }
            ],
            required: true
          },
          defaultValue: 1
        },
        {
          className: 'col-12 form-group',
          key: 'cabinetCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã trạm',
            required: true,
          },
          expressionProperties: {
            'templateOptions.readonly': (model: any, formState: any, field: FormlyFieldConfig) => {
              if (!this.data.suggestStation.suggestionNewSiteDTO) {
                return false;
              }
              if (this.data.suggestStation.suggestionNewSiteDTO.stationCodeApproved) {
                return true;
              }
            },
          },
          hooks: {
            onInit: (field) => {
              if (this.data.suggestStation.suggestionNewSiteDTO && this.data.suggestStation.suggestionNewSiteDTO.stationCodeApproved) {
                field.formControl.setValue(this.data.suggestStation.suggestionNewSiteDTO.stationCodeApproved);
              }
            }
          },
          hideExpression: (model) => {
            if (STATUS_WITH_CALLOFF.indexOf(this.data.suggestStation.suggestStatus) === -1) {
              return true;
            }
          }
        },
        {
          className: 'col-12 form-group',
          key: 'calloff2gCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã trạm 2G',
            required: true,
          },
          hideExpression: (model) => {
            if (!this.data.suggestStation.suggestionCallOff2gDTOList) {
              return true
            }
            if (this.data.suggestStation.suggestionCallOff2gDTOList.length < 1 || model.trangThaiPheDuyet === 0) {
              return true;
            }
            if (STATUS_WITH_CALLOFF.indexOf(this.data.suggestStation.suggestStatus) === -1) {
              return true;
            }
          },
          expressionProperties: {
            'templateOptions.readonly': (model: any, formState: any, field: FormlyFieldConfig) => {
              if (!this.data.suggestStation.suggestionCallOff2gDTOList) {
                return false;
              }
              if (this.data.suggestStation.suggestionCallOff2gDTOList[0].cabinetCode) {
                return true;
              }
            },
          },
          hooks: {
            onInit: (field) => {
              if (this.data.suggestStation.suggestionCallOff2gDTOList && this.data.suggestStation.suggestionCallOff2gDTOList[0].cabinetCode) {
                field.formControl.setValue(this.data.suggestStation.suggestionCallOff2gDTOList[0].cabinetCode);
              }
            }
          }
        },
        {
          className: 'col-12 form-group',
          key: 'calloff3gCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã trạm 3G',
            required: true,
          },
          hideExpression: (model) => {
            if (!this.data.suggestStation.suggestionCallOff3gDTOList) {
              return true
            }
            if (this.data.suggestStation.suggestionCallOff3gDTOList.length < 1 || model.trangThaiPheDuyet === 0) {
              return true;
            }
            if (STATUS_WITH_CALLOFF.indexOf(this.data.suggestStation.suggestStatus) === -1) {
              return true;
            }
          }
        },
        {
          className: 'col-12 form-group',
          key: 'calloff4gCode',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã trạm 4G',
            required: true,
          },
          hideExpression: (model) => {
            if (!this.data.suggestStation.suggestionCallOff4gDTOList) {
              return true
            }
            if (this.data.suggestStation.suggestionCallOff4gDTOList.length < 1 || model.trangThaiPheDuyet === 0) {
              return true;
            }
            if (STATUS_WITH_CALLOFF.indexOf(this.data.suggestStation.suggestStatus) === -1) {
              return true;
            }
          }
        },
        {
          className: 'col-12 form-group',
          key: 'note',
          type: 'textareaT',
          templateOptions: {
            readonly: false,
            label: 'Ghi chú',
            required: true
          },
        },
      ]
    }
  ];

  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private suggestStationService: SuggestionStationService) {
  }

  ngOnInit() {

    this.data = this.config.data;

    console.log(this.data);
    // if (this.data.suggestStation.suggestionCallOff2gDTOList.length === 0) {
    //   this.fields[0].fieldGroup[2].hide = true;
    // }
    // console.log(this.fields[0].fieldGroup);

    // if (this.data.suggestStation.suggestionCallOff3gDTOList.length === 0) {
    //   this.fields[0].fieldGroup[3].hide = true;
    // }
    // if (this.data.suggestStation.suggestionCallOff4gDTOList.length === 0) {
    //   this.fields[0].fieldGroup[4].hide = true;
    // }

    console.log(this.data);
    //load data when open dialog
    //   this.model.cabinetCode = this.config.data.suggestionNewSiteDTO.stationCodeApproved;
    //   this.model.calloff2gCode = this.config.data.suggestionCallOff2gDTOList[0].cabinetCode;
    //   this.model.calloff3gCode = this.config.data.suggestionCallOff3gDTOList[0].cabinetCode;
    //   this.model.calloff4gCode = this.config.data.suggestionCallOff4gDTOList[0].cabinetCode;
    //  console.log(this.model);
  }

  onPheDuyet() {
    // truyen model+ id va status tu suggestStation
    const data = {
      suggestStation: this.data.suggestStation,
      model: this.model,
    };

    this.ref.close(data);
  }


}
