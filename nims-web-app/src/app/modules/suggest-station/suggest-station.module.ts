import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SuggestStationRoutingModule} from './suggest-station-routing.module';
import {SuggestionStationComponent} from './suggestion-station.component';
import {TaFormControlModule} from '@app/modules/ta-form-control/ta-form-control.module';
import {PanelModule} from 'primeng/panel';
import {FormlyModule} from '@ngx-formly/core';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {TableModule} from 'primeng/table';
import {ButtonModule, CheckboxModule, FieldsetModule, MenuModule, MessagesModule, ProgressBarModule} from 'primeng/primeng';
import {SuggestStationManagementComponent} from './pages/suggest-station-management/suggest-station-management.component';
import {RadioInformationComponent} from './pages/radio-information/radio-information.component';
import {RadioSuggestionInforComponent} from './pages/radio-suggestion-infor/radio-suggestion-infor.component';
import {Calloff2GComponent} from './pages/calloff2-g/calloff2-g.component';
import {TabViewModule} from 'primeng/tabview';
import {Calloff4GComponent} from '@app/modules/suggest-station/pages/calloff4-g/calloff4-g.component';
import {Calloff3GComponent} from '@app/modules/suggest-station/pages/calloff3-g/calloff3-g.component';
import {MultiSelectModule} from 'primeng/multiselect';
import {TreeModule} from 'primeng/tree';
import {ToastModule} from 'primeng/toast';
import {SectorComponent} from '@app/modules/suggest-station/pages/sector/sector.component';
import {L10nLoader, TranslationModule} from 'angular-l10n';
import {CallOffTruyenDanComponent} from './pages/call-off-truyen-dan/call-off-truyen-dan.component';
import {Sector4gComponent} from './pages/sector4g/sector4g.component';
import {Sector3gComponent} from './pages/sector3g/sector3g.component';

import {SharedModule} from '@app/shared';
import {SuggestStationHolderComponent} from './pages/suggest-station-holder/suggest-station-holder.component';
import {TechAsianSharedModule} from '@app/shared/tech-asian-shared/tech-asian-shared.module';

@NgModule({
  declarations: [SuggestionStationComponent, SuggestStationManagementComponent,
    RadioInformationComponent, RadioSuggestionInforComponent, Calloff2GComponent,
    Calloff4GComponent, Calloff3GComponent, SectorComponent, CallOffTruyenDanComponent, Sector4gComponent, Sector3gComponent,
    SuggestStationHolderComponent
  ],
  imports: [
    CommonModule,
    SuggestStationRoutingModule,
    TechAsianSharedModule,
    SharedModule,
    MessagesModule
  ],
  entryComponents:[
    SuggestionStationComponent,

  ]
})
export class SuggestStationModule {
  constructor(public l10nLoader: L10nLoader) {
    this.l10nLoader.load();
  }
}
