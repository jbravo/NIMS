import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';

import { LabourContractTypeRoutingModule } from './labour-contract-type-routing.module';
import { LabourContractTypeComponent } from './labour-contract-type.component';
import { LabourContractTypeSearchComponent } from './labour-contract-type-search/labour-contract-type-search.component';
import { LabourContractTypeFormComponent } from './labour-contract-type-form/labour-contract-type-form.component';

@NgModule({
  declarations: [LabourContractTypeComponent, LabourContractTypeSearchComponent, LabourContractTypeFormComponent],
  imports: [
    CommonModule,
    SharedModule,

    LabourContractTypeRoutingModule,
  ],
  entryComponents: [LabourContractTypeFormComponent]
})
export class LabourContractTypeModule { }
