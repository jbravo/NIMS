import { Component, OnInit, ElementRef, ViewChild, HostListener, AfterViewInit, OnDestroy } from '@angular/core';
import { WeldingOdfService } from '../../service/welding-odf.service';
import { TranslationService } from 'angular-l10n';
import { TabLayoutService, TabLayoutComponent } from '@app/layouts/tab-layout';
import { MessageService } from 'primeng/api';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { CommonUtils } from '@app/shared/services';
import { Subscription } from 'rxjs';
import { WeldingOdfListComponent } from '../welding-odf-list/welding-odf-list.component';
import { EventBusService } from '@app/shared/services/event-bus.service';

@Component({
  selector: 'welding-odf-update',
  templateUrl: './welding-odf-update.component.html',
  styleUrls: ['./welding-odf-update.component.css']
})
export class WeldingOdfUpdateComponent implements OnInit, AfterViewInit, OnDestroy {
  isTabChanged: string;
  item: any;
  couplerList: any[] = [];
  sourceCouplerList: any[] = [];
  destCouplerList: any[] = [];
  linesList: any[] = [];
  odfConnectType: number;
  odfId: number;
  cableId: number;
  destOdfId: number;
  couplerNo: number;
  lineNo: number;
  destCouplerNo: number;
  createUser: string;
  createDate: string;
  note: string;
  attenuation: any;
  formUpdate: FormGroup;
  statusCheck = false;
  selectedWelding = false;
  selectedJointing = false;
  private _contentHasChangedSub: Subscription;
  private _genKey = '_';
  action: string;
  tabId: number;

  constructor(
    private weldingOdfService: WeldingOdfService,
    private translation: TranslationService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private fb: FormBuilder,
    private tabLayoutComponent: TabLayoutComponent,
    private eventBusService: EventBusService) {
    this.item = this.weldingOdfService.item;
    this.buildForm();
  }

  ngOnInit() {
    this.tabId = Number.parseFloat(this.item.odfId + '.' + this.item.couplerNo);
    this.action = this.weldingOdfService.action;
    if (this.item) {
      this.odfConnectType = (this.item.odfConnectType === this.translation.translate('welding.odf.weld')) ? 1 : 2;
      this.formUpdate.controls['odfConnectType'].setValue(this.odfConnectType);
      if (this.odfConnectType === 1) { // Hàn nối
        this.selectedWelding = true;
        this.formUpdate.controls['pairNumberOption'].setValue(1);
      } else if (this.odfConnectType === 2) { // Đấu nối
        this.selectedJointing = true;
        this.formUpdate.controls['pairNumberOption'].setValue(1);
      }
    }
  }

  ngAfterViewInit(): void {
    this._contentHasChangedSub = this.formUpdate.statusChanges.subscribe(val => {
      this.isTabChanged = val;
      const action = this.action ? this._genKey + this.action : '';
      const tabId = this.tabId ? this._genKey + this.tabId : '';
      const key = WeldingOdfUpdateComponent.name + action + tabId;
      this.tabLayoutService.isTabContentHasChanged({ component: WeldingOdfUpdateComponent.name, key });
    });
  }

  ngOnDestroy(): void {
    if (this._contentHasChangedSub) {
      this._contentHasChangedSub.unsubscribe();
    }
  }

  buildForm() {
    this.formUpdate = this.fb.group({
      odfConnectType: [this.odfConnectType],
      odfId: [this.item.odfId],
      cableId: [this.item.cableId],
      destOdfId: [this.item.destOdfId],
      couplerNo: [this.item.couplerNo],
      lineNo: [this.item.lineNo],
      destCouplerNo: [this.item.destCouplerNo],
      createUser: [this.item.createUser],
      createDate: [this.item.createDate],
      note: [this.item.note],
      attenuation: [this.item.attenuation],
      pairNumberOption: [1]
    });
  }

