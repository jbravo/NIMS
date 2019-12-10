import { Component, OnInit, ViewChild } from '@angular/core';
import { DialogService, MessageService, TreeNode } from 'primeng/api';
import { RadioInformationComponent } from '../radio-information/radio-information.component';
import { RadioSuggestionInforComponent } from '../radio-suggestion-infor/radio-suggestion-infor.component';
import { Calloff2GComponent } from '../calloff2-g/calloff2-g.component';
import { Calloff3GComponent } from '../calloff3-g/calloff3-g.component';
import { SuggestionStationModel } from '../../model/suggestion-station.model';
import { SuggestionStationService } from '../../suggestion-station.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SuggestStationDataService } from '@app/modules/suggest-station/suggest-station-data.service';
import { getPheDuyetButton, USERNAME } from '../../suggest.constant';
import { SuggestDialogService } from '../../suggest-dialog.service';

export const TREE_NODES = {
  THONG_TIN_VI_TRI: 1,
  THONG_TIN_DE_XUAT_VO_TUYEN: 2,
  CALL_OF_2G: 3,
  CALL_OF_3G: 4,
  CALL_OF_4G: 5,
  SECTOR_2G: '2S',
  SECTOR_3G: '3S',
  SECTOR_4G: '4S',
};

@Component({
  selector: 'suggest-station-holder',
  templateUrl: './suggest-station-holder.component.html',
  styleUrls: ['./suggest-station-holder.component.scss'],
  providers: [DialogService]
})
export class SuggestStationHolderComponent implements OnInit {

  constructor(
    private messageService: MessageService,
    private suggestionStationService: SuggestionStationService,
    private route: ActivatedRoute,
    private router: Router,
    private dataService: SuggestStationDataService,
    private suggestDialogService: SuggestDialogService,
  ) {
  }
  isSave: boolean; //
  id: any; //id when view and edit
  count0 = 1; // for add new sector calloff 2g
  count1 = 1; // for add new sector calloff 3g
  count2 = 1; // for add new sector calloff 4g
  nodeSelect: number; // determine what node are choosing
  componentChosing = 'radioInformation';
  index = 0;
  isClosed = true;
  isViewClosed = true;
  isEditClosed = true;
  selectedFiles: TreeNode;
  value1: boolean;
  value2: boolean;
  value: any = 50; // progressBar
  title: any;
  suggestStation: any;
  actionType: 'new' | 'edit' | 'view';
  files = [
    {
      label: 'Thông tin vị trí',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.THONG_TIN_VI_TRI
    },
    {
      label: 'Thông tin đề xuất vô tuyến',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.THONG_TIN_DE_XUAT_VO_TUYEN
    },
    {
      label: 'Thông tin call-off',
      id: null,
      selectable: false,
      children: [],
      collapsedIcon: 'fa fa-folder',
      expandedIcon: 'fa fa-folder-open'
    },
  ];
  // @ViewChild(RadioInformationComponent) radioInfor: RadioInformationComponent;
  // @ViewChild(RadioSuggestionInforComponent) radioSuggestionInforComponent: RadioSuggestionInforComponent;
  // @ViewChild(Calloff2GComponent) callOff2g: Calloff2GComponent;
  // @ViewChild(Calloff3GComponent) callOff3g: Calloff3GComponent;

  suggestionStationModel: SuggestionStationModel;
  ctmenus: any = [
    {
      label: 'Thêm sector', icon: 'pi pi-fw pi-plus', command: (event) => {
        this.addSector();
      }
    },
    { label: 'Reset dữ liệu', icon: 'pi pi-fw pi-refresh' },
    {
      label: 'Nhân bản', icon: 'pi pi-fw pi-refresh', command: (event) => {
        this.copySector();
      }
    },
  ];
  selectedNode: any;
  selectedNodeId: any;
  TREE_NODES = TREE_NODES;
  isLoadingData = true;
  checkvalidate = 0;

  ngOnInit() {
    this.dataService.resetData();
    this.selectedNode = this.files[0];
    this.suggestionStationModel = new SuggestionStationModel();
    this.route.params.subscribe(param => {
      this.actionType = param.action;
      switch (param.action) {
        case 'new': {
          this.dataService.resetData();
          this.title = 'Tạo mới đề xuất Trạm';
          this.value = 6;
          this.isLoadingData = false;
          break;
        }
        case 'edit': {
          this.loadDataDetail(false, true);
          this.title = 'Sửa đề xuất Trạm';
          // this.isLoadingData = true;
          break;
        }
        case 'view': {
          this.loadDataDetail(true, true);
          this.title = 'Chi tiết đề xuất Trạm';
          break;
        }
      }
    });
  }

