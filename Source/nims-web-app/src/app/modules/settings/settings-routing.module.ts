import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: []
  },
  {
    path: 'emp-type',
    loadChildren: './emp-type/emp-type.module#EmpTypeModule'
  },
  {
    path: 'nation',
    loadChildren: './nation/nation.module#NationModule'
  },
  {
    path: 'sys-cat',
    loadChildren: './sys-cat/sys-cat.module#SysCatModule'
  },
  {
    path: 'sys-property',
    loadChildren: './sys-property/sys-property.module#SysPropertyModule'
  },
  {
    path: 'allowance-type',
    loadChildren: './allowance-type/allowance-type.module#AllowanceTypeModule'
  },
  {
    path: 'labour_contract_type',
    loadChildren: './labour-contract-type/labour-contract-type.module#LabourContractTypeModule'
  },
  {
    path: 'work-flows',
    loadChildren: './work-flows/work-flows.module#WorkFlowsModule'
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SettingsRoutingModule { }
