import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@app/shared';
import { ReactiveFormsModule } from '@angular/forms';

import { NationRoutingModule } from './nation-routing.module';
import { NationPageComponent } from './nation-page.component';
import { NationSearchComponent } from './nation-search/nation-search.component';
import { NationFormComponent } from './nation-form/nation-form.component';

@NgModule({
    declarations: [ NationPageComponent, NationSearchComponent, NationFormComponent ],
    imports: [
      CommonModule,
      SharedModule,
      ReactiveFormsModule,
      NationRoutingModule,
    ],
    entryComponents: [NationFormComponent]
  })
  export class NationModule { }
