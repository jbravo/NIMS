import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  Type,
  ViewChild,
  HostListener
} from '@angular/core';
import { BreadcrumbService } from '@app/shared/services/breadcrumb.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators
} from '@angular/forms';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { ValidationService } from '@app/shared/services';
import { AppComponent } from '@app/app.component';
import { MessageService } from 'primeng/api';
import { TranslationService } from 'angular-l10n';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { CABLE_STATUS } from '@app/shared/services/constants';
import { PermissionService } from '@app/shared/services/permission.service';
import { ActivatedRoute } from '@angular/router';
import { TabLayoutService, TabLayoutComponent } from '@app/layouts/tab-layout';
import { Subscription } from 'rxjs';
import { CableService } from '../../service/cable.service';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { AutocompleteSearchStationModalComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-station-modal/autocomplete-search-station-modal.component';
import { AutocompleteSearchControlComponent } from '@app/shared/components/autocomplete-search-control/autocomplete-search-control.component';
import { StationService } from '@app/modules/stations-management/service/station.service';

@Component({
  selector: 'cable-save',
  templateUrl: './cable-save.component.html',
  styleUrls: ['./cable-save.component.css']
})
export class CableSaveComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('autocompleteStation')
  autocompleteStation: AutocompleteSearchControlComponent;

  formAdd: FormGroup;
  statusCheck = false;
  statusList: any[] = [];
  filteredStation: any[];
  listODFFist: any = new Array();
  listODFEnd: any = new Array();
  listCable: any = new Array();
  listStatus: any[];
  filteredStationSingle: any[];
  station: Type<any> = AutocompleteSearchStationModalComponent;
  isStationCode = false;
  // station: any;
  stationId: number;
  validateFloat: RegExp = /^[0-9]+(\.[0-9]+)?$/;
  private contentHasChangedSub: Subscription;
  private mapValueObjSub: Subscription;
  viewData: any;
  isTabChanged: string;
  headerStation = this.app.translation.translate(
    'common.dialog.header.station'
  );
  private _genKey: string = '_';
  action: any;
  tabId: number;
  idCableLocal: any;
  isEditStation = false;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private fb: FormBuilder,
    private app: AppComponent,
    private cableService: CableService,
    private translation: TranslationService,
    private dataCommon: DataCommonService,
    private permissionService: PermissionService,
    private messageService: MessageService,
    private activatedRoute: ActivatedRoute,
    private tabLayoutService: TabLayoutService,
    private  eventBusService: EventBusService,
    private stationService: StationService,
    private tabLayoutComponent: TabLayoutComponent
  ) {
    this.buildForm();
    this.mapValueObjSub = this.eventBusService.coordinatesChange.subscribe(
      val => {
        if (val && val['value']) {
          this.formAdd.patchValue({
            latitude: val['value'].lat,
            longitude: val['value'].lng
          });
        }
      }
    );
  }

  ngOnInit() {
    this.action = this.cableService.action;
    this.tabId = this.cableService.id;
    this.listODFFist = [];
    this.listODFFist.push({
      label: this.translation.translate('common.label.cboSelect'),
      value: null
    });
    this.listODFEnd = [];
    this.listODFEnd.push({
      label: this.translation.translate('common.label.cboSelect'),
      value: null
    });
    if (this.action === 'edit' || this.action === 'view') {
      this.formAdd.value.cableId = this.cableService.id;
      this.cableService.findCableById(this.cableService.id).subscribe(res => {
        this.isEditStation = true;
        let object = res.content;
        this.viewData = res.content;
        if (object) {
          object.installationDate = CommonUtils.stringToDate(
            object.installationDate,
            'yyyy/MM/dd'
          );
          object.length = this.cableService.formatFloatLeng(object.length);
          // lay danh sach nha tram
          this.stationService
            .findStationById(+object.stationId)
            .subscribe(r => {
              this.formAdd.patchValue({ _stationId: r.content });
              this.autocompleteStation.displayField = {
                stationCode: r.content.stationCode
              };
              this.isEditStation = false;
            });

          // lay ODF dau
          this.getODFFirst({ stationId: object.stationId });
          // lay ODF cuoi
          this.getODFEnd({ stationId: object.stationId });

          let a = object.cableCode.slice(-2);

          if (this.action === 'view') {
            this.statusList.forEach(item => {
              if (item.value === object.status) {
                object.status = item.label;
              }
            });
          }
          this.formAdd.reset(object);
          this.formAdd.controls['cableIndex'].setValue(a);
          // this.isEditStation = false;
        }
      });
    }

    // lay danh loai cap
    this.getCableTypeCode();
    // list trang thai
    this.statusList = this.dataCommon.getDropDownListNoSelected(CABLE_STATUS);
    //   // });
  }

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
    if (this.mapValueObjSub) {
      this.mapValueObjSub.unsubscribe();
    }
  }

  ngAfterViewInit(): void {
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      if (!this.isEditStation) {
      this.isTabChanged = 'isTabChanged';
      const action = this.action ? this._genKey + this.action : '';
      const tabId = this.tabId ? this._genKey + this.tabId : '';
      const key = CableSaveComponent.name + action + tabId;
      this.tabLayoutService.isTabContentHasChanged({
        component: CableSaveComponent.name,
        key
      });
    }
    });

  }

  get formControls() {
    return this.formAdd.controls;
  }

  buildForm() {
    this.formAdd = this.fb.group({
      _stationId: [],
      cableId: [],
      stationId: [
        '',
        Validators.compose([
          Validators.required,
          Validators.maxLength(19),
          CommonUtils.customValidateReturnLabel('station.stationCode')
        ])
      ],
      sourceId: [
        null,
        Validators.compose([
          Validators.required,
          Validators.maxLength(19),
          CommonUtils.customValidateReturnLabel('cable.ODFfist')
        ])
      ],
      destId: [
        null,
        Validators.compose([
          Validators.required,
          Validators.maxLength(19),
          CommonUtils.customValidateReturnLabel('cable.ODFend')
        ])
      ],
      cableIndex: [''],
      cableCode: [''],
      cableTypeId: [
        '',
        Validators.compose([
          Validators.required,
          Validators.maxLength(2),
          CommonUtils.customValidateReturnLabel('cable.cableTypeCode')
        ])
      ],
      length: [
        '',
        Validators.compose([
          Validators.required,
          ValidationService.positiveNumber,
          CommonUtils.customValidateReturnLabel('cable.cableLength')
        ])
      ],
      status: [
        0,
        Validators.compose([
          Validators.required,
          CommonUtils.customValidateReturnLabel('cable.status')
        ])
      ],
      installationDate: [new Date()],
      note: ['', Validators.maxLength(500)],
      createTime: [null],
      rowStatus: [null]
    });
  }

  get f() {
    return this.formAdd.controls;
  }

  onClearDatePicker() {
    this.formAdd.controls['installationDate'].setValue(null);
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formAdd.controls['installationDate'].setValue(null);
      }
    }
  }

  // validate form va show message
  getFormValidationErrors(success: () => void) {
    if (CommonUtils.getFormValidationErrors(this.formAdd, this.app,null, 'messCable')) {
      success();
    }
  }

  // lay gia tri cua dropdown roi set vao form
  setSelectedValue(event, element: string) {
    if (event.value != null && event.value !== '') {
      this.formAdd.controls[element].setValue(event.value.value);
    } else {
      this.formAdd.controls[element].setValue('');
    }
  }

  // Ham luu lai
  onSubmit() {
    this.statusCheck = true;
    this.messageService.clear();
    this.formAdd.patchValue({
      stationId: this.formAdd.value._stationId
        ? this.formAdd.value._stationId.stationId
        : null
    });
    this.getFormValidationErrors(() => {
      if (this.formAdd.value.sourceId === this.formAdd.value.destId) {
        this.messageService.add({
          key: 'checkOdfExisted',
          severity: 'warn',
          summary: this.translation.translate('common.label.NOTIFICATIONS'),
          detail: this.translation.translate('cable.equal.odf')
        });
        // setTimeout(() => {
        //   this.messageService.clear('euqal');
        // }, 3000);
      } else {
        if (this.formAdd.value.cableIndex > 0) {
          if (this.validateFloat.test(this.formAdd.value.length) != true) {
            this.messageService.add({
              key: 'messCable',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('cable.error.lengthError')
            });
          } else {
            // cat length
            let index = this.formAdd.value.length.indexOf('.');
            if (index >= 0) {
              if (this.formAdd.value.length.length > index + 4) {
                this.formAdd.value.length = this.formAdd.value.length.slice(
                  0,
                  index + 4
                );
              } else {
                this.formAdd.value.length = this.formAdd.value.length.slice(
                  0,
                  this.formAdd.value.length.length
                );
              }
            }
            // this.formAdd.value.length = this.cutLongLength(
            //   this.formAdd.value.length
            // );
            this.isExitCode(() => {
              this.cableService.saveCable(this.formAdd.value).subscribe(
                res => {
                  if (res.status === '200') {
                    this.messageService.add({
                      key: 'success',
                      severity: 'success',
                      summary: this.translation.translate(
                        'common.label.NOTIFICATIONS'
                      ),
                      detail:
                        this.action === 'edit'
                          ? this.translation.translate('cable.update.success')
                          : this.translation.translate('cable.add.success')
                    });
                    setTimeout(() => {
                      this.onClosed();
                    }, 1000);
                  }
                  if (
                    status === '404' ||
                    status === '500' ||
                    status === '400'
                  ) {
                    this.app.messageProcess(res.status, res.content);
                  }
                },
                error => {
                  console.log(error);
                }
              );
            });
          }
        } else {
          this.messageService.add({
            key: 'checkOdfExisted',
            severity: 'error',
            summary: this.translation.translate('common.label.NOTIFICATIONS'),
            detail: this.translation.translate(
              'common.message.error.system.save'
            )
          });
        }
      }
    });
  }

  // check ton tai cablecode
  isExitCode(success: () => void) {
    if (typeof this.action === 'undefined') {
      this.cableService
        .isExitCode({ cableCode: this.formAdd.value.cableCode })
        .subscribe(res => {
          let isExit = res.content;
          if (!isExit) {
            success();
          } else {
            this.messageService.add({
              key: 'messCable',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate(
                'cable.title.isExitCableCode',
                { cableCode: this.formAdd.value.cableCode }
              )
            });
            setTimeout(() => {
              this.messageService.clear('euqal');
            }, 3000);
          }
        });
    } else {
      success();
    }
  }

  onReject() {
    this.messageService.clear('success');
    this.messageService.clear();
  }

  checkWarning(e: FormControl) {
    if (this.statusCheck) {
      if (!e.valid) {
        return true;
      }
    }
    return false;
  }

  onClosed() {
    this.eventBusService.emit({
      type: 'cableList',
    });
    this.tabLayoutService.close({
      component: CableSaveComponent.name,
      header: '',
      action: this.action,
      tabId: this.tabId
    });
    this.cableService.openCableListTab();
  }

  onClosedTab() {
    const action = this.action ? this._genKey + this.action : '';
    const tabId = this.tabId ? this._genKey + this.tabId : '';
    const key = CableSaveComponent.name + action + tabId;
    if (this.isTabChanged === 'isTabChanged') {
      this.tabLayoutService.isTabContentHasChanged({
        component: CableSaveComponent.name,
        key
      });
      const parent = {
        component: 'CableListComponent',
        header: 'cable.manage.label',
        breadcrumb: [
          { label: this.translation.translate('cable.manage.label') }
        ]
      };
      this.tabLayoutComponent.befClose(
        this.tabLayoutService.currentTab(key),
        parent
      );
    } else {
      this.tabLayoutService.close({
        component: CableSaveComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId
      });
      this.cableService.openCableListTab();
    }
  }

  onBlurInput(data) {
    if (data === 'stationId' && this.isStationCode === false) {
      this.formAdd.controls['stationId'].setValue('');
    }
  }

  onChangeRowSelectStation(event: any) {
    this.stationId = event.stationId;
    this.isStationCode = true;
    this.getODFFirst({ stationId: event.stationId });
    this.getODFEnd({ stationId: event.stationId });
  }

  onClearRowSelect() {
    this.formAdd.controls['_stationId'].setValue(null);

    this.listODFFist = [];
    this.listODFEnd = [];

    this.isStationCode = false;
  }

  // lay list ma loai cap
  getCableTypeCode() {
    this.cableService.findListCableType().subscribe(res => {
      this.listCable = [];
      this.listCable.push({
        label: this.translation.translate('common.label.cboSelect'),
        value: null
      });
      res.content.listData.forEach(element => {
        this.listCable.push({ label: element[1], value: element[0] });
      });

      if (this.action === 'view') {
        this.listCable.forEach(item => {
          if (item.value === this.formAdd.value.cableTypeCode) {
            this.formAdd.value.cableTypeCode = item.label;
            this.formAdd.patchValue({ cableTypeCode: item.label });
          }
        });
      }
    });
  }

  getODFFirst(event) {
    this.cableService
      .findListODFFist({ stationId: event.stationId })
      .subscribe(res => {
        this.listODFFist = [];
        this.listODFFist.push({
          label: this.translation.translate('common.label.cboSelect'),
          value: null
        });
        res.content.listData.forEach(element => {
          this.listODFFist.push({ label: element[1], value: element[0] });
        });
      });
    this.listODFFist.forEach(item => {
      if (item.value === this.formAdd.value.sourceId) {
        if (this.action === 'view') {
          this.formAdd.value.sourceId = item.value;
          this.formAdd.patchValue({ sourceId: item.value });
        }
        if (this.action === 'edit') {
          this.formAdd.value.sourceId = item.value;
          this.formAdd.patchValue({ sourceId: item.value });
        }
      }
    });
  }

  getODFEnd(event) {
    this.cableService
      .findListODFEnd({ stationId: event.stationId })
      .subscribe(res => {
        this.listODFEnd = [];
        this.listODFEnd.push({
          label: this.translation.translate('common.label.cboSelect'),
          value: null
        });
        res.content.listData.forEach(element => {
          this.listODFEnd.push({ label: element[1], value: element[0] });
        });
      });

    this.listODFEnd.forEach(item => {
      if (item.value === this.formAdd.value.destId) {
        if (this.action === 'view') {
          this.formAdd.value.destId = item.value;
          this.formAdd.patchValue({ destId: item.value });
        }
        if (this.action === 'edit') {
          this.formAdd.value.destId = item.value;
          this.formAdd.patchValue({ destId: item.value });
        }
      }
    });
  }

  onChangeODF(event) {
    this.formAdd.value.cableCode = '';
    if (this.formAdd.value.sourceId && this.formAdd.value.destId) {
      let sourceList = this.listODFFist.filter(x => {
        return x.value === this.formAdd.value.sourceId;
      });
      let sourceCode = sourceList[0].label;
      let destList = this.listODFEnd.filter(x => {
        return x.value === this.formAdd.value.destId;
      });
      let destCode = destList[0].label;
      this.cableService
        .getCableIndex({
          sourceId: this.formAdd.value.sourceId,
          destId: this.formAdd.value.destId
        })
        .subscribe(res => {
          console.log(res);
          let temp = sourceCode + '-' + destCode + '/';
          if (res.content < 100) {
            this.formAdd.patchValue({ cableCode: temp + res.content });
            this.formAdd.patchValue({ cableIndex: res.content });
          } else {
            this.formAdd.patchValue({ cableCode: temp });
            this.formAdd.patchValue({ cableIndex: null });
            this.messageService.add({
              key: 'messCable',
              severity: 'warn',
              summary: '',
              detail: this.translation.translate('cable.error.cableIndex')
            });
            // setTimeout(() => {
            //   this.messageService.clear('euqal');
            // }, 2000);
          }
        });
    }
  }

  onChangeCableIndex() {
    if (!this.action) {
      if (this.formAdd.value.cableIndex.length === 1) {
        let index = this.formAdd.value.cableIndex + '0';
        this.formAdd.patchValue({ cableIndex: index });
      }
      if (this.formAdd.value.sourceId && this.formAdd.value.destId) {
        let sourceList = this.listODFFist.filter(x => {
          return x.value === this.formAdd.value.sourceId;
        });
        let sourceCode = sourceList[0].label;
        let destList = this.listODFEnd.filter(x => {
          return x.value === this.formAdd.value.destId;
        });

        let destCode = destList[0].label;
        let cableIndex = this.formAdd.value.cableIndex;
        let temp = sourceCode + '-' + destCode + '/' + cableIndex;
        this.formAdd.patchValue({ cableCode: temp });
      }
    }
  }

  // Cap nhat START
  update(id?: number, action?: string) {
    this.cableService.id = this.tabId;
    this.cableService.action = 'edit';
    this.tabLayoutService.open({
      component: CableSaveComponent.name,
      header: this.tabId ? 'cable.update.label' : 'cable.create.label',
      breadcrumb: [
        { label: this.translation.translate('cable.manage.label') },
        { label: this.translation.translate(this.tabId ? 'cable.update.label' : 'cable.create.label') },
      ],
      action: this.tabId ? 'edit' : '',
      tabId: this.tabId,
    });
  }


  // @HostListener('document:keydown', ['$event'])
  // handleKeyboardEvent(event: KeyboardEvent): void {
  //   if (event.keyCode=== 13) {
  //     this.eventBusService.dataChange.subscribe(val => {
  //       debugger
  //       console.log(val);
  //       if (val) {
  //         if (val.data.component === CableSaveComponent.name && (!this.tabId || (val.data.tabId && val.data.tabId === this.tabId) )) {
  //         // if (val.data.key === CableSaveComponent.name) {
  //           this.onSubmit();
  //         }
  //       }

  //     }).unsubscribe();
  //   }
  // }
}
