import { Component, OnInit, OnDestroy, AfterViewInit, Type, ViewChild, ElementRef, HostListener } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { AppComponent } from '@app/app.component';
import { MessageService } from 'primeng/api';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { CAT_ITEM, VALIDATE_STYLE } from '@app/shared/services/constants';
import { PermissionService } from '@app/shared/services/permission.service';
import { ActivatedRoute } from '@angular/router';
import { OdfService } from '../../service/odf.service';
import { TabLayoutService, TabLayoutComponent } from '@app/layouts/tab-layout';
import { Subscription } from 'rxjs';
import {
  AutocompleteSearchStationModalComponent,
} from '@app/shared/components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
import {
  AutocompleteSearchDepartmentModalComponent
  // tslint:disable-next-line:max-line-length
} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {
  AutocompleteSearchControlComponent
} from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import { EventBusService } from '@app/shared/services/event-bus.service';

/**
 * Component ODF Edit
 * Created by DungPH
 */
@Component({
  selector: 'odf-edit',
  templateUrl: '../odf-save/odf-save.component.html',
  styleUrls: ['../odf-save/odf-save.component.css']
})
export class ODFEditComponent implements OnInit, OnDestroy, AfterViewInit {
  // @ViewChild('enterPress') inputEl: ElementRef;
  @ViewChild('autoCompleteDept') autoCompleteDept: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteStation') autoCompleteStation: AutocompleteSearchControlComponent;

  formAdd: FormGroup;
  statusCheck = false;
  positions: any[] = [];
  action: string;
  isTabChanged: string;

  vendorList: any[] = [];
  odfTypeList: any[] = [];
  ownerList: any[] = [];

  stationList: any[];
  deptList: any[];

  // Style
  styleClass;
  styleStation;
  styleDept;
  styleOdfIndex;
  styleOdfType;
  styleOwner;
  styleODFCode;
  styleEdit;

  // odfCodeShow = `${this.odfCodeFirst}-ODF-${this.odfCodeLast}`;óóóó
  private contentHasChangedSub: Subscription;
  station: Type<any> = AutocompleteSearchStationModalComponent;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  headerStation = this.app.translation.translate('common.dialog.header.station');
  headerDept = this.app.translation.translate('common.dialog.header.dept');

  stationId: number;
  deptId: number;
  deptLabel: string;
  odfId: number;

  isNoteFocus = false;
  // style = 'style="background: #F8F8F8; color: rgb(204, 204, 204);"';
  dataRequest = {
    odfId: null,
    stationId: null,
    // odfIndex: null,
    odfCode: null,
    deptId: null,
    odfTypeId: null,
    ownerId: null,
    vendorId: null,
    installationDate: null,
    note: null,
  };

  constructor(private app: AppComponent,
    private odfService: OdfService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private tabLayoutComponent: TabLayoutComponent) {
    this.buildForm({});
    this.getVendorList();
    this.getOdfTypeList();
    this.getOwnerList();
  }

  ngOnInit() {
    // setTimeout(() => this.inputEl.nativeElement.focus());
    this.action = this.odfService.action;
    this.formAdd.value.odfId = this.odfService.id;
    this.odfId = this.odfService.id;
    // this.styleEdit = 'background: #d0d0d0; color:black';
    this.odfService.findOdfById(this.odfService.id).subscribe(res => {
      const object = res.content;
      if (object) {
        const odfIndexArray = object.odfCode.split('-');
        const odfIndex = odfIndexArray[odfIndexArray.length - 1];
        const note = object.note;
        const installationDate = CommonUtils.stringToDate(object.installationDate, 'yyyy/MM/dd');
        // console.log(object);
        this.formAdd.value.odfIndex = odfIndex;
        this.formAdd.value.note = note;
        this.formAdd.patchValue({
          odfId: object.odfId,
          odfIndex: odfIndex,
          note: note,
          installationDate: installationDate,
          odfTypeId: object.odfTypeId,
          ownerId: object.ownerId,
          vendorId: object.vendorId,
          odfCode: object.odfCode
        });
        this.permissionService.findStation({ stationId: object.stationId, first: 0, rows: 10 }).subscribe(result => {
          const item = result.content.listData[0];
          this.formAdd.patchValue({ stationId: item.stationId });
          this.autoCompleteStation.displayField = { stationCode: item.stationCode };
          // this.eventBusService.emit({
          //   deptDataObj: item
          // });
        });
        this.permissionService.findDepartmentById(object.deptId).subscribe(rs => {
          this.formAdd.patchValue({ deptId: rs.deptId });
          this.autoCompleteDept.displayField = { pathName: rs.pathName };
          // this.eventBusService.emit({
          //   deptDataObj: rs
          // });
        });
      }
    });

    // this.odfCode = '-ODF-';
  }

