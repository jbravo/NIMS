import { Component, ElementRef, OnDestroy, OnInit, Renderer2, Type, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { TabLayoutService } from './tab-layout.service';
import { MenuItem, MessageService } from 'primeng/api';
import { TranslationService } from 'angular-l10n';
import { AppComponent } from '@app/app.component';


export class TabConfig {
  component: string;
  selector?: Type<any>;
  isTranslateHeader?: boolean;
  header?: string;
  active?: boolean;
  breadcrumb?: MenuItem[];
  isContentHasChanged?: boolean;
  key?: string;
  action?: string;
  tabId?: number;
  reload?: boolean;
}

@Component({
  selector: 'app-tab-layout',
  templateUrl: './tab-layout.component.html',
  styleUrls: ['./tab-layout.component.css']
})
export class TabLayoutComponent implements OnInit, OnDestroy {

  tabSubscription: Subscription;
  contentHasChangedSubscription: Subscription;
  tabs: TabConfig[];
  private _keyToastConfirm = 'tab-confirm';
  private _currentTab: TabConfig;
  private escapeListener: Function;
  parent: any;

  @ViewChild('focus') searchElement: ElementRef;
  displayConfirmCloseTab = false;

  constructor(
    public tabLayoutService: TabLayoutService,
    private messageService: MessageService,
    private translationService: TranslationService,
    private renderer: Renderer2,
    private app: AppComponent
  ) {
  }

  ngOnInit(): void {
    this.tabSubscription = this.tabLayoutService.tabsChange.subscribe(_tabs => {
      this.tabs = [];
      _tabs.forEach(_tab => {
        this.tabs.push(_tab);
      });
    });
    this.app.storage();
  }

  ngOnDestroy(): void {
    if (this.tabSubscription) {
      this.tabSubscription.unsubscribe();
    }
    if (this.contentHasChangedSubscription) {
      this.contentHasChangedSubscription.unsubscribe();
    }
  }

  open(tab: TabConfig) {
    this.tabLayoutService.open(tab);
  }

  befClose(tab: TabConfig, parent?: any) {
    this.parent = null;
    if (tab) {
      this._currentTab = tab;
      if (tab.isContentHasChanged) {
        this.openConfirm();
      } else {
        this.close(null);
      }
    }
    if (parent) {
      this.parent = parent;
    }
  }

  close(event) {
    this.tabLayoutService.close(this._currentTab);
    this.cancel(event);

    if (this.parent) {
      this.tabLayoutService.open({
        component: this.parent.component ? this.parent.component : '',
        header: this.parent.header ? this.parent.header : '',
        breadcrumb: this.parent.breadcrumb ? this.parent.breadcrumb : null,
        action: this.parent.action ? this.parent.action : '',
        tabId: this.parent.id ? this.parent.id : null,
      });
    }
  }

  cancel(event) {
    this.closeConfirm();
    this.displayConfirmCloseTab = false;
    if (event) {
      event.preventDefault();
    }
  }

  private openConfirm() {
    this.closeConfirm();
    this.displayConfirmCloseTab = true;
    setTimeout(() => {
      this.searchElement.nativeElement.focus();
    }, 0);
    // this.messageService.add(
    //   {
    //     key: this._keyToastConfirm,
    //     sticky: true,
    //     summary: this.translationService.translate('common.message.confirm.closeTab'),
    //     detail: this.translationService.translate('common.message.confirm.closeTab.detail')
    //   }
    // );
    this.bindEscapeListener();
  }

  private closeConfirm() {
    this.messageService.clear(this._keyToastConfirm);
    this.unbindEscapeListener();
  }

  private bindEscapeListener() {
    this.escapeListener = this.renderer.listen('document', 'keydown', (event) => {
      if (event.which === 27) {
        this.cancel(event);
      }
    });
  }

  private unbindEscapeListener() {
    if (this.escapeListener) {
      this.escapeListener();
      this.escapeListener = null;
    }
  }
}
