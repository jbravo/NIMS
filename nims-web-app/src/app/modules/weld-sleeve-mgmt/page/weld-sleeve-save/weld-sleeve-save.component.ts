import {Component, OnInit, OnDestroy, AfterViewInit, ViewChild} from '@angular/core';
import {Cable} from '../../service/cable';
import {MenuItem, MessageService} from 'primeng/api';
import {BreadcrumbService} from '@app/shared/services/breadcrumb.service';
import {WeldSleeveService} from '../../service/weld-sleeve.service';
import {CommonUtils} from '@app/shared/services';
import {Validators, FormGroup, FormControl} from '@angular/forms';

import {COLS_TABLE} from '@app/shared/services/constants';
import {AppComponent} from '@app/app.component';
import {WeldSleeve} from '../../service/WeldSleeve';
import {TranslationService} from 'angular-l10n';
import {first} from 'rxjs-compat/operator/first';
import {TabLayoutService, TabLayoutComponent} from '@app/layouts/tab-layout';
import {Subscription} from 'rxjs';
import {EventBusService} from '@app/shared/services/event-bus.service';
import {SleeveService} from '@app/modules/sleeve-management/service/sleeve.service';
import {ContextMenu} from 'primeng/primeng';

@Component({
  selector: 'weld-sleeve-save',
  templateUrl: './weld-sleeve-save.component.html',
  styleUrls: ['./weld-sleeve-save.component.css']
})
export class WeldSleeveSaveComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('contextMenu') contextMenu: ContextMenu;
  items: MenuItem[];
  isCollapse1 = false;
  isCollapse2 = false;
  formAdd: FormGroup;
  cablesSoure: Cable[];
  cablesSoureName: Cable[];
  cablesDest: Cable[];
  sleeves: any[];
  selectedCableSource: string;
  selectedSleeve;
  selectedCableDest;
  selectedTypeWeld;
  selectedWeldAmount;
  inputWeldPerson;
  inputWeldAttenuation;
  inputQuadSource;
  inputQuadDest;
  formdata;
  welddingBy;//Người hàn
  cableSource;//Cáp đến
  sleeveCode;//Mã măng xông
  cableDest;//Cáp đi
  typeWeld; //Kiểu hàn
  weldAmount;//Số lượng hàn
  attenuation;//suy hao mối hàn
  quadSource;//Quad đến
  quadDest;//Quad đi
  yarnSource;//Sợi đến
  yarnDest;//Sợi đi
  check1Direct = false;//Check 1 trực tiếp
  checkManyDirect = false;//Check nhiều trực tiếp
  check1Normal = false;//Check 1 và thông thường
  checkManyNormal = false;//Check nhiều và thông thường
  cableSourceYarnStart;//Cáp đến từ sợi
  cableSourceYarnEnd;//Cáp đến đến sợi
  cableDestYarnStart;//Cáp đi từ sợi
  cableDestYarnEnd;//Cáp đi đến sợi
  quadSourceStart;//Quad đến từ
  quadSourceEnd;//Quad đến đến
  quadDestStart;//Quad đi từ
  quadDestEnd;//quad đi đến
  cols;
  firstSource;
  rowsSource;
  last;
  weldService;
  cableSourceTotal;
  cableDestTotal;
  resultList: any = [];//data cable
  saveData: WeldSleeve;
  sleeveId: number;
  listDataSource: any[];//Data cáp đến
  listDataDest: any[];// Data cáp đi
  holderId;//Diem chua mang xong
  blockAZ: RegExp = /[0-9]/;
  inputFloat: RegExp = /[0-9.]/;

  validateFloat: RegExp = /^[0-9]+.{0,1}[0-9]{0,}$/;
  //  /[0-9.?]/;
  listDataSourceRes: any[];
  listDataDestRes: any[];
  firstDest;
  rowsDest;
  keyInput: String = '';
  keySortS;
  action: string;
  tabId: number;
  isTabChanged: string;
  private _genKey: string = '_';
  isEditStation = false;

  private contentHasChangedSub: Subscription;

  constructor(private breadcrumbService: BreadcrumbService,
              private weldSleeveService: WeldSleeveService,
              private app: AppComponent,
              private messageService: MessageService,
              private translation: TranslationService,
              private tabLayoutService: TabLayoutService,
              private tabLayoutComponent: TabLayoutComponent,
              private eventBusService: EventBusService,
              private sleeveService: SleeveService) {

    this.buildForm({});
    this.onProcess({first: 0, rows: 10});
  }

  ngOnInit() {
    this.cols = COLS_TABLE.WELDSlEEVE;
    this.action = this.weldSleeveService.action;
    this.tabId = this.weldSleeveService.index;
    if ('save' == this.action) {
      this.sleeveId = this.weldSleeveService.sleeveId;
      this.getSleeveCode(this.sleeveId);
      this.formAdd.controls['typeWeld'].setValue('direct');
      this.formAdd.controls['weldAmount'].setValue('weldOne');
      this.check1Normal = false;//Hiển thị sợi đến, sợi đi
      this.check1Direct = true;//Ẩn 1 và trực tiếp
      this.checkManyDirect = false;//Ẩn nhiều và trực tiếp
      this.checkManyNormal = false;//ẩn nhiều và thông thường
    }
    if ('edit' == this.action) {

      this.sleeveId = this.weldSleeveService.data.sleeveId;
      this.getSleeveCode(this.sleeveId);
      //get cap den
      this.isEditStation = true;
      this.weldSleeveService.getCableById(this.weldSleeveService.data.sourceCableId).subscribe(res => {
        this.cablesSoure = [];
        let cable = new Cable();
        cable.id = res.cableId;
        cable.name = res.cableCode;
        cable.cableTypeId = res.cableTypeId;
        this.cablesSoure.push(cable);
        this.formAdd.controls['cableSource'].setValue(cable);
        this.getYarnCableSourceList();
      }, error => {
      }, () => {
        //get cap di
        this.weldSleeveService.getCableById(this.weldSleeveService.data.destCableId).subscribe(res => {
          this.cablesDest = [];
          let cable = new Cable();
          cable.id = res.cableId;
          cable.name = res.cableCode;
          cable.cableTypeId = res.cableTypeId;
          this.cablesDest.push(cable);
          this.formAdd.controls['cableDest'].setValue(cable);
          this.getYarnCableDestList();
          this.isEditStation = false;
        });
      });

      //this.typeWeld="normal";
      this.formAdd.controls['typeWeld'].setValue('normal');
      this.formAdd.controls['weldAmount'].setValue('weldOne');
      this.formAdd.controls['welddingBy'].setValue(this.weldSleeveService.data.createUser);
      this.formAdd.controls['attenuation'].setValue(this.weldSleeveService.data.attenuation);
      this.formAdd.controls['yarnSource'].setValue(this.weldSleeveService.data.sourceLineNo);
      this.formAdd.controls['yarnDest'].setValue(this.weldSleeveService.data.destLineNo);
      this.check1Normal = true;//Hiển thị sợi đến, sợi đi
      this.check1Direct = false;//Ẩn 1 và trực tiếp
      this.checkManyDirect = false;//Ẩn nhiều và trực tiếp
      this.checkManyNormal = false;//ẩn nhiều và thông thường
    }

  }


  ngAfterViewInit(): void {
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      if (!this.isEditStation) {
        this.isTabChanged = 'isTabChanged';
        const action = this.action ? this._genKey + this.action : '';
        const tabId = this.tabId ? this._genKey + this.tabId : '';
        const key = WeldSleeveSaveComponent.name + action + tabId;
        this.tabLayoutService.isTabContentHasChanged({component: WeldSleeveSaveComponent.name, key});
      }
    });

  }

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
  }

  onProcess(event) {
    this.firstSource = event.first;
    this.rowsSource = event.rows;
  }

  // load data table cable source
  public onLazyLoadSource(event) {
    this.firstSource = event.first;
    this.rowsSource = event.rows;
    this.getYarnCableSourceList();
  }

  //load data table cable dest
  public onLazyLoadDest(event) {
    this.firstDest = event.first;
    this.rowsDest = event.rows;
    this.getYarnCableDestList();
  }

  setKeyInput(keyInput) {
    this.keyInput = keyInput;
  }

  //Search cap den
  onInputSearch(event, element: string) {
    this.setKeyInput(event.target.value);
    this.formAdd.controls['keySortS'].setValue(this.keyInput);
    this.formAdd.controls['sortFieldS'].setValue(element);
    if (this.listDataSource) {
      this.advanceSearchSource();
    }
  }

  //Search cap di
  onInputSearchDest(event, element: string) {
    this.setKeyInput(event.target.value);
    this.formAdd.controls['keySortD'].setValue(this.keyInput);
    this.formAdd.controls['sortFieldD'].setValue(element);
    if (this.listDataDest) {
      this.advanceSearchDest();
    }
  }

  advanceSearchDest() {

    this.formAdd.controls['firstDest'].setValue(0);
    if (this.formAdd.value.keySortD) {
      this.weldSleeveService.getYarnCable(this.formAdd.value.cableDest.cableTypeId, this.sleeveId, this.formAdd.value.cableDest.id).subscribe(res => {
        this.listDataDestRes = res;
        //search cablecode start
        if (this.formAdd.value.sortFieldD == 'cableCode') {
          let cableCode = res[0].cableCode;
          cableCode = cableCode.toLocaleLowerCase();
          let keySearch = this.formAdd.value.keySortD.toLocaleLowerCase();
          this.cableDestTotal = this.listDataDestRes.length;
          if (this.cableDestTotal > 0) {
            if (cableCode.lastIndexOf(keySearch) >= 0) {
              this.listDataDest = this.listDataDestRes.slice(this.firstDest, this.firstDest + this.rowsDest);
            } else {
              this.listDataDest = [];
            }
          }
        }
        //search cableCode End
        //search quad start
        if (this.formAdd.value.sortFieldD == 'quad') {
          this.cableDestTotal = this.listDataDestRes.length;
          this.listDataDest = [];
          let quad = this.formAdd.value.keySortD * 1;
          for (let i = 0; i < this.cableDestTotal; i++) {

            if (this.listDataDestRes[i].quad == quad) {
              this.listDataDest.push(this.listDataDestRes[i]);
            }
          }
        }
        //search quad End
        //search yarn start
        if (this.formAdd.value.sortFieldD == 'yarn') {
          this.cableDestTotal = this.listDataDestRes.length;
          this.listDataDest = [];
          let yarn = this.formAdd.value.keySortD * 1;
          for (let i = 0; i < this.cableDestTotal; i++) {

            if (this.listDataDestRes[i].yarn == yarn) {
              this.listDataDest.push(this.listDataDestRes[i]);
            }
          }
        }
        //search yarn End
      });
    } else {
      this.getYarnCableDestList();
    }
  }

  advanceSearchSource() {
    this.formAdd.controls['firstSource'].setValue(0);
    if (this.formAdd.value.keySortS) {
      this.weldSleeveService.getYarnCable(this.formAdd.value.cableSource.cableTypeId, this.sleeveId, this.formAdd.value.cableSource.id).subscribe(res => {
        this.listDataSourceRes = res;
        //search cablecode start
        if (this.formAdd.value.sortFieldS == 'cableCode') {
          let cableCode = res[0].cableCode;
          cableCode = cableCode.toLocaleLowerCase();
          let keySearch = this.formAdd.value.keySortS.toLocaleLowerCase();
          this.cableSourceTotal = this.listDataSourceRes.length;
          if (this.cableSourceTotal > 0) {
            if (cableCode.lastIndexOf(keySearch) >= 0) {
              this.listDataSource = this.listDataSourceRes.slice(this.firstSource, this.firstSource + this.rowsSource);
            } else {
              this.listDataSource = [];
            }
          }
        }
        //search cableCode End
        //search quad start
        if (this.formAdd.value.sortFieldS == 'quad') {
          this.cableSourceTotal = this.listDataSourceRes.length;
          this.listDataSource = [];
          let quad = this.formAdd.value.keySortS * 1;
          for (let i = 0; i < this.cableSourceTotal; i++) {

            if (this.listDataSourceRes[i].quad == quad) {
              this.listDataSource.push(this.listDataSourceRes[i]);
            }
          }
        }
        //search quad End
        //search yarn start
        if (this.formAdd.value.sortFieldS == 'yarn') {
          this.cableSourceTotal = this.listDataSourceRes.length;
          this.listDataSource = [];
          let yarn = this.formAdd.value.keySortS * 1;
          for (let i = 0; i < this.cableSourceTotal; i++) {

            if (this.listDataSourceRes[i].yarn == yarn) {
              this.listDataSource.push(this.listDataSourceRes[i]);
            }
          }
        }
        //search yarn End
      });
    } else {
      this.getYarnCableSourceList();
    }
  }

  //Search cap di
  buildForm(formData: any) {
    this.formAdd = CommonUtils.createForm(formData, {
      cableSource: [''],
      sleeveCode: '',
      cableDest: [''],
      typeWeld: '',
      weldAmount: '',
      attenuation: '',
      welddingBy: '',
      quadSource: '',
      quadDest: '',
      yarnSource: '',
      yarnDest: '',
      cableSourceYarnStart: '',
      cableSourceYarnEnd: '',
      cableDestYarnStart: '',
      cableDestYarnEnd: '',
      quadSourceStart: '',
      quadSourceEnd: '',
      quadDestStart: '',
      quadDestEnd: '',
      firstSource: [''],
      recordsTotal: [''],
      rowsSource: [''],
      sortFieldS: [''],
      sortOrderS: [''],
      sortFieldD: [''],
      sortOrderD: [''],
      rowsDest: [''],
      keySortD: [''],
      keySortS: [''],
      firstDest: [''],
    });
  }

  getFormValidationErrors(data): boolean {
    this.messageService.clear();
    //Check empty start
    let checkValidate = true;
    if (!data.cableSource) {//Validate cable den
      this.messageService.add({
        key: 'weldSleeveMsg',
        severity: 'error',
        summary: '',
        detail: this.translation.translate('weldding.error.cableSourceEmpty')
      });
      checkValidate = false;
    }
    if (!data.cableDest) {//Validate cable di
      this.messageService.add({
        key: 'weldSleeveMsg',
        severity: 'error',
        summary: '',
        detail: this.translation.translate('weldding.error.cableDestEmty')
      });
      checkValidate = false;
    }
    if (!data.typeWeld) {//Validate Kiểu hàn
      this.messageService.add({
        key: 'weldSleeveMsg',
        severity: 'error',
        summary: '',
        detail: this.translation.translate('weldding.error.typeWeldEmpty')
      });
      checkValidate = false;
    }
    if (!data.weldAmount) {//Validate số lượng hàn
      this.messageService.add({
        key: 'weldSleeveMsg',
        severity: 'error',
        summary: '',
        detail: this.translation.translate('weldding.error.weldAmountEmpty')
      });
      checkValidate = false;
    }

    if (data.attenuation) {//Validate suy hao han
      if (this.validateFloat.test(data.attenuation) != true) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.attenuationError')
        });
        checkValidate = false;
      } else {
        // cat chuoi suy hao
        let index = data.attenuation.toString().indexOf('.');
        if (index >= 0) {
          if (data.attenuation.length > index + 3) {
            data.attenuation = data.attenuation.toString().slice(0, index + 3);
          } else {
            data.attenuation = data.attenuation.toString().slice(0, data.attenuation.length);
          }
        }
      }
    }
    if (this.check1Normal) {//Validate han 1 cap va thong thuong
      if (!data.yarnSource) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.yarnSourceEmpty')
        });
        checkValidate = false;
      }
      if (!data.yarnDest) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.yarnDestEmpty')
        });
        checkValidate = false;
      }
    }
    if (this.check1Direct) {//Validate han 1 cap va truc tiep
      if (!data.quadSource) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.quadSourceEmpty')
        });
        checkValidate = false;
      }
      if (!data.quadDest) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.quadDestEmpty')
        });
        checkValidate = false;
      }
    }
    if (this.checkManyNormal) {//Validate han nhieu cap va thong thuong
      if (!data.cableSourceYarnStart) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.cableSourceYarnStartEmpty')
        });
        checkValidate = false;
      }
      if (!data.cableSourceYarnEnd) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.cableSourceYarnEndEmpty')
        });
        checkValidate = false;
      }
      if (!data.cableDestYarnStart) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.cableDestYarnStartEmpty')
        });
        checkValidate = false;
      }
      if (!data.cableDestYarnEnd) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.cableDestYarnEndEmpty')
        });
        checkValidate = false;
      }
    }
    if (this.checkManyDirect) {//Validate han nhieu cap va truc tiep
      if (!data.quadSourceStart) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.quadSourceStartEmpty')
        });
        checkValidate = false;
      }
      if (!data.quadSourceEnd) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.quadSourceEndEmpty')
        });
        checkValidate = false;
      }
      if (!data.quadDestStart) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.quadDestStartEmpty')
        });
        checkValidate = false;
      }
      if (!data.quadDestEnd) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.quadDestEndEmpty')
        });
        checkValidate = false;
      }
    }
    if (data.cableSource && data.cableDest) {
      if (data.cableDest.id == data.cableSource.id) {
        this.messageService.add({
          key: 'weldSleeveMsg',
          severity: 'error',
          summary: '',
          detail: this.translation.translate('weldding.error.cableSourceSameCableDest')
        });
        checkValidate = false;
      }
    }
    if (this.action == 'save') {
      //Check empty End
      if (checkValidate) {
        //Check han 1 va truc tiep START
        if (this.check1Direct) {
          let checkQuadSource = true;
          let checkQuadDest = true;
          let listEnableQuadSource = [];
          let listEnableQuadDest = [];
          //Check quad den, quad di co nam trong danh sach soi duoc noi hay khong
          for (let i = 0; i < this.listDataSourceRes.length; i++) {
            if (data.quadSource == this.listDataSourceRes[i].quad) {
              listEnableQuadSource.push(this.listDataSourceRes[i]);
              checkQuadSource = false;
            }
          }
          for (let i = 0; i < this.listDataDestRes.length; i++) {
            if (data.quadDest == this.listDataDestRes[i].quad) {
              listEnableQuadDest.push(this.listDataDestRes[i]);
              checkQuadDest = false;
            }
          }
          if (checkQuadSource) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.quadSourceNotFoud')
            });
            checkValidate = false;
          }
          if (checkQuadDest) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.quadDestNotFoud')
            });
            checkValidate = false;
          }
          if (!checkQuadDest && !checkQuadSource) {
            if (listEnableQuadDest.length != listEnableQuadSource.length) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.quadSourceNotSameQuadDest')
              });
              checkValidate = false;
            }
          }
        }
        //Check han 1 va truc tiep END
        //Check han 1 va thong thuong START
        if (this.check1Normal) {
          let checkYarnSource = true;
          let checkYarnDest = true;
          let listEnableYarnSource = [];
          let listEnableYarnDest = [];
          //check soi den, soi di có nam trong soi duoc noi hay k
          for (let i = 0; i < this.listDataSourceRes.length; i++) {
            if (data.yarnSource == this.listDataSourceRes[i].yarn) {
              listEnableYarnSource.push(this.listDataSourceRes[i].yarn);
              checkYarnSource = false;
            }
          }
          for (let i = 0; i < this.listDataDestRes.length; i++) {
            if (data.yarnDest == this.listDataDestRes[i].yarn) {
              listEnableYarnDest.push(this.listDataDestRes[i].yarn);
              checkYarnDest = false;
            }
          }
          if (checkYarnSource) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.yarnSourceNotFoud')
            });
            checkValidate = false;
          }
          if (checkYarnDest) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.yarnDestNotFoud')
            });
            checkValidate = false;
          }
          if (!checkYarnSource && !checkYarnDest) {
            if (listEnableYarnSource.length != listEnableYarnDest.length) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.yarnDestNotSameYarnSource')
              });
              checkValidate = false;
            }
          }
        }
        //Check han 1 cap va thong thuong END
        //Check han nhieu cap va truc tiep
        if (this.checkManyDirect) {
          //quad den (tu, den)
          let listEnableQuadSource = [];
          let listEnableQuadDest = [];
          //quad den start
          if (data.quadSourceStart > data.quadSourceEnd) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.quadSourceStartMoreSourceEnd')
            });
            checkValidate = false;
          } else {
            let quadMax = false;
            let quadMin = false;
            for (let i = 0; i < this.listDataSourceRes.length; i++) {
              if (data.quadSourceEnd == this.listDataSourceRes[i].quad) {
                quadMax = true;
              }
              if (data.quadSourceStart == this.listDataSourceRes[i].quad) {
                quadMin = true;
              }
              for (let j = data.quadSourceStart; j <= data.quadSourceEnd; j++) {
                if (j == this.listDataSourceRes[i].quad) {
                  listEnableQuadSource.push(this.listDataSourceRes[i]);
                }
              }
            }
            if (!quadMin) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.quadSourceStartNotFound')
              });
              checkValidate = false;
            }
            if (!quadMax) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.quadSourceEndNotFound')
              });
              checkValidate = false;
            }
          }
          //quad den end
          //quad di start
          if (data.quadDestStart > data.quadDestEnd) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.quadDestStartMoreSourceEnd')
            });
            checkValidate = false;
          } else {
            let quadMax = false;
            let quadMin = false;
            for (let i = 0; i < this.listDataDestRes.length; i++) {
              if (data.quadDestEnd == this.listDataDestRes[i].quad) {
                quadMax = true;
              }
              if (data.quadDestStart == this.listDataDestRes[i].quad) {
                quadMin = true;
              }
              for (let j = data.quadDestStart; j <= data.quadDestEnd; j++) {
                if (j == this.listDataDestRes[i].quad) {
                  listEnableQuadDest.push(this.listDataDestRes[i]);
                }
              }
            }
            if (!quadMin) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.quadDestStartNotFound')
              });
              checkValidate = false;
            }
            if (!quadMax) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.quadDestEndNotFound')
              });
              checkValidate = false;
            }
          }
          if (listEnableQuadSource.length != listEnableQuadDest.length) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.quadSourceStartNotSameDestStart')
            });
            checkValidate = false;
          }
        }
        //Check han nhieu cap va truc tiep end
        //Check han nhieu cap va thong thuong start
        if (this.checkManyNormal) {
          //quad den (tu, den)
          let listEnableYarnSource = [];
          let listEnableYarnDest = [];
          //quad den start
          if (data.cableSourceYarnStart > data.cableSourceYarnEnd) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.yarnSourceStartMoreSourceEnd')
            });
            checkValidate = false;
          } else {
            let yarnMax = false;
            let yarnMin = false;
            for (let i = 0; i < this.listDataSourceRes.length; i++) {
              if (data.cableSourceYarnEnd == this.listDataSourceRes[i].yarn) {
                yarnMax = true;
              }
              if (data.cableSourceYarnStart == this.listDataSourceRes[i].yarn) {
                yarnMin = true;
              }
              for (let j = data.cableSourceYarnStart; j <= data.cableSourceYarnEnd; j++) {
                if (j == this.listDataSourceRes[i].yarn) {
                  listEnableYarnSource.push(this.listDataSourceRes[i]);
                }
              }
            }

            if (!yarnMin) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.yarnSourceStartNotFound')
              });
              checkValidate = false;
            }
            if (!yarnMax) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.yarnSourceEndNotFound')
              });
              checkValidate = false;
            }
          }
          //quad den end
          //quad di start
          if (data.cableDestYarnStart > data.cableDestYarnEnd) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.yarnDestStartMoreSourceEnd')
            });
            checkValidate = false;
          } else {
            let yarnMax = false;
            let yarnMin = false;
            for (let i = 0; i < this.listDataDestRes.length; i++) {
              if (data.cableDestYarnEnd == this.listDataDestRes[i].yarn) {
                yarnMax = true;
              }
              if (data.cableDestYarnStart == this.listDataDestRes[i].yarn) {
                yarnMin = true;
              }
              for (let j = data.cableDestYarnStart; j <= data.cableDestYarnEnd; j++) {
                if (j == this.listDataDestRes[i].yarn) {
                  listEnableYarnDest.push(this.listDataDestRes[i]);
                }
              }
            }
            if (!yarnMin) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.yarnDestStartNotFound')
              });
              checkValidate = false;
            }
            if (!yarnMax) {
              this.messageService.add({
                key: 'weldSleeveMsg',
                severity: 'error',
                summary: '',
                detail: this.translation.translate('weldding.error.yarnDestEndNotFound')
              });
              checkValidate = false;
            }
          }
          if (listEnableYarnSource.length != listEnableYarnDest.length) {
            this.messageService.add({
              key: 'weldSleeveMsg',
              severity: 'error',
              summary: '',
              detail: this.translation.translate('weldding.error.yarnSourceStartNotSameDestStart')
            });
            checkValidate = false;
          }
        }
        //Check han nhieu cap va thong thuong end
      }
    }
    return checkValidate;

  }

  onClickSubmit(data) {
    data = this.formAdd.value;
    data.sleeveId = this.sleeveId;
    if (this.formAdd.value.cableSource) {
      data.cableSourceId = this.formAdd.value.cableSource.id;
    }
    data.cableDestId = this.formAdd.value.cableDest.id;
    if (this.getFormValidationErrors(data)) {
      if (this.action != 'edit') {
        //save
        this.weldSleeveService.saveWeldSleeve(data).subscribe(res => {
          if (res == 'OK') {
            this.messageService.add({
              severity: 'success',
              key: 'weldSleeveSave',
              summary: '',
              detail: this.translation.translate('weldding.message.created.succes')
            });
            this.eventBusService.emit({
              type: 'weldingSleeveList',
            });
            this.weldSleeveService.sleeveId = this.sleeveId;
            this.weldSleeveService.message = 'succes',

              // this.onClosed();
              this.sleeveService.activeWeld = true;
            setTimeout(() => {
              this.onClose();
            }, 1000);
          } else {
            this.messageService.add({
              key: 'weldSleeveSave',
              sticky: true,
              severity: 'error',
              summary: this.translation.translate('message.error.have.error'),
            });
            this.weldSleeveService.sleeveId = this.sleeveId;
            setTimeout(() => {
              this.onClose();
            }, 1000);
          }
        });
      } else {
        //edit
        this.weldSleeveService.updateWeldSleeve(data).subscribe(res => {
          if (res == 'OK') {
            this.messageService.add({
              severity: 'success',
              key: 'weldSleeveSave',
              summary: '',
              detail: this.translation.translate('weldding.message.updated.success')
            });
            this.eventBusService.emit({
              type: 'weldingSleeveList',
            });
            this.weldSleeveService.sleeveId = this.sleeveId;
            this.weldSleeveService.message = 'weldding.message.updated.success';
            this.sleeveService.activeWeld = true;
            setTimeout(() => {
              this.onClose();
            }, 1000);

          } else {
            this.weldSleeveService.sleeveId = this.sleeveId;
            this.messageService.add({
              key: 'weldSleeveSave',
              sticky: true,
              severity: 'error',
              summary: this.translation.translate('message.error.have.error'),
            });
            setTimeout(() => {
              this.onClose();
            }, 1000);
          }
        });
      }
    }
  }

  checkTypeAmout() {
    this.typeWeld = this.formAdd.value.typeWeld;
    this.weldAmount = this.formAdd.value.weldAmount;
    if (this.typeWeld == 'normal' && this.weldAmount == 'weldOne') { //1 cặp và thông thường
      this.formAdd.controls['yarnSource'].setValue('');
      this.formAdd.controls['yarnDest'].setValue('');
      this.check1Normal = true;//Hiển thị sợi đến, sợi đi
      this.check1Direct = false;//Ẩn 1 và trực tiếp
      this.checkManyDirect = false;//Ẩn nhiều và trực tiếp
      this.checkManyNormal = false;//ẩn nhiều và thông thường
    } else if (this.typeWeld == 'direct' && this.weldAmount == 'weldOne') {// 1 cặp và trực tiếp
      this.formAdd.controls['quadSource'].setValue('');
      this.formAdd.controls['quadDest'].setValue('');
      this.check1Normal = false;//Ẩn sợi đến, sợi đi
      this.check1Direct = true;//Hiển thị và cho nhập quad đến và quad đi
      this.checkManyDirect = false;//Ẩn nhiều và trực tiếp
      this.checkManyNormal = false;//ẩn nhiều và thông thường
    } else if (this.typeWeld == 'normal' && this.weldAmount == 'weldMany') {//nhiều cặp và thông thường
      this.formAdd.controls['cableSourceYarnStart'].setValue('');
      this.formAdd.controls['cableSourceYarnEnd'].setValue('');
      this.formAdd.controls['cableDestYarnStart'].setValue('');
      this.formAdd.controls['cableDestYarnEnd'].setValue('');
      this.check1Normal = false;//Ẩn  sợi đến, sợi đi
      this.check1Direct = false;//Ẩn 1 và trực tiếp
      this.checkManyDirect = false;//Ẩn nhiều và trực tiếp
      this.checkManyNormal = true;//Hiển thị cáp đến(từ, đến), cáp đi(từ, đến)
    } else if (this.typeWeld == 'direct' && this.weldAmount == 'weldMany') {//nhiều cặp và trực tiếp
      this.formAdd.controls['quadSourceStart'].setValue('');
      this.formAdd.controls['quadSourceEnd'].setValue('');
      this.formAdd.controls['quadDestStart'].setValue('');
      this.formAdd.controls['quadDestEnd'].setValue('');
      this.check1Normal = false;//Hiển thị sợi đến, sợi đi
      this.check1Direct = false;//Ẩn 1 và trực tiếp
      this.checkManyDirect = true;//Hiển thị quad đến(từ, đến), quad đi(từ,đến)
      this.checkManyNormal = false;//ẩn nhiều và thông thường
    }
  }

  getSleeveCode(sleeveId: number) {
    this.weldSleeveService.getSleeveById(sleeveId).subscribe(res => {
      this.sleeves = [];
      this.sleeves.push({id: sleeveId, label: res.sleeveCode});
      this.holderId = res.holderId;
      this.getCableSource(this.holderId);
    }, er => {
    }, () => {
    });
  }

  getCableSource(holderId: number) {
    this.weldSleeveService.getCableCode(holderId).subscribe(res => {
      this.cablesSoureName = [];
      for (let i = 0; i < res.length; i++) {
        let cableS = new Cable();
        cableS.id = res[i].cableId;
        cableS.name = res[i].cableCode;
        cableS.cableTypeId = res[i].cableTypeId;
        this.cablesSoureName.push(cableS);
      }
    });
  }

  getYarnCableSourceList() {
    if (this.formAdd.value.cableSource.cableTypeId && this.sleeveId && this.formAdd.value.cableSource.id) {
      this.weldSleeveService.getYarnCable(this.formAdd.value.cableSource.cableTypeId, this.sleeveId, this.formAdd.value.cableSource.id).subscribe(res => {
        this.listDataSourceRes = res;
        this.cableSourceTotal = this.listDataSourceRes.length;
        this.listDataSource = this.listDataSourceRes.slice(this.firstSource, this.firstSource + this.rowsSource);
      });
    }
  }

  getYarnCableDestList() {
    if (this.formAdd.value.cableDest.cableTypeId && this.sleeveId && this.formAdd.value.cableDest.id) {
      this.weldSleeveService.getYarnCable(this.formAdd.value.cableDest.cableTypeId, this.sleeveId, this.formAdd.value.cableDest.id).subscribe(res => {
        this.listDataDestRes = res;
        this.cableDestTotal = this.listDataDestRes.length;
        this.listDataDest = this.listDataDestRes.slice(this.firstDest, this.firstDest + this.rowsDest);
      });
    }
  }

  onReject() {
    this.messageService.clear('cws');
  }


  onConfirm() {
    this.tabLayoutService.close({
      component: 'WeldSleeveSaveComponent',
      header: '',
      action: this.action,
      tabId: this.tabId
    });

  }

  onClose() {
    this.tabLayoutService.close({
      component: 'WeldSleeveSaveComponent',
      header: '',
      action: this.action,
      tabId: this.tabId
    });
    this.tabLayoutService.open({
      component: 'SleeveDatailComponent',
      header: 'sleeve.manage.detail.label',
      tabId: this.sleeveId,
      action: 'view',
      breadcrumb: [
        {label: this.translation.translate('infra.sleeves.management')},
        {
          label: this.translation.translate('sleeve.manage.detail.label')
        },
        {
          label: this.translation.translate('infra.weld.in.sleeves')
        }
      ]
    });
  }

  onClosedTab() {
    debugger
    if (this.isTabChanged === 'isTabChanged') {
      const action = this.action ? this._genKey + this.action : '';
      const tabId = this.tabId ? this._genKey + this.tabId : '';
      const key = WeldSleeveSaveComponent.name + action + tabId;
      this.tabLayoutService.isTabContentHasChanged({component: WeldSleeveSaveComponent.name, key});
      const parent = {
        component: 'SleeveDatailComponent',
        header: 'sleeve.manage.detail.label',
        tabId: this.sleeveId,
        action: 'view',
        breadcrumb: [
          {label: this.translation.translate('infra.sleeves.management')},
          {
            label: this.translation.translate('sleeve.manage.detail.label')
          },
          {
            label: this.translation.translate('infra.weld.in.sleeves')
          }]
      };
      this.tabLayoutComponent.befClose(
        this.tabLayoutService.currentTab(key),
        parent
      );
    } else {
      this.tabLayoutService.close({
        component: WeldSleeveSaveComponent.name,
        header: '',
        action: this.action,
        tabId: this.tabId
      });
    }
  }

  openWeldSleeveListTab() {
    this.tabLayoutService.open({
      component: 'WeldSleeveListComponent',
      header: 'cable.manage.label',
      breadcrumb: [{label: this.translation.translate('cable.manage.label')}]
    });
  }


  onLinkRightClickedHeader(content: string, event: any, table: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          {label: this.translation.translate('common.view.truncate'), icon: 'fa fa-compress', command: () => this.onCollapse(table)},
          {label: this.translation.translate('common.view.all'), icon: 'fa fa-expand', command: () => this.onCollapse(table, 'expend')},
        );
        this.contextMenu.model = this.items;
        this.contextMenu.show(event);
      }
    }
  }

  onLinkRightClickedRow(content: string, event: any, table: any): void {
    if (content === undefined || content === null || content === '') {
      return;
    } else {
      if (this.contextMenu) {
        this.items = [];
        this.items.push(
          {label: this.translation.translate('common.view.truncate'), icon: 'fa fa-compress', command: () => this.onCollapse(table)},
          {label: this.translation.translate('common.view.all'), icon: 'fa fa-expand', command: () => this.onCollapse(table, 'expend')},
          {label: 'Copy', icon: 'fa fa-clipboard', command: () => this.onCopy(content)}
        );
        this.contextMenu.model = this.items;
        this.contextMenu.show(event);
      }
    }
  }

  onCollapse(table: any, type?: string) {
    if (table === 1) {
      this.isCollapse1 = type === 'expend';
    } else {
      this.isCollapse2 = type === 'expend';
    }

  }

  onCopy(event) {
    if (event) {
      this.messageService.add({
        key: 'copySuccess', severity: 'success', summary: '', detail: this.translation.translate('common.label.copied')
      });
      this.items = [];
    }
  }
}
