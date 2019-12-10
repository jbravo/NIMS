import { FormGroup, Validators, FormControl, ValidationErrors, AbstractControl } from '@angular/forms';
import { AppParamsService } from '@app/core/services/app-param/app-param.service';
import { CommonUtils } from '@app/shared/services/common-utils.service';
import { APP_CONSTANTS } from '@app/core/app-config';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from '@app/app.component';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'save-app-param',
  templateUrl: './save-app-param.component.html',
})
export class SaveAppParamComponent implements OnInit {

  /* PROPERTY */
  formUpdate: FormGroup;

  appParamId: number;

  @ViewChild('parCode') parCodeRef: ElementRef;
  @ViewChild('parName') parNameRef: ElementRef;
  /* CONSTRUCTOR & OnInit */
  constructor(
  private app: AppComponent,
  private activatedRoute: ActivatedRoute,
  private router: Router,
  private appParamService: AppParamsService) {
      
      this.buildForm({});
  }

  ngOnInit() {
      // check xem co param khong, neu co thi la method update
      const params = this.activatedRoute.snapshot.params;

      // check param - check insert or update
      if (params.id == undefined) { // insert module
          this.appParamId = 0;
      return;
      }

      if (params && CommonUtils.isValidId(params.id)) { // update module
          this.appParamId = params.id;
      }
      
  }

  // Create Form
  private buildForm(formData: any) {
      this.formUpdate = CommonUtils.createForm(formData, {
        parId: [''],
        parCode: ['', Validators.compose([Validators.required, CommonUtils.customValidateReturnLabel('appparam.label.mp')])],
        parName: [''],
        parValue: [''],
        parType: [''],
        description: [''],
        parOrder: ['']
      });
  }

  // Get Form
  get f() {
      return this.formUpdate.controls;
  }

  // Save Transfer Activity
  save() {
    /*
      if(this.formUpdate.invalid) {

        this.getFormValidationErrors(this.formUpdate, this.app);
      }
      */

    //  if (CommonUtils.getFormValidationErrors(this.formUpdate, this.app)) 
    //  {
    //    // xử lý đúng
    //    alert("Dung");
    //    console.log(this.formUpdate.controls["parCode"]);
    //  } else {
    //    // xử lý sai
    //    alert("Sai");
    //    console.log(this.formUpdate.controls["parCode"]);
    //  }
    //  console.log(this.formUpdate.controls["parCode"]);

      //this.formUpdate.value.parId = this.appParamId;
      
      this.app.confirmMessage(null, () => {// on accepted

          this.appParamService.saveAppParam(this.formUpdate.value).subscribe(res => {
            // this.router.navigate(['/']);
            this.app.successMessage('message.success.created.success');
          });
          
        }, () => {
      });
      
  }

  //validate
  customValidateReturnLabel(label: string) {
    return (control: FormControl): {[key: string] : any} | null => {   

      return {labelFromCustomValidate: label};

    };
  }

  // generate default error from Angular Validators
  generateAngularErrors(control: AbstractControl, label: string) {

    // create array contains errors
    var errorsArray = new Array();

    // if control have error REQUIRED -> add error to array
    if(control.getError("required") == true) {
      errorsArray.push({error: "messages.error.required", label: label});
    }
    if(control.getError("pattern")) {
      errorsArray.push({error: "messages.error.pattern", label: label});
    }
    if(control.getError("minlength")) {
      errorsArray.push({error: "messages.error.minlength", label: label});
    }
    if(control.getError("maxlength")) {
      errorsArray.push({error: "messages.error.maxlength", label: label});
    }
    if(control.getError("email") == true) {
      errorsArray.push({error: "messages.error.email", label: label});
    }

    // return array error
    return errorsArray;
  }

