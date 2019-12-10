import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CableListComponent} from '@app/modules/cables-management/page/cable-list/cable-list.component';
import {CableSaveComponent} from '@app/modules/cables-management/page/cable-save/cable-save.component';

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
            component: CableSaveComponent
          },
          {
            path: ':id/:action',
            component: CableSaveComponent
          }
        ]
      },
      {
        path: 'list',
        component: CableListComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CablesManagementRoutingModule { }
