import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '@app/shared';
import {WeldSleeveMgmtRoutingModule} from './weld-sleeve-mgmt-routing.module';
import {WeldSleeveListComponent} from './page/weld-sleeve-list/weld-sleeve-list.component';
import {WeldSleeveDetailComponent} from './page/weld-sleeve-detail/weld-sleeve-detail.component';
import {WeldSleeveService} from '@app/modules/weld-sleeve-mgmt/service/weld-sleeve.service';
import { WeldSleeveSaveComponent } from './page/weld-sleeve-save/weld-sleeve-save.component';import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {ToastModule} from 'primeng/toast';
import {SpinnerModule} from 'primeng/spinner';
import {InputMaskModule} from 'primeng/inputmask';
@NgModule({
  declarations: [WeldSleeveListComponent, WeldSleeveDetailComponent, WeldSleeveSaveComponent],
  imports: [
    CommonModule,
    SharedModule,
    WeldSleeveMgmtRoutingModule,
    MessagesModule,
    MessageModule,
    ToastModule,
    SpinnerModule,
    InputMaskModule
  ],
  exports: [WeldSleeveListComponent, WeldSleeveDetailComponent,WeldSleeveSaveComponent],
  entryComponents: [WeldSleeveListComponent, WeldSleeveDetailComponent,WeldSleeveSaveComponent],
  providers: [WeldSleeveService]
})
export class WeldSleeveMgmtModule {
}
