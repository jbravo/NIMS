import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import {CommonModule} from '@angular/common';
import {CableListComponent} from './page/cable-list/cable-list.component';
import {CableSaveComponent} from './page/cable-save/cable-save.component';
import {CablesManagementRoutingModule} from './cables-management-routing.module';
import {CableService} from '@app/modules/cables-management/service/cable.service';
import { AutoCompleteModule, CalendarModule, CheckboxModule, DataTableModule, DropdownModule, FileUploadModule, InputTextareaModule, MessagesModule, PaginatorModule, RadioButtonModule, TabViewModule } from 'primeng/primeng';
import { SharedModule } from '@app/shared';
import { DialogModule } from 'primeng/dialog';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import {KeyFilterModule} from 'primeng/keyfilter';


@NgModule({
  declarations: [CableListComponent, CableSaveComponent],
  exports: [CableListComponent, CableSaveComponent],
  entryComponents: [CableListComponent, CableSaveComponent],
  imports: [
    CommonModule,
    CablesManagementRoutingModule,
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
    KeyFilterModule
  ]
})
export class CablesManagementModule {
}
