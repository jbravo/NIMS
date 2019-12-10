import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PillarsMgmtRoutingModule} from './pillars-mgmt-routing.module';
import {PillarListComponent} from './page/pillar-list/pillar-list.component';
import {
  AutoCompleteModule,
  CalendarModule,
  CheckboxModule,
  DataTableModule,
  DropdownModule,
  FileUploadModule,
  InputTextareaModule,
  MessagesModule,
  PaginatorModule,
  RadioButtonModule,
  TabViewModule
} from 'primeng/primeng';
import {SharedModule} from '@app/shared';
import {DialogModule} from 'primeng/dialog';
import {MessageModule} from 'primeng/message';
import {PillarService} from './service/pillar.service';
import {MultiSelectModule} from 'primeng/multiselect';
import {PillarSaveComponent} from './page/pillar-save/pillar-save.component';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {KeyFilterModule} from 'primeng/keyfilter';
import {PillarDetailComponent} from './page/pillar-detail/pillar-detail.component';
import {CableLaneListComponent} from './page/cable-lane-list/cable-lane-list.component';
import {SleevesManagementModule} from '../sleeve-management/sleves-management.module';

@NgModule({
  declarations: [PillarListComponent, PillarSaveComponent, PillarDetailComponent, CableLaneListComponent],
  imports: [
    CommonModule,
    PillarsMgmtRoutingModule,
    SharedModule,
    CheckboxModule,
    RadioButtonModule,
    PaginatorModule,
    DataTableModule,
    AutoCompleteModule,
    FileUploadModule,
    DialogModule,
    TabViewModule,
    DropdownModule,
    InputTextareaModule,
    CalendarModule,
    MessagesModule,
    MessageModule,
    MultiSelectModule,
    OverlayPanelModule,
    KeyFilterModule,
    SleevesManagementModule
  ],
  exports: [
    PillarListComponent,
    PillarSaveComponent,
    PillarDetailComponent
  ],
  entryComponents: [
    PillarListComponent,
    PillarSaveComponent,
    PillarDetailComponent
  ],
  providers: [PillarService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillarsMgmtModule {
}
