import { SysPropertyDetailBean } from './../../core/models/sys-property-details.model';
import { ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn, FormGroup, Validators, FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {
  public static maxLength(max: number) {
    return Validators.compose([Validators.maxLength(max), ValidationService.isValidInput]);
  }

  public static passwordValidator(control: AbstractControl): any {
    if (!control.value) { return; }

    // {6,100}           - Assert password is between 6 and 100 characters
    // (?=.*[0-9])       - Assert a string has at least one number
    // (?!.*\s)          - Spaces are not allowed
    return (control.value.match(/^(?=.*\d)(?=.*[a-zA-Z!@#$%^&*])(?!.*\s).{6,100}$/)) ? '' : { invalidPassword: true };
  }

  /**
   * validate onlyLetterNumber
   */
  public static onlyLetterNumber(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.match(/^[0-9a-zA-Z]+$/)) ? '' : { onlyLetterNumber: true };
  }

  /**
   * validate personalIdNumber
   */
  public static personalIdNumber(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.match(/^[0-9a-zA-Z]{8,15}$/)) ? '' : { personalIdNumber: true };
  }

  /**
   * validate phone
   */
  public static phone(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.match(/^([\+])?(\d([.\s])?){1,15}$/)) ? '' : { phone: true };
  }

  /**
   * validate mobileNumber
   */
  public static mobileNumber(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.match(/^([+][0-9]{1,3}([ .-])?)?([(][0-9]{1,6}[)])?([0-9 .-]{1,32})(([A-Za-z :]{1,11})?[0-9]{1,4}?)$/))
            ? '' : { mobileNumber: true };
  }

  /**
   * validate integer
   */
  public static integer(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.toString().match(/^[\-\+]?\d+$/)) ? '' : { integer: true };
  }

  /**
   * validate positiveInteger
   */
  public static positiveInteger(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.toString().match(/^\d+$/)) ? '' : { positiveInteger: true };
  }

  /**
   * validate number
   */
  public static number(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.toString().match(/^[\-\+]?(([0-9]+)([\.]([0-9]+))?|([\.]([0-9]+))?)$/))
            ? '' : { number: true };
  }

  /**
   * validate positiveNumber
   */
  public static positiveNumber(control: AbstractControl): any {
    if (!control.value) { return; }
    return (control.value.toString().match(/^(([0-9]+)([\.]([0-9]+))?|([\.]([0-9]+))?)$/))
            ? '' : { positiveNumber: true };
  }

  /**
   * Validate ky tu dac biet
   * @param control: any
   */
  public static isValidInput(control: AbstractControl): any {
    if (!control.value) { return; }
    const text = control.value;
    const iChars = '!#$^*[]\\{}\"?<>';
    for (let j = 0; j < text.length; j++) {
      if (iChars.indexOf(text.charAt(j)) >= 0) {
          return { isValidInput: true };
      }
    }
    return null;
  }

  /**
   * Validate so
   * @param control: any
   */
  public static isNumber(control: AbstractControl): any {
    if (!control.value) { return; }
    return (!isNaN(parseInt(control.value)) && isFinite(control.value))
    && parseInt(control.value) === parseFloat(control.value)
    && parseFloat(control.value) > 0 ? '' : { isNumber: true };
  }
  /**
   * Validate IP
   * @param control : any
   */
  public static isIp(control: AbstractControl): any {
    if (!control.value) { return; }
    const str: string  = '^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.'
                      + '){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$';
    const regExp = new RegExp(str);
    if (!regExp.test(control.value)) {
      return {isIp: true};
    }
    return '';
  }
    /**
   * Validate URL
   * @param control : any
   */
  public static isUrl(control: AbstractControl): any {
    if (!control.value) { return; }
    const str = '(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|' +
    'www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|' +
    'https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9]\.[^\s]{2,})';
    const regExp = new RegExp(str);
    if (!regExp.test(control.value)) {
      return {isUrl: true};
    }
    return '';
  }
  /**
   * Validate date not affter date
   * @param targetKey: any
   * @param toMatchKey: any
   */
  public static notAffter(targetKey: string, toMatchKey: string, labelMatchCode: string): ValidatorFn {
    return (group: FormGroup): {[key: string]: any} => {
      const target = group.controls[targetKey];
      const toMatch = group.controls[toMatchKey];
      if (target.value && toMatch.value) {
        const isCheck = target.value <= toMatch.value;
        // set equal value error on dirty controls
        if (!isCheck && target.valid && toMatch.valid) {
          target.setErrors({dateNotAffter: {dateNotAffter: labelMatchCode}});
          target.markAsTouched();
        }
        if (isCheck && target.hasError('dateNotAffter')) {
          target.setErrors(null);
          target.markAsUntouched();
        }
      }
      return null;
    };
  }
  public static getArrValidator(prop: SysPropertyDetailBean, oldArr: Array<ValidatorFn>): Array<ValidatorFn> {
    const validators = new Array<ValidatorFn>();
    if (oldArr && oldArr.length > 0 && !prop) {
      return oldArr;
    }
    if (!prop || prop.isHide === true) {
      return validators;
    }
    if (prop.isRequire) {
      validators.push(Validators.required);
    }
    if (prop.isEmail) {
      validators.push(Validators.email);
    }
    if (prop.isNumber) {
        validators.push(ValidationService.isNumber);
      // Pattern number
      // validators.push(Validators.pattern('^-?\\d*(\\.\\d+)?$'));
    }
    if (prop.isUrl) {
      validators.push(ValidationService.isUrl);
    }
    if (prop.isIp) {
      validators.push(ValidationService.isIp);
    }
    if (prop.minLength) {
      validators.push(Validators.minLength(prop.minLength));
    }
    if (prop.maxLength) {
      validators.push(Validators.maxLength(prop.maxLength));
    }
    if (prop.numberMin) {
      validators.push(Validators.min(prop.numberMin));
    }
    if (prop.numberMax) {
      validators.push(Validators.max(prop.numberMax));
    }
    return validators;
  }
  public static getConfigBuildForm(formBuilder: FormBuilder, controlsConfig: any, propertyDetails: Array<any>): FormGroup {
    let formGroup: FormGroup;
    formGroup = formBuilder.group(controlsConfig);
    for (const field in formGroup.controls) {
      const prop = propertyDetails.filter(item => item.propertyCode === field)[0];
      if (prop) {
        if (prop.isHide === true) {
          formGroup.get(field).clearValidators();
        } else {
          const validators = new Array<ValidatorFn>();
          if (prop.isRequire) {
            validators.push(Validators.required);
          }
          if (prop.isEmail) {
            validators.push(Validators.email);
          }
          if (prop.isNumber) {
             validators.push(ValidationService.isNumber);
            // Pattern number
            // validators.push(Validators.pattern('^-?\\d*(\\.\\d+)?$'));
          }
          if (prop.isUrl) {
            validators.push(ValidationService.isUrl);
          }
          if (prop.isIp) {
            validators.push(ValidationService.isIp);
          }
          if (prop.minLength) {
            validators.push(Validators.minLength(prop.minLength));
          }
          if (prop.maxLength) {
            validators.push(Validators.maxLength(prop.maxLength));
          }
          if (prop.numberMin) {
            validators.push(Validators.min(prop.numberMin));
          }
          if (prop.numberMax) {
            validators.push(Validators.max(prop.numberMax));
          }
          formGroup.get(field).setValidators(validators);
        }
        formGroup.get(field).updateValueAndValidity();
      }
    }
    return formGroup;
  }

}
