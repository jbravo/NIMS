import { AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, Type, ViewChild, HostListener } from '@angular/core';
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
import { TabLayoutComponent, TabLayoutService } from '@app/layouts/tab-layout';
import { Subscription } from 'rxjs';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchStationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchDepartmentModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
// tslint:disable-next-line:max-line-length
import { AutocompleteSearchControlComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';

import { EventBusService } from '@app/shared/services/event-bus.service';
// import * as $ from 'jquery';
import { FormHooks } from '@angular/forms/src/model';
import { StationService } from '@app/modules/stations-management/service/station.service';

/**
 * Component ODF Save
 * Created by DungPH
 */
@Component({
  selector: 'odf-save',
  templateUrl: './odf-save.component.html',
  styleUrls: ['./odf-save.component.css']
})
export class ODFSaveComponent implements OnInit, OnDestroy, AfterViewInit {
  // tslint:disable-next-line:no-input-rename
  // @ViewChild('enterPress') inputEl: ElementRef;
  // @Input('ngModelOptions');
  @ViewChild('autoCompleteDept')
  autoCompleteDept: AutocompleteSearchControlComponent;
  @ViewChild('autoCompleteStation')
  autoCompleteStation: AutocompleteSearchControlComponent;

  options: {
    name?: string;
    standalone?: boolean;
    updateOn?: FormHooks;
  };
  // Style
  styleClass;
  styleStation;
  styleDept;
  styleOdfIndex;
  styleOdfType;
  styleOwner;
  styleODFCode;
  styleEdit;

  formAdd: FormGroup;
  statusCheck = false;
  positions: any[] = [];
  action: string;
  isTabChanged: string;
  isNoteFocus = false;

  vendorList: any[] = [];
  odfTypeList: any[] = [];
  ownerList: any[] = [];

  stationList: any[];
  deptList: any[];

  odfCodeFirst = '';
  odfCodeLast = '';
  odfCode = '';

  // odfCodeShow = `${this.odfCodeFirst}-ODF-${this.odfCodeLast}`;
  style = '';
  private contentHasChangedSub: Subscription;
  stationId: number;
  deptId: number;
  deptLabel: string;
  headerStation = this.app.translation.translate('common.dialog.header.station');
  headerDept = this.app.translation.translate('common.dialog.header.dept');
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  station: Type<any> = AutocompleteSearchStationModalComponent;
  odfId;

  dataRequest = {
    odfId: null,
    stationId: '',
    // odfIndex: '',
    odfCode: '',
    deptId: '',
    odfTypeId: '',
    ownerId: '',
    vendorId: '',
    installationDate: '',
    note: ''
  };
  odfAction;

  private dataChanger: Subscription;

  constructor(
    private app: AppComponent,
    private odfService: OdfService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private eventBusService: EventBusService,
    private tabLayoutComponent: TabLayoutComponent,
    private stationService: StationService
  ) {
    this.buildForm({});
    this.getVendorList();
    this.getOdfTypeList();
    this.getOwnerList();
    this.action = '';
    // $(function () {
    //   $('#enterPress').keypress(function (e) {
    //     if (e.keyCode === 13) {
    //       alert('You pressed enter!');
    //     }
    //   });
    // });
  }

  ngOnInit() {
    this.formAdd.value.installationDate = new Date();
    this.formAdd.patchValue({ installationDate: new Date() });
    this.action = this.odfService.action;
    if (this.action === 'edit') {
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
      // setTimeout(() => this.inputEl.nativeElement.focus());
    } else {
      this.eventBusService.odfStationChange.subscribe(val => {
        // console.log(val);
        if (val.data) {
          this.permissionService.findStation({ stationId: val.data, first: 0, rows: 10 }).subscribe(result => {
            const item = result.content.listData[0];
            this.formAdd.patchValue({ stationId: item.stationId });
            this.autoCompleteStation.displayField = { stationCode: item.stationCode };
            this.onChangeRowSelectStation(item);
          });
        }
        // this.autoCompleteStation.displayField = { stationCode: item.stationCode };
      }).unsubscribe();
      this.eventBusService.emitDataChange({
        type: 'fromStation',
        data: null
      });
      // setTimeout(() => this.inputEl.nativeElement.focus());}
    }
  }


  ngAfterViewInit(): void {
    if (this.action === 'edit') {
      this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
        this.isTabChanged = val;
        // console.log(this.isTabChanged);
        this.tabLayoutService.isTabContentHasChanged({
          component: ODFSaveComponent.name,
          key: `${ODFSaveComponent.name}_edit_${this.odfId * 121}`
        });
      });
    } else {
      console.log(1);
      this.formAdd.statusChanges.subscribe(val => {
        console.log(val);
        this.isTabChanged = val;
        this.tabLayoutService.isTabContentHasChanged({
          component: ODFSaveComponent.name,
          key: ODFSaveComponent.name
        });
      });
    }

  }

  ngOnDestroy(): void {
    this.messageService.clear('odfSave');
    if (this.action === 'edit') {
      if (this.contentHasChangedSub) {
        this.contentHasChangedSub.unsubscribe();
      }
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.keyCode === 13) {
      this.eventBusService.dataChange.subscribe(val => {
        console.log(val);
        if (val) {
          // if (val.key === ODFSaveComponent.name || (val.tabId && val.tabId === this.odfIid )) {
          if (val.data.key === `${ODFSaveComponent.name}`
            || (val.data.key === `${ODFSaveComponent.name}_edit_${this.odfId * 121}`)) {
            if (!this.isNoteFocus) {
              this.onSubmit();
            }
          }
        }
      }).unsubscribe();
    }
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
      if (this.action === 'view') {
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

      if (this.action === 'view') {
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

      if (this.action === 'view') {
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

  // buildForm(formData: any) {
  //   // this.formAdd = CommonUtils.createForm(formData, null, {});
  //   this.formAdd = CommonUtils.createForm(formData, {
  //     odfId: [null],

  //     stationId: new FormControl(null, {
  //       validators:
  //         Validators.compose([
  //           Validators.required
  //           // CommonUtils.customValidateReturnLabel('station.stationCode')
  //         ])
  //       , updateOn: 'change'
  //     }),

  //     odfIndex: new FormControl(null, {
  //       validators: Validators.compose([
  //         Validators.required, Validators.minLength(1)

  //         // Validators.pattern('\"^\d{2}\"'),
  //         // CommonUtils.customValidateReturnLabel('odf.odfIndex')
  //       ]),
  //       updateOn: 'change'
  //     }),

  //     odfCode: ['', Validators.compose([
  //       Validators.maxLength(200),
  //       // CommonUtils.customValidateReturnLabel('odf.Code')
  //     ])],

  //     deptId: [null, Validators.compose([
  //       Validators.required
  //       // CommonUtils.customValidateReturnLabel('odf.deptName')
  //     ])],

  //     odfTypeId: new FormControl(null, {
  //       validators: Validators.compose([
  //         Validators.required,
  //         // CommonUtils.customValidateReturnLabel('odf.odfTypeCode')
  //       ]), updateOn: 'change'
  //     }),

  //     ownerId: [null, Validators.compose([])],

  //     vendorId: [null, Validators.compose([])],


  //     installationDate: [new Date(), Validators.compose([])],

  //     note: ['', Validators.maxLength(500)]
  //   }, Validators.required);
  // }

  get f() {
    return this.formAdd.controls;
  }

  get formControls() {
    return this.formAdd.controls;
  }

  onClearDatePicker() {
    this.formAdd.controls['installationDate'].setValue('');
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formAdd.controls['installationDate'].setValue('');
      }
    }
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    this.clearFocusValidate();
    let isSuccess = true;

    // if (CommonUtils.getFormValidationErrors(this.formAdd, this.app, 'odf', 'odfSave')) {
    //   isSuccess = true;
    // }

    isSuccess = this.customValidator(isSuccess);
    if (this.action === 'edit') {
      if (isSuccess) {
        success();
      }
    } else {
      if (this.stationId) {
        this.stationService.findStationById(this.stationId).subscribe(res => {
          console.log(res);
          // this.odfCode = '';
          // console.log(res.content.listData.length);
          if (!res.content) {
            isSuccess = false;
            this.styleStation = VALIDATE_STYLE.INVALID;
            this.messageService.add({
              key: 'odfSave',
              sticky: true,
              severity: 'error',
              summary: this.translation.translate('odf.stationId.notExist')
            });
          }
          this.odfService.findAdvanceOdf({ odfCode: this.odfCode, rows: 1, first: 0 }).subscribe(result => {
            console.log(result);
            // this.odfCode = '';
            // console.log(result.content.listData.length);
            if (result.content.listData.length > 0) {
              isSuccess = false;
              this.styleODFCode = VALIDATE_STYLE.INVALID;
              this.messageService.add({
                key: 'odfSave',
                sticky: true,
                severity: 'error',
                summary: this.translation.translate('odf.odfCode.conflict')
              });
            }
            if (isSuccess) {
              success();
            }
          });
        });
      }
    }
  }

  customValidator(isSuccess) {
    if (this.action !== 'edit') {
      if (this.formAdd.value.stationId === null || this.formAdd.value.stationId === '' || this.formAdd.value.stationId === undefined) {
        isSuccess = false;
        // this.formAdd.value.stationId.markAsDirty();
        this.styleStation = VALIDATE_STYLE.INVALID;
        this.messageService.add({
          key: 'odfSave',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('odf.validate.input').concat(this.translation.translate('station.stationCode'))
        });
      }
      if (this.formAdd.value.odfIndex === null || this.formAdd.value.odfIndex === '') {
        isSuccess = false;
        // this.formAdd.markAsDirty({onlySelf: true});
        this.styleOdfIndex = VALIDATE_STYLE.INVALID;
        this.messageService.add({
          key: 'odfSave',
          sticky: true,
          severity: 'error',
          summary: this.translation.translate('odf.validate.input').concat(this.translation.translate('odf.odfIndex'))
        });
      }
    }
    if (this.formAdd.value.deptId === null || this.formAdd.value.deptId === '' || this.formAdd.value.deptId === undefined) {
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

    if (this.formAdd.value.odfTypeId === null || this.formAdd.value.odfTypeId === '') {
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
    if (this.formAdd.value.ownerId === null || this.formAdd.value.ownerId === '') {
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
    this.styleStation = VALIDATE_STYLE.VALID;
    this.styleOdfIndex = VALIDATE_STYLE.VALID;
    this.styleOdfType = VALIDATE_STYLE.VALID;
    this.styleOwner = VALIDATE_STYLE.VALID;
    this.styleODFCode = VALIDATE_STYLE.VALID;
  }

  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    // this.convertDataRequest();
    // console.log(this.dataRequest);
    this.getFormValidationErrors(() => {
      this.convertDataRequest();
      this.odfService.saveOdf(this.dataRequest).subscribe(res => {

        // let ok = true;
        let isSuccess = 'success';
        let toarstMess = this.translation.translate('odf.save.success');
        let key = 'odfSave';
        let summary = '';
        // console.log(res);
        // console.log(res.status);

        switch (res.status) {
          case '200':
            isSuccess = 'success';
            toarstMess = (this.action === 'edit') ? this.translation.translate('odf.update.success')
              : this.translation.translate('odf.save.success');
            summary = this.translation.translate('common.label.NOTIFICATIONS');
            key = 'odfSave';
            break;
          case '409':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.update.failed.coupler.conflict');
            key = 'odfSave';
            break;
          case '406':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.update.failed.coupler.notAccept');
            key = 'odfSave';
            break;
          case '428':
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.save.failed.system');
            key = 'odfSave';
            break;
          default:
            // console.log(isSuccess);
            isSuccess = 'error';
            toarstMess = this.translation.translate('odf.save.failed.system');
            key = 'odfSave';
        }
        this.messageService.add({
          severity: isSuccess,
          key: key,
          summary: summary,
          detail: toarstMess
        });
        this.isTabChanged = null;
        if (isSuccess === 'success') {

          this.odfService.listAction = 'reload';
          if (this.action === 'edit') {
            this.odfService.saveOrEdit = 'edit';
            this.tabLayoutService.close({
              component: ODFSaveComponent.name,
              tabId: (this.odfId * 121),
              action: 'edit'
            });
          } else {
            this.odfService.saveOrEdit = 'save';
            this.tabLayoutService.close({
              component: ODFSaveComponent.name,
              header: ''
            });
          }
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

        }

      }, error => {
        this.messageService.add({
          severity: 'error',
          key: 'br',
          sticky: true,
          summary: this.translation.translate('odf.save.failed.system'),
        });
      });
    });
  }

  convertDataRequest() {
    // console.log(this.formAdd.value);
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
    this.permissionService.findStation({ stationCode: event.query, first: 0, rows: 10 }).subscribe(result => {
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
          if (this.action === 'view') {
            this.formAdd.value.stationId = item.label;
            this.formAdd.patchValue({ stationId: item.label });
          }
          if (this.action === 'edit') {
            this.formAdd.patchValue({ stationId: item });
            this.formAdd.value.stationId = item.value;
          }
        }
      });
    });
  }

  onclear(data) {
    this.formAdd.controls[data].setValue('');
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
          if (this.action === 'view') {
            this.formAdd.value.deptId = item.label;
            this.formAdd.patchValue({ deptId: item.label });
          }
          if (this.action === 'edit') {
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

  onChangeRowSelectStation(event: any) {
    // console.log(event);
    this.stationId = event.stationId;
    this.odfCodeFirst = event.stationCode;
    this.styleStation = VALIDATE_STYLE.VALID;
    this.styleOdfIndex = VALIDATE_STYLE.VALID;
    this.styleOwner = VALIDATE_STYLE.VALID;
    // this.styleOwner = '';
    this.formAdd.patchValue({
      ownerId: event.ownerId
    });
    this.odfService.getMaxOdfByStationId(event.stationId).subscribe(res => {
      // console.log(res);
      let odfIndex = res.data.odfIndex;
      if (odfIndex >= 99) {
        this.messageService.clear('deleteSuccess');
        this.styleODFCode = VALIDATE_STYLE.INVALID;
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

  onSelectStation(event) {
    // console.log(event);
    this.odfCodeFirst = event.stationCode;
    // console.log(this.odfCodeFirst);
    this.formAdd.patchValue({
      ownerId: event.ownerId
    });

    this.convertDataRequest();
    this.formAdd.value.ownerId = event.ownerId;
    // console.log(event.ownerId);
    this.odfService.getMaxOdfByStationId(event.stationId).subscribe(res => {
      let odfIndex = res.data.odfIndex;
      if (odfIndex >= 99) {
        this.messageService.clear('deleteSuccess');
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

    let temp = this.formAdd.value.odfIndex;
    if (parseInt(temp) === 0) {
      temp = '01';
    }
    if (temp.length < 2 && temp !== '') {
      temp = temp + '0';
    }
    this.formAdd.controls['odfIndex'].setValue(temp);
    this.odfCodeLast = temp;
    this.styleOdfIndex = VALIDATE_STYLE.VALID;

    this.setOdfIndex();

  }

  setOdfIndex() {
    this.odfCode = `${this.odfCodeFirst}-ODF-${this.odfCodeLast}`;
    if (this.odfCodeLast === '') {
      this.odfCode = '';
    }

    this.formAdd.controls['odfCode'].setValue(this.odfCode);
  }

  onClosedTab() {
    console.log(this.isTabChanged);
    if (this.isTabChanged === 'INVALID') {
      this.tabLayoutService.isTabContentHasChanged({ component: ODFSaveComponent.name, key: ODFSaveComponent.name });
      // this.tabLayoutComponent.openConfirm();
      if (this.action === 'edit') {
        this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab('ODFEditComponent'));
      } else {
        this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(ODFSaveComponent.name));
      }

    } else {
      this.odfService.listAction = 'reload';
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
      if (this.action === 'edit') {
        this.tabLayoutService.close({
          component: 'ODFEditComponent',
          action: 'edit',
          tabId: this.odfId * 121
        });
      } else {
        this.tabLayoutService.close({
          component: ODFSaveComponent.name,
          header: ''
        });
      }

      // this.eventBusService.emit({
      //   type: 'odfList',
      // });
    }
  }

  onClearRowSelect(event) {
    this.formAdd.controls[event].setValue('');
    // console.log(event);
    if (event === 'stationId') {
      this.odfCode = '';
      this.formAdd.controls['odfCode'].setValue('');
      this.formAdd.controls['odfIndex'].setValue('');
      this.formAdd.patchValue({
        ownerId: null
      });
      // console.log(3);
    }
    // console.log(4);
  }

  onConfirm(item) {
    this.messageService.clear('c');
    this.tabLayoutService.close({
      component: ODFSaveComponent.name,
      header: ''
    });
  }

  onReject() {
    this.messageService.clear('c');
  }

  onBlurFocus(event) {
    // setTimeout(() => this.inputEl.nativeElement.focus());
  }
  onBlurStation() {
    console.log(1);
    this.odfCode = '';
    this.formAdd.controls['odfCode'].setValue('');
    this.formAdd.controls['odfIndex'].setValue('');
    // console.log(event);
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
