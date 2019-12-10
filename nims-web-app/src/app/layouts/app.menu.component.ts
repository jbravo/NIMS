import {Component, Input, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MenuItem} from 'primeng/primeng';
import {ActivatedRoute, Router} from '@angular/router';
import {BreadcrumbService} from '@app/shared/services/breadcrumb.service';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {ContentLayoutComponent} from '@app/layouts/content-layout/content-layout.component';
import {TabLayoutService} from '@app/layouts/tab-layout';
import {TranslationService} from 'angular-l10n';

@Component({
  selector: 'app-menu',
  template: `
      <ul app-submenu [item]="model" root="true" class="layout-menu"
          [reset]="reset" visible="true" parentActive="true"></ul>
  `
})
export class AppMenuComponent implements OnInit {

  @Input() reset: boolean;
  userToken = HrStorage.getUserToken();
  model: MenuItem[];
  url: any;

  constructor(private route: Router, private routes: ActivatedRoute,
              private breadcrumbService: BreadcrumbService, public app: ContentLayoutComponent,
              private tabLayoutService: TabLayoutService, private translationService: TranslationService
  ) {
  }

  ngOnInit() {
    this.model = [
      {
        label: 'Trang Chủ', icon: 'fa fa-fw fa-dashboard', routerLink: [''], command: event => {
          this.tabLayoutService.openDefaultTab();
        }
      },
      {
        label: this.translationService.translate('map.header'),
        icon: 'fa fa-map-marker',
        routerLink: [''],
        command: event => {
          this.tabLayoutService.openDefaultTab();
        }
      },
      {
        label: this.translationService.translate('station.manage.label'),
        icon: 'fa fa-home',
        routerLink: [''],
        command: event => {
          this.tabLayoutService.open({
            component: 'StationListComponent',
            header: 'station.manage.label',
            breadcrumb: [
              {label: this.translationService.translate('station.manage.label')}
            ]
          });
        }
      },
      {
        label: this.translationService.translate('odf.tab.list'),
        icon: 'fa fa-bars',
        routerLink: [''],
        command: event => {
          this.tabLayoutService.open({
            component: 'OdfListComponent',
            header: 'odf.tab.list',
            breadcrumb: [
              {label: this.translationService.translate('odf.tab.list')}
            ]
          });
        }
      },
      // {
      //   label: this.translationService.translate('weldingOdf.tab'),
      //   icon: 'fa fa-plug',
      //   routerLink: [''],
      //   command: event => {
      //     this.tabLayoutService.open({
      //       component: 'WeldingOdfListComponent',
      //       header: 'weldingOdf.tab'
      //     });
      //   },
      // },
      {
        label: this.translationService.translate('cable.manage.label'),
        icon: 'fa fa-sliders',
        command: event => {
          this.tabLayoutService.open({
            component: 'CableListComponent',
            header: 'cable.manage.label',
            breadcrumb: [
              {label: this.translationService.translate('cable.manage.label')}
            ]
          });
        },
        routerLink: ['']
      },
      {
        label: this.translationService.translate('pillar.manage.label'),
        icon: 'fa fa-columns',
        title: 'Pillar',
        command: event => {
          this.tabLayoutService.open({
            component: 'PillarListComponent',
            header: 'pillar.manage.label',
            breadcrumb: [
              {label: this.translationService.translate('pillar.manage.label')}
            ]
          });
        },
        routerLink: ['']
      },
      {
        label: this.translationService.translate('pool.header'),
        icon: 'fa fa-cogs',
        command: event => {
          this.tabLayoutService.open({
            component: 'PoolListComponent',
            header: 'pool.header',
            breadcrumb: [
              {label: this.translationService.translate('pool.header')}
            ]
          });
        },
        routerLink: ['']
      },
      {
        label: this.translationService.translate('infra.sleeves.management'),
        icon: 'fa fa-fw fa-road',
        routerLink: [''],
        command: event => {
          this.tabLayoutService.open({
            component: 'SleeveListComponent',
            header: 'infra.sleeves.management',
            breadcrumb: [
              {label: this.translationService.translate('infra.sleeves.management')}
            ]
          });
        }
      },
      {
        label: this.translationService.translate('weldingSleeve.tab.header'),
        icon: 'fa fa-plug',
        command: event => {
          this.tabLayoutService.open({
            component: 'WeldSleeveListComponent',
            header: 'weldingSleeve.tab.header'
          });
        },
        routerLink: ['']
      },
      {
        label: this.translationService.translate('menu.header.cate'),
        icon: 'fa fa-folder-open-o',
        items: [
          {
            label: this.translationService.translate('pillar.type.tab.header'),
            icon: 'fa fa-columns',
            command: event => {
              this.tabLayoutService.open({
                component: 'CatPillarTypeComComponent',
                header: 'pillar.type.tab.header'
              });
            },
            routerLink: ['']
          },
          {
            label: this.translationService.translate('sleeve.type.tab.header'),
            icon: 'fa fa-fw fa-road',
            command: event => {
              this.tabLayoutService.open({
                component: 'CatSleeveTypeComComponent',
                header: 'sleeve.type.tab.header'
              });
            },
            routerLink: ['']
          },
          {
            label: this.translationService.translate('cat.department.label'),
            icon: 'fa fa-list-ul',
            command: event => {
              this.tabLayoutService.open({
                component: 'CatDepartmentComComponent',
                header: 'cat.department.tab.header'
              });
            },
            routerLink: ['']
          }
        ]
      },
      {
        label: 'Station',
        icon: 'fa fa-thumb-tack',
        title: 'Station',
        command: event => {
          this.tabLayoutService.open({
            component: 'SuggestionStationComponent',
            header: 'Station'
          });
        },
        routerLink: ['']
      }
    ];
  }
}

