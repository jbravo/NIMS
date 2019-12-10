import {ValidationService} from '@app/shared/services/validation.service';
import {SysPropertyDetailBean} from './../../core/models/sys-property-details.model';
import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {PERMISSION_CODE} from '@app/core/app-config';
import {Constants} from '@env/environment';
import {UserPermission, UserToken} from '@app/core/models';
import {AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors} from '@angular/forms';
import {AppComponent} from '@app/app.component';
import {HrStorage} from '@app/core/services/auth/HrStorage';
import {SysGridViewService} from '@app/shared/services/sys-grid-view.service';

@Injectable({
  providedIn: 'root'
})
export class CommonUtils {
  public static isNullOrEmpty(str: string): boolean {
    return !str || str === '';
  }

  public static isValidId(id: any): boolean {
    if (CommonUtils.isNullOrEmpty(id)) {
      return false;
    }
    if (id === '0') {
      return false;
    }
    return true;
  }

  /**
   * getPermissionCode
   * @param code: string
   */
  public static getPermissionCode(code: string): string {
    return PERMISSION_CODE[code] || '';
  }

  /**
   * has Permission
   */
  public static havePermission(operationKey: string, adResourceKey: string): boolean {
    const permissionCode = this.getPermissionCode(operationKey) + ' ' + this.getPermissionCode(adResourceKey);
    // const userInfo = sessionStorage.getItem(Constants.userToken);
    // if (!userInfo) {
    //   return false;
    // }

    // const userToken: UserToken = JSON.parse(userInfo);
    const userToken: UserToken = HrStorage.getUserToken();
    if (!userToken.userId) {
      return false;
    }

    const userPermissionList: UserPermission[] = userToken.userPermissionList;
    if (userPermissionList == null || userPermissionList.length <= 0) {
      return false;
    }

    for (const userPermission of userPermissionList) {
      const check = userPermission.operationCode + ' ' + userPermission.resourceCode;
      if (check === permissionCode) {
        return true;
      }
    }

    return false;
  }

  /**
   * copyProperties
   * param dest
   * param orgs
   */
  public static copyProperties(dest: any, orig: any): any {
    if (!orig) {
      return dest;
    }

    for (const k in dest) {
      if (orig.hasOwnProperty(k)) {
        dest[k] = orig[k];
      }
    }
    return dest;
  }

  /**
   * Clone all properties from source and save typeof dest
   * Author:huynq
   * @param source :object Source
   */
  public static cloneObject(dest: any, source: any): any {
    if (!source) {
      return dest;
    }
    for (const attribute in source) {
      if (source[attribute] !== undefined && source[attribute] !== null) {
        if (typeof source[attribute] === 'object') {
          dest[attribute] = Object.assign({}, source[attribute]);
        } else {
          dest[attribute] = source[attribute];
        }
      }
    }
    return dest;
  }

  /**
   * toPropertyDetails: transfer json to Array<SysPropertyDetailBean>
   */
  public static toPropertyDetails(source: any): Array<SysPropertyDetailBean> {
    const dest: Array<SysPropertyDetailBean> = [];
    if (source.length > 0) {
      for (const item of source) {
        let bean = new SysPropertyDetailBean(item.sysPropertyBO, item.nationBO);
        bean = CommonUtils.cloneObject(bean, item);
        dest.push(bean);
      }
    }
    return dest;
  }

  /**
   * copyProperties
   * param dest
   * param orgs
   */
  public static buildParams(obj: any): HttpParams {
    return Object.entries(obj || {})
      .reduce((params, [key, value]) => {
        if (value === null) {
          return params.set(key, String(''));
        } else if (typeof value === typeof {}) {
          return params.set(key, JSON.stringify(value));
        } else {
          return params.set(key, String(value));
        }
      }, new HttpParams());
  }

  /**
   * validateForm
   * @param form: FormGroup
   */
  public static isValidForm(form: any): boolean {
    this.markAsTouched(form);
    return !form.invalid;
  }

