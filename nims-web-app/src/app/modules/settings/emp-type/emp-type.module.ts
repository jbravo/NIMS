import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';

import { EmpTypeRoutingModule } from './emp-type-routing.module';
import { EmpTypePageComponent } from './emp-type-page.component';
import { EmpTypeSearchComponent } from './emp-type-search/emp-type-search.component';
import { EmpTypeFormComponent } from './emp-type-form/emp-type-form.component';

@NgModule({
  declarations: [EmpTypePageComponent, EmpTypeSearchComponent, EmpTypeFormComponent],
  imports: [
    CommonModule,
    SharedModule,

    EmpTypeRoutingModule,
  ],
  entryComponents: [EmpTypeFormComponent]
})
export class EmpTypeModule { }
