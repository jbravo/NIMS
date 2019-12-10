import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {TaFormControlRoutingModule} from './ta-form-control-routing.module';
import {FormlyModule} from '@ngx-formly/core';
import {InputTypeComponent} from './types/input-type/input-type.component';
import {config} from './types.config';
import {TestUiComponent} from './test-ui/test-ui.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  AutoCompleteModule,
  CalendarModule,
  CheckboxModule,
  DropdownModule,
  InputMaskModule,
  InputTextModule,
  MenuModule,
  OverlayPanelModule,
  PaginatorModule,
  PanelModule,
  TreeModule
} from 'primeng/primeng';
import {SelectTypeComponent} from './types/select-type/select-type.component';
import {CalendarTypeComponent} from './types/calendar-type/calendar-type.component';
import {TableModule} from 'primeng/table';
import {TextareaTypeComponent} from './types/textarea-type/textarea-type.component';
import {IConfig, NgxMaskModule} from 'ngx-mask';
import {InputTreeTypeComponent} from './types/input-tree-type/input-tree-type.component';

@NgModule({
  declarations: [InputTypeComponent, TestUiComponent, SelectTypeComponent, CalendarTypeComponent,
    TextareaTypeComponent, InputTreeTypeComponent],
  imports: [
    CommonModule,
    TaFormControlRoutingModule,
    FormlyModule.forRoot(config),
    ReactiveFormsModule,
    FormsModule,
    InputTextModule,
    DropdownModule,
    CalendarModule,
    TableModule,
    CheckboxModule,
    PanelModule,
    MenuModule,
    PaginatorModule,
    AutoCompleteModule,
    InputMaskModule,
    // NgxMaskModule,
    OverlayPanelModule,
    TreeModule,
    NgxMaskModule.forRoot()
  ]
})
export class TaFormControlModule {
}
