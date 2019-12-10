import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {StationSaveComponent} from '@app/modules/stations-management/page/station-save/station-save.component';
import {StationListComponent} from '@app/modules/stations-management/page/station-list/station-list.component';
import {WeldSleeveDetailComponent} from '@app/modules/weld-sleeve-mgmt/page/weld-sleeve-detail/weld-sleeve-detail.component';
import {WeldSleeveListComponent} from '@app/modules/weld-sleeve-mgmt/page/weld-sleeve-list/weld-sleeve-list.component';

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
            component: WeldSleeveDetailComponent
          },
          {
            path: ':id/:action',
            component: WeldSleeveDetailComponent
          }
        ]
      },
      {
        path: 'list',
        component: WeldSleeveListComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WeldSleeveMgmtRoutingModule { }
