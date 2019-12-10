import {Component, OnInit, Type, ViewChild, HostListener, AfterViewInit} from '@angular/core';
import {FormGroup, FormBuilder} from '@angular/forms';
import {SleeveService} from '../../service/sleeve.service';
import {TranslationService} from 'angular-l10n';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {TreeNode} from 'primeng/api';
// tslint:disable-next-line:max-line-length
import {AutocompleteSearchDepartmentModalComponent} from '@app/shared/components/autocomplete-search-control/autocomplete-search-department-modal/autocomplete-search-department-modal.component';
import {Table} from 'primeng/table';
import {environment} from '@env/environment';
import {NgxSpinnerService} from 'ngx-spinner';
import {Subscription} from 'rxjs';
import {SleeveSaveComponent} from '../sleeve-save/sleeve-save.component';
import {SleeveListComponent} from '../sleeve-list/sleeve-list.component';
import {WeldSleeveListComponent} from '@app/modules/weld-sleeve-mgmt/page/weld-sleeve-list/weld-sleeve-list.component';

@Component({
  selector: 'sleeve-datail',
  templateUrl: './sleeve-datail.component.html',
  styleUrls: ['./sleeve-datail.component.css']
})
export class SleeveDatailComponent implements OnInit, AfterViewInit {
  @ViewChild('dataTable') dataTable: Table;
  @ViewChild('weldSleeveComponent') weldSleeveComponent: WeldSleeveListComponent;
  formAdd: FormGroup;
  private contentHasChangedSub: Subscription;
  isTabChanged: string;
  action: string;
  tabId: number;
  private _genKey: string;
  item: any;
  dept: Type<any> = AutocompleteSearchDepartmentModalComponent;
  treeNodes: TreeNode[] = [];
  selectedNode: TreeNode;
  checkSleeve: boolean;
  checkWeldSleeve: boolean;
  environment = environment;

  constructor(private sleeveService: SleeveService,
              private fb: FormBuilder,
              private translation: TranslationService,
              private tabLayoutService: TabLayoutService,
              private spinner: NgxSpinnerService) {
    this.buildForm();
  }

  ngOnInit() {
    this._genKey = '_';
    this.tabId = this.sleeveService.item.sleeveId;
    this.action = this.sleeveService.action;
    this.checkSleeve = true;
    this.checkWeldSleeve = false;
    if (this.sleeveService.action === 'view') {
      this.formAdd.value.sleeveId = this.sleeveService.id;
      this.action = this.action;
      this.item = this.sleeveService.item;
    }
    this.treeNodes = [{
      'label': this.translation.translate('infra.sleeves.infomation'),
      'expandedIcon': 'fa fa-folder-o',
      'collapsedIcon': 'fa fa-folder-o',
      'expanded': true,
      'children': [{
        'label': this.translation.translate('infra.weld.in.sleeves'),
        'expandedIcon': 'fa fa-folder-o',
        'collapsedIcon': 'fa fa-folder-o',
      }]
    }];
  }

  ngAfterViewInit(): void {
    this.sleeveService.item.sleeveId = this.tabId;
    if (this.sleeveService.activeWeld) {
      this.checkWeldSleeve = true;
      this.checkSleeve = false;
      this.weldSleeveComponent.onProcess({first: 0, rows: 10});
    }
    this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
      this.isTabChanged = val;
      const action = this.action ? this._genKey + this.action : '';
      const tabId = this.tabId ? this._genKey + this.tabId : '';
      const key = SleeveDatailComponent.name + action + tabId;
      this.tabLayoutService.isTabContentHasChanged({component: SleeveDatailComponent.name, key});
    });
  }

  private buildForm() {
    this.formAdd = this.fb.group({
      sleeveId: [null],
      sleeveCode: [''],
      sleeveTypeId: [''],
      installationDate: [''],
      ownerName: [''],
      ownerId: [''],
      updateTime: [''],
      vendorName: [''],
      vendorId: [''],
      laneCode: [''],
      serial: [''],
      purpose: [''],
      sleeveIndex: [''],
      status: [''],
      note: [''],
      holderId: [''],
      first: [''],
      rows: [''],
      laneId: [''],
      deptId: [''],
      deptPath: [''],
      pillarId: [''],
      poolId: [''],
    });
  }

  onSelectTree(event) {
    if (this.translation.translate('infra.weld.in.sleeves') === event.srcElement.textContent) {
      this.checkSleeve = false;
      this.checkWeldSleeve = true;
      this.sleeveService.item.sleeveId = this.tabId;
      this.tabLayoutService.open({
        component: SleeveDatailComponent.name,
        header: 'sleeve.manage.detail.label',
        breadcrumb: [
          {label: this.translation.translate('infra.sleeves.management')},
          {
            label: this.translation.translate('sleeve.manage.detail.label')
          },
          {
            label: this.translation.translate('infra.weld.in.sleeves')
          }
        ],
        action: this.action,
        tabId: this.tabId,
      });
    }
    if (this.translation.translate('infra.sleeves.infomation') === event.srcElement.textContent) {
      this.checkSleeve = true;
      this.checkWeldSleeve = false;
      this.sleeveService.item.sleeveId = this.tabId;
      this.tabLayoutService.open({
        component: SleeveDatailComponent.name,
        header: 'sleeve.manage.detail.label',
        breadcrumb: [
          {label: this.translation.translate('infra.sleeves.management')},
          {
            label: this.translation.translate('sleeve.manage.detail.label')
          }
        ],
        action: this.action,
        tabId: this.tabId,
      });
    }
  }

  onClosed() {
    this.tabLayoutService.close({
      component: 'SleeveDatailComponent',
      header: '',
      action: this.action,
      tabId: this.tabId,
    });
    this.tabLayoutService.open({
      component: SleeveListComponent.name,
      header: 'infra.sleeves.management',
      breadcrumb: [
        {label: this.translation.translate('infra.sleeves.management')}
      ]
    });
  }

  saveOrEdit(id, action?: string) {
    this.sleeveService.id = id;
    this.sleeveService.action = action;
    if ('edit' === action) {
      this.tabLayoutService.open({
        component: SleeveSaveComponent.name,
        header: 'sleeve.manage.update.label',
        action: 'edit',
        tabId: id,
        breadcrumb: [
          {label: this.translation.translate('infra.sleeves.management')},
          {label: this.translation.translate('sleeve.manage.update.label')}
        ]
      });
    }
  }

  @HostListener('document:keydown', ['$event'])
  onEnter(event: KeyboardEvent): void {
    if ('Enter' === event.code || 'NumpadEnter' === event.code) {
      this.saveOrEdit(this.sleeveService.item.sleeveId, 'edit');
    }
  }
}
