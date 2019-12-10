import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkFlowsIndexComponent } from './work-flows-index/work-flows-index.component';

const routes: Routes = [
  {
    path: '',
    component: WorkFlowsIndexComponent
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkFlowsRoutingModule { }
