import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {TableModule} from 'primeng/table';

@Component({
  selector: 'test-ui',
  templateUrl: './test-ui.component.html',
  styleUrls: ['./test-ui.component.css']
})
export class TestUiComponent implements OnInit {
  form = new FormGroup({});
  model = {};
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'unit',
          type: 'input',
          templateOptions: {
            label: 'Đơn vị',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'type',
          type: 'select',
          templateOptions: {
            label: 'Loại đề xuất',
            required: true,
            options: [
              {label: 'Select City', value: null},
              {label: 'New York', value: {id: 1, name: 'New York', code: 'NY'}},
              {label: 'Rome', value: {id: 2, name: 'Rome', code: 'RM'}},
              {label: 'London', value: {id: 3, name: 'London', code: 'LDN'}},
              {label: 'Istanbul', value: {id: 4, name: 'Istanbul', code: 'IST'}},
              {label: 'Paris', value: {id: 5, name: 'Paris', code: 'PRS'}}
            ],
            showClear: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'status',
          type: 'input',
          templateOptions: {
            label: 'Trạng thái đề xuất',
            required: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'fromDate',
          type: 'calendar',
          templateOptions: {
            label: 'Ngày đề xuất (Từ ngày)',
            required: true,
            showIcon: true
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'toDate',
          type: 'calendar',
          templateOptions: {
            label: 'Ngày đề xuất (Đến ngày)',
            required: true,
            showIcon: true,
          }
        },
      ]
    },
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-6 col-md-4 col-sm-6 form-group',
          key: 'text',
          type: 'textareaT',
          templateOptions: {
            placeholder: 'Nhập trường tìm kiếm',
            label: 'Nguyên nhân đề suất',
            rowsT: '3',
            colsT: '150'
          }
        }
      ]
    }
  ];
  fieldsSearch: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-6 col-md-4 col-sm-6 form-group',
          key: 'search',
          type: 'input',
          templateOptions: {
            placeholder: 'Nhập trường tìm kiếm',
            suffixIcon: 'person',
            showicon: 'true',
          }
        }
      ]
    }
  ];

  constructor() {
  }

  listData: Suggestion[] = [];
  options: Options[] = [];
  items: any;
  mSearch: any;

  ngOnInit() {
    this.mSearch = false;
    this.listData = [
      {code: '1121321', type: 'VÔ tuyến', status: 'true', unit: 'Đơn vị 1', auth: 'SInh', createdDate: '01/11/2019'},
      {code: '122323', type: 'Vô tuyến', status: 'false', unit: 'Đơn vị 2', auth: 'Sinh', createdDate: '01/11/2019'},
      {code: '122323', type: 'Vô tuyến', status: 'false', unit: 'Đơn vị 3', auth: 'Sinh', createdDate: '01/11/2019'},

    ];
    this.options = [
      {name: 'Xem chi tiết', code: '1221321'},
      {name: 'Xem lịch sử tác động', code: '23123'},
      {name: 'Chỉnh sửa', code: '21321321'},
      {name: 'Cập nhất trạng thái', code: '21312123'},
      {name: 'Xoá', code: 'ưqewqewqewqe'}

    ];
    this.items = [
      {label: 'New', icon: 'fa fa-plus', routerLink: '/controls'},
      {label: 'Open', icon: 'fa fa-download'}
    ];
  }

  submit() {
    console.log(this.model);
  }

  changeSearch() {
    this.mSearch = !this.mSearch;
  }
}

export interface Suggestion {
  code;
  type;
  status;
  unit;
  auth;
  createdDate;
}

interface Options {
  name: string;
  code: string;
}
