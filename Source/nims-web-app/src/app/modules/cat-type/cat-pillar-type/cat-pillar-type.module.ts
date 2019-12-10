import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MessagesModule,
  AutoCompleteModule,
} from 'primeng/primeng';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';
import {DialogModule} from 'primeng/dialog';
import { SharedModule } from '@app/shared';
import { CatPillarTypeComComponent } from './page/cat-pillar-type-com/cat-pillar-type-com.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CatPillarTypeServiceService } from './service/cat-pillar-type-service.service';
import { CatPillarTypeSaveComponent } from './page/cat-pillar-type-save/cat-pillar-type-save.component';

@NgModule({
  declarations: [CatPillarTypeComComponent, CatPillarTypeSaveComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    MessagesModule,
    AutoCompleteModule,
    ConfirmDialogModule,
    DialogModule
  ],
  entryComponents: [
    CatPillarTypeComComponent,
    CatPillarTypeSaveComponent
  ],
  providers:[
    CatPillarTypeServiceService,
    ConfirmationService

  ]
})
export class CatPillarTypeModule { }
