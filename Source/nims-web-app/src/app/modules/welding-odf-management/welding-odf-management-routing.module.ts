import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { WeldingOdfListComponent } from './page/welding-odf-list/welding-odf-list.component';
import { WeldingOdfDetailComponent } from './page/welding-odf-detail/welding-odf-detail.component';
import { WeldingOdfCreateComponent } from './page/welding-odf-create/welding-odf-create.component';
import { WeldingOdfUpdateComponent } from './page/welding-odf-update/welding-odf-update.component';

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
            component: WeldingOdfDetailComponent
          },
          {
            path: '',
            component: WeldingOdfCreateComponent
          },
          {
            path: '',
            component: WeldingOdfUpdateComponent
          }
        ]
      },
      {
        path: 'list',
        component: WeldingOdfListComponent
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WeldingOdfsManagementRoutingModule {
}
