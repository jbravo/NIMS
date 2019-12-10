import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ODFSaveComponent } from './page/odf-save/odf-save.component';
import { OdfListComponent } from './page/odf-list/odf-list.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'list',
        pathMatch: 'full'
      },
      {
        path: 'detail',
        children: [
          {
            path: '',
            component: ODFSaveComponent
          },
          {
            path: ':id/:action',
            component: ODFSaveComponent
          }
        ]
      },
      {
        path: 'list',
        component: OdfListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ODFManagementRoutingModule { }
