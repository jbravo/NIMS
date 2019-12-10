import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SleevesManagementRoutingModule } from './sleves-management-routing.module';
import { SleeveListComponent } from './page/sleeve-list/sleeve-list.component';
import { SleeveSaveComponent } from '@app/modules/sleeve-management/page/sleeve-save/sleeve-save.component';
import { SleeveDatailComponent } from './page/sleeve-datail/sleeve-datail.component';
import { SleeveService } from './service/sleeve.service';
import {
  AutoCompleteModule, CalendarModule, CheckboxModule, DataTableModule, DropdownModule, FileUploadModule,
  InputTextareaModule, MessagesModule,
  PaginatorModule,
  RadioButtonModule, TabViewModule
} from 'primeng/primeng';
import {SharedModule} from '@app/shared';
import {DialogModule} from 'primeng/dialog';
import {MessageModule} from 'primeng/message';
import { InputMaskModule } from 'primeng/inputmask';
import { WeldSleeveMgmtModule } from '@app/modules/weld-sleeve-mgmt/weld-sleeve-mgmt.module';

@NgModule({
  declarations: [SleeveListComponent, SleeveSaveComponent, SleeveDatailComponent],
  imports: [
    CommonModule,
    SleevesManagementRoutingModule,
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
    InputMaskModule,
    WeldSleeveMgmtModule
  ],
  exports: [SleeveListComponent, SleeveSaveComponent, SleeveDatailComponent],
  entryComponents: [SleeveListComponent, SleeveSaveComponent, SleeveDatailComponent],
  providers: [SleeveService]
})
export class SleevesManagementModule { }
