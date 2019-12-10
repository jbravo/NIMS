import {advantSearch, simpleSearch, SUGGEST_STATUS_SEARCH, SUGGEST_TYPES, SUGGEST_TYPES_SEARCH} from './../../suggest.constant';
import {BoldPipe} from './../../../../shared/pipes/bold.pipe';
import {Component, OnInit, Output, EventEmitter, ViewChild} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {FormlyFieldConfig} from '@ngx-formly/core';
import {Suggestion} from '@app/modules/ta-form-control/test-ui/test-ui.component';
import {SuggestionStationService} from '@app/modules/suggest-station/suggestion-station.service';
import {SuggestStationSearchModel} from '@app/modules/suggest-station/model/suggest-station-search.model';
import {Message, MessageService} from 'primeng/api';
import {ConfirmationService} from 'primeng/components/common/confirmationservice';
import {modelGroupProvider} from '@angular/forms/src/directives/ng_model_group';
import {SUGGEST_STATUS} from '../../suggest.constant';
import {Router, ActivatedRoute} from '@angular/router';
import * as moment from 'moment';
import {Table} from 'primeng/table';
import {SuggestDialogService} from '@app/modules/suggest-station/suggest-dialog.service';
@Component({
  selector: 'suggest-station-management',
  templateUrl: './suggest-station-management.component.html',
  styleUrls: ['./suggest-station-management.component.scss']
})
export class SuggestStationManagementComponent implements OnInit {
  @Output() changeTab = new EventEmitter();
  @Output() view = new EventEmitter();
  @Output() edit = new EventEmitter();
  @Output() row = new EventEmitter();

  @ViewChild('searchTable') searchTable: Table;

