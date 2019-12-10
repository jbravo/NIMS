import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContentLayoutComponent} from '@app/layouts/content-layout/content-layout.component';
import {CONTENT_ROUTES, Err404Component} from '@app/shared';

const routes: Routes = [
  {
    path: '',
    component: ContentLayoutComponent,
    children: CONTENT_ROUTES,
  },
  {
    path: '404',
    component: Err404Component
  },
  {
    path: '**', redirectTo: '404', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload', scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}
