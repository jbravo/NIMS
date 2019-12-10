import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SysPropertyRoutingModule } from './sys-property-routing.module';
import { SysPropertyIndexComponent } from './sys-property-index/sys-property-index.component';
import { SysPropertyFormComponent } from './sys-property-form/sys-property-form.component';
import { SharedModule } from '@app/shared';
import { SysPropertyConfigComponent } from './sys-property-config/sys-property-config.component';
import {SelectButtonModule} from 'primeng/primeng';

@NgModule({
  declarations: [SysPropertyIndexComponent, SysPropertyFormComponent, SysPropertyConfigComponent,  ],
  imports: [
    CommonModule,
    SharedModule,
    SysPropertyRoutingModule,
    SelectButtonModule
  ],
  entryComponents: [SysPropertyFormComponent]
})
export class SysPropertyModule { }
