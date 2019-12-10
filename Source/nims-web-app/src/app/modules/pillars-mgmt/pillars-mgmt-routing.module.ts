import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PillarListComponent} from '@app/modules/pillars-mgmt/page/pillar-list/pillar-list.component';
//import {StationSaveComponent} from '@app/modules/stations-management/page/station-save/station-save.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'pillar-list',
        pathMatch: 'full'
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
        path: 'pillar-list',
        component: PillarListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PillarsMgmtRoutingModule { }