  loadDataDetail(readOnly?, save?) {
    this.route.queryParams.subscribe(va => {
      if (va.id) {
        this.id = va.id;
        this.suggestionStationService.getSuggestionById(this.id).subscribe(res => {
          this.suggestStation = res.body.data;
          this.remapTree(this.suggestStation);
          this.dataService.mainData = { ...this.dataService.mapperToResponse(this.suggestStation, readOnly, save) };
          console.log(this.dataService.mainData);
          this.isLoadingData = false;
        });
      }
    });
  }

  remapTree(data) {
    console.log(data);
    this.files[2].children = [];
    const call2G = {
      label: 'Call-off 2G',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.CALL_OF_2G,
      children: []
    };
    const call3G = {
      label: 'Call-off 3G',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.CALL_OF_3G,
      children: []
    };
    const call4G = {
      label: 'Call-off 4G',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.CALL_OF_4G,
      children: []
    };
    if (data.suggestionCallOff2gDTOList && data.suggestionCallOff2gDTOList[0]) {
      this.files[2].children.push(call2G);
      const data2g = data.suggestionCallOff2gDTOList[0];
      if (data2g.suggestionSector2gDTOList && data2g.suggestionSector2gDTOList.length > 0) {
        data2g.suggestionSector2gDTOList.forEach((sector, index) => {
          this.files[2].children[this.files[2].children.length - 1].children.push(
            {
              label: `Sector ${index + 1}`,
              id: `${TREE_NODES.SECTOR_2G}`,
              index: index
            }
          );
        });
      }
    }
    if (data.suggestionCallOff3gDTOList && data.suggestionCallOff3gDTOList[0]) {
      this.files[2].children.push(call3G);
      const data3g = data.suggestionCallOff3gDTOList[0];
      if (data3g.suggestionSector3gDTOList && data3g.suggestionSector3gDTOList.length > 0) {
        data3g.suggestionSector3gDTOList.forEach((sector, index) => {
          this.files[2].children[this.files[2].children.length - 1].children.push(
            {
              label: `Sector ${index + 1}`,
              id: `${TREE_NODES.SECTOR_3G}`,
              index: index
            }
          );
        });
      }
    }
    if (data.suggestionCallOff4gDTOList && data.suggestionCallOff4gDTOList[0]) {
      this.files[2].children.push(call4G);
      const data4g = data.suggestionCallOff4gDTOList[0];
      if (data4g.suggestionSector4gDTOList && data4g.suggestionSector4gDTOList.length > 0) {
        data4g.suggestionSector4gDTOList.forEach((sector, index) => {
          this.files[2].children[this.files[2].children.length - 1].children.push(
            {
              label: `Sector ${index + 1}`,
              id: `${TREE_NODES.SECTOR_4G}`,
              index: index
            }
          );
        });
      }
    }
    console.log(this.files);
  }

  onNodeSelect(event) {
    // this.route.navigateByUrl("radio-suggestion-infor");
    this.value1 = false;
    this.value2 = false;
  }

