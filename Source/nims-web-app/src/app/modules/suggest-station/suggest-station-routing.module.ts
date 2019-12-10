import { SuggestionStationComponent } from './suggestion-station.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

// tslint:disable-next-line:max-line-length
import { SuggestStationManagementComponent } from '@app/modules/suggest-station/pages/suggest-station-management/suggest-station-management.component';
import { RadioInformationComponent } from '@app/modules/suggest-station/pages/radio-information/radio-information.component';
import { RadioSuggestionInforComponent } from '@app/modules/suggest-station/pages/radio-suggestion-infor/radio-suggestion-infor.component';
import { Calloff2GComponent } from '@app/modules/suggest-station/pages/calloff2-g/calloff2-g.component';
import { SectorComponent } from '@app/modules/suggest-station/pages/sector/sector.component';
import { Calloff3GComponent } from '@app/modules/suggest-station/pages/calloff3-g/calloff3-g.component';
import { Calloff4GComponent } from '@app/modules/suggest-station/pages/calloff4-g/calloff4-g.component';
import { CallOffTruyenDanComponent } from '@app/modules/suggest-station/pages/call-off-truyen-dan/call-off-truyen-dan.component';
import { SuggestStationHolderComponent } from './pages/suggest-station-holder/suggest-station-holder.component';

const routes: Routes = [
  {
    path: '', component: SuggestionStationComponent
  },
  {
    path: 'station-management', component: SuggestStationManagementComponent
  },
  {
    path: 'calloff2g', component: Calloff2GComponent
  },
  {
    path: 'callof3g', component: Calloff3GComponent
  },
  {
    path: 'calloff4g', component: Calloff4GComponent
  },
  {
    path: 'radio-local-inof', component: RadioInformationComponent
  },
  {
    path: 'radio-suggestion-infor', component: RadioSuggestionInforComponent
  },
  {
    path: 'sector', component: SectorComponent
  },
  {
    path: 'action/:action', component: SuggestStationHolderComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SuggestStationRoutingModule {
}
