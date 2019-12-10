import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';

import { AllowanceTypeRoutingModule } from './allowance-type-routing.module';
import { AllowanceTypePageComponent } from './pages/allowance-type-index/allowance-type-index.component';
import { AllowanceTypeSearchComponent } from './pages/allowance-type-search/allowance-type-search.component';
import { AllowanceTypeFormComponent } from './pages/allowance-type-form/allowance-type-form.component';

@NgModule({
  declarations: [AllowanceTypePageComponent, AllowanceTypeSearchComponent, AllowanceTypeFormComponent],
  imports: [
    CommonModule,
    SharedModule,

    AllowanceTypeRoutingModule,
  ],
  entryComponents: [AllowanceTypeFormComponent]
})
export class AllowanceTypeModule { }
