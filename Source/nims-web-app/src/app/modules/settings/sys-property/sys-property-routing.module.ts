import { PropertyResolver } from './../../../shared/services/property.resolver';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SysPropertyIndexComponent } from './sys-property-index/sys-property-index.component';

const routes: Routes = [
  {
    path: '',
    component: SysPropertyIndexComponent,
    resolve: {
      props: PropertyResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SysPropertyRoutingModule { }