  date: any;
  msearchkey: any;
  valueItem: boolean; // checkbox table
  value: boolean;
  form = new FormGroup({});
  filter: any;
  suggestTypes: any[] = [{value: 0, label: '0'}, {value: 1, label: '1'}, {value: 2, label: '2'}, {value: 3, label: '3'}, {
    value: 4,
    label: '4'
  }, {value: 5, label: '5'}];
  suggestionSearchDTO: SuggestStationSearchModel = new SuggestStationSearchModel;
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row',
      fieldGroup: [
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'deptId',
          type: 'inputTree',
          templateOptions: {
            label: ' Đơn vị',
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'suggestType',
          type: 'select',
          defaultValue: null,
          templateOptions: {
            label: 'Loại đề xuất',
            placeholder: '--Lựa chọn--',
            options: SUGGEST_TYPES_SEARCH,
            showClear: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'suggestStatus',
          type: 'select',
          defaultValue: null,
          templateOptions: {
            label: 'Trạng thái đề xuất',
            placeholder: '--Lựa chọn--',
            options: SUGGEST_STATUS_SEARCH
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'fromDate',
          type: 'calendar',
          defaultValue: '',
          templateOptions: {
            label: 'Ngày đề xuất (Từ ngày)',
            showIcon: true
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'toDate',
          type: 'calendar',
          defaultValue: '',
          templateOptions: {
            label: 'Ngày đề xuất (Đến ngày)',
            showIcon: true,
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'suggestCode',
          type: 'input',
          templateOptions: {
            label: 'Mã đề xuất',
          }
        },
        {
          className: 'col-12 col-md-4 col-sm-6 form-group',
          key: 'userSearch',
          type: 'input',
          templateOptions: {
            label: 'Người đề xuất',
          }
        },
      ]
    },
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
  selectedRow: any;
  listData: any[] = [];
  options: Options[] = [];
  tableBtns: any;
  mSearch: any;
  columns = [
    // { field: 'stt', header: 'STT' },
    // { field: 'hanhDong', header: 'Hành động' },
    {field: 'suggestCode', header: 'Mã đề xuất'},
    {field: 'suggestTypeStr', header: 'Loại đề xuất'},
    {field: 'suggestStatusStr', header: 'Trạng thái đề xuất'},
    {field: 'deptName', header: 'Đơn vị đề xuất'},
    {field: 'userName', header: 'Người đề xuất'},
    {field: 'createTimeStr', header: 'Ngày tạo'},
  ];
  display = false;
  suggestId: any;

  constructor(private suggestionStationService: SuggestionStationService,
              private messageService: MessageService,
              private router: Router,
              private route: ActivatedRoute,
              private suggestDialogService: SuggestDialogService,
  ) {
  }

  ngOnInit() {

    this.suggestionSearchDTO.suggestCode = '';

    this.mSearch = false;
    this.searchEvent();
  }

  convertBeforeDate(mode: any) {
    const date2 = moment(mode.fromDate);
    return mode.beforeDate = date2.format('YYYY-MM-DD');
  }

  convertAfterDate(mode: any) {
    const date2 = moment(mode.toDate);
    return mode.afterDate = date2.format('YYYY-MM-DD');
  }

  convertDate(dateTime: any, pattern: any) {
    const date = moment(new Date(dateTime));
    return date.format(pattern);
  }

  convertSuggestType(type: any) {
    switch (type) {
      case (0): {
        return 'Đề xuất mới';
      }
      case (1): {
        return 'Đề xuất cosite';
      }
      case (2): {
        return 'Đề xuất di dời';
      }
      case (3): {
        return 'Đề xuất nâng hạ độ cao anten';
      }
      case (4): {
        return 'Đề xuất thêm cell';
      }
      case (5): {
        return 'Đề xuất swap thiết bị';
      }
      case (6): {
        return 'Đề xuất hủy';
      }
      default:
        return '';
    }
  }

  convertSuggestStatus(status: any) {
    switch (status) {
      case(0): {
        return 'Tạo mới';
      }
      case(1): {
        return 'BGĐ tỉnh phê duyệt thiết kế đề xuất';
      }
      case(2): {
        return 'BGĐ tỉnh từ chối thiết kế đề xuất';
      }
      case(3): {
        return 'Cập nhật thiết kế sau khảo sát';
      }
      case(4): {
        return 'BGĐ tỉnh đồng ý thiết kế sau khảo sát';
      }
      case(5): {
        return 'BGĐ tỉnh từ chối thiết kế sau khảo sát';
      }
      case(6): {
        return 'Hoàn thành thẩm định';
      }
      case(7): {
        return 'Đang trình ký';
      }
      case(8): {
        return 'Đã được phê duyệt trên VOFFICE';
      }
      case(9): {
        return 'Đã bị từ chối trên VOFFICE';
      }
      case(10): {
        return 'Đã thuê được vị trí';
      }
      case(11): {
        return 'Đã cập nhật call-off thực tế';
      }
      case(12): {
        return 'Đề xuất thay đổi giải pháp';
      }
      case(13): {
        return 'Đang trình ký call off sau khi thay đổi giải pháp';
      }
      case(14): {
        return 'Đã được phê duyệt call off sau khi thay đổi giải pháp';
      }
      case(15): {
        return 'Đã bị từ chối call off sau khi thay đổi giải pháp';
      }
      case(16): {
        return 'Đã phê duyệt call off thực tế';
      }
      case(17): {
        return 'Đã từ chối call off thực tế';
      }
      case(18): {
        return 'Đã cập nhật thiết kế thi công';
      }
      case(19): {
        return 'Đã phê duyệt thiết kế thi công';
      }
      case(20): {
        return 'Đã bị từ chối thiết kế thi công';
      }

      default:
        return '';
    }
  }

  searchEvent() {
    if (this.mSearch) {

      if (!this.validateAdvanceSearch()) {
        return;
      }

      this.convertBeforeDate(this.suggestionSearchDTO);
      this.convertAfterDate(this.suggestionSearchDTO);
      this.suggestionSearchDTO.type = advantSearch;

    } else {
      this.suggestionSearchDTO.type = simpleSearch;
    }
    this.suggestionSearchDTO.userName = 'nims_dev';

    this.onSearch();
  }

  validateAdvanceSearch() {
    if (!this.suggestionSearchDTO.fromDate) {
      this.messageService.add({severity: 'error', summary: 'Không thành công!', detail: 'Ngày đề xuất(Từ ngày) trống'});
      return false;
    }

    const now = moment().toDate();
    // this.converdatbeforeDate(this.suggestionSearchDTO);
    if (this.suggestionSearchDTO.fromDate.getTime() > now.getTime()) {
      this.messageService.add({
        severity: 'error',
        summary: 'Không thành công!',
        detail: 'Ngày đề xuất(Từ ngày) vượt quá ngày hiện tại'
      });
      return false;
    }

    if (!this.suggestionSearchDTO.toDate) {
      this.messageService.add({severity: 'error', summary: 'Không thành công!', detail: 'Ngày đề xuất(Đến ngày) trống'});
      return false;
    }
    const date = moment().toDate();
    if (this.suggestionSearchDTO.toDate.getTime() > date.getTime()) {
      this.messageService.add({
        severity: 'error',
        summary: 'Không thành công!',
        detail: 'Ngày đề xuất(Đến ngày) vượt quá ngày hiện tại'
      });
      return false;
    }

    if (this.suggestionSearchDTO.fromDate.getTime() > this.suggestionSearchDTO.toDate.getTime()) {
      this.messageService.add({
        severity: 'error',
        summary: 'Không thành công!',
        detail: 'Ngày đề xuất(Từ ngày) lớn hơn Ngày đề xuất(Đến ngày)'
      });
      return false;
    }
    return true;
  }

  onSearch() {
    console.log(this.suggestionSearchDTO);
    this.listData = [];
    this.suggestionStationService.getList(this.suggestionSearchDTO).subscribe(res => {
      if (res.body.data.length > 0) {
        res.body.data.forEach(element => {
          element.createTimeStr = this.convertDate(element.createTime, 'DD-MM-YYYY HH:mm:ss');
          element.suggestTypeStr = this.convertSuggestType(element.suggestType);
          element.suggestStatusStr = this.convertSuggestStatus(element.suggestStatus);
        });
        this.listData = res.body.data;
      } else {
        console.log(res.body);
        if (res.body && res.body.errorCode && res.body.errorCode === 'INVALID_DATA') {
          this.messageService.add({
            severity: 'error',
            summary: 'Không thành công!',
            detail: res.body.message
          });
        }
      }
      console.log(this.listData);
    });
  }

  changeSearch() {
    this.mSearch = !this.mSearch;
    this.suggestionSearchDTO = new SuggestStationSearchModel();
    this.suggestionSearchDTO.toDate = moment().toDate();
    this.suggestionSearchDTO.fromDate = moment().add('days', -365).toDate();
  }

  onAdd() {

    // this.changeTab.emit();
    this.router.navigate(['./action', 'new'], {relativeTo: this.route});
  }

  goToDetail(item) {
    this.router.navigate(['./action', 'view'], {relativeTo: this.route, queryParams: {id: item.suggestId}});
  }

  goToEdit(list) {
    this.router.navigate(['./action', 'edit'], {relativeTo: this.route, queryParams: {id: list.suggestId}});
  }

  onAccept(list: any) {
    const dialogRef = this.suggestDialogService.getDialog(list);
    dialogRef.onClose.subscribe((model: any) => {
      console.log(model);
      const data = {
        suggestId: model.suggestStation.suggestId,
        suggestStatus: model.model.trangThaiPheDuyet,
        suggestStationCode: model.model.cabinetCode,
        userName: model.suggestStation.userName,
        note: model.model.note,
        calloff2gCode: model.model.calloff2gCode,
        calloff3gCode: model.model.calloff3gCode,
        calloff4gCode: model.model.calloff4gCode,
      };
      this.suggestionStationService.setSuggestStatus(data).subscribe(
        res => {
          this.messageService.add({severity: 'success', summary: 'Cập nhật phê duyệt!', detail: 'Cập nhật phê duyệt Trạm thành công'});
          this.searchEvent();
        },
        err => {
          this.messageService.add({severity: 'error', summary: 'Cập nhật phê duyệt!', detail: 'Cập nhật phê duyệt Trạm thất bại'});
        }
      );
    });
  }

  // phê duyệt
  // onSet() {
  //   console.log(this.suggestId);
  //   this.suggestionStationService.setSuggestStatus(1, this.suggestId).subscribe(
  //     res => {
  //       this.messageService.add({severity: 'success', summary: 'Phê duyệt thành công!', detail: 'Phê duyệt đề xuất Trạm thành công'});
  //       this.display = false;
  //       this.searchEvent();
  //     },
  //     err => {
  //       this.messageService.add({severity: 'error', summary: 'Phê duyệt thất bại!', detail: 'Phê duyệt đề xuất Trạm thất bại'});
  //       console.log(err);

  //     }

  //   );
  // }
  // từ chối
  // onUnSet() {
  //   this.suggestionStationService.setSuggestStatus(2, this.suggestId).subscribe(
  //     res => {
  //       this.messageService.add({severity: 'success', summary: 'Từ chối phê duyệt!', detail: 'Từ chối phê duyệt Trạm thành công'});
  //       this.display = false;
  //       this.searchEvent();
  //     },
  //     err => {
  //       console.log(err);
  //       this.messageService.add({severity: 'error', summary: 'Từ chối phê duyệt!', detail: 'Từ chối phê duyệt Trạm thất bại'});
  //     }
  //   );
  // }

  showConfirm2() {
    let listDelete: any = [];
    // this.confirmationService.confirm({
    //   message: 'Are you sure that you want to perform this action?',
    //   accept: () => {
    //     //Actual logic to perform a confirmation
    //   }
    // });
    const accept = confirm('Chắc chắn xóa');
    if (accept) {
      listDelete = this.selectedRow.map(item => {
        return item.suggestId;
      });
      const data = {
        userName: 'nims_dev',
        idSuggestions: listDelete
      }
      console.log(data);
      this.suggestionStationService.deleteSuggestion(data).subscribe(res => {
        this.messageService.add({severity: 'success', summary: 'Xoá thành cồng', detail: res.body.message});
        this.onSearch();
      });
    }
  }
}

interface Options {
  name: string;
  code: string;
}
