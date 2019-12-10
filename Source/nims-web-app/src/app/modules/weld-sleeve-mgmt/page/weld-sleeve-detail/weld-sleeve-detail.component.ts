import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {BreadcrumbService} from '@app/shared/services/breadcrumb.service';
import {WeldSleeveService} from '@app/modules/weld-sleeve-mgmt/service/weld-sleeve.service';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {error} from 'util';
import {TranslationService} from 'angular-l10n';
import {Subscription} from 'rxjs';
import {SleeveService} from '@app/modules/sleeve-management/service/sleeve.service';

@Component({
  selector: 'weld-sleeve-detail',
  templateUrl: './weld-sleeve-detail.component.html',
  styleUrls: ['./weld-sleeve-detail.component.css']
})
export class WeldSleeveDetailComponent implements OnInit, OnDestroy, AfterViewInit {
  item;
  sleeveId;
  cableSourceId;
  cableSourceNo;
  cableDestId;
  cableDestNo;
  data;
  private contentHasChangedSub: Subscription;
  action: string;
  tabId: number;
  isTabChanged: string;
  private _genKey: string = '_';

  constructor(private breadcrumbService: BreadcrumbService,
              private weldSleeveService: WeldSleeveService,
              private tabLayoutService: TabLayoutService,
              private translation: TranslationService,
              private sleeveService: SleeveService) {

  }

  ngOnInit() {
    this.data = this.weldSleeveService.data;
    this.sleeveId = this.sleeveService.item.sleeveId;
  }

  //Thoat START
  onClosed() {
    this.tabLayoutService.close({
      component: 'WeldSleeveDetailComponent',
    });
    this.tabLayoutService.close({
      component: 'WeldSleeveListComponent',
    });
    //truyen sleeveId ve man list
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

  //Thoat END
  //Cap nhat START
  update() {

    this.weldSleeveService.data = this.data;
    console.log(this.weldSleeveService.data);
    this.weldSleeveService.action = 'edit';
    this.tabLayoutService.close({
      component: 'WeldSleeveSaveComponent',
    });
    this.tabLayoutService.open({
      component: 'WeldSleeveSaveComponent',
      header: 'weldingSleeve.tab.update.header',
      breadcrumb: [
        {label: this.translation.translate('weldingSleeve.tab.header')},
        {label: this.translation.translate('weldingSleeve.tab.update.header')},
      ]
    });
  }

  //Cap nhat END

  ngAfterViewInit(): void {
    // this.contentHasChangedSub = this.formAdd.statusChanges.subscribe(val => {
    //   this.isTabChanged = val;
    //   const action = this.action ? this._genKey + this.action : '';
    //   const tabId = this.tabId ? this._genKey + this.tabId : '';
    //   const key = WeldSleeveDetailComponent.name + action + tabId;
    //   this.tabLayoutService.isTabContentHasChanged({component: WeldSleeveDetailComponent.name, key});
    // });
  }

  ngOnDestroy(): void {
    if (this.contentHasChangedSub) {
      this.contentHasChangedSub.unsubscribe();
    }
  }

}
