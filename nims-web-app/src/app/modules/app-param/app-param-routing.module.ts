import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SaveAppParamComponent } from './page/save-update/save-app-param.component';
import { ListAppParamComponent } from './page/search-list/list-app-param.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'list'
      },
      {
        path: 'detail',
        children: [
          {
            path: '',
            component: SaveAppParamComponent,
          },
          {
            path: ':id/:action',
            component: SaveAppParamComponent,
          }
        ]
      },
      {
        path: 'list',
        component: ListAppParamComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppParamRoutingModule { }
