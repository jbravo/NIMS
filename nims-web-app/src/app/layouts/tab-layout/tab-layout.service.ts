import { ComponentFactoryResolver, Injectable, Type } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { TabConfig } from './tab-layout.component';
import { TranslationService } from 'angular-l10n';
import { CommonParam } from '@app/core/app-common-param';
import { EventBusService } from '@app/shared/services/event-bus.service';

@Injectable({
  providedIn: 'root'
})
export class TabLayoutService {

  private _tabs = new BehaviorSubject<Map<String, TabConfig>>(new Map<String, TabConfig>());
  private _keyConcat = '_';
  tabsChange = this._tabs.asObservable();
  constructor(
    private resolver: ComponentFactoryResolver,
    private translation: TranslationService,
    private commonParam: CommonParam,
    private eventBusService: EventBusService,
  ) {
  }

  isTabContentHasChanged(tab: TabConfig): void {
    this.checkComponentType(tab);
    if (tab.component) {
      const _tabs = this._tabs.getValue();
      const _tab = _tabs.get(tab.key);
      _tab.isContentHasChanged = true;
    }
  }

  openDefaultTab(): void {
    this.open({
      component: 'MapComponent',
      header: 'map.header'
    });
  }

  open(tab: TabConfig): void {
    this.checkComponentType(tab);
    if (this.isNotExit(tab)) {
      this.add(tab);
    }
    this.active(tab);

  }

  close(tab: TabConfig): void {
    this.checkComponentType(tab);
    if (!this.isNotExit(tab)) {
      // clear tab
      const _tabs = this._tabs.getValue();
      this.genKeyTab(tab);
      _tabs.delete(tab.key);
      this._tabs.next(_tabs);
      // active other tab
      this.reActive(_tabs);
    }
  }

  currentTab(selectorName: string): TabConfig {
    const _tabs = this._tabs.getValue();
    const _tab = _tabs.get(selectorName);
    return _tab;
  }

  private isNotExit(tab: TabConfig): boolean {
    const _tabs = this._tabs.getValue();
    this.genKeyTab(tab);
    return !_tabs.has(tab.key);
  }

  private add(tab: TabConfig): void {
    if (this.isNotExit(tab)) {
      // add tab
      const _tabs = this._tabs.getValue();
      this.genKeyTab(tab);
      _tabs.set(tab.key, tab);
      this._tabs.next(_tabs);
    }
  }

  private active(tab: TabConfig): void {
    this.inactiveAll();
    // console.log(tab);
    this.eventBusService.emitDataChange({
      type: 'onTabChangeActive',
      data: tab
    });
    // console.log(btoa(JSON.stringify(tab)));
    // sessionStorage.setItem(btoa('onTabChangeActive'), btoa(JSON.stringify(tab)));
    const _tabs = this._tabs.getValue();
    this.genKeyTab(tab);
    if (_tabs.has(tab.key)) {
      const _tab = _tabs.get(tab.key);
      _tab.active = true;
      if (tab.breadcrumb) {
        _tab.breadcrumb = tab.breadcrumb;
      }
      _tabs.set(tab.key, _tab);
      this._tabs.next(_tabs);
    }
    this.commonParam.hideContextMenu(false);
  }

  private inactiveAll(): void {
    const _tabs = this._tabs.getValue();
    _tabs.forEach(_tab => _tab.active = false);
  }

  private checkComponentType(tab: TabConfig): void {
    const factories = Array.from(this.resolver['_factories'].keys());
    if (tab.component) {
      tab.selector = <Type<any>>factories.find((x: any) => x.name === tab.component);
    }

    if (tab.header && !tab.isTranslateHeader) {
      tab.header = this.translation.translate(tab.header);
      tab.isTranslateHeader = true;
    }
  }

  private reActive(_tabs: Map<String, TabConfig>): void {
    if (_tabs.size <= 0) {
      this.openDefaultTab();
    }
    _tabs.forEach(_tab => {
      this.active(_tab);
      return;
    });
  }

  private genKeyTab(tab: TabConfig) {
    const action = tab.action ? this._keyConcat + tab.action : '';
    const tabId = tab.tabId ? this._keyConcat + tab.tabId : '';
    const name = tab.selector.name ? tab.selector.name : '';
    tab.key = name + action + tabId;
    return tab.key;
  }


}