  ngOnDestroy(): void {
    if (this.odfService.action !== 'view') {
      if (this.contentHasChangedSub) {
        this.contentHasChangedSub.unsubscribe();
      }
    }

  }

  ngAfterViewInit(): void {
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      this.isTabChanged = val;
      // console.log(this.isTabChanged);
      this.tabLayoutService.isTabContentHasChanged({
        component: ODFEditComponent.name,
        key: `${ODFEditComponent.name}_${this.odfId * 121}`
      });
    });
  }


  test(event) {
    // console.log(this.formAdd.value.houseStationTypeId);
  }

  // lay danh sach hang san xuat
  getVendorList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.VENDOR).subscribe(res => {

      this.vendorList = [];
      // truong hop them moi
      this.vendorList.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      for (let i = 0; i < res.length; i++) {
        this.vendorList.push({
          label: res[i].itemName,
          value: +res[i].itemId
        });
      }
      if (this.odfService.action === 'view') {
        this.vendorList.forEach(item => {
          if (item.value === this.formAdd.value.vendorId) {

            this.formAdd.patchValue({ vendorId: item.label });
            this.formAdd.value.vendorId = item.label;
          }
        });
      }
      // console.log(this.vendorList);
    });

    // console.log(this.vendorList);
  }

  // lay danh sach loai ODF
  getOdfTypeList() {
    this.permissionService.getCatOdfTypes().subscribe(res => {

      this.odfTypeList = [];
      // truong hop them moi
      this.odfTypeList.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      for (let i = 0; i < res.content.listData.length; i++) {
        this.odfTypeList.push({
          label: res.content.listData[i].odfTypeCode,
          value: +res.content.listData[i].odfTypeId
        });
      }

      if (this.odfService.action === 'view') {
        this.odfTypeList.forEach(item => {
          if (item.value === this.formAdd.value.odfTypeId) {
            // console.log(item);

            this.formAdd.patchValue({ odfTypeId: item.label });
            this.formAdd.value.odfTypeId = item.label;
          }
        });
      }
    });
  }

  // lay list chu so huu
  getOwnerList() {
    this.permissionService.getInfoFromCatItem(CAT_ITEM.CAT_OWNER).subscribe(res => {
      this.ownerList = [];
      // truong hop them moi
      this.ownerList.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      for (let i = 0; i < res.length; i++) {
        this.ownerList.push({
          label: res[i].itemName,
          value: +res[i].itemId
        });
      }

      if (this.odfService.action === 'view') {
        this.ownerList.forEach(item => {
          if (item.value === this.formAdd.value.ownerId) {
            // console.log(item);

            this.formAdd.patchValue({ ownerId: item.label });
            this.formAdd.value.ownerId = item.label;
          }
        });
      }
    });
  }

  // lay gia tri cua dropdown roi set vao form
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formAdd.controls[element].setValue(event.value.value);
    } else {
      this.formAdd.controls[element].setValue('');
    }
  }

  setAuditStatus(event, element: string) {
    this.setSelectedValue(event, element);
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      odfId: [null],

      stationId: ['', Validators.compose([
        // Validators.required,
        // CommonUtils.customValidateReturnLabel('station.stationCode')
      ])],

      odfIndex: [null, Validators.compose([
        // Validators.required,
        // CommonUtils.customValidateReturnLabel('odf.odfIndex')
      ])],

      odfCode: ['', Validators.compose([
        // Validators.maxLength(200),
        // CommonUtils.customValidateReturnLabel('odf.Code')
      ])],

      deptId: [null, Validators.compose([
        // Validators.required,
        // CommonUtils.customValidateReturnLabel('odf.deptName')
      ])],

      odfTypeId: [null, Validators.compose([
        // Validators.required,
        // CommonUtils.customValidateReturnLabel('odf.odfTypeCode')
      ])],

      ownerId: [null, Validators.compose([
        // Validators.required,
        // CommonUtils.customValidateReturnLabel('odf.ownerCode')
      ])],

      vendorId: [null, Validators.compose([])],


      installationDate: ['', Validators.compose([
        // Validators.pattern('')
      ])],

      note: ['', Validators.maxLength(500)],
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  get formControls() {
    return this.formAdd.controls;
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    this.clearFocusValidate();
    let isSuccess = false;

    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'odf', 'odfSave')) {
      isSuccess = true;
    }
    if (this.customValidator(isSuccess)) {
      success();
    }

  }

  customValidator(isSuccess) {

    if (this.formAdd.value.deptId === null || this.formAdd.value.deptId === '' || this.formAdd.value.stationId === undefined) {
      isSuccess = false;
      // this.formAdd.value.deptId.markAsDirty();
      this.styleDept = VALIDATE_STYLE.INVALID;
      this.messageService.add({
        key: 'odfSave',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('odf.validate.input').concat(this.translation.translate('odf.deptName'))
      });
    }
    if (this.formAdd.value.odfTypeId === null || this.formAdd.value.odfTypeId === '' || this.formAdd.value.odfTypeId === undefined) {
      isSuccess = false;
      this.styleOdfType = VALIDATE_STYLE.INVALID;
      // this.formAdd.markAsDirty({onlySelf: true});
      this.messageService.add({
        key: 'odfSave',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('odf.validate.select').concat(this.translation.translate('odf.odfTypeCode'))
      });
    }
    if (this.formAdd.value.ownerId === null || this.formAdd.value.ownerId === '' || this.formAdd.value.ownerId === undefined) {
      isSuccess = false;
      this.styleOwner = VALIDATE_STYLE.INVALID;
      // this.formAdd.markAsDirty({onlySelf: true});
      this.messageService.add({
        key: 'odfSave',
        sticky: true,
        severity: 'error',
        summary: this.translation.translate('odf.validate.select').concat(this.translation.translate('odf.ownerCode'))
      });
    }
    return isSuccess;
  }

  clearFocusValidate() {
    this.styleDept = VALIDATE_STYLE.VALID;
    this.styleOdfType = VALIDATE_STYLE.VALID_DROPDOWN;
    this.styleOwner = VALIDATE_STYLE.VALID_DROPDOWN;
  }

  onSubmit() {
    // if (this.isTabChanged === 'INVALID') {
    this.statusCheck = true;
    this.messageService.clear();
    // this.convertDataRequest();
    // console.log(this.dataRequest);
    this.getFormValidationErrors(() => {
      this.convertDataRequest();
      this.odfService.saveOdf(this.dataRequest).subscribe(res => {
        let isSuccess = 'success';
        let toarstMess = this.translation.translate('odf.save.success');
        let key = 'odfSave';
        let summary = '';
        // console.log(res);
        switch (res.status) {
          case '200':
            isSuccess = 'success';
            toarstMess = this.translation.translate('odf.update.success');
            summary = this.translation.translate('common.label.NOTIFICATIONS');
            key = 'sd';
            break;
          case '304':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.update.failed.coupler.notModify');
            key = 'odfSave';
            break;
          case '409':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.update.failed.coupler.conflict');
            key = 'odfSave';
            break;
          case '428':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.save.failed.system');
            key = 'odfSave';
            break;
          case '406':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.update.failed.coupler.notAccept');
            key = 'odfSave';
            break;
          // case '428':
          //   // switch (res.message) {
          //   //   case '':
          //   //     break;

          //   // }
          //   isSuccess = 'error';
          //   toarstMess = this.translation.translate('odf.save.failed.system');
          //   key = 'odfSave';
          //   break;
          // case '409':
          //   break;
          default:
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.save.failed.system');
            key = 'odfSave';
        }
        this.messageService.clear('odfSave');
        this.messageService.add({
          severity: isSuccess,
          key: key,
          summary: summary,
          detail: toarstMess
        });
        // console.log(isSuccess);
        if (isSuccess === 'success') {
          this.odfService.listAction = 'reload';
          this.odfService.saveOrEdit = 'edit';
          this.tabLayoutService.close({
            component: 'OdfListComponent',
            header: 'odf.tab.list'
          });
          this.tabLayoutService.open({
            component: 'OdfListComponent',
            header: 'odf.tab.list',
            breadcrumb: [
              { label: this.translation.translate('odf.tab.list') }
            ]
          });
          this.tabLayoutService.close({
            component: ODFEditComponent.name,
            header: '',
            tabId: 121 * this.odfId
          });
        }
        // this.closeTabView();
      }, error => {
        this.messageService.add({
          severity: 'error',
          key: 'odfSave',
          sticky: true,
          summary: this.translation.translate('odf.save.failed.system'),
        });
      });
    });

    // }

  }

  convertDataRequest() {
    this.dataRequest.odfId = this.formAdd.value.odfId ? this.formAdd.value.odfId : null;
    this.dataRequest.stationId
      = this.formAdd.value.stationId.stationId
        ? this.formAdd.value.stationId.stationId
        : this.formAdd.value.stationId.value
          ? this.formAdd.value.stationId.value
          : this.formAdd.value.stationId
            ? this.formAdd.value.stationId
            : '';
    // this.dataRequest.odfIndex = undefined;
    this.dataRequest.odfCode = this.formAdd.value.odfCode ? this.formAdd.value.odfCode : '';
    this.dataRequest.deptId = this.formAdd.value.deptId.deptId
      ? this.formAdd.value.deptId.deptId
      : this.formAdd.value.deptId.value
        ? this.formAdd.value.deptId.value
        : this.formAdd.value.deptId
          ? this.formAdd.value.deptId
          : '';
    this.dataRequest.odfTypeId = this.formAdd.value.odfTypeId ? this.formAdd.value.odfTypeId : null;
    this.dataRequest.ownerId = this.formAdd.value.ownerId ? this.formAdd.value.ownerId : null;
    this.dataRequest.vendorId = this.formAdd.value.vendorId ? this.formAdd.value.vendorId : null;
    this.dataRequest.installationDate = this.formAdd.value.installationDate ? this.formAdd.value.installationDate : null;
    this.dataRequest.note = this.formAdd.value.note ? this.formAdd.value.note : null;
  }

  checkWarning(e: FormControl) {
    if (this.statusCheck) {
      if (!e.valid) {
        return true;
      }
    }
    return false;
  }
  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.keyCode === 13) {
      this.eventBusService.dataChange.subscribe(val => {
        // console.log(val);
        if (val) {
          if (val.data.key === `${ODFEditComponent.name}_${this.odfId * 121}`) {
            // if (val.data.key === ODFEditComponent.name) {
            this.onSubmit();
          }
        }
      }).unsubscribe();
    }
  }

  onChangeRowSelectDept(event: any) {
    this.formAdd.controls['deptId'].setValue(event.deptId);
    this.deptLabel = event.pathName;
    this.styleDept = VALIDATE_STYLE.VALID;
    this.eventBusService.emit({
      value: event
    });
    // console.log('ád');
  }

  getStation(event) {
    // console.log(event);
    this.permissionService.findStation({ stationId: event, first: 0, rows: 10 }).subscribe(result => {
      // console.log(result);
      this.stationList = [];
      for (let i = 0; i < result.content.listData.length; i++) {
        this.stationList.push({
          label: result.content.listData[i].stationCode,
          value: result.content.listData[i].stationId
        });
      }
      this.stationList.forEach(item => {
        if (item.value === this.formAdd.value.stationId) {
          if (this.odfService.action === 'view') {
            this.formAdd.value.stationId = item.label;
            this.formAdd.patchValue({ stationId: item.label });
          }
          if (this.odfService.action === 'edit') {
            this.formAdd.patchValue({ stationId: item });
            this.formAdd.value.stationId = item.value;
          }

        }
      });
    });
  }

  onclear(data) {
    this.formAdd.controls[data].setValue(null);
  }

  getDept(event) {
    this.permissionService.findCatDeptByPost({ pathName: event.query, first: 0, rows: 10 }).subscribe(s => {
      // console.log('sd');
      // console.log(s);
      this.deptList = [];
      for (let i = 0; i < s.content.listData.length; i++) {
        this.deptList.push({
          label: s.content.listData[i].pathName,
          value: s.content.listData[i].deptId,
          name: s.content.listData[i].deptName
        });
      }

      this.deptList.forEach(item => {
        if (item.value === this.formAdd.value.deptId) {
          // console.log(`123`);
          // console.log(item);
          if (this.odfService.action === 'view') {
            this.formAdd.value.deptId = item.label;
            this.formAdd.patchValue({ deptId: item.label });
          }
          if (this.odfService.action === 'edit') {
            this.formAdd.patchValue({ deptId: item });
            this.formAdd.value.deptId = item.value;
          }
        }
      });
    });
  }

  setFormValue(event, element) {
    this.formAdd.controls[element].setValue(event);

  }

  onSelectStation(event) {
    // console.log('avc');
    // console.log(event);

    // this.formAdd.controls[element].setValue(event.value);

    // this.formAdd.controls['odfIndex'].setValue(99);

    // this.odfCodeFirst = event.value;
    // this.odfCode = `${this.odfCodeFirst}-ODF-${this.odfCodeLast}`;
    // this.formAdd.value.odfCode = `${this.odfCodeFirst}-ODF-${this.odfCodeLast}`;
    // console.log(`${event.value}  ${this.odfCodeFirst} ${this.odfCode}`);

    this.odfService.getMaxOdfByStationId(event.value).subscribe(res => {
      // console.log(res);
      let odfIndex = res.data.odfIndex;
      if (odfIndex >= 99) {
        this.messageService.clear('deleteSuccess');

        // console.log(status);
        this.messageService.add({
          severity: 'error',
          key: 'br',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('odf.odfIndex.failed')
        });
        this.setOdfIndex();
        return;
      }

      if ((++odfIndex).toString().length < 2) {
        odfIndex = `0${odfIndex}`;
      }

      this.formAdd.controls['odfIndex'].setValue(odfIndex);
      this.setOdfIndex();
    });
  }

  onBlurOdfIndex(event) {
    // console.log(event);
    if (this.action !== 'edit') {
      let temp = this.formAdd.value.odfIndex;

      // console.log(temp);
      if (parseInt(temp) === 0) {
        temp = 1;
      }
      // if (parseInt(temp) < 10) {


      //   // console.log(`this.formAdd.value.odfIndex = ${this.formAdd.value.odfIndex}`);
      // }
      if (temp.length < 2) {
        temp = temp + '0';

      }
      // console.log(temp);
      this.formAdd.controls['odfIndex'].setValue(temp);

      this.setOdfIndex();
    }

  }

  setOdfIndex() {

  }

  onClosedTab() {
    if (this.isTabChanged === 'INVALID') {
      this.messageService.clear('c');
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(ODFEditComponent.name));
    } else {
      this.tabLayoutService.close({
        component: ODFEditComponent.name,
        tabId: this.odfId * 121
      });
    }

  }

  submitOdf() {
    this.messageService.clear('c');
    let message = this.translation.translate('odf.confirm.save');
    if (this.action === 'edit') {
      message = this.translation.translate('odf.confirm.update');

    }
    this.messageService.add({
      key: 'c',
      sticky: true,
      severity: 'warn',
      summary: message,
    });
  }

  // onChangeRowSelectStation(event: any) {
  //   // console.log(event);
  //   this.stationId = event.stationId;
  //   this.odfCodeFirst = event.stationCode;
  //   this.odfService.getMaxOdfByStationId(event.stationId).subscribe(res => {
  //     // console.log(res);
  //     let odfIndex = res.data.odfIndex;
  //     if (odfIndex >= 99) {
  //       this.messageService.clear('ocf');

  //       // console.log(status);
  //       this.messageService.add({
  //         severity: 'error',
  //         key: 'br',
  //         summary: this.translation.translate('common.label.NOTIFICATIONS'),
  //         detail: this.translation.translate('odf.odfIndex.failed')
  //       });
  //       this.setOdfIndex();
  //       return;
  //     }

  //     if ((++odfIndex).toString().length < 2) {
  //       odfIndex = `0${odfIndex}`;
  //     }

  //     this.formAdd.controls['odfIndex'].setValue(odfIndex);
  //     this.setOdfIndex();
  //   });
  // }

  onClearRowSelect(event) {
    this.formAdd.controls[event].setValue(null);
  }

  onConfirm(item) {
    this.messageService.clear('c');
    this.onSubmit();
  }

  onReject() {
    this.messageService.clear('c');
  }

  onClearDatePicker() {
    this.formAdd.controls['installationDate'].setValue(null);
  }

  onInput(event) {
    // console.log(event);
    if (event) {
      if (event.currentTarget.value === '') {
        this.formAdd.controls['installationDate'].setValue(null);
      }
    }
  }

  onNoteFocus() {
    console.log(this.isNoteFocus);
    this.isNoteFocus = true;

  }
  onNoteBlur() {
    console.log(this.isNoteFocus);
    this.isNoteFocus = false;
  }
}
