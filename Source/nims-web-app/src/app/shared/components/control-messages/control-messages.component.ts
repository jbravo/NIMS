import { Component, Input } from '@angular/core';
import { FormControl, ValidationErrors } from '@angular/forms';
import { TranslationService } from 'angular-l10n';

@Component({
  selector: 'app-control-messages',
  templateUrl: './control-messages.component.html',
})
export class ControlMessagesComponent {
  @Input()
  public control: FormControl;
  @Input()
  public labelName?: string;
  private replaceKeys = ['max', 'min', 'maxlength'     , 'minlength'     , 'dateNotAffter', ];
  private actualKeys =  ['max', 'min', 'requiredLength', 'requiredLength', 'dateNotAffter', ];

  constructor(public translation: TranslationService) {}

  get errorMessage(): string {
    for (const propertyName in this.control.errors) {
      if (this.control.errors.hasOwnProperty(propertyName) && this.control.touched) {
        const messageText = this.translation.translate(`validate.${propertyName}`);
        const errors = this.control.errors[propertyName];
        return this.buildMessage(messageText, errors);
      }
    }
    return undefined;
  }
  /**
   * buildMessage
   * @param messageText: string
   * @param errors: ValidationErrors
   */
  buildMessage(messageText: string, errors: ValidationErrors): string {
    for (const i in this.replaceKeys) {
      if (errors.hasOwnProperty(this.actualKeys[i])) {
        let text = errors[this.actualKeys[i]];
        if (this.actualKeys[i] === 'dateNotAffter') {
          text = this.translation.translate(errors[this.actualKeys[i]]);
        }
        messageText = messageText.replace(new RegExp('\\$\\{' + this.replaceKeys[i] + '\\}', 'g'), text);
      }
    }
    return messageText;
  }
}