  public static markAsTouched(form: any) {
    if (form instanceof FormGroup) {
      CommonUtils.isValidFormGroup(form);
    } else if (form instanceof FormArray) {
      CommonUtils.isValidFormArray(form);
    } else if (form instanceof FormControl) {
      form.markAsTouched();
      if (form.invalid) {
        console.warn('Validate error field:', form);
      }
    }
  }

  public static isValidFormArray(form: FormArray) {
    for (const i in form.controls) {
      CommonUtils.markAsTouched(form.controls[i]);
    }
  }

  public static isValidFormGroup(form: FormGroup) {
    Object.keys(form.controls).forEach(key => {
      CommonUtils.markAsTouched(form.get(key));
    });
  }

  /**
   * hàm xử lý lấy nationId hiện tại theo quốc gia
   */
  public static getNationId(): number {
    return 1740;
  }

  /**
   * hàm xử lý lấy nationId hiện tại theo quốc gia
   */
  public static toTreeNode(res: any): any {
    for (const node of res) {
      if (!node.leaf) {
        delete node.icon;
        if (node.children && node.children.length > 0) {
          node.children = CommonUtils.toTreeNode(node.children);
        }
      }
    }
    return res;
  }


  /**
   * @author dodviist
   * @function filters tree node
   *
   */
  public static addTreeNodes(data) {
    const treeNode: any = [];
    for (const node of data) {
      if (node.parentId == null || node.parentId == 0) {
        treeNode.push(node);
      }
      if (node.children == null) {
        node.children = [];
      }
      for (const nodeChild of data) {
        if (node.nodeId == nodeChild.parentId) {
          node.children.push(nodeChild);
        }
      }
    }
    return treeNode;
  }

  /**
   * nvl
   * param value
   * param defaultValue
   */
  public static nvl(value: any, defaultValue: number = 0): number {
    if (value === null || value === undefined || value === '') {
      return defaultValue;
    }
    return value;
  }

  public static nvlString(value: any, defaultValue: string = ''): string {
    if (value === null || value === undefined || value === '') {
      return defaultValue;
    }
    return value;
  }

  /**
   * convert To FormData mutilpart request post
   */
  public static convertFormFile(dataPost: any): FormData {
    const filteredData = CommonUtils.convertData(dataPost);
    const formData = CommonUtils.objectToFormData(filteredData, '', []);
    return formData;
  }

  /**
   * objectToFormData
   */
  public static objectToFormData(obj, rootName, ignoreList): FormData {
    const formData = new FormData();

    function appendFormData(data, root) {
      if (!ignore(root)) {
        root = root || '';
        if (data instanceof File) {
          if (data.type !== 'vhr_stored_file') {
            formData.append(root, data);
          }
        } else if (Array.isArray(data)) {
          for (let i = 0; i < data.length; i++) {
            appendFormData(data[i], root + '[' + i + ']');
          }
        } else if (data && typeof data === 'object') {
          for (const key in data) {
            if (data.hasOwnProperty(key)) {
              if (root === '') {
                appendFormData(data[key], key);
              } else {
                appendFormData(data[key], root + '.' + key);
              }
            }
          }
        } else {
          if (data !== null && typeof data !== 'undefined') {
            formData.append(root, data);
          }
        }
      }
    }

    function ignore(root) {
      return Array.isArray(ignoreList) && ignoreList.some(function (x) {
        return x === root;
      });
    }

    appendFormData(obj, rootName);
    return formData;
  }

  /**
   * convertData
   */
  public static convertData(data: any): any {
    if (typeof data === typeof {}) {
      return CommonUtils.convertDataObject(data);
    } else if (typeof data === typeof []) {
      return CommonUtils.convertDataArray(data);
    } else if (typeof data === typeof true) {
      return CommonUtils.convertBoolean(data);
    }
    return data;
  }

  /**
   * convertDataObject
   * param data
   */
  public static convertDataObject(data: Object): Object {
    if (data) {
      for (const key in data) {
        if (data[key] instanceof File) {
          //do nothing
        } else {
          data[key] = CommonUtils.convertData(data[key]);
        }
      }
    }
    return data;
  }

  public static convertDataArray(data: Array<any>): Array<any> {
    if (data && data.length > 0) {
      for (const i in data) {
        data[i] = CommonUtils.convertData(data[i]);
      }
    }
    return data;
  }