  // cap nhat trang thai de xuat
  onAccept() {
    const rs = getPheDuyetButton(this.suggestStation.suggestStatus);
    if (rs.onlySave) {
      this.saveData();
      return;
    }
    const dialogRef = this.suggestDialogService.getDialog(this.suggestStation);
    dialogRef.onClose.subscribe((model: any) => {
      if (!model) {
        return;
      }
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
          // this.ngOnInit();
          this.dataService.resetData();
          this.suggestionStationService.getSuggestionById(this.id).subscribe(dd => {
            this.suggestStation = dd.body.data;
            this.remapTree(this.suggestStation);
            this.dataService.mainData = { ...this.dataService.mapperToResponse(this.suggestStation, false) };
          });
          this.messageService.add({ severity: 'success', summary: 'Cập nhật phê duyệt!', detail: 'Cập nhật phê duyệt Trạm thành công' });
        },
        err => {
          this.messageService.add({ severity: 'error', summary: 'Cập nhật phê duyệt!', detail: 'Cập nhật phê duyệt Trạm thất bại' });
        }
      );
    });
    console.log(dialogRef);
    // dialogRef.on
    // this.suggestionStationService.setSuggestStatus(this.suggestStation.suggestStatus,this.suggestStation.id).subscribe(
    //   res=>{
    //     this.messageService.add({severity: 'success', summary: 'Cập nhật thành công!', detail: 'Cập nhật đề xuất Trạm thành công'});
    //   },
    //   err=>{
    //     this.messageService.add({severity: 'error', summary: 'Cập nhật thất bại!', detail: 'Cập nhật đề xuất Trạm thất bại'});
    //     console.log(err);
    //   }

    // );
  }

  saveData() {
    this.checkvalidate = 0;
    this.suggestionStationModel = new SuggestionStationModel();
    console.log(this.suggestStation);
    this.suggestionStationModel = this.dataService.mapperToRequest(this.dataService.mainData);
    this.suggestionStationModel.suggestId = this.suggestStation ? this.suggestStation.suggestId : null;
    this.suggestionStationModel.createTime = this.suggestStation ? this.suggestStation.createTime : null;
    this.suggestionStationModel.userName = 'nims_dev';
    this.suggestionStationModel.deptId = 341;
    console.log(this.suggestionStationModel);
    // if (this.suggestionStationModel.suggestionCallOff2gDTOList && this.suggestionStationModel.suggestionCallOff2gDTOList[0]) {
    //   if (Object.keys(this.suggestionStationModel.suggestionCallOff2gDTOList[0]).length <= 3) {
    //     this.suggestionStationModel.suggestionCallOff2gDTOList = null;
    //   }
    // }
    // if (this.suggestionStationModel.suggestionCallOff2gDTOList != null) {
    //   this.suggestionStationModel.suggestionCallOff2gDTOList.forEach(e => {
    //     if (e.suggestionSector2gDTOList.length < 1) {
    //       e.suggestionSector2gDTOList = null;
    //     }
    //   });
    // }
    // if (this.suggestionStationModel.suggestionCallOff3gDTOList && this.suggestionStationModel.suggestionCallOff3gDTOList[0]) {
    //   if (Object.keys(this.suggestionStationModel.suggestionCallOff3gDTOList[0]).length <= 3) {
    //     this.suggestionStationModel.suggestionCallOff3gDTOList = null;
    //   }
    // }
    // if (this.suggestionStationModel.suggestionCallOff3gDTOList != null) {
    //   this.suggestionStationModel.suggestionCallOff3gDTOList.forEach(e => {
    //     if (e.suggestionSector3gDTOList.length < 1) {
    //       e.suggestionSector3gDTOList = null;
    //     }
    //   });
    // }
    // if (this.suggestionStationModel.suggestionCallOff4gDTOList && this.suggestionStationModel.suggestionCallOff4gDTOList[0]) {
    //   if (Object.keys(this.suggestionStationModel.suggestionCallOff4gDTOList[0]).length <= 3) {
    //     this.suggestionStationModel.suggestionCallOff4gDTOList = null;
    //   }
    // }
    // if (this.suggestionStationModel.suggestionCallOff4gDTOList != null) {
    //   this.suggestionStationModel.suggestionCallOff4gDTOList.forEach(e => {
    //     if (e.suggestionSector4gDTOList.length < 1) {
    //       e.suggestionSector4gDTOList = null;
    //     }
    //   });
    // }
    console.log(this.suggestionStationModel);
    // console.log(JSON.stringify(this.suggestionStationModel));
    this.validate();
    if (this.checkvalidate == 0) {
      if (this.suggestionStationModel.suggestId) {
        this.updateDataAfterValidate();
      } else {
        this.saveDataAfterValidate();
      }
    }
  }

  private addSector() {
    const current = this.selectedNode;
    console.log(current);

    if (current.id === TREE_NODES.CALL_OF_2G || current.id === TREE_NODES.CALL_OF_3G || current.id === TREE_NODES.CALL_OF_4G) {
      const newSector = {
        label: `Sector ${current.children.length + 1}`,
        id: current.id === TREE_NODES.CALL_OF_2G ? TREE_NODES.SECTOR_2G :
          (current.id === TREE_NODES.CALL_OF_3G ? TREE_NODES.SECTOR_3G : TREE_NODES.SECTOR_4G),
        index: current.children.length
      };
      if (current.children.length < 9) {
        current.children.push(newSector);
        this.dataService.addSector(current);
      } else {
        this.messageService.add({ severity: 'error', summary: 'Số sector', detail: 'Không vượt quá 9' });
        return;
      }

    }
  }

  onListType($event) {
    // em add call-off o day
    // console.log('aaa');
    this.dataService.resetCallOf();

    this.files[2].children = [];
    const call2G = {
      label: 'Call-off 2G',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.CALL_OF_2G,
      children: []
    };
    const call3G = {
      label: 'Call-off 3G',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.CALL_OF_3G,
      children: []
    };
    const call4G = {
      label: 'Call-off 4G',
      expandedIcon: 'fa fa-folder-open',
      collapsedIcon: 'fa fa-folder',
      id: TREE_NODES.CALL_OF_4G,
      children: []
    };

    switch ($event) {
      case 0: {
        this.files[2].children[0] = call2G;
        this.files[2].children[0].children = [];
        this.dataService.mainData.callOff2G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
      case 1: {
        this.files[2].children[0] = call3G;
        this.dataService.mainData.callOff3G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
      case 2: {
        this.files[2].children[0] = call4G;
        this.dataService.mainData.callOff4G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
      case 4: {
        this.files[2].children[0] = call2G;
        this.files[2].children[1] = call3G;
        this.dataService.mainData.callOff2G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        this.dataService.mainData.callOff3G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
      case 5: {
        this.files[2].children[0] = call2G;
        this.files[2].children[1] = call4G;
        this.dataService.mainData.callOff2G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        this.dataService.mainData.callOff4G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
      case 7: {
        this.files[2].children[0] = call3G;
        this.files[2].children[1] = call4G;
        this.dataService.mainData.callOff3G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        this.dataService.mainData.callOff4G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
      case 10: {
        this.files[2].children[0] = call2G;
        this.files[2].children[1] = call3G;
        this.files[2].children[2] = call4G;
        this.dataService.mainData.callOff2G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        this.dataService.mainData.callOff3G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        this.dataService.mainData.callOff4G = {
          radioPillarHeight: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarHeight,
          radioPillarType: this.dataService.mainData.thongTinDeXuatVoTuyen.radioPillarType,
        };
        break;
      }
    }
  }

  private validate() {
    if (this.suggestionStationModel.suggestionNewSiteDTO.stationCode == null) {
      this.messageService.add({ severity: 'error', summary: 'Thông tin vị trí', detail: 'Mã nhà trạm đề xuất không được bỏ trống' });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.lng == null) {
      this.messageService.add({ severity: 'error', summary: 'Thông tin vị trí', detail: 'Kinh độ không được bỏ trống' });
      console.log(this.suggestionStationModel.suggestionNewSiteDTO.lng);
      this.checkvalidate++;
      return;
    } else {
      const listCkeckLngStie = this.suggestionStationModel.suggestionNewSiteDTO.lng.toString().split('.');
      if (this.suggestionStationModel.suggestionNewSiteDTO.lng >= 0) {
        if (listCkeckLngStie[0].length > 3 || (+listCkeckLngStie[0]) > 180
          || (+listCkeckLngStie[0]) < -180 || (listCkeckLngStie[1] && listCkeckLngStie[1].length < 5)) {
          this.messageService.add({
            severity: 'error', summary: 'Thông tin vị trí',
            detail: 'Kinh độ sai định dạng, bạn được nhập tối đa 3 ' +
              'chữ số phần nguyên và tối thiểu 5 chữ ' +
              'số phần thập phân và nằm trong khoảng [-180,180]'
          });
          this.checkvalidate++;
          return;
        }
      } else {
        if (listCkeckLngStie[0].length > 4 || (+listCkeckLngStie[0]) > 180
          || (+listCkeckLngStie[0]) < -180 || (listCkeckLngStie[1] && listCkeckLngStie[1].length < 5)) {
          this.messageService.add({
            severity: 'error', summary: 'Thông tin vị trí',
            detail: 'Kinh độ sai định dạng, bạn được nhập tối đa 3 ' +
              'chữ số phần nguyên và tối thiểu 5 chữ ' +
              'số phần thập phân và nằm trong khoảng [-180,180]'
          });
          this.checkvalidate++;
          return;
        }
      }
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.stationCode == null) {
      this.messageService.add({ severity: 'error', summary: 'Thông tin vị trí', detail: 'Mã nhà trạm đề xuất không được bỏ trống' });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.auditType == null) {
      this.messageService.add({ severity: 'error', summary: 'Thông tin vị trí', detail: 'Phân loại kiểm định trạm gốc không được để trống' });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.lat == null) {
      this.messageService.add({ severity: 'error', summary: 'Thông tin vị trí', detail: 'Vĩ độ không được bỏ trống' });
      this.checkvalidate++;
      return;
    } else {
      const listCheck = this.suggestionStationModel.suggestionNewSiteDTO.lat.toString().split('.');
      const phanNguyen = listCheck[0];
      const phanThuc = listCheck[1];
      if (this.suggestionStationModel.suggestionNewSiteDTO.lat >= 0) {
        if (phanNguyen.length > 2 || (+phanNguyen) > 90 || (+phanNguyen) < -90 || (phanThuc && phanThuc.length < 5)) {
          this.messageService.add({
            severity: 'error', summary: 'Thông tin vị trí',
            detail: 'Vĩ độ sai định dạng, bạn được nhập tối đa 2 ' +
              'chữ số phần nguyên và tối thiểu 5 chữ ' +
              'số phần thập phân và nằm trong khoảng [-90,90]'
          });
          this.checkvalidate++;
          return;
        }
      } else {
        if (listCheck[0].length > 3 || (+listCheck[0]) > 90 || (+listCheck[0]) < -90 || (listCheck[1] && listCheck[1].length < 5)) {
          this.messageService.add({
            severity: 'error', summary: 'Thông tin vị trí',
            detail: 'Vĩ độ sai định dạng, bạn được nhập tối đa 2 ' +
              'chữ số phần nguyên và tối thiểu 5 chữ ' +
              'số phần thập phân và nằm trong khoảng [-90,90]'
          });
          this.checkvalidate++;
          return;
        }
      }
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.locationId == null) {
      this.messageService.add({ severity: 'error', summary: 'Thông tin vị trí', detail: 'Địa bàn không được bỏ trống.' });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.locationId == null) {
      this.messageService.add({
        severity: 'error', summary: 'Thông tin vị trí',
        detail: 'Phận loại kiểm định trạm gốc không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.stationTechType == null) {
      this.messageService.add({
        severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
        detail: 'Phân loại trạm theo công nghệ triển khai không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.stationSolutionType == null) {
      this.messageService.add({
        severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
        detail: 'Phân loại trạm theo giải pháp phủ sóng không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.radioLocationType == null) {
      this.messageService.add({
        severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
        detail: 'Phân loại địa hình theo vô tuyến không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.radioPillarType == null) {
      this.messageService.add({
        severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
        detail: 'Loại cột không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.hasConcavePoint === 1) {
      if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.concavePointPopulation2g == null) {

        this.messageService.add({
          severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
          detail: 'Số dân phủ lõm phủ được 2G không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      }
      if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.concavePointPopulation4g == null) {

        this.messageService.add({
          severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
          detail: 'Số dân phủ lõm phủ được 4G không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      }
      if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.concavePointPopulation3gData == null) {

        this.messageService.add({
          severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
          detail: 'Số dân phủ lõm phủ được 3G data không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      }
      if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.concavePointPopulation3gVoice == null) {

        this.messageService.add({
          severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
          detail: 'Số dân phủ lõm phủ được 3G thoại không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      }
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.hasConcavePoint != null &&
      this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.hasConcavePoint !== 2) {
      if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.concaveCode == null) {
        this.messageService.add({
          severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
          detail: 'Mã vùng lõm không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      }
    } else {
      this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.concaveCode = null;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.radioPillarHeight == null) {
      this.messageService.add({
        severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
        detail: 'Độ cao cột không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.specialArea != null
      && this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.specialArea !== 2) {
      if (this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.specialAreaType == null) {
        this.messageService.add({
          severity: 'error', summary: 'Thông tin đề xuất vô tuyến',
          detail: 'Loại khu vực đặc biệt không được để trống.'
        });
        this.checkvalidate++;
        return;
      }
    } else {
      this.suggestionStationModel.suggestionNewSiteDTO.suggestionRadioDTO.specialAreaType = null;
    }
    console.log(this.suggestionStationModel);
    if (this.suggestionStationModel.suggestionCallOff2gDTOList != null &&
      this.suggestionStationModel.suggestionCallOff2gDTOList.length > 0) {
      this.suggestionStationModel.suggestionCallOff2gDTOList.forEach(res => {
        // if (res.cabinetCode == null) {
        //   this.messageService.add({
        //     severity: 'error', summary: 'Call Off 2G', detail: 'Bạn cần nhập thông tin “Mã trạm”.'
        //   });
        //   this.checkvalidate++;
        //   return;
        // }
        if (res.cabinetCodeSuggest == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Bạn cần nhập thông tin “Mã trạm đề xuất”.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.itemId == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Tên thiết bị không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.deviceTypeId == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Loại thiết bị không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transType == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Loại truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transInterface == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Giao diện truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transCapacity == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Dung lượng truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.cfg == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Cấu hình không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.stationPurpose == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Mục đích đặt trạm không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.isBuildingEdge == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 2G', detail: 'Triển khai cột ở các mép tòa nhà không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.suggestionSector2gDTOList != null && res.suggestionSector2gDTOList.length > 0) {
          for (let i = 0; i < res.suggestionSector2gDTOList.length; i++) {
            this.vadidateSector('Call Off 2G-Sector ' + (i + 1), res.suggestionSector2gDTOList[i]);
          }
        }

      });
    }
    if (this.suggestionStationModel.suggestionCallOff3gDTOList != null &&
      this.suggestionStationModel.suggestionCallOff3gDTOList.length > 0) {
      this.suggestionStationModel.suggestionCallOff3gDTOList.forEach(res => {
        // if (res.cabinetCode == null) {
        //   this.messageService.add({
        //     severity: 'error', summary: 'Call Off 3G', detail: 'Mã trạm không được bỏ trống.'
        //   });
        //   this.checkvalidate++;
        //   return;
        // }
        if (res.cabinetCodeSuggest == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Mã trạm đề xuất không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.radioPillarType == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Loại cột không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.radioPillarHeight == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Độ cao cột không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.itemId == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Tên thiết bị không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.deviceTypeId == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Loại thiết thiết bị không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transType == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Loại truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transInterface == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Giao diện truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transCapacity == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Dung lượng diện truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.cfg == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Cấu hình CE không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.stationPurpose == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Mục đích đặt trạm không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.isBuildingEdge == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 3G', detail: 'Triển khai cột ở các mép tòa nhà không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.suggestionSector3gDTOList != null && res.suggestionSector3gDTOList.length > 0) {
          for (let i = 0; i < res.suggestionSector3gDTOList.length; i++) {
            this.vadidateSector('Call Off 3G-Sector ' + (i + 1), res.suggestionSector3gDTOList[i]);
          }
        }

      });
    }
    if (this.suggestionStationModel.suggestionCallOff4gDTOList != null &&
      this.suggestionStationModel.suggestionCallOff4gDTOList.length > 0) {
      this.suggestionStationModel.suggestionCallOff4gDTOList.forEach(res => {
        // if (res.cabinetCode == null) {
        //   this.messageService.add({
        //     severity: 'error', summary: 'Call Off 4G', detail: 'Mã trạm không được bỏ trống.'
        //   });
        //   this.checkvalidate++;
        //   return;
        // }
        if (res.cabinetCodeSuggest == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Bạn cần nhập thông tin “Mã trạm đề xuất”.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.radioPillarType == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Loại cột không được bỏ trống.'
          });
          return;
        }
        if (res.transType == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Loại truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transInterface == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Giao diện truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.itemId == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Tên thiết bị không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.deviceTypeId == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Loại thiết thiết bị không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.transCapacity == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Dung lượng truyền dẫn không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.cfgHardwave == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Cấu hình phần cứng không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.cfgSoftware == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Cấu hình phần mềm không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.cfg == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Cấu hình CE không được bỏ trống.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.stationPurpose == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Bạn cần nhập thông tin “Mục đích đặt trạm.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.isBuildingEdge == null) {
          this.messageService.add({
            severity: 'error', summary: 'Call Off 4G', detail: 'Bạn cần nhập thông tin: Triển khai cột ở các mép tòa nhà.'
          });
          this.checkvalidate++;
          return;
        }
        if (res.suggestionSector4gDTOList != null && res.suggestionSector4gDTOList.length > 0) {
          for (let i = 0; i < res.suggestionSector4gDTOList.length; i++) {
            this.vadidateSector('Call Off 4G-Sector ' + (i + 1), res.suggestionSector4gDTOList[i]);
          }
        }
      });
    }
  }

  saveDataAfterValidate() {
    this.suggestionStationModel.userName = USERNAME;
    this.suggestionStationService.createSuggestion(this.suggestionStationModel).subscribe(res => {
      if (res && res.body.errorCode.toLocaleLowerCase() === 'error') {
        this.messageService.add({
          severity: 'error', summary: 'Thêm mới đề xuất', detail: res.body.message
        });
        return;
      }
      if (res && res.body.errorCode.toLocaleLowerCase() === 'success') {
        this.messageService.add({
          severity: 'success', summary: 'Thêm mới đề xuất', detail: res.body.message
        });
        this.router.navigate(['/suggest-station']);
        return;
      }
    });
  }

  updateDataAfterValidate() {
    this.suggestionStationModel.userName = USERNAME;
    this.suggestionStationService.updateSuggestion(this.suggestionStationModel).subscribe(res => {
      if (res && res.body.errorCode.toLocaleLowerCase() === 'error') {
        this.messageService.add({
          severity: 'error', summary: 'Update đề xuất', detail: res.body.message
        });
        return;
      }
      if (res && res.body.errorCode.toLocaleLowerCase() === 'success') {
        this.messageService.add({
          severity: 'success', summary: 'Update đề xuất', detail: res.body.message
        });
        this.router.navigate(['/suggest-station']);
        return;
      }
    });
  }

  vadidateSector(nameField: string, obj: any) {
    if (obj.sectorCode == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Tên sector không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.sectorCfg == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Cấu hình sector không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.isJointSector == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Có ghép sector hay không không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.numberSplitterPerSector == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Số splitter cần/sector không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.jointSectorCode == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Mã sector ghép không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.isJointRru == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Có ghép RRU hay không? không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.numRruPerSector == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Số RRU/sector không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.increaseCapacitySectorCode == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Sector tăng công suất không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.isJointAntena == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Có dùng chung anten hay không? không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.antenaHeight == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Chiều cao anten so với chân cột không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.antenaHeightGround == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Chiều cao anten so với mặt đất không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.antenaTypeId == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Loại anten không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.rruHeight == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Khoảng cách anten đến đáy RRU không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    }
    if (obj.lng == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Kinh độ không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    } else {
      const listchecklng = obj.lng.toString().split('.');
      if (+listchecklng[0] > 0) {
        if (listchecklng[0].length > 3 || (+listchecklng[0]) > 180 ||
          (+listchecklng[0] < (-180) || (listchecklng[1] && listchecklng[1].length < 5))) {
          this.messageService.add({
            severity: 'error', summary: nameField,
            detail: 'Kinh độ sai định dạng, bạn được nhập tối đa 3 chữ ' +
              'số phần nguyên và tối thiểu 5 chữ số phần thập phân và nằm trong khoảng [-180,180].'
          });
          this.checkvalidate++;
          return;
        }
      } else {
        if (listchecklng[0].length > 4 || (+listchecklng[0]) > 180 ||
          (+listchecklng[0] < (-180) || (listchecklng[1] && listchecklng[1].length < 5))) {
          this.messageService.add({
            severity: 'error', summary: nameField,
            detail: 'Kinh độ sai định dạng, bạn được nhập tối đa 3 chữ ' +
              'số phần nguyên và tối thiểu 5 chữ số phần thập phân và nằm trong khoảng [-180,180].'
          });
          this.checkvalidate++;
          return;
        }
      }
    }
    if (obj.lat == null) {
      this.messageService.add({
        severity: 'error', summary: nameField, detail: 'Vĩ độ không được bỏ trống.'
      });
      this.checkvalidate++;
      return;
    } else {
      const listcheckLat = obj.lat.toString().split('.');
      if (+listcheckLat[0] > 0) {
        if (listcheckLat[0].length > 3 || (+listcheckLat[0]) > 180
          || (+listcheckLat[0] < (-180) || (listcheckLat[1] && listcheckLat[1].length < 5))) {
          this.messageService.add({
            severity: 'error', summary: nameField,
            detail: 'Kinh độ sai định dạng, bạn được nhập tối đa 3 chữ ' +
              'số phần nguyên và tối thiểu 5 chữ số phần thập phân và nằm trong khoảng [-180,180].'
          });
          this.checkvalidate++;
          return;
        }
      } else {
        if (listcheckLat[0].length > 4
          || (+listcheckLat[0]) > 180 || (+listcheckLat[0] < (-180) || (listcheckLat[1] && listcheckLat[1].length < 5))) {
          this.messageService.add({
            severity: 'error', summary: nameField,
            detail: 'Kinh độ sai định dạng, bạn được nhập tối đa 3 chữ ' +
              'số phần nguyên và tối thiểu 5 chữ số phần thập phân và nằm trong khoảng [-180,180].'
          });
          this.checkvalidate++;
          return;
        }
      }
      if (obj.azimuth == null) {
        this.messageService.add({
          severity: 'error', summary: nameField, detail: 'Góc azimuth không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      } else {
        if (obj.azimuth > 360 || obj.azimuth < 0) {
          this.messageService.add({
            severity: 'error', summary: nameField, detail: 'Góc azimuth nhập giá trị từ  [0;360].'
          });
          this.checkvalidate++;
          return;
        }
      }
      if (obj.tiltMechan == null) {
        this.messageService.add({
          severity: 'error', summary: nameField, detail: 'Góc tilt cơ không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      } else {
        if (obj.tiltMechan > 90 || obj.tiltMechan < -90) {
          this.messageService.add({
            severity: 'error', summary: nameField, detail: 'Góc tilt cơ nhập giá trị từ  [-90,90].'
          });
          this.checkvalidate++;
          return;
        }
      }
      if (obj.tiltElec == null) {
        this.messageService.add({
          severity: 'error', summary: nameField, detail: 'Góc tilt cơ không được bỏ trống.'
        });
        this.checkvalidate++;
        return;
      } else {
        if (obj.tiltElec > 90 || obj.tiltElec < -90) {
          this.messageService.add({
            severity: 'error', summary: nameField, detail: 'Góc tilt cơ nhập giá trị từ  [-90,90].'
          });
          this.checkvalidate++;
          return;
        }
      }
    }
  }


  // private addSector() {
  //   const current = this.selectedNode;
  //   if (current.id === TREE_NODES.CALL_OF_2G || current.id === TREE_NODES.CALL_OF_3G || current.id === TREE_NODES.CALL_OF_4G) {
  //     const newSector = {
  //       label: `Sector ${current.children.length + 1}`,
  //       id: current.id === TREE_NODES.CALL_OF_2G ? TREE_NODES.SECTOR_2G :
  //         (TREE_NODES.CALL_OF_3G ? TREE_NODES.SECTOR_3G : TREE_NODES.SECTOR_4G),
  //       index: current.children.length
  //     };
  //     current.children.push(newSector);
  //     console.log(current.children)
  //     this.dataService.addSector(current);
  //   }
  // }
  private copySector() {
    const current = this.selectedNode;
    if ([TREE_NODES.SECTOR_2G, TREE_NODES.SECTOR_3G, TREE_NODES.SECTOR_4G].indexOf(current.id) === (-1)) {
      return;
    }
    const newSector = {
      label: `Sector ${current.parent.children.length + 1}`,
      id: current.parent.id === TREE_NODES.CALL_OF_2G ? TREE_NODES.SECTOR_2G :
        (current.parent.id === TREE_NODES.CALL_OF_3G ? TREE_NODES.SECTOR_3G : TREE_NODES.SECTOR_4G),
      index: current.parent.children.length
    };
    if (current.parent.children.length < 9) {
      current.parent.children.push(newSector);
      this.dataService.copySector(current);
    } else {
      this.messageService.add({ severity: 'error', summary: 'Số sector', detail: 'Không vượt quá 9' });
      return;
    }
  }

  getTitlePheDuyet(data) {
    if (!data) {
      return;
    }
    const rs = getPheDuyetButton(data.suggestStatus);
    return rs.label;
  }

  checkHidePheDuyetBtn() {
    if (!this.suggestStation) {
      return;
    }
    return ([1, 10, 16].indexOf(this.suggestStation.suggestStatus) > -1);
  }
}
