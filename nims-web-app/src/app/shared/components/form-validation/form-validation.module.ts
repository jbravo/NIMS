import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {ErrorDirective} from './error.directive';
import {ErrorMessageComponent} from './error-message.component';
import {HasErrorDirective} from './has-error.directive';
import {FocusFieldFormDirective} from './focus-field-form.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    ErrorDirective,
    ErrorMessageComponent,
    HasErrorDirective,
    FocusFieldFormDirective,
  ],
  exports: [
    ErrorDirective,
    ErrorMessageComponent,
    HasErrorDirective,
    FocusFieldFormDirective,
  ]
})

export class FormValidationModule {
}
