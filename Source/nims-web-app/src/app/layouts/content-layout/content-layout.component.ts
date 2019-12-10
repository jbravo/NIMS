import {AfterViewInit, Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {LocaleService, TranslationService} from 'angular-l10n';
import {BreadcrumbService} from '@app/shared/services/breadcrumb.service';
import {ScrollPanel} from 'primeng/primeng';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {CommonParam, CommonParamObserver} from '@app/core/app-common-param';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {StationManagementComponent} from '@app/modules/map/stations/station-management/station-management.component';
import {CablesManagementComponent} from '@app/modules/map/cables/cables-management/cables-management.component';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';

@Component({
  selector: 'app-content-layout',
  templateUrl: './content-layout.component.html',
  styleUrls: ['./content-layout.component.css'],
  animations: [
    trigger('changeSide', [
      state('default', style({
        left: '100%'
      })),
      state('colspan', style({
        left: '50px'
      })),

      transition('default => colspan', [
        animate('0.5s')
      ]),
      transition('colspan => default', [
        animate('0.5s')
      ])
    ]),
    trigger('hiddenSide', [
      state('hidden', style({
        display: 'none'
      })),
      state('show', style({
        display: 'block'
      }))
    ])

  ]
})
export class ContentLayoutComponent implements OnInit, AfterViewInit, CommonParamObserver {
  @ViewChild('layoutMenuScroller') layoutMenuScrollerViewChild: ScrollPanel;
  @ViewChild('station') station: StationManagementComponent;
  @ViewChild('cable') cable: CablesManagementComponent;

  layoutMode = 'static';
  menuMode = 'light';
  profileMode = 'top';
  topbarMenuActive: boolean;
  overlayMenuActive: boolean;
  staticMenuDesktopInactive: boolean;
  staticMenuMobileActive: boolean;
  menuClick: boolean;
  topbarItemClick: boolean;
  activeTopbarItem: any;
  resetMenu: boolean;
  menuHoverActive: boolean;
  usermenuActive: boolean;
  usermenuClick: boolean;
  activeProfileItem: any;
  isChange: boolean;
  isHidden: boolean;
  isType: string;
  data: any;

  // isShowBtnSave :boolean;
  constructor(
    public locale: LocaleService,
    public translation: TranslationService,
    private breadcrumb: BreadcrumbService,
    private tabLayoutService: TabLayoutService,
    private renderer: Renderer2,
    private commonParam: CommonParam,
    private sysGridViewService: SysGridViewService) {
  }

  selectLanguage(language: string): void {
    this.locale.setCurrentLanguage(language);
  }

  ngOnInit() {
    this.isChange = true;
    this.isHidden = true;
    // this.isShowBtnSave=false;
    this.commonParam.attach(this);
    this.tabLayoutService.openDefaultTab();
  }

  hideContextMenu(isShow: boolean) {
  }

  onChange(isShow: boolean) {
    this.isHidden = isShow;
  }

  addItem(evt: string) {

  }

  locateFunction(longitude, latitude) {

  }


  focusItem(data: any) {
  }

  closePopupLeft() {
  }

  viewLayer(listObject: any[]) {
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.layoutMenuScrollerViewChild.moveBar();
    }, 100);
  }

  onLayoutClick() {
    if (!this.topbarItemClick) {
      this.activeTopbarItem = null;
      this.topbarMenuActive = false;
    }
    if (!this.usermenuClick && this.isSlim()) {
      this.usermenuActive = false;
      this.activeProfileItem = null;
    }
    if (!this.menuClick) {
      if (this.isHorizontal() || this.isSlim()) {
        this.resetMenu = true;
      }
      if (this.overlayMenuActive || this.staticMenuMobileActive) {
        this.hideOverlayMenu();
      }
      this.menuHoverActive = false;
    }
    this.topbarItemClick = false;
    this.menuClick = false;
    this.usermenuClick = false;
  }

  handleClickCloseSide(event) {
    this.isHidden = true;
  }

  onTopbarSwitchThemeClick(item: any) {
    if (item == 1) {
      this.renderer.addClass(document.body, 'dark_theme');
      this.renderer.removeClass(document.body, 'green_theme');
    }
    if (item == 2) {
      this.renderer.removeClass(document.body, 'green_theme');
      this.renderer.removeClass(document.body, 'dark_theme');
    }
    if (item == 3) {
      this.renderer.addClass(document.body, 'green_theme');
      this.renderer.removeClass(document.body, 'dark_theme');
    }
  }

  onMenuButtonClick(event) {
    this.menuClick = true;
    this.topbarMenuActive = false;
    this.isChange = !this.isChange;
    if (this.isOverlay()) {
      this.overlayMenuActive = !this.overlayMenuActive;
    } else {
      if (this.isDesktop()) {
        if (this.isSlim()) {
          this.layoutMode = 'static';
        } else if (this.isStatic()) {
          this.layoutMode = 'slim';
        }
        // this.staticMenuDesktopInactive = !this.staticMenuDesktopInactive;
      } else {
        this.staticMenuMobileActive = !this.staticMenuMobileActive;
      }
    }
    event.preventDefault();
  }

  onMenuClick(event) {
    this.menuClick = true;
    this.resetMenu = false;
  }

  onTopbarMenuButtonClick(event) {
    this.topbarItemClick = true;
    this.topbarMenuActive = !this.topbarMenuActive;
    this.hideOverlayMenu();
    event.preventDefault();
  }

  onTopbarItemClick(event, item) {
    this.topbarItemClick = true;
    if (this.activeTopbarItem === item) {
      this.activeTopbarItem = null;
    } else {
      this.activeTopbarItem = item;
    }
    event.preventDefault();
  }

  onTopbarSubItemClick(event) {
    let storage = HrStorage.getUserToken();
    storage.userId = event;
    this.sysGridViewService.getGridView({userId: storage.userId}).subscribe(res => {
      storage.userId = event;
      storage.sysGridView = res.data;
      HrStorage.setUserToken(storage);
    });
    // event.preventDefault();
  }

  hideOverlayMenu() {
    this.overlayMenuActive = false;
    this.staticMenuMobileActive = false;
  }

  isTablet() {
    const width = window.innerWidth;
    return width <= 1024 && width > 640;
  }

  isDesktop() {
    return window.innerWidth > 1024;
  }

  isMobile() {
    return window.innerWidth <= 640;
  }

  isStatic() {
    return this.layoutMode === 'static';
  }

  isHorizontal() {
    return this.layoutMode === 'horizontal';
  }

  isSlim() {
    return this.layoutMode === 'slim';
  }

  isOverlay() {
    return this.layoutMode === 'overlay';
  }

  showProperties(type: string, data: any) {
    // if (type == 'station') {
    //   if (this.station) {
    //     this.station.onChange(data);
    //   }
    // } else if (type == 'cables') {
    //   if (this.cable) {
    //     this.cable.onChange(data);
    //   }
    // }
    // this.isType = type;
    // this.data = data;
  }


}
