import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllowanceTypePageComponent } from './pages/allowance-type-index/allowance-type-index.component';

const routes: Routes = [
  {
    path: '',
    component: AllowanceTypePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AllowanceTypeRoutingModule { }
