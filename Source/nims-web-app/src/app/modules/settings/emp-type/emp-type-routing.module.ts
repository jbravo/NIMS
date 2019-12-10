import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmpTypePageComponent } from './emp-type-page.component';

const routes: Routes = [
  {
    path: '',
    component: EmpTypePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpTypeRoutingModule { }
