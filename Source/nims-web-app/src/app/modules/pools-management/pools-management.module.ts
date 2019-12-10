import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import {
  AutoCompleteModule, CalendarModule, CheckboxModule, DataTableModule, DropdownModule, FileUploadModule,
  InputTextareaModule, MessagesModule,
  PaginatorModule,
  RadioButtonModule, TabViewModule
} from 'primeng/primeng';
import {SharedModule} from '@app/shared';
import {DialogModule} from 'primeng/dialog';
import {MessageModule} from "primeng/message";
import { PoolListComponent } from './page/pool-list/pool-list.component';
import { PoolsManagementRoutingModule } from './pools-management-routing.module';
import { PoolService } from './service/pool.service';
import { PoolSaveComponent } from './page/pool-save/pool-save.component';
import { PoolDetailComponent } from './page/pool-detail/pool-detail.component';
import { SleeveListComponent } from '../sleeve-management/page/sleeve-list/sleeve-list.component';
import { SleevesManagementModule } from '../sleeve-management/sleves-management.module';

@NgModule({
  declarations: [PoolListComponent, PoolSaveComponent, PoolDetailComponent],
  imports: [
    CommonModule,
    // PoolsManagementRoutingModule,
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
    SleevesManagementModule
  ],
  entryComponents: [PoolListComponent, PoolSaveComponent, PoolDetailComponent],
  providers: [PoolService]
})
export class PoolsManagementModule { }
