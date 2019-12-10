import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';

import {CoreModule} from '@app/core';
import {SharedModule} from '@app/shared';
import {ToastModule} from 'primeng/toast';
import {BlockUIModule} from 'primeng/blockui';
import {CheckboxModule, ToolbarModule} from 'primeng/primeng';
import {MenuModule} from 'primeng/menu';
import {InputMaskModule} from 'primeng/inputmask';
import {DynamicDialogModule} from 'primeng/dynamicdialog';
import {MessageService} from 'primeng/api';
import {ToastrModule} from 'ngx-toastr';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';

import {L10nConfig, L10nLoader, ProviderType, StorageStrategy, TranslationModule} from 'angular-l10n';
import {AppMenuComponent, AppSubMenuComponent} from '@app/layouts/app.menu.component';
import {AppBreadcrumbComponent} from '@app/layouts/app.breadcrumb.component';
import {AppTopBarComponent} from '@app/layouts/app.topbar.component';
import {AppFooterComponent} from '@app/layouts/app.footer.component';
import {AppProfileComponent} from '@app/layouts/app.profile.component';
import {ContentLayoutComponent} from '@app/layouts/content-layout/content-layout.component';
import {MapComponent} from './modules/map/map.component';
import {TabLayoutComponent, TabLayoutContentDirective, TabLayoutService} from '@app/layouts/tab-layout';
import {CablesManagementModule} from '@app/modules/cables-management/cables-management.module';
import {StationsManagementModule} from './modules/stations-management/stations-management.module';
import {ODFsMgmtModule} from './modules/odfs-mgmt/odfs-mgmt.module';
import {PoolsManagementModule} from './modules/pools-management/pools-management.module';
import {PillarsMgmtModule} from '@app/modules/pillars-mgmt/pillars-mgmt.module';
import {SleevesManagementModule} from './modules/sleeve-management/sleves-management.module';
import {CatPillarTypeModule} from './modules/cat-type/cat-pillar-type/cat-pillar-type.module';
import {CatSleeveTypeModule} from './modules/cat-type/cat-sleeve-type/cat-sleeve-type.module';
import {CatDepartmentModule} from './modules/cat-type/cat-department/cat-department.module';
import {StationManagementComponent} from './modules/map/stations/station-management/station-management.component';

import {WeldSleeveMgmtModule} from '@app/modules/weld-sleeve-mgmt/weld-sleeve-mgmt.module';
import {MapCablesComponent} from './layouts/map-cables/map-cables.component';
import {CableLanesComponent} from './modules/map/cable-lanes/cable-lanes.component';
import {MapSidebarDirective} from './modules/map/map-sidebar.directive';

import {CablesManagementComponent} from './modules/map/cables/cables-management/cables-management.component';
import {ListViewMapComponent} from '@app/modules/map/list-view-map/list-view-map.component';
import {ConfigMapComponent} from './modules/map/config-map/config-map.component';
import {ObjectAddComponent} from './modules/map/config-map/object-add/object-add.component';
import {LocateComponent} from './modules/map/locate/locate.component';
import {NgxSpinnerModule} from 'ngx-spinner';
import {AutocompleteSelectObjComponent} from './modules/map/config-map/autocomplete-select-obj/autocomplete-select-obj.component';

import {SuggestStationModule} from '@app/modules/suggest-station/suggest-station.module';
import {SearchAddressComponent} from './modules/map/search-address/search-address.component';
import {CableDetailComponent} from './modules/map/view/cable-detail/cable-detail.component';
import {ViewLayerComponent} from '@app/modules/map/view-layer/view-layer.component';
import {StationMapDetailComponent} from '@app/modules/map/view/station-detail/station-detail.component';
import {PoolsComponent} from '@app/modules/map/pools/pools.component';

const l10nConfig: L10nConfig = {
  logger: {
    // level: LogLevel.Warn
  },
  locale: {
    languages: [
      {code: 'en', dir: 'ltr'},
      {code: 'vi', dir: 'ltr'}
    ],
    language: 'vi',
    storage: StorageStrategy.Cookie
  },
  translation: {
    providers: [
      {type: ProviderType.Static, prefix: './assets/locale/locale-'}
    ],
    caching: true,
    // composedKeySeparator: '.',
    missingValue: 'No key'
  }
};

export function initL10n(l10nLoader: L10nLoader): Function {
  return () => l10nLoader.load();
}

@NgModule({
  declarations: [
    AppComponent,
    AppMenuComponent,
    AppSubMenuComponent,
    AppBreadcrumbComponent,
    AppTopBarComponent,
    AppFooterComponent,
    AppProfileComponent,
    ContentLayoutComponent,
    TabLayoutComponent,
    TabLayoutContentDirective,
    MapComponent,
    TabLayoutComponent,
    MapCablesComponent,
    MapSidebarDirective,
    CableLanesComponent,
    StationManagementComponent,
    CablesManagementComponent,
    ListViewMapComponent,
    LocateComponent,
    ConfigMapComponent,
    ObjectAddComponent,
    AutocompleteSelectObjComponent,
    ViewLayerComponent,
    SearchAddressComponent,
    StationMapDetailComponent,
    CableDetailComponent,
    PoolsComponent
  ],
  imports: [
    // angular
    BrowserModule,
    BrowserAnimationsModule,
    DynamicDialogModule,

    // 3rd party
    ToastModule,
    BlockUIModule,
    ToastrModule.forRoot(),

    // core & shared
    CoreModule,
    SharedModule,
    NgxSpinnerModule,

    // app
    AppRoutingModule,
    TranslationModule.forRoot(l10nConfig),

    // nims
    // Station
    StationsManagementModule,
    SleevesManagementModule,
    ODFsMgmtModule,
    PillarsMgmtModule,
    CablesManagementModule,
    PoolsManagementModule,
    // WeldingOdfsManagementModule,
    // Weld-Sleeve
    WeldSleeveMgmtModule,
    CheckboxModule,
    ToolbarModule,
    MenuModule,
    InputMaskModule,
    // Cat-Type
    CatPillarTypeModule,
    CatSleeveTypeModule,
    CatDepartmentModule,
    // optimal-design
    SuggestStationModule
  ],
  entryComponents: [
    TabLayoutComponent,
    MapComponent,
    CablesManagementComponent,
    LocateComponent,
    ConfigMapComponent,
    ObjectAddComponent,
    StationMapDetailComponent,
    SearchAddressComponent,
    CableDetailComponent,
    ViewLayerComponent
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initL10n,
      deps: [L10nLoader],
      multi: true
    },
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    AppComponent, MessageService, TabLayoutService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(public l10nLoader: L10nLoader) {
    this.l10nLoader.load();
  }
}
