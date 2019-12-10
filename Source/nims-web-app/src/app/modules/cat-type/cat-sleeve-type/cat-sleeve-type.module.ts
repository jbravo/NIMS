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
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CatSleeveTypeServiceService } from './service/cat-sleeve-type-service.service';
import { CatSleeveTypeComComponent } from './page/cat-sleeve-type-com/cat-sleeve-type-com.component';
import {DropdownModule} from 'primeng/dropdown';
import { CatSleeveTypeSaveComponent } from './page/cat-sleeve-type-save/cat-sleeve-type-save.component';
import {  CalendarModule, CheckboxModule, DataTableModule, FileUploadModule, 
  InputTextareaModule,  PaginatorModule, RadioButtonModule, TabViewModule } from 'primeng/primeng';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';

@NgModule({
  declarations: [CatSleeveTypeComComponent, CatSleeveTypeSaveComponent],
  exports:[CatSleeveTypeComComponent, CatSleeveTypeSaveComponent],
  imports: [
    CommonModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    MessagesModule,
    AutoCompleteModule,
    ConfirmDialogModule,
    DialogModule,
    DropdownModule,
    CalendarModule,
    CheckboxModule,
    DataTableModule,
    FileUploadModule,
    InputTextareaModule,
    PaginatorModule,
    RadioButtonModule,
    TabViewModule,
    MessageModule,
    ToastModule
  ],
  entryComponents:[
    CatSleeveTypeComComponent,
    CatSleeveTypeSaveComponent
  ],
  providers:[
    CatSleeveTypeServiceService,
    ConfirmationService

  ]
})
export class CatSleeveTypeModule { }
