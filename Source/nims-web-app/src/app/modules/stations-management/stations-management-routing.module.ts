import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StationListComponent} from '@app/modules/stations-management/page/station-list/station-list.component';
import {StationSaveComponent} from '@app/modules/stations-management/page/station-save/station-save.component';
import {StationDetailComponent} from '@app/modules/stations-management/page/station-detail/station-detail.component';

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
        path: 'edit',
        children: [
          {
            path: '',
            component: StationSaveComponent
          },
          {
            path: ':id/:action',
            component: StationSaveComponent
          }
        ]
      },
      {
        path: 'detail',
        children: [
          {
            path: ':id/:action',
            component: StationDetailComponent
          }
        ]
      },
      {
        path: 'list',
        component: StationListComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StationsManagementRoutingModule {
}
