import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatDepartmentComComponent } from './page/cat-department-com/cat-department-com.component';
import { CatDepartmentSerService } from './services/cat-department-ser.service';
import { CatTerantSerService} from './services/cat-terant-ser.service';
import { DropdownModule } from 'primeng/dropdown';
import {
  CalendarModule, CheckboxModule, DataTableModule, FileUploadModule,
  InputTextareaModule, PaginatorModule, RadioButtonModule, TabViewModule, TreeModule
} from 'primeng/primeng';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { DialogModule } from 'primeng/dialog';
import { SharedModule } from '@app/shared';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CatDepartmentSaveComponent } from './page/cat-department-save/cat-department-save.component';

@NgModule({
  declarations: [CatDepartmentComComponent, CatDepartmentSaveComponent],
  exports: [CatDepartmentComComponent, CatDepartmentSaveComponent],
  imports: [
    CommonModule,
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
    ToastModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    DialogModule,
    TreeModule,
  ],
  entryComponents: [
    CatDepartmentComComponent,
    CatDepartmentSaveComponent
  ],
  providers: [
    CatDepartmentSerService,
    CatTerantSerService
  ]
})
export class CatDepartmentModule { }
