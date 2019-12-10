import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '@app/shared';
import {WeldingOdfListComponent} from './page/welding-odf-list/welding-odf-list.component';
import {WeldingOdfsManagementRoutingModule} from './welding-odf-management-routing.module';
import {WeldingOdfService} from './service/welding-odf.service';
import {WeldingOdfDetailComponent} from './page/welding-odf-detail/welding-odf-detail.component';
import {WeldingOdfCreateComponent} from './page/welding-odf-create/welding-odf-create.component';
import {WeldingOdfUpdateComponent} from './page/welding-odf-update/welding-odf-update.component';

@NgModule({
  declarations: [
    WeldingOdfListComponent,
    WeldingOdfDetailComponent,
    WeldingOdfCreateComponent,
    WeldingOdfUpdateComponent],
  imports: [
    CommonModule,
    WeldingOdfsManagementRoutingModule,
    SharedModule
  ],
  exports: [
    WeldingOdfListComponent,
    WeldingOdfDetailComponent,
    WeldingOdfUpdateComponent,
    WeldingOdfCreateComponent],
  entryComponents: [
    WeldingOdfListComponent,
    WeldingOdfDetailComponent,
    WeldingOdfUpdateComponent,
    WeldingOdfCreateComponent
  ],
  providers: [WeldingOdfService]
})
export class WeldingOdfsManagementModule {
}