  public static convertBoolean(value: boolean): number {
    return value ? 1 : 0;
  }

  /**
   * tctGetFileSize
   * param files
   */
  public static tctGetFileSize(files) {
    try {
      let fileSize;
      // if (typeof files === typeof []) {
      //   fileSize = files[0].size;
      // } else {
      fileSize = files.size;
      // }
      fileSize /= (1024 * 1024); // chuyen ve MB
      return fileSize.toFixed(2);
    } catch (ex) {
      console.error(ex.message);
    }
  }

  /**
   * createForm controls
   */
  public static createForm(formData: any, options: any, validate?: any): FormGroup {
    const formGroup = new FormGroup({});
    for (const property in options) {
      if (formData.hasOwnProperty(property)) {
        options[property][0] = formData[property];
      }
      formGroup.addControl(property, new FormControl(options[property][0], options[property][1]));
    }
    if (validate) {
      formGroup.setValidators(validate);
    }
    return formGroup;
  }

  /**
   * createForm controls
   */
  public static createFormNew(formData: any, options: any, propConfigs: Array<SysPropertyDetailBean>, validate?: any): FormGroup {
    const formGroup = new FormGroup({});
    for (const property in options) {
      if (formData.hasOwnProperty(property)) {
        options[property][0] = formData[property];
      }
      const config = propConfigs.filter(item => item.propertyCode === property)[0];
      if (config) {
        options[property][1] = ValidationService.getArrValidator(config, options[property][1]);
      }
      formGroup.addControl(property, new FormControl(options[property][0], options[property][1]));
    }
    if (validate) {
      formGroup.setValidators(validate);
    }
    return formGroup;
  }


  public static stringToDate(value: any, pattern: string): Date | null {
    if ((typeof value === 'string') && (value.indexOf('/') > -1)) {
      const str = value.split('/');
      if (pattern == 'yyyy/MM/dd') {
        const year = Number(str[0]);
        const month = Number(str[1]) - 1;
        const date = Number(str[2]);

        return new Date(year, month, date);
      } else {
        const year = Number(str[2]);
        const month = Number(str[1]) - 1;
        const date = Number(str[0]);

        return new Date(year, month, date);
      }
    } else if ((typeof value === 'string') && value === '') {
      return new Date();
    }
    const timestamp = typeof value === 'number' ? value : Date.parse(value);
    return isNaN(timestamp) ? null : new Date(timestamp);
  }

