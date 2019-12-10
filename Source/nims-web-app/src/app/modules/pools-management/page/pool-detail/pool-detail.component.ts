import { Component, OnInit } from '@angular/core';
import { TreeNode, MessageService } from 'primeng/api';
import { TranslationService } from 'angular-l10n';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PoolService } from '../../service/pool.service';
import { CommonUtils } from '@app/shared/services';
import { DataCommonService } from '@app/shared/services/data-common.service';
import { STATUS_POOL, COLS_TABLE } from '@app/shared/services/constants';
import { PillarService } from '@app/modules/pillars-mgmt/service/pillar.service';
import { TabLayoutService } from '@app/layouts/tab-layout';
import { PoolSaveComponent } from '../pool-save/pool-save.component';
@Component({
  selector: 'pool-detail',
  templateUrl: './pool-detail.component.html',
  styleUrls: ['./pool-detail.component.css']
})
export class PoolDetailComponent implements OnInit {
  formAdd: FormGroup;
  treeNodes: TreeNode[] = [];
  selectedNode: TreeNode;

  poolDetail = true;
  cableLaneDetail = false;
  PoolSleeve = false;
  statusListNew: any[] = [];
  childData = true;
  parentPoolCode: any;
  parentPool: any;
  cableLaneList: any = {
    listData : [],
    totalRecords : 0
  };
  selectedColumns: any;
  selectedResult;
  idPoolLocal: any;
  action: any;

  poolCodeStart = 4;
  poolCodeEnd = 8;
  first: number;
  rows: number;
  last: number;

  paramsSearch = {
    first: 0,
    rows: undefined,
    pillarCodeTemp: undefined,
    pillarCode: undefined,
    poolCode: undefined,
    laneCode: undefined,
    cableCode: undefined,
    sortField: undefined,
    sortOrder: undefined
  };

  constructor(private translation: TranslationService,
    private poolService: PoolService,
    private pillarService: PillarService,
    private dataCommon: DataCommonService,
    private fb: FormBuilder,
    private tabLayoutService: TabLayoutService,
    private messageService: MessageService,
  ) {
    this.buildForm();
  }

  ngOnInit() {
    this.selectedColumns = COLS_TABLE.VIEW_LANECODE_POOL;
    this.formAdd.value.poolId = this.poolService.id;
    this.idPoolLocal = this.poolService.id;
    this.action = this.poolService.action;
    this.poolService.id = null;
    this.poolService.action = null;

    this.poolService.findById(this.idPoolLocal).subscribe(res => {
      const object = res.content;
      if (object) {
        this.statusListNew = this.dataCommon.getDropDownList(STATUS_POOL);
        object.constructionDate = CommonUtils.stringToDate(object.constructionDate, 'yyyy/MM/dd');
        object.deliveryDate = CommonUtils.stringToDate(object.deliveryDate, 'yyyy/MM/dd');
        object.acceptanceDate = CommonUtils.stringToDate(object.acceptanceDate, 'yyyy/MM/dd');
        let deptIdView;
        if (this.action === 'view') {
          deptIdView = object.deptId;
          object.deptId = object.pathName;
          object.numberPool = this.poolService.formartNumberPool(
            object.poolCode.substring(this.poolCodeStart, this.poolCodeEnd) + '', false);
          object.poolTypeId = object.poolTypeCode;
          object.locationId = object.pathLocationName;
          object.ownerId = object.ownerName;
          this.parentPoolCode = object.poolCode;
          // this.parentPool = {poolCode : object.poolCode, poolId : object.poolId};
          this.statusListNew.forEach(element => {
            if (element.value === object.status) {
              object.status = element.label;
            }
          });
          object.longitude = this.poolService.formatLongLat(object.longitude);
          object.latitude = this.poolService.formatLongLat(object.latitude);
        }

        this.formAdd.reset(object);
        if (this.action === 'view') {
          object.deptId = deptIdView;
          this.parentPool = object;
        }
      }
    });
    this.treeNodes = [{
      'label': this.translation.translate('pool.info.detail'),
      'data': 'detail',
      'expandedIcon': 'fa fa-folder-o',
      'collapsedIcon': 'fa fa-folder-o',
      'expanded': true,
      'children': [{
        'label': this.translation.translate('pool.info.hang'),
        'data': 'cableLaneDetail',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }, {
        'label': this.translation.translate('pool.info.sleeve'),
        'data': 'PoolSleeve',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }]
    }];
  }

  get f() {
    return this.formAdd.controls;
  }