  passData(data) {
    data = this.formUpdate.value;
    data.createDate = data.createDate !== null ? ((typeof data.createDate === 'string')
      ? data.createDate : CommonUtils.dateToString(data.createDate)) : null;
    const obj = {
      odfId: Number, couplerNo: Number, createUser: String, note: String,
      attenuation: String, createDate: String, odfConnectType: Number,
    };
    obj.odfId = data.odfId;
    obj.couplerNo = data.couplerNo;
    obj.odfConnectType = data.odfConnectType;
    obj.attenuation = data.attenuation;
    obj.createUser = data.createUser;
    obj.createDate = data.createDate;
    obj.note = data.note;
    return obj;
  }

  onSubmit(data) {
    data = this.formUpdate.value;
    this.statusCheck = true;
    this.messageService.clear();
    const dataSubmit = this.passData(data);
    this.weldingOdfService.saveUpdateWeldingOdf(dataSubmit).subscribe(res => {
      if (res.data.code === 1) {
        this.messageService.add({
          key: 'suc',
          severity: 'success',
          summary: this.translation.translate(data.odfConnectType === 1
            ? 'message.success.updated.weld.success'
            : 'message.success.updated.joint.success'),
        });
        setTimeout(() => {
          this.tabLayoutService.open({
            component: 'ODFViewComponent',
            header: 'odf.tab.detail',
            tabId: (this.odfId * 121),
            breadcrumb: [
              { label: this.translation.translate('odf.tab.list') },
              { label: this.translation.translate('odf.tab.detail') },
              { label: this.translation.translate('odf.info.weldingOdf') }
            ]
          });
          this.eventBusService.emitDataChange({
            type: 'weldingOdfUpdate',
            data: 'reload'
          });
          this.onClosed();
        }, 1000);
      } else {
        this.messageService.add({
          key: 'err',
          severity: 'error',
          summary: this.translation.translate('message.error.system.error'),
        });
      }
    });
  }

  // @HostListener('document:keydown', ['$event'])
  // enterToSubmit(event: KeyboardEvent): void {
  //   if (event.code === 'Enter') {
  //     this.onSubmit(this.formUpdate.value);
  //     event.stopPropagation();
  //   }
  // }

  onClosed() {
    // this.eventBusService.emit({
    //   type: 'weldingOdfList',
    // });
    // this.tabLayoutService.close({
    //   component: WeldingOdfListComponent.name,
    //   header: 'weldingOdf.tab',
    //   breadcrumb: [
    //     { label: this.translation.translate('weldingOdf.tab') }
    //   ]
    // });
    // this.tabLayoutService.open({
    //   component: WeldingOdfListComponent.name,
    //   header: 'weldingOdf.tab',
    //   breadcrumb: [
    //     { label: this.translation.translate('weldingOdf.tab') }
    //   ]
    // });
    this.tabLayoutService.close({
      component: WeldingOdfUpdateComponent.name,
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
    this.tabLayoutService.open({
      component: 'ODFViewComponent',
      header: 'odf.tab.detail',
      tabId: (this.item.odfId * 121),
      breadcrumb: [
        { label: this.translation.translate('odf.tab.list') },
        {
          label: this.translation.translate('odf.tab.detail')
        },
        {
          label: this.translation.translate('odf.info.weldingOdf')
        }
      ],
    });
    // console.log(this.item.odfId * 121);

  }

  setFormValue(event, element) {
    this.formUpdate.controls[element].setValue(event);
  }

  onClearDatePicker() {
    this.formUpdate.controls['createDate'].setValue(null);
  }

  checkWarning(e: FormControl) {
    if (this.statusCheck) {
      if (!e.valid) {
        return true;
      }
    }
    return false;
  }

  closeTabSave() {
    const action = this.action ? this._genKey + this.action : '';
    const tabId = this.tabId ? this._genKey + this.tabId : '';
    const key = WeldingOdfUpdateComponent.name + action + tabId;
    if (this.isTabChanged === 'VALID') {
      this.tabLayoutService.isTabContentHasChanged({ component: WeldingOdfUpdateComponent.name, key });
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(key));
    } else {
      this.tabLayoutService.close({
        component: WeldingOdfUpdateComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
    }
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        this.formUpdate.controls['createDate'].setValue(null);
      }
    }
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({ key: 'cp', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied') });
    }
  }
}
