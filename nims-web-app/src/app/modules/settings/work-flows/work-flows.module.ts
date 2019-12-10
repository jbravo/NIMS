import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WorkFlowsRoutingModule } from './work-flows-routing.module';
import { WorkFlowsIndexComponent } from './work-flows-index/work-flows-index.component';
import { WorkFlowsSearchComponent } from './work-flows-search/work-flows-search.component';
import { WorkFlowsFormComponent } from './work-flows-form/work-flows-form.component';
import { SharedModule } from '@app/shared';

@NgModule({
  declarations: [WorkFlowsIndexComponent, WorkFlowsSearchComponent, WorkFlowsFormComponent],
  imports: [
    CommonModule,
    SharedModule,
    WorkFlowsRoutingModule
  ]
})
export class WorkFlowsModule { }
