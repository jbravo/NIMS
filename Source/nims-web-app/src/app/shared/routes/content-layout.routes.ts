import {Routes} from '@angular/router';
import {TabLayoutComponent} from '@app/layouts/tab-layout/tab-layout.component';

export const CONTENT_ROUTES: Routes = [
  {
    path: '',
    component: TabLayoutComponent,
  },
  // {
  //   path: 'app-param',
  //   loadChildren: './modules/app-param/app-param.module#AppParamModule'
  // },

];

