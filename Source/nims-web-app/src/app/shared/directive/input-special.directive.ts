import { ReactiveFormsModule, NG_VALIDATORS, FormsModule, FormGroup, FormControl, ValidatorFn, Validator} from '@angular/forms';
import { Directive, Input } from '@angular/core';

@Directive({
  selector: 'input[type="text"],input[type="email"],input[type="tel"]',
  providers: [
    {
     provide: NG_VALIDATORS,
     useExisting: InputSpecialDirective,
     multi: true
    }
   ]
})
export class InputSpecialDirective implements Validator {
  private _type = '';
  private _onChange: Function;

  @Input()
  set type(value: string) {
    this._type = value;
  }
  validator: ValidatorFn;

  constructor() {
    this.validator = this.validateSpecialCharecter();
  }

  validate(c: FormControl) {
   return this.validator(c);
  }

  validateSpecialCharecter() {
    return (c: FormControl) => {
      const text = c.value;
      if (text && (this._type === 'text' || this._type === 'email' || this._type === 'tel')) {
        const iChars = '!#$^*[]\\{}\"?<>';
        for (let j = 0; j < text.length; j++) {
          if (iChars.indexOf(text.charAt(j)) >= 0) {
              return { isValidInput: true };
          }
        }
      }
      return null;
    };
  }

  registerOnValidatorChange(fn: () => void): void {
    this._onChange = fn;
  }
}
