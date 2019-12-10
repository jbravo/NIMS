import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';

import { SettingsRoutingModule } from './settings-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';

@NgModule({
  declarations: [DashboardComponent],
  imports: [
    CommonModule,
    SharedModule,
    SettingsRoutingModule
  ],
  exports: [

  ]
})
export class SettingsModule { }
