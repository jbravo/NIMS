import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { WeldingOdfService } from '../../service/welding-odf.service';
import { TranslationService } from 'angular-l10n';
import { TabLayoutService, TabLayoutComponent } from '@app/layouts/tab-layout';
import { MessageService } from 'primeng/api';
import { CommonUtils } from '@app/shared/services';
import { Subscription } from 'rxjs';
import { WeldingOdfListComponent } from '../welding-odf-list/welding-odf-list.component';
import { EventBusService } from '@app/shared/services/event-bus.service';

@Component({
  selector: 'welding-odf-create',
  templateUrl: './welding-odf-create.component.html',
  styleUrls: ['./welding-odf-create.component.css']
})
export class WeldingOdfCreateComponent implements OnInit, OnDestroy, AfterViewInit {
  isTabChanged: string;
  cableList: any[] = [];
  couplerNos: any[] = [];
  sourceCouplerNos: any[];
  destCouplerNos: any[];
  cntSourceCouplers: any[];
  cntDestCouplers: any[];
  formAdd: FormGroup;
  odfConnectType: any = 1;
  odfId: any;
  odfCode: any;
  couplerList: any[] = [];
  sourceCouplerList: any[] = [];
  destCouplerList: any[] = [];
  couplerNo: number;
  sourceCouplerNo: any;
  sourceCouplerNoFrom: any;
  sourceCouplerNoTo: any;
  destOdfList: any[] = [];
  destOdfId: any;
  destCouplerNo: any;
  couplerNoFrom: any;
  destCouplerNoFrom: any;
  couplerNoTo: any;
  destCouplerNoTo: any;
  cableId: any;
  cableCode: any;
  linesList: any[] = [];
  lineNos: any[];
  lineNo: any;
  lineNoFrom: any;
  lineNoTo: any;
  attenuation: any;
  weldAttenuation: any;
  jointAttenuation: any;
  createUser: any;
  weldCreateUser: any;
  jointCreateUser: any;
  createDate: any;
  jointCreateDate: any;
  weldCreateDate: any;
  noteJoint: any;
  noteWeld: any;
  note: any;
  pairNumberOption = 1;
  selectedWelding = true; // chọn Hàn nối
  selectedJointing = false; // chọn Đấu nối
  aPairWelding = true; // hàn 1 cặp
  manyPairWelding = false; // hàn nhiều cặp
  aPairJointing = true; // đấu 1 cặp
  manyPairJointing = false; // đấu nhiều cặp
  private _contentHasChangedSub: Subscription;
  private _genKey = '_';
  action: string;
  tabId: number;
  statusCheck = false;
  errorCoupler = false;
  errorCouplerFr = false;
  errorCouplerTo = false;
  errorSourceCoupler = false;
  errorSourceCouplerFr = false;
  errorSourceCouplerTo = false;
  errorDestCoupler = false;
  errorDestCouplerFr = false;
  errorDestCouplerTo = false;
  errorDestOdf = false;
  errorCable = false;
  errorLine = false;
  errorLineFr = false;
  errorLineTo = false;
  isCableSelect = false;
  inputBinhStyle;

  constructor(
    private weldingOdfService: WeldingOdfService,
    private translation: TranslationService,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private tabLayoutComponent: TabLayoutComponent,
    private eventBusService: EventBusService,
    private fb: FormBuilder) {
    this.buildForm({});
  }

  ngOnInit() {
    this.tabId = this.weldingOdfService.id;
    this.action = this.weldingOdfService.action;
    this.odfId = this.weldingOdfService.id;
    this.formAdd.value.weldCreateDate = new Date();
    this.formAdd.value.jointCreateDate = new Date();
    this.weldingOdfService.getOdfCode(this.odfId).subscribe(res => {
      this.odfCode = res.content;
    });

    this.weldingOdfService.getCableCodes(this.odfId).subscribe(res => {
      for (let i = 0; i < res.content.length; i++) {
        this.cableList.push({ label: res.content[i].cableCode, value: res.content[i].cableId });
      }
    });
    this.weldingOdfService.getDestOdfCodes(this.odfId).subscribe(res => {
      for (let i = 0; i < res.content.length; i++) {
        this.destOdfList.push({ label: res.content[i].odfCode, value: res.content[i].odfId });
      }
    });
    this.weldingOdfService.getCouplers(this.odfId).subscribe(res => {
      // this.couplerList = res.content;
      for (let index = 0; index < res.content.length; index++) {
        this.couplerNos.push(res.content[index]);
        this.couplerList.push({ couplerNo: res.content[index] });
      }
    });
    this.weldingOdfService.getSourceCouplers(this.odfId).subscribe(res => {
      this.sourceCouplerList = res.content;
      this.sourceCouplerNos = [];
      for (let index = 0; index < res.content.length; index++) {
        if (res.content[index].couplerNo !== null) {
          this.sourceCouplerNos.push(res.content[index].couplerNo);
        }
      }
    });
    const frmOld = this.formAdd.value;
    this.formAdd.reset(frmOld);
  }