  public static dateToString(value: any) {
    if (value == null) {
      return '';
    }
    const date = new Date(value);
    return (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '/'
      + ((date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + '/'
      + date.getFullYear();

  }

  public static dateTimeToStringFile(value: any) {
    if (value == null) {
      return '';
    }
    const date = new Date(value);
    return date.getFullYear() + '' + ((date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1)) + ''
      + (date.getDate() < 10 ? '0' + date.getDate() : date.getDate())
      + '_' + (date.getHours() < 10 ? ('0' + date.getHours()) : date.getHours())
      + '' + (date.getMinutes() < 10 ? ('0' + date.getMinutes()) : date.getMinutes())
      + '' + (date.getMilliseconds() < 10 ? ('0' + date.getMilliseconds()) : date.getMilliseconds());

  }

  public static searchTree(list: any, key: any, value: any, childList: any) {
    let r = null;
    if (list != null && list.length > 0) {
      for (const item of list) {
        if (item[key] === value) {
          return item;
        }
        r = this.searchTree(item[childList], key, value, childList);
        if (r != null) {
          return r;
        }
      }
    }
  }

  public static createTree(list: any, key: any, parentKey: any, childKey: any, indent: any) {
    if (list != null) {
      list.forEach((item) => {
        list.forEach((child) => {
          item[childKey] = item[childKey] || [];
          if (item[key] === child[parentKey]) {
            item[childKey].push(child);
            child.isUsed = true;
          }
        });
      });
      const l = [];
      list.forEach((item) => {
        if (!item.isUsed) {
          if (indent == null) {
            item.indent = '';
          } else {
            item.indent = indent + '   ';
          }
          l.push(item);
        } else {
          item.isUsed = undefined;
        }
      });
      l.forEach((item) => {
        item[childKey] = this.createTree(item[childKey], key, parentKey, childKey, item.indent);
      });
      return l;
    }
    return [];
  }


  public static pushMessage(app: AppComponent, form: FormGroup, mapControlNameLang?: any) {
    let isBreak: boolean = false;
    for (const controlName in form.controls) {
      const control: AbstractControl = form.get(controlName);
      for (const propertyName in control.errors) {
        if (control.touched) {
          let key = '';
          if (mapControlNameLang) {
            key = app.translation.translate(mapControlNameLang[`${controlName}`]);
          }
          app.warningMessage(`validate.${propertyName}`, undefined, {field: key});
          isBreak = true;
          break;
        }
      }
      if (isBreak) {
        return;
      }
    }
  }

  public static removeHTMLTag(str: any): any {
    return str.replace(/<[^>]*>/g, '').replace(/\&nbsp;/g, '');
  }

  /* VALIDATE COMMON */

  //validate
  public static customValidateReturnLabel(label: string) {
    return (control: FormControl): { [key: string]: any } | null => {
      if (!control.value) {
        return {labelFromCustomValidate: label};
      }
      return null;
    };
  }


  // Compare Date input with Now
  public static customValidateDateNow(dateInput: Date) {
    return (control: FormControl): { [key: string]: any } | null => {
      var now = new Date();

      if (dateInput != null && dateInput.getTime() > now.getTime()) {
        return {compareDateWithNow: true};
      }

    };
  }

  // Compare Date From input with To input
  public static customValidateDateFromWithTo(dateFrom: Date, dateTo: Date) {
    return (control: FormControl): { [key: string]: any } | null => {

      if (dateFrom != null && dateTo != null && dateFrom.getTime() > dateTo.getTime()) {
        dateFrom = null;
        dateTo = null;

        return {compareDateFromWithTo: true};
      }
    };
  }

  // Compare Date From input with To input
  public static customValidateDateFromWithToInModel(dateFrom: Date, dateTo: Date, message: string, app: AppComponent) {
    if (dateFrom != null && dateTo != null && dateFrom.getTime() > dateTo.getTime()) {
      app.toastMessage('warn', message, 'common.messages.FromWithTo.validDate', null);
      return false;
    } else {
      return true;
    }
  }

  // generate default error from Angular Validators
  static generateAngularErrors(control: AbstractControl, label: string) {

    // create array contains errors
    var errorsArray = new Array();

    // if control have error REQUIRED -> add error to array
    if (control.getError('required') == true) {
      if(label.indexOf('requiredSelect//') !== 0) {
      errorsArray.push({error: 'messages.error.requiredInput', label: label});
    } else {
      errorsArray.push({error: 'messages.error.requiredSelect', label: label.substring('requiredSelect//'.length)});
    }
    }
    if (control.getError('pattern')) {
      errorsArray.push({error: 'messages.error.pattern', label: label});
    }
    if (control.getError('minlength')) {
      errorsArray.push({error: 'messages.error.minlength', label: label});
    }
    if (control.getError('maxlength')) {
      errorsArray.push({error: 'messages.error.maxlength', label: label});
    }
    if (control.getError('email') == true) {
      errorsArray.push({error: 'messages.error.email', label: label});
    }
    if (control.getError('compareDateWithNow') == true) {
      errorsArray.push({error: 'messages.error.compareDateWithNow', label: label});
    }
    if (control.getError('compareDateFromWithTo') == true) {
      errorsArray.push({error: 'messages.error.compareDateFromWithTo', label: label});
    }
    if (control.getError('couplerFromGtCouplerTo') == true) {
      errorsArray.push({error: 'messages.error.couplerFromGtCouplerTo', label: label});
    }
    if (control.getError('couplerFromEqCouplerTo') == true) {
      errorsArray.push({error: 'messages.error.couplerFromEqCouplerTo', label: label});
    }
    if (control.getError('destCouplerFromGtDestCouplerTo') == true) {
      errorsArray.push({error: 'messages.error.destCouplerFromGtDestCouplerTo', label: label});
    }
    if (control.getError('destCouplerFromEqDestCouplerTo') == true) {
      errorsArray.push({error: 'messages.error.destCouplerFromEqDestCouplerTo', label: label});
    }
    if (control.getError('lineFromGtLineTo') == true) {
      errorsArray.push({error: 'messages.error.lineFromGtLineTo', label: label});
    }
    if (control.getError('lineFromEqLineTo') == true) {
      errorsArray.push({error: 'messages.error.lineFromEqLineTo', label: label});
    }
    // return array error
    return errorsArray;
  }

  // get form error and show message
  static getFormValidationErrors(form: FormGroup, app: AppComponent, type: string, keyMess?: any) {
    let errorStatus = false;
    // for all controls of form -> key
    for (const key in form.controls) {
      const control: AbstractControl = form.get(key);
      // get all errors of each control in for -> controlErrors
      const controlErrors: ValidationErrors = control.errors;
      // if have error
      if (controlErrors != null) {
        Object.keys(controlErrors).forEach(keyError => {
          // array contain errors of control
          let errorsArray = new Array();

          // get label of each control -> get from custom validator
          if ('labelFromCustomValidate' == keyError) {
            let labelFromControl: string;
            labelFromControl = controlErrors[keyError];

            // fill error of Angular Validators, return array of errors -> errorsArray
            errorsArray = this.generateAngularErrors(form.controls[key], labelFromControl);
          }
          // errorsArray = this.generateAngularErrors(control, type + '.' + key);
          // if have error of Angular Validators
          if (errorsArray.length > 0) {
            errorStatus = true;
            // for all errors array -> show popup error message
            for (let i = 0; i < errorsArray.length; i++) {
              const element = errorsArray[i];
              let keyLabel = app.translation.translate(element.label);

              // let detail = app.translation.translate(element.error);
              // this.messageService.add({severity: severity.toLowerCase(), detail: detail});
              app.errorFormMessage(`${element.error}`, undefined, {field: keyLabel}, keyMess);
            }
          }
        });
      }
    }
    if (errorStatus) {
      return false;
    } else {
      return true;
    }
  }

  public static setValueForDropDown(label: string, value: any) {
    return {label: label, value: value};
  }

  /* VALIDATE COMMON END */

  // get value for keyup
  public static getValueForKeyup(formGroup: FormGroup, key: string, value: any) {
    formGroup.controls[key].setValue(value);
    return formGroup;
  }

  public static cutLongLat(item) {
    // cat long latitude
    item = item.toString();
    const indexOf = item.indexOf('.');
    if (indexOf >= 0) {
      if (item.length > indexOf + 6) {
        item = item.slice(0, indexOf + 6);
      } else {
        item = this.addFiveZeroBehind(indexOf, item);
      }
    } else {
      item = item.concat('.');
      const indexOf2 = item.indexOf('.');
      item = this.addFiveZeroBehind(indexOf2, item);
    }
    return item;
  }

  // automatically add 0 behind
  public static addFiveZeroBehind(indexOf, str) {
    const length = str.length;
    for (let i = length; i < indexOf + 6; i++) {
      str = str.concat('0');
    }
    return str;
  }

  public static addThreeZeroBehind(indexOf, str) {
    const length = str.length;
    for (let i = length; i < indexOf + 4; i++) {
      str = str.concat('0');
    }
    return str;
  }

  public static setColumns(view: string, data: any, sysGridViewService: SysGridViewService) {
    let listRs = [];
    const hrStorage = HrStorage.getUserToken();
    data.forEach(el => {
      listRs.push({gridViewName: view, columnName: el.field});
    });
    if (listRs.length === 0) {
      listRs.push({gridViewName: view, columnName: 'null'});
    }
    sysGridViewService.saveGridView(listRs).subscribe(res => {
    }, er => {
    }, () => {
      sysGridViewService.getGridView({userId: hrStorage.userId}).subscribe(rs => {
        hrStorage.sysGridView = rs.data;
        HrStorage.setUserToken(hrStorage);
      });
    });
  }
}
