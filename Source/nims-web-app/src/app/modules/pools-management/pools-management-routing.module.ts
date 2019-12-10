import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { PoolListComponent } from './page/pool-list/pool-list.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'list',
        // pathMatch: 'full'
      },
      // {
      //   path: 'detail',
      //   children: [ 
      //     {
      //       path: '',
      //       component: CableSaveComponent
      //     },
      //     {
      //       path: ':id/:action',
      //       component: CableSaveComponent
      //     }
      //   ]
      // },
      {
        path: 'list',
        component: PoolListComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PoolsManagementRoutingModule { }