  buildForm() {
    this.formAdd = this.fb.group({
      poolId: [''],
      deptId: [''],
      numberPool: [''],
      poolTypeId: [''],
      locationId: [''],
      address: [''],
      ownerId: [''],
      status: [0],
      longitude: [''],
      latitude: [''],
      note: [''],
      poolCode: [''],
      deliveryDate: [''],
      acceptanceDate: [''],
      constructionDate: [''],
    });
  }

  onNodeSelect(event) {
    console.log(event);
    console.log(event.node);
    console.log(event.node.data);
    if (event.node.data === 'cableLaneDetail') {
      this.cableLaneDetail = true;
      this.poolDetail = false;
      this.PoolSleeve = false;
      // this.cableLaneDetailLoad(this.parentPoolCode);
      this.tabLayoutService.open({
        component: PoolDetailComponent.name,
        header:  'pool.manage.detail.label',
        breadcrumb: [
          { label: this.translation.translate('pool.header') },
          {
            label: this.translation.translate('pool.manage.detail.label')
          },
          {
            label: this.translation.translate('pool.manage.detail.label.hang')
          }
        ],
        action: this.action,
        tabId: this.idPoolLocal,
      });

    } else if (event.node.data === 'PoolSleeve') {
      this.cableLaneDetail = false;
      this.poolDetail = false;
      this.PoolSleeve = true;

      this.tabLayoutService.open({
        component: PoolDetailComponent.name,
        header:  'pool.manage.detail.label',
        breadcrumb: [
          { label: this.translation.translate('pool.header') },
          {
            label: this.translation.translate('pool.manage.detail.label')
          },
          {
            label: this.translation.translate('pool.manage.detail.label.sleeve')
          }
        ],
        action: this.action,
        tabId: this.idPoolLocal,
      });

    } else {
      this.cableLaneDetail = false;
      this.poolDetail = true;
      this.PoolSleeve = false;

      this.tabLayoutService.open({
        component: PoolDetailComponent.name,
        header:  'pool.manage.detail.label',
        breadcrumb: [
          { label: this.translation.translate('pool.header') },
          {
            label: this.translation.translate('pool.manage.detail.label')
          },
        ],
        action: this.action,
        tabId: this.idPoolLocal,
      });
    }

  }


  // cableLaneDetailLoad(poolCode) {
  //   this.poolService.getListLaneCodeHang({ poolCode: poolCode }).subscribe(res => {
  //     this.cableLaneList = res.content.listData;
  //   });
  // }

  onClosed() {
    this.tabLayoutService.close({
      component: PoolDetailComponent.name,
      header: '',
      action: this.action,
      tabId: this.idPoolLocal,
    });
    this.poolService.openPoolListTab();
  }

  onRowSelect(event: any, template?: any) {
    // console.log('vtData : ', event);
    // console.log(this.selectedResult);
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({
        severity: 'success', key: 'rightDown',
        summary: '', detail: this.translation.translate('common.label.copied')
      });
    }
  }


  showDetail(id, action) {
  }

  onSubmit() {
  }

  saveOrEdit(id?: number, action?: string) {
    this.poolService.id = this.idPoolLocal;
    this.poolService.action = 'edit';
    this.tabLayoutService.open({
      component: PoolSaveComponent.name,
      header: this.idPoolLocal ? 'pool.manage.update.label' : 'pool.manage.create.label',
      breadcrumb: [
        { label: this.translation.translate('pool.header') },
        { label: this.translation.translate(this.idPoolLocal ? 'pool.manage.update.label' : 'pool.manage.create.label') },
      ],
      action: this.idPoolLocal ? 'edit' : '',
      tabId: this.idPoolLocal,
    });
  }

  searchInTable(event, field) {
    this.paramsSearch.poolCode = this.parentPoolCode;
    if (field) {
      if (field.field === 'poolCode') {
        // this.paramsSearch.pillarCodeTemp = event.target.value;
        return;
      }
      if (field.field === 'cableCode') {
        this.paramsSearch.cableCode = event.target.value;
      }
      if (field.field === 'laneCode') {
        this.paramsSearch.laneCode = event.target.value;
      }
    }
    this.pillarService.getListLaneCodeHangPillar(this.paramsSearch).subscribe(res => {
      this.cableLaneList = res.content;
      this.last = (this.rows + this.first) < this.cableLaneList.totalRecords ? this.first + this.rows : this.cableLaneList.totalRecords;
      debugger
    });
  }


  public onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    // this.formSearch.controls['first'].setValue(event.first);
    // this.formSearch.controls['rows'].setValue(event.rows);
    this.paramsSearch.first = event.first;
    this.paramsSearch.rows = event.rows;
    if (event.sortField) {
      this.paramsSearch.sortField = event.sortField;
      this.paramsSearch.sortOrder = event.sortOrder;
    }
    
    this.searchInTable(null, null);
  }
}
