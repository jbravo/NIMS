import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NationPageComponent } from './nation-page.component';

const routes: Routes = [
  {
    path: '',
    component: NationPageComponent
  }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class NationRoutingModule {}
