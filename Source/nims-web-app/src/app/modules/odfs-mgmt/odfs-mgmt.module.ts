import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OdfListComponent } from './page/odf-list/odf-list.component';
import {
  MessagesModule,
  AutoCompleteModule,
} from 'primeng/primeng';
import { SharedModule } from '@app/shared';
import { ODFSaveComponent } from './page/odf-save/odf-save.component';
import { OdfService } from './service/odf.service';
// import { ODFManagementRoutingModule } from './odfs-mgmt-routing.module';
import { ToastModule } from 'primeng/toast';
import { InputMaskModule } from 'primeng/inputmask';
import { ODFEditComponent } from './page/odf-edit/odf-edit.component';
import { ODFViewComponent } from './page/odf-view/odf-view.component';
import { WeldingOdfsManagementModule } from '../welding-odf-management/welding-odf-management.module';

@NgModule({
  declarations: [
    ODFSaveComponent,
    OdfListComponent,
    ODFEditComponent,
    ODFViewComponent
  ],
  imports: [
    CommonModule,
    // ODFManagementRoutingModule,
    SharedModule,
    MessagesModule,
    AutoCompleteModule,
    ToastModule,
    InputMaskModule,
    WeldingOdfsManagementModule
  ],
  exports: [
    ODFSaveComponent,
    OdfListComponent,
    ODFEditComponent,
    ODFViewComponent
  ],
  entryComponents: [
    ODFSaveComponent,
    OdfListComponent,
    ODFEditComponent,
    ODFViewComponent

  ],
  providers: [OdfService]
})
export class ODFsMgmtModule {
}