  ngAfterViewInit(): void {
    this._contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      this.isTabChanged = val;
      const action = this.action ? this._genKey + this.action : '';
      const tabId = this.tabId ? this._genKey + this.tabId : '';
      const key = WeldingOdfCreateComponent.name + action + tabId;
      this.tabLayoutService.isTabContentHasChanged({ component: WeldingOdfCreateComponent.name, key });
    });
  }

  ngOnDestroy(): void {
    if (this._contentHasChangedSub) {
      this._contentHasChangedSub.unsubscribe();
    }
  }

  getLines(event) {
    debugger
    console.log(event);
    this.cableId = event.value;
    this.formAdd.value.cableId = this.cableId;
    if (event.value === '') {
      this.linesList = [];
    } else if (this.cableId || this.cableId === '0') {
      this.cableList.forEach(element => {
        if (this.cableId === element.value) {
          this.cableCode = element.label;
        }
      });
      this.weldingOdfService.getLines(this.cableId).subscribe(res => {
        this.linesList = [];
        this.lineNos = [];
        this.isCableSelect = true;
        res.content.map(item => {
          this.lineNos.push(item);
          this.linesList.push({
            lineNo: item,
            quadNo: Math.ceil(item / 6),
            cableCode: this.cableCode
          });
        });
      });
    }
  }

  getDestCouplers() {
    this.destOdfId = this.formAdd.value.destOdfId;
    if (this.destOdfId) {
      this.weldingOdfService.getDestCouplers(this.destOdfId).subscribe(res => {
        this.destCouplerList = res.content;
        this.destCouplerNos = [];
        for (let i = 0; i < res.content.length; i++) {
          if (res.content[i].couplerNo !== null) {
            this.destCouplerNos.push(res.content[i].couplerNo);
          }
        }
      });
    }
  }

  checkType() {
    this.odfConnectType = this.formAdd.value.odfConnectType;
    if (this.odfConnectType === 1) { // Hàn nối
      this.selectedWelding = true;
      this.selectedJointing = false;
      this.aPairWelding = true;
    } else { // Đấu nối
      this.selectedJointing = true;
      this.selectedWelding = false;
      this.aPairJointing = true;
    }
    this.formAdd.controls['pairNumberOption'].setValue('1');
    this.checkPair();
  }

  checkPair() {
    this.odfConnectType = this.formAdd.value.odfConnectType;
    this.pairNumberOption = this.formAdd.value.pairNumberOption;
    if (this.odfConnectType === 1 && this.pairNumberOption === 1) { // Hàn 1 cặp
      this.aPairWelding = true;
      this.manyPairWelding = false;
      this.aPairJointing = false;
      this.manyPairJointing = false;
    } else if (this.odfConnectType === 1 && this.pairNumberOption === 2) { // Hàn nhiều cặp
      this.aPairWelding = false;
      this.manyPairWelding = true;
      this.aPairJointing = false;
      this.manyPairJointing = false;
    } else if (this.odfConnectType === 2 && this.pairNumberOption === 1) { // Đấu 1 cặp
      this.aPairWelding = false;
      this.manyPairWelding = false;
      this.aPairJointing = true;
      this.manyPairJointing = false;
    } else if (this.odfConnectType === 2 && this.pairNumberOption === 2) { // Đấu nhiều cặp
      this.aPairWelding = false;
      this.manyPairWelding = false;
      this.aPairJointing = false;
      this.manyPairJointing = true;
    }
  }

  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      odfConnectType: [1],
      pairNumberOption: [1],
      odfId: [this.weldingOdfService.id],
      cableId: [null],
      destOdfId: [null],
      couplerNo: [null],
      sourceCouplerNo: [null],
      sourceCouplerNoFrom: [null],
      sourceCouplerNoTo: [null],
      couplerNoFrom: [null],
      couplerNoTo: [null],
      lineNo: [null],
      lineNoFrom: [null],
      lineNoTo: [null],
      destCouplerNo: [null],
      destCouplerNoFrom: [null],
      destCouplerNoTo: [null],
      jointCreateUser: [''],
      weldCreateUser: [''],
      weldCreateDate: [''],
      jointCreateDate: [''],
      noteJoint: [''],
      noteWeld: [''],
      jointAttenuation: [''],
      weldAttenuation: ['']
    });
  }

  onClearDatePicker() {
    if (this.formAdd.value.odfConnectType === 1) {
      this.formAdd.controls['weldCreateDate'].setValue(null);
    } else {
      this.formAdd.controls['jointCreateDate'].setValue(null);
    }
  }

  onInput(event) {
    if (event) {
      if (event.currentTarget.value === '') {
        if (this.formAdd.value.odfConnectType === 1) {
          this.formAdd.controls['weldCreateDate'].setValue(null);
        } else {
          this.formAdd.controls['jointCreateDate'].setValue(null);
        }
      }
    }
  }

  passData(data) {
    data = this.formAdd.value;
    const obj = {
      odfConnectType: String,
      odfId: Number,
      cableId: Number,
      destOdfId: Number,
      createUser: String,
      attenuation: String,
      createDate: String,
      note: String,
      couplerNo: Number,
      lineNo: Number,
      couplerNoFrom: Number,
      couplerNoTo: Number,
      lineNoFrom: Number,
      lineNoTo: Number,
      destCouplerNo: Number,
      sourceCouplerNos: [],
      destCouplerNos: []
    };
    obj.odfId = data.odfId;
    if (this.selectedWelding) {
      obj.odfConnectType = data.odfConnectType;
      obj.cableId = data.cableId;
      obj.attenuation = data.weldAttenuation;
      obj.createUser = data.weldCreateUser;
      data.weldCreateDate = data.weldCreateDate !== null ? CommonUtils.dateToString(data.weldCreateDate) : null;
      obj.createDate = data.weldCreateDate;
      obj.note = data.noteWeld;
      if (this.aPairWelding) {
        obj.couplerNo = data.couplerNo;
        obj.lineNo = data.lineNo;
      } else if (this.manyPairWelding) {
        obj.couplerNoFrom = data.couplerNoFrom;
        obj.couplerNoTo = data.couplerNoTo;
        obj.lineNoFrom = data.lineNoFrom;
        obj.lineNoTo = data.lineNoTo;
      }
    } else if (this.selectedJointing) {
      obj.odfConnectType = data.odfConnectType;
      obj.destOdfId = data.destOdfId;
      obj.attenuation = data.jointAttenuation;
      obj.createUser = data.jointCreateUser;
      data.jointCreateDate = data.jointCreateDate !== null ? CommonUtils.dateToString(data.jointCreateDate) : null;
      obj.createDate = data.jointCreateDate;
      obj.note = data.noteJoint;
      if (this.aPairJointing) {
        obj.couplerNo = data.sourceCouplerNo;
        obj.destCouplerNo = data.destCouplerNo;
      } else if (this.manyPairJointing) {
        obj.sourceCouplerNos = this.cntSourceCouplers;
        obj.destCouplerNos = this.cntDestCouplers;
      }
    }
    return obj;
  }

  onSubmit(data) {
    debugger
    data = this.formAdd.value;
    this.statusCheck = true;
    this.messageService.clear();
    if (this.getFormValidationErrors(data)) {
      const dataSubmit = this.passData(data);
      this.weldingOdfService.saveCreateWeldingOdf(dataSubmit).subscribe(res => {
        if (res.status === 1) {
          this.messageService.add({
            key: 'suc',
            severity: 'success',
            summary: this.translation
              .translate(data.odfConnectType === 1 ? 'message.success.created.weld.success' : 'message.success.created.joint.success'),
          });
          setTimeout(() => {
            this.onClosed();
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
              type: 'weldingOdfCreate',
              data: 'reload'
            });
          }, 1000);
        } else {
          this.messageService.add({
            key: 'err',
            severity: 'error',
            summary: this.translation.translate('message.error.system.error'),
          });
        }
      }, error => {
        console.log(error);
      });
    }
  }

  // @HostListener('document:keydown', ['$event'])
  // enterToSubmit(event: KeyboardEvent): void {
  //   if (event.code=== 'Enter') {
  //     this.onSubmit(this.formAdd.value);
  //     event.stopPropagation();
  //   }
  // }

  getFormValidationErrors(data): boolean {
    debugger
    this.messageService.clear();
    let checkValidate = true;
    // when welding
    if (this.selectedWelding) {
      if (!data.cableId) {
        this.messageService.add({
          key: 'vld',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('messages.error.notSelect')
            .concat(this.translation.translate('weldingOdf.cableCode'))
        });
        this.errorCable = true;
        checkValidate = false;
      } else { this.errorCable = false; }
      // when weld a pair
      if (this.aPairWelding) {
        // check coupler empty
        if (!data.couplerNo && data.couplerNo !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty').concat(this.translation.translate('weldingOdf.sourceCoupler'))
          });
          if (!data.lineNo && data.lineNo !== '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('messages.error.notEmpty')
                .concat(this.translation.translate('weldingOdf.lineNo'))
            });
            this.errorLine = true;
          } else if ((data.lineNo === '0') || (data.lineNo && !this.lineNos.includes(Number.parseInt(data.lineNo)))) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('weldingOdf.lineNo')
                .concat(this.translation.translate('messages.error.outOfRange'))
            });
            this.errorLine = true;
          } else { this.errorLine = false; }
          this.errorCoupler = true;
          checkValidate = false;
        } else if ((data.couplerNo === '0') || (data.couplerNo && !this.couplerNos.includes(Number.parseInt(data.couplerNo)))) {
          // check coupler out of range
          if (!data.lineNo && data.lineNo !== '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('messages.error.notEmpty')
                .concat(this.translation.translate('weldingOdf.lineNo'))
            });
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('weldingOdf.sourceCoupler')
                .concat(this.translation.translate('messages.error.outOfRange'))
            });
            this.errorLine = true;
          } else if (data.lineNo === '0' || (data.lineNo && !this.lineNos.includes(Number.parseInt(data.lineNo)))) {
            this.messageService.add({ key: 'vld', severity: 'error', detail: this.translation.translate('messages.error.exist') });
            this.errorLine = true;
          } else if (data.lineNo && this.lineNos.includes(Number.parseInt(data.lineNo))) {
            this.errorLine = false;
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('weldingOdf.sourceCoupler')
                .concat(this.translation.translate('messages.error.outOfRange'))
            });
          }
          this.errorCoupler = true;
          checkValidate = false;
        } else if (data.couplerNo && this.couplerNos.includes(Number.parseInt(data.couplerNo))) {
          this.errorCoupler = false;
          // check coupler valid
          if (!data.lineNo && data.lineNo !== '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('messages.error.notEmpty')
                .concat(this.translation.translate('weldingOdf.lineNo'))
            });
            this.errorLine = true;
            checkValidate = false;
          } else if (data.lineNo === '0' || (data.lineNo && !this.lineNos.includes(Number.parseInt(data.lineNo)))) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('weldingOdf.lineNo')
                .concat(this.translation.translate('messages.error.outOfRange'))
            });
            this.errorLine = true;
            checkValidate = false;
          } else { this.errorLine = false; }
        }
      } else if (this.manyPairWelding) {
        // when weld many pair
        // check couplers empty
        if (!data.couplerNoFrom && data.couplerNoFrom !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.sourceCouplerFrom'))
          });
          this.errorCouplerFr = true;
          checkValidate = false;
        }
        if (!data.couplerNoTo && data.couplerNoTo !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.sourceCouplerTo'))
          });
          this.errorCouplerTo = true;
          checkValidate = false;
        }
        // check coupler From vs To inputted
        let cntCouplersValid = 0;
        if (data.couplerNoFrom === '0') {
          if (data.couplerNoTo === '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.couplerFromEqCouplerTo')
            });
          } else if (data.couplerNoTo >= 1) {
            const couplerArr = [];
            for (let i = 1; i <= data.couplerNoTo; i++) {
              if (!this.couplerNos.includes(i)) {
                couplerArr.push(i);
              }
            }
            const arr = couplerArr.join(', ');
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.couplerNo')
                .concat('0, ')
                .concat(arr)
                .concat(this.translation.translate('messages.error.outOfRangeOdf'))
            });
          }
          this.errorCouplerFr = true;
          this.errorCouplerTo = true;
          checkValidate = false;
        } else if (data.couplerNoFrom && data.couplerNoFrom >= 1) {
          if (data.couplerNoTo === '0' || (data.couplerNoTo && data.couplerNoTo >= 1 && data.couplerNoFrom > data.couplerNoTo)) {
            this.messageService.add(
              {
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.couplerFromGtCouplerTo')
              });
            this.errorCouplerFr = true;
            this.errorCouplerTo = true;
            checkValidate = false;
          } else if ((data.couplerNoTo && data.couplerNoTo >= 1 && data.couplerNoFrom === data.couplerNoTo)) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.couplerFromEqCouplerTo')
            });
            this.errorCouplerFr = true;
            this.errorCouplerTo = true;
            checkValidate = false;
          } else if ((data.couplerNoTo && data.couplerNoTo >= 1 && data.couplerNoFrom < data.couplerNoTo)) {
            const couplerArr = [];
            for (let i = Number.parseInt(data.couplerNoFrom); i <= Number.parseInt(data.couplerNoTo); i++) {
              if (!this.couplerNos.includes(i)) {
                couplerArr.push(i);
              } else if (this.couplerNos.includes(i)) {
                cntCouplersValid++;
              }
            }
            if (couplerArr.length !== 0) {
              const arr = couplerArr.join(', ');
              this.messageService.add({
                key: 'vld',
                severity: 'error', detail: this.translation.translate('messages.error.couplerNo').concat(arr)
                  .concat(this.translation.translate('messages.error.outOfRangeOdf'))
              });
              this.errorCouplerFr = true;
              this.errorCouplerTo = true;
              checkValidate = false;
            } else {
              this.errorCouplerFr = false;
              this.errorCouplerTo = false;
            }
          }
        }
        // check lines empty
        if (!data.lineNoFrom && data.lineNoFrom !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.lineNoFrom'))
          });
          this.errorLineFr = true;
          checkValidate = false;
        }
        if (!data.lineNoTo && data.lineNoTo !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            summary: '',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.lineNoTo'))
          });
          this.errorLineTo = true;
          checkValidate = false;
        }
        // check line From or To inputted
        let cntLinesValid = 0;
        if (data.lineNoFrom === '0') {
          if (data.lineNoTo === '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.lineFromEqLineTo')
            });
          } else if (data.lineNoTo >= 1) {
            const lineArr = [];
            for (let i = 1; i <= Number.parseInt(data.lineNoTo); i++) {
              if (!this.lineNos.includes(i)) {
                lineArr.push(i);
              }
            }
            const arr = lineArr.join(', ');
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.lineNo')
                .concat('0, ')
                .concat(arr)
                .concat(this.translation.translate('messages.error.outOfRangeOdf'))
            });
          }
          this.errorLineFr = true;
          this.errorLineTo = true;
          checkValidate = false;
        } else if (data.lineNoFrom && data.lineNoFrom >= 1) {
          if (data.lineNoTo === '0' || (data.lineNoTo && data.lineNoTo >= 1 && data.lineNoFrom > data.lineNoTo)) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.lineFromGtLineTo')
            });
            this.errorLineFr = true;
            this.errorLineTo = true;
            checkValidate = false;
          } else if ((data.lineNoTo && data.lineNoTo >= 1 && data.lineNoFrom === data.lineNoTo)) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.lineFromEqLineTo')
            });
            this.errorLineFr = true;
            this.errorLineTo = true;
            checkValidate = false;
          } else if ((data.lineNoTo && data.lineNoTo >= 1 && data.lineNoFrom < data.lineNoTo)) {
            const lineArr = [];
            for (let i = Number.parseInt(data.lineNoFrom); i <= Number.parseInt(data.lineNoTo); i++) {
              if (!this.lineNos.includes(i)) {
                lineArr.push(i);
              } else if (this.lineNos.includes(i)) {
                cntLinesValid++;
              }
            }
            if (lineArr.length !== 0) {
              const arr = lineArr.join(', ');
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.lineNo')
                  .concat(arr)
                  .concat(this.translation.translate('messages.error.outOfRangeCable'))
              });
              this.errorLineFr = true;
              this.errorLineTo = true;
              checkValidate = false;
            } else {
              this.errorLineFr = false;
              this.errorLineTo = false;
            }
          }
        }
        if (cntCouplersValid === (data.couplerNoTo - data.couplerNoFrom + 1) && cntLinesValid === (data.lineNoTo - data.lineNoFrom + 1)) {
          if (cntCouplersValid !== cntLinesValid) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.courplersLinesNotMatch')
            });
            this.errorCouplerFr = true;
            this.errorCouplerTo = true;
            this.errorLineFr = true;
            this.errorLineTo = true;
            checkValidate = false;
          } else {
            this.errorCouplerFr = false;
            this.errorCouplerTo = false;
            this.errorLineFr = false;
            this.errorLineTo = false;
          }
        }
      }
    } else if (this.selectedJointing) {
      // check dest ODF empty
      if (!data.destOdfId) {
        this.messageService.add({
          key: 'vld',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('messages.error.notSelect')
            .concat(this.translation.translate('weldingOdf.destCode'))
        });
        this.errorDestOdf = true;
        checkValidate = false;
      } else { this.errorDestOdf = false; }
      // when joint a pair
      if (this.aPairJointing) {
        // check Coupler vs Coupler dau nhay
        if (!data.destCouplerNo) {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.destCoupler'))
          });
          // this.errorSourceCoupler = true;
          // this.errorDestCoupler = true;
          // checkValidate = false;
        }
        if ((!data.sourceCouplerNo && data.sourceCouplerNo !== '0') || (!data.destCouplerNo && data.destCouplerNo !== '0')) {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.sourceCoupler'))
          });
          this.errorSourceCoupler = true;
          this.errorDestCoupler = true;
          checkValidate = false;
        } else if ((data.sourceCouplerNo === '0') || (data.destCouplerNo === '0')
          || (data.sourceCouplerNo && !this.sourceCouplerNos.includes(Number.parseInt(data.sourceCouplerNo)))
          || (data.destCouplerNo && !this.destCouplerNos.includes(Number.parseInt(data.destCouplerNo)))) {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('weldingOdf.sourceCoupler')
              .concat(this.translation.translate('messages.error.outOfRangeJoint'))
          });
          this.errorSourceCoupler = true;
          this.errorDestCoupler = true;
          checkValidate = false;
          // tslint:disable-next-line:max-line-length
        } else if (data.destOdfId === this.odfId && data.sourceCouplerNo && data.destCouplerNo && data.sourceCouplerNo === data.destCouplerNo) {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.twoCourplerConflicted')
          });
          this.errorSourceCoupler = true;
          this.errorDestCoupler = true;
          checkValidate = false;
        } else {
          this.errorSourceCoupler = false;
          this.errorDestCoupler = false;
        }
      } else if (this.manyPairJointing) {
        // when joint many pair
        // check Source Coupler empty
        if (!data.sourceCouplerNoFrom && data.sourceCouplerNoFrom !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.sourceCouplerFrom'))
          });
          this.errorSourceCouplerFr = true;
          checkValidate = false;
        }
        if (!data.sourceCouplerNoTo && data.sourceCouplerNoTo !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.sourceCouplerTo'))
          });
          this.errorSourceCouplerTo = true;
          checkValidate = false;
        }

        // check Dest Coupler empty
        if (!data.destCouplerNoFrom && data.destCouplerNoFrom !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.destCouplerFrom'))
          });
          this.errorDestCouplerFr = true;
          checkValidate = false;
        }
        if (!data.destCouplerNoTo && data.destCouplerNoTo !== '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.notEmpty')
              .concat(this.translation.translate('weldingOdf.destCouplerTo'))
          });
          this.errorDestCouplerTo = true;
          checkValidate = false;
        }

        if (Number.parseInt(data.sourceCouplerNoFrom) > 0 && data.sourceCouplerNoTo === '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.couplerFromGtCouplerTo')
          });
          this.errorSourceCouplerFr = true;
          this.errorSourceCouplerTo = true;
          checkValidate = false;
        }

        // tslint:disable-next-line:max-line-length
        if (data.sourceCouplerNoFrom && data.sourceCouplerNoTo && (Number.parseInt(data.sourceCouplerNoFrom) > Number.parseInt(data.sourceCouplerNoTo))) {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.couplerFromGtCouplerTo')
          });
          this.errorSourceCouplerFr = true;
          this.errorSourceCouplerTo = true;
          checkValidate = false;
        }

        if (Number.parseInt(data.destCouplerNoFrom) > 0 && data.destCouplerNoTo === '0') {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.destCouplerFromGtDestCouplerTo')
          });
          this.errorDestCouplerFr = true;
          this.errorDestCouplerTo = true;
          checkValidate = false;
        }

        // tslint:disable-next-line:max-line-length
        if (data.destCouplerNoFrom && data.destCouplerNoTo && (Number.parseInt(data.destCouplerNoFrom) > Number.parseInt(data.destCouplerNoTo))) {
          this.messageService.add({
            key: 'vld',
            severity: 'error',
            detail: this.translation.translate('messages.error.destCouplerFromGtDestCouplerTo')
          });
          this.errorDestCouplerFr = true;
          this.errorDestCouplerTo = true;
          checkValidate = false;
        }

        if (data.sourceCouplerNoFrom === '0' && data.sourceCouplerNoTo === '0') {
          if (data.destCouplerNoFrom === '0' && (data.destCouplerNoTo === '0' || Number.parseInt(data.destCouplerNoTo) > 0)) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.courplerNosNotMatch')
            });
            this.errorDestCouplerFr = true;
            this.errorDestCouplerTo = true;
            checkValidate = false;

            // tslint:disable-next-line:max-line-length
          } else if (data.destCouplerNoFrom && data.destCouplerNoTo && (Number.parseInt(data.destCouplerNoFrom) <= Number.parseInt(data.destCouplerNoTo))) {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.courplerNosNotMatch')
            });
            this.errorDestCouplerFr = false;
            this.errorDestCouplerTo = false;
            checkValidate = false;
          }
          this.errorSourceCouplerFr = true;
          this.errorSourceCouplerTo = true;
        } else if (data.sourceCouplerNoFrom === '0' && Number.parseInt(data.sourceCouplerNoTo) > 0) {
          this.cntSourceCouplers = [];
          for (let i = 1; i <= Number.parseInt(data.sourceCouplerNoTo); i++) {
            if (this.sourceCouplerNos.includes(i)) {
              this.cntSourceCouplers.push(i);
            }
          }
          if (data.destCouplerNoFrom === '0' && data.destCouplerNoTo === '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.courplerNosNotMatch')
            });
            this.errorDestCouplerFr = true;
            this.errorDestCouplerTo = true;
            checkValidate = false;
          } else if (data.destCouplerNoFrom === '0' && Number.parseInt(data.destCouplerNoTo) > 0 && this.destOdfId) {
            this.cntDestCouplers = [];
            for (let j = 1; j <= Number.parseInt(data.destCouplerNoTo); j++) {
              if (this.destCouplerNos.includes(j)) {
                this.cntDestCouplers.push(j);
              }
            }
            if (this.cntSourceCouplers.length !== this.cntDestCouplers.length) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplerNosNotMatch')
              });
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              this.errorSourceCouplerFr = true;
              this.errorSourceCouplerTo = true;
              checkValidate = false;
            } else if ((this.destOdfId === this.odfId) && (this.cntSourceCouplers.length > 0 && this.cntDestCouplers.length > 0)
              && (this.cntSourceCouplers[0] === this.cntDestCouplers[0])) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplersConflicted')
              });
              this.errorDestCouplerFr = true;
              this.errorSourceCouplerFr = true;
              this.errorDestCouplerTo = true;
              this.errorSourceCouplerTo = true;
              checkValidate = false;
            } else {
              this.errorDestCouplerFr = false;
              this.errorDestCouplerTo = false;
              this.errorCouplerFr = false;
              this.errorCouplerTo = false;
            }
          } else if (Number.parseInt(data.destCouplerNoFrom) > 0 && Number.parseInt(data.destCouplerNoTo) > 0
            && (Number.parseInt(data.destCouplerNoFrom) <= Number.parseInt(data.destCouplerNoTo)) && this.destOdfId) {
            this.cntDestCouplers = [];
            for (let j = Number.parseInt(data.destCouplerNoFrom); j <= Number.parseInt(data.destCouplerNoTo); j++) {
              if (this.destCouplerNos.includes(j)) {
                this.cntDestCouplers.push(j);
              }
            }
            if (this.cntSourceCouplers.length !== this.cntDestCouplers.length) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplerNosNotMatch')
              });
              this.errorCouplerFr = true;
              this.errorCouplerTo = true;
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              checkValidate = false;
            } else if ((this.destOdfId === this.odfId) && (this.cntSourceCouplers.length > 0 && this.cntDestCouplers.length > 0)
              && (this.cntSourceCouplers[0] === this.cntDestCouplers[0])) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplersConflicted')
              });
              this.errorCouplerFr = true;
              this.errorCouplerTo = true;
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              checkValidate = false;
            } else {
              this.errorDestCouplerFr = false;
              this.errorDestCouplerTo = false;
              this.errorCouplerFr = false;
              this.errorCouplerTo = false;
            }
          }
        } else if (Number.parseInt(data.sourceCouplerNoFrom) > 0 && Number.parseInt(data.sourceCouplerNoTo) > 0
          && Number.parseInt(data.sourceCouplerNoFrom) <= Number.parseInt(data.sourceCouplerNoTo)) {
          this.cntSourceCouplers = [];
          for (let i = Number.parseInt(data.sourceCouplerNoFrom); i <= Number.parseInt(data.sourceCouplerNoTo); i++) {
            if (this.sourceCouplerNos.includes(i)) {
              this.cntSourceCouplers.push(i);
            }
          }
          if (data.destCouplerNoFrom === '0' && data.destCouplerNoTo === '0') {
            this.messageService.add({
              key: 'vld',
              severity: 'error',
              detail: this.translation.translate('messages.error.courplerNosNotMatch')
            });
            this.errorDestCouplerFr = true;
            this.errorDestCouplerTo = true;
            this.errorSourceCouplerFr = false;
            this.errorSourceCouplerTo = false;
            checkValidate = false;
          } else if (data.destCouplerNoFrom === '0' && Number.parseInt(data.destCouplerNoTo) > 0 && this.destOdfId) {
            this.cntDestCouplers = [];
            for (let j = 1; j <= Number.parseInt(data.destCouplerNoTo); j++) {
              if (this.destCouplerNos.includes(j)) {
                this.cntDestCouplers.push(j);
              }
            }

            if (this.cntSourceCouplers.length !== this.cntDestCouplers.length) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplerNosNotMatch')
              });
              this.errorSourceCouplerFr = true;
              this.errorSourceCouplerTo = true;
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              checkValidate = false;
            } else if ((this.destOdfId === this.odfId) && (this.cntSourceCouplers.length > 0 && this.cntDestCouplers.length > 0)
              && (this.cntSourceCouplers[0] === this.cntDestCouplers[0])) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplersConflicted')
              });
              this.errorCouplerFr = true;
              this.errorCouplerTo = true;
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              checkValidate = false;
            } else {
              this.errorDestCouplerFr = false;
              this.errorDestCouplerTo = false;
              this.errorSourceCouplerFr = false;
              this.errorSourceCouplerTo = false;
            }
          } else if (Number.parseInt(data.destCouplerNoFrom) > 0 && Number.parseInt(data.destCouplerNoTo) > 0
            && (Number.parseInt(data.destCouplerNoFrom) <= Number.parseInt(data.destCouplerNoTo)) && this.destOdfId) {
            this.cntDestCouplers = [];
            for (let j = Number.parseInt(data.destCouplerNoFrom); j <= Number.parseInt(data.destCouplerNoTo); j++) {
              if (this.destCouplerNos.includes(j)) {
                this.cntDestCouplers.push(j);
              }
            }
            if (this.cntSourceCouplers.length !== this.cntDestCouplers.length) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplerNosNotMatch')
              });
              this.errorSourceCouplerFr = true;
              this.errorSourceCouplerTo = true;
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              checkValidate = false;
            } else if ((this.destOdfId === this.odfId) && (this.cntSourceCouplers.length > 0 && this.cntDestCouplers.length > 0)
              && (this.cntSourceCouplers[0] === this.cntDestCouplers[0])) {
              this.messageService.add({
                key: 'vld',
                severity: 'error',
                detail: this.translation.translate('messages.error.courplersConflicted')
              });
              this.errorSourceCouplerFr = true;
              this.errorSourceCouplerTo = true;
              this.errorDestCouplerFr = true;
              this.errorDestCouplerTo = true;
              checkValidate = false;
            } else {
              this.errorDestCouplerFr = false;
              this.errorDestCouplerTo = false;
              this.errorSourceCouplerFr = false;
              this.errorSourceCouplerTo = false;
            }
          }
        }
      }
    }
    return checkValidate;
  }

  setFormValue(event, element) {
    this.formAdd.controls[element].setValue(event);
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
      type: 'weldingOdfList',
    });
    this.tabLayoutService.close({
      component: WeldingOdfCreateComponent.name,
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
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
    this.tabLayoutService.open({
      component: 'ODFViewComponent',
      header: 'odf.tab.detail',
      tabId: (this.odfId * 121),
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
  }

  closeTabSave() {
    const action = this.action ? this._genKey + this.action : '';
    const tabId = this.tabId ? this._genKey + this.tabId : '';
    const key = WeldingOdfCreateComponent.name + action + tabId;
    if (this.isTabChanged === 'VALID') {
      this.tabLayoutService.isTabContentHasChanged({ component: WeldingOdfCreateComponent.name, key });
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(key));
    } else {
      this.tabLayoutService.close({
        component: WeldingOdfCreateComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId,
      });
    }
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({ key: 'cp', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied') });
    }
  }
  onBlurCable(event) {
    console.log(event);
    if (!this.isCableSelect) {
      // this.getLines({});
      this.isCableSelect = false;
    }
  }
  onBlurBinhInput() {
    this.inputBinhStyle = `input_form ng-pristine ng-valid ui-inputtext ui-corner-all ui-state-default ui-widget ng-touched`;
  }
}