  // get form error and show message
  getFormValidationErrors(form: FormGroup, app: AppComponent) {
    // check error
    let errorStatus = false;

    // for all controls of form -> key
    for (const key in form.controls) {
  
      // get all errors of each control in for -> controlErrors
      const controlErrors: ValidationErrors = form.get(key).errors;
        // if have error
        if (controlErrors != null) 
        {
          // for each error of all errors -> keyError
          Object.keys(controlErrors).forEach(keyError => {
            // log error and control

            // array contain errors of control
            var errorsArray = new Array();
            
            // get label of each control -> get from custom validator
            if("labelFromCustomValidate" == keyError) {
              let labelFromControl: string;
              labelFromControl = controlErrors[keyError];

              // fill error of Angular Validators, return array of errors -> errorsArray
              errorsArray = this.generateAngularErrors(form.controls[key], labelFromControl);
            }
              
            // if have error of Angular Validators
            if(errorsArray.length > 0) {
              // set check error = true
              errorStatus = true;
              
              // for all errors array -> show popup error message
              for (let index = 0; index < errorsArray.length; index++) {
                const element = errorsArray[index];
                
                let keyLabel = app.translation.translate(element.label);
                
                app.warningMessage(`${element.error}`, undefined, {field: keyLabel});  
                
              }
            }
            
          });// END - for each error of all errors -> keyError
        } // END - if have error

        if(errorStatus) break; // if check error == true (one of control have error and show message) -> break
    } // END - for all controls of form -> key
  }

  getFormValidationErrorsOk(form: FormGroup) {
    let errorStatus = false;

    Object.keys(form.controls).forEach(key => {
  
      const controlErrors: ValidationErrors = form.get(key).errors;
        if (controlErrors != null) 
        {
          Object.keys(controlErrors).forEach(keyError => {

            var errorsArray: any[];
            errorsArray = new Array();
            
            if("labelFromCustomValidate" == keyError) {
              let labelFromControl: string;
              labelFromControl = controlErrors[keyError];

              errorsArray = this.generateAngularErrors(form.controls[key], labelFromControl);
            }

              
            if(errorsArray.length > 0) {
              errorStatus = true;
              
              for (let index = 0; index < errorsArray.length; index++) {
                const element = errorsArray[index];
                
                let keyLabel = this.app.translation.translate(element.label);
                
                this.app.warningMessage(`${element.error}`, undefined, {field: keyLabel});  
              }
            }
            
          });
        }

        if(errorStatus == true) return false;
    });
  }

  customValidateBackup(label: string) {
    return (control: FormControl): {[key: string] : any} | null => {

      var errorsArray: any[];

      errorsArray = new Array();

      if(control.getError("required") == true) {
        errorsArray.push({error: "messages.error.required", label: label});
      }
      if(control.getError("pattern")) {
        errorsArray.push({error: "pattern", label: label});
      }
      if(control.getError("minlength")) {
        errorsArray.push({error: "minlength", label: label});
      }
      if(control.getError("maxlength")) {
        errorsArray.push({error: "maxlength", label: label});
      }
      if(control.getError("email") == true) {
        errorsArray.push({error: "messages.error.email", label: label});
      }

      if("1@gmail.com" == control.value) {
        errorsArray.push({error: "messages.error.customErrors", label: label});
      }

      return {errors: errorsArray};

    };
  }

  getFormValidationErrorsBackup(form: FormGroup) {
    Object.keys(form.controls).forEach(key => {

    const controlErrors: ValidationErrors = form.get(key).errors;
      if (controlErrors != null) 
        {
          Object.keys(controlErrors).forEach(keyError => {

            if("errors" == keyError) {
              for (let index = 0; index < controlErrors[keyError].length; index++) {
                const element = controlErrors[keyError][index];

                let keyLabel = this.app.translation.translate(element.label);
                
                // let elementRef = key + 'Ref';
                // elementRef.focus();

                this.app.warningMessage(`${element.error}`, undefined, {field: keyLabel});   
                form.controls[key].clearValidators(); 
                
                //return;          
              }
            }
            
          });
        }
    });
  }

  getControlName(c: AbstractControl): string | null {
    const formGroup = c.parent.controls;
    return Object.keys(formGroup).find(name => c === formGroup[name]) || null;
  }

  // button cancel
  cancel() {
      this.app.confirmMessage('common.message.confirm.cancel', () => {// on accepted
          this.router.navigate(['/']);
      }, () => {// on rejected

      });
  }

}


