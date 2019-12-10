import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';

import { SysCatService, SysCatTypeService, NationService } from '@app/core';

import { SysCatRoutingModule } from './sys-cat-routing.module';
import { IndexComponent } from './pages/index/index.component';
import { SysCatSearchComponent } from './pages/sys-cat-search/sys-cat-search.component';
import { SysCatAddComponent } from './pages/sys-cat-add/sys-cat-add.component';
import { SysCatTypeComponent, FilterPipe } from './pages/sys-cat-type/sys-cat-type.component';
import { SysCatTypeAddComponent } from './pages/sys-cat-type-add/sys-cat-type-add.component';
import { SysCatTypeTranferComponent } from './pages/sys-cat-type-tranfer/sys-cat-type-tranfer.component';

@NgModule({
  declarations: [
    IndexComponent, SysCatSearchComponent, SysCatAddComponent,
    SysCatTypeComponent, SysCatTypeAddComponent, SysCatTypeTranferComponent, FilterPipe
  ],
  imports: [
    /**
     * Required
     */
    CommonModule,
    SharedModule,

    SysCatRoutingModule,
  ],
  entryComponents: [SysCatAddComponent, SysCatTypeAddComponent, SysCatTypeTranferComponent],
  providers: [
    SysCatService, SysCatTypeService, NationService
  ]

})
export class SysCatModule { }
