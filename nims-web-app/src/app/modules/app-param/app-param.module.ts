import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';

import {AutoCompleteModule} from 'primeng/autocomplete';
import { AppParamRoutingModule } from './app-param-routing.module';
import {CalendarModule} from 'primeng/calendar';
import {CheckboxModule} from 'primeng/checkbox';
import { SaveAppParamComponent } from './page/save-update/save-app-param.component';
import { ListAppParamComponent } from './page/search-list/list-app-param.component';
import {TooltipModule} from 'primeng/tooltip';
import {RadioButtonModule} from 'primeng/radiobutton';

@NgModule({
  declarations: [
    SaveAppParamComponent,
    ListAppParamComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    AppParamRoutingModule,
    AutoCompleteModule,
    CalendarModule,
    TooltipModule,
    RadioButtonModule,
    CheckboxModule
  ],
  entryComponents: [SaveAppParamComponent, ListAppParamComponent],
  exports: [
  ],
})
export class AppParamModule { }
