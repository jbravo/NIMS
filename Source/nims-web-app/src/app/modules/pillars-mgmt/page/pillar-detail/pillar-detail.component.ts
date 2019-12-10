import { Component, OnDestroy, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PillarService } from '../../service/pillar.service';
import { TranslationService } from 'angular-l10n';
import { TabLayoutComponent, TabLayoutService } from '@app/layouts/tab-layout';
import { MessageService, TreeNode } from 'primeng/api';
import { Table } from 'primeng/table';
import { SleeveService } from '../../../sleeve-management/service/sleeve.service';
import { COLS_TABLE } from '@app/shared/services/constants';
import { EventBusService } from '@app/shared/services/event-bus.service';
import { Subscription } from 'rxjs';
import { PoolService } from '@app/modules/pools-management/service/pool.service';
@Component({
  selector: 'pillar-detail',
  templateUrl: './pillar-detail.component.html',
  styleUrls: ['./pillar-detail.component.css']
})
export class PillarDetailComponent implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('dataTable') dataTable: Table;

  private contentHasChangedSub: Subscription;
  formAdd: FormGroup;
  action: string;
  tabId: number;
  isTabChanged: string;
  item: any;
  treeNodes: TreeNode[] = [];
  selectedNode: TreeNode;
  cableLaneDetail = false;
  sleeveDetail = false;
  pillarDetail = false;
  childData = true;
  parentPillarCode;
  parentPillar;
  selectedColumns: any;
  pillarDataObjSub;
  first: number;
  rows: number;
  pillarCode: string;
  cableLaneList: any = {
    listData: [],
    totalRecords: 0
  };
  paramsSearch = {
    first: 0,
    rows: undefined,
    pillarCodeTemp: undefined,
    pillarCode: undefined,
    laneCode: undefined,
    cableCode: undefined,
    sortField: undefined,
    sortOrder: undefined
  };
  private _genKey: string = '_';

  constructor(
    private pillarService: PillarService,
    private fb: FormBuilder,
    private translation: TranslationService,
    private tabLayoutService: TabLayoutService,
    private sleeveService: SleeveService,
    private eventBusService: EventBusService,
    private tabLayoutComponent: TabLayoutComponent,
    private messageService: MessageService,
    private poolService: PoolService,
  ) {
    this.buildForm();
  }

  ngOnInit() {
    this.selectedColumns = COLS_TABLE.VIEW_LANECODE;
    this.pillarDetail = true;
    this.tabId = this.pillarService.item.pillarId;
    this.action = this.pillarService.action;
    this.pillarDetailLoad();
    this.treeNodes = [{
      'label': this.translation.translate('pillar.information'),
      'data': 'pillarDetail',
      'expandedIcon': 'fa fa-folder-o',
      'collapsedIcon': 'fa fa-folder-o',
      'expanded': true,
      'children': [{
        'label': this.translation.translate('pillar.hang.cable.lane'),
        'data': 'cableLaneDetail',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }, {
        'label': this.translation.translate('common.label.sleeve'),
        'data': 'sleeveDetail',
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }]
    }];
    this.eventBusService.on('pillarRowData').subscribe(val => {
      console.log(val);
    });
  }

  // load data table
  onLazyLoad(event) {
    this.first = event.first;
    this.rows = event.rows;
    this.paramsSearch.first = event.first;
    this.paramsSearch.rows = event.rows;
    if (event.sortField) {
      this.paramsSearch.sortField = event.sortField;
      this.paramsSearch.sortOrder = event.sortOrder;
    }
    this.searchInTable(null, null);
  }


  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
    if (this.pillarDataObjSub) {
      this.pillarDataObjSub.unsubscribe();
    }
  }

  ngAfterViewInit(): void {
    if(this.pillarService.SleeveList === true){
      this.pillarDetail = false;
      this.cableLaneDetail = false;
      this.sleeveDetail = true;
      this.pillarService.SleeveList = false;
    }
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      this.isTabChanged = val;
      const action = this.action ? this._genKey + this.action : '';
      const tabId = this.tabId ? this._genKey + this.tabId : '';
      const key = PillarDetailComponent.name + action + tabId;
      this.tabLayoutService.isTabContentHasChanged({ component: PillarDetailComponent.name, key });
    });
  }

  private buildForm() {
    this.formAdd = this.fb.group({});
  }

  onNodeSelect(event, pillarCode) {
    if (event.node.data === 'cableLaneDetail') {
      this.pillarDetail = false;
      this.cableLaneDetail = true;
      this.sleeveDetail = false;
      this.pillarCode = pillarCode;
      this.tabLayoutService.open({
        component: PillarDetailComponent.name,
        header: 'pillar.manage.detail.label',
        breadcrumb: [
          { label: this.translation.translate('pillar.manage.label') },
          {
            label: this.translation.translate('pillar.manage.detail.label')
          },
          {
            label: this.translation.translate('pillar.view.hang.cable.lane')
          }
        ],
        action: this.action,
        tabId: this.tabId,
      });
    } else if (event.node.data === 'sleeveDetail') {
      this.sleeveService.pillarCode = pillarCode;
      this.openSleeveList();
    } else {
      this.pillarDetail = true;
      this.cableLaneDetail = false;
      this.sleeveDetail = false;
      // this.pillarDetailLoad();
      this.tabLayoutService.open({
        component: PillarDetailComponent.name,
        header: 'pillar.manage.detail.label',
        breadcrumb: [
          { label: this.translation.translate('pillar.manage.label') },
          {
            label: this.translation.translate('pillar.manage.detail.label')
          }
        ],
        action: this.action,
        tabId: this.tabId,
      });
    }
  }
  openSleeveList() {
    this.pillarDetail = false;
    this.cableLaneDetail = false;
    this.sleeveDetail = true;
    this.tabLayoutService.open({
      component: PillarDetailComponent.name,
      header: 'pillar.manage.detail.label',
      breadcrumb: [
        { label: this.translation.translate('pillar.manage.label') },
        {
          label: this.translation.translate('pillar.manage.detail.label')
        },
        {
          label: this.translation.translate('pillar.view.sleeve')
        }
      ],
      action: this.action,
      tabId: this.tabId,
    });
  }
  cableLaneDetailLoad(pillarCode) {
    this.pillarService.getListLaneCodeHangPillar({ pillarCode: pillarCode }).subscribe(res => {
      this.cableLaneList = res.content.listData;
    });
  }

  pillarDetailLoad() {
    this.formAdd.value.pillarId = this.pillarService.id;
    this.action = this.pillarService.action;
    this.item = this.pillarService.item;
    if (this.item.pillarStatusCable == 1) {
      this.item.pillarStatusCable = this.translation.translate('pillar.cable.status.yes');
    }
    if (this.item.pillarStatusCable == 0) {
      this.item.pillarStatusCable = this.translation.translate('pillar.cable.status.no');
    }
    if (this.item.status === 1) {
      this.item.status = this.translation.translate('pillar.status.one');
    } else {
      this.item.status = this.translation.translate('pillar.status.two');
    }
    this.item.pillarIndex = this.poolService.formartNumberPool(
      this.item.pillarIndex + '', false);
    this.parentPillarCode = this.item.pillarCode;
    this.parentPillar = { pillarCode: this.item.pillarCode, pillarId: this.item.pillarId };
  }

  onClosed() {
    this.tabLayoutService.close({
      component: 'PillarDetailComponent',
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
    this.tabLayoutService.open({
      component: 'PillarListComponent',
      header: '',
    });
  }

  onClosedTab() {
    if (this.isTabChanged === 'INVALID') {
      this.tabLayoutService.isTabContentHasChanged({ component: PillarDetailComponent.name });
      this.tabLayoutComponent.befClose(this.tabLayoutService.currentTab(PillarDetailComponent.name));
    } else {
      this.tabLayoutService.close({
        component: PillarDetailComponent.name,
        header: ''
      });
    }
  }

  saveOrEdit(id, action?: string) {
    this.pillarService.action = action;
    this.pillarService.id = id;
    this.tabLayoutService.open({
      component: 'PillarSaveComponent',
      header: 'pillar.manage.update.label',
      breadcrumb: [
        { label: this.translation.translate('pillar.manage.label') },
        { label: this.translation.translate(id ? 'pillar.manage.update.label' : 'pillar.manage.create.label') }
      ],
      action: action ? 'edit' : '',
      tabId: id,
    });
  }

  searchInTable(event, field) {
    this.paramsSearch.pillarCode = this.parentPillarCode;
    if (event != null && field != null) {
      if (field.field === 'pillarCode') {
        this.paramsSearch.pillarCodeTemp = event.target.value;
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
    });
  }

  copied(e) {
    if (e['copied']) {
      this.messageService.add({ key: 'kkkk', severity: 'success', summary: '', detail: e['content'] + ' are copied!' });
    }
  }
}

