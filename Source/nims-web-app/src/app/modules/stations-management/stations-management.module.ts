import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StationsManagementRoutingModule} from './stations-management-routing.module';
import {StationListComponent} from './page/station-list/station-list.component';
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
import {StationSaveComponent} from '@app/modules/stations-management/page/station-save/station-save.component';
import {ToastModule} from 'primeng/toast';
import {StationDetailComponent} from './page/station-detail/station-detail.component';
import {ODFsMgmtModule} from '../odfs-mgmt/odfs-mgmt.module';
import {WeldingOdfsManagementModule} from '@app/modules/welding-odf-management/welding-odf-management.module';
import { OdfStationsComponent } from './page/odf-stations/odf-stations.component';

@NgModule({
  declarations: [StationListComponent, StationSaveComponent, StationDetailComponent, OdfStationsComponent],
  exports: [StationListComponent, StationSaveComponent],
  entryComponents: [StationListComponent, StationSaveComponent],
  imports: [
    CommonModule,
    StationsManagementRoutingModule,
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
    ToastModule,
    WeldingOdfsManagementModule,
    ODFsMgmtModule
  ]
})
export class StationsManagementModule {
}
