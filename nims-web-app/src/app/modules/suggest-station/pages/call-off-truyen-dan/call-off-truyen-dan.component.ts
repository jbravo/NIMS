import {Component, OnInit} from '@angular/core';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {FormGroup} from '@angular/forms';
import {MessageService} from 'primeng/primeng';

@Component({
  selector: 'call-off-truyen-dan',
  templateUrl: './call-off-truyen-dan.component.html',
  styleUrls: ['./call-off-truyen-dan.component.scss']
})
export class CallOffTruyenDanComponent implements OnInit {
  form = new FormGroup({});
  model: any;
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'callOffType',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: '* Loại call-off',
            options: [
              {label: 'Call-off đề xuất', value: null},
              {label: 'Call-off khảo sát', value: null},
              {label: 'Call-off thực tế', value: null},
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
          key: 'transInterface',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: '* Giao diện truyền dẫn',
            options: [
              {label: '2G: E1/GE', value: null},
              {label: '3G:FE/GE', value: null},
              {label: '4G: GE/10GE', value: null},
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
          key: 'designer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: '* Người thiết kế',
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
  fieldsInformation: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'type_offer',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Mã tuyến',
            options: [
              {label: 'Lấy từ danh sách tuyến hiện có trên NIMS', value: null},
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
          key: 'type_offer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Chiều dài treo tái sử dụng theo (m)',
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
          key: 'type_offer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Chiều dài trôn tái sử dụng theo (m)',
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
  fieldsInformationT: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'type_offer',
          type: 'select',
          templateOptions: {
            readonly: false,
            label: 'Mã tuyến',
            options: [
              {label: 'Lấy từ danh sách tuyến hiện có trên NIMS', value: null},
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
          key: 'type_offer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã điểm đầu',
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
          key: 'type_offer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Mã điểm cuối',
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
          key: 'type_offer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Chiều dài treo',
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
          key: 'type_offer',
          type: 'input',
          templateOptions: {
            readonly: false,
            label: 'Chiều dài chôn',
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

  constructor() {
  }

  ngOnInit() {
  }

}
