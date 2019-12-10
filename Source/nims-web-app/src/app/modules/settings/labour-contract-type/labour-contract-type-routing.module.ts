import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LabourContractTypeComponent } from './labour-contract-type.component';

const routes: Routes = [
  {
    path: '',
    component: LabourContractTypeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LabourContractTypeRoutingModule { }
