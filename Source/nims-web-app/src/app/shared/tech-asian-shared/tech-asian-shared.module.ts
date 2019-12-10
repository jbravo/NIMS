import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ConfirmDialogComponent} from '@app/modules/suggest-station/shared-components/confirm-dialog/confirm-dialog.component';
import {DialogModule} from 'primeng/dialog';
import {
  ButtonModule,
  CheckboxModule,
  ConfirmDialogModule,
  ContextMenuModule,
  DialogService,
  FieldsetModule,
  MenuModule,
  MultiSelectModule,
  PanelModule,
  ProgressBarModule,
  TabViewModule,
  TreeModule
} from 'primeng/primeng';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ToastModule} from 'primeng/toast';
import {TranslationModule} from 'angular-l10n';
import {TableModule} from 'primeng/table';
import {FormlyModule} from '@ngx-formly/core';
import {TaFormControlModule} from '@app/modules/ta-form-control/ta-form-control.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [ConfirmDialogComponent],
  imports: [
    CommonModule,
    TaFormControlModule,
    PanelModule,
    FormlyModule,
    ReactiveFormsModule,
    TableModule,
    CheckboxModule,
    MenuModule,
    ButtonModule,
    FieldsetModule,
    TabViewModule,
    MultiSelectModule,
    TreeModule, ToastModule, TranslationModule,
    FormsModule,
    ProgressBarModule,
    DialogModule,
    ContextMenuModule,
    NgbModule,
    ConfirmDialogModule
  ],
  exports: [
    TaFormControlModule,
    PanelModule,
    FormlyModule,
    ReactiveFormsModule,
    TableModule,
    CheckboxModule,
    MenuModule,
    ButtonModule,
    FieldsetModule,
    TabViewModule,
    MultiSelectModule,
    TreeModule, ToastModule, TranslationModule,
    FormsModule,
    ProgressBarModule,
    DialogModule,
    ContextMenuModule,
    NgbModule,
    ConfirmDialogModule
  ],
  entryComponents: [ConfirmDialogComponent],
  providers: [DialogService]
})
export class TechAsianSharedModule {
}