@Component({
  selector: '[app-submenu]',
  template: `
      <ng-template ngFor let-child let-i="index" [ngForOf]="(root ? item : item.items)">
          <li [ngClass]="{'active-menuitem': isActive(i)}" [class]="child.badgeStyleClass"
              *ngIf="child.visible === false ? false : true" pTooltip="{{child.label}}">
              <a [href]="child.url||'#'" (click)="itemClick($event,child,i)" (mouseenter)="onMouseEnter(i)"
                 class="ripplelink" *ngIf="!child.routerLink"
                 [attr.tabindex]="!visible ? '-1' : null" [attr.target]="child.target">
                  <i [ngClass]="child.icon"></i><span>{{child.label}}</span>
                  <span class="menuitem-badge" *ngIf="child.badge">{{child.badge}}</span>
                  <i class="fa fa-fw fa-angle-down layout-menuitem-toggler" *ngIf="child.items"></i>
              </a>

              <a (click)="itemClick($event,child,i)" (mouseenter)="onMouseEnter(i)" class="ripplelink"
                 *ngIf="child.routerLink"
                 [routerLink]="child.routerLink" routerLinkActive="active-menuitem-routerlink"
                 [routerLinkActiveOptions]="{exact: true}" [attr.tabindex]="!visible ? '-1' : null"
                 [attr.target]="child.target">
                  <i [ngClass]="child.icon"></i><span>{{child.label}}</span>
                  <span class="menuitem-badge" *ngIf="child.badge">{{child.badge}}</span>
                  <i class="fa fa-fw fa-angle-down layout-menuitem-toggler" *ngIf="child.items"></i>
              </a>
              <div class="submenu-arrow" *ngIf="child.items"></div>
              <ul app-submenu [item]="child" *ngIf="child.items" [visible]="isActive(i)" [reset]="reset"
                  [parentActive]="isActive(i)"
                  [@children]="(app.isSlim()||app.isHorizontal())&&root ? isActive(i) ?
                    'visible' : 'hidden' : isActive(i) ? 'visibleAnimated' : 'hiddenAnimated'"></ul>
          </li>
      </ng-template>
  `,
  animations: [
    trigger('children', [
      state('hiddenAnimated', style({
        height: '0px',
        opacity: 0
      })),
      state('visibleAnimated', style({
        height: '*',
        opacity: 1
      })),
      state('visible', style({
        height: '*',
        'z-index': 100,
        opacity: 1
      })),
      state('hidden', style({
        height: '0px',
        'z-index': '*',
        opacity: 0
      })),
      transition('visibleAnimated => hiddenAnimated', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
      transition('hiddenAnimated => visibleAnimated', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
    ])
  ]
})
export class AppSubMenuComponent {

  @Input() item: MenuItem;

  @Input() root: boolean;

  @Input() visible: boolean;
  activeIndex: number;

  constructor(public app: ContentLayoutComponent) {
  }

  _reset: boolean;

  @Input() get reset(): boolean {
    return this._reset;
  }

  set reset(val: boolean) {
    this._reset = val;

    if (this._reset && (this.app.isHorizontal() || this.app.isSlim())) {
      this.activeIndex = null;
    }
  }

  _parentActive: boolean;

  @Input() get parentActive(): boolean {
    return this._parentActive;
  }

  set parentActive(val: boolean) {
    this._parentActive = val;

    if (!this._parentActive) {
      this.activeIndex = null;
    }
  }

  itemClick(event, item: MenuItem, index: number) {
    if (this.root) {
      this.app.menuHoverActive = !this.app.menuHoverActive;
    }

    // avoid processing disabled items
    if (item.disabled) {
      event.preventDefault();
      return true;
    }

    // activate current item and deactivate active sibling if any
    this.activeIndex = (this.activeIndex === index) ? null : index;

    // execute command
    if (item.command) {
      item.command({originalEvent: event, item: item});
    }

    // prevent hash change
    if (item.items || (!item.url && !item.routerLink)) {
      setTimeout(() => {
        this.app.layoutMenuScrollerViewChild.moveBar();
      }, 450);
      event.preventDefault();
    }

    // hide menu
    if (!item.items) {
      if (this.app.isHorizontal() || this.app.isSlim()) {
        this.app.resetMenu = true;
      } else {
        this.app.resetMenu = false;
      }

      this.app.overlayMenuActive = false;
      this.app.staticMenuMobileActive = false;
      this.app.menuHoverActive = !this.app.menuHoverActive;
    }
  }

  onMouseEnter(index: number) {
    if (this.root && this.app.menuHoverActive && (this.app.isHorizontal() || this.app.isSlim())
      && !this.app.isMobile() && !this.app.isTablet()) {
      this.activeIndex = index;
    }
  }

  isActive(index: number): boolean {
    return this.activeIndex === index;
  }
}
